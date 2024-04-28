package presentation;

import utils.Observable;

import java.util.List;

public interface ToysShopPresenter {

    Observable<List<Command>> getPossibleCommands();

    Observable<String> getObservableMessages();

    Observable<String> getObservableGamePrompts();
    Observable<Prompt> getObservablePrompts();

    void onUserGameInput(String input);
    void onUserInput(Prompt input);
    void onNewCommand(String command);
    void onViewReady();


}
