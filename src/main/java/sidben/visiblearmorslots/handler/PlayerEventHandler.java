package sidben.visiblearmorslots.handler;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.helper.LogHelper;



public class PlayerEventHandler
{


    @SubscribeEvent
    public void onPlayerOpenContainerEvent(PlayerContainerEvent event)
    {
        // OBS: Runs server-side
        // NOTE - Gets called constantly while the container is open


        final Container openedContainer = event.getEntityPlayer().openContainer;
        final IInventory playerInventory = event.getEntityPlayer().inventory;


        if (ModVisibleArmorSlots.extraSlotsHelper.shouldAddExtraSlotsToContainer(openedContainer)) {
            LogHelper.info("Adding extra slots server-side.");
            ModVisibleArmorSlots.extraSlotsHelper.addExtraSlotsToContainer(openedContainer, playerInventory);
        }

    }



}