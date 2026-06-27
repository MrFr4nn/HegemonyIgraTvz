package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.logika.HegemonyEngine;
import hr.tvz.java.projekt.model.KapitalistickaKlasa;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.SrednjaKlasa;
import hr.tvz.java.projekt.model.Vlada;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class KontrolePoteza {

    private KreatorIgraceKarte kreatorIgraceKarte;
    private DefinicijeKarataPoKlasi definicijeKarata;

    public KontrolePoteza() {
        this.kreatorIgraceKarte = new KreatorIgraceKarte();
        this.definicijeKarata = new DefinicijeKarataPoKlasi();
    }

    public VBox napraviKontroleZaIgraca(HegemonyEngine engineIgre, KlasaIgraca igrac, Runnable akcijaPrijedlog, Runnable akcijaPonovnogPrikaza) {
        definicijeKarata.postaviLimiteAkoNisuPostavljeni(engineIgre, igrac);

        VBox panelKontrola = new VBox(10);
        panelKontrola.setPadding(new Insets(10));
        StilGumba.primijeniObrubAktivneKlase(panelKontrola, igrac);

        Label naslovPanela = new Label(odrediNazivUloge(igrac).toUpperCase() + " - ODABERITE KARTU");
        naslovPanela.setFont(Font.font("Arial Black", FontWeight.BOLD, 13));
        naslovPanela.setStyle("-fx-text-fill: " + StilGumba.dohvatiBojuKlase(igrac) + ";");

        HBox redKarata = new HBox(10);
        redKarata.setAlignment(Pos.CENTER);

        if (igrac instanceof RadnickaKlasa) {
            definicijeKarata.dodajKarteRadnicke(redKarata, this, engineIgre, (RadnickaKlasa) igrac, akcijaPonovnogPrikaza);
        } else if (igrac instanceof SrednjaKlasa) {
            definicijeKarata.dodajKarteSrednje(redKarata, this, engineIgre, (SrednjaKlasa) igrac, akcijaPonovnogPrikaza);
        } else if (igrac instanceof KapitalistickaKlasa) {
            definicijeKarata.dodajKarteKapitalisticke(redKarata, this, engineIgre, (KapitalistickaKlasa) igrac, akcijaPonovnogPrikaza);
        } else {
            definicijeKarata.dodajKarteVlade(redKarata, this, engineIgre, (Vlada) igrac, akcijaPonovnogPrikaza);
        }

        dodajKartuPrijedlogaZakona(redKarata, engineIgre, igrac, akcijaPrijedlog);

        panelKontrola.getChildren().addAll(naslovPanela, redKarata);
        return panelKontrola;
    }

    private void dodajKartuPrijedlogaZakona(HBox red, HegemonyEngine engineIgre, KlasaIgraca igrac, Runnable akcijaPrijedlog) {
        boolean dostupna = engineIgre.jeAkcijaDostupnaTrenutnomIgracu("PrijedlogZakona");
        String bojaHex = StilGumba.dohvatiBojuKlase(igrac);
        VBox karta = kreatorIgraceKarte.napraviKartu("Politicki pritisak",
                "Predlozi promjenu zakona", "M5 21 H19 M12 3 L19 9 H5 Z M7 9 V21 M17 9 V21", bojaHex, !dostupna);

        if (dostupna) {
            kreatorIgraceKarte.omoguciHover(karta, bojaHex);
            karta.setOnMouseClicked(dogadjaj -> {
                if (engineIgre.iskoristiAkcijuTrenutnogIgraca("PrijedlogZakona")) {
                    akcijaPrijedlog.run();
                }
            });
        }
        red.getChildren().add(karta);
    }

    public void dodajKartu(HBox red, HegemonyEngine engineIgre, KlasaIgraca igrac, String naziv, String opis,
                           String svgIkona, String nazivAkcije, Runnable efekt) {
        boolean dostupna = engineIgre.jeAkcijaDostupnaTrenutnomIgracu(nazivAkcije);
        String bojaHex = StilGumba.dohvatiBojuKlase(igrac);
        VBox karta = kreatorIgraceKarte.napraviKartu(naziv, opis, svgIkona, bojaHex, !dostupna);

        if (dostupna) {
            kreatorIgraceKarte.omoguciHover(karta, bojaHex);
            karta.setOnMouseClicked(dogadjaj -> {
                if (engineIgre.iskoristiAkcijuTrenutnogIgraca(nazivAkcije)) {
                    efekt.run();
                }
            });
        }
        red.getChildren().add(karta);
    }

    private String odrediNazivUloge(KlasaIgraca igrac) {
        if (igrac instanceof RadnickaKlasa) {
            return "Radnicka klasa";
        } else if (igrac instanceof SrednjaKlasa) {
            return "Srednja klasa";
        } else if (igrac instanceof KapitalistickaKlasa) {
            return "Kapitalisticka klasa";
        } else {
            return "Vlada";
        }
    }

    public VBox napraviPanelGlasanja(String nazivZakona, String nazivGlasaca, String bojaHex, Runnable akcijaZa, Runnable akcijaProtiv) {
        VBox panelKontrola = new VBox(12);
        panelKontrola.setAlignment(Pos.CENTER);
        panelKontrola.setPadding(new Insets(10));

        Label oznakaTko = new Label("GLASA: " + nazivGlasaca.toUpperCase());
        oznakaTko.setFont(Font.font("Arial Black", FontWeight.BOLD, 15));
        oznakaTko.setStyle("-fx-text-fill: " + bojaHex + "; -fx-effect: dropshadow(gaussian, " + bojaHex + ", 12, 0.4, 0, 0);");

        Label naslovPanela = new Label("ZAKON: " + nazivZakona.toUpperCase());
        naslovPanela.setFont(Font.font("Verdana", 13));
        naslovPanela.setStyle("-fx-text-fill: " + StilGumba.TEKST_SVIJETLI + ";");

        HBox redGumbova = new HBox(20);
        redGumbova.setAlignment(Pos.CENTER);

        Button gumbZa = new Button("✓  ZA");
        StilGumba.primijeniPozitivniVeliki(gumbZa);
        gumbZa.setOnAction(dogadjaj -> akcijaZa.run());

        Button gumbProtiv = new Button("✗  PROTIV");
        StilGumba.primijeniNegativniVeliki(gumbProtiv);
        gumbProtiv.setOnAction(dogadjaj -> akcijaProtiv.run());

        redGumbova.getChildren().addAll(gumbZa, gumbProtiv);
        panelKontrola.getChildren().addAll(oznakaTko, naslovPanela, redGumbova);
        return panelKontrola;
    }
}