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



    @Deprecated     // TODO: check if can be removed, the message can work with just the container index
    public int getInventorySlotIndex()
    {
        return this._playerInventorySlotIndex;
    }

    public int getContainerSlotIndex()
    {
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
            final EntityPlayer player = ctx.getServerHandler().playerEntity;
            if (player == null) { return null; }

            final Slot targetSlot = player.inventoryContainer.getSlot(message.getContainerSlotIndex());
            if (targetSlot == null) { return null; }

            final ItemStack targetSlotItem = targetSlot.getStack();
            final ItemStack currentItemPicked = player.inventory.getItemStack();


            LogHelper.trace("    (1) current stack on mouse %s", player.inventory.getItemStack());
            LogHelper.trace("    (1) stack on player inventory slot %d = %s", message.getInventorySlotIndex(), player.inventory.getStackInSlot(message.getInventorySlotIndex()));
            LogHelper.trace("    (1) player container slot index %d, number %d, content %s, can take [%s]", targetSlot.getSlotIndex(), targetSlot.slotNumber, targetSlot.getStack(),
                    targetSlot.canTakeStack(player));



            if (currentItemPicked.isEmpty()) {
                if (targetSlot.canTakeStack(player)) {
                    LogHelper.trace("  Taking item from slot");

                    player.inventory.setItemStack(targetSlotItem);
                    targetSlot.putStack(ItemStack.EMPTY);
                    // targetSlot.onTake(player, targetSlotItem);
                }
            } else {
                if (targetSlot.isItemValid(currentItemPicked)) {
                    LogHelper.trace("  Placing item on slot");

                    player.inventory.setItemStack(targetSlotItem);
                    targetSlot.putStack(currentItemPicked);
                }
            }

            targetSlot.onSlotChanged();



            LogHelper.trace("    (2) current stack on mouse %s", player.inventory.getItemStack());
            LogHelper.trace("    (2) stack on player inventory slot %d = %s", message.getInventorySlotIndex(), player.inventory.getStackInSlot(message.getInventorySlotIndex()));
            LogHelper.trace("    (2) player container slot index %d, number %d, content %s, can take [%s]", targetSlot.getSlotIndex(), targetSlot.slotNumber, targetSlot.getStack(),
                    targetSlot.canTakeStack(player));
            return null;
        }

    }



}