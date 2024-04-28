package model.data;

import model.entities.Rarity;
import model.entities.Toy;
import model.utils.FileUtils;
import model.utils.ShopToysSupplier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MachineDataFileImpl implements MachineData {

    private final String TOYS_FILE = "machinetoys.txt";
    private final String PENDING_TOYS_FILE = "pendingtoys.txt";

    @Override
    public List<Toy> addToy(Toy toy) {
        List<Toy> toys = getAllToys();
        toys.add(toy);
        writeToFile(toys, TOYS_FILE);
        return toys;
    }

    @Override
    public List<Toy> removeToy(Toy toy) {
        List<Toy> toys = getAllToys();
        toys.remove(toy);
        writeToFile(toys, TOYS_FILE);
        return toys;
    }

    @Override
    public List<Rarity> addToPendingList(Rarity rarity) {
        List<Rarity> pending = getPendingList();
        pending.addLast(rarity);
        writeToFile(pending, PENDING_TOYS_FILE);
        return pending;
    }

    @Override
    public List<Rarity> removeFromPendingList(Rarity rarity) {
        List<Rarity> pending = getPendingList();
        pending.reversed().remove(rarity);
        writeToFile(pending, PENDING_TOYS_FILE);
        return pending;
    }

    @Override
    public List<Toy> getAllToys() {
        File file = new File(TOYS_FILE);
        if(file.exists()){
            try {
                return (List<Toy>) FileUtils.readObject(file);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("Could not read toys list from file", e);
            }
        }
        else {
            List<Toy> toys = ShopToysSupplier.supply();
            writeToFile(toys, TOYS_FILE);
            return toys;
        }
    }

    @Override
    public List<Rarity> getPendingList() {
        File file = new File(PENDING_TOYS_FILE);
        if(file.exists()){
            try {
                return (List<Rarity>) FileUtils.readObject(file);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("Could not load pending toys!", e);
            }
        }
        else return new ArrayList<>();
    }

    private void writeToFile(Object obj, String path){
        try {
            FileUtils.writeObject(path, obj);
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while writing to file");
        }
    }
}
