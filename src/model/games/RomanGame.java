package model.games;

import model.entities.GameOutput;

import java.util.Random;
import java.util.TreeMap;

public class RomanGame implements Game {

    Random random = new Random();
    String answer;

    private final static TreeMap<Integer, String> map = new TreeMap<>();

    static {
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
    }

    @Override
    public GameOutput getOutput() {

        String message = "Введите результат (Roman): \n" + generateGameOutput();

        return new GameOutput(message, false, true);
    }

    @Override
    public GameOutput checkInput(String input) {

        boolean isCorrect = input.toUpperCase().equals(answer);
        String message = isCorrect ? "Совершенно верно!" : "Неверный ответ!"
                + " Правильный ответ - " + answer;

        return new GameOutput(message, true, isCorrect);
    }


    private String generateGameOutput(){

        int numOne = random.nextInt(1, 20);
        int numTwo = random.nextInt(1, 19);

        String output;

        if(random.nextBoolean()){
            output = arabicToRoman(numOne) + " + " + arabicToRoman(numTwo);
            answer = arabicToRoman(numOne + numTwo);
        } else {
            int min = Math.min(numOne, numTwo);
            int max = Math.max(numOne, numTwo);
            output = arabicToRoman(max) + " - " + arabicToRoman(min);
            answer = arabicToRoman(max - min);
        }

        return output;
    }

    private String arabicToRoman(int number){
        int l =  map.floorKey(number);
        if ( number == l ) {
            return map.get(number);
        }
        return map.get(l) + arabicToRoman(number-l);
    }

}
