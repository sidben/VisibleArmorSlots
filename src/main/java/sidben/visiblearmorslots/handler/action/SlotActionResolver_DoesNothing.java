package sidben.visiblearmorslots.handler.action;

import sidben.visiblearmorslots.helper.LogHelper;


public class SlotActionResolver_DoesNothing extends SlotActionResolver
{

    @Override
    protected boolean isSatisfiedByInternal(SlotActionType action)
    {
        return true;
    }

}
