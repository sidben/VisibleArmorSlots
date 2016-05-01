package sidben.visiblearmorslots.proxy;

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
import net.minecraft.world.World;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.client.gui.GuiBeaconCustom;
import sidben.visiblearmorslots.client.gui.GuiBrewingStandCustom;
import sidben.visiblearmorslots.client.gui.GuiChestCustom;
import sidben.visiblearmorslots.client.gui.GuiCraftingCustom;
import sidben.visiblearmorslots.client.gui.GuiDispenserCustom;
import sidben.visiblearmorslots.client.gui.GuiEnchantmentCustom;
import sidben.visiblearmorslots.client.gui.GuiFurnaceCustom;
import sidben.visiblearmorslots.client.gui.GuiHopperCustom;
import sidben.visiblearmorslots.client.gui.GuiRepairCustom;
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

        if (guiID == ModVisibleArmorSlots.GUI_ENCHANTMENT_TABLE) {
            if (!(targetTile instanceof TileEntityEnchantmentTable)) {
                return null;
            }
            final TileEntityEnchantmentTable tile = (TileEntityEnchantmentTable) targetTile;
            return new GuiEnchantmentCustom(player.inventory, world, tile);
        }

        else if (guiID == ModVisibleArmorSlots.GUI_ANVIL) {
            return new GuiRepairCustom(player.inventory, world);
        }

        else if (guiID == ModVisibleArmorSlots.GUI_CHEST) {
            if (!(targetTile instanceof TileEntityChest)) {
                return null;
            }
            final TileEntityChest tile = (TileEntityChest) targetTile;
            return new GuiChestCustom(player.inventory, tile);
        }

        else if (guiID == ModVisibleArmorSlots.GUI_ENDER_CHEST) {
            if (!(targetTile instanceof TileEntityEnderChest)) {
                return null;
            }
            final InventoryEnderChest enderInventory = player.getInventoryEnderChest();
            return new GuiChestCustom(player.inventory, enderInventory);
        }

        else if (guiID == ModVisibleArmorSlots.GUI_CRAFTING_TABLE) {
            return new GuiCraftingCustom(player.inventory, world);
        }

        else if (guiID == ModVisibleArmorSlots.GUI_FURNACE) {
            if (!(targetTile instanceof TileEntityFurnace)) {
                return null;
            }
            final TileEntityFurnace tile = (TileEntityFurnace) targetTile;
            return new GuiFurnaceCustom(player.inventory, tile);
        }

        else if (guiID == ModVisibleArmorSlots.GUI_DISPENSER) {
            final TileEntityDispenser tile = (TileEntityDispenser) targetTile;
            return new GuiDispenserCustom(player.inventory, tile);
        }

        else if (guiID == ModVisibleArmorSlots.GUI_HOPPER) {
            final TileEntityHopper tile = (TileEntityHopper) targetTile;
            return new GuiHopperCustom(player.inventory, tile);
        }

        else if (guiID == ModVisibleArmorSlots.GUI_BREWING_STAND) {
            final TileEntityBrewingStand tile = (TileEntityBrewingStand) targetTile;
            return new GuiBrewingStandCustom(player.inventory, tile);
        }

        else if (guiID == ModVisibleArmorSlots.GUI_BEACON) {
            final TileEntityBeacon tile = (TileEntityBeacon) targetTile;
            return new GuiBeaconCustom(player.inventory, tile);
        }

        return null;
    }



}
