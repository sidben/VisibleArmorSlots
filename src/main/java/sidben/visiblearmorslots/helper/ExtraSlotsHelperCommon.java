package sidben.visiblearmorslots.helper;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sidben.visiblearmorslots.client.gui.GuiBeaconCustom;
import sidben.visiblearmorslots.client.gui.GuiHopperCustom;
import sidben.visiblearmorslots.inventory.ContainerBeaconCustom;
import sidben.visiblearmorslots.inventory.ContainerHopperCustom;
import sidben.visiblearmorslots.inventory.SlotArmor;
import sidben.visiblearmorslots.inventory.SlotOffHand;


// TODO: Process shift+click on my GUIs

public abstract class ExtraSlotsHelperCommon
{

    public static int                      PLAYER_SLOT_INDEX_HELMET     = 39;
    public static int                      PLAYER_SLOT_INDEX_CHESTPLATE = 38;
    public static int                      PLAYER_SLOT_INDEX_LEGGINGS   = 37;
    public static int                      PLAYER_SLOT_INDEX_BOOTS      = 36;
    public static int                      PLAYER_SLOT_INDEX_OFFHAND    = 40;

    protected static final int             GUI_SLOTS_XOFFSET            = -22;
    protected static final int             HOPPER_YOFFSET               = -33;
    protected static final int             BEACON_YOFFSET               = 53;

    protected final Map<Class<?>, Integer> guiYOffsetMap;



    public ExtraSlotsHelperCommon() {
        guiYOffsetMap = new HashMap<Class<?>, Integer>();

        guiYOffsetMap.put(ContainerBeaconCustom.class, BEACON_YOFFSET);
        guiYOffsetMap.put(ContainerHopperCustom.class, HOPPER_YOFFSET);
    }



    public void drawExtraSlotsOnGui(GuiContainer originalGui, int xSize, int ySize)
    {
    }



    public void addExtraSlotsToContainer(Container originalContainer, IInventory playerInventory)
    {
        Integer yOffset = 0;

        // Check if this GUI need extra Y offset
        yOffset = guiYOffsetMap.containsKey(originalContainer.getClass()) ? guiYOffsetMap.get(originalContainer.getClass()) : 0;


        // Adds the armor slots
        addSlotToContainer(originalContainer, new SlotArmor(playerInventory, PLAYER_SLOT_INDEX_HELMET, GUI_SLOTS_XOFFSET, 66 + yOffset));
        addSlotToContainer(originalContainer, new SlotArmor(playerInventory, PLAYER_SLOT_INDEX_CHESTPLATE, GUI_SLOTS_XOFFSET, 84 + yOffset));
        addSlotToContainer(originalContainer, new SlotArmor(playerInventory, PLAYER_SLOT_INDEX_LEGGINGS, GUI_SLOTS_XOFFSET, 102 + yOffset));
        addSlotToContainer(originalContainer, new SlotArmor(playerInventory, PLAYER_SLOT_INDEX_BOOTS, GUI_SLOTS_XOFFSET, 120 + yOffset));
        addSlotToContainer(originalContainer, new SlotOffHand(playerInventory, PLAYER_SLOT_INDEX_OFFHAND, GUI_SLOTS_XOFFSET, 142 + yOffset));

    }


    protected Slot addSlotToContainer(Container container, Slot slotIn)
    {
        slotIn.slotNumber = container.inventorySlots.size();
        container.inventorySlots.add(slotIn);
        container.inventoryItemStacks.add((ItemStack) null);
        return slotIn;
    }


}
