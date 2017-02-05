package sidben.visiblearmorslots.inventory;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class SlotArmor extends Slot
{


    private static final EntityEquipmentSlot[] armorSloyArray = new EntityEquipmentSlot[] { EntityEquipmentSlot.FEET, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.HEAD };

    private final EntityPlayer                 thePlayer;
    private int                                slotTypeIndex  = 0;



    public SlotArmor(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);

        thePlayer = ((InventoryPlayer) inventoryIn).player;
        slotTypeIndex = index - 36;
        slotTypeIndex = Math.min(Math.max(slotTypeIndex, 0), armorSloyArray.length - 1);
    }


    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1
     * in the case of armor slots)
     */
    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }


    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    @Override
    public boolean isItemValid(ItemStack stack)
    {
        if (stack == null) {
            return false;
        } else {
            return stack.getItem().isValidArmor(stack, armorSloyArray[slotTypeIndex], thePlayer);
        }
    }


    @Override
    @SideOnly(Side.CLIENT)
    public String getSlotTexture()
    {
        return ItemArmor.EMPTY_SLOT_NAMES[slotTypeIndex];
    }



    @Override
    public boolean canTakeStack(EntityPlayer player)
    {
        final ItemStack itemstack = this.getStack();
        return !itemstack.isEmpty() && !player.isCreative() && EnchantmentHelper.hasBindingCurse(itemstack) ? false : super.canTakeStack(player);
    }


}
