package sidben.visiblearmorslots.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.client.gui.GuiEnchantmentCustom;
import sidben.visiblearmorslots.client.gui.GuiRepairCustom;
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
        if (guiID == ModVisibleArmorSlots.GUI_ENCHANTMENT_TABLE) {
            final TileEntityEnchantmentTable tile = (TileEntityEnchantmentTable) world.getTileEntity(new BlockPos(x, y, z));
            return new GuiEnchantmentCustom(player.inventory, world, tile);
        } else if (guiID == ModVisibleArmorSlots.GUI_ANVIL) {
            return new GuiRepairCustom(player.inventory, world);
        }

        return null;
    }



}
