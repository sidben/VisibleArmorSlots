package sidben.visiblearmorslots.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sidben.visiblearmorslots.helper.LogHelper;
import sidben.visiblearmorslots.reference.Reference;


public class ConfigurationHandler
{
    public static final String  POSITION_LEFT       = "LEFT";
    public static final String  POSITION_RIGHT      = "RIGHT";
    public static final String  CATEGORY_DEBUG      = "debug";
    public static final String  MINECRAFT_NAMESPACE = "net.minecraft";

    public static boolean       onDebug;                                                                                   // TODO: read-only accessors
    public static String        extraSlotsSide;
    public static int           extraSlotsMargin;
    public static String[]      blacklistedModIds;
    public static String[]      blacklistedModPackages;



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
        final String[] slotSidesValidEntries = new String[] { POSITION_LEFT, POSITION_RIGHT };

        // Load properties
        onDebug = config.getBoolean("on_debug", CATEGORY_DEBUG, false, "");
        extraSlotsSide = config.getString("slots_side", Configuration.CATEGORY_GENERAL, POSITION_LEFT, "", slotSidesValidEntries);
        extraSlotsMargin = config.getInt("slots_margin", Configuration.CATEGORY_GENERAL, 2, 0, 128, "");
        blacklistedModIds = config.getStringList("blacklisted_mod_ids", Configuration.CATEGORY_GENERAL, new String[0], "");

        // saving the configuration to its file
        if (config.hasChanged()) {
            config.save();
        }
    }



    /**
     * Check each mod id that is blacklisted and find out which package they belong to,
     * so I can ignore them.
     */
    public static void updateBlacklistedMods()
    {
        blacklistedModPackages = new String[0];
        if (blacklistedModIds.length == 0) { return; }


        final List<String> modPackages = new ArrayList<String>();

        LogHelper.info("The following mods are blacklisted and will be ignored by the Visible Armor Slots mod:");
        LogHelper.info("[START]");

        for (final String blacklistedModId : blacklistedModIds) {
            Loader.instance();
            if (Loader.isModLoaded(blacklistedModId)) {

                if (blacklistedModId.equalsIgnoreCase("minecraft")) {
                    LogHelper.info("  - [%s] :(", blacklistedModId);
                    modPackages.add(MINECRAFT_NAMESPACE);
                }

                else {
                    for (final ModContainer mod : Loader.instance().getActiveModList()) {
                        if (blacklistedModId.equalsIgnoreCase(mod.getModId()) && mod.getMod() != null) {
                            String modClassPackage = mod.getMod().getClass().getName();
                            final int lastDotPosition = modClassPackage.lastIndexOf('.');
                            if (lastDotPosition > 0) {
                                modClassPackage = modClassPackage.substring(0, lastDotPosition);
                            }

                            LogHelper.info("  - [%s] in the namespace [%s];", blacklistedModId, modClassPackage);

                            modPackages.add(modClassPackage);
                            break;
                        }
                    }

                }

            } else {
                LogHelper.info("  - [%s] (WARNING: Mod not found, check your config file);", blacklistedModId);
            }
        }

        LogHelper.info("[END]");


        blacklistedModPackages = modPackages.toArray(new String[0]);
    }



    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equalsIgnoreCase(Reference.ModID)) {
            // Resync config
            loadConfig();
            updateBlacklistedMods();
        }
    }

}
