package sidben.visiblearmorslots.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.client.gui.GuiChestCustom;
import sidben.visiblearmorslots.handler.PlayerEventHandler;
import sidben.visiblearmorslots.inventory.ContainerChestCustom;
import sidben.visiblearmorslots.inventory.ContainerEnchantmentCustom;
import sidben.visiblearmorslots.inventory.ContainerRepairCustom;


/*
 * Base proxy class, here I initialize everything that must happen on both, server and client.
 */
public abstract class CommonProxy implements IProxy
{


    @Override
    public void pre_initialize()
    {
    }


    @Override
    public void initialize()
    {
        // Event Handlers
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
    }


    @Override
    public void post_initialize()
    {
    }



    // returns an instance of the Container
    @Override
    public Object getServerGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z)
    {
        final TileEntity targetTile = world.getTileEntity(new BlockPos(x, y, z));
        
        if (guiID == ModVisibleArmorSlots.GUI_ENCHANTMENT_TABLE) {
            return new ContainerEnchantmentCustom(player.inventory, world, new BlockPos(x, y, z));
        } 
        
        else if (guiID == ModVisibleArmorSlots.GUI_ANVIL) {
            return new ContainerRepairCustom(player.inventory, world, new BlockPos(x, y, z), player);
        } 
        
        else if (guiID == ModVisibleArmorSlots.GUI_CHEST) {
            final TileEntityChest tile = (TileEntityChest) targetTile;
            return new ContainerChestCustom(player.inventory, tile, player);
        }

        else if (guiID == ModVisibleArmorSlots.GUI_ENDER_CHEST) {
            InventoryEnderChest enderInventory = player.getInventoryEnderChest();
            return new ContainerChestCustom(player.inventory, enderInventory, player);
        }

        return null;
    }


    @Override
    public Object getClientGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }



}
