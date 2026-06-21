package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.Vlada;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class KontrolePoteza {

    public VBox napraviKontroleRadnickeKlase(RadnickaKlasa radnickaKlasa, Runnable akcijaNakonPoteza) {
        VBox panelKontrola = new VBox(10);
        panelKontrola.setPadding(new Insets(15));

        Label naslovPanela = new Label("Potezi - Radnicka klasa");
        naslovPanela.setTextFill(Color.ORANGE);

        Button gumbKupiHranu = new Button("Kupi hranu (trosak 8)");
        gumbKupiHranu.setOnAction(dogadjaj -> {
            radnickaKlasa.kupiHranu(10, 8);
            akcijaNakonPoteza.run();
        });

        Button gumbInvestirajObrazovanje = new Button("Investiraj u obrazovanje (trosak 10)");
        gumbInvestirajObrazovanje.setOnAction(dogadjaj -> {
            radnickaKlasa.investirajUObrazovanje(10);
            akcijaNakonPoteza.run();
        });

        Button gumbZaposliRadnika = new Button("Zaposli radnika");
        gumbZaposliRadnika.setOnAction(dogadjaj -> {
            radnickaKlasa.zaposliRadnika(1);
            akcijaNakonPoteza.run();
        });

        Button gumbOtpustiRadnika = new Button("Otpusti radnika");
        gumbOtpustiRadnika.setOnAction(dogadjaj -> {
            radnickaKlasa.otpustiRadnika(1);
            akcijaNakonPoteza.run();
        });

        panelKontrola.getChildren().addAll(naslovPanela, gumbKupiHranu, gumbInvestirajObrazovanje,
                gumbZaposliRadnika, gumbOtpustiRadnika);
        return panelKontrola;
    }

    public VBox napraviKontroleVlade(Vlada vlada, Runnable akcijaNakonPoteza) {
        VBox panelKontrola = new VBox(10);
        panelKontrola.setPadding(new Insets(15));

        Label naslovPanela = new Label("Potezi - Vlada");
        naslovPanela.setTextFill(Color.LIGHTBLUE);

        Button gumbPovecajPorez = new Button("Povecaj porez (+0.05)");
        gumbPovecajPorez.setOnAction(dogadjaj -> {
            double novaStopa = vlada.getStopaPoreza() + 0.05;
            vlada.promijeniStopuPoreza(novaStopa);
            akcijaNakonPoteza.run();
        });

        Button gumbSmanjiPorez = new Button("Smanji porez (-0.05)");
        gumbSmanjiPorez.setOnAction(dogadjaj -> {
            double novaStopa = vlada.getStopaPoreza() - 0.05;
            vlada.promijeniStopuPoreza(novaStopa);
            akcijaNakonPoteza.run();
        });

        Button gumbPovecajPlacu = new Button("Povecaj min. placu (+1)");
        gumbPovecajPlacu.setOnAction(dogadjaj -> {
            double novaPlaca = vlada.getMinimalnaPlaca() + 1.0;
            vlada.promijeniMinimalnuPlacu(novaPlaca);
            akcijaNakonPoteza.run();
        });

        Button gumbIsplatiSubvenciju = new Button("Isplati subvenciju (15)");
        gumbIsplatiSubvenciju.setOnAction(dogadjaj -> {
            vlada.isplatiSubvenciju(15.0);
            akcijaNakonPoteza.run();
        });

        Button gumbNaplatiPorez = new Button("Naplati porez (od 50 prihoda)");
        gumbNaplatiPorez.setOnAction(dogadjaj -> {
            vlada.naplatiPorez(50.0);
            akcijaNakonPoteza.run();
        });

        panelKontrola.getChildren().addAll(naslovPanela, gumbPovecajPorez, gumbSmanjiPorez,
                gumbPovecajPlacu, gumbIsplatiSubvenciju, gumbNaplatiPorez);
        return panelKontrola;
    }
}