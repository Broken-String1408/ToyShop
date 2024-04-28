package model.games;

import model.entities.GameOutput;

public interface Game {


    GameOutput getOutput();

    GameOutput checkInput(String input);


}
