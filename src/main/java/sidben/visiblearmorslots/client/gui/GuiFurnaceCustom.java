package sidben.visiblearmorslots.client.gui;

import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sidben.visiblearmorslots.helper.ExtraSlotsHelper;
import sidben.visiblearmorslots.inventory.ContainerFurnaceCustom;



@SideOnly(Side.CLIENT)
public class GuiFurnaceCustom extends GuiFurnace
{


    public GuiFurnaceCustom(InventoryPlayer playerInv, IInventory furnaceInv) {
        super(playerInv, furnaceInv);

        final ContainerFurnaceCustom customContainer = new ContainerFurnaceCustom(playerInv, furnaceInv);

        this.inventorySlots = customContainer;
        ObfuscationReflectionHelper.setPrivateValue(GuiFurnace.class, this, furnaceInv, "tileFurnace", "field_147086_v");
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        // Draws the extra slots
        ExtraSlotsHelper.drawExtraSlotsOnGui(this, this.xSize, this.ySize);

        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    }


}