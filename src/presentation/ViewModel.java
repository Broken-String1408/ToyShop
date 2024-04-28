package presentation;

import model.Model;
import model.entities.Rarity;
import model.entities.Toy;
import utils.MutableObservableData;
import utils.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ViewModel implements ToysShopPresenter {

    private final Model model;
    private boolean isAdminFlag = false;
    private List<Toy> toysInMachine;
    private List<Toy> playerToys;
    private List<Rarity> pendingToys;
    private int balance;

    private final MutableObservableData<String> gamePrompts = new MutableObservableData<>();
    private final MutableObservableData<String> messages = new MutableObservableData<>();
    private final MutableObservableData<Prompt> prompts = new MutableObservableData<>();
    private final MutableObservableData<List<Command>> possibleCommands = new MutableObservableData<>();



    public ViewModel(Model model){
        this.model = model;

    }

    @Override
    public void onViewReady() {
        messages.postValue("""
                Добро пожаловать в наш магазин игрушек НеЛего.
                В качестве программы лояльности вы можете принять участие в розыгрыше супер призов.
                Удачи!""");
        getObservables();
        sendMainMenuCommands();
    }

    private void getObservables(){

        model.getObservableBalance().subscribe(
                balance -> {
                    messages.postValue("Ваш балланс: " + balance + " монет");
                    this.balance = balance;
                }
        );

        model.getObservableToysInMachine().subscribe(
                toys -> toysInMachine = toys);

        model.getObservablePlayerToysCollection().subscribe(
                toys -> playerToys = toys);

        model.getObservableToysInPending().subscribe(
                pendingToys -> this.pendingToys = pendingToys);

        model.getObservableResultOperations().subscribe( operationResultMsg ->{
                    messages.postValue(operationResultMsg);
                    if(isAdminFlag) sendAdminCommands();
                    else sendMainMenuCommands();
                }
        );

        model.getObservableGameNotifications().subscribe( notification -> {
            if(notification.isRequestingInput()){
                gamePrompts.postValue(notification.message());
            }
            else {
                messages.postValue(notification.message());
            }

            if(notification.isGameEnded()){
                sendMainMenuCommands();
            }
        });
    }

    @Override
    public void onUserGameInput(String input) {
        model.onGameInput(input);
    }


    @Override
    public void onNewCommand(String command) {
        Optional<Command> cmd = possibleCommands
                .getValue()
                .stream()
                .filter(c -> c.command.equals(command))
                .findFirst();

        if(cmd.isPresent()){
            handleNewCommand(cmd.get());
        }
        else {
            messages.postValue("Несуществующая команда");
            if(isAdminFlag) sendAdminCommands();
            else sendMainMenuCommands();
        }
    }

    private void handleNewCommand(Command command){

        switch (command){
            case CALL_ADMIN -> {
                isAdminFlag = true;
                sendAdminCommands();
            }
            case REMOVE_ADMIN -> {
                isAdminFlag = false;
                sendMainMenuCommands();
            }
            case ADD_TOY -> model.addToy();

            case CHANGE_TOY_RARITY -> model.changeToyRarity();

            case REQUEST_PRIZE -> model.requestToy();

            case START_GAME -> model.startGame();

            case SELL_ALL_TOYS -> model.sellAll();

            case SELL_TOY -> {
                onSellToy();
            }

            case FREE_PRIZE -> model.freeSpin();
            case MAKE_BET -> {
                prompts.postValue(new Prompt("Сделайте вашу ставку от 20 до 100 монет", Prompt.PromptType.MAKE_BET));
            }

            case INSPECT_COLLECTION -> {
                onInspectCollectionCmd();
                sendMainMenuCommands();
            }
            case INSPECT_MACHINE -> {
                onInspectMachineCmd();
                sendMainMenuCommands();
            }
            case GO_HOME -> messages.postValue("До свидания!");

        }
    }

    private void onSellToy() {
        StringBuilder builder = new StringBuilder();
        builder.append("Введите номер игрушки для продажи:\n");
        for (int i = 0; i < playerToys.size(); i++) {
            builder.append("%d - %s\n".formatted(i + 1, playerToys.get(i)));
        }
        prompts.postValue(new Prompt(builder.toString(), Prompt.PromptType.SELECT_TOY));
    }
    @Override
    public void onUserInput(Prompt input){
        boolean isCorrect = true;
        int inputInt = 0;
        try {
            inputInt = Integer.parseInt(input.message());
        } catch (NumberFormatException e){
            messages.postValue("Вы ввели некорректное значение");
            isCorrect = false;
        }
        switch (input.type()){

            case SELECT_TOY -> {
                if(inputInt < 1 || inputInt > playerToys.size()) {
                    messages.postValue("Неверный номер!");
                    isCorrect = false;
                }
                if(isCorrect){
                    model.sellToy(playerToys.get(inputInt - 1));
                }
                else{
                    sendMainMenuCommands();
                }
            }
            case MAKE_BET -> {
                if(inputInt < 20 || inputInt > 100){
                    messages.postValue("Ставка должна быть от 20 до 100 монет");
                    isCorrect = false;
                }
                if(inputInt > balance){
                    messages.postValue("У вас недостаточно монет");
                    isCorrect = false;
                }
                if(isCorrect){
                    model.makeBet(inputInt);
                }
                else {
                    sendMainMenuCommands();
                }
            }
        }


    }

    private void onInspectMachineCmd(){
        int common = 0;
        int rare = 0;
        int epic = 0;
        int legend = 0;

        for(Toy toy : toysInMachine){
            switch (toy.rarity()){
                case LEGENDARY -> legend++;
                case EPIC -> epic ++;
                case RARE -> rare++;
                case COMMON -> common++;
            }
        }

        String res = "В автомате есть: " +
                "обычных игрушек - " + common +
                ", редких - " + rare +
                ", эпичных - " + epic +
                ", легендарных - " + legend;

        messages.postValue(res);
    }

    private void onInspectCollectionCmd(){
        StringBuilder builder = new StringBuilder();
        builder.append("Ваша коллекция игрушек включает:\n");

        if(!playerToys.isEmpty()){
            for(Toy toy : playerToys){
                builder.append(toy);
                builder.append("\n");
            }
        }

        if (!pendingToys.isEmpty()){
            builder.append("Еще не выданные игрушки: ")
                    .append(pendingToys.size())
                    .append(" шт.");

        }

        messages.postValue(builder.toString());
    }


    private void sendMainMenuCommands(){
        List<Command> commands = new ArrayList<>();
        commands.add(Command.START_GAME);
        commands.add(Command.CALL_ADMIN);
        commands.add(Command.INSPECT_MACHINE);
        if(!toysInMachine.isEmpty()) commands.add(Command.FREE_PRIZE);
        if(balance >= 20) commands.add(Command.MAKE_BET);
        if(!playerToys.isEmpty()){
            commands.add(Command.INSPECT_COLLECTION);
            commands.add(Command.SELL_TOY);
            commands.add(Command.SELL_ALL_TOYS);
        }
        if(!pendingToys.isEmpty()){
            if (playerToys.isEmpty()){
                commands.add(Command.INSPECT_COLLECTION);
            }
            commands.add(Command.REQUEST_PRIZE);
        }
        commands.add(Command.GO_HOME);
        possibleCommands.postValue(commands);
    }

    private void sendAdminCommands(){
        List<Command> initialCommands = new ArrayList<>();
        initialCommands.add(Command.ADD_TOY);
        if(!toysInMachine.isEmpty()) initialCommands.add(Command.CHANGE_TOY_RARITY);
        initialCommands.add(Command.REMOVE_ADMIN);
        possibleCommands.postValue(initialCommands);
    }



    @Override
    public Observable<List<Command>> getPossibleCommands() {
        return possibleCommands.asObservableData();
    }
    @Override
    public Observable<String> getObservableMessages() {
        return messages.asObservableData();
    }
    @Override
    public Observable<String> getObservableGamePrompts() {
        return gamePrompts.asObservableData();
    }

    @Override
    public Observable<Prompt> getObservablePrompts() {
        return prompts.asObservableData();
    }
}
