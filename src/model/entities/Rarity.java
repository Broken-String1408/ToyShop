package model.entities;

import utils.Colors;

public enum Rarity {
    LEGENDARY(Colors.YELLOW, "легендарный", 4),
    EPIC(Colors.MAGENTA, "эпичный", 3),
    RARE(Colors.BLUE, "редкий", 2),
    COMMON(Colors.GREEN, "обычный", 1);


    public final Colors ASCII_COLOR;
    public final String label;
    public final int priceMultiplier;

    Rarity(Colors ASCII_COLOR, String label, int priceMultiplier){
        this.ASCII_COLOR = ASCII_COLOR;
        this.label = label;
        this.priceMultiplier = priceMultiplier;
    }


}
