package view;

import presentation.Command;
import presentation.Prompt;
import presentation.ToysShopPresenter;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class TerminalView {

    Scanner scanner = new Scanner(System.in);

    ToysShopPresenter presenter;
    public TerminalView(ToysShopPresenter presenter){
        this.presenter = presenter;
        subscribeToObservers();
        this.presenter.onViewReady();
    }

    private void subscribeToObservers(){

        presenter.getObservablePrompts().subscribe(this::onPrompt);

        presenter.getObservableMessages().subscribe(this::typeWriter);

        presenter.getObservableGamePrompts().subscribe(this::onGamePrompts);

        presenter.getPossibleCommands().subscribe(this::onCommandsPrompt);

    }
    private void onPrompt(Prompt prompt){
        System.out.println(prompt.message());
        String input = scanner.nextLine();
        presenter.onUserInput(new Prompt(input, prompt.type()));
    }
    private void onGamePrompts(String gamePrompt){
        System.out.println(gamePrompt);

        try {
            while (System.in.available() > 0){
                scanner.nextLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        String marker = scanner.nextLine();

//        for (int i = 0; i < gamePrompt.timerSec() * 10; i++) {
//            try {
//                Thread.sleep(100);
//                if (System.in.available() > 0) {
//                    marker = scanner.nextLine();
//                    while (marker.isBlank() && System.in.available() > 0){
//                        marker = scanner.nextLine();
//                    }
//                    break;
//                }
//            } catch (IOException | InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }


        presenter.onUserGameInput(marker);
    }

    private void onCommandsPrompt(List<Command> commands){
        System.out.println("\nДоступные команды:");

        for (Command command : commands) {
            System.out.println(command);
        }

        String userInput = scanner.nextLine();
        presenter.onNewCommand(userInput);
    }


    private void typeWriter(String message){

        for(char c : message.toCharArray()){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.print(c);
        }

        System.out.println("\n");

    }

}
