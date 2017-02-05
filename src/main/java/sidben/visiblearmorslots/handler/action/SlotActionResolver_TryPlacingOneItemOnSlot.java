package sidben.visiblearmorslots.handler.action;

import sidben.visiblearmorslots.helper.LogHelper;


public class SlotActionResolver_TryPlacingOneItemOnSlot extends SlotActionResolver
{


    @Override
    public boolean requiresServerSideHandling()
    {
        return true;
    }
    
    
    @Override
    protected boolean isSatisfiedByInternal(SlotActionType action)
    {
        if (action.mouseButton.equals(SlotActionType.MouseButton.PLACE_BLOCK_BUTTON) && action.playerMouseHasItemStack) { return true; }
        return false;
    }

}
