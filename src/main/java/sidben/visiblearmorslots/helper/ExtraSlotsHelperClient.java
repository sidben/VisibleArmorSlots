package sidben.visiblearmorslots.helper;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import sidben.visiblearmorslots.handler.ConfigurationHandler;
import sidben.visiblearmorslots.reference.Reference;


public class ExtraSlotsHelperClient extends ExtraSlotsHelperCommon
{

    private static final ResourceLocation GUI_EXTRA_SLOTS         = new ResourceLocation(Reference.ModID + ":textures/gui/extra-slots.png");
    private static final int              GUI_EXTRA_SLOTS_START_X = 0;
    private static final int              GUI_EXTRA_SLOTS_START_Y = 62;
    private static final int              GUI_EXTRA_SLOTS_WIDTH   = 24;
    private static final int              GUI_EXTRA_SLOTS_HEIGHT  = 100;



    @Override
    public void drawExtraSlotsOnGui(GuiContainer originalGui)
    {
        // NOTES:
        // - originalGui.width and originalGui.height are the window size.
        // - xSize and ySize are the GUI size in pixels.


        int xSize = originalGui.getXSize();
        int ySize = originalGui.getYSize();


        // HOTFIX: Chest containers have their height wrong by 2 pixels
        if (originalGui instanceof GuiChest) {
            ySize -= 2;
        }

        // Check if this GUI need extra offset
        Integer xOffset = 0;
        final Integer yOffset = ySize - GUI_EXTRA_SLOTS_HEIGHT - 4;

        if (ConfigurationHandler.extraSlotsSide.equals(ConfigurationHandler.POSITION_RIGHT)) {
            xOffset = xSize + ConfigurationHandler.extraSlotsMargin;
        } else {
            xOffset = (GUI_EXTRA_SLOTS_WIDTH * -1) - ConfigurationHandler.extraSlotsMargin;
        }



        // Draws the extra armor slots
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        originalGui.mc.getTextureManager().bindTexture(GUI_EXTRA_SLOTS);


        final int startX = ((originalGui.width - xSize) / 2) + xOffset;
        final int startY = ((originalGui.height - ySize) / 2) + yOffset;

        originalGui.drawTexturedModalRect(startX, startY, GUI_EXTRA_SLOTS_START_X, GUI_EXTRA_SLOTS_START_Y, GUI_EXTRA_SLOTS_WIDTH, GUI_EXTRA_SLOTS_HEIGHT);
    }


    @Override
    public void addExtraSlotsToContainer(Container originalContainer, IInventory playerInventory)
    {
        super.addExtraSlotsToContainer(originalContainer, playerInventory);
    }


    @Override
    public boolean shouldAddExtraSlotsToContainer(Container container)
    {
        // Special case for creative container (client-side only).
        //
        // The class is [GuiContainerCreative.ContainerCreative], but that class
        // is not public so I have to work around it.
        final Class containerWrapperClass = container.getClass().getEnclosingClass();
        if (containerWrapperClass != null && containerWrapperClass.equals(GuiContainerCreative.class)) {
            return false;
        }

        return super.shouldAddExtraSlotsToContainer(container);
    }


    @Override
    public boolean shouldDrawExtraSlotsOnGui(GuiContainer gui)
    {
        // Check if it's a valid container to get extra slots
        if (gui == null) return false;
        if (gui instanceof GuiInventory) return false;
        if (gui instanceof GuiContainerCreative) return false;
        
        return super.shouldDrawExtraSlotsOnGui(gui);
    }

}