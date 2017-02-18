package sidben.visiblearmorslots.handler.action;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sidben.visiblearmorslots.util.LogHelper;


/**
 * Try to send the item on the overlay slot to a specific hotbar slot and/or equip
 * the item on that hotbar slot.
 */
public class SlotActionResolver_TrySwapSlotWithHotbar extends SlotActionResolver
{

    private boolean                                 _needsServerSide = false;
    private final int                               _hotbarIndex;
    private final SlotActionType.EnumKeyboardAction _keyboarcAction;



    /**
     * @param hotbarIndex
     *            The hotbar slot index, from 0 to 9.
     */
    public SlotActionResolver_TrySwapSlotWithHotbar(int hotbarIndex) {
        this._hotbarIndex = hotbarIndex;
        this._keyboarcAction = SlotActionType.EnumKeyboardAction.createHotbar(_hotbarIndex);
    }



    @Override
    public void handleClientSide(Slot targetSlot, EntityPlayer player)
    {
        this._needsServerSide = false;
        this.swapSlotWithPlayerInventory(targetSlot, player);
    }


    @Override
    public void handleServerSide(Slot targetSlot, EntityPlayer player)
    {
        this.swapSlotWithPlayerInventory(targetSlot, player);
    }


    private void swapSlotWithPlayerInventory(Slot targetSlot, EntityPlayer player)
    {
        if (player.openContainer == null || player.openContainer.inventorySlots.size() < 9) { return; }


        LogHelper.trace("swapSlotWithPlayerInventory()");
        LogHelper.trace("    My slot index: %d", this._hotbarIndex);
        LogHelper.trace("    Target slot: %s has %s", targetSlot, targetSlot.getStack());


        // NOTE: Assume that the last 9 slots are the player hotbar
        final int playerFirstHotbarIndex = Math.max(player.openContainer.inventorySlots.size() - 9, 0);
        final int targetHotbarIndex = playerFirstHotbarIndex + this._hotbarIndex;
        final Slot hotbarSlot = player.openContainer.inventorySlots.get(targetHotbarIndex);

        LogHelper.trace("    Player slot: %s has %s", hotbarSlot, hotbarSlot.getStack());

        final ItemStack hotbarStack = hotbarSlot.getStack();
        final boolean canPlaceOnSlot = hotbarStack.isEmpty() || targetSlot.isItemValid(hotbarStack);
        final boolean canTakeFromSlot = targetSlot.getStack().isEmpty() || targetSlot.canTakeStack(player);
        final boolean bothSlotsEmpty = hotbarStack.isEmpty() && targetSlot.getStack().isEmpty();

        if (canPlaceOnSlot && canTakeFromSlot && !bothSlotsEmpty) {
            // Swaps the item on selected hotbar slot with the (gui overlay) target slot
            hotbarSlot.putStack(targetSlot.getStack());
            targetSlot.putStack(hotbarStack);
            this._needsServerSide = true;
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
        /*
         * final EnumSet<EnumKeyboardAction> hotbarSlots = EnumSet.of(EnumKeyboardAction.HOTBAR_SLOT_1, EnumKeyboardAction.HOTBAR_SLOT_2, EnumKeyboardAction.HOTBAR_SLOT_3, EnumKeyboardAction.HOTBAR_SLOT_4, EnumKeyboardAction.HOTBAR_SLOT_5, EnumKeyboardAction.HOTBAR_SLOT_6,
         * EnumKeyboardAction.HOTBAR_SLOT_7, EnumKeyboardAction.HOTBAR_SLOT_8, EnumKeyboardAction.HOTBAR_SLOT_9);
         * if (hotbarSlots.contains(action.keyboardKey) && (!action.playerMouseHasItemStack)) { return true; }
         */
        return action.keyboardKey == this._keyboarcAction;
    }

}
