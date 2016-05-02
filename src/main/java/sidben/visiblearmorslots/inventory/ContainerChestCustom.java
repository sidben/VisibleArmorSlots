package sidben.visiblearmorslots.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.handler.ConfigurationHandler;
import sidben.visiblearmorslots.helper.IExtraOffset;


public class ContainerChestCustom extends ContainerChest implements IExtraOffset
{

    private final int _yOffset;


    public ContainerChestCustom(IInventory playerInventory, IInventory chestInventory, EntityPlayer player) {
        super(playerInventory, chestInventory, player);

        _yOffset = (chestInventory.getSizeInventory() > 27) ? ConfigurationHandler.CHEST_DOUBLE_YOFFSET : ConfigurationHandler.CHEST_SINGLE_YOFFSET;

        // Adds the extra slots
        ModVisibleArmorSlots.extraSlotsHelper.addExtraSlotsToContainer(this, playerInventory);
    }


    @Override
    public int getXOffset()
    {
        return 0;
    }

    
    @Override
    public int getYOffset()
    {
        return _yOffset;
    }


}