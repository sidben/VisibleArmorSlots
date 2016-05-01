package sidben.visiblearmorslots.inventory;

import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import sidben.visiblearmorslots.helper.ExtraSlotsHelper;


public class ContainerDispenserCustom extends ContainerDispenser
{


    public ContainerDispenserCustom(IInventory playerInventory, IInventory dispenserInventoryIn) {
        super(playerInventory, dispenserInventoryIn);

        // Adds the extra slots
        ExtraSlotsHelper.addExtraSlotsToContainer(this, playerInventory);
    }


}