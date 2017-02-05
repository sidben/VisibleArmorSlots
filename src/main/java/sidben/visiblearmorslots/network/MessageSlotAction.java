package sidben.visiblearmorslots.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import sidben.visiblearmorslots.handler.action.SlotActionManager;
import sidben.visiblearmorslots.handler.action.SlotActionType;
import sidben.visiblearmorslots.helper.LogHelper;


public class MessageSlotAction implements IMessage
{

    private int _actionResolverIndex = 0;
    private int _playerContainerSlotIndex = -1;
    
    
    public MessageSlotAction() {}
    
    public MessageSlotAction(Integer resolverIndex, Slot targetSlot) {
        this._playerContainerSlotIndex = targetSlot.slotNumber;
        this._actionResolverIndex = resolverIndex;
    }
    
    
    
    public int getContainerSlotIndex()
    {
        return this._playerContainerSlotIndex;
    }

    public int getActionResolverIndex()
    {
        return this._actionResolverIndex;
    }
    
    
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
        this._playerContainerSlotIndex = buf.readInt();
        this._actionResolverIndex = buf.readInt();
    }


    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this._playerContainerSlotIndex);
        buf.writeInt(this._actionResolverIndex);
    }
    
    
    @Override
    public String toString()
    {
        return String.format("MessageSlotAction [slot index=%d, resolver index=%d]", this.getContainerSlotIndex(), this.getActionResolverIndex());
    }

    
    
    public static class Handler implements IMessageHandler<MessageSlotAction, IMessage>
    {

        @Override
        public IMessage onMessage(MessageSlotAction message, MessageContext ctx)
        {
            LogHelper.trace("IMessage.handle - %s", message);
            
            final EntityPlayer player = ctx.getServerHandler().playerEntity;
            final Slot targetSlot = player.inventoryContainer.getSlot(message.getContainerSlotIndex());

            if (player == null || targetSlot == null) { return null; }
            
            
            int actionIndex = message.getActionResolverIndex();
            
            //SlotActionManager.instance.processActionOnServer(actionIndex, targetSlot, player);

            try {
                player.getServer().addScheduledTask(new Runnable() 
                {
                    @Override
                    public void run()
                    {
                        SlotActionManager.instance.processActionOnServer(actionIndex, targetSlot, player);
                    }
                });

            } catch (Exception e) {
                System.out.println(e);
                
            }


            
            return null;
        }
        
    }
    
}
