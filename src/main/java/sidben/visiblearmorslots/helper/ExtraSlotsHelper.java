package sidben.visiblearmorslots.helper;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sidben.visiblearmorslots.inventory.SlotArmor;
import sidben.visiblearmorslots.inventory.SlotOffHand;
import sidben.visiblearmorslots.proxy.ClientProxy;


// TODO: Process shift+click on my GUIs

public class ExtraSlotsHelper
{

    private static final ResourceLocation GUI_EXTRA_SLOTS   = new ResourceLocation(ClientProxy.guiTextureExtraSlots);
    private static final int              GUI_SLOTS_YOFFSET = -22;



    public static void drawExtraSlotsOnGui(GuiContainer originalGui, int xSize, int ySize)
    {

        // Draws the extra armor slots
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        originalGui.mc.getTextureManager().bindTexture(GUI_EXTRA_SLOTS);

        final int startX = ((originalGui.width - xSize) / 2) - 4 + GUI_SLOTS_YOFFSET;
        final int startY = (originalGui.height - ySize) / 2;
        originalGui.drawTexturedModalRect(startX, startY, 0, 0, xSize, ySize);

    }



    public static void addExtraSlotsToContainer(Container originalContainer, InventoryPlayer playerInv)
    {

        // Adds the armor slots
        addSlotToContainer(originalContainer, new SlotArmor(playerInv, 39, GUI_SLOTS_YOFFSET, 66));				// head
        addSlotToContainer(originalContainer, new SlotArmor(playerInv, 38, GUI_SLOTS_YOFFSET, 84));				// chest
        addSlotToContainer(originalContainer, new SlotArmor(playerInv, 37, GUI_SLOTS_YOFFSET, 102));			// legs
        addSlotToContainer(originalContainer, new SlotArmor(playerInv, 36, GUI_SLOTS_YOFFSET, 120));			// boots
        addSlotToContainer(originalContainer, new SlotOffHand(playerInv, 40, GUI_SLOTS_YOFFSET, 142));			// off-hand

    }



    private static Slot addSlotToContainer(Container container, Slot slotIn)
    {
        slotIn.slotNumber = container.inventorySlots.size();
        container.inventorySlots.add(slotIn);
        container.inventoryItemStacks.add((ItemStack) null);
        return slotIn;
    }


}
