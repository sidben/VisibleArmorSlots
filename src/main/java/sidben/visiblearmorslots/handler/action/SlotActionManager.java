package sidben.visiblearmorslots.handler.action;

import java.util.HashMap;
import sidben.visiblearmorslots.handler.InfoGuiOverlayDisplayParams;
import sidben.visiblearmorslots.handler.action.SlotActionType.MouseButton;
import sidben.visiblearmorslots.helper.LogHelper;

/**
 *  Works like an event handler for slot actions.
 */
public class SlotActionManager 
{
    
    private ISlotActionResolver[] _actionResolvers = {
            new SlotActionResolver_Clone(),
            new SlotActionResolver_TryPlacingOneItemOnSlot(),
            new SlotActionResolver_QuickTakeFromSlot(),
            new SlotActionResolver_TakeHalfStack(),
            new SlotActionResolver_TrySwapMouseWithSlot(),
            new SlotActionResolver_DoesNothing() 
        };
    
    
    
    public static SlotActionManager instance = new SlotActionManager(); 
    
    
    private SlotActionManager() 
    {
    }
    
    
    


    private ISlotActionResolver getResolverForAction(SlotActionType actionType)
    {
        // LogHelper.debug("***TEST*** %s", (MouseButton.ATTACK_BUTTON & (MouseButton.ATTACK_BUTTON | MouseButton.PLACE_BLOCK_BUTTON))));
        
        for (ISlotActionResolver actionResolver : this._actionResolvers)
        {
            if (actionResolver.isSatisfiedBy(actionType)) {
                LogHelper.trace("SlotActionManager: Using [%s] to resolve %s", actionResolver, actionType);
                return actionResolver;
            }
        }

        return null;
    }
    
    
    
    /**
     * Process what should happen on the server. 
     */
    public void processActionOnServer(SlotActionType actionType) 
    {
        ISlotActionResolver actionResolver = this.getResolverForAction(actionType);
        if (actionResolver != null) actionResolver.handleServerSide();
    }
    
    
    /**
     * Process what should happen on the client. 
     */
    public void processActionOnClient(SlotActionType actionType) 
    {
        ISlotActionResolver actionResolver = this.getResolverForAction(actionType);
        if (actionResolver != null) actionResolver.handleClientSide();
    }

    
}
