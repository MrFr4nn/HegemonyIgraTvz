package hr.tvz.java.projekt;

import hr.tvz.java.projekt.gui.GlavniProzor;
import hr.tvz.java.projekt.gui.PocetniEkran;
import javafx.application.Application;
import javafx.stage.Stage;

public class PokretacAplikacije extends Application {

    @Override
    public void start(Stage glavnaScena) {
        PocetniEkran pocetniEkran = new PocetniEkran(glavnaScena, listaIgraca -> {
            glavnaScena.hide();
            Stage noviProzor = new Stage();
            GlavniProzor glavniProzor = new GlavniProzor(noviProzor, listaIgraca);
            glavniProzor.prikaziProzor();
            noviProzor.setMaximized(true);
        });
        pocetniEkran.prikaziEkran();
    }

    public static void main(String[] argumenti) {
        System.out.println("Pokretanje...");
        launch(argumenti);
    }
}