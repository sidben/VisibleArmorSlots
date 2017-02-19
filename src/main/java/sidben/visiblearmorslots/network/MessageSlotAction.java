package sidben.visiblearmorslots.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import sidben.visiblearmorslots.handler.action.SlotActionManager;
import sidben.visiblearmorslots.inventory.SlotArmor;
import sidben.visiblearmorslots.inventory.SlotOffHand;
import sidben.visiblearmorslots.util.LogHelper;


public class MessageSlotAction implements IMessage
{

    // ---------------------------------------------
    // Fields
    // ---------------------------------------------

    private int     _actionResolverIndex      = 0;
    private int     _playerContainerSlotIndex = -1;
    private boolean _usePlayerInventory       = true;



    // ---------------------------------------------
    // Constructors
    // ---------------------------------------------

    public MessageSlotAction() {
    }

    public MessageSlotAction(Integer resolverIndex, Slot targetSlot) {
        this._playerContainerSlotIndex = targetSlot.slotNumber;
        this._actionResolverIndex = resolverIndex;
        this._usePlayerInventory = targetSlot instanceof SlotArmor || targetSlot instanceof SlotOffHand;

        LogHelper.debug("MessageSlotAction() - number: %d, index: %d, inventory: %s", targetSlot.slotNumber, targetSlot.getSlotIndex(), targetSlot.inventory);
    }



    // ---------------------------------------------
    // Properties
    // ---------------------------------------------

    public int getContainerSlotIndex()
    {
        return this._playerContainerSlotIndex;
    }

    public int getActionResolverIndex()
    {
        return this._actionResolverIndex;
    }

    /**
     * If TRUE, the container slot index must be used with player.inventoryContainer, because
     * the slot is not part of the open container.<br/>
     * <br/>
     *
     * If FALSE, the container slot index must be used with player.openContainer.
     */
    public boolean getUsePlayerInventory()
    {
        return this._usePlayerInventory;
    }



    // ---------------------------------------------
    // Methods
    // ---------------------------------------------

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this._playerContainerSlotIndex = buf.readInt();
        this._actionResolverIndex = buf.readInt();
        this._usePlayerInventory = buf.readBoolean();
    }


    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this._playerContainerSlotIndex);
        buf.writeInt(this._actionResolverIndex);
        buf.writeBoolean(this._usePlayerInventory);
    }


    @Override
    public String toString()
    {
        return String.format("MessageSlotAction [slot index=%d, resolver index=%d, use player inventory=%s]", this.getContainerSlotIndex(), this.getActionResolverIndex(),
                this.getUsePlayerInventory());
    }



    public static class Handler implements IMessageHandler<MessageSlotAction, IMessage>
    {

        @Override
        public IMessage onMessage(MessageSlotAction message, MessageContext ctx)
        {
            LogHelper.trace("IMessage.onMessage - %s", message);

            final EntityPlayer player = ctx.getServerHandler().playerEntity;
            if (player == null) { return null; }
            player.getServer().addScheduledTask(() -> handle(message, ctx));

            return null;
        }


        private void handle(MessageSlotAction message, MessageContext ctx)
        {
            LogHelper.trace("IMessage.handle");

            final EntityPlayer player = ctx.getServerHandler().playerEntity;
            final int actionIndex = message.getActionResolverIndex();
            Slot targetSlot = null;

            // NOTE: The extra slots are not part of the openContainer, so I need to
            // find them directly on the player inventory.
            try {
                if (message.getUsePlayerInventory()) {
                    targetSlot = player.inventoryContainer.getSlot(message.getContainerSlotIndex());
                } else {
                    if (player.openContainer != null) {
                        targetSlot = player.openContainer.getSlot(message.getContainerSlotIndex());
                    }
                }
            } catch (final Exception e1) {
                LogHelper.error("ERROR - could not find target slot: %s", e1);
                targetSlot = null;
            }

            if (player == null || targetSlot == null) { return; }


            SlotActionManager.instance.processActionOnServer(actionIndex, targetSlot, player);
        }


    }

}
