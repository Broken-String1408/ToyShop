package model.entities;

import java.io.Serializable;

public record Toy(long id, ToyType type, Rarity rarity) implements Serializable {

    @Override
    public String toString() {

        return rarity.ASCII_COLOR.colorize(rarity.label) +
                " " +
                type.label + " стоимостью " + type.basePrice * rarity.priceMultiplier + " монет";

    }
}
