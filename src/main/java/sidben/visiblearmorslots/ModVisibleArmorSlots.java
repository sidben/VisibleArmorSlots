package sidben.visiblearmorslots;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import sidben.visiblearmorslots.handler.ConfigurationHandler;
import sidben.visiblearmorslots.helper.ExtraSlotsHelperCommon;
import sidben.visiblearmorslots.proxy.IProxy;
import sidben.visiblearmorslots.reference.Reference;


@Mod(modid = Reference.ModID, name = Reference.ModName, version = Reference.ModVersion, guiFactory = Reference.GuiFactoryClass)
public class ModVisibleArmorSlots
{


    // The instance of your mod that Forge uses.
    @Mod.Instance(Reference.ModID)
    public static ModVisibleArmorSlots   instance;


    @SidedProxy(clientSide = Reference.ClientProxyClass, serverSide = Reference.ServerProxyClass)
    public static IProxy                 proxy;

    @SidedProxy(clientSide = Reference.ClientSlotHelperClass, serverSide = Reference.ServerSlotHelperClass)
    public static ExtraSlotsHelperCommon extraSlotsHelper;





    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // Loads config
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());

        // Sided pre-initialization
        proxy.pre_initialize();
    }


    @Mod.EventHandler
    public void load(FMLInitializationEvent event)
    {
        // GUIs
        NetworkRegistry.INSTANCE.registerGuiHandler(this, ModVisibleArmorSlots.proxy);

        // Sided initializations
        proxy.initialize();
    }


    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        // Sided post-initialization
        proxy.post_initialize();
    }



}
