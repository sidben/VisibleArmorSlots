package sidben.visiblearmorslots.proxy;

import sidben.visiblearmorslots.network.NetworkManager;


/*
 * Base proxy class, here I initialize everything that must happen on both, server and client.
 */
public abstract class ProxyCommon implements IProxy
{


    @Override
    public void pre_initialize()
    {
        // Register network messages
        NetworkManager.registerMessages();
    }


    @Override
    public void initialize()
    {
        // Event Handlers
    }


    @Override
    public void post_initialize()
    {
    }


}
