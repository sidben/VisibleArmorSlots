package sidben.visiblearmorslots.client.gui;

import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.handler.ConfigurationHandler;
import sidben.visiblearmorslots.helper.IVerticalOffset;
import sidben.visiblearmorslots.inventory.ContainerBeaconCustom;



@SideOnly(Side.CLIENT)
public class GuiBeaconCustom extends GuiBeacon implements IVerticalOffset
{


    public GuiBeaconCustom(InventoryPlayer playerInv, IInventory tileBeaconIn) {
        super(playerInv, tileBeaconIn);

        final ContainerBeaconCustom customContainer = new ContainerBeaconCustom(playerInv, tileBeaconIn);
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
        return ConfigurationHandler.BEACON_YOFFSET;
    }


}