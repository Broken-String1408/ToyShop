package model.data;

import model.entities.Rarity;
import model.entities.Toy;

import java.util.List;

public interface MachineData {

    List<Toy> addToy(Toy toy);

    List<Toy> removeToy(Toy toy);

    List<Rarity> addToPendingList(Rarity rarity);

    List<Rarity> removeFromPendingList(Rarity rarity);

    List<Toy> getAllToys();

    List<Rarity> getPendingList();

}
