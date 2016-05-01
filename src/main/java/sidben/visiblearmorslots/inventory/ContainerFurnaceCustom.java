package sidben.visiblearmorslots.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import sidben.visiblearmorslots.ModVisibleArmorSlots;


public class ContainerFurnaceCustom extends ContainerFurnace
{


    public ContainerFurnaceCustom(InventoryPlayer playerInventory, IInventory furnaceInventory) {
        super(playerInventory, furnaceInventory);

        // Adds the extra slots
        ModVisibleArmorSlots.extraSlotsHelper.addExtraSlotsToContainer(this, playerInventory);
    }


}