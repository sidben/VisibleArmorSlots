package sidben.visiblearmorslots.handler;

import java.util.HashMap;
import org.lwjgl.input.Mouse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardInputEvent;
import net.minecraftforge.client.event.GuiScreenEvent.MouseInputEvent;
import net.minecraftforge.client.event.GuiScreenEvent.PotionShiftEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sidben.visiblearmorslots.client.gui.GuiExtraSlotsOverlay;
import sidben.visiblearmorslots.helper.LogHelper;
import sidben.visiblearmorslots.reference.Reference;


/**
 * Delegates GuiEvents to the GuiExtraSlotsOverlay.
 *
 */
public class EventDelegatorGuiOverlay
{

    private static GuiExtraSlotsOverlay                         _guiOverlay;
    private static HashMap<String, InfoGuiOverlayDisplayParams> _cacheDisplayParams = new HashMap<String, InfoGuiOverlayDisplayParams>();



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
        if (Minecraft.getMinecraft().player.isSpectator()) { return false; }

        final InfoGuiOverlayDisplayParams displayParams = getDisplayParamsForGui(gui);
        return displayParams.getShouldDisplay();
    }



    /**
     * Returns the display parameters for the given GUI.
     */
    InfoGuiOverlayDisplayParams getDisplayParamsForGui(GuiScreen gui)
    {
        if (!(gui instanceof GuiContainer)) { return InfoGuiOverlayDisplayParams.EMPTY; }

        final GuiContainer guiContainer = (GuiContainer) gui;
        InfoGuiOverlayDisplayParams displayParams = InfoGuiOverlayDisplayParams.EMPTY;

        // NOTE: inventorySlots should not be null, but we never know for sure...
        int containerSize = 0;
        if (guiContainer.inventorySlots != null && guiContainer.inventorySlots.inventorySlots != null) {
            containerSize = guiContainer.inventorySlots.inventorySlots.size();
        }

        // Allows the same GuiContainer to have different parameters, if the container size
        // is different. Example: Chests and Double Chests.
        //
        // If this causes problems in the future I'll append the gui size to the key.
        String guiClassKey = gui.getClass().getName();
        guiClassKey += "|" + containerSize;



        // LogHelper.trace("* Looking for info with key [%s]", guiClassName);
        if (EventDelegatorGuiOverlay._cacheDisplayParams.containsKey(guiClassKey)) {
            displayParams = _cacheDisplayParams.get(guiClassKey);
            // LogHelper.trace("* Found: [%s]", displayParams);

        } else {
            displayParams = InfoGuiOverlayDisplayParams.create(guiContainer, guiClassKey);
            _cacheDisplayParams.put(guiClassKey, displayParams);
            LogHelper.trace("  Cached display parameters for [%s], key [%s], value [%s]", gui, guiClassKey, displayParams);
        }


        return displayParams;
    }



    // -----------------------------------------------------------
    // Event handlers
    // -----------------------------------------------------------

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onInitGuiEvent(InitGuiEvent.Post event)
    {
        if (!this.shouldDisplayGuiOverlay(event.getGui())) { return; }
        LogHelper.trace("EventDelegatorGuiOverlay.onInitGuiEvent.Post(%s)", event.getGui());

        final GuiScreen gui = event.getGui();
        final InfoGuiOverlayDisplayParams displayParams = getDisplayParamsForGui(gui);

        this.getGuiOverlay().setWorldAndResolution(gui.mc, gui.width, gui.height);
        this.getGuiOverlay().guiLeft = displayParams.getGuiLeft();
        this.getGuiOverlay().guiTop = displayParams.getGuiTop();
        this.getGuiOverlay().refreshExtraSlotsInfo(gui.mc.player.inventory);
    }



    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onBackgroundDrawEvent(BackgroundDrawnEvent event)
    {
        if (!this.shouldDisplayGuiOverlay(event.getGui())) { return; }

        this.getGuiOverlay().drawBackground();
        this.getGuiOverlay().drawScreen(event.getMouseX(), event.getMouseY());
    }



    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onDrawScreenEventPost(DrawScreenEvent.Post event)
    {
        if (!this.shouldDisplayGuiOverlay(event.getGui())) { return; }

        this.getGuiOverlay().drawForeground(event.getMouseX(), event.getMouseY());
    }



    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onPotionShiftEvent(PotionShiftEvent event)
    {
        if (!this.shouldDisplayGuiOverlay(event.getGui())) { return; }

        // TODO: handle potion pushing player inventory to the side
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


    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onKeyboardInputEvent(KeyboardInputEvent.Post event)
    {
        // TODO: for future use (manual gui overlay positioning)
    }



    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equalsIgnoreCase(Reference.ModID)) {
            // Refresh the display parameters when the config changes
            _cacheDisplayParams = new HashMap<String, InfoGuiOverlayDisplayParams>();
        }
    }



}
