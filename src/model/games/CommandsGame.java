package model.games;

import model.entities.GameOutput;
import utils.Colors;

import java.util.Random;

public class CommandsGame implements Game {

    Random random = new Random();

    String[] actions = {"reboot", "overload", "boost", "check", "synchronize", "maintain",
            "upload", "debug", "calculate", "finalize", "vandalize", "permute", "abort"};
    String[] target = {"synchronizer", "performance", "caches", "algorithm", "database",
            "encryption", "firewall", "framework", "networking", "operating_system",
            "virtualization", "automation", "debugging", "multithreading", "derivative"};
    String[] endings = {"out", "in", "clockwise", "down", "programmatically", "persistently",
            "viciously", "forwarding", "backup", "upleft", "neglectfully", "blindly",
            "unconditionally"
    };

    String answer;

    @Override
    public GameOutput getOutput() {
        String command =  "Введите правильно команду (eng) : \n" + generateCommand();
        return new GameOutput(command, false, true);
    }

    @Override
    public GameOutput checkInput(String input) {

        boolean isCorrect = input.toLowerCase().equals(answer);

        String message = isCorrect? "Совершенно верно!\n" :
                "Неверная команда!\n" + getHighLightedWord(input);

        return new GameOutput(message, true, isCorrect);
    }

    private String getHighLightedWord(String input){

        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < input.length(); i++){

            char c = input.charAt(i);

            if(i >= answer.length() || answer.charAt(i) != c){
                builder.append(Colors.RED.colorize(c));
            }
            else {
                builder.append(c);
            }

        }

        return builder.toString();
    }

    private String generateCommand(){

        String command = actions[random.nextInt(actions.length)] + "_" +
                target[random.nextInt(target.length)] + "_" +
                endings[random.nextInt(endings.length)];

        answer = command;

        char[] letters = command.toCharArray();

        if(random.nextBoolean()){
            int start = 0;
            int end = command.length() - 1;

            while (start < end){
                char temp = letters[start];
                letters[start] = letters[end];
                letters[end] = temp;
                start++;
                end --;
            }
        }


        StringBuilder builder = new StringBuilder();

        for(char c : letters){
            builder.append(c);
        }

        String res = builder.toString();

        return Colors.WHITE_BACKGROUND.code + Colors.WHITE + res + Colors.RESET;

    }

}
