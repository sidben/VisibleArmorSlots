package sidben.visiblearmorslots.handler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.village.MerchantRecipeList;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.client.gui.GuiEnchantmentCustom;
import sidben.visiblearmorslots.helper.LogHelper;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class PlayerEventHandler
{


    @SubscribeEvent
    public void onRightClickBlock(RightClickBlock event)
    {
    	// 1.9 OBS - this event is called twice, for main and off hand. Trying to open the GUI
    	// twice don't work, so to make it easy I only check the main hand.
    
    	
    	/*
    	LogHelper.info("    " + event.getHand());
    	LogHelper.info("    " + event.getPos());
    	LogHelper.info("    " + event.getUseBlock());
    	LogHelper.info("    " + event.getUseItem());
    	LogHelper.info("    " + event.getClass());
    	LogHelper.info("    " + event.getWorld().getBlockState(event.getPos()));
    	*/

    	IBlockState targetBlock = event.getWorld().getBlockState(event.getPos());
    	String blockName = targetBlock.getBlock().getUnlocalizedName();

    	LogHelper.info("onRightClickBlock() " + event.getHand() + " on " + (event.getWorld().isRemote ? "Client" : "Server") + " -> " + blockName);
    	


    	// --- Enchantment Table --- //
    	if (targetBlock.getBlock().equals(Blocks.enchanting_table)) {

    		// First deny the vanilla GUI
            event.setCanceled(true);

            // Display the custom GUI
	    	if (!event.getWorld().isRemote && event.getHand().equals(EnumHand.MAIN_HAND))
	    	{
            	event.getEntityPlayer().openGui(ModVisibleArmorSlots.instance, ModVisibleArmorSlots.GUI_ENCHANTMENT_TABLE, event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
	    	}

    	}
    	
    	
    }
    
    
    
    
    
    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event)
    {
    	LogHelper.info("onGuiOpen()");
    	
    	if (event.getGui() == null) {
        	LogHelper.info("    NULL (closes window)");
    	} else {
        	LogHelper.info("    " + event.getGui());
    	}
    	
    	if (event.getGui() instanceof GuiEnchantment && !(event.getGui() instanceof GuiEnchantmentCustom)) {
    		// LogHelper.info("    Nullifying vanilla enchant screen");
    		// event.setCanceled(true);
    		// event.setGui(new GuiEnchantmentCustom());
    	}
    }
    
}
