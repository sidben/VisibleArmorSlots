package sidben.visiblearmorslots.handler;

import java.io.File;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.reference.Reference;


public class ConfigurationHandler
{
    public static final String[]       SLOT_SIDES           = new String[] { "LEFT", "RIGHT" };

    public static final String         CATEGORY_DEBUG       = "debug";

    public static String               extraSlotsSide;
    public static int                  extraSlotsMargin;



    // Instance
    public static Configuration        config;



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
        extraSlotsSide = config.getString("slots_side", Configuration.CATEGORY_GENERAL, SLOT_SIDES[0], "", SLOT_SIDES);
        extraSlotsMargin = config.getInt("slots_margin", Configuration.CATEGORY_GENERAL, 2, 0, 128, "");

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
