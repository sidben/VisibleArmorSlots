package sidben.visiblearmorslots.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.handler.ConfigurationHandler;
import sidben.visiblearmorslots.helper.IVerticalOffset;
import sidben.visiblearmorslots.inventory.ContainerHopperCustom;



@SideOnly(Side.CLIENT)
public class GuiHopperCustom extends GuiHopper implements IVerticalOffset
{


    public GuiHopperCustom(InventoryPlayer playerInv, IInventory hopperInv) {
        super(playerInv, hopperInv);

        final ContainerHopperCustom customContainer = new ContainerHopperCustom(playerInv, hopperInv, Minecraft.getMinecraft().thePlayer);
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
        return ConfigurationHandler.HOPPER_YOFFSET;
    }


}