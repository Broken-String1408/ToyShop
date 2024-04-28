package model;

import model.data.MachineData;
import model.entities.Rarity;
import model.entities.Toy;
import model.utils.ShopToysSupplier;
import utils.MutableObservableData;
import utils.Observable;

import java.util.*;

public class ToyMachine {
    private final MutableObservableData<List<Toy>> toysInMachine = new MutableObservableData<>();

    private final MutableObservableData<List<Rarity>> pendingToys = new MutableObservableData<>();

    private final Random random = new Random();

    private final MachineData data;

    protected ToyMachine(MachineData data)
    {
        this.data = data;
        loadToys();
        loadPendingToys();
    }

    protected Toy requestMatchingToy(){

        List<Toy> matches;

        Set<Rarity> raritySet = new LinkedHashSet<>(pendingToys.getValue().reversed());

        for(Rarity rarity : raritySet){
            matches = toysInMachine
                    .getValue()
                    .stream()
                    .filter( toy -> toy.rarity().equals(rarity))
                    .toList();
            if(!matches.isEmpty()){
                int idx = random.nextInt(matches.size());
                Toy toy = matches.get(idx);
                removeToyFromMachine(toy);
                pendingToys.postValue(
                        data.removeFromPendingList(rarity)
                );
                return toy;
            }
        }

        return null;
    }

    protected Toy requestMatchingToy(Rarity rarity){

        List<Toy> matches = toysInMachine
                .getValue()
                .stream()
                .filter( toy -> toy.rarity().equals(rarity))
                .toList();

        if(matches.isEmpty()) {

            pendingToys.postValue(
                    data.addToPendingList(rarity)
            );
            return null;
        }

        else {
            Toy toy = matches.get(random.nextInt(matches.size()));
            removeToyFromMachine(toy);
            return toy;
        }
    }
    protected Rarity makeBet(int bet){
        int pass = random.nextInt(101);
        Rarity rarity;
        if(bet * 0.5 > pass){
            rarity = Rarity.LEGENDARY;
        }
        else if (bet * 0.6 > pass) {
            rarity = Rarity.EPIC;
        }
        else if (bet * 0.8 > pass) {
            rarity = Rarity.RARE;
        }
        else rarity = Rarity.COMMON;
        return rarity;
    }
    private void loadPendingToys(){
        pendingToys.postValue(
                data.getPendingList()
        );
    }

    private void loadToys(){
        toysInMachine.postValue(
                data.getAllToys()
        );
    }

    protected Toy getRandomToy(){
        int index = random.nextInt(toysInMachine.getValue().size());
        return toysInMachine.getValue().get(index);
    }

    protected Rarity changeToyRarityRandomly(Toy toy){

        Rarity updatedRarity = toy.rarity();
        List<Rarity> list = Arrays.stream(Rarity.values()).toList();

        while (toy.rarity() == updatedRarity){
            int idx = random.nextInt(list.size());
            updatedRarity = list.get(idx);
        }
        Toy newToy = new Toy(System.nanoTime(), toy.type(), updatedRarity);

        data.removeToy(toy);
        toysInMachine.postValue(
                data.addToy(newToy)
        );
        return updatedRarity;
    }


    protected Toy addToyToMachine(){
        Toy toy = ShopToysSupplier.supplyOneToy();
        toysInMachine.postValue(
                data.addToy(toy)
        );
        return toy;
    }


    public void removeToyFromMachine(Toy toy){
        toysInMachine.postValue(
                data.removeToy(toy)
        );
    }

    public Observable<List<Toy>> getObservableToysInMachine(){
        return toysInMachine.asObservableData();
    }

    public Observable<List<Rarity>> getObservablePendingToys(){
        return pendingToys.asObservableData();
    }


}
