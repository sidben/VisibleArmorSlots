package sidben.visiblearmorslots.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import sidben.visiblearmorslots.helper.LogHelper;


public class NetworkManager
{

    private static final String  MOD_CHANNEL = "CH_VSA";
    private SimpleNetworkWrapper _networkWrapper;



    public void registerMessages()
    {
        this._networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_CHANNEL);

        int packetdId = 0;
        this._networkWrapper.registerMessage(MessageExtraSlotInteract.Handler.class, MessageExtraSlotInteract.class, packetdId++, Side.SERVER);
    }



    public void sendPickupFromExtraSlot(int inventorySlotIndex, int containerSlotIndex)
    {
        final MessageExtraSlotInteract message = new MessageExtraSlotInteract(inventorySlotIndex, containerSlotIndex);
        LogHelper.trace("Sending MessagePickupFromExtraSlots(%d, %d)", inventorySlotIndex, containerSlotIndex);

        this._networkWrapper.sendToServer(message);
    }


}
