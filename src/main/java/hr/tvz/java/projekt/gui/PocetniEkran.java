package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.model.KlasaIgraca;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PocetniEkran {

    private Stage glavnaScena;
    private Consumer<List<KlasaIgraca>> akcijaPoOdabiru;
    private ProvjeraOdabira provjeraOdabira;
    private KreatorKarticeUloge kreatorKartice;
    private int odabraniBrojIgraca;
    private List<String> odabraneUlogePoPoziciji;
    private List<HBox> redoviPozicija;
    private VBox panelIzbornikaUloga;

    private static final String[] NAZIVI_ULOGA = {"Radnicka klasa", "Srednja klasa", "Kapitalisticka klasa", "Vlada"};
    private static final String[] BOJE_ULOGA = {"#8C3A36", "#A6862C", "#2B4C70", "#4A4458"};

    public PocetniEkran(Stage glavnaScena, Consumer<List<KlasaIgraca>> akcijaPoOdabiru) {
        this.glavnaScena = glavnaScena;
        this.akcijaPoOdabiru = akcijaPoOdabiru;
        this.provjeraOdabira = new ProvjeraOdabira();
        this.kreatorKartice = new KreatorKarticeUloge();
        this.odabraniBrojIgraca = 2;
        this.odabraneUlogePoPoziciji = new ArrayList<>();
        this.redoviPozicija = new ArrayList<>();
    }

    public void prikaziEkran() {
        VBox korijenskiLayout = new VBox(20);
        korijenskiLayout.setPadding(new Insets(35));
        korijenskiLayout.setAlignment(Pos.TOP_CENTER);
        korijenskiLayout.setBackground(napraviGradijentnuPodlogu());

        Label naslov = new Label("HEGEMONY: LEAD YOUR CLASS TO VICTORY");
        naslov.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #2B2520;");

        Label podnaslov = new Label("Odaberite broj igraca, zatim kliknite karticu za odabir uloge svakog igraca");
        podnaslov.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 13px; -fx-text-fill: #4A4438;");

        HBox panelBrojaIgraca = napraviPanelBrojaIgraca();

        panelIzbornikaUloga = new VBox(15);
        panelIzbornikaUloga.setAlignment(Pos.CENTER);
        azurirajIzbornikeUloga();

        Button gumbZapocni = new Button("Zapocni igru");
        StilGumba.primijeniNaglaseni(gumbZapocni);
        gumbZapocni.setStyle(gumbZapocni.getStyle() + "-fx-font-size: 15px; -fx-padding: 12 30 12 30;");
        gumbZapocni.setOnAction(dogadjaj -> obradiZapocniIgru());

        korijenskiLayout.getChildren().addAll(naslov, podnaslov, panelBrojaIgraca, panelIzbornikaUloga, gumbZapocni);

        Scene scenaPocetnogEkrana = new Scene(korijenskiLayout, 950, 700);
        glavnaScena.setTitle("Hegemony - Postavke igre");
        glavnaScena.setScene(scenaPocetnogEkrana);
        glavnaScena.show();
    }

    private javafx.scene.layout.Background napraviGradijentnuPodlogu() {
        Stop[] tockeBoje = {
                new Stop(0, Color.web("#EFE7D8")),
                new Stop(0.5, Color.web("#E3D6BE")),
                new Stop(1, Color.web("#D8C9A8"))
        };
        LinearGradient gradijent = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, tockeBoje);
        return new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(gradijent, javafx.scene.layout.CornerRadii.EMPTY, Insets.EMPTY));
    }

    private HBox napraviPanelBrojaIgraca() {
        HBox panel = new HBox(12);
        panel.setAlignment(Pos.CENTER);

        Label oznaka = new Label("Broj igraca:");
        oznaka.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 14px; -fx-text-fill: #2B2520;");

        ComboBox<Integer> izbornikBrojaIgraca = new ComboBox<>();
        izbornikBrojaIgraca.getItems().addAll(2, 3, 4);
        izbornikBrojaIgraca.setValue(2);
        izbornikBrojaIgraca.setOnAction(dogadjaj -> {
            odabraniBrojIgraca = izbornikBrojaIgraca.getValue();
            azurirajIzbornikeUloga();
        });

        panel.getChildren().addAll(oznaka, izbornikBrojaIgraca);
        return panel;
    }

    private void azurirajIzbornikeUloga() {
        panelIzbornikaUloga.getChildren().clear();
        redoviPozicija.clear();
        odabraneUlogePoPoziciji.clear();

        int brojac = 0;
        while (brojac < odabraniBrojIgraca) {
            odabraneUlogePoPoziciji.add(NAZIVI_ULOGA[brojac % NAZIVI_ULOGA.length]);
            brojac = brojac + 1;
        }

        brojac = 0;
        while (brojac < odabraniBrojIgraca) {
            int pozicija = brojac;
            Label oznakaPozicije = new Label("Igrac " + (pozicija + 1) + ":");
            oznakaPozicije.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #2B2520;");

            HBox redKartica = napraviRedKarticaZaPoziciju(pozicija);
            VBox blokPozicije = new VBox(8, oznakaPozicije, redKartica);
            blokPozicije.setAlignment(Pos.CENTER);

            panelIzbornikaUloga.getChildren().add(blokPozicije);
            brojac = brojac + 1;
        }
    }

    private HBox napraviRedKarticaZaPoziciju(int pozicija) {
        HBox red = new HBox(12);
        red.setAlignment(Pos.CENTER);

        int brojac = 0;
        while (brojac < NAZIVI_ULOGA.length) {
            String nazivUloge = NAZIVI_ULOGA[brojac];
            String bojaHex = BOJE_ULOGA[brojac];
            String svgIkona = dohvatiSvgZaUlogu(brojac);
            boolean odabrana = odabraneUlogePoPoziciji.get(pozicija).equals(nazivUloge);

            VBox kartica = kreatorKartice.napraviKarticu(nazivUloge, bojaHex, svgIkona, odabrana);
            kartica.setOnMouseClicked(dogadjaj -> {
                odabraneUlogePoPoziciji.set(pozicija, nazivUloge);
                azurirajIzbornikeUloga();
            });

            red.getChildren().add(kartica);
            brojac = brojac + 1;
        }
        redoviPozicija.add(red);
        return red;
    }

    private String dohvatiSvgZaUlogu(int indeks) {
        if (indeks == 0) {
            return kreatorKartice.dohvatiIkonuRadnicke();
        } else if (indeks == 1) {
            return kreatorKartice.dohvatiIkonuSrednje();
        } else if (indeks == 2) {
            return kreatorKartice.dohvatiIkonuKapitalisticke();
        } else {
            return kreatorKartice.dohvatiIkonuVlade();
        }
    }

    private void obradiZapocniIgru() {
        if (!provjeraOdabira.provjeriJesuLiUlogeRazlicite(odabraneUlogePoPoziciji)) {
            prikaziUpozorenje("Svaka uloga mora biti odabrana samo jednom.");
            return;
        }

        if (!provjeraOdabira.sadrziVladu(odabraneUlogePoPoziciji)) {
            prikaziUpozorenje("Igra mora imati barem jednog igraca s ulogom Vlada.");
            return;
        }

        List<KlasaIgraca> listaIgraca = provjeraOdabira.napraviIgraceOdUloga(odabraneUlogePoPoziciji);
        akcijaPoOdabiru.accept(listaIgraca);
    }

    private void prikaziUpozorenje(String poruka) {
        Alert upozorenje = new Alert(Alert.AlertType.WARNING);
        upozorenje.setTitle("Neispravan odabir");
        upozorenje.setHeaderText(null);
        upozorenje.setContentText(poruka);
        upozorenje.showAndWait();
    }
}