package model.data;

import model.entities.Toy;
import model.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDataFileImpl implements PlayerData {

    private final String TOYS_FILE_PATH = "playertoys.txt";
    private final String BALANCE_FILE_PATH = "balance.txt";
    @Override
    public List<Toy> addToy(Toy toy) {
        List<Toy> toys = getAllToys();
        toys.add(toy);
        try {
            FileUtils.writeObject(TOYS_FILE_PATH, toys);
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while writing toys list to file");
        }
        return toys;
    }

    @Override
    public List<Toy> getAllToys() {
        File file = new File(TOYS_FILE_PATH);
        if(file.exists()){
            try {
                return (List<Toy>) FileUtils.readObject(file);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("Could not load player toys", e);
            }
        }
        return new ArrayList<>();
    }

    @Override
    public int getBalance() {
        File file = new File(BALANCE_FILE_PATH);
        if(file.exists()){
            try {
                return (Integer) FileUtils.readObject(file);
            } catch (IOException|ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return 100;
    }

    @Override
    public void removeAllToys() {
        try {
            FileUtils.writeObject(TOYS_FILE_PATH, new ArrayList<Toy>());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Toy> removeToy(Toy toy) {
        List<Toy> toys = getAllToys();
        toys.remove(toy);
        try {
            FileUtils.writeObject(TOYS_FILE_PATH, toys);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toys;
    }

    @Override
    public void updateBalance(Integer balance) {
        try {
            FileUtils.writeObject(BALANCE_FILE_PATH, balance);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
