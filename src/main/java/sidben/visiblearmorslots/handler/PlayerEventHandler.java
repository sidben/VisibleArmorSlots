package sidben.visiblearmorslots.handler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.helper.VanillaGuiRedirect;


public class PlayerEventHandler
{

    private static final VanillaGuiRedirect[] guiRedirectArray = new VanillaGuiRedirect[] { 
            new VanillaGuiRedirect(Blocks.enchanting_table, ModVisibleArmorSlots.GUI_ENCHANTMENT_TABLE),
            new VanillaGuiRedirect(Blocks.anvil, ModVisibleArmorSlots.GUI_ANVIL), 
            new VanillaGuiRedirect(Blocks.chest, ModVisibleArmorSlots.GUI_CHEST),
            new VanillaGuiRedirect(Blocks.trapped_chest, ModVisibleArmorSlots.GUI_CHEST), 
            new VanillaGuiRedirect(Blocks.ender_chest, ModVisibleArmorSlots.GUI_ENDER_CHEST),
            new VanillaGuiRedirect(Blocks.furnace, ModVisibleArmorSlots.GUI_FURNACE), 
            new VanillaGuiRedirect(Blocks.crafting_table, ModVisibleArmorSlots.GUI_CRAFTING_TABLE),
            new VanillaGuiRedirect(Blocks.dispenser, ModVisibleArmorSlots.GUI_DISPENSER), 
            new VanillaGuiRedirect(Blocks.dropper, ModVisibleArmorSlots.GUI_DISPENSER),
            new VanillaGuiRedirect(Blocks.hopper, ModVisibleArmorSlots.GUI_HOPPER), 
            new VanillaGuiRedirect(Blocks.brewing_stand, ModVisibleArmorSlots.GUI_BREWING_STAND),
            new VanillaGuiRedirect(Blocks.beacon, ModVisibleArmorSlots.GUI_BEACON) };



    @SubscribeEvent
    public void onRightClickBlock(RightClickBlock event)
    {
        // 1.9 OBS - this event is called twice, for main and off hand. Trying to open the GUI
        // twice don't work, so to make it easy I only check the main hand.


        /*
         * final String blockName = targetBlock.getBlock().getUnlocalizedName();
         * LogHelper.info("onRightClickBlock() " + event.getHand() + " on " + (event.getWorld().isRemote ? "Client" : "Server") + " -> " + blockName);
         */

        
        final IBlockState targetBlock = event.getWorld().getBlockState(event.getPos());
        
        // NOTE: Keep an eye on PlayerInteractionManager.processRightClickBlock. There is a check for the held item
        // doesSneakBypassUse() method, but from what I can see from the code, every item returns false. 
        if (!event.getEntityPlayer().isSneaking()) {
            
            // Check blocks that should have the GUI redirected
            for (final VanillaGuiRedirect item : guiRedirectArray) {
    
                if (item.compareBlock(targetBlock.getBlock())) {
                    // First deny the vanilla GUI
                    event.setCanceled(true);
    
                    
                    // Display the custom GUI (only on server)
                    if (!event.getWorld().isRemote && event.getHand().equals(EnumHand.MAIN_HAND)) {
                        event.getEntityPlayer().openGui(ModVisibleArmorSlots.instance, item.getRedirectGuiId(), event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
                    }
    
                    break;
                }
    
            }

        }
        
        


    }


}