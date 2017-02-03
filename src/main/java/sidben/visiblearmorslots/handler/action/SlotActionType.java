package sidben.visiblearmorslots.handler.action;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotActionType
{
    
    public enum MouseButton 
    {
        ATTACK_BUTTON(0),
        PLACE_BLOCK_BUTTON(1),
        PICK_BLOCK_BUTTON(2),
        INVALID(-1);
        
        
        public final int buttonValue;
        
        private MouseButton(int button) {
            this.buttonValue = button;
        }
        
        public static MouseButton create(int clickedButton, boolean isPickBlockClick) {
            if (isPickBlockClick) return PICK_BLOCK_BUTTON;
            if (clickedButton == 0) return ATTACK_BUTTON;
            if (clickedButton == 1) return PLACE_BLOCK_BUTTON;
            return INVALID;
        }
    }
    
    
    public static SlotActionType EMPTY = new SlotActionType(false, false, false, false, false, MouseButton.INVALID);
    
    
    

    
    
    
    public final boolean playerMouseHasItemStack;
    @Deprecated // TODO: remove, I can check for this on the swap action handler
    public final boolean playerCanTakeFromSlot;
    public final boolean playerInCreativeMode;
    public final boolean slotHasItemStack;
    public final boolean isShiftPressed;
    public final MouseButton mouseButton;
    
    
    
    
    
    private SlotActionType(boolean playerMouseHasItemStack, 
            boolean playerCanTakeFromSlot,
            boolean playerInCreativeMode,
            boolean slotHasItemStack,
            boolean isShiftPressed,
            MouseButton mouseButton) 
    {
        this.playerMouseHasItemStack = playerMouseHasItemStack;
        this.playerCanTakeFromSlot = playerCanTakeFromSlot;
        this.playerInCreativeMode = playerInCreativeMode;
        this.slotHasItemStack = slotHasItemStack;
        this.isShiftPressed = isShiftPressed;
        this.mouseButton = mouseButton;
    }
    
    
    
    public static SlotActionType create(EntityPlayer player, Slot slot, boolean shiftPressed, MouseButton mouseButton)
    {
        if (player != null && slot != null)
        {
            return new SlotActionType(!player.inventory.getItemStack().isEmpty(), 
                    slot.canTakeStack(player), 
                    player.capabilities.isCreativeMode, 
                    slot.getHasStack(), 
                    shiftPressed, 
                    mouseButton);
        }
        
        return SlotActionType.EMPTY;
    }
    
    
    
    
    
    @Override
    public boolean equals(Object other)
    {
        if (other == null) return false;
        if (this.getClass() != other.getClass()) return false;

        SlotActionType otherConverted = (SlotActionType) other;
        if (this.isShiftPressed != otherConverted.isShiftPressed) return false;
        if (this.playerCanTakeFromSlot != otherConverted.playerCanTakeFromSlot) return false;
        if (this.playerInCreativeMode != otherConverted.playerInCreativeMode) return false;
        if (this.playerMouseHasItemStack != otherConverted.playerMouseHasItemStack) return false;
        if (this.slotHasItemStack != otherConverted.slotHasItemStack) return false;
        if (this.mouseButton != otherConverted.mouseButton) return false;
            
        return true;
    }


    @Override
    public int hashCode()
    {
        int hashCode = 17;

        hashCode = hashCode * 59 + Boolean.valueOf(this.isShiftPressed).hashCode();
        hashCode = hashCode * 59 + Boolean.valueOf(this.playerCanTakeFromSlot).hashCode();
        hashCode = hashCode * 59 + Boolean.valueOf(this.playerInCreativeMode).hashCode();
        hashCode = hashCode * 59 + Boolean.valueOf(this.playerMouseHasItemStack).hashCode();
        hashCode = hashCode * 59 + Boolean.valueOf(this.slotHasItemStack).hashCode();
        hashCode = hashCode * 59 + this.mouseButton.buttonValue;

        return hashCode;
    }

    
    
    @Override
    public String toString()
    {
        final StringBuilder r = new StringBuilder();

        r.append("SlotActionType [Hash code: " + this.hashCode());
        r.append(" - playerMouseHasItemStack: " + this.playerMouseHasItemStack);
        r.append(", playerCanTakeFromSlot: " + this.playerCanTakeFromSlot);
        r.append(", playerInCreativeMode: " + this.playerInCreativeMode);
        r.append(", slotHasItemStack: " + this.slotHasItemStack);
        r.append(", isShiftPressed: " + this.isShiftPressed);
        r.append(", mouseButton: " + this.mouseButton);
        r.append("]");

        return r.toString();
    }

    
}
