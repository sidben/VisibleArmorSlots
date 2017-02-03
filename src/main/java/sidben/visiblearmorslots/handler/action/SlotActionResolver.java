package sidben.visiblearmorslots.handler.action;

import sidben.visiblearmorslots.helper.LogHelper;


public abstract class SlotActionResolver implements ISlotActionResolver
{


    @Override
    public void handleClientSide()
    {
    }

    
    @Override
    public void handleServerSide()
    {
    }

    
    @Override
    public final boolean isSatisfiedBy(SlotActionType action)
    {
        boolean result = this.isSatisfiedByInternal(action);
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
