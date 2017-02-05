package sidben.visiblearmorslots.handler.action;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sidben.visiblearmorslots.helper.LogHelper;


public class SlotActionResolver_Clone extends SlotActionResolver
{


    @Override
    public void handleClientSide(Slot targetSlot, EntityPlayer player)
    {
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
        if (targetSlot.getStack().isEmpty()) return;
        
        ItemStack clonedStack = targetSlot.getStack().copy();
        clonedStack.setCount(clonedStack.getMaxStackSize());
        player.inventory.setItemStack(clonedStack);
    }

    
    
    @Override
    public boolean requiresServerSideHandling()
    {
        return true;
    }
    
    
    @Override
    protected boolean isSatisfiedByInternal(SlotActionType action)
    {
        if (action.mouseButton.equals(SlotActionType.MouseButton.PICK_BLOCK_BUTTON) && action.playerInCreativeMode && action.slotHasItemStack) { return true; }
        return false;
    }

}
