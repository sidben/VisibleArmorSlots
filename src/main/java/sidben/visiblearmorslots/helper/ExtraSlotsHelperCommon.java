package sidben.visiblearmorslots.helper;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sidben.visiblearmorslots.handler.ConfigurationHandler;
import sidben.visiblearmorslots.inventory.SlotArmor;
import sidben.visiblearmorslots.inventory.SlotOffHand;


// TODO: Process shift+click on my GUIs
// TODO: Fix creative mode

public abstract class ExtraSlotsHelperCommon
{

    public static int PLAYER_SLOT_INDEX_HELMET     = 39;
    public static int PLAYER_SLOT_INDEX_CHESTPLATE = 38;
    public static int PLAYER_SLOT_INDEX_LEGGINGS   = 37;
    public static int PLAYER_SLOT_INDEX_BOOTS      = 36;
    public static int PLAYER_SLOT_INDEX_OFFHAND    = 40;



    public void drawExtraSlotsOnGui(GuiContainer originalGui)
    {
    }



    public void addExtraSlotsToContainer(Container container, IInventory playerInventory)
    {
        LogHelper.info("addExtraSlotsToContainer(" + container + ")");
        LogHelper.info("    Before: " + container.inventorySlots.size() + " slots");

        
        // Check if this GUI need extra offset
        Integer xOffset = getExtraSlotsXOffset(container);
        Integer yOffset = getExtraSlotsYOffset(container);
       

        // Adds the extra slots
        addSlotToContainer(container, new SlotArmor(playerInventory, PLAYER_SLOT_INDEX_HELMET, xOffset, 0 + yOffset));
        addSlotToContainer(container, new SlotArmor(playerInventory, PLAYER_SLOT_INDEX_CHESTPLATE, xOffset, 18 + yOffset));
        addSlotToContainer(container, new SlotArmor(playerInventory, PLAYER_SLOT_INDEX_LEGGINGS, xOffset, 36 + yOffset));
        addSlotToContainer(container, new SlotArmor(playerInventory, PLAYER_SLOT_INDEX_BOOTS, xOffset, 54 + yOffset));
        addSlotToContainer(container, new SlotOffHand(playerInventory, PLAYER_SLOT_INDEX_OFFHAND, xOffset, 76 + yOffset));

        
        LogHelper.info("    After: " + container.inventorySlots.size() + " slots");
    }


    protected Slot addSlotToContainer(Container container, Slot slotIn)
    {
        slotIn.slotNumber = container.inventorySlots.size();
        container.inventorySlots.add(slotIn);
        container.inventoryItemStacks.add((ItemStack) null);
        return slotIn;
    }

    
    protected int getExtraSlotsXOffset(Container container) 
    {
        // NOTE: SLOT_SIDES[1] == "RIGHT"
        if (ConfigurationHandler.extraSlotsSide.equals(ConfigurationHandler.SLOT_SIDES[1])) {
            int xOffset = 0;
            
            
            // OBS: for vanilla containers I can use fixed values. Containers from other mods need guessing. 
            if (container instanceof ContainerBeacon) {
                xOffset = 234;
            } else {
                xOffset = 180;
            }
            
            
            
            
            // TODO: Detect vanilla or modded containers
            
            // Since containers don't have width/height, I need to
            // use other slots as reference to position my custom slots.
            //
            // For the X coordinate, I seek the first hotbar slot (slot #1)
            // and compare to the initial point of the container. Assuming that 
            // the hot bar is centered and the container is symmetrical, the distance
            // from the first slot to the zero coordinate should be the same from
            // the last slot and the right margin.
            /*

            for (Slot theSlot : container.inventorySlots) 
            {
                if (theSlot.inventory instanceof InventoryPlayer && theSlot.getSlotIndex() == 0) {      // TODO: static
                    LogHelper.info("  x: " + theSlot.xDisplayPosition + " / y: " + theSlot.yDisplayPosition + " / stack: " + theSlot.getStack());
                    
                    int positionOfFirstHotbarSlot = theSlot.xDisplayPosition;
                    int estimatedPositionOfLastHotbarSlot = positionOfFirstHotbarSlot + 144;        // 144 == 8 slots with 18px width 
                    
                    xOffset = estimatedPositionOfLastHotbarSlot + 16 + positionOfFirstHotbarSlot + 4;
                    break;
                }
            }

            
            LogHelper.info("  " + xOffset);
           */
            
            return xOffset + ConfigurationHandler.extraSlotsMargin;
            
        } else {
            // For the left side, no extra math needed.
            return -20 - ConfigurationHandler.extraSlotsMargin;
            
        }
    }

    protected int getExtraSlotsYOffset(Container container) 
    {
        // Since containers don't have width/height, I need to
        // use other slots as reference to position my custom slots.
        //
        // For the Y coordinate, I seek the middle hotbar slot (slot #5)
        // to find where is the bottom.
        
        int yOffset = 0;
        
        for (Slot theSlot : container.inventorySlots) 
        {
            // LogHelper.info("  " + theSlot.getSlotIndex() + " - " + theSlot + " : " + theSlot.getStack() + " / inv: " + theSlot.inventory);
            if (theSlot.inventory instanceof InventoryPlayer && theSlot.getSlotIndex() == 4) {      // TODO: static
                // LogHelper.info("  x: " + theSlot.xDisplayPosition + " / y: " + theSlot.yDisplayPosition);
                
                yOffset = theSlot.yDisplayPosition - 76;
                break;
            }
        }
        
        return yOffset;
    }
    
    
    
    public boolean shouldAddExtraSlotsToContainer(Container container) 
    {
        // Check if it's a valid container to get extra slots
        if (container == null) return false;
        if (container instanceof ContainerPlayer) return false;
        
        // Check if the slots weren't added already
        for (Slot theSlot : container.inventorySlots)       // TODO: iterates on reverse order, the slots should be at the end of the array
        {
            if ((theSlot instanceof SlotArmor) || (theSlot instanceof SlotOffHand)) {
                return false;
            }
         }
       
        return true;
    }

    
    public boolean shouldDrawExtraSlotsOnGui(GuiContainer gui) 
    {
        return false;
    }
    
}
