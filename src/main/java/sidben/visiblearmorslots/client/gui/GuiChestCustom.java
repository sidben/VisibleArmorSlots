package sidben.visiblearmorslots.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.handler.ConfigurationHandler;
import sidben.visiblearmorslots.helper.IVerticalOffset;
import sidben.visiblearmorslots.inventory.ContainerChestCustom;



@SideOnly(Side.CLIENT)
public class GuiChestCustom extends GuiChest implements IVerticalOffset
{

    private final int _yOffset;



    public GuiChestCustom(IInventory upperInv, IInventory lowerInv) {
        super(upperInv, lowerInv);

        _yOffset = (lowerInv.getSizeInventory() > 27) ? ConfigurationHandler.CHEST_DOUBLE_YOFFSET : ConfigurationHandler.CHEST_SINGLE_YOFFSET;

        final ContainerChestCustom customContainer = new ContainerChestCustom(upperInv, lowerInv, Minecraft.getMinecraft().thePlayer);
        this.inventorySlots = customContainer;
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        // Draws the extra slots
        ModVisibleArmorSlots.extraSlotsHelper.drawExtraSlotsOnGui(this, this.xSize, this.ySize);

        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    }


    @Override
    public int getYOffset()
    {
        return _yOffset;
    }


}