package model;

import model.entities.GameNotification;
import model.entities.Rarity;
import model.entities.Toy;
import utils.Observable;

import java.util.List;

public interface Model {

    Observable<List<Toy>> getObservablePlayerToysCollection();
    Observable<List<Toy>> getObservableToysInMachine();
    Observable<List<Rarity>> getObservableToysInPending();
    Observable<GameNotification> getObservableGameNotifications();
    Observable<String> getObservableResultOperations();
    Observable<Integer> getObservableBalance();


    void startGame();
    void onGameInput(String input);
    void requestToy();
    void freeSpin();
    void sellToy(Toy toy);
    void sellAll();
    void makeBet(int bet);

    //Randomly for now...
    void addToy();
    void changeToyRarity();

}
