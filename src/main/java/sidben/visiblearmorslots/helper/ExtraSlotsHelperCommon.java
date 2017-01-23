package sidben.visiblearmorslots.helper;

import java.util.ListIterator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sidben.visiblearmorslots.config.ConfigurationHandler;
import sidben.visiblearmorslots.inventory.SlotArmor;
import sidben.visiblearmorslots.inventory.SlotOffHand;


// TODO: Implement extra slots on creative menu (right know it messes up the hotbar slots, so I removed it)

public abstract class ExtraSlotsHelperCommon
{

    private final static int PLAYER_SLOT_INDEX_HELMET     = 39;
    private final static int PLAYER_SLOT_INDEX_CHESTPLATE = 38;
    private final static int PLAYER_SLOT_INDEX_LEGGINGS   = 37;
    private final static int PLAYER_SLOT_INDEX_BOOTS      = 36;
    private final static int PLAYER_SLOT_INDEX_OFFHAND    = 40;
    private final static int HOTBAR_FIRST_SLOT_INDEX      = 0;
    private final static int HELMET_SLOT_Y_POSITION       = (18 * 0);
    private final static int CHESTPLATE_SLOT_Y_POSITION   = (18 * 1);
    private final static int LEGGINGS_SLOT_Y_POSITION     = (18 * 2);
    private final static int BOOTS_SLOT_Y_POSITION        = (18 * 3);
    private final static int OFFHAND_SLOT_Y_POSITION      = 76;



    public void drawExtraSlotsOnGui(GuiContainer originalGui)
    {
    }



    public void addExtraSlotsToContainer(Container container, IInventory playerInventory)
    {
        LogHelper.trace("addExtraSlotsToContainer(%s)", container);
        LogHelper.trace("    Before: %d slots", container.inventorySlots.size());


        // Check if this GUI need extra offset
        final Integer xOffset = getExtraSlotsXOffset(container);
        final Integer yOffset = getExtraSlotsYOffset(container);


        // Adds the extra slots
        // NOTE: When the player shift-clicks from a container to the inventory, the first slot
        // that the game will try to use is the last of this list (helmet).
        addSlotToContainer(container, new SlotOffHand(playerInventory, PLAYER_SLOT_INDEX_OFFHAND, xOffset, OFFHAND_SLOT_Y_POSITION + yOffset));
        addSlotToContainer(container, new SlotArmor(playerInventory, PLAYER_SLOT_INDEX_BOOTS, xOffset, BOOTS_SLOT_Y_POSITION + yOffset));
        addSlotToContainer(container, new SlotArmor(playerInventory, PLAYER_SLOT_INDEX_LEGGINGS, xOffset, LEGGINGS_SLOT_Y_POSITION + yOffset));
        addSlotToContainer(container, new SlotArmor(playerInventory, PLAYER_SLOT_INDEX_CHESTPLATE, xOffset, CHESTPLATE_SLOT_Y_POSITION + yOffset));
        addSlotToContainer(container, new SlotArmor(playerInventory, PLAYER_SLOT_INDEX_HELMET, xOffset, HELMET_SLOT_Y_POSITION + yOffset));

        // TODO: change the order to attempt fix integration with Inventory Tweaks and creative mode

        LogHelper.trace("    After: %d slots", container.inventorySlots.size());
    }


    protected Slot addSlotToContainer(Container container, Slot slotIn)
    {
        slotIn.slotNumber = container.inventorySlots.size();
        container.inventorySlots.add(slotIn);
        container.inventoryItemStacks.add(ItemStack.EMPTY);
        return slotIn;
    }


