package sidben.visiblearmorslots.handler.action;

import javax.annotation.concurrent.Immutable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;


@Immutable
public class SlotActionType
{

    @Immutable
    public enum EnumMouseAction {
        ATTACK_BUTTON(0),
        PLACE_BLOCK_BUTTON(1),
        PICK_BLOCK_BUTTON(2),
        INVALID(-1);


        public final int buttonValue;

        private EnumMouseAction(int button) {
            this.buttonValue = button;
        }


        public static EnumMouseAction create(int clickedButton, boolean isPickBlockClick)
        {
            if (isPickBlockClick) { return PICK_BLOCK_BUTTON; }
            if (clickedButton == 0) { return ATTACK_BUTTON; }
            if (clickedButton == 1) { return PLACE_BLOCK_BUTTON; }
            return INVALID;
        }
    }


    @Immutable
    public enum EnumKeyboardAction {
        HOTBAR_SLOT_1(0),
        HOTBAR_SLOT_2(1),
        HOTBAR_SLOT_3(2),
        HOTBAR_SLOT_4(3),
        HOTBAR_SLOT_5(4),
        HOTBAR_SLOT_6(5),
        HOTBAR_SLOT_7(6),
        HOTBAR_SLOT_8(7),
        HOTBAR_SLOT_9(8),
        SWAP_HANDS(10),
        INVALID(-1);

        public final int keySerializingValue;

        private EnumKeyboardAction(int key) {
            this.keySerializingValue = key;
        }


        public static EnumKeyboardAction createHotbar(int hotbarIndex)
        {
            if (hotbarIndex >= 0 && hotbarIndex < 9) {
                for (final EnumKeyboardAction item : EnumKeyboardAction.values()) {
                    if (item.keySerializingValue == hotbarIndex) { return item; }
                }
            }

            return INVALID;
        }

    }



    public static SlotActionType    EMPTY = new SlotActionType(false, false, false, false, EnumMouseAction.INVALID, EnumKeyboardAction.INVALID);



    public final boolean            playerMouseHasItemStack;
    public final boolean            playerInCreativeMode;
    public final boolean            slotHasItemStack;
    public final boolean            isShiftPressed;
    public final EnumMouseAction    mouseButton;
    public final EnumKeyboardAction keyboardKey;



    private SlotActionType(boolean playerMouseHasItemStack, boolean playerInCreativeMode, boolean slotHasItemStack, boolean isShiftPressed, EnumMouseAction mouseButton,
            EnumKeyboardAction keyboardKey) {
        this.playerMouseHasItemStack = playerMouseHasItemStack;
        this.playerInCreativeMode = playerInCreativeMode;
        this.slotHasItemStack = slotHasItemStack;
        this.isShiftPressed = isShiftPressed;
        this.mouseButton = mouseButton;
        this.keyboardKey = keyboardKey;
    }


    /**
     * @param player
     *            Player that caused the action.
     * @param slot
     *            The target slot in the gui overlay.
     * @param shiftPressed
     *            Is the SHIFT key pressed?
     * @param mouseButton
     *            Type of mouse click.
     * @return
     */
    public static SlotActionType create(EntityPlayer player, Slot slot, boolean shiftPressed, EnumMouseAction mouseButton)
    {
        if (player != null && slot != null) { return new SlotActionType(!player.inventory.getItemStack().isEmpty(), player.capabilities.isCreativeMode, slot.getHasStack(), shiftPressed, mouseButton,
                EnumKeyboardAction.INVALID); }

        return SlotActionType.EMPTY;
    }


    /**
     * @param player
     *            Player that caused the action.
     * @param slot
     *            The target slot in the gui overlay.
     * @param keyboardKey
     *            Type of keyboard key press.
     * @return
     */
    public static SlotActionType create(EntityPlayer player, Slot slot, EnumKeyboardAction keyboardKey)
    {
        if (player != null && slot != null) { return new SlotActionType(!player.inventory.getItemStack().isEmpty(), player.capabilities.isCreativeMode, slot.getHasStack(), false,
                EnumMouseAction.INVALID, keyboardKey); }

        return SlotActionType.EMPTY;
    }



    public boolean isValid()
    {
        return this.mouseButton != EnumMouseAction.INVALID || this.keyboardKey != EnumKeyboardAction.INVALID;
    }



    @Override
    public boolean equals(Object other)
    {
        if (other == null) { return false; }
        if (this.getClass() != other.getClass()) { return false; }

        final SlotActionType otherConverted = (SlotActionType) other;
        if (this.isShiftPressed != otherConverted.isShiftPressed) { return false; }
        if (this.playerInCreativeMode != otherConverted.playerInCreativeMode) { return false; }
        if (this.playerMouseHasItemStack != otherConverted.playerMouseHasItemStack) { return false; }
        if (this.slotHasItemStack != otherConverted.slotHasItemStack) { return false; }
        if (this.mouseButton != otherConverted.mouseButton) { return false; }
        if (this.keyboardKey != otherConverted.keyboardKey) { return false; }

        return true;
    }


    @Override
    public int hashCode()
    {
        int hashCode = 17;

        hashCode = hashCode * 59 + Boolean.valueOf(this.isShiftPressed).hashCode();
        hashCode = hashCode * 59 + Boolean.valueOf(this.playerInCreativeMode).hashCode();
        hashCode = hashCode * 59 + Boolean.valueOf(this.playerMouseHasItemStack).hashCode();
        hashCode = hashCode * 59 + Boolean.valueOf(this.slotHasItemStack).hashCode();
        hashCode = hashCode * 59 + this.mouseButton.buttonValue;
        hashCode = hashCode * 59 + this.keyboardKey.keySerializingValue;

        return hashCode;
    }



    @Override
    public String toString()
    {
        final StringBuilder r = new StringBuilder();

        r.append("SlotActionType [Hash code: " + this.hashCode());
        r.append(" - playerMouseHasItemStack: " + this.playerMouseHasItemStack);
        r.append(", playerInCreativeMode: " + this.playerInCreativeMode);
        r.append(", slotHasItemStack: " + this.slotHasItemStack);
        r.append(", isShiftPressed: " + this.isShiftPressed);
        r.append(", mouseButton: " + this.mouseButton);
        r.append(", keyboardKey: " + this.keyboardKey);
        r.append("]");

        return r.toString();
    }


}
