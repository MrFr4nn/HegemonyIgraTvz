package hr.tvz.java.projekt;

import hr.tvz.java.projekt.gui.GlavniProzor;
import hr.tvz.java.projekt.gui.PocetniEkran;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.logging.Logger;

public class PokretacAplikacije extends Application {

    private static final Logger LOG = Logger.getLogger(PokretacAplikacije.class.getName());

    @Override
    public void start(Stage glavnaScena) {
        glavnaScena.setResizable(true);
        PocetniEkran pocetniEkran = new PocetniEkran(glavnaScena, listaIgraca -> {
            PauseTransition odgoda = new PauseTransition(Duration.millis(50));
            odgoda.setOnFinished(dogadjaj -> {
                GlavniProzor glavniProzor = new GlavniProzor(glavnaScena, listaIgraca);
                glavniProzor.prikaziProzor();
                glavnaScena.setIconified(false);
                glavnaScena.setMaximized(true);
            });
            odgoda.play();
        });
        pocetniEkran.prikaziEkran();
    }

    public static void main(String[] argumenti) {
        LOG.info("Pokretanje aplikacije Hegemony: Lead Your Class to Victory...");
        launch(argumenti);
    }
}