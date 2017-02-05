package sidben.visiblearmorslots.handler.action;

public class SlotActionResolver_DoesNothing extends SlotActionResolver
{

    @Override
    protected boolean isSatisfiedByInternal(SlotActionType action)
    {
        return true;
    }

}
