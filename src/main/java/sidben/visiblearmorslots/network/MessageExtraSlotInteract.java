package sidben.visiblearmorslots.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import sidben.visiblearmorslots.helper.LogHelper;


public class MessageExtraSlotInteract implements IMessage
{
    private int _playerInventorySlotIndex = -1;
    private int _playerContainerSlotIndex = -1;


    public MessageExtraSlotInteract() {
    }

    public MessageExtraSlotInteract(int inventorySlotIndex, int containerSlotIndex) {
        this._playerContainerSlotIndex = containerSlotIndex;
        this._playerInventorySlotIndex = inventorySlotIndex;
    }

    
    
    
    
    public int getInventorySlotIndex() {
        return this._playerInventorySlotIndex;
    }
    
    public int getContainerSlotIndex() {
        return this._playerContainerSlotIndex;
    }
    
    
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
        this._playerContainerSlotIndex = buf.readInt();
        this._playerInventorySlotIndex = buf.readInt();
    }
    

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this._playerContainerSlotIndex);
        buf.writeInt(this._playerInventorySlotIndex);
    }

    
    @Override
    public String toString()
    {
        return String.format("Extra slot index %d (container index %d)", this.getInventorySlotIndex(), this.getContainerSlotIndex());
    }


    
    
    public static class PickupHandler implements IMessageHandler<MessageExtraSlotInteract, IMessage>
    {

        @Override
        public IMessage onMessage(MessageExtraSlotInteract message, MessageContext ctx)
        {
            LogHelper.trace("Handling MessageExtraSlotInteract.PickupHandler (%s)", message);
            EntityPlayer player = ctx.getServerHandler().playerEntity;

            if (player != null) {
                LogHelper.trace("    current stack (1) %s", player.inventory.getItemStack());
                LogHelper.trace("    stack on slot %d = %s", message.getInventorySlotIndex(), player.inventory.getStackInSlot(message.getInventorySlotIndex()));


                Slot targetSlot = player.inventoryContainer.getSlot(message.getContainerSlotIndex());
                
                if (targetSlot != null) {
                    LogHelper.trace("        target slot index %d, number %d, content %s, can take [%s]", targetSlot.getSlotIndex(), targetSlot.slotNumber, targetSlot.getStack(), targetSlot.canTakeStack(player));
                    
                    ItemStack targetSlotItem = targetSlot.getStack();
                    ItemStack currentItemPicked = player.inventory.getItemStack();
                    
                    if (currentItemPicked.isEmpty()) {
                        if (targetSlot.canTakeStack(player)) {
                            LogHelper.trace("    Taking item from slot");
                            
                            player.inventory.setItemStack(targetSlotItem);
                            
                            targetSlot.putStack(ItemStack.EMPTY);
                            targetSlot.onTake(player, currentItemPicked);
                        }
                    }
                    
                    targetSlot.onSlotChanged();
                }
                

                
                LogHelper.trace("    current stack (2) %s", player.inventory.getItemStack());

                
            }
            
            return null;
        }

    }

    
    /*
    public static class PlaceOnHandler implements IMessageHandler<MessageExtraSlotInteract, IMessage>
    {

        @Override
        public IMessage onMessage(MessageExtraSlotInteract message, MessageContext ctx)
        {
            LogHelper.trace("Handling MessageExtraSlotInteract.PlaceOnHandler");
            EntityPlayer player = ctx.getServerHandler().playerEntity;

            LogHelper.trace("    %s", message);
            LogHelper.trace("    player %s", player);
            if (player != null) {
                LogHelper.trace("    current stack %s", player.inventory.getItemStack());
                LogHelper.trace("    stack on slot %d = %s", message.getSlotIndex(), player.inventory.getStackInSlot(message.getSlotIndex()));
            }
            
            return null;
        }

    }
    */

    
}