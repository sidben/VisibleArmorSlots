package sidben.visiblearmorslots.handler.action;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;


/**
 * Takes half of the items in the target slot.
 */
public class SlotActionResolver_TakeHalfStack extends SlotActionResolver
{

    @Override
    public void handleClientSide(Slot targetSlot, EntityPlayer player)
    {
        this.takeHalfStack(targetSlot, player);
    }


    @Override
    public void handleServerSide(Slot targetSlot, EntityPlayer player)
    {
        this.takeHalfStack(targetSlot, player);
    }


    /**
     * Reference: {@link net.minecraft.inventory.Container#slotClick() Container.slotClick()}
     */
    private void takeHalfStack(Slot targetSlot, EntityPlayer player)
    {
        final int amount = (targetSlot.getStack().getCount() + 1) / 2;

        final ItemStack slotStack = targetSlot.getStack();
        player.inventory.setItemStack(targetSlot.decrStackSize(amount));
        if (slotStack.isEmpty()) {
            targetSlot.putStack(ItemStack.EMPTY);
        }
        targetSlot.onTake(player, player.inventory.getItemStack());
    }



    @Override
    public boolean requiresServerSideHandling()
    {
        return true;
    }


    @Override
    protected boolean isSatisfiedByInternal(SlotActionType action)
    {
        if (action.mouseButton.equals(SlotActionType.MouseButton.PLACE_BLOCK_BUTTON) && action.slotHasItemStack && !action.playerMouseHasItemStack) { return true; }
        return false;
    }

}
