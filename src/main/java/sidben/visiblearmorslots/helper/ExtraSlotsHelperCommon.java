package sidben.visiblearmorslots.helper;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sidben.visiblearmorslots.handler.ConfigurationHandler;
import sidben.visiblearmorslots.inventory.SlotArmor;
import sidben.visiblearmorslots.inventory.SlotOffHand;


// TODO: Process shift+click on my GUIs

public abstract class ExtraSlotsHelperCommon
{

    public static int PLAYER_SLOT_INDEX_HELMET     = 39;
    public static int PLAYER_SLOT_INDEX_CHESTPLATE = 38;
    public static int PLAYER_SLOT_INDEX_LEGGINGS   = 37;
    public static int PLAYER_SLOT_INDEX_BOOTS      = 36;
    public static int PLAYER_SLOT_INDEX_OFFHAND    = 40;



    public void drawExtraSlotsOnGui(GuiContainer originalGui, int xSize, int ySize)
    {
    }



    public void addExtraSlotsToContainer(Container originalContainer, IInventory playerInventory)
    {

        // Check if this GUI need extra Y offset
        Integer yOffset = 0;
        if (originalContainer instanceof IVerticalOffset) {
            yOffset = ((IVerticalOffset) originalContainer).getYOffset();
        }

        // Adds the armor slots
        addSlotToContainer(originalContainer, new SlotArmor(playerInventory, PLAYER_SLOT_INDEX_HELMET, ConfigurationHandler.GUI_SLOTS_XOFFSET, 66 + yOffset));
        addSlotToContainer(originalContainer, new SlotArmor(playerInventory, PLAYER_SLOT_INDEX_CHESTPLATE, ConfigurationHandler.GUI_SLOTS_XOFFSET, 84 + yOffset));
        addSlotToContainer(originalContainer, new SlotArmor(playerInventory, PLAYER_SLOT_INDEX_LEGGINGS, ConfigurationHandler.GUI_SLOTS_XOFFSET, 102 + yOffset));
        addSlotToContainer(originalContainer, new SlotArmor(playerInventory, PLAYER_SLOT_INDEX_BOOTS, ConfigurationHandler.GUI_SLOTS_XOFFSET, 120 + yOffset));
        addSlotToContainer(originalContainer, new SlotOffHand(playerInventory, PLAYER_SLOT_INDEX_OFFHAND, ConfigurationHandler.GUI_SLOTS_XOFFSET, 142 + yOffset));

    }


    protected Slot addSlotToContainer(Container container, Slot slotIn)
    {
        slotIn.slotNumber = container.inventorySlots.size();
        container.inventorySlots.add(slotIn);
        container.inventoryItemStacks.add((ItemStack) null);
        return slotIn;
    }


}
