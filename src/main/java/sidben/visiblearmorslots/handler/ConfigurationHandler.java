package sidben.visiblearmorslots.handler;

import java.io.File;
import net.minecraftforge.common.config.Configuration;
import sidben.visiblearmorslots.reference.Reference;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class ConfigurationHandler
{
    public static final String  CATEGORY_DEBUG             = "debug";

    public static boolean       overrideEnchantTable       = true;
    public static boolean       overrideAnvil       	   = true;

    // Instance
    public static Configuration config;



    public static void init(File configFile)
    {

        // Create configuration object from config file
        if (config == null) {
            config = new Configuration(configFile);
            loadConfig();
        }

    }



    private static void loadConfig()
    {

        // Load properties - debug
    	overrideEnchantTable = config.getBoolean("enabled_on_enchanting", Configuration.CATEGORY_GENERAL, true, "");
    	overrideAnvil = config.getBoolean("enabled_on_anvil", Configuration.CATEGORY_GENERAL, true, "");

        // saving the configuration to its file
        if (config.hasChanged()) {
            config.save();
        }
    }



    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equalsIgnoreCase(Reference.ModID)) {
            // Resync config
            loadConfig();
        }
    }

}
