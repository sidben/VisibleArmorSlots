package sidben.visiblearmorslots.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sidben.visiblearmorslots.helper.ExtraSlotsHelper;
import sidben.visiblearmorslots.inventory.ContainerBeaconCustom;
import sidben.visiblearmorslots.inventory.ContainerHopperCustom;



@SideOnly(Side.CLIENT)
public class GuiBeaconCustom extends GuiBeacon
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
        ExtraSlotsHelper.drawExtraSlotsOnGui(this, this.xSize, this.ySize);

        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    }


}