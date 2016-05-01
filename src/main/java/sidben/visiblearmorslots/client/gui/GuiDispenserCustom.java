package sidben.visiblearmorslots.client.gui;

import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.inventory.ContainerDispenserCustom;



@SideOnly(Side.CLIENT)
public class GuiDispenserCustom extends GuiDispenser
{


    public GuiDispenserCustom(InventoryPlayer playerInv, IInventory dispenserInv) {
        super(playerInv, dispenserInv);

        final ContainerDispenserCustom customContainer = new ContainerDispenserCustom(playerInv, dispenserInv);
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