package sidben.visiblearmorslots.client.gui;

import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.helper.ExtraSlotsHelper;
import sidben.visiblearmorslots.helper.LogHelper;
import sidben.visiblearmorslots.inventory.ContainerEnchantmentCustom;
import sidben.visiblearmorslots.inventory.ContainerRepairCustom;
import sidben.visiblearmorslots.proxy.ClientProxy;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.glu.Project;




@SideOnly(Side.CLIENT)
public class GuiEnchantmentCustom extends GuiEnchantment {

    
    public GuiEnchantmentCustom(InventoryPlayer inventory, World worldIn, IWorldNameable nameable)
    {
        super(inventory, worldIn, nameable);

		ContainerEnchantmentCustom customContainer = new ContainerEnchantmentCustom(inventory, worldIn);

		this.inventorySlots = customContainer;
		ObfuscationReflectionHelper.setPrivateValue(GuiEnchantment.class, this, customContainer, "container", "field_147075_G");
    }


    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
    	// Draws the extra slots
    	ExtraSlotsHelper.drawExtraSlotsOnGui(this, this.xSize, this.ySize);
        
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    }

    
}