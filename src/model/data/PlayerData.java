package model.data;

import model.entities.Toy;

import java.util.List;

public interface PlayerData {

    List<Toy> addToy(Toy toy);

    List<Toy> getAllToys();

    int getBalance();
    void updateBalance(Integer balance);
    void removeAllToys();
    List<Toy> removeToy(Toy toy);

}
