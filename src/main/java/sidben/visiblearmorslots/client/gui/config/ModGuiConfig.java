package sidben.visiblearmorslots.client.gui.config;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;
import sidben.visiblearmorslots.handler.ConfigurationHandler;
import sidben.visiblearmorslots.reference.Reference;


public class ModGuiConfig extends GuiConfig
{


    public ModGuiConfig(GuiScreen guiScreen) {
        super(guiScreen, getConfigElements(), Reference.ModID, false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
    }


    private static List<IConfigElement> getConfigElements()
    {
        final List<IConfigElement> list = new ArrayList<IConfigElement>();


        // General config
        final List<IConfigElement> generalConfigs = new ArrayList<IConfigElement>();
        final ConfigCategory generalCat = ConfigurationHandler.config.getCategory(Configuration.CATEGORY_GENERAL);

        generalConfigs.add(new ConfigElement(generalCat.get("slots_side")));
        generalConfigs.add(new ConfigElement(generalCat.get("slots_margin").setConfigEntryClass(GuiConfigEntries.NumberSliderEntry.class)));

        generalConfigs.add(new ConfigElement(generalCat.get("enabled_on_anvil")));
        generalConfigs.add(new ConfigElement(generalCat.get("enabled_on_beacon")));
        generalConfigs.add(new ConfigElement(generalCat.get("enabled_on_brewing")));
        generalConfigs.add(new ConfigElement(generalCat.get("enabled_on_chest")));
        generalConfigs.add(new ConfigElement(generalCat.get("enabled_on_crafting")));
        generalConfigs.add(new ConfigElement(generalCat.get("enabled_on_dispenser")));
        generalConfigs.add(new ConfigElement(generalCat.get("enabled_on_dropper")));
        generalConfigs.add(new ConfigElement(generalCat.get("enabled_on_enchanting")));
        generalConfigs.add(new ConfigElement(generalCat.get("enabled_on_ender_chest")));
        generalConfigs.add(new ConfigElement(generalCat.get("enabled_on_furnace")));
        generalConfigs.add(new ConfigElement(generalCat.get("enabled_on_hopper")));

        list.addAll(generalConfigs);


        return list;
    }

}