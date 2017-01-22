package sidben.visiblearmorslots.client.gui;

import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sidben.visiblearmorslots.helper.LogHelper;
import sidben.visiblearmorslots.reference.Reference;


@SideOnly(Side.CLIENT)
public class GuiExtraSlots extends GuiScreen
{

    private static final ResourceLocation GUI_EXTRA_SLOTS         = new ResourceLocation(Reference.ModID + ":textures/gui/extra-slots.png");
    private static final int              GUI_EXTRA_SLOTS_START_X = 0;
    private static final int              GUI_EXTRA_SLOTS_START_Y = 62;
    private static final int              GUI_EXTRA_SLOTS_WIDTH   = 24;
    private static final int              GUI_EXTRA_SLOTS_HEIGHT  = 100;

    protected int xSize = 24;
    protected int ySize = 100;
    protected int guiLeft;
    protected int guiTop;
    
    private final GuiContainer _originalWrappedGui;

    
    
    public GuiExtraSlots(EntityPlayer player, GuiContainer originalGui) {
        this.allowUserInput = true;
        this._originalWrappedGui = originalGui;
        
        LogHelper.trace(" *** GuiExtraSlots(%s, %s)", player, originalGui);
    }
    
    
    

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        super.initGui();
        // this.guiLeft = (this.width - this.xSize) / 2;
        // this.guiTop = (this.height - this.ySize) / 2;
        
        
        LogHelper.trace(" *** initGui...");
        
        
        this.guiLeft = 10;
        this.guiTop = 30;
    }

    
    
    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        int i = this.guiLeft;
        int j = this.guiTop;

        /*
        this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        */

        
        // LogHelper.trace(" *** drawScreen...");
        
        
        // Draws the extra armor slots background
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_EXTRA_SLOTS);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, GUI_EXTRA_SLOTS_START_X, GUI_EXTRA_SLOTS_START_Y, GUI_EXTRA_SLOTS_WIDTH, GUI_EXTRA_SLOTS_HEIGHT);
        
        
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
 
    
    
    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    
    /**
     * Called when a mouse button is pressed and the mouse is moved around. Parameters are : mouseX, mouseY,
     * lastButtonClicked & timeSinceMouseClick.
     */
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
    }

    
    
    
    
    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    
    
    
    
    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        LogHelper.trace(" *** onGuiClosed...");
        
        
        if (this.mc.player != null)
        {
            // this.inventorySlots.onContainerClosed(this.mc.player);
        }
    }
    
    
    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        
        
        // LogHelper.trace(" *** updateScreen...");
        
        

        if (!this.mc.player.isEntityAlive() || this.mc.player.isDead)
        {
            this.mc.player.closeScreen();
        }
    }

    
}
