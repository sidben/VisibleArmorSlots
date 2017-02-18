package sidben.visiblearmorslots.handler.action;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;


public class SlotActionResolver_Clone extends SlotActionResolver
{

    private boolean _needsServerSide = false;


    @Override
    public void handleClientSide(Slot targetSlot, EntityPlayer player)
    {
        this._needsServerSide = false;
        this.cloneStack(targetSlot, player);
    }


    @Override
    public void handleServerSide(Slot targetSlot, EntityPlayer player)
    {
        this.cloneStack(targetSlot, player);
    }


    /**
     * Reference: {@link net.minecraft.inventory.Container#slotClick() Container.slotClick()}
     */
    private void cloneStack(Slot targetSlot, EntityPlayer player)
    {
        if (targetSlot.getStack().isEmpty()) { return; }

        final ItemStack clonedStack = targetSlot.getStack().copy();
        clonedStack.setCount(clonedStack.getMaxStackSize());
        player.inventory.setItemStack(clonedStack);
        this._needsServerSide = true;
    }



    @Override
    public boolean requiresServerSideHandling()
    {
        return this._needsServerSide;
    }


    @Override
    protected boolean isSatisfiedByInternal(SlotActionType action)
    {
        if (action.mouseButton.equals(SlotActionType.EnumMouseAction.PICK_BLOCK_BUTTON) && action.playerInCreativeMode && action.slotHasItemStack) { return true; }
        return false;
    }

}
