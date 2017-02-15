package sidben.visiblearmorslots;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sidben.visiblearmorslots.handler.EventHandlerConfig;
import sidben.visiblearmorslots.main.ModConfig;
import sidben.visiblearmorslots.main.Reference;
import sidben.visiblearmorslots.proxy.IProxy;


@Mod(modid = Reference.ModID, name = Reference.ModName, version = Reference.ModVersion, guiFactory = Reference.GuiFactoryClass)
public class ModVisibleArmorSlots
{

    @Mod.Instance(Reference.ModID)
    public static ModVisibleArmorSlots instance;

    @SidedProxy(clientSide = Reference.ClientProxyClass, serverSide = Reference.ServerProxyClass)
    public static IProxy               proxy;



    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // Loads config
        ModConfig.init(event.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(EventHandlerConfig.class);

        // Sided pre-initialization
        proxy.pre_initialize();
    }


    @Mod.EventHandler
    public void load(FMLInitializationEvent event)
    {
        // Sided initializations
        proxy.initialize();
    }


    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        // Sided post-initialization
        proxy.post_initialize();

        ModConfig.updateBlacklistedMods();
    }



}
