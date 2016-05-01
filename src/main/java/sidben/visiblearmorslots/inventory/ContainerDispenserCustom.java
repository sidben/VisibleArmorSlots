package sidben.visiblearmorslots.inventory;

import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import sidben.visiblearmorslots.ModVisibleArmorSlots;


public class ContainerDispenserCustom extends ContainerDispenser
{


    public ContainerDispenserCustom(IInventory playerInventory, IInventory dispenserInventoryIn) {
        super(playerInventory, dispenserInventoryIn);

        // Adds the extra slots
        ModVisibleArmorSlots.extraSlotsHelper.addExtraSlotsToContainer(this, playerInventory);
    }


}