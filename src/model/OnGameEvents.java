package model;

import model.entities.Rarity;

public interface OnGameEvents {

    void onGameResults(Rarity rarity, int mistakes, int time);

    void onGameNotification(String message, boolean isRequestingInput);

//    void onGamePrompt(String prompt);

}
