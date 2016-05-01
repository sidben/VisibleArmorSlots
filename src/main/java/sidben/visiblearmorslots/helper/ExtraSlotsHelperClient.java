package sidben.visiblearmorslots.helper;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import sidben.visiblearmorslots.client.gui.GuiBeaconCustom;
import sidben.visiblearmorslots.client.gui.GuiHopperCustom;
import sidben.visiblearmorslots.proxy.ClientProxy;
import sidben.visiblearmorslots.reference.Reference;


public class ExtraSlotsHelperClient extends ExtraSlotsHelperCommon
{

    private static final ResourceLocation GUI_EXTRA_SLOTS = new ResourceLocation(Reference.ModID + ":textures/gui/extra-slots.png");



    public ExtraSlotsHelperClient() {
        super();

        guiYOffsetMap.put(GuiBeaconCustom.class, BEACON_YOFFSET);
        guiYOffsetMap.put(GuiHopperCustom.class, HOPPER_YOFFSET);
    }



    @Override
    public void drawExtraSlotsOnGui(GuiContainer originalGui, int xSize, int ySize)
    {
        Integer yOffset = 0;

        // Check if this GUI need extra Y offset
        yOffset = guiYOffsetMap.containsKey(originalGui.getClass()) ? guiYOffsetMap.get(originalGui.getClass()) : 0;


        // Draws the extra armor slots
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        originalGui.mc.getTextureManager().bindTexture(GUI_EXTRA_SLOTS);

        final int startX = ((originalGui.width - xSize) / 2) - 4 + GUI_SLOTS_XOFFSET;
        final int startY = ((originalGui.height - ySize) / 2) + yOffset;
        originalGui.drawTexturedModalRect(startX, startY, 0, 0, xSize, 162);

    }


    @Override
    public void addExtraSlotsToContainer(Container originalContainer, IInventory playerInventory)
    {
        super.addExtraSlotsToContainer(originalContainer, playerInventory);
    }

}
