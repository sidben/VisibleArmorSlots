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

        // Check if this GUI need extra offset
        Integer xOffset = 0;
        Integer yOffset = 0;
        
        if (originalContainer instanceof IExtraOffset) {
            IExtraOffset extra = ((IExtraOffset) originalContainer);
            xOffset = extra.getXOffset();
            yOffset = extra.getYOffset();
        }

        // Adds the armor slots
        addSlotToContainer(originalContainer, new SlotArmor(playerInventory, PLAYER_SLOT_INDEX_HELMET, getExtraSlotsXOffset() + xOffset, 66 + yOffset));
        addSlotToContainer(originalContainer, new SlotArmor(playerInventory, PLAYER_SLOT_INDEX_CHESTPLATE, getExtraSlotsXOffset() + xOffset, 84 + yOffset));
        addSlotToContainer(originalContainer, new SlotArmor(playerInventory, PLAYER_SLOT_INDEX_LEGGINGS, getExtraSlotsXOffset() + xOffset, 102 + yOffset));
        addSlotToContainer(originalContainer, new SlotArmor(playerInventory, PLAYER_SLOT_INDEX_BOOTS, getExtraSlotsXOffset() + xOffset, 120 + yOffset));
        addSlotToContainer(originalContainer, new SlotOffHand(playerInventory, PLAYER_SLOT_INDEX_OFFHAND, getExtraSlotsXOffset() + xOffset, 142 + yOffset));

    }


    protected Slot addSlotToContainer(Container container, Slot slotIn)
    {
        slotIn.slotNumber = container.inventorySlots.size();
        container.inventorySlots.add(slotIn);
        container.inventoryItemStacks.add((ItemStack) null);
        return slotIn;
    }

    
    protected int getExtraSlotsXOffset() 
    {
        // NOTE: SLOT_SIDES[1] == "RIGHT"
        if (ConfigurationHandler.extraSlotsSide.equals(ConfigurationHandler.SLOT_SIDES[1])) {
            return 180 + ConfigurationHandler.extraSlotsMargin;
        } else {
            return -20 - ConfigurationHandler.extraSlotsMargin;
        }
    }

}
