package sidben.visiblearmorslots.helper;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sidben.visiblearmorslots.client.gui.GuiBeaconCustom;
import sidben.visiblearmorslots.client.gui.GuiHopperCustom;
import sidben.visiblearmorslots.inventory.ContainerBeaconCustom;
import sidben.visiblearmorslots.inventory.ContainerHopperCustom;
import sidben.visiblearmorslots.inventory.SlotArmor;
import sidben.visiblearmorslots.inventory.SlotOffHand;
import sidben.visiblearmorslots.proxy.ClientProxy;


// TODO: Process shift+click on my GUIs

public class ExtraSlotsHelper
{

    public static int                           PLAYER_SLOT_INDEX_HELMET     = 39;
    public static int                           PLAYER_SLOT_INDEX_CHESTPLATE = 38;
    public static int                           PLAYER_SLOT_INDEX_LEGGINGS   = 37;
    public static int                           PLAYER_SLOT_INDEX_BOOTS      = 36;
    public static int                           PLAYER_SLOT_INDEX_OFFHAND    = 40;

    private static final ResourceLocation       GUI_EXTRA_SLOTS              = new ResourceLocation(ClientProxy.guiTextureExtraSlots);
    private static final int                    GUI_SLOTS_XOFFSET            = -22;

    private static final Map<Class<?>, Integer> guiYOffsetMap;

    static {
        guiYOffsetMap = new HashMap<Class<?>, Integer>();
        guiYOffsetMap.put(GuiBeaconCustom.class, 53);
        guiYOffsetMap.put(ContainerBeaconCustom.class, 53);
        guiYOffsetMap.put(GuiHopperCustom.class, -33);
        guiYOffsetMap.put(ContainerHopperCustom.class, -33);
    }

    
    


    public static void drawExtraSlotsOnGui(GuiContainer originalGui, int xSize, int ySize)
    {
        Integer yOffset = 0;

        // Check if this GUI need extra Y offset
        yOffset = guiYOffsetMap.containsKey(originalGui.getClass()) ? guiYOffsetMap.get(originalGui.getClass()) : 0;


        // Draws the extra armor slots
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        originalGui.mc.getTextureManager().bindTexture(GUI_EXTRA_SLOTS);

        final int startX = ((originalGui.width - xSize) / 2) - 4 + GUI_SLOTS_XOFFSET;
        final int startY = ((originalGui.height - ySize) / 2) + yOffset;
        originalGui.drawTexturedModalRect(startX, startY, 0, 0, xSize, 162);

    }



    public static void addExtraSlotsToContainer(Container originalContainer, IInventory playerInventory)
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


    private static Slot addSlotToContainer(Container container, Slot slotIn)
    {
        slotIn.slotNumber = container.inventorySlots.size();
        container.inventorySlots.add(slotIn);
        container.inventoryItemStacks.add((ItemStack) null);
        return slotIn;
    }


}
