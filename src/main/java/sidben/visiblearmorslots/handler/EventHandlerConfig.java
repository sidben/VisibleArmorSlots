package sidben.visiblearmorslots.handler;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sidben.visiblearmorslots.main.ModConfig;
import sidben.visiblearmorslots.main.Reference;

public class EventHandlerConfig
{

    @SubscribeEvent
    public static void onConfigurationChangedEvent(OnConfigChangedEvent event)
    {
        if (event.getModID().equalsIgnoreCase(Reference.ModID)) {
            // Resync config
            ModConfig.refreshConfig();
            ModConfig.updateBlacklistedMods();
        }
    }
    
}
