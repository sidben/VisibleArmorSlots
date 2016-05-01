package sidben.visiblearmorslots.client.gui;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.inventory.ContainerCraftingCustom;



@SideOnly(Side.CLIENT)
public class GuiCraftingCustom extends GuiCrafting
{


    public GuiCraftingCustom(InventoryPlayer playerInv, World worldIn) {
        this(playerInv, worldIn, BlockPos.ORIGIN);
    }

    public GuiCraftingCustom(InventoryPlayer playerInv, World worldIn, BlockPos blockPosition) {
        super(playerInv, worldIn, blockPosition);

        final ContainerCraftingCustom customContainer = new ContainerCraftingCustom(playerInv, worldIn, blockPosition);
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