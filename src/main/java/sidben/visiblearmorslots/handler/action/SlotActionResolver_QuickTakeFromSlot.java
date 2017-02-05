package sidben.visiblearmorslots.handler.action;

import sidben.visiblearmorslots.helper.LogHelper;


public class SlotActionResolver_QuickTakeFromSlot extends SlotActionResolver
{


    @Override
    public boolean requiresServerSideHandling()
    {
        return true;
    }
    
    
    @Override
    protected boolean isSatisfiedByInternal(SlotActionType action)
    {
        if (action.mouseButton.equals(SlotActionType.MouseButton.ATTACK_BUTTON) && action.isShiftPressed && action.slotHasItemStack) { return true; }
        return false;
    }

}
