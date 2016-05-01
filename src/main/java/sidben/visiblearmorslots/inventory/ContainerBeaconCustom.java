package sidben.visiblearmorslots.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.IInventory;
import sidben.visiblearmorslots.ModVisibleArmorSlots;


public class ContainerBeaconCustom extends ContainerBeacon
{


    public ContainerBeaconCustom(InventoryPlayer playerInventory, IInventory tileBeaconIn) {
        super(playerInventory, tileBeaconIn);

        // Adds the extra slots
        ModVisibleArmorSlots.extraSlotsHelper.addExtraSlotsToContainer(this, playerInventory);
    }


}