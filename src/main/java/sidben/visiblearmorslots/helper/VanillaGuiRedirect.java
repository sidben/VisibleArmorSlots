package sidben.visiblearmorslots.helper;

import net.minecraft.block.Block;



public class VanillaGuiRedirect
{

    private final Block   _theBlock;
    private final int     _theNewGuiId;
    private final boolean _enabled;


    public boolean compareBlock(Block targetBlock)
    {
        return !this._enabled ? false : (targetBlock == null ? false : this._theBlock.equals(targetBlock));
    }

    public int getRedirectGuiId()
    {
        return this._theNewGuiId;
    }



    public VanillaGuiRedirect(Block blockToBeChecked, int guiToBeUsed, boolean shouldOverride) {
        this._theBlock = blockToBeChecked;
        this._theNewGuiId = guiToBeUsed;
        this._enabled = shouldOverride;
    }

}
