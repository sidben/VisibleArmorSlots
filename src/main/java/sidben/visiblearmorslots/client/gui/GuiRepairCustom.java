package sidben.visiblearmorslots.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sidben.visiblearmorslots.helper.ExtraSlotsHelper;
import sidben.visiblearmorslots.inventory.ContainerRepairCustom;



@SideOnly(Side.CLIENT)
public class GuiRepairCustom extends GuiRepair
{


    public GuiRepairCustom(InventoryPlayer inventory, World worldIn) {
        super(inventory, worldIn);

        final ContainerRepairCustom customContainer = new ContainerRepairCustom(inventory, worldIn, Minecraft.getMinecraft().thePlayer);

        this.inventorySlots = customContainer;
        ObfuscationReflectionHelper.setPrivateValue(GuiRepair.class, this, customContainer, "anvil", "field_147092_v");
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        // Draws the extra slots
        ExtraSlotsHelper.drawExtraSlotsOnGui(this, this.xSize, this.ySize);

        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    }


}