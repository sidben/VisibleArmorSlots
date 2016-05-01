package sidben.visiblearmorslots.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sidben.visiblearmorslots.helper.ExtraSlotsHelper;


public class ContainerRepairCustom extends ContainerRepair
{


    @SideOnly(Side.CLIENT)
    public ContainerRepairCustom(InventoryPlayer playerInventory, World worldIn, EntityPlayer player) {
        this(playerInventory, worldIn, BlockPos.ORIGIN, player);
    }


    public ContainerRepairCustom(InventoryPlayer playerInventory, World worldIn, BlockPos pos, EntityPlayer player) {
        super(playerInventory, worldIn, pos, player);

        // Adds the extra slots
        ExtraSlotsHelper.addExtraSlotsToContainer(this, playerInventory);
    }


}