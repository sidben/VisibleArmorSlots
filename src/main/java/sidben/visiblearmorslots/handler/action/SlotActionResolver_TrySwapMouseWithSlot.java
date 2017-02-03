package sidben.visiblearmorslots.handler.action;

import sidben.visiblearmorslots.handler.action.SlotActionType.MouseButton;
import sidben.visiblearmorslots.helper.LogHelper;


/**
 * Try to place the item on the player mouse on the given slot, and/or
 * take the item currently on the slot.
 * 
 * Further validation rules, like checking it the slot can accept the 
 * item or if the player can take the item should be performed on both
 * handling methods.
 */
public class SlotActionResolver_TrySwapMouseWithSlot extends SlotActionResolver
{

    @Override
    public void handleClientSide()
    {
        // LogHelper.trace("Handle on client");
    }

    @Override
    public void handleServerSide()
    {
        // LogHelper.trace("Handle on server");
    }

    @Override
    protected boolean isSatisfiedByInternal(SlotActionType action)
    {
        // TODO: bitwise operator
        if ((action.mouseButton.equals(MouseButton.ATTACK_BUTTON) || action.mouseButton.equals(MouseButton.PLACE_BLOCK_BUTTON)) 
                && (action.playerMouseHasItemStack || action.slotHasItemStack)) { return true; }
        return false;
    }

}
