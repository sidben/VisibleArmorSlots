package sidben.visiblearmorslots.handler.action;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;


public abstract class SlotActionResolver implements ISlotActionResolver
{


    @Override
    public void handleClientSide(Slot targetSlot, EntityPlayer player)
    {
    }


    @Override
    public void handleServerSide(Slot targetSlot, EntityPlayer player)
    {
    }


    @Override
    public boolean requiresServerSideHandling()
    {
        return false;
    }


    @Override
    public final boolean isSatisfiedBy(SlotActionType action)
    {
        final boolean result = this.isSatisfiedByInternal(action);
        // LogHelper.trace("%s.isSatisfiedBy() == %s", this.getClass().getSimpleName(), result);
        return result;
    }


    @Override
    public String toString()
    {
        return this.getClass().getSimpleName();
    }


    protected abstract boolean isSatisfiedByInternal(SlotActionType action);
}
