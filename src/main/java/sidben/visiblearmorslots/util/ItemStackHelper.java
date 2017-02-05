package sidben.visiblearmorslots.util;

import net.minecraft.item.ItemStack;


public class ItemStackHelper
{

    private ItemStackHelper() 
    {
    }



    public static boolean areStacksCompatible(ItemStack targetStack, ItemStack candidateStack)
    {
        final boolean stacksCompatible = ItemStack.areItemsEqual(targetStack, candidateStack) 
                && targetStack.getMetadata() == candidateStack.getMetadata()
                && ItemStack.areItemStackTagsEqual(targetStack, candidateStack);

        return stacksCompatible;
    }

    
    public static boolean isStackFull(ItemStack targetStack)
    {
        final boolean stackFull = !(targetStack.isStackable() && targetStack.getCount() < targetStack.getMaxStackSize());
        return stackFull;
    }
    
}
