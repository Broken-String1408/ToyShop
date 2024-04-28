package presentation;

import utils.Colors;

public enum Command {

    CALL_ADMIN("Позвать администратора", "call_admin"),
    REMOVE_ADMIN("Прогнать администратора", "remove_admin"),
    ADD_TOY("Попросить добавить игрушку в автомат", "add_toy"),
    CHANGE_TOY_RARITY("Попросить изменить редкость игрушки", "change_rarity"),
    START_GAME("Пройти призовую викторину", "start_quiz"),
    SELL_ALL_TOYS("Продать все игрушки", "sell_all"),
    SELL_TOY("Продать одну игрушку", "sell_toy"),
    FREE_PRIZE("Получить случайный приз", "free_spin"),
    MAKE_BET("Попытай удачу! Чем больше ставка, тем выше вероятность выиграть редкий приз!", "bet_spin"),
    INSPECT_COLLECTION("Посмотреть коллекцию своих игрушек", "inspect_collection"),
    INSPECT_MACHINE("Заглянуть в автомат", "inspect_machine"),
    REQUEST_PRIZE("Требовать приз", "request_prize"),
    GO_HOME("Уйти домой", "exit");

    public final String label;

    public final String command;

    Command(String label, String command){
        this.label = label;
        this.command = command;
    }

    @Override
    public String toString() {
        return label + " - введите " + Colors.WHITE.colorize(command);
    }
}
