package sidben.visiblearmorslots.handler;

import org.lwjgl.input.Mouse;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.GuiScreenEvent.MouseInputEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sidben.visiblearmorslots.client.gui.GuiExtraSlotsOverlay;
import sidben.visiblearmorslots.helper.LogHelper;


/**
 * Delegates GuiEvents to the GuiExtraSlotsOverlay.
 *
 */
public class EventDelegatorGuiOverlay
{

    private static GuiExtraSlotsOverlay _guiOverlay;



    GuiExtraSlotsOverlay getGuiOverlay()
    {
        if (_guiOverlay == null) {
            _guiOverlay = new GuiExtraSlotsOverlay();
        }
        return _guiOverlay;
    }



    /**
     * Returns if the current GUI should have the extra slots visible.
     */
    boolean shouldDisplayGuiOverlay(GuiScreen gui)
    {
        if (gui == null) { return false; }
        if (!(gui instanceof GuiContainer)) { return false; }

        final InfoGuiOverlayDisplayParams displayParams = getDisplayParamsForGui(gui);
        return displayParams.getShouldDisplay();
    }



    /**
     * Returns the display parameters for the given GUI.
     */
    InfoGuiOverlayDisplayParams getDisplayParamsForGui(GuiScreen gui)
    {
        if (gui instanceof GuiInventory) { return new InfoGuiOverlayDisplayParams(((gui.width - 176) / 2) - 30, ((gui.height - 166) / 2), true); }
        if (gui instanceof GuiChest) { return new InfoGuiOverlayDisplayParams(25, 10, true); }

        // TODO
        // OBS: cache on array, index is the string with classname

        return InfoGuiOverlayDisplayParams.EMPTY;
    }



    /*
     * boolean isGuiBlacklisted(GuiScreen container)
     * {
     * return false; // TODO
     * }
     */



    // -----------------------------------------------------------
    // Event handlers
    // -----------------------------------------------------------

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onInitGuiEventPost(InitGuiEvent.Post event)
    {
        if (!this.shouldDisplayGuiOverlay(event.getGui())) { return; }
        LogHelper.trace("EventDelegatorGuiOverlay.onInitGuiEventPost(%s)", event.getGui());

        final GuiScreen gui = event.getGui();
        final InfoGuiOverlayDisplayParams displayParams = getDisplayParamsForGui(gui);

        this.getGuiOverlay().setWorldAndResolution(gui.mc, gui.width, gui.height);
        this.getGuiOverlay().guiLeft = displayParams.getGuiLeft();
        this.getGuiOverlay().guiTop = displayParams.getGuiTop();
        this.getGuiOverlay().refreshExtraSlotsInfo(gui.mc.player.inventory);
    }



    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onBackgroundDrawScreenEvent(BackgroundDrawnEvent event)
    {
        if (!this.shouldDisplayGuiOverlay(event.getGui())) { return; }

        // LogHelper.trace("EventDelegatorGuiOverlay.onBackgroundDrawScreenEvent(%s)", event.getGui());
        this.getGuiOverlay().drawBackground();
    }



    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onDrawScreenEvent(DrawScreenEvent event)
    {
        if (!this.shouldDisplayGuiOverlay(event.getGui())) { return; }

        // LogHelper.trace("EventDelegatorGuiOverlay.onDrawScreenEvent(%s)", event.getGui());
        this.getGuiOverlay().drawScreen(event.getMouseX(), event.getMouseY(), event.getRenderPartialTicks());
    }



    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onMouseInputEvent(MouseInputEvent.Pre event)
    {
        // Only accepts clicks - 0 = left, 1 = right, 2 = middle
        if (Mouse.getEventButton() < 0) { return; }
        if (!this.shouldDisplayGuiOverlay(event.getGui())) { return; }


        final boolean shouldCancelEvent = this.getGuiOverlay().handleMouseInput();

        if (shouldCancelEvent) {
            // Prevents clicks on the gui overlay dropping items on the world
            event.setCanceled(true);
        }

    }



}
