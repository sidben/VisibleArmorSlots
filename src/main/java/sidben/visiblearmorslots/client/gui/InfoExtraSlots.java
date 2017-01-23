package sidben.visiblearmorslots.client.gui;


/**
 * Holds information about the type, position and indexes of the armor and off-hand slots.
 *
 */
public class InfoExtraSlots
{

    public enum EnumSlotType {
        ARMOR,
        OFF_HAND;
    }


    private final int          _playerInventorySlotIndex;
    private final int          _playerContainerSlotIndex;
    private final int          _xPos;
    private final int          _yPos;
    private final EnumSlotType _type;



    /**
     * Position where the slot will be draw, related to the gui.
     */
    public int getX()
    {
        return this._xPos;
    }

    /**
     * Position where the slot will be draw, related to the gui.
     */
    public int getY()
    {
        return this._yPos;
    }

    /**
     * Index where the ItemStack will be found in the player inventory.
     *
     * @see {@link net.minecraft.entity.player.EntityPlayer#inventory EntityPlayer.inventory}.
     */
    public int getInventorySlotIndex()
    {
        return this._playerInventorySlotIndex;
    }

    /**
     * Index where the slot that holds this item will be found in the player container.
     *
     * @see {@link net.minecraft.entity.player.EntityPlayer#inventoryContainer EntityPlayer.inventoryContainer}.
     */
    public int getContainerSlotIndex()
    {
        return this._playerContainerSlotIndex;
    }

    /**
     * Slot type.
     */
    public EnumSlotType getSlotType()
    {
        return this._type;
    }



    public InfoExtraSlots(EnumSlotType type, int x, int y, int invIndex, int containerIndex) {
        this._playerContainerSlotIndex = containerIndex;
        this._playerInventorySlotIndex = invIndex;
        this._type = type;
        this._xPos = x;
        this._yPos = y;
    }



}
