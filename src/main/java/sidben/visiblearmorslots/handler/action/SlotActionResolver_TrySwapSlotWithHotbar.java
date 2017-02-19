package sidben.visiblearmorslots.handler.action;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;


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

        // NOTE: Assume that the last 9 slots are the player hotbar
        final int playerFirstHotbarIndex = Math.max(player.openContainer.inventorySlots.size() - 9, 0);
        final int targetHotbarIndex = playerFirstHotbarIndex + this._hotbarIndex;
        final Slot hotbarSlot = player.openContainer.inventorySlots.get(targetHotbarIndex);

        final ItemStack hotbarStack = hotbarSlot.getStack();
        final boolean canPlaceOnSlot = hotbarStack.isEmpty() || targetSlot.isItemValid(hotbarStack);
        final boolean canTakeFromSlot = targetSlot.getStack().isEmpty() || targetSlot.canTakeStack(player);
        final boolean bothSlotsEmpty = hotbarStack.isEmpty() && targetSlot.getStack().isEmpty();

        if (canPlaceOnSlot && canTakeFromSlot && !bothSlotsEmpty) {
            // Swaps the item on selected hotbar slot with the (gui overlay) target slot
            hotbarSlot.putStack(targetSlot.getStack());
            targetSlot.putStack(hotbarStack);
            targetSlot.onTake(player, hotbarSlot.getStack());
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
        return action.keyboardKey == this._keyboarcAction;
    }

}
