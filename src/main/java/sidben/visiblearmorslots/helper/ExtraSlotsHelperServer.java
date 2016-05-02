package sidben.visiblearmorslots.helper;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;


public class ExtraSlotsHelperServer extends ExtraSlotsHelperCommon
{

    @Override
    public void drawExtraSlotsOnGui(GuiContainer originalGui, int xSize, int ySize)
    {
    }


    @Override
    public void addExtraSlotsToContainer(Container originalContainer, IInventory playerInventory)
    {
        super.addExtraSlotsToContainer(originalContainer, playerInventory);
    }

}
