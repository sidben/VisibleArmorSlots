package sidben.visiblearmorslots.helper;

import net.minecraft.block.Block;



public class VanillaGuiRedirect
{

    private final Block _theBlock;
    private final int   _theNewGuiId;


    public boolean compareBlock(Block targetBlock)
    {
        return targetBlock == null ? false : this._theBlock.equals(targetBlock);
    }

    public int getRedirectGuiId()
    {
        return this._theNewGuiId;
    }



    public VanillaGuiRedirect(Block blockToBeChecked, int guiToBeUsed) {
        this._theBlock = blockToBeChecked;
        this._theNewGuiId = guiToBeUsed;
    }

}
