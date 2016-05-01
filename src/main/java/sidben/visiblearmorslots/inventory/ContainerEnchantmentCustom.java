package sidben.visiblearmorslots.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sidben.visiblearmorslots.helper.ExtraSlotsHelper;


public class ContainerEnchantmentCustom extends ContainerEnchantment
{


    @SideOnly(Side.CLIENT)
    public ContainerEnchantmentCustom(InventoryPlayer playerInv, World worldIn) {
        this(playerInv, worldIn, BlockPos.ORIGIN);
    }


    public ContainerEnchantmentCustom(InventoryPlayer playerInv, World worldIn, BlockPos pos) {
        super(playerInv, worldIn, pos);

        // Adds the extra slots
        ExtraSlotsHelper.addExtraSlotsToContainer(this, playerInv);
    }


}