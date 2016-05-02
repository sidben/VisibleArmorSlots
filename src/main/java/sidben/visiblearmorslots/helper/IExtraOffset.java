package sidben.visiblearmorslots.helper;


/*
 * Allows GUIs/Containers that doesn't follow the default size to correctly position the extra armor slots.
 */
public interface IExtraOffset
{

    public int getXOffset();        // TODO: refactor to rightSideOffset? This is only useful for the right positioned config.
    public int getYOffset();

}
