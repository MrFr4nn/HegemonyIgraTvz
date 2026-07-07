package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.logika.HegemonyEngine;
import hr.tvz.java.projekt.model.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

public class KontrolePoteza {

    private static final String FX_TEXT_FILL_STIL = "-fx-text-fill: ";

    private final KreatorIgraceKarte kreatorIgraceKarte;
    private final DefinicijeKarataPoKlasi definicijeKarata;
    private final hr.tvz.java.projekt.util.XmlUpravitelj xmlUpravitelj;

    public static class PodaciOKarti {
        private final String naziv;
        private final String opis;
        private final String emojiIkona;
        private final String nazivAkcije;
        private final Runnable efekt;

        public PodaciOKarti(String naziv, String opis, String emojiIkona, String nazivAkcije, Runnable efekt) {
            this.naziv = naziv;
            this.opis = opis;
            this.emojiIkona = emojiIkona;
            this.nazivAkcije = nazivAkcije;
            this.efekt = efekt;
        }

        public String getNaziv() { return naziv; }
        public String getOpis() { return opis; }
        public String getEmojiIkona() { return emojiIkona; }
        public String getNazivAkcije() { return nazivAkcije; }
        public Runnable getEfekt() { return efekt; }
    }

    public KontrolePoteza(hr.tvz.java.projekt.util.XmlUpravitelj xmlUpravitelj) {
        this.kreatorIgraceKarte = new KreatorIgraceKarte();
        this.definicijeKarata = new DefinicijeKarataPoKlasi();
        this.xmlUpravitelj = xmlUpravitelj;
    }

    public void prekiniSveStrajkoveNaPocetku(List<KlasaIgraca> listaIgraca) {
        definicijeKarata.prekiniSveStrajkoveNaPocetkuRunde(listaIgraca);
    }

    public VBox napraviKontroleZaIgraca(HegemonyEngine engineIgre, KlasaIgraca igrac,
                                        Runnable akcijaPokreniGlasanje, Runnable akcijaPonovnogPrikaza) {
        definicijeKarata.postaviLimiteAkoNisuPostavljeni(engineIgre, igrac);

        VBox panelKontrola = new VBox(10);
        panelKontrola.setPadding(new Insets(10));
        StilGumba.primijeniObrubAktivneKlase(panelKontrola, igrac);

        Label naslovPanela = new Label(odrediNazivUloge(igrac).toUpperCase() + " - ODABERITE KARTU");
        naslovPanela.setFont(Font.font("Arial Black", FontWeight.BOLD, 13));
        naslovPanela.setStyle(FX_TEXT_FILL_STIL + StilGumba.dohvatiBojuKlase(igrac) + ";");

        HBox redKarata = new HBox(10);
        redKarata.setAlignment(Pos.CENTER);

        if (igrac instanceof RadnickaKlasa radnicka) {
            definicijeKarata.dodajKarteRadnicke(redKarata, this, engineIgre, radnicka, akcijaPonovnogPrikaza);
        } else if (igrac instanceof SrednjaKlasa srednja) {
            definicijeKarata.dodajKarteSrednje(redKarata, this, engineIgre, srednja, akcijaPonovnogPrikaza);
        } else if (igrac instanceof KapitalistickaKlasa kapitalist) {
            definicijeKarata.dodajKarteKapitalisticke(redKarata, this, engineIgre, kapitalist, akcijaPonovnogPrikaza);
        } else if (igrac instanceof Vlada vlada) {
            dodajKarteVladeIGumbGlasanja(redKarata, engineIgre, vlada, akcijaPonovnogPrikaza, akcijaPokreniGlasanje);
        }

        panelKontrola.getChildren().addAll(naslovPanela, redKarata);
        return panelKontrola;
    }

    private void dodajKarteVladeIGumbGlasanja(HBox red, HegemonyEngine engineIgre, Vlada vlada,
                                              Runnable akcijaPonovnogPrikaza, Runnable akcijaPokreniGlasanje) {
        definicijeKarata.dodajKarteVlade(red, this, engineIgre, vlada, engineIgre.getListaIgraca(), akcijaPonovnogPrikaza);

        VBox gumbKarta = new VBox(8);
        gumbKarta.setAlignment(Pos.CENTER);
        gumbKarta.setPadding(new Insets(14, 10, 14, 10));
        gumbKarta.setPrefSize(150, 190);

        Button gumbPokreniGlasanje = new Button("POKRENI\nGLASANJE");
        StilGumba.primijeniNaglaseni(gumbPokreniGlasanje);
        gumbPokreniGlasanje.setWrapText(true);
        gumbPokreniGlasanje.setOnAction(dogadjaj -> akcijaPokreniGlasanje.run());

        gumbKarta.getChildren().add(gumbPokreniGlasanje);
        red.getChildren().add(gumbKarta);
    }

    public void dodajKartu(HBox red, HegemonyEngine engineIgre, KlasaIgraca igrac, PodaciOKarti podaci) {
        boolean dostupna = engineIgre.jeAkcijaDostupnaTrenutnomIgracu(podaci.getNazivAkcije());
        String bojaHex = StilGumba.dohvatiBojuKlase(igrac);
        VBox karta = kreatorIgraceKarte.napraviKartu(podaci.getNaziv(), podaci.getOpis(), podaci.getEmojiIkona(), bojaHex, !dostupna);

        if (dostupna) {
            kreatorIgraceKarte.omoguciHover(karta, bojaHex);
            karta.setOnMouseClicked(dogadjaj -> {
                engineIgre.iskoristiAkcijuTrenutnogIgraca(podaci.getNazivAkcije());
                xmlUpravitelj.dodajPotezUPovijest(engineIgre.getBrojRunde(), igrac.getNaziv(), "Odigrana karta: " + podaci.getNaziv());
                podaci.getEfekt().run();
            });
        }
        red.getChildren().add(karta);
    }

    private String odrediNazivUloge(KlasaIgraca igrac) {
        if (igrac instanceof RadnickaKlasa) return "Radnicka klasa";
        if (igrac instanceof SrednjaKlasa) return "Srednja klasa";
        if (igrac instanceof KapitalistickaKlasa) return "Kapitalisticka klasa";
        return "Vlada";
    }

    public VBox napraviPanelGlasanja(String nazivZakona, String nazivGlasaca, String bojaHex,
                                     Runnable akcijaZa, Runnable akcijaProtiv) {
        VBox panelKontrola = new VBox(12);
        panelKontrola.setAlignment(Pos.CENTER);
        panelKontrola.setPadding(new Insets(10));

        Label oznakaTko = new Label("GLASA: " + nazivGlasaca.toUpperCase());
        oznakaTko.setFont(Font.font("Arial Black", FontWeight.BOLD, 15));
        oznakaTko.setStyle(FX_TEXT_FILL_STIL + bojaHex + "; -fx-effect: dropshadow(gaussian, " + bojaHex + ", 12, 0.4, 0, 0);");

        Label naslovPanela = new Label("ZAKON: " + nazivZakona.toUpperCase());
        naslovPanela.setFont(Font.font("Verdana", 13));
        naslovPanela.setStyle(FX_TEXT_FILL_STIL + StilGumba.TEKST_SVIJETLI + ";");

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