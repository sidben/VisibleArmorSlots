package sidben.visiblearmorslots.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import sidben.visiblearmorslots.util.LogHelper;


public class ModConfig
{
    public static final String   POSITION_LEFT             = "LEFT";
    public static final String   POSITION_RIGHT            = "RIGHT";
    public static final int      POTION_SHIFT_MARGIN_LEFT  = -62;
    public static final int      POTION_SHIFT_MARGIN_RIGHT = 60;
    public static final String   CATEGORY_DEBUG            = "debug";

    private static Configuration _config;
    private static boolean       _onDebug;
    private static boolean       _debugAsInfo;
    private static String        _extraSlotsSide;
    private static int           _extraSlotsMargin;
    private static boolean       _swapKeyEnabled;
    private static String[]      _blacklistedModIds;
    private static String[]      _blacklistedModPackages;



    public static void init(File configFile)
    {
        // Create configuration object from config file
        if (_config == null) {
            _config = new Configuration(configFile);
            refreshConfig();
        }
    }



    public static void refreshConfig()
    {
        final String[] slotSidesValidEntries = new String[] { POSITION_LEFT, POSITION_RIGHT };

        // Load properties
        _onDebug = _config.getBoolean("on_debug", CATEGORY_DEBUG, false, "");
        _debugAsInfo = _config.getBoolean("debug_as_level_info", CATEGORY_DEBUG, false, "");
        _extraSlotsSide = _config.getString("slots_side", Configuration.CATEGORY_GENERAL, POSITION_LEFT, "", slotSidesValidEntries);
        _extraSlotsMargin = _config.getInt("slots_margin", Configuration.CATEGORY_GENERAL, 2, 0, 128, "");
        _swapKeyEnabled = _config.getBoolean("swap_hands", Configuration.CATEGORY_GENERAL, true, "");
        _blacklistedModIds = _config.getStringList("blacklisted_mod_ids", Configuration.CATEGORY_GENERAL, new String[0], "");

        // saving the configuration to its file
        if (_config.hasChanged()) {
            _config.save();
        }
    }



    /**
     * Check each mod id that is blacklisted and find out which package they belong to,
     * so I can ignore them.
     */
    public static void updateBlacklistedMods()
    {
        _blacklistedModPackages = new String[0];
        if (_blacklistedModIds.length == 0) { return; }


        final List<String> modPackages = new ArrayList<String>();

        LogHelper.info("The following mods are blacklisted and will be ignored by the Visible Armor Slots mod:");
        LogHelper.info("[START]");

        for (final String blacklistedModId : _blacklistedModIds) {
            Loader.instance();
            if (Loader.isModLoaded(blacklistedModId)) {

                if (blacklistedModId.equalsIgnoreCase("minecraft")) {
                    LogHelper.info("  - [%s] :(", blacklistedModId);
                    modPackages.add("net.minecraft");
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


        _blacklistedModPackages = modPackages.toArray(new String[0]);
    }



    public static ConfigCategory getCategory(String category)
    {
        return _config.getCategory(category);
    }



    // --------------------------------------------
    // Public config values
    // --------------------------------------------

    /**
     * When the mod is on 'debug mode', messages with the level Trace and Debug will be added to the logs.
     */
    public static boolean onDebug()
    {
        return _onDebug;
    }


    /**
     * DEBUG and TRACE messages are logged with the level INFO. Requires onDebug set to true.
     */
    public static boolean debugAsInfo()
    {
        return _debugAsInfo;
    }


    public static String extraSlotsSide()
    {
        return _extraSlotsSide;
    }


    public static int extraSlotsMargin()
    {
        return _extraSlotsMargin;
    }


    /**
     * Enabled swapping items of the off-hand slot by pressing the swap hands key when hovering over any slot.
     */
    public static boolean swapKeyEnabled()
    {
        return _swapKeyEnabled;
    }


    public static String[] blacklistedModPackages()
    {
        return _blacklistedModPackages;
    }
}
