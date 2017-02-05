package sidben.visiblearmorslots.network;

import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import sidben.visiblearmorslots.helper.LogHelper;


public class NetworkManager
{

    private static final String  MOD_CHANNEL = "ch_sidben_vsa";
    private SimpleNetworkWrapper _networkWrapper;



    public void registerMessages()
    {
        this._networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_CHANNEL);

        int packetdId = 0;
        this._networkWrapper.registerMessage(MessageSlotAction.Handler.class, MessageSlotAction.class, packetdId++, Side.SERVER);
    }



    public void sendSlotActionToServer(Integer resolverIndex, Slot targetSlot)
    {
        final MessageSlotAction message = new MessageSlotAction(resolverIndex, targetSlot);
        LogHelper.trace("NetworkManager.send - %s", message);

        this._networkWrapper.sendToServer(message);
    }


}
