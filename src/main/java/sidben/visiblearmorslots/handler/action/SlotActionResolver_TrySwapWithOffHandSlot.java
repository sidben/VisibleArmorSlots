package sidben.visiblearmorslots.handler.action;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sidben.visiblearmorslots.inventory.SlotOffHand;
import sidben.visiblearmorslots.main.ModConfig;


/**
 * Sends/swaps the contents of the slot under the mouse to the off-hand slot.
 */
public class SlotActionResolver_TrySwapWithOffHandSlot extends SlotActionResolver
{

    private final String CREATIVE_CONTAINER_NAME = "ContainerCreative";
    private final int    OFF_HAND_SLOT_INDEX     = 40;

    private boolean      _needsServerSide        = false;



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

        final ItemStack offHandStack = player.inventory.getStackInSlot(OFF_HAND_SLOT_INDEX);
        final ItemStack slotStack = targetSlot.getStack();
        if (offHandStack.isEmpty() && slotStack.isEmpty()) { return; }

        final int maxItemsAllowed = targetSlot.getItemStackLimit(offHandStack);

        // -- NOTE --
        // When in creative mode, the player open container is {@link net.minecraft.client.gui.inventory.GuiContainerCreative$ContainerCreative}
        // and the slots on the player regular inventory are converted to {@link net.minecraft.client.gui.inventory.GuiContainerCreative$CreativeSlot}.
        //
        // The slots on the creative tabs are basic instances of {@link net.minecraft.inventory.Slot}.
        // Slots of the creative inventory have an InventoryBasic reference, player slots have a InventoryPlayer reference.

        final boolean isPlayerOnCreativeInventory = player.openContainer.getClass().getName().contains(CREATIVE_CONTAINER_NAME);
        final boolean isTargetSlotFromCreativeTabs = isPlayerOnCreativeInventory && !(targetSlot.inventory instanceof InventoryPlayer);



        if (isTargetSlotFromCreativeTabs) {
            if (offHandStack.isEmpty()) {
                // --- Clones a full stack from the creative menu
                final ItemStack clonedStack = targetSlot.getStack().copy();
                clonedStack.setCount(clonedStack.getMaxStackSize());
                player.inventory.setInventorySlotContents(OFF_HAND_SLOT_INDEX, clonedStack);
            }


        } else {

            this._needsServerSide = true;

            /**
             * Reference: {@link net.minecraft.inventory.Container#slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player),
             * where ClickType == ClickType.SWAP and dragType == 0 to 9, the hotbar slot}
             */

            if (offHandStack.isEmpty() && targetSlot.canTakeStack(player)) {
                // --- Places item on the off-hand slot
                player.inventory.setInventorySlotContents(OFF_HAND_SLOT_INDEX, slotStack);
                // targetSlot.onSwapCraft(slotStack.getCount()); // Not visible, only used in SlotCrafting
                targetSlot.putStack(ItemStack.EMPTY);
                targetSlot.onTake(player, slotStack);


            } else if (slotStack.isEmpty() && targetSlot.isItemValid(offHandStack)) {
                // --- Places item on the target slot
                if (offHandStack.getCount() > maxItemsAllowed) {
                    targetSlot.putStack(offHandStack.splitStack(maxItemsAllowed));
                } else {
                    targetSlot.putStack(offHandStack);
                    player.inventory.setInventorySlotContents(OFF_HAND_SLOT_INDEX, ItemStack.EMPTY);
                }


            } else if (targetSlot.canTakeStack(player) && targetSlot.isItemValid(offHandStack)) {
                // --- Swap slots
                if (offHandStack.getCount() > maxItemsAllowed) {
                    targetSlot.putStack(offHandStack.splitStack(maxItemsAllowed));
                    targetSlot.onTake(player, slotStack);
                    if (!player.inventory.addItemStackToInventory(slotStack)) {
                        player.dropItem(slotStack, true);
                    }

                } else {
                    targetSlot.putStack(offHandStack);
                    player.inventory.setInventorySlotContents(OFF_HAND_SLOT_INDEX, slotStack);
                    targetSlot.onTake(player, slotStack);
                }


            }



        } // if (isTargetSlotFromCreativeTabs)


    }



    @Override
    public boolean requiresServerSideHandling()
    {
        return this._needsServerSide;
    }


    @Override
    protected boolean isSatisfiedByInternal(SlotActionType action)
    {
        return ModConfig.swapKeyEnabled() && action.keyboardKey == SlotActionType.EnumKeyboardAction.SWAP_HANDS;
    }


}
