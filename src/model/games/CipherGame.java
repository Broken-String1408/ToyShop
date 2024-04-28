package model.games;

import model.entities.GameOutput;
import utils.Colors;

import java.util.*;


public class CipherGame implements Game {

    private final String[] phrases = {
            "i am sure you can do it in time",
            "i am asking why did you think so",
            "i am saying it is time to move on",
            "i will see you in a while",
            "i will try to do harder tomorrow",
            "kill or be killed"
    };

    private String answer;
    private Map<Character, Character> answerMap;
    private char[] encryptedPhrase;
    private int mistakesRemain;
    boolean isSolved;

    @Override
    public GameOutput getOutput() {
        isSolved = false;
        mistakesRemain = 6;
        encryptedPhrase = getEncryptArray();
        String message = "Расшифруйте фразу (eng). Вводите по 1 букве или фразу целиком\n" + getHighlightedWord();
        return new GameOutput(message, false, true);
    }

    @Override
    public GameOutput checkInput(String input) {

        String lowercase = input.toLowerCase();

        String message = input.length() == 1 ? handleSingleLetterInput(lowercase.charAt(0))
                : handleWholePhraseInput(lowercase);

        return new GameOutput(message, mistakesRemain == 0 || isSolved, mistakesRemain != 0);
    }

    private String handleSingleLetterInput(char input){

        boolean correct = answerMap.containsKey(input);

        if(!correct){
            mistakesRemain--;
            return "Такой буквы нет. Осталось попыток: " + mistakesRemain;
        }
        else {
            for(int i = 0; i < answer.length(); i ++){
                if(answer.charAt(i) == input){
                    encryptedPhrase[i] = input;
                }
            }

            String highlighted = getHighlightedWord();

            if(isSolved){
                return "Вы разгадали шифр!\n" + highlighted;
            } else {
                return "Такая буква есть\n" + highlighted;
            }
        }
    }


    private String handleWholePhraseInput(String input){
        if(input.equals(answer)){
            isSolved = true;
            return "Вы разгадали шифр!\n" + Colors.WHITE.colorize(answer);
        } else {
            mistakesRemain --;
            return "Неверная фраза. Осталось попыток: " + mistakesRemain;
        }
    }

    private String getHighlightedWord(){

        StringBuilder highlighted = new StringBuilder();

        isSolved = true;

        for (int c = 0; c < answer.length(); c++) {

            if(encryptedPhrase[c] == answer.charAt(c)){
                highlighted.append(Colors.WHITE.code);
                highlighted.append(encryptedPhrase[c]);
                highlighted.append(Colors.RESET);
            } else {
                isSolved = false;
                highlighted.append(encryptedPhrase[c]);
            }
        }

        return highlighted.toString();
    }


    private char[] getEncryptArray(){

        Random random = new Random();

        String phrase = phrases[random.nextInt(phrases.length)];

        answer = phrase;

        Set<Character> initialCharsSet = new HashSet<>();


        char[] charArray = phrase.toCharArray();

        for (char c : charArray) {
            if(c != ' '){
                initialCharsSet.add(c);
            }
        }

        Map<Character, Character> map = new HashMap<>();

        // For optimizing iterations on map values...
        Set<Character> encryptedCharsSet = new HashSet<>();

        char c;

        for(char key : initialCharsSet){

            do {
                c = (char)(random.nextInt(26) + 'a');
            } while (encryptedCharsSet.contains(c));

            encryptedCharsSet.add(c);
            map.put(key, c);

        }

        answerMap = map;


        StringBuilder result = new StringBuilder();

        for(char key : charArray){

            if(key == ' ')
                result.append(' ');
            else
                result.append(map.get(key));

        }

        return result.toString().toCharArray();

    }


}