    protected final int getExtraSlotsXOffset(Container container)
    {
        if (ConfigurationHandler.extraSlotsSide.equals(ConfigurationHandler.POSITION_RIGHT)) {
            int xOffset = 0;


            // OBS: for vanilla containers I can use fixed values. Containers from other mods need guessing.
            if (isVanillaContainer(container)) {
                if (container instanceof ContainerBeacon) {
                    xOffset = 234;
                } else {
                    xOffset = 180;
                }

            } else {
                // Since containers don't have width/height, I need to
                // use other slots as reference to position my custom slots.
                //
                // For the X coordinate, I seek the first hotbar slot (slot #1)
                // and compare to the initial point of the container. Assuming that
                // the hot bar is centered and the container is symmetrical, the distance
                // from the first slot to the zero coordinate should be the same from
                // the last slot and the right margin.

                for (final Slot theSlot : container.inventorySlots) {
                    if (theSlot.inventory instanceof InventoryPlayer && theSlot.getSlotIndex() == HOTBAR_FIRST_SLOT_INDEX) {
                        final int positionOfFirstHotbarSlot = theSlot.xPos;
                        final int estimatedPositionOfLastHotbarSlot = positionOfFirstHotbarSlot + 144; // 144 == 8 slots with 18px width

                        xOffset = estimatedPositionOfLastHotbarSlot + 16 + positionOfFirstHotbarSlot + 4;

                        LogHelper.trace("  Reference slot - x: %d / y: %d / stack: %s", theSlot.xPos, theSlot.yPos, theSlot.getStack());
                        LogHelper.trace("  xOffset: %d", xOffset);

                        break;
                    }
                }

            }

            return xOffset + ConfigurationHandler.extraSlotsMargin;

        } else {
            // For the left side, no extra math needed.
            return -20 - ConfigurationHandler.extraSlotsMargin;

        }
    }

    protected final int getExtraSlotsYOffset(Container container)
    {
        // Since containers don't have width/height, I need to
        // use other slots as reference to position my custom slots.
        //
        // For the Y coordinate, I seek a hotbar slot (slot #1, index 0)
        // to find where is the bottom.

        int yOffset = 0;

        for (final Slot theSlot : container.inventorySlots) {
            if (theSlot.inventory instanceof InventoryPlayer && theSlot.getSlotIndex() == HOTBAR_FIRST_SLOT_INDEX) {
                yOffset = theSlot.yPos - OFFHAND_SLOT_Y_POSITION;
                break;
            }
        }

        return yOffset;
    }



    public boolean shouldAddExtraSlotsToContainer(Container container)
    {
        // Check if it's a valid container to get extra slots
        if (container == null) { return false; }
        if (container instanceof ContainerPlayer) { return false; }
        if (isContainerBlacklisted(container)) { return false; }


        // TODO: counter for the amount of iterations to find the slot
        // Check if the slots weren't added already (iterates in reverse order, the slots should be at the end of the array)
        final ListIterator<Slot> iterator = container.inventorySlots.listIterator(container.inventorySlots.size());
        while (iterator.hasPrevious()) {
            final Slot theSlot = iterator.previous();
            if ((theSlot instanceof SlotArmor) || (theSlot instanceof SlotOffHand)) { return false; }
        }

        return true;
    }


    public boolean shouldDrawExtraSlotsOnGui(GuiContainer gui)
    {
        if (isContainerBlacklisted(gui.inventorySlots)) { return false; }

        return true;
    }



    /**
     * Checks is the container belongs to vanilla Minecraft or not.
     * Modded containers may require extra logic.
     */
    protected final boolean isVanillaContainer(Container container)
    {
        final String className = container.getClass().getName();
        return className.startsWith(ConfigurationHandler.MINECRAFT_NAMESPACE + ".");
    }


    protected final boolean isContainerBlacklisted(Container container)
    {
        if (ConfigurationHandler.blacklistedModPackages.length > 0) {
            final String className = container.getClass().getName();
            LogHelper.trace("Checking if %s is blacklisted...", className);

            for (final String blacklisted : ConfigurationHandler.blacklistedModPackages) {
                if (className.startsWith(blacklisted + ".")) { return true; }
            }
        }

        return false;
    }



}
