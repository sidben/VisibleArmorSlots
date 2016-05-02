package sidben.visiblearmorslots.handler;

import java.io.File;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sidben.visiblearmorslots.ModVisibleArmorSlots;
import sidben.visiblearmorslots.helper.VanillaGuiRedirect;
import sidben.visiblearmorslots.reference.Reference;


public class ConfigurationHandler
{
    // public static final int GUI_SLOTS_XOFFSET = -22;

    public static final int            HOPPER_YOFFSET       = -33;
    public static final int            BEACON_XOFFSET       = 54;
    public static final int            BEACON_YOFFSET       = 53;
    public static final int            CHEST_SINGLE_YOFFSET = 1;
    public static final int            CHEST_DOUBLE_YOFFSET = 55;


    public static final String[]       SLOT_SIDES           = new String[] { "LEFT", "RIGHT" };

    public static final String         CATEGORY_DEBUG       = "debug";

    public static String               extraSlotsSide;
    public static int                  extraSlotsMargin;

    public static boolean              overrideEnchantTable;
    public static boolean              overrideAnvil;
    public static boolean              overrideChest;
    public static boolean              overrideEnderChest;
    public static boolean              overrideFurnace;
    public static boolean              overrideCraftingTable;
    public static boolean              overrideDispenser;
    public static boolean              overrideDropper;
    public static boolean              overrideHopper;
    public static boolean              overrideBrewingStand;
    public static boolean              overrideBeacon;

    public static VanillaGuiRedirect[] guiRedirectArray     = null;



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

        overrideEnchantTable = config.getBoolean("enabled_on_enchanting", Configuration.CATEGORY_GENERAL, true, "");
        overrideAnvil = config.getBoolean("enabled_on_anvil", Configuration.CATEGORY_GENERAL, true, "");
        overrideChest = config.getBoolean("enabled_on_chest", Configuration.CATEGORY_GENERAL, true, "");
        overrideEnderChest = config.getBoolean("enabled_on_ender_chest", Configuration.CATEGORY_GENERAL, true, "");
        overrideFurnace = config.getBoolean("enabled_on_furnace", Configuration.CATEGORY_GENERAL, true, "");
        overrideCraftingTable = config.getBoolean("enabled_on_crafting", Configuration.CATEGORY_GENERAL, true, "");
        overrideDispenser = config.getBoolean("enabled_on_dispenser", Configuration.CATEGORY_GENERAL, true, "");
        overrideDropper = config.getBoolean("enabled_on_dropper", Configuration.CATEGORY_GENERAL, true, "");
        overrideHopper = config.getBoolean("enabled_on_hopper", Configuration.CATEGORY_GENERAL, true, "");
        overrideBrewingStand = config.getBoolean("enabled_on_brewing", Configuration.CATEGORY_GENERAL, true, "");
        overrideBeacon = config.getBoolean("enabled_on_beacon", Configuration.CATEGORY_GENERAL, true, "");

        // load the redirect array
        guiRedirectArray = new VanillaGuiRedirect[] { 
                new VanillaGuiRedirect(Blocks.enchanting_table, ModVisibleArmorSlots.GUI_ENCHANTMENT_TABLE, overrideEnchantTable),
                new VanillaGuiRedirect(Blocks.anvil, ModVisibleArmorSlots.GUI_ANVIL, overrideAnvil), 
                new VanillaGuiRedirect(Blocks.chest, ModVisibleArmorSlots.GUI_CHEST, overrideChest),
                new VanillaGuiRedirect(Blocks.trapped_chest, ModVisibleArmorSlots.GUI_CHEST, overrideChest),
                new VanillaGuiRedirect(Blocks.ender_chest, ModVisibleArmorSlots.GUI_ENDER_CHEST, overrideEnderChest),
                new VanillaGuiRedirect(Blocks.furnace, ModVisibleArmorSlots.GUI_FURNACE, overrideFurnace),
                new VanillaGuiRedirect(Blocks.crafting_table, ModVisibleArmorSlots.GUI_CRAFTING_TABLE, overrideCraftingTable),
                new VanillaGuiRedirect(Blocks.dispenser, ModVisibleArmorSlots.GUI_DISPENSER, overrideDispenser),
                new VanillaGuiRedirect(Blocks.dropper, ModVisibleArmorSlots.GUI_DISPENSER, overrideDropper), 
                new VanillaGuiRedirect(Blocks.hopper, ModVisibleArmorSlots.GUI_HOPPER, overrideHopper),
                new VanillaGuiRedirect(Blocks.brewing_stand, ModVisibleArmorSlots.GUI_BREWING_STAND, overrideBrewingStand),
                new VanillaGuiRedirect(Blocks.beacon, ModVisibleArmorSlots.GUI_BEACON, overrideBeacon) };


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
