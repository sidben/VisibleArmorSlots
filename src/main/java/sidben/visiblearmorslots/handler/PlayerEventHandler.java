package sidben.visiblearmorslots.handler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent.Post;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerOpenContainerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.helper.LogHelper;
import sidben.visiblearmorslots.reference.Reference;


// TODO: Test adventure mode
// TODO: ONDEBUG for debug text
// TODO: Fix messed up hotbar on creative inventory GuiContainerCreative


public class PlayerEventHandler
{


    @SubscribeEvent
    public void onPlayerOpenContainerEvent(PlayerOpenContainerEvent event)
    {
        // OBS: Runs server-side
        // NOTE - Gets called constantly while the container is open
        
        
        Container openedContainer = event.getEntityPlayer().openContainer;
        IInventory playerInventory = event.getEntityPlayer().inventory;
        
        
        if (ModVisibleArmorSlots.extraSlotsHelper.shouldAddExtraSlotsToContainer(openedContainer)) {
            LogHelper.info("Adding extra slots server-side.");
            ModVisibleArmorSlots.extraSlotsHelper.addExtraSlotsToContainer(openedContainer, playerInventory); 
        }
        
    }


    
    @SubscribeEvent
    public void onGuiOpenEvent(GuiOpenEvent event)
    {
        // OBS: Runs client-side
        
        
        if (!(event.getGui() instanceof GuiContainer)) return;
        GuiContainer targetGui = (GuiContainer)event.getGui();


        // Adds the extra slots
        Minecraft mc = Minecraft.getMinecraft();
        IInventory playerInventory = mc.thePlayer.inventory;
        Container openedContainer = targetGui.inventorySlots;
        

        if (ModVisibleArmorSlots.extraSlotsHelper.shouldAddExtraSlotsToContainer(openedContainer)) {
            LogHelper.info("Adding extra slots client-side.");
            ModVisibleArmorSlots.extraSlotsHelper.addExtraSlotsToContainer(openedContainer, playerInventory); 
        }
        
    }


    @SubscribeEvent
    public void onDrawScreenEvent(BackgroundDrawnEvent event)
    {
        if (!(event.getGui() instanceof GuiContainer)) return;      // Ignores non-container GUIs
        GuiContainer targetGui = (GuiContainer)event.getGui();

        if (ModVisibleArmorSlots.extraSlotsHelper.shouldDrawExtraSlotsOnGui(targetGui)) {
            ModVisibleArmorSlots.extraSlotsHelper.drawExtraSlotsOnGui(targetGui);
        }
        
    }
    


}