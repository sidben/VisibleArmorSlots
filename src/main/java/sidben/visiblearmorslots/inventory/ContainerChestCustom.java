package sidben.visiblearmorslots.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import sidben.visiblearmorslots.ModVisibleArmorSlots;


public class ContainerChestCustom extends ContainerChest
{


    public ContainerChestCustom(IInventory playerInventory, IInventory chestInventory, EntityPlayer player) {
        super(playerInventory, chestInventory, player);

        // Adds the extra slots
        ModVisibleArmorSlots.extraSlotsHelper.addExtraSlotsToContainer(this, playerInventory);
    }


}