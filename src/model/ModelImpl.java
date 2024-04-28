package model;

import model.data.MachineDataFileImpl;
import model.data.PlayerDataFileImpl;
import model.entities.GameNotification;
import model.entities.Rarity;
import model.entities.Toy;
import model.utils.GameResults;
import utils.Colors;
import utils.MutableObservableData;
import utils.Observable;

import java.util.List;


public class ModelImpl implements Model {

    private static ModelImpl instance = null;
    private final ToyMachine machine = new ToyMachine(new MachineDataFileImpl());
    private final Player player = new Player(new PlayerDataFileImpl());
    private final MutableObservableData<GameNotification> gameNotifications = new MutableObservableData<>();
    private final MutableObservableData<String> pendingResultOperations = new MutableObservableData<>();

    private final GameController gameLauncher = new GameController(new OnGameEvents() {
        @Override
        public void onGameResults(Rarity rarity, int mistakes, int time) {
            Toy toy = machine.requestMatchingToy(rarity);
            if(toy != null) player.addToyToCollection(toy);
            String resultsMessage = GameResults.toString(toy, rarity, mistakes, time);
            gameNotifications.postValue(new GameNotification(resultsMessage, false, true));
        }

        @Override
        public void onGameNotification(String message, boolean isRequestingInput) {
            gameNotifications.postValue(new GameNotification(message, isRequestingInput, false));
        }

    });


    private ModelImpl(){};

    public static ModelImpl create(){
        if(instance == null){
            instance = new ModelImpl();
        }
        return instance;
    }


    @Override
    public void requestToy() {
        Toy toy = machine.requestMatchingToy();
        if(toy == null) {
            pendingResultOperations.postValue(
                    "В машине снова не нашелся зверь подходящего уровня"
            );
            return;
        }

        player.addToyToCollection(toy);

        pendingResultOperations.postValue(
                "К счастью, в автомате нашелся " + toy
        );

    }

    @Override
    public void freeSpin() {
        Toy toy = machine.getRandomToy();
        machine.removeToyFromMachine(toy);
        player.addToyToCollection(toy);
        pendingResultOperations.postValue(
                "Поздравляю! Вы выиграли " + toy
        );
    }

    @Override
    public void makeBet(int bet) {
        player.updateBalance(bet, false);
        Rarity rarity = machine.makeBet(bet);
        Toy toy = machine.requestMatchingToy(rarity);
        if(toy != null) player.addToyToCollection(toy);
        String message = GameResults.toString(rarity, toy);
        pendingResultOperations.postValue(message);
    }

    @Override
    public void sellToy(Toy toy) {
        player.sellToy(toy);
        pendingResultOperations.postValue("Вы продали " + toy);
    }

    @Override
    public void sellAll() {
        player.sellAll();
        pendingResultOperations.postValue("Вы продали все имеющиеся игрушки");
    }

    @Override
    public void addToy() {
        Toy toy = machine.addToyToMachine();
        pendingResultOperations.postValue(
                "В автомате теперь есть: " + toy
        );
    }

    @Override
    public void changeToyRarity() {
        Toy toyToUpdate = machine.getRandomToy();
        Rarity updatedRarity = machine.changeToyRarityRandomly(toyToUpdate);
        String message = toyToUpdate + " теперь " + updatedRarity.ASCII_COLOR.colorize(updatedRarity.label);
        pendingResultOperations.postValue(message);
    }

    @Override
    public Observable<String> getObservableResultOperations() {
        return pendingResultOperations.asObservableData();
    }

    @Override
    public void startGame() {
        notifyGameStarted();
        gameLauncher.initializeGames();
    }

    private void notifyGameStarted(){
        String message = "Игра началась!\n" + "[прервать - " +
                Colors.WHITE.colorize("abort") + "]";
        gameNotifications.postValue(
                new GameNotification(message, false, false)
        );

    }

    @Override
    public void onGameInput(String input) {
        if(input.equals("abort")){
            gameNotifications.postValue(
                    new GameNotification("Вы прервали игру", false, true)
            );
        } else {
            gameLauncher.onUserInput(input);
        }
    }

    @Override
    public Observable<GameNotification> getObservableGameNotifications() {
        return gameNotifications.asObservableData();
    }


    @Override
    public Observable<List<Rarity>> getObservableToysInPending() {
        return machine.getObservablePendingToys();
    }
    @Override
    public Observable<List<Toy>> getObservablePlayerToysCollection() {
        return player.getObservablePlayerToys();
    }
    @Override
    public Observable<List<Toy>> getObservableToysInMachine() {
        return machine.getObservableToysInMachine();
    }

    @Override
    public Observable<Integer> getObservableBalance() {
        return player.getObservablePlayerBalance();
    }
}
