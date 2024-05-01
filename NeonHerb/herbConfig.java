package net.runelite.client.plugins.microbot.NeonHerb;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.plugins.microbot.NeonHerb.enums.states;
import net.runelite.client.plugins.microbot.NeonHerb.enums.herbs;

@ConfigGroup("herblore")
public interface herbConfig extends Config {
    @ConfigItem(
            keyName = "State",
            name = "State",
            description = "Choose state.",
            position = 0
    )
    default states getState()
    {
        return states.getHerb;
    }
    @ConfigItem(
            keyName = "Herb",
            name = "Herb",
            description = "Choose herb.",
            position = 0
    )
    default herbs getHerb()
    {
        return herbs.GUAM;
    }
}