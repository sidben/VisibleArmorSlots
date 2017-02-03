package sidben.visiblearmorslots.handler.action;

import sidben.visiblearmorslots.helper.LogHelper;


public class SlotActionResolver_Clone extends SlotActionResolver
{

    @Override
    protected boolean isSatisfiedByInternal(SlotActionType action)
    {
        if (action.mouseButton.equals(SlotActionType.MouseButton.PICK_BLOCK_BUTTON) && action.playerInCreativeMode && action.slotHasItemStack) { return true; }
        return false;
    }

}
