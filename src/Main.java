import model.Model;
import model.ModelImpl;
import presentation.ToysShopPresenter;
import presentation.ViewModel;
import view.TerminalView;

import java.io.IOException;


public class Main {
    public static void main (String[] args) {


        Model model = ModelImpl.create();

        ToysShopPresenter toysShopVM = new ViewModel(model);

        new TerminalView(toysShopVM);


    }
}
