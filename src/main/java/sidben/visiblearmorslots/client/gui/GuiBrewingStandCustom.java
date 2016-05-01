package sidben.visiblearmorslots.client.gui;

import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.inventory.ContainerBrewingStandCustom;



@SideOnly(Side.CLIENT)
public class GuiBrewingStandCustom extends GuiBrewingStand
{


    public GuiBrewingStandCustom(InventoryPlayer playerInv, IInventory tileBrewingStandIn) {
        super(playerInv, tileBrewingStandIn);

        final ContainerBrewingStandCustom customContainer = new ContainerBrewingStandCustom(playerInv, tileBrewingStandIn);
        this.inventorySlots = customContainer;
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        // Draws the extra slots
        ModVisibleArmorSlots.extraSlotsHelper.drawExtraSlotsOnGui(this, this.xSize, this.ySize);

        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    }


}