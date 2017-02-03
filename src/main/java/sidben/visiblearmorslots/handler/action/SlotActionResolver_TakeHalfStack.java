package sidben.visiblearmorslots.handler.action;

import sidben.visiblearmorslots.helper.LogHelper;


public class SlotActionResolver_TakeHalfStack extends SlotActionResolver
{

    @Override
    protected boolean isSatisfiedByInternal(SlotActionType action)
    {
        if (action.mouseButton.equals(SlotActionType.MouseButton.PLACE_BLOCK_BUTTON) && action.slotHasItemStack) { return true; }
        return false;
    }

}
