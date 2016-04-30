package sidben.visiblearmorslots.proxy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.handler.PlayerEventHandler;
import sidben.visiblearmorslots.helper.LogHelper;
import sidben.visiblearmorslots.inventory.ContainerEnchantmentCustom;
import sidben.visiblearmorslots.reference.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;


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
    	LogHelper.info("getServerGuiElement() - " + guiID);

    	if (guiID == ModVisibleArmorSlots.GUI_ENCHANTMENT_TABLE) {
            // final TileEntityEnchantmentTable tile = (TileEntityEnchantmentTable) world.getTileEntity(new BlockPos(x, y, z));
            return new ContainerEnchantmentCustom(player.inventory, world, new BlockPos(x, y, z));
    	}

        return null;
    }


    @Override
    public Object getClientGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z)
    {
    	LogHelper.info("getClientGuiElement() - " + guiID);

    	return null;
    }



}
