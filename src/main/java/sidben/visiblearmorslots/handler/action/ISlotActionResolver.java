package sidben.visiblearmorslots.handler.action;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;


public interface ISlotActionResolver
{
    public void handleClientSide(Slot targetSlot, EntityPlayer player);

    public void handleServerSide(Slot targetSlot, EntityPlayer player);

    public boolean requiresServerSideHandling();

    public boolean isSatisfiedBy(SlotActionType action);
}
