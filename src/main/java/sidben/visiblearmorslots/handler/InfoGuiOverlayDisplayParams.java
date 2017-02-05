package sidben.visiblearmorslots.handler;

import javax.annotation.concurrent.Immutable;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import sidben.visiblearmorslots.client.gui.GuiExtraSlotsOverlay;
import sidben.visiblearmorslots.config.ConfigurationHandler;
import sidben.visiblearmorslots.helper.LogHelper;


@Immutable
public class InfoGuiOverlayDisplayParams
{

    public static InfoGuiOverlayDisplayParams EMPTY = new InfoGuiOverlayDisplayParams(0, 0, false);



    private final int                         _guiLeft;
    private final int                         _guiTop;
    private final boolean                     _shouldDisplay;


    public int getGuiLeft()
    {
        return this._guiLeft;
    }

    public int getGuiTop()
    {
        return this._guiTop;
    }

    public boolean getShouldDisplay()
    {
        return this._shouldDisplay;
    }



    public InfoGuiOverlayDisplayParams(int x, int y, boolean shouldDisplay) {
        this._guiLeft = x;
        this._guiTop = y;
        this._shouldDisplay = shouldDisplay;
    }



    public static InfoGuiOverlayDisplayParams create(GuiContainer gui, String guiClassName)
    {
        if (gui == null) { return InfoGuiOverlayDisplayParams.EMPTY; }


        // Check for blacklisted gui's
        if (isBlacklisted(gui)) { return InfoGuiOverlayDisplayParams.EMPTY; }


        int overlayX = 0;
        int overlayY = 0;

        if (ConfigurationHandler.extraSlotsSide.equals(ConfigurationHandler.POSITION_LEFT)) {
            overlayX = gui.getGuiLeft() - GuiExtraSlotsOverlay.GUI_WIDTH - ConfigurationHandler.extraSlotsMargin;
        } else if (ConfigurationHandler.extraSlotsSide.equals(ConfigurationHandler.POSITION_RIGHT)) {
            overlayX = gui.getGuiLeft() + gui.getXSize() + ConfigurationHandler.extraSlotsMargin;
        }
        overlayY = gui.getGuiTop() + gui.getYSize() - GuiExtraSlotsOverlay.GUI_HEIGHT - 4;


        // HOTFIX: Chest containers have their height (YSize) wrong
        if (gui instanceof GuiChest || gui instanceof GuiShulkerBox) {
            overlayY -= 1;
        }


        final InfoGuiOverlayDisplayParams displayParams = new InfoGuiOverlayDisplayParams(overlayX, overlayY, true);
        return displayParams;
    }



    protected final static boolean isBlacklisted(GuiContainer gui)
    {
        if (ConfigurationHandler.blacklistedModPackages.length > 0) {
            final String className = gui.getClass().getName();

            for (final String blacklisted : ConfigurationHandler.blacklistedModPackages) {
                if (className.startsWith(blacklisted + ".")) {
                    LogHelper.trace("  This gui is blacklisted: [%s]", className);
                    return true;
                }
            }
        }

        return false;
    }



    @Override
    public String toString()
    {
        return String.format("x: %d, y: %d, visible: %s", this.getGuiLeft(), this.getGuiTop(), this.getShouldDisplay());
    }
}
