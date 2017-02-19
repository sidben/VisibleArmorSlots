package sidben.visiblearmorslots.handler.action;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sidben.visiblearmorslots.inventory.SlotOffHand;
import sidben.visiblearmorslots.util.LogHelper;

/**
 * Sends/swaps the contents of the slot under the mouse to the off-hand slot.
 */
public class SlotActionResolver_TrySwapWithOffHandSlot extends SlotActionResolver
{

    private boolean _needsServerSide = false;

    
    @Override
    public void handleClientSide(Slot targetSlot, EntityPlayer player)
    {
        this._needsServerSide = false;
        this.swapWithOffHandSlot(targetSlot, player);
    }


    @Override
    public void handleServerSide(Slot targetSlot, EntityPlayer player)
    {
        this.swapWithOffHandSlot(targetSlot, player);
    }

    
    
    private void swapWithOffHandSlot(Slot targetSlot, EntityPlayer player)
    {
        if (targetSlot instanceof SlotOffHand) return;
        
        Slot offHandSlot = player.inventoryContainer.getSlot(45);

        LogHelper.debug("swapWithOffHandSlot()");
        LogHelper.trace("  Target slot at %d, %d, stack %s", targetSlot.xPos, targetSlot.yPos, targetSlot.getStack());
        LogHelper.trace("  Player slot at %d, %d, stack %s", offHandSlot.xPos, offHandSlot.yPos, offHandSlot.getStack());
        

        final ItemStack offHandStack = offHandSlot.getStack();
        final boolean canPlaceOnSlot = offHandStack.isEmpty() || targetSlot.isItemValid(offHandStack);
        final boolean canTakeFromSlot = targetSlot.getStack().isEmpty() || targetSlot.canTakeStack(player);
        final boolean bothSlotsEmpty = offHandStack.isEmpty() && targetSlot.getStack().isEmpty();

        
        // TODO: handle creative menu (should take a full stack and place nothing)
        // TODO: handle crafting slots not consuming the recipes
        
        if (canPlaceOnSlot && canTakeFromSlot && !bothSlotsEmpty) {
            
            int maxItemsAllowed = targetSlot.getItemStackLimit(offHandStack);
            
            if (offHandStack.getCount() <= maxItemsAllowed) {
                // Swaps the item on the off hand slot with the target slot
                offHandSlot.putStack(targetSlot.getStack());
                targetSlot.putStack(offHandStack);
                this._needsServerSide = true;
                
            } else {
                // This target slot has limited capacity, only places a few items, if the slot is empty.
                if (targetSlot.getStack().isEmpty()) {
                    targetSlot.putStack(offHandSlot.decrStackSize(maxItemsAllowed));
                    targetSlot.onSlotChanged();
                    this._needsServerSide = true;
                }
                
            }
            
        }
        
    }
    
    
    
    @Override
    public boolean requiresServerSideHandling()
    {
        return this._needsServerSide;
    }


    @Override
    protected boolean isSatisfiedByInternal(SlotActionType action)
    {
        return action.keyboardKey == SlotActionType.EnumKeyboardAction.SWAP_HANDS;
    }

    
}
