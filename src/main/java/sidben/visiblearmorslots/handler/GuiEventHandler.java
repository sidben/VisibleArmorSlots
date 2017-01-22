package sidben.visiblearmorslots.handler;

import java.util.List;
import org.lwjgl.input.Mouse;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.GuiScreenEvent.MouseInputEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.client.gui.GuiExtraSlots;
import sidben.visiblearmorslots.helper.LogHelper;
import sidben.visiblearmorslots.inventory.SlotArmor;
import sidben.visiblearmorslots.inventory.SlotOffHand;
import sidben.visiblearmorslots.reference.Reference;


public class GuiEventHandler
{

    
    
    

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onGuiOpenEvent(GuiOpenEvent event)
    {
        if (event.getGui() == null) { return; }
        
        final Minecraft mc = Minecraft.getMinecraft();

        
        LogHelper.trace("  onGuiOpenEvent(%s)", event.getGui());
        LogHelper.trace("      world: %s", mc.world);
        LogHelper.trace("      current screen: %s", mc.currentScreen);
        

        /*
        // TEMP for debug, only affect chests
        if (!(event.getGui() instanceof GuiChest)) { return; }
        
        
        if (event.getGui() instanceof GuiExtraSlots) { return; }
        if (!(event.getGui() instanceof GuiContainer)) { return; }
        
        if (mc.currentScreen == null) {
            LogHelper.trace("      - Injecting extra slots...");

            mc.displayGuiScreen(new GuiExtraSlots(mc.player, (GuiContainer) event.getGui()));
            event.setCanceled(true);
            
        } else {
            LogHelper.trace("      - Extra slots already injected");
            
        }
        */
    }


    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onInitGuiEvent(InitGuiEvent event)
    {
        // LogHelper.trace("  InitGuiEvent(%s)", event.getGui());
    }

    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onInitGuiEventPre(InitGuiEvent.Pre event)
    {
        LogHelper.trace("  InitGuiEvent.Pre(%s)", event.getGui());
    }

    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onInitGuiEventPost(InitGuiEvent.Post event)
    {
        LogHelper.trace("  InitGuiEvent.Post(%s)", event.getGui());
        LogHelper.trace("      width %d (%d) / height %d (%d)", event.getGui().width, Minecraft.getMinecraft().displayWidth, event.getGui().height, Minecraft.getMinecraft().displayHeight);
        
        
        /*
        // This negates the original GUI and only display my own

        final Minecraft mc = Minecraft.getMinecraft();
        if (!(event.getGui() instanceof GuiChest)) { return; }

        LogHelper.trace("      - Injecting extra slots...");
        mc.displayGuiScreen(new GuiExtraSlots(mc.player, (GuiContainer) event.getGui()));
        */
    }
    
    
    
    
    // TODO: check BUG: Open inventory, pick item, press ESC. Items disappears

    
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onActionPerformedEvent(ActionPerformedEvent event)
    {
        LogHelper.trace("  onActionPerformedEvent(%s, %s)", event.getGui(), event.getButton());
    }
        
    
    
    
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onDrawScreenEvent(DrawScreenEvent event)
    {
        if (event.getGui() == null) { return; }
        if (!(event.getGui() instanceof GuiChest || event.getGui() instanceof GuiInventory)) { return; } 
        
        
        /*
        LogHelper.trace("  onDrawScreenEvent(%s)", event.getGui());
        LogHelper.trace("      mouseX %04d, mouseY %04d, partialTicks %f", event.getMouseX(), event.getMouseY(), event.getRenderPartialTicks());
        */
        
        
        // Draws the extra armor slots
        final Minecraft mc = Minecraft.getMinecraft();
        
        int PLAYER_SLOT_INDEX_HELMET     = 39;
        int PLAYER_SLOT_INDEX_CHESTPLATE = 38;
        int PLAYER_SLOT_INDEX_LEGGINGS   = 37;
        int PLAYER_SLOT_INDEX_BOOTS      = 36;
        int PLAYER_SLOT_INDEX_OFFHAND    = 40;
        int HOTBAR_FIRST_SLOT_INDEX      = 0;
        int HELMET_SLOT_Y_POSITION       = (18 * 0);
        int CHESTPLATE_SLOT_Y_POSITION   = (18 * 1);
        int LEGGINGS_SLOT_Y_POSITION     = (18 * 2);
        int BOOTS_SLOT_Y_POSITION        = (18 * 3);
        int OFFHAND_SLOT_Y_POSITION      = 76;
        
        int xOffset = 14;
        int yOffset = 24;

        
        IInventory playerInventory = mc.player.inventory;
        Slot slot1 = new SlotOffHand(playerInventory, PLAYER_SLOT_INDEX_OFFHAND, xOffset, OFFHAND_SLOT_Y_POSITION + yOffset);
        Slot slot2 = new SlotOffHand(playerInventory, PLAYER_SLOT_INDEX_BOOTS, xOffset, BOOTS_SLOT_Y_POSITION + yOffset);
        Slot slot3 = new SlotOffHand(playerInventory, PLAYER_SLOT_INDEX_LEGGINGS, xOffset, LEGGINGS_SLOT_Y_POSITION + yOffset);
        Slot slot4 = new SlotOffHand(playerInventory, PLAYER_SLOT_INDEX_CHESTPLATE, xOffset, CHESTPLATE_SLOT_Y_POSITION + yOffset);
        Slot slot5 = new SlotOffHand(playerInventory, PLAYER_SLOT_INDEX_HELMET, xOffset, HELMET_SLOT_Y_POSITION + yOffset);
        
        List<Slot> extraSlots = Lists.<Slot>newArrayList();
        extraSlots.add(slot1);
        extraSlots.add(slot2);
        extraSlots.add(slot3);
        extraSlots.add(slot4);
        extraSlots.add(slot5);

        

        
        
        
        for (Slot oneSlot : extraSlots) {
            
            // Slot
            if (oneSlot.canBeHovered())
            {
                this.drawSlot(oneSlot, event.getGui());
            }


            // Hover
            /*
            if (this.isMouseOverSlot(oneSlot, event.getMouseX(), event.getMouseY()) && oneSlot.canBeHovered())
            {
                // this.theSlot = slot;
                
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                int j1 = oneSlot.xPos;
                int k1 = oneSlot.yPos;
                GlStateManager.colorMask(true, true, true, false);
                // event.getGui().drawGradientRect(j1, k1, j1 + 16, k1 + 16, -2130706433, -2130706433);     // Not Visible
                event.getGui().drawRect(j1, k1, j1 + 16, k1 + 16, -2130706433);
                GlStateManager.colorMask(true, true, true, true);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
            */        
        }
        
    }
        
    
    
        
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onBackgroundDrawScreenEvent(BackgroundDrawnEvent event)
    {
        if (event.getGui() == null) { return; }
        if (!(event.getGui() instanceof GuiChest || event.getGui() instanceof GuiInventory)) { return; } 

        
        // LogHelper.trace("  onBackgroundDrawScreenEvent(%s)", event.getGui());

        
        ResourceLocation GUI_EXTRA_SLOTS         = new ResourceLocation(Reference.ModID + ":textures/gui/extra-slots.png");

        // Draws the extra armor slots background
        final Minecraft mc = Minecraft.getMinecraft();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(GUI_EXTRA_SLOTS);
        event.getGui().drawTexturedModalRect(10, 20, 0, 62, 24, 100);
        // this.drawTexturedModalRect(this.guiLeft, this.guiTop, GUI_EXTRA_SLOTS_START_X, GUI_EXTRA_SLOTS_START_Y, GUI_EXTRA_SLOTS_WIDTH, GUI_EXTRA_SLOTS_HEIGHT);

        

    }


    
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onMouseInputEvent(MouseInputEvent.Post event)
    {
        if (event.getGui() == null) { return; }
        if (!(event.getGui() instanceof GuiChest || event.getGui() instanceof GuiInventory)) { return; } 
        // if (Mouse.getEventButton() < 0) return;         // Only accepts clicks - 0 = left, 1 = right, 2 = middle
        // if (!Mouse.getEventButtonState()) return;       // true == mouse is pressed / false = mouse was released
        
        
        // LogHelper.trace("  onMouseInputEvent.Post(%s)", event.getGui());
        // LogHelper.trace("    Mouse event: %s, button state: %s", Mouse.getEventButton(), Mouse.getEventButtonState());
        
        
        
        final Minecraft mc = Minecraft.getMinecraft();
        
        int PLAYER_SLOT_INDEX_HELMET     = 39;
        int PLAYER_SLOT_INDEX_CHESTPLATE = 38;
        int PLAYER_SLOT_INDEX_LEGGINGS   = 37;
        int PLAYER_SLOT_INDEX_BOOTS      = 36;
        int PLAYER_SLOT_INDEX_OFFHAND    = 40;
        int HOTBAR_FIRST_SLOT_INDEX      = 0;
        int HELMET_SLOT_Y_POSITION       = (18 * 0);
        int CHESTPLATE_SLOT_Y_POSITION   = (18 * 1);
        int LEGGINGS_SLOT_Y_POSITION     = (18 * 2);
        int BOOTS_SLOT_Y_POSITION        = (18 * 3);
        int OFFHAND_SLOT_Y_POSITION      = 76;

        int xOffset = 14;
        int yOffset = 24;

        
        IInventory playerInventory = mc.player.inventory;
        Slot slot1 = new SlotOffHand(playerInventory, PLAYER_SLOT_INDEX_OFFHAND, xOffset, OFFHAND_SLOT_Y_POSITION + yOffset);
        Slot slot2 = new SlotOffHand(playerInventory, PLAYER_SLOT_INDEX_BOOTS, xOffset, BOOTS_SLOT_Y_POSITION + yOffset);
        Slot slot3 = new SlotOffHand(playerInventory, PLAYER_SLOT_INDEX_LEGGINGS, xOffset, LEGGINGS_SLOT_Y_POSITION + yOffset);
        Slot slot4 = new SlotOffHand(playerInventory, PLAYER_SLOT_INDEX_CHESTPLATE, xOffset, CHESTPLATE_SLOT_Y_POSITION + yOffset);
        Slot slot5 = new SlotOffHand(playerInventory, PLAYER_SLOT_INDEX_HELMET, xOffset, HELMET_SLOT_Y_POSITION + yOffset);
        
        slot1.slotNumber = 45;
        slot2.slotNumber = 8;
        slot3.slotNumber = 7;
        slot4.slotNumber = 6;
        slot5.slotNumber = 5;
        
        List<Slot> extraSlots = Lists.<Slot>newArrayList();
        extraSlots.add(slot1);
        extraSlots.add(slot2);
        extraSlots.add(slot3);
        extraSlots.add(slot4);
        extraSlots.add(slot5);
        
        
        //int i = Mouse.getEventX() * this.width / this.mc.displayWidth;
        //int j = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        int i = Mouse.getEventX() * 427 / mc.displayWidth;
        int j = 240 - Mouse.getEventY() * 240 / mc.displayHeight - 1;
        
        // LogHelper.trace("    Mouse on %d / %d (%d / %d)", Mouse.getX(), Mouse.getY(), i, j);
        for (Slot oneSlot : extraSlots) {
            if (i >= oneSlot.xPos - 1 
                    && i < oneSlot.xPos + 16 + 1
                    && j >= oneSlot.yPos - 1
                    && j < oneSlot.yPos + 16 + 1) {
                // LogHelper.trace("    Mouse over slot %s (#%d)", oneSlot, oneSlot.getSlotIndex());
                
                
                if (oneSlot instanceof SlotOffHand && Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
                    LogHelper.trace("    Sending debug item stack to player...");
                    // ItemStack debugStack = new ItemStack(Items.DIAMOND, 3);
                    
                    
                    // TODO: check if empty hand, etc
                    

                    // Puts item on player mouse
                    mc.player.inventory.setItemStack(oneSlot.getStack());
                    ModVisibleArmorSlots.instance.getNetworkManager().sendPickupFromExtraSlot(oneSlot.getSlotIndex(), oneSlot.slotNumber);

                    // Removes from slot
                    mc.player.inventory.setInventorySlotContents(oneSlot.getSlotIndex(), ItemStack.EMPTY);
                    
                    
                    
                    //mc.playerController.sendSlotPacket(debugStack, oneSlot.getSlotIndex());
                    
                    /*
                    // Puts item on player mouse
                    mc.player.inventory.setItemStack(debugStack.copy());
                    
                    // Puts item on slot
                    mc.player.inventory.markDirty();
                    mc.playerController.sendSlotPacket(debugStack, 36);
                    */
                    
                    
                    //mc.player.inventoryContainer.slotClick(9, 0, ClickType.PICKUP, mc.player);
                    //mc.player.inventoryContainer.detectAndSendChanges();
                    
                    
                    // mc.playerController.sendSlotPacket(debugStack, oneSlot.getSlotIndex());      // Nao funcionou
                    
                    
                    /*
                    mc.player.inventory.setInventorySlotContents(PLAYER_SLOT_INDEX_OFFHAND, ItemStack.EMPTY);
                    mc.player.inventory.setItemStack(debugStack);
                    mc.player.inventoryContainer.detectAndSendChanges();
                    */
                    
                    
                    
                    
                }
                
                break;
            }
        }        
    }
    
    
    
