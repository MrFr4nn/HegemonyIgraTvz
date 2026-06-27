package hr.tvz.java.projekt;

import hr.tvz.java.projekt.gui.GlavniProzor;
import hr.tvz.java.projekt.gui.PocetniEkran;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PokretacAplikacije extends Application {

    @Override
    public void start(Stage glavnaScena) {
        glavnaScena.setResizable(true);
        PocetniEkran pocetniEkran = new PocetniEkran(glavnaScena, listaIgraca -> {
            PauseTransition odgoda = new PauseTransition(Duration.millis(50));
            odgoda.setOnFinished(dogadjaj -> {
                GlavniProzor glavniProzor = new GlavniProzor(glavnaScena, listaIgraca);
                glavniProzor.prikaziProzor();
                glavnaScena.setIconified(false);
            });
            odgoda.play();
        });
        pocetniEkran.prikaziEkran();
    }

    public static void main(String[] argumenti) {
        System.out.println("Pokretanje...");
        launch(argumenti);
    }
}