package sidben.visiblearmorslots.helper;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import sidben.visiblearmorslots.handler.ConfigurationHandler;
import sidben.visiblearmorslots.reference.Reference;


public class ExtraSlotsHelperClient extends ExtraSlotsHelperCommon
{

    private static final ResourceLocation GUI_EXTRA_SLOTS = new ResourceLocation(Reference.ModID + ":textures/gui/extra-slots.png");



    @Override
    public void drawExtraSlotsOnGui(GuiContainer originalGui, int xSize, int ySize)
    {
        // NOTES:
        // originalGui.width and originalGui.height are the window size.
        // xSize and ySize are the GUI size.


        // Check if this GUI need extra Y offset
        Integer yOffset = 0;
        if (originalGui instanceof IVerticalOffset) {
            yOffset = ((IVerticalOffset) originalGui).getYOffset();
        }

        // Draws the extra armor slots
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        originalGui.mc.getTextureManager().bindTexture(GUI_EXTRA_SLOTS);

        final int startX = ((originalGui.width - xSize) / 2) - 4 + ConfigurationHandler.GUI_SLOTS_XOFFSET;
        final int startY = ((originalGui.height - ySize) / 2) + 62 + yOffset;
        originalGui.drawTexturedModalRect(startX, startY, 0, 62, 24, 100);

    }


    @Override
    public void addExtraSlotsToContainer(Container originalContainer, IInventory playerInventory)
    {
        super.addExtraSlotsToContainer(originalContainer, playerInventory);
    }

}