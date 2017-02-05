package sidben.visiblearmorslots.proxy;

import net.minecraftforge.common.MinecraftForge;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.config.ConfigurationHandler;
import sidben.visiblearmorslots.handler.EventHandlerPlayer;


/*
 * Base proxy class, here I initialize everything that must happen on both, server and client.
 */
public abstract class ProxyCommon implements IProxy
{


    @Override
    public void pre_initialize()
    {
        // Register network messages
        ModVisibleArmorSlots.instance.getNetworkManager().registerMessages();
    }


    @Override
    public void initialize()
    {
        // Event Handlers
        MinecraftForge.EVENT_BUS.register(new EventHandlerPlayer());
    }


    @Override
    public void post_initialize()
    {
        ConfigurationHandler.updateBlacklistedMods();
    }


}
