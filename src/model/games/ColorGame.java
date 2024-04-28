package model.games;

import model.entities.GameOutput;
import utils.Colors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColorGame implements Game {

    public record FaceColor(Colors color, Colors label){}

    private String answer;
    Random random = new Random();
    private static final List<Colors> playableColors = new ArrayList<>();

    static {
        playableColors.add(Colors.BLUE);
        playableColors.add(Colors.GREEN);
        playableColors.add(Colors.YELLOW);
        playableColors.add(Colors.RED);
    }

    @Override
    public GameOutput getOutput() {

        int color;
        int word;

        do {
            color = random.nextInt(playableColors.size());
            word = random.nextInt(playableColors.size());
        } while (color == word);

        StringBuilder message = new StringBuilder();

        if(random.nextInt(3) < 2){
            message.append("Какой цвет? (рус)\n");
            answer = playableColors.get(color).label;
        }
        else {
            message.append("Какое слово? (рус) \n");
            answer = playableColors.get(word).label;
        }

        message
                .append(playableColors.get(color).code)
                .append(playableColors.get(word).label)
                .append(Colors.RESET);

        return new GameOutput(message.toString(), false, true);

    }

    @Override
    public GameOutput checkInput(String input) {

        boolean isCorrect = input.toLowerCase().equals(answer);

        String message = isCorrect? "Верно!"
                : "Неверно. Ответ - " + answer;

        return new GameOutput(message, true, isCorrect);
    }
}
