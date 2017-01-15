package sidben.visiblearmorslots.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.helper.LogHelper;


public class GuiEventHandler
{


    @SubscribeEvent
    @SideOnly(Side.CLIENT)
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
    @SideOnly(Side.CLIENT)
    public void onBackgroundDrawScreenEvent(BackgroundDrawnEvent event)
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
