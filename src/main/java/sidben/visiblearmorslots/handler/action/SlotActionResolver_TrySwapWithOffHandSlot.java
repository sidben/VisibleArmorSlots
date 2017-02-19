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

    private final String                            CREATIVE_CONTAINER_NAME = "ContainerCreative";
    private final String                            CREATIVE_SLOT_NAME = "CreativeSlot";

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
        if (targetSlot instanceof SlotOffHand) { return; }

        final Slot offHandSlot = player.inventoryContainer.getSlot(45);

        LogHelper.debug("swapWithOffHandSlot()");
        LogHelper.trace("  Target slot at %d, %d, stack %s", targetSlot.xPos, targetSlot.yPos, targetSlot.getStack());
        LogHelper.trace("  Player slot at %d, %d, stack %s", offHandSlot.xPos, offHandSlot.yPos, offHandSlot.getStack());
        
        LogHelper.trace("  - %s", targetSlot.getClass());
        LogHelper.trace("  - %s", player.openContainer);


        final ItemStack offHandStack = offHandSlot.getStack();
        final boolean canPlaceOnSlot = offHandStack.isEmpty() || targetSlot.isItemValid(offHandStack);
        final boolean canTakeFromSlot = targetSlot.getStack().isEmpty() || targetSlot.canTakeStack(player);
        final boolean bothSlotsEmpty = offHandStack.isEmpty() && targetSlot.getStack().isEmpty();


        if (canPlaceOnSlot && canTakeFromSlot && !bothSlotsEmpty) {

            // NOTE: When in creative mode, the player open container is {@link net.minecraft.client.gui.inventory.GuiContainerCreative$ContainerCreative}
            //       and the slots on the player regular inventory are converted to {@link net.minecraft.client.gui.inventory.GuiContainerCreative$CreativeSlot}.
            //
            //       The slots on the creative tabs are basic instances of {@link net.minecraft.inventory.Slot}.
            
            final boolean isPlayerOnCreativeInventory = player.openContainer.getClass().getName().contains(CREATIVE_CONTAINER_NAME);
            final boolean isTargetSlotFromCreativeTabs = isPlayerOnCreativeInventory && !targetSlot.getClass().getName().contains(CREATIVE_SLOT_NAME);
            
            // TODO: Fix hotbar slots being considered creative slots

            
            if (isTargetSlotFromCreativeTabs && offHandStack.isEmpty()) {
                // Clones a full stack from the creative menu
                final ItemStack clonedStack = targetSlot.getStack().copy();
                clonedStack.setCount(clonedStack.getMaxStackSize());
                offHandSlot.putStack(clonedStack);
                
            } else if (!isTargetSlotFromCreativeTabs) {
                final int maxItemsAllowed = targetSlot.getItemStackLimit(offHandStack);
                
                if (offHandStack.getCount() <= maxItemsAllowed) {
                    // Swaps the item on the off hand slot with the target slot
                    offHandSlot.putStack(targetSlot.getStack());
                    targetSlot.putStack(offHandStack);
                    targetSlot.onTake(player, offHandSlot.getStack());
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
