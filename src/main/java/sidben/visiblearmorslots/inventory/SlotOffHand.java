package sidben.visiblearmorslots.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



public class SlotOffHand extends Slot {

    public SlotOffHand(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}


    @SideOnly(Side.CLIENT)
    public String getSlotTexture()
    {
        return "minecraft:items/empty_armor_slot_shield";
    }

}