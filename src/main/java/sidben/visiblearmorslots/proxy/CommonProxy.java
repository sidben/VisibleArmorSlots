package sidben.visiblearmorslots.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.handler.PlayerEventHandler;
import sidben.visiblearmorslots.inventory.ContainerBrewingStandCustom;
import sidben.visiblearmorslots.inventory.ContainerChestCustom;
import sidben.visiblearmorslots.inventory.ContainerCraftingCustom;
import sidben.visiblearmorslots.inventory.ContainerDispenserCustom;
import sidben.visiblearmorslots.inventory.ContainerEnchantmentCustom;
import sidben.visiblearmorslots.inventory.ContainerFurnaceCustom;
import sidben.visiblearmorslots.inventory.ContainerHopperCustom;
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
        final BlockPos targetPos = new BlockPos(x, y, z);
        final TileEntity targetTile = world.getTileEntity(targetPos);

        if (guiID == ModVisibleArmorSlots.GUI_ENCHANTMENT_TABLE) {
            return new ContainerEnchantmentCustom(player.inventory, world, targetPos);
        }

        else if (guiID == ModVisibleArmorSlots.GUI_ANVIL) {
            return new ContainerRepairCustom(player.inventory, world, targetPos, player);
        }

        else if (guiID == ModVisibleArmorSlots.GUI_CHEST) {
            final TileEntityChest tile = (TileEntityChest) targetTile;
            return new ContainerChestCustom(player.inventory, tile, player);
        }

        else if (guiID == ModVisibleArmorSlots.GUI_ENDER_CHEST) {
            final InventoryEnderChest enderInventory = player.getInventoryEnderChest();
            return new ContainerChestCustom(player.inventory, enderInventory, player);
        }

        else if (guiID == ModVisibleArmorSlots.GUI_CRAFTING_TABLE) {
            return new ContainerCraftingCustom(player.inventory, world, targetPos);
        }

        else if (guiID == ModVisibleArmorSlots.GUI_FURNACE) {
            return new ContainerFurnaceCustom(player.inventory, (IInventory) targetTile);
        }

        else if (guiID == ModVisibleArmorSlots.GUI_DISPENSER) {
            return new ContainerDispenserCustom(player.inventory, (IInventory) targetTile);
        }

        else if (guiID == ModVisibleArmorSlots.GUI_HOPPER) {
            return new ContainerHopperCustom(player.inventory, (IInventory) targetTile, player);
        }

        else if (guiID == ModVisibleArmorSlots.GUI_BREWING_STAND) {
            return new ContainerBrewingStandCustom(player.inventory, (IInventory) targetTile);
        }

        return null;
    }


    @Override
    public Object getClientGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }



}
