package sidben.visiblearmorslots.helper;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import sidben.visiblearmorslots.handler.ConfigurationHandler;
import sidben.visiblearmorslots.reference.Reference;


public class ExtraSlotsHelperClient extends ExtraSlotsHelperCommon
{

    private static final ResourceLocation GUI_EXTRA_SLOTS = new ResourceLocation(Reference.ModID + ":textures/gui/extra-slots.png");
    private static final int extraSlotsGuiStartX = 0;
    private static final int extraSlotsGuiStartY = 62;
    private static final int extraSlotsGuiWidth = 24;
    private static final int extraSlotsGuiHeight = 100;

    private static Field xSizeGuiField;
    private static Field ySizeGuiField;
    

    
    private void loadGuiContainerProtectedFields()
    {
        if (xSizeGuiField == null) {
            xSizeGuiField = ReflectionHelper.findField(GuiContainer.class, "xSize");        // TODO: reflected field
            xSizeGuiField.setAccessible(true);
        }

        if (ySizeGuiField == null) {
            ySizeGuiField = ReflectionHelper.findField(GuiContainer.class, "ySize");        // TODO: reflected field
            ySizeGuiField.setAccessible(true);
        }
    }
    
    
    
    
    @Override
    public void drawExtraSlotsOnGui(GuiContainer originalGui)
    {
        // NOTES:
        // originalGui.width and originalGui.height are the window size.
        // xSize and ySize are the GUI size.


        int xSize = 0;
        int ySize = 0;
        loadGuiContainerProtectedFields();
        
        try {
            xSize = xSizeGuiField.getInt(originalGui);
            ySize = ySizeGuiField.getInt(originalGui);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            LogHelper.info("Error loading xSize and ySize from container.");
            LogHelper.error(e);
        }
        

        // HOTFIX: Chest containers have their height wrong by 2 pixels
        if (originalGui instanceof GuiChest) {
            ySize -= 2;
        }

        // Check if this GUI need extra offset
        Integer xOffset = 0;
        Integer yOffset = ySize - extraSlotsGuiHeight -4;
        
        // NOTE: SLOT_SIDES[1] == "RIGHT"
        if (ConfigurationHandler.extraSlotsSide.equals(ConfigurationHandler.SLOT_SIDES[1])) {
            xOffset = xSize + ConfigurationHandler.extraSlotsMargin;
        } else {
            xOffset = (extraSlotsGuiWidth * -1) - ConfigurationHandler.extraSlotsMargin; 
        }

        
        
        // Draws the extra armor slots
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        originalGui.mc.getTextureManager().bindTexture(GUI_EXTRA_SLOTS);


        final int startX = ((originalGui.width - xSize) / 2) + xOffset;
        final int startY = ((originalGui.height - ySize) / 2) + yOffset;
        
        originalGui.drawTexturedModalRect(startX, startY, extraSlotsGuiStartX, extraSlotsGuiStartY, extraSlotsGuiWidth, extraSlotsGuiHeight);
    }


    @Override
    public void addExtraSlotsToContainer(Container originalContainer, IInventory playerInventory)
    {
        super.addExtraSlotsToContainer(originalContainer, playerInventory);
    }
    

    @Override
    public boolean shouldAddExtraSlotsToContainer(Container container) 
    {
        // Special case for creative container (client-side only)
        // if (container instanceof GuiContainerCreative.ContainerCreative) return false;
        
        return super.shouldAddExtraSlotsToContainer(container);
    }
    
    
    @Override
    public boolean shouldDrawExtraSlotsOnGui(GuiContainer gui)
    {
        // Check if it's a valid container to get extra slots
        if (gui == null) return false;
        if (gui instanceof GuiInventory) return false;
        
        return true;
    }

}