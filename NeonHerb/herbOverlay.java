package net.runelite.client.plugins.microbot.NeonHerb;

import net.runelite.api.Skill;
import net.runelite.client.plugins.microbot.Microbot;
import net.runelite.client.plugins.natepainthelper.PaintFormat;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;
import javax.inject.Inject;
import java.awt.*;
import java.text.DecimalFormat;

import static net.runelite.client.plugins.microbot.NeonHerb.herbScript.*;
import static net.runelite.client.plugins.natepainthelper.Info.*;

public class herbOverlay extends OverlayPanel {
    @Inject
    herbOverlay(herbPlugin plugin)
    {
        super(plugin);
        setPosition(OverlayPosition.TOP_LEFT);
        setNaughty();
    }
    @Override
    public Dimension render(Graphics2D graphics) {
        try {
            DecimalFormat formatter = new DecimalFormat("#,###");

            xpGained = Microbot.getClient().getSkillExperience(Skill.HERBLORE) - expstarted;
            int xpPerHour = (int)( xpGained / ((System.currentTimeMillis() - timeBegan) / 3600000.0D));

            double herbsCleaned = xpGained / herbXp;
            int herbsPerHour = (int) (herbsCleaned / ((System.currentTimeMillis() - timeBegan) / 3600000.0D));

            double profit = herbsCleaned * herbProfit;
            int profitPerHour = (int) (profit / ((System.currentTimeMillis() - timeBegan) / 3600000.0D));

            nextLevelXp = XP_TABLE[Microbot.getClient().getRealSkillLevel(Skill.HERBLORE) + 1];
            xpTillNextLevel = nextLevelXp - Microbot.getClient().getSkillExperience(Skill.HERBLORE);
            if (xpGained >= 1)
            {
                timeTNL = (long) ((xpTillNextLevel / xpPerHour) * 3600000);
            }

            panelComponent.setPreferredSize(new Dimension(300, 300));
            panelComponent.getChildren().add(TitleComponent.builder()
                    .text("Neon Herb V" + herbScript.version)
                    .color(Color.GREEN)
                    .build());

            panelComponent.getChildren().add(LineComponent.builder()
                    .left("Herblore Exp Gained (hr): " + formatter.format(xpGained)  + " (" + formatter.format(xpPerHour) + ")")
                    .build());
            panelComponent.getChildren().add(LineComponent.builder()
                    .left("Herblore Levels Gained: " + formatter.format(Microbot.getClient().getRealSkillLevel(Skill.HERBLORE) - startinglevel))
                    .build());
            panelComponent.getChildren().add(LineComponent.builder()
                    .left("Time till next level: " + PaintFormat.ft(timeTNL))
                    .build());
            panelComponent.getChildren().add(LineComponent.builder()
                    .left(herbName + " cleaned: " + formatter.format(herbsCleaned) + " (" + formatter.format(herbsPerHour) + ")")
                    .build());
            panelComponent.getChildren().add(LineComponent.builder()
                    .left("Profit made: " + formatter.format(profit) + " (" + formatter.format(profitPerHour) + ")")
                    .build());

            panelComponent.getChildren().add(LineComponent.builder()
                    .left("State: " + herbScript.state)
                    .build());
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        return super.render(graphics);
    }
}
