package sidben.visiblearmorslots.handler.action;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import sidben.visiblearmorslots.util.ItemStackHelper;


/**
 * Moves all items from the selected slot to the player inventory or hotbar.
 */
public class SlotActionResolver_QuickTakeFromSlot extends SlotActionResolver
{

    @Override
    public void handleClientSide(Slot targetSlot, EntityPlayer player)
    {
        this.quickTake(targetSlot, player);
    }


    @Override
    public void handleServerSide(Slot targetSlot, EntityPlayer player)
    {
        this.quickTake(targetSlot, player);
    }


    /**
     * Reference: {@link net.minecraft.inventory.Container#mergeItemStack() Container.mergeItemStack()}
     */
    private void quickTake(Slot targetSlot, EntityPlayer player)
    {
        if (targetSlot.getStack().isEmpty() || !targetSlot.canTakeStack(player)) { return; }


        final int playerLastSlotIndex = player.openContainer.inventorySlots.size();
        final int playerFirstSlotIndex = Math.max(playerLastSlotIndex - 36, 0);       // 36 == 9 hotbar slots + 27 inventory slots
        ItemStack originalStack = targetSlot.getStack();


        // First tries to stack the item with compatible slots
        for (int i = playerFirstSlotIndex; i < playerLastSlotIndex; i++) {
            final Slot slot = player.openContainer.inventorySlots.get(i);
            final ItemStack slotStack = slot.getStack();

            if (slot.isItemValid(originalStack)) {
                // TODO: create a helper method maxTransferAmount
                final boolean stacksCompatible = ItemStackHelper.areStacksCompatible(originalStack, slotStack);
                final int amountTheSlotCanTake = Math.max(slotStack.getMaxStackSize() - slotStack.getCount(), 0);
                final int amountTheSlotWillTake = stacksCompatible ? MathHelper.clamp(originalStack.getCount(), 0, amountTheSlotCanTake) : 0;

                if (amountTheSlotWillTake > 0) {
                    slotStack.grow(amountTheSlotWillTake);
                    originalStack.shrink(amountTheSlotWillTake);
                    slot.onSlotChanged();
                }
            }

            if (originalStack.isEmpty()) {
                break;
            }
        } // for


        // Then seeks for empty slots, if needed
        if (!originalStack.isEmpty()) {
            for (int i = playerFirstSlotIndex; i < playerLastSlotIndex; i++) {
                final Slot slot = player.openContainer.inventorySlots.get(i);

                if (slot.getStack().isEmpty() && slot.isItemValid(originalStack)) {
                    slot.putStack(originalStack);
                    originalStack = ItemStack.EMPTY;
                    break;
                }
            }
        }


        targetSlot.putStack(originalStack);
    }



    @Override
    public boolean requiresServerSideHandling()
    {
        return true;
    }


    @Override
    protected boolean isSatisfiedByInternal(SlotActionType action)
    {
        if (action.mouseButton.equals(SlotActionType.MouseButton.ATTACK_BUTTON) && action.isShiftPressed && action.slotHasItemStack) { return true; }
        return false;
    }

}
