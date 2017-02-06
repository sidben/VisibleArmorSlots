package sidben.visiblearmorslots.handler.action;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.helper.LogHelper;


/**
 * Works like an event handler for slot actions.
 */
public class SlotActionManager
{

    private final Map<Integer, ISlotActionResolver> _actionResolvers = new HashMap<Integer, ISlotActionResolver>();


    public static SlotActionManager                 instance         = new SlotActionManager();


    private SlotActionManager() {
        int index = 0;

        // NOTE: The order is essential
        _actionResolvers.put(index++, new SlotActionResolver_Debug());
        _actionResolvers.put(index++, new SlotActionResolver_Clone());
        _actionResolvers.put(index++, new SlotActionResolver_TryPlacingOneItemOnSlot());
        _actionResolvers.put(index++, new SlotActionResolver_QuickTakeFromSlot());
        _actionResolvers.put(index++, new SlotActionResolver_TakeHalfStack());
        _actionResolvers.put(index++, new SlotActionResolver_TrySwapMouseWithSlot());
        _actionResolvers.put(index++, new SlotActionResolver_DoesNothing());
    }



    private Map.Entry<Integer, ISlotActionResolver> getResolverForAction(SlotActionType actionType)
    {
        // for (ISlotActionResolver actionResolver : this._actionResolvers.)
        for (final Map.Entry<Integer, ISlotActionResolver> entry : this._actionResolvers.entrySet()) {
            if (entry.getValue().isSatisfiedBy(actionType)) {
                LogHelper.trace("SlotActionManager: Using [%s], index %d, to resolve %s", entry.getValue(), entry.getKey(), actionType);
                return entry;
            }
        }

        return null;
    }

    private ISlotActionResolver getResolverByIndex(int actionResolverIndex)
    {
        final ISlotActionResolver actionResolver = this._actionResolvers.getOrDefault(actionResolverIndex, new SlotActionResolver_DoesNothing());
        LogHelper.trace("SlotActionManager: Using [%s] to resolve index %d", actionResolver, actionResolverIndex);
        return actionResolver;
    }



    /**
     * Process what should happen on the server.
     */
    public void processActionOnServer(int actionResolverIndex, Slot targetSlot, EntityPlayer player)
    {
        if (targetSlot == null || player == null || player.isSpectator()) { return; }
        final ISlotActionResolver actionResolver = this.getResolverByIndex(actionResolverIndex);
        if (actionResolver != null) {
            actionResolver.handleServerSide(targetSlot, player);
        }
    }


    /**
     * Process what should happen on the client.
     */
    public void processActionOnClient(SlotActionType actionType, Slot targetSlot, EntityPlayer player)
    {
        if (actionType == null || targetSlot == null || player == null || actionType == SlotActionType.EMPTY || player.isSpectator()) { return; }

        final Map.Entry<Integer, ISlotActionResolver> resolverEntry = this.getResolverForAction(actionType);
        if (resolverEntry != null) {
            final ISlotActionResolver actionResolver = resolverEntry.getValue();
            final boolean isPlayerOnCreativeInventory = player.openContainer.getClass().getName().contains("ContainerCreative");
            
            actionResolver.handleClientSide(targetSlot, player);

            // NOTE: the creative mode player inventory must be client-side only
            if (isPlayerOnCreativeInventory) {
                player.inventoryContainer.detectAndSendChanges();
            } else if (actionResolver.requiresServerSideHandling()) {
                ModVisibleArmorSlots.instance.getNetworkManager().sendSlotActionToServer(resolverEntry.getKey(), targetSlot);
            }
        }
    }


}
