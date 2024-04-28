package model.utils;

import model.entities.Rarity;
import model.entities.Toy;
import utils.Colors;



public class GameResults {

    public static String toString(Toy toy, Rarity rarity, int mistakes, int time){

        StringBuilder builder = new StringBuilder();

        builder.append("Ваш результат ")
                .append(rarity.ASCII_COLOR)
                .append(rarity.label)
                .append(Colors.RESET)
                .append("!!!\n")
                .append("ошибок - ")
                .append(mistakes)
                .append(" | время - ")
                .append(time)
                .append(" сек.\n");

       return ending(builder, toy, rarity);
    }
    public static String toString(Rarity rarity, Toy toy){
        StringBuilder message = new StringBuilder();
        message.append("Результат вашей ставки ")
                .append(rarity.ASCII_COLOR.colorize(rarity.label))
                .append("!!!\n");
        return ending(message, toy, rarity);
    }
    private static String ending(StringBuilder builder, Toy toy, Rarity rarity){
        if(toy == null){
            builder.append("К сожалению, в автомате не нашелся ")
                    .append(rarity.ASCII_COLOR)
                    .append(rarity.label)
                    .append(Colors.RESET)
                    .append(" зверь. Обратитесь к администрации!");
        }
        else {
            builder.append("К счастью, а автомате нашелся ")
                    .append(toy);
        }

        return builder.toString();
    }
}
