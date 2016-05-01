package sidben.visiblearmorslots.client.gui;

import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.inventory.ContainerEnchantmentCustom;



@SideOnly(Side.CLIENT)
public class GuiEnchantmentCustom extends GuiEnchantment
{


    public GuiEnchantmentCustom(InventoryPlayer inventory, World worldIn, IWorldNameable nameable) {
        super(inventory, worldIn, nameable);

        final ContainerEnchantmentCustom customContainer = new ContainerEnchantmentCustom(inventory, worldIn);

        this.inventorySlots = customContainer;
        ObfuscationReflectionHelper.setPrivateValue(GuiEnchantment.class, this, customContainer, "container", "field_147075_G");
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        // Draws the extra slots
        ModVisibleArmorSlots.extraSlotsHelper.drawExtraSlotsOnGui(this, this.xSize, this.ySize);

        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    }


}