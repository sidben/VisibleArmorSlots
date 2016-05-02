package sidben.visiblearmorslots.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.handler.ConfigurationHandler;
import sidben.visiblearmorslots.helper.IVerticalOffset;


public class ContainerHopperCustom extends ContainerHopper implements IVerticalOffset
{


    public ContainerHopperCustom(InventoryPlayer playerInventory, IInventory hopperInventoryIn, EntityPlayer player) {
        super(playerInventory, hopperInventoryIn, player);

        // Adds the extra slots
        ModVisibleArmorSlots.extraSlotsHelper.addExtraSlotsToContainer(this, playerInventory);
    }


    @Override
    public int getYOffset()
    {
        return ConfigurationHandler.HOPPER_YOFFSET;
    }


}