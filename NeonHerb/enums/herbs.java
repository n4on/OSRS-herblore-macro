package net.runelite.client.plugins.microbot.NeonHerb.enums;


import lombok.Getter;

@Getter
public enum herbs {
    GUAM("Grimy gaum leaf", 55, 2.5, 199, 249),
    MARRENTILL("Grimy marrentill", 27, 3.8, 201, 251),
    TARROMIN("Grimy tarromin", 35, 5, 203, 253),
    HARRALANDER("Grimy harralander", 37, 6.3, 205, 255),
    RANARR("Grimy ranarr weed", 52, 7.5, 207, 257),
    TOADFLAX("Grimy toadflax", 20, 8, 3049, 2998),
    IRIT("Grimy irit leaf", 57, 8.8, 209, 259),
    AVANTOE("Grimy avantoe", 99, 10, 211, 261),
    KWUARM("Grimy kwuarm", 65, 11.3, 213, 263),
    SNAPDRAGON("Grimy snapdragon", 42, 11.8, 3051, 3000),
    CADANTINE("Grimy cadantine", 63, 12.5, 215, 265),
    LANTADYME("Grimy lantadyme", 71, 13.1, 2485, 2481),
    DWARF("Grimy dwarf weed", 30, 13.8, 217, 267),
    TORSTOL("Grimy torstol", 112, 15, 219, 269);

    private final String name;
    private final int profit;
    private final double xp;
    private final int itemId;
    private final int cleanHerbItemId;

    herbs(String name, int profit, double xp, int itemId, int cleanHerbItemId) {
        this.name = name;
        this.profit = profit;
        this.xp = xp;
        this.itemId = itemId;
        this.cleanHerbItemId = cleanHerbItemId;
    }
}
