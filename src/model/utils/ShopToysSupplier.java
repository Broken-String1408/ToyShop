package model.utils;

import model.entities.Rarity;
import model.entities.Toy;
import model.entities.ToyType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ShopToysSupplier {

    private static final Random random = new Random();

    static public Toy supplyOneToy(){

        int rarityIdx = random.nextInt(Rarity.values().length);
        Rarity rarity = Arrays.stream(Rarity.values()).toList().get(rarityIdx);

        int typeIdx = random.nextInt(ToyType.values().length);
        ToyType type = Arrays.stream(ToyType.values()).toList().get(typeIdx);

        return new Toy(System.nanoTime(), type, rarity);
    }

    static public List<Toy> supply(){

        List<Toy> toys = new ArrayList<>();

        for(int i = 0; i < 10; i ++){

            Rarity rarity;

            if(i > 8) rarity = Rarity.EPIC;
            else if(i > 4) rarity = Rarity.RARE;
            else rarity = Rarity.COMMON;

            int typeIdx = random.nextInt(ToyType.values().length);

            ToyType type = Arrays.stream(ToyType.values()).toList().get(typeIdx);

            Toy toy = new Toy(System.nanoTime(), type, rarity);

            toys.add(toy);

        }

        return toys;

    }

}
