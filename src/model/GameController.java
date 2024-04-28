package model;

import model.entities.GameOutput;
import model.entities.Rarity;
import model.games.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {

    private enum GameType {
        COLOR_GAME, CIPHER_GAME, COMMANDS_GAME, ROMAN_GAME
    }
    private final List<GameType> gamesToRun = new ArrayList<>();
    private Game currentGame;
    private int currentGameIdx;
    private final Game colorGame = new ColorGame();
    private final Game cipherGame = new CipherGame();
    private final Game commandsGame = new CommandsGame();
    private final Game romanGame = new RomanGame();

    private int mistakesCount;
    private long startingTime;

    private final Random random = new Random();

    private final OnGameEvents onGameEvents;

    protected GameController(OnGameEvents results){
        this.onGameEvents = results;
    }


    public void initializeGames(){

        startingTime = System.currentTimeMillis();
        gamesToRun.clear();
        gamesToRun.add(GameType.COLOR_GAME);
        gamesToRun.add(GameType.COLOR_GAME);
        gamesToRun.add(GameType.COLOR_GAME);
        gamesToRun.add(GameType.COMMANDS_GAME);
        gamesToRun.add(GameType.COMMANDS_GAME);
        gamesToRun.add(GameType.CIPHER_GAME);
        gamesToRun.add(GameType.ROMAN_GAME);
        gamesToRun.add(GameType.ROMAN_GAME);
        mistakesCount = 0;

        launchRandomGame();
    }

    private void launchRandomGame(){

        if(gamesToRun.isEmpty()){
            calculateResults();
            return;
        }
        currentGameIdx = random.nextInt(gamesToRun.size());
        GameType randomGame = gamesToRun.get(currentGameIdx);

        switch (randomGame){

            case COLOR_GAME -> currentGame = colorGame;

            case CIPHER_GAME -> currentGame = cipherGame;

            case COMMANDS_GAME -> currentGame = commandsGame;

            case ROMAN_GAME -> currentGame = romanGame;
        }

        GameOutput output = currentGame.getOutput();

        handleGameOutput(output);
    }


    private void handleGameOutput(GameOutput output){

        if(output.isEnded()){
            if(!output.isCorrect()) mistakesCount++;
            gamesToRun.remove(currentGameIdx);
            onGameEvents.onGameNotification(output.message(), false);
            launchRandomGame();
        }
        else {
            onGameEvents.onGameNotification(output.message(), true);
        }
    }


    public void onUserInput(String input){
        GameOutput output = currentGame.checkInput(input);
        handleGameOutput(output);
    }

    private void calculateResults(){

        int time = (int) (System.currentTimeMillis() - startingTime) / 1000;

        Rarity rarity = switch (mistakesCount) {
            case 0 -> Rarity.LEGENDARY;
            case 1 -> Rarity.EPIC;
            case 2 -> Rarity.RARE;
            default -> Rarity.COMMON;
        };

        onGameEvents.onGameResults(rarity, mistakesCount, time);
    }


}