    /**
     * Draws the given slot: any item in it, the slot's background, the hovered highlight, etc.
     */
    private void drawSlot(Slot slotIn, GuiScreen guiIn)
    {
        final Minecraft mc = Minecraft.getMinecraft();
        final RenderItem itemRender = mc.getRenderItem();
        final FontRenderer fontRendererObj = mc.fontRendererObj;
        
        
        int i = slotIn.xPos;
        int j = slotIn.yPos;
        String s = null;
        ItemStack itemstack = slotIn.getStack();

        GlStateManager.enableDepth();
        itemRender.renderItemAndEffectIntoGUI(mc.player, itemstack, i, j);
        itemRender.renderItemOverlayIntoGUI(fontRendererObj, itemstack, i, j, s);

        
        /*
        int i = slotIn.xPos;
        int j = slotIn.yPos;
        ItemStack itemstack = slotIn.getStack();
        boolean flag = false;
        // boolean flag1 = slotIn == this.clickedSlot && !this.draggedStack.isEmpty() && !this.isRightMouseClick;
        boolean flag1 = false;
        

        ItemStack itemstack1 = mc.player.inventory.getItemStack();
        String s = null;
        */

        /*
        if (slotIn == this.clickedSlot && !this.draggedStack.isEmpty() && this.isRightMouseClick && !itemstack.isEmpty())
        {
            itemstack = itemstack.copy();
            itemstack.setCount(itemstack.getCount() / 2);
        }
        else if (this.dragSplitting && this.dragSplittingSlots.contains(slotIn) && !itemstack1.isEmpty())
        {
            if (this.dragSplittingSlots.size() == 1)
            {
                return;
            }

            if (Container.canAddItemToSlot(slotIn, itemstack1, true) && this.inventorySlots.canDragIntoSlot(slotIn))
            {
                itemstack = itemstack1.copy();
                flag = true;
                Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemstack, slotIn.getStack().isEmpty() ? 0 : slotIn.getStack().getCount());
                int k = Math.min(itemstack.getMaxStackSize(), slotIn.getItemStackLimit(itemstack));

                if (itemstack.getCount() > k)
                {
                    s = TextFormatting.YELLOW.toString() + k;
                    itemstack.setCount(k);
                }
            }
            else
            {
                this.dragSplittingSlots.remove(slotIn);
                this.updateDragSplitting();
            }
        }
        */

        /*
        this.zLevel = 100.0F;
        this.itemRender.zLevel = 100.0F;

        if (itemstack.isEmpty() && slotIn.canBeHovered())
        {
            TextureAtlasSprite textureatlassprite = slotIn.getBackgroundSprite();

            if (textureatlassprite != null)
            {
                GlStateManager.disableLighting();
                this.mc.getTextureManager().bindTexture(slotIn.getBackgroundLocation());
                this.drawTexturedModalRect(i, j, textureatlassprite, 16, 16);
                GlStateManager.enableLighting();
                flag1 = true;
            }
        }

        if (!flag1)
        {
            if (flag)
            {
                drawRect(i, j, i + 16, j + 16, -2130706433);
            }

            GlStateManager.enableDepth();
            this.itemRender.renderItemAndEffectIntoGUI(this.mc.player, itemstack, i, j);
            this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, itemstack, i, j, s);
        }

        guiIn.itemRender.zLevel = 0.0F;             // Not Visible
        guiIn.zLevel = 0.0F;                        // Not Visible
        */
    }
    

}
