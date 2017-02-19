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


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, guiFactory = Reference.GUI_FACTORY_CLASS, updateJSON = Reference.UPDATE_JSON_URL)
public class ModVisibleArmorSlots
{

    @Mod.Instance(Reference.MOD_ID)
    public static ModVisibleArmorSlots INSTANCE;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy               PROXY;



    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // Loads config
        ModConfig.init(event.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(EventHandlerConfig.class);

        // Sided pre-initialization
        PROXY.pre_initialize();
    }


    @Mod.EventHandler
    public void load(FMLInitializationEvent event)
    {
        // Sided initializations
        PROXY.initialize();
    }


    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        // Sided post-initialization
        PROXY.post_initialize();

        ModConfig.updateBlacklistedMods();
    }



}
