package sidben.visiblearmorslots.proxy;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.reference.Reference;



public class ClientProxy extends CommonProxy
{



    @Override
    public void pre_initialize()
    {
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
        final BlockPos targetPos = new BlockPos(x, y, z);
        final TileEntity targetTile = world.getTileEntity(targetPos);


        return null;
    }



}
