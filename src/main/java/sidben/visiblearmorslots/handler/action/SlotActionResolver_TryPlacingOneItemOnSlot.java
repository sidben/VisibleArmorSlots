package sidben.visiblearmorslots.handler.action;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sidben.visiblearmorslots.util.ItemStackHelper;


public class SlotActionResolver_TryPlacingOneItemOnSlot extends SlotActionResolver
{

    private boolean _needsServerSide = false;



    @Override
    public void handleClientSide(Slot targetSlot, EntityPlayer player)
    {
        this._needsServerSide = false;
        this.placeOneItemOnSlot(targetSlot, player);
    }


    @Override
    public void handleServerSide(Slot targetSlot, EntityPlayer player)
    {
        this.placeOneItemOnSlot(targetSlot, player);
    }


    private void placeOneItemOnSlot(Slot targetSlot, EntityPlayer player)
    {

        final ItemStack mouseStack = player.inventory.getItemStack();
        final ItemStack slotStack = targetSlot.getStack();

        if (slotStack.isEmpty()) {
            // Slot is empty, place a new stack of one
            final boolean canPlaceOnSlot = targetSlot.isItemValid(mouseStack) && !mouseStack.isEmpty();
            if (canPlaceOnSlot) {
                targetSlot.putStack(mouseStack.splitStack(1));
                this._needsServerSide = true;
            }


        } else {

            // Mouse and slot have items, place one
            final boolean stacksCompatible = ItemStackHelper.areStacksCompatible(mouseStack, slotStack);
            final boolean isSlotFull = ItemStackHelper.isStackFull(slotStack);

            if (stacksCompatible && !isSlotFull) {
                mouseStack.shrink(1);
                slotStack.grow(1);
                this._needsServerSide = true;
            }

        }

    }



    @Override
    public boolean requiresServerSideHandling()
    {
        return _needsServerSide;
    }


    @Override
    protected boolean isSatisfiedByInternal(SlotActionType action)
    {
        if (action.mouseButton.equals(SlotActionType.EnumMouseAction.PLACE_BLOCK_BUTTON) && action.playerMouseHasItemStack) { return true; }
        return false;
    }

}
