package sidben.visiblearmorslots.proxy;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;


public interface IProxy extends IGuiHandler
{

    public void pre_initialize();

    public void initialize();

    public void post_initialize();

}
