package hr.tvz.java.projekt;

import hr.tvz.java.projekt.gui.GlavniProzor;
import hr.tvz.java.projekt.gui.PocetniEkran;
import javafx.application.Application;
import javafx.stage.Stage;

public class PokretacAplikacije extends Application {

    @Override
    public void start(Stage glavnaScena) {
        PocetniEkran pocetniEkran = new PocetniEkran(glavnaScena, listaIgraca -> {
            GlavniProzor glavniProzor = new GlavniProzor(glavnaScena, listaIgraca);
            glavniProzor.prikaziProzor();
        });
        pocetniEkran.prikaziEkran();
    }

    public static void main(String[] argumenti) {
        System.out.println("Pokretanje...");
        launch(argumenti);
    }
}