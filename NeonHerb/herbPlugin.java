package net.runelite.client.plugins.microbot.NeonHerb;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Skill;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.microbot.Microbot;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.awt.*;

import static net.runelite.client.plugins.natepainthelper.Info.*;

@PluginDescriptor(
        name = PluginDescriptor.Default + "NeonHerb",
        description = "Microbot Herblore bot",
        tags = {"Herblore", "microbot"},
        enabledByDefault = false
)
@Slf4j
public class herbPlugin extends Plugin {
    @Inject
    private herbConfig config;
    @Provides
    herbConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(herbConfig.class);
    }

    @Inject
    net.runelite.client.plugins.microbot.NeonHerb.herbScript herbScript;
    @Inject
    private OverlayManager overlayManager;
    @Inject
    private herbOverlay herbOverlay;

    @Override
    protected void startUp() throws AWTException {
        expstarted = Microbot.getClient().getSkillExperience(Skill.HERBLORE);
        startinglevel = Microbot.getClient().getRealSkillLevel(Skill.HERBLORE);
        timeBegan = System.currentTimeMillis();

        if (overlayManager != null) {
            overlayManager.add(herbOverlay);
        }
        herbScript.run(config);
    }
    protected void shutDown() {
        herbScript.shutdown();
        overlayManager.remove(herbOverlay);
    }
}