package sidben.visiblearmorslots.handler.action;

public interface ISlotActionResolver
{
    public void handleClientSide();
    public void handleServerSide();
    public boolean isSatisfiedBy(SlotActionType action);
}
