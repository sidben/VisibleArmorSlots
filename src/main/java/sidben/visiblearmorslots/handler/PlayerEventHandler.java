package sidben.visiblearmorslots.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent;
import net.minecraftforge.event.entity.player.PlayerOpenContainerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.helper.LogHelper;



public class PlayerEventHandler
{


    @SubscribeEvent
    public void onPlayerOpenContainerEvent(PlayerOpenContainerEvent event)
    {
        // OBS: Runs server-side
        // NOTE - Gets called constantly while the container is open


        final Container openedContainer = event.getEntityPlayer().openContainer;
        final IInventory playerInventory = event.getEntityPlayer().inventory;


        if (ModVisibleArmorSlots.extraSlotsHelper.shouldAddExtraSlotsToContainer(openedContainer)) {
            LogHelper.info("Adding extra slots server-side.");
            ModVisibleArmorSlots.extraSlotsHelper.addExtraSlotsToContainer(openedContainer, playerInventory);
        }

    }



    @SubscribeEvent
    public void onGuiOpenEvent(GuiOpenEvent event)
    {
        // OBS: Runs client-side

        if (!(event.getGui() instanceof GuiContainer)) {
            return;
        }
        final GuiContainer targetGui = (GuiContainer) event.getGui();


        // Adds the extra slots
        final Minecraft mc = Minecraft.getMinecraft();
        final IInventory playerInventory = mc.thePlayer.inventory;
        final Container openedContainer = targetGui.inventorySlots;


        if (ModVisibleArmorSlots.extraSlotsHelper.shouldAddExtraSlotsToContainer(openedContainer)) {
            LogHelper.info("Adding extra slots client-side.");
            ModVisibleArmorSlots.extraSlotsHelper.addExtraSlotsToContainer(openedContainer, playerInventory);
        }

    }


    @SubscribeEvent
    public void onDrawScreenEvent(BackgroundDrawnEvent event)
    {
        if (!(event.getGui() instanceof GuiContainer)) {
            return;      // Ignores non-container GUIs
        }
        final GuiContainer targetGui = (GuiContainer) event.getGui();

        if (ModVisibleArmorSlots.extraSlotsHelper.shouldDrawExtraSlotsOnGui(targetGui)) {
            ModVisibleArmorSlots.extraSlotsHelper.drawExtraSlotsOnGui(targetGui);
        }

    }



}