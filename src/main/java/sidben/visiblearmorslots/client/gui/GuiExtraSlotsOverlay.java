package sidben.visiblearmorslots.client.gui;

import java.util.List;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sidben.visiblearmorslots.client.gui.InfoExtraSlots.EnumSlotType;
import sidben.visiblearmorslots.handler.action.SlotActionManager;
import sidben.visiblearmorslots.handler.action.SlotActionType;
import sidben.visiblearmorslots.inventory.SlotArmor;
import sidben.visiblearmorslots.inventory.SlotOffHand;
import sidben.visiblearmorslots.main.Reference;
import sidben.visiblearmorslots.util.LogHelper;



/**
 * This class simulates a simplified GuiContainer that runs only on the client,
 * since it uses the player inventory.<br/>
 * <br/>
 *
 * Using Forge hooks on {@link sidben.visiblearmorslots.handler.EventDelegatorGuiOverlay EventDelegatorGuiOverlay},
 * this class simulates the regular flow of a GUI.
 *
 */
@SideOnly(Side.CLIENT)
public class GuiExtraSlotsOverlay extends Gui
{

    private static final ResourceLocation GUI_EXTRA_SLOTS = new ResourceLocation(Reference.MOD_ID + ":textures/gui/extra-slots.png");
    public static final int               GUI_WIDTH       = 24;
    public static final int               GUI_HEIGHT      = 100;

    /** holds the slot currently hovered */
    private Slot                          theSlot;
    private int                           eventButton;
    private long                          lastMouseEvent;
    private boolean                       _potionShiftActive;
    private int                           _externalGuiLeft;
    private int                           _externalGuiTop;

    protected List<InfoExtraSlots>        supportedSlotsInfo;
    protected List<Slot>                  extraSlots;
    protected Minecraft                   mc;
    protected RenderItem                  itemRender;
    protected FontRenderer                fontRendererObj;

    public int                            screenWidth;
    public int                            screenHeight;
    public int                            guiLeft;
    public int                            guiTop;



    public GuiExtraSlotsOverlay() {
        supportedSlotsInfo = Lists.<InfoExtraSlots> newArrayList();
        loadSupportedSlotsInfo(supportedSlotsInfo);

        extraSlots = Lists.<Slot> newArrayList();
    }



    // -----------------------------------------------------------
    // Slots info
    // -----------------------------------------------------------

    protected void loadSupportedSlotsInfo(List<InfoExtraSlots> list)
    {
        final int xStart = 4;       // Gui margin
        final int yStart = 4;       // Gui margin

        list.add(new InfoExtraSlots(EnumSlotType.ARMOR, xStart, (18 * 0) + yStart, 39, 5));     // Helmet
        list.add(new InfoExtraSlots(EnumSlotType.ARMOR, xStart, (18 * 1) + yStart, 38, 6));     // Chest
        list.add(new InfoExtraSlots(EnumSlotType.ARMOR, xStart, (18 * 2) + yStart, 37, 7));     // Legs
        list.add(new InfoExtraSlots(EnumSlotType.ARMOR, xStart, (18 * 3) + yStart, 36, 8));     // Boots
        list.add(new InfoExtraSlots(EnumSlotType.OFF_HAND, xStart, 76 + yStart, 40, 45));       // Off-hand
    }



    public void refreshExtraSlotsInfo(InventoryPlayer inventoryplayer)
    {
        extraSlots = Lists.<Slot> newArrayList();

        for (final InfoExtraSlots slotInfo : supportedSlotsInfo) {
            Slot extraSlot = null;

            if (slotInfo.getSlotType() == EnumSlotType.ARMOR) {
                extraSlot = new SlotArmor(inventoryplayer, slotInfo.getInventorySlotIndex(), slotInfo.getX(), slotInfo.getY());
            } else if (slotInfo.getSlotType() == EnumSlotType.OFF_HAND) {
                extraSlot = new SlotOffHand(inventoryplayer, slotInfo.getInventorySlotIndex(), slotInfo.getX(), slotInfo.getY());
            }

            if (extraSlot != null) {
                extraSlot.slotNumber = slotInfo.getContainerSlotIndex();
                extraSlots.add(extraSlot);
            }
        }
    }



    // -----------------------------------------------------------
    // Gui parameters
    // -----------------------------------------------------------

    public void setWorldAndResolution(Minecraft mc, int width, int height)
    {
        this.mc = mc;
        this.itemRender = mc.getRenderItem();
        this.fontRendererObj = mc.fontRendererObj;
        this.screenWidth = width;
        this.screenHeight = height;

        // Reset values that may leak from other gui's
        this.theSlot = null;
    }


    public void setExternalGuiPosition(GuiScreen gui)
    {
        if (gui instanceof GuiContainer) {
            final int candidateGuiLeft = ((GuiContainer) gui).getGuiLeft();
            final int candidateGuiTop = ((GuiContainer) gui).getGuiTop();

            // -- NOTE --
            // The creative inventory would cause this method to be fired twice, once for
            // net.minecraft.client.gui.inventory.GuiContainerCreative (with correct values)
            // and once for net.minecraft.client.gui.inventory.GuiInventory (with wrong values).
            //
            // I ignore the second call so the gui overlay preserves the correct values.
            if (gui instanceof GuiInventory && candidateGuiLeft == 0 && candidateGuiTop == 0) { return; }

            this._externalGuiLeft = candidateGuiLeft;
            this._externalGuiTop = candidateGuiTop;

        } else {
            this._externalGuiLeft = -1;
            this._externalGuiTop = -1;

        }
        
        LogHelper.trace("GuiExtraSlotsOverlay.setExternalGuiPosition() - left: %d, top: %d", this._externalGuiLeft, this._externalGuiTop);
    }

    /*
     * Informs if the potion effects pushed the original gui to the side.
     */
    public boolean isPotionShiftActive()
    {
        return this._potionShiftActive;
    }

    /*
     * Holds the information if the potion effects pushed the original gui to the side.
     */
    public void setPotionShiftState(boolean active)
    {
        this._potionShiftActive = active;
    }



    // -----------------------------------------------------------
    // Gui drawing
    // -----------------------------------------------------------

    /**
     * Draws the extra slots overlay slots and their contents.
     */
    public void drawScreen(int mouseX, int mouseY)
    {
        this.theSlot = null;

        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.guiLeft, this.guiTop, 0.0F);


        // Draw the slots
        for (final Slot oneSlot : extraSlots) {

            // Slot content
            if (oneSlot.canBeHovered()) {
                this.drawSlot(oneSlot);
            }

            // Hover box
            if (this.isMouseOverSlot(oneSlot, mouseX, mouseY) && oneSlot.canBeHovered()) {
                final int hoverX = oneSlot.xPos;
                final int hoverY = oneSlot.yPos;
                this.theSlot = oneSlot;

                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.colorMask(true, true, true, false);
                this.drawGradientRect(hoverX, hoverY, hoverX + 16, hoverY + 16, 0x7FFFFFFF, 0x7FFFFFFF);
                GlStateManager.colorMask(true, true, true, true);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }

        }


        GlStateManager.popMatrix();
        GlStateManager.disableLighting();
    }



    /**
     * Draws the extra slots overlay background.
     */
    public void drawBackground()
    {
        final int textureStartX = 0;
        final int textureStartY = 62;
        final int textureWidth = 24;
        final int textureHeight = 100;
        final int startX = guiLeft;
        final int startY = guiTop;

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_EXTRA_SLOTS);
        this.drawTexturedModalRect(startX, startY, textureStartX, textureStartY, textureWidth, textureHeight);
    }



    /**
     * Draws the extra slots overlay tooltips.
     */
    public void drawForeground(int mouseX, int mouseY)
    {
        final InventoryPlayer inventoryplayer = this.mc.player.inventory;
        final ItemStack playerItemStack = inventoryplayer.getItemStack();


        // Tooltip
        if (playerItemStack.isEmpty() && this.theSlot != null && this.theSlot.getHasStack()) {
            final ItemStack slotStack = this.theSlot.getStack();
            this.renderToolTip(slotStack, mouseX, mouseY);
        }
    }



    private void drawSlot(Slot slotIn)
    {
        final int x = slotIn.xPos;
        final int y = slotIn.yPos;
        final ItemStack itemstack = slotIn.getStack();


        // Slot background
        if (itemstack.isEmpty() && slotIn.canBeHovered()) {
            final TextureAtlasSprite textureatlassprite = slotIn.getBackgroundSprite();

            if (textureatlassprite != null) {
                GlStateManager.disableLighting();
                this.mc.getTextureManager().bindTexture(slotIn.getBackgroundLocation());
                this.drawTexturedModalRect(x, y, textureatlassprite, 16, 16);
                GlStateManager.enableLighting();
            }

        }

        // Slot item
        if (!itemstack.isEmpty()) {
            itemRender.renderItemAndEffectIntoGUI(mc.player, itemstack, x, y);
            itemRender.renderItemOverlayIntoGUI(fontRendererObj, itemstack, x, y, null);
        }


    }



    protected void renderToolTip(ItemStack stack, int x, int y)
    {
        final List<String> list = stack.getTooltip(this.mc.player, this.mc.gameSettings.advancedItemTooltips);

        for (int i = 0; i < list.size(); ++i) {
            if (i == 0) {
                list.set(i, stack.getRarity().rarityColor + list.get(i));
            } else {
                list.set(i, TextFormatting.GRAY + list.get(i));
            }
        }

        FontRenderer font = stack.getItem().getFontRenderer(stack);
        font = (font == null ? fontRendererObj : font);
        net.minecraftforge.fml.client.config.GuiUtils.preItemToolTip(stack);
        net.minecraftforge.fml.client.config.GuiUtils.drawHoveringText(list, x, y, this.screenWidth, this.screenHeight, -1, font);
        net.minecraftforge.fml.client.config.GuiUtils.postItemToolTip();
    }



    // -----------------------------------------------------------
    // Mouse interaction
    // -----------------------------------------------------------

    /**
     * @return Should cancel the mouse event (since it was handled by this gui).
     */
    public boolean handleMouseInput()
    {
        final int relativeMouseX = Mouse.getEventX() * this.screenWidth / this.mc.displayWidth;
        final int relativeMouseY = this.screenHeight - Mouse.getEventY() * this.screenHeight / this.mc.displayHeight - 1;
        final int mouseButton = Mouse.getEventButton();

        if (!this.isMouseOverGui(relativeMouseX, relativeMouseY)) { return false; }


        if (Mouse.getEventButtonState()) {
            this.eventButton = mouseButton;
            this.lastMouseEvent = Minecraft.getSystemTime();
            this.mouseClicked(relativeMouseX, relativeMouseY, this.eventButton);
        } else if (mouseButton != -1) {
            this.eventButton = -1;
            this.mouseReleased(relativeMouseX, relativeMouseY, mouseButton);
        } else if (this.eventButton != -1 && this.lastMouseEvent > 0L) {
            final long l = Minecraft.getSystemTime() - this.lastMouseEvent;
            this.mouseClickMove(relativeMouseX, relativeMouseY, this.eventButton, l);
        }

        // Needed to avoid clicks on the gui overlay being interpreted as clicks outside the open gui,
        // causing the items on the mouse to drop.
        return true;
    }


    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int clickedButton)
    {
        /*
         * About {@link net.minecraft.inventory.ClickTypes ClickTypes}:
         *
         * - PICKUP: Mouse clicked on the slot (left or right button)
         * - PICKUP_ALL: Double-click on the slot, pick all items of the type
         * - CLONE: Middle click, creative only
         * - QUICK_MOVE: Shift + click
         * - SWAP: Swap with hotbar when a number is pressed (can use this.slotUnderTheMouse)
         * - THROW: Click outside the gui
         *
         */

        // LogHelper.trace(" mouseClicked(%d, %d, %d)", mouseX, mouseY, clickedButton);


        final boolean isButtonPickBlock = this.mc.gameSettings.keyBindPickBlock.isActiveAndMatches(clickedButton - 100);
        final Slot slot = this.getSlotAtPosition(mouseX, mouseY, true, false);

        // NOTE: I don't need to handle the ClickType.THROW, the parent gui will take care of it.
        if (slot == null) { return; }



        // TODO: handle dragging (?)
        // TODO: handle ClickType.PICKUP_ALL (?)

        final EntityPlayer player = this.mc.player;
        final SlotActionType.EnumMouseAction slotMouseButton = SlotActionType.EnumMouseAction.create(clickedButton, isButtonPickBlock);
        final SlotActionType slotAction = SlotActionType.create(player, slot, GuiExtraSlotsOverlay.isShiftKeyDown(), slotMouseButton);

        if (slotAction.isValid()) {
            SlotActionManager.instance.processActionOnClient(slotAction, slot, this.mc.player);
        }
    }


    /**
     * Called when a mouse button is pressed and the mouse is moved around. Parameters are : mouseX, mouseY,
     * lastButtonClicked & timeSinceMouseClick.
     */
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        // LogHelper.trace(" mouseClickMove(%d, %d, %d, %d)", mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }


    /**
     * Called when a mouse button is released.
     */
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        // LogHelper.trace(" mouseReleased(%d, %d, %d)", mouseX, mouseY, state);
    }



    // -----------------------------------------------------------
    // Keyboard interaction
    // -----------------------------------------------------------

    /**
     * Handles keyboard input.<br/>
     * <br/>
     *
     * Reference: {@link net.minecraft.client.gui.GuiScreen#handleKeyboardInput() GuiScreen.handleKeyboardInput()}
     */
    public void handleKeyboardInput()
    {
        final char keyChar = Keyboard.getEventCharacter();

        if (Keyboard.getEventKey() == 0 && keyChar >= 32 || Keyboard.getEventKeyState()) {
            this.keyTyped(Keyboard.getEventKey());
        }
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)<br/>
     * <br/>
     *
     * Reference: {@link net.minecraft.client.gui.inventory.GuiContainer#keyTyped() GuiContainer.keyTyped()} and
     * {@link net.minecraft.client.gui.inventory.GuiContainer#checkHotbarKeys() GuiContainer.checkHotbarKeys()}
     */
    protected void keyTyped(int keyCode)
    {
        final EntityPlayer player = this.mc.player;
        Slot slot = this.theSlot;       // Slot under the mouse (if the mouse is inside the gui overlay)
        SlotActionType.EnumKeyboardAction keyboardAction = SlotActionType.EnumKeyboardAction.INVALID;

        // NOTE: KeyBinding.isActiveAndMatches() will check if the gui is active, so it would always return false here.
        if (this.mc.gameSettings.keyBindSwapHands.getKeyCode() == keyCode) {
            if (this.theSlot == null) {
                // The mouse is outside the overlay gui, try to find the slot of the open container
                final int relativeMouseX = Mouse.getEventX() * this.screenWidth / this.mc.displayWidth;
                final int relativeMouseY = this.screenHeight - Mouse.getEventY() * this.screenHeight / this.mc.displayHeight - 1;

                slot = this.getSlotAtPosition(relativeMouseX, relativeMouseY, false, true);
            }
            keyboardAction = SlotActionType.EnumKeyboardAction.SWAP_HANDS;

        } else {
            // test hotbar swap key
            for (int i = 0; i < 9; i++) {
                if (this.mc.gameSettings.keyBindsHotbar[i].isActiveAndMatches(keyCode)) {
                    keyboardAction = SlotActionType.EnumKeyboardAction.createHotbar(i);
                    break;
                }
            }
        }

        final SlotActionType slotAction = SlotActionType.create(player, slot, keyboardAction);

        if (slotAction.isValid()) {
            SlotActionManager.instance.processActionOnClient(slotAction, slot, this.mc.player);
        }
    }



    // -----------------------------------------------------------
    // Utility
    // -----------------------------------------------------------

    /**
     * Returns if the mouse is over this GUI.
     */
    private boolean isMouseOverGui(int mouseX, int mouseY)
    {
        return this.isPointInRegion(0, 0, GUI_WIDTH, GUI_HEIGHT, mouseX, mouseY, false);
    }


    /**
     * Returns whether the mouse is over the given slot (only check the gui overlay).
     */
    private boolean isMouseOverSlot(Slot slot, int mouseX, int mouseY)
    {
        return this.isPointInRegion(slot.xPos, slot.yPos, 16, 16, mouseX, mouseY, false);
    }


    /**
     * Returns whether the mouse is over the given slot (only check the external gui).
     */
    private boolean isMouseOverExternalSlot(Slot slot, int mouseX, int mouseY)
    {
        return this.isPointInRegion(slot.xPos, slot.yPos, 16, 16, mouseX, mouseY, true);
    }


    /**
     * Test if the 2D point is in a rectangle (relative to the GUI). Args : rectX, rectY, rectWidth, rectHeight, pointX,
     * pointY
     */
    protected boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY, boolean lookAtExternalGui)
    {
        if (lookAtExternalGui && this._externalGuiLeft < 0 && this._externalGuiTop < 0) { return false; }
        final int i = lookAtExternalGui ? this._externalGuiLeft : this.guiLeft;
        final int j = lookAtExternalGui ? this._externalGuiTop : this.guiTop;
        pointX = pointX - i;
        pointY = pointY - j;
        return pointX >= rectX - 1 && pointX < rectX + rectWidth + 1 && pointY >= rectY - 1 && pointY < rectY + rectHeight + 1;
    }


    /**
     * Returns the slot at the given coordinates or null if there is none.
     */
    private Slot getSlotAtPosition(int x, int y, boolean checkGuiOverlay, boolean checkExternalGui)
    {
        if (checkGuiOverlay) {
            for (final Slot internalSlot : extraSlots) {
                if (this.isMouseOverSlot(internalSlot, x, y) && internalSlot.canBeHovered()) { return internalSlot; }
            }
        }

        // TODO: try refactor to use getSlotUnderMouse() of the external gui
        if (checkExternalGui && this.mc.player.openContainer != null && this.mc.player.openContainer.inventorySlots.size() > 0) {
            for (final Slot externalSlot : this.mc.player.openContainer.inventorySlots) {
                if (this.isMouseOverExternalSlot(externalSlot, x, y) && externalSlot.canBeHovered()) { return externalSlot; }
            }
        }

        return null;
    }


    /**
     * Returns true if either shift key is down
     */
    public static boolean isShiftKeyDown()
    {
        return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
    }


}
