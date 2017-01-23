package sidben.visiblearmorslots.handler;

public class InfoGuiOverlayDisplayParams
{

    public static InfoGuiOverlayDisplayParams EMPTY = new InfoGuiOverlayDisplayParams(0, 0, false);



    private final int                         _guiLeft;
    private final int                         _guiTop;
    private final boolean                     _shouldDisplay;


    public int getGuiLeft()
    {
        return this._guiLeft;
    }

    public int getGuiTop()
    {
        return this._guiTop;
    }

    public boolean getShouldDisplay()
    {
        return this._shouldDisplay;
    }



    public InfoGuiOverlayDisplayParams(int x, int y, boolean shouldDisplay) {
        this._guiLeft = x;
        this._guiTop = y;
        this._shouldDisplay = shouldDisplay;
    }



    @Override
    public String toString()
    {
        return String.format("Display info - x: %d, y: %d, visible: %s", this.getGuiLeft(), this.getGuiTop(), this.getShouldDisplay());
    }
}
