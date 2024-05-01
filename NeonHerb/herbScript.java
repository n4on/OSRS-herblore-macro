package net.runelite.client.plugins.microbot.NeonHerb;

import net.runelite.api.MenuAction;
import net.runelite.client.plugins.microbot.Microbot;
import net.runelite.client.plugins.microbot.Script;
import net.runelite.client.plugins.microbot.util.bank.Rs2Bank;
import net.runelite.client.plugins.microbot.util.inventory.Rs2Inventory;
import net.runelite.client.plugins.microbot.util.inventory.Rs2Item;
import net.runelite.client.plugins.microbot.util.menu.NewMenuEntry;
import net.runelite.client.plugins.microbot.NeonHerb.enums.states;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class herbScript extends Script {
    public static double version = 1.0;

    public static states state;
    public static String herbName;
    public static int herbProfit;
    public static double herbXp;
    public static int herbId;
    public static int cleanHerbItemId;

    public boolean run(herbConfig config) {
        state = config.getState();
        herbName = config.getHerb().getName();
        herbProfit = config.getHerb().getProfit();
        herbXp = config.getHerb().getXp();
        herbId = config.getHerb().getItemId();
        cleanHerbItemId = config.getHerb().getCleanHerbItemId();

        mainScheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(() -> {
            if (!super.run()) return;
            try {

                switch (state) {
                    case getHerb:
                        getHerb();
                        break;
                    case cleanHerb:
                        cleanHerb();
                        break;
                }

            } catch (Exception ignored) {
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
        return true;
    }

    private void getHerb() {
        state = states.doing;

        if (!Rs2Bank.isOpen()) {
            Rs2Bank.openBank();
            sleepUntil(Rs2Bank::isOpen);
        }

        if (!Rs2Inventory.hasItem(herbName)) {
            if (Rs2Inventory.hasItem(cleanHerbItemId)) {
                Rs2Bank.depositAll(cleanHerbItemId);
                sleepUntil(()-> !Rs2Inventory.hasItem(cleanHerbItemId));
            }

            Rs2Bank.withdrawAll(herbId);
            boolean hasHerb = sleepUntil(() -> Rs2Inventory.hasItem(herbId), 2_000);
            if (!hasHerb) {
                Microbot.getNotifier().notify("You have no more " + herbName);
                shutdown();
                state = states.doing;
                return;
            }
        }


        Rs2Bank.closeBank();
        sleepUntil(() -> !Rs2Bank.isOpen(), 2_000);

        state = states.cleanHerb;
    }
    private void cleanHerb() {
        state = states.doing;

        for (Rs2Item item: Rs2Inventory.items()) {
            Microbot.doInvoke(new NewMenuEntry(item.slot, 9764864, MenuAction.CC_OP.getId(), 2, herbId, herbName), new Rectangle(0,0,1,1));
        }

        state = states.getHerb;
    }
}