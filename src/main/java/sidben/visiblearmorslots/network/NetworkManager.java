package sidben.visiblearmorslots.network;

import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import sidben.visiblearmorslots.util.LogHelper;


public class NetworkManager
{

    private static final String         MOD_CHANNEL = "ch_sidben_vsa";
    private static int                  packetdId   = 0;
    private static SimpleNetworkWrapper _networkWrapper;



    public static void registerMessages()
    {
        _networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_CHANNEL);

        _networkWrapper.registerMessage(MessageSlotAction.Handler.class, MessageSlotAction.class, packetdId++, Side.SERVER);
    }



    public static void sendSlotActionToServer(Integer resolverIndex, Slot targetSlot)
    {
        final MessageSlotAction message = new MessageSlotAction(resolverIndex, targetSlot);
        LogHelper.trace("NetworkManager.send - %s", message);

        _networkWrapper.sendToServer(message);
    }


}
