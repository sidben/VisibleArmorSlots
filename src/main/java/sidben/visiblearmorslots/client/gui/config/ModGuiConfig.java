package sidben.visiblearmorslots.client.gui.config;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import sidben.visiblearmorslots.handler.ConfigurationHandler;
import sidben.visiblearmorslots.reference.Reference;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;


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
        generalConfigs.add(new ConfigElement(generalCat.get("enabled_on_enchanting")));
        generalConfigs.add(new ConfigElement(generalCat.get("enabled_on_anvil")));

        list.addAll(generalConfigs);


        return list;
    }

}