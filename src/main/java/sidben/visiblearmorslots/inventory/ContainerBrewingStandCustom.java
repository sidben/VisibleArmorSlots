package sidben.visiblearmorslots.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.IInventory;
import sidben.visiblearmorslots.ModVisibleArmorSlots;


public class ContainerBrewingStandCustom extends ContainerBrewingStand
{


    public ContainerBrewingStandCustom(InventoryPlayer playerInventory, IInventory tileBrewingStandIn) {
        super(playerInventory, tileBrewingStandIn);

        // Adds the extra slots
        ModVisibleArmorSlots.extraSlotsHelper.addExtraSlotsToContainer(this, playerInventory);
    }


}