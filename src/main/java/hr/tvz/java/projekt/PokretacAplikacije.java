package hr.tvz.java.projekt;

import hr.tvz.java.projekt.gui.GlavniProzor;
import hr.tvz.java.projekt.gui.PocetniEkran;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class PokretacAplikacije extends Application {

    @Override
    public void start(Stage glavnaScena) {
        glavnaScena.setResizable(true);
        PocetniEkran pocetniEkran = new PocetniEkran(glavnaScena, listaIgraca -> {
            Platform.runLater(() -> {
                GlavniProzor glavniProzor = new GlavniProzor(glavnaScena, listaIgraca);
                glavniProzor.prikaziProzor();
                glavnaScena.setIconified(false);
            });
        });
        pocetniEkran.prikaziEkran();
    }

    public static void main(String[] argumenti) {
        System.out.println("Pokretanje...");
        launch(argumenti);
    }
}