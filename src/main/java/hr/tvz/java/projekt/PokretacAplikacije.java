package hr.tvz.java.projekt;

import hr.tvz.java.projekt.gui.GlavniProzor;
import javafx.application.Application;
import javafx.stage.Stage;

public class PokretacAplikacije extends Application {

    @Override
    public void start(Stage glavnaScena) {
        GlavniProzor glavniProzor = new GlavniProzor(glavnaScena);
        glavniProzor.prikaziProzor();
    }

    public static void main(String[] argumenti) {
        System.out.println("Pokretanje...");
        launch(argumenti);
    }
}