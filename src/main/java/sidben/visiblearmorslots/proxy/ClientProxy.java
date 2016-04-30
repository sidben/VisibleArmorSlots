package sidben.visiblearmorslots.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.client.gui.GuiEnchantmentCustom;
import sidben.visiblearmorslots.handler.PlayerEventHandler;
import sidben.visiblearmorslots.helper.LogHelper;
import sidben.visiblearmorslots.reference.Reference;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.RenderingRegistry;



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
    	LogHelper.info("getClientGuiElement() - " + guiID);
    	
    	
    	if (guiID == ModVisibleArmorSlots.GUI_ENCHANTMENT_TABLE) {
            // final TileEntityEnchantmentTable tile = (TileEntityEnchantmentTable) world.getTileEntity(new BlockPos(x, y, z));
            return new GuiEnchantmentCustom(player.inventory, world);
        }

        return null;
    }



}
