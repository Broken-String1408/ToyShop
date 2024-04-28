package model.games;

import java.util.Random;

public class ScrambleGame {


    public static String wordScrambler(String word){

        Random random = new Random();

        char [] wordArr = word.toCharArray();

        int wordLength = word.length();
        int firstIndex;
        int secondIndex;


        for(int i = 0; i < wordLength; i ++){

            do {
                firstIndex = random.nextInt(wordLength);
                secondIndex = random.nextInt(wordLength);

            } while (firstIndex == secondIndex);

            char temp = wordArr[firstIndex];
            wordArr[firstIndex] = wordArr[secondIndex];
            wordArr[secondIndex] = temp;

        }

        StringBuilder result = new StringBuilder();

        for(char c : wordArr){
            result.append(c);
        }

        return result.toString();

    }

}
