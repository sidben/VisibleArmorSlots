package sidben.visiblearmorslots.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.client.gui.GuiChestCustom;
import sidben.visiblearmorslots.client.gui.GuiEnchantmentCustom;
import sidben.visiblearmorslots.client.gui.GuiRepairCustom;
import sidben.visiblearmorslots.inventory.ContainerChestCustom;
import sidben.visiblearmorslots.reference.Reference;



public class ClientProxy extends CommonProxy
{


    // GUI textures and paths
    public static String guiTextureExtraSlots;



    @Override
    public void pre_initialize()
    {
        // GUI
        ClientProxy.guiTextureExtraSlots = Reference.ModID + ":textures/gui/extra-slots.png";


        super.pre_initialize();
    }



    @Override
    public void initialize()
    {
        super.initialize();
    }


    // returns an instance of the GUI
    @Override
    public Object getClientGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z)
    {
        final TileEntity targetTile = world.getTileEntity(new BlockPos(x, y, z));
        
        if (guiID == ModVisibleArmorSlots.GUI_ENCHANTMENT_TABLE) {
            if (!(targetTile instanceof TileEntityEnchantmentTable)) return null;
            final TileEntityEnchantmentTable tile = (TileEntityEnchantmentTable) targetTile;
            return new GuiEnchantmentCustom(player.inventory, world, tile);
        } 
        
        else if (guiID == ModVisibleArmorSlots.GUI_ANVIL) {
            return new GuiRepairCustom(player.inventory, world);
        } 
        
        else if (guiID == ModVisibleArmorSlots.GUI_CHEST) {
            if (!(targetTile instanceof TileEntityChest)) return null;
            final TileEntityChest tile = (TileEntityChest) targetTile;
            return new GuiChestCustom(player.inventory, tile);
        }

        else if (guiID == ModVisibleArmorSlots.GUI_ENDER_CHEST) {
            if (!(targetTile instanceof TileEntityEnderChest)) return null;
            InventoryEnderChest enderInventory = player.getInventoryEnderChest();
            return new GuiChestCustom(player.inventory, enderInventory);
        }
        
        return null;
    }



}
