package sidben.visiblearmorslots.inventory;

import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.helper.ExtraSlotsHelper;
import sidben.visiblearmorslots.helper.LogHelper;
import java.util.List;
import java.util.Random;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class ContainerEnchantmentCustom extends ContainerEnchantment {


    @SideOnly(Side.CLIENT)
    public ContainerEnchantmentCustom(InventoryPlayer playerInv, World worldIn)
    {
    	this(playerInv, worldIn, BlockPos.ORIGIN);
    }
    
    
	public ContainerEnchantmentCustom(InventoryPlayer playerInv, World worldIn, BlockPos pos) {
		super(playerInv, worldIn, pos);

        // Adds the extra slots
    	ExtraSlotsHelper.addExtraSlotsToContainer(this, playerInv);
	}
	
	
}