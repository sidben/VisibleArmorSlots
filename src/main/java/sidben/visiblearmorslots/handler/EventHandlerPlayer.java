package sidben.visiblearmorslots.handler;

import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


@Deprecated
public class EventHandlerPlayer
{


    @SubscribeEvent
    public void onPlayerContainerEvent(PlayerContainerEvent event)
    {
        // LogHelper.trace(" onPlayerContainerEvent(%s)", event.getContainer());
    }


    @SubscribeEvent
    public void onPlayerOpenContainerEvent(PlayerContainerEvent.Open event)
    {
        // LogHelper.trace(" onPlayerOpenContainerEvent(%s)", event.getContainer());
    }


    @SubscribeEvent
    public void onPlayerCloseContainerEvent(PlayerContainerEvent.Close event)
    {
        // LogHelper.trace(" onPlayerCloseContainerEvent(%s)", event.getContainer());
    }


}