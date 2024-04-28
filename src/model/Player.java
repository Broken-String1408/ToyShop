package model;

import model.data.PlayerData;
import model.entities.Toy;
import utils.MutableObservableData;
import utils.Observable;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final MutableObservableData<List<Toy>> toysCollection = new MutableObservableData<>();
    private final MutableObservableData<Integer> balance = new MutableObservableData<>();
    private final PlayerData data;

    public Player(PlayerData data)
    {
        this.data = data;
        loadToysFromFile();
        getBalance();
    }

    public void addToyToCollection(Toy toy){
        toysCollection.postValue(
                data.addToy(toy)
        );
    }

    private void loadToysFromFile(){
        toysCollection.postValue(
                data.getAllToys()
        );
    }
    public void sellAll(){
        int sum = 0;
        List<Toy> toys = data.getAllToys();
        for (Toy toy : toys) {
            sum += toy.rarity().priceMultiplier * toy.type().basePrice;
        }
        updateBalance(sum, true);
        data.removeAllToys();
        toysCollection.postValue(new ArrayList<>());
    }
    public void sellToy(Toy toy){
        toysCollection.postValue(data.removeToy(toy));
        int toAdd = toy.type().basePrice * toy.rarity().priceMultiplier;
        updateBalance(toAdd, true);
    }
    private void getBalance(){
        balance.postValue(
                data.getBalance()
        );
    }
    public void updateBalance(int value, boolean isToSum){
      Integer updBalance = isToSum? data.getBalance() + value: data.getBalance() - value;
      data.updateBalance(updBalance);
      balance.postValue(updBalance);
    }

    public Observable<List<Toy>> getObservablePlayerToys(){
        return toysCollection.asObservableData();
    }
    public Observable<Integer> getObservablePlayerBalance(){
        return balance.asObservableData();
    }


}
