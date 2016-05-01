package sidben.visiblearmorslots.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;
import sidben.visiblearmorslots.helper.ExtraSlotsHelper;


public class ContainerHopperCustom extends ContainerHopper
{


    public ContainerHopperCustom(InventoryPlayer playerInventory, IInventory hopperInventoryIn, EntityPlayer player) {
        super(playerInventory, hopperInventoryIn, player);

        // Adds the extra slots
        ExtraSlotsHelper.addExtraSlotsToContainer(this, playerInventory);
    }


}