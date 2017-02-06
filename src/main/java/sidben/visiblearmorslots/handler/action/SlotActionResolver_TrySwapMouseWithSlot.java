package sidben.visiblearmorslots.handler.action;

import java.util.EnumSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import sidben.visiblearmorslots.handler.action.SlotActionType.MouseButton;
import sidben.visiblearmorslots.util.ItemStackHelper;


// TODO: Future config: can place any item on head slot
// TODO: make the item disappears instantly, like vanilla hotbar slot


/**
 * Try to place the item on the player mouse on the given slot, and/or
 * take the item currently on the slot.
 *
 * Further validation rules, like checking it the slot can accept the
 * item or if the player can take the item should be performed on both
 * handling methods.
 */
public class SlotActionResolver_TrySwapMouseWithSlot extends SlotActionResolver
{

    @Override
    public void handleClientSide(Slot targetSlot, EntityPlayer player)
    {
        this.swapMouseWithSlot(targetSlot, player);
    }


    @Override
    public void handleServerSide(Slot targetSlot, EntityPlayer player)
    {
        this.swapMouseWithSlot(targetSlot, player);
    }


    private void swapMouseWithSlot(Slot targetSlot, EntityPlayer player)
    {
        final ItemStack playerMouseItem = player.inventory.getItemStack();

        final boolean canPlaceOnSlot = playerMouseItem.isEmpty() || targetSlot.isItemValid(playerMouseItem);
        final boolean canTakeFromSlot = targetSlot.getStack().isEmpty() || targetSlot.canTakeStack(player);
        final boolean stacksCompatible = ItemStackHelper.areStacksCompatible(playerMouseItem, targetSlot.getStack());

        if (stacksCompatible && targetSlot.getStack().isStackable()) {
            // Combines the stacks
            final int amountTheSlotCanTake = Math.max(targetSlot.getStack().getMaxStackSize() - targetSlot.getStack().getCount(), 0);
            final int amountTheSlotWillTake = MathHelper.clamp(playerMouseItem.getCount(), 0, amountTheSlotCanTake);
            targetSlot.getStack().grow(amountTheSlotWillTake);
            playerMouseItem.shrink(amountTheSlotWillTake);
            targetSlot.onSlotChanged();
            
        } else if (canPlaceOnSlot && canTakeFromSlot) {
            // Swaps the item on the player mouse with the target slot
            player.inventory.setItemStack(targetSlot.getStack());
            targetSlot.putStack(playerMouseItem);
            
        }
    }



    @Override
    public boolean requiresServerSideHandling()
    {
        return true;
    }


    @Override
    protected boolean isSatisfiedByInternal(SlotActionType action)
    {
        final EnumSet<MouseButton> validButtons = EnumSet.of(MouseButton.ATTACK_BUTTON, MouseButton.PLACE_BLOCK_BUTTON);
        if (validButtons.contains(action.mouseButton) && (action.playerMouseHasItemStack || action.slotHasItemStack)) { return true; }
        return false;
    }

}
