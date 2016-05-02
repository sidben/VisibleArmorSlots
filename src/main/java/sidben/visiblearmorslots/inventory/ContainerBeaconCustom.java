package sidben.visiblearmorslots.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.IInventory;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.handler.ConfigurationHandler;
import sidben.visiblearmorslots.helper.IVerticalOffset;


public class ContainerBeaconCustom extends ContainerBeacon implements IVerticalOffset
{


    public ContainerBeaconCustom(InventoryPlayer playerInventory, IInventory tileBeaconIn) {
        super(playerInventory, tileBeaconIn);

        // Adds the extra slots
        ModVisibleArmorSlots.extraSlotsHelper.addExtraSlotsToContainer(this, playerInventory);
    }


    @Override
    public int getYOffset()
    {
        return ConfigurationHandler.BEACON_YOFFSET;
    }


}