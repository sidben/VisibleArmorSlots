package sidben.visiblearmorslots.proxy;

import net.minecraftforge.common.MinecraftForge;
import sidben.visiblearmorslots.handler.EventDelegatorGuiOverlay;


public class ProxyClient extends ProxyCommon
{

    @Override
    public void initialize()
    {
        super.initialize();

        // Event Handlers
        MinecraftForge.EVENT_BUS.register(new EventDelegatorGuiOverlay());
    }

}
