package sidben.visiblearmorslots.proxy;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.handler.PlayerEventHandler;


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
        final BlockPos targetPos = new BlockPos(x, y, z);
        final TileEntity targetTile = world.getTileEntity(targetPos);

        return null;
    }


    @Override
    public Object getClientGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }



}
