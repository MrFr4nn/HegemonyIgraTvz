package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.model.KlasaIgraca;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PocetniEkran {

    private static final String BOJA_PODLOGE = "#EFE7D8";

    private Stage glavnaScena;
    private Consumer<List<KlasaIgraca>> akcijaPoOdabiru;
    private ProvjeraOdabira provjeraOdabira;
    private int odabraniBrojIgraca;
    private List<ComboBox<String>> listaIzbornika;
    private VBox panelIzbornikaUloga;

    public PocetniEkran(Stage glavnaScena, Consumer<List<KlasaIgraca>> akcijaPoOdabiru) {
        this.glavnaScena = glavnaScena;
        this.akcijaPoOdabiru = akcijaPoOdabiru;
        this.provjeraOdabira = new ProvjeraOdabira();
        this.odabraniBrojIgraca = 2;
        this.listaIzbornika = new ArrayList<>();
    }

    public void prikaziEkran() {
        VBox korijenskiLayout = new VBox(20);
        korijenskiLayout.setPadding(new Insets(35));
        korijenskiLayout.setAlignment(Pos.TOP_CENTER);
        korijenskiLayout.setBackground(new Background(new BackgroundFill(Color.web(BOJA_PODLOGE), CornerRadii.EMPTY, Insets.EMPTY)));

        Label naslov = new Label("HEGEMONY: LEAD YOUR CLASS TO VICTORY");
        naslov.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2B2520;");

        Label podnaslov = new Label("Odaberite broj igraca i ulogu za svakog igraca");
        podnaslov.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 13px; -fx-text-fill: #6B6357;");

        VBox panelPostavki = napraviPanelPostavki();

        Button gumbZapocni = new Button("Zapocni igru");
        StilGumba.primijeniNaglaseni(gumbZapocni);
        gumbZapocni.setOnAction(dogadjaj -> obradiZapocniIgru());

        korijenskiLayout.getChildren().addAll(naslov, podnaslov, panelPostavki, gumbZapocni);

        Scene scenaPocetnogEkrana = new Scene(korijenskiLayout, 600, 520);
        glavnaScena.setTitle("Hegemony - Postavke igre");
        glavnaScena.setScene(scenaPocetnogEkrana);
        glavnaScena.show();
    }

    private VBox napraviPanelPostavki() {
        VBox panelPostavki = new VBox(18);
        panelPostavki.setAlignment(Pos.TOP_CENTER);
        panelPostavki.setPadding(new Insets(25));
        panelPostavki.setMaxWidth(420);
        panelPostavki.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));

        HBox panelBrojaIgraca = napraviPanelBrojaIgraca();

        panelIzbornikaUloga = new VBox(10);
        panelIzbornikaUloga.setAlignment(Pos.CENTER);
        azurirajIzbornikeUloga();

        panelPostavki.getChildren().addAll(panelBrojaIgraca, panelIzbornikaUloga);
        return panelPostavki;
    }

    private HBox napraviPanelBrojaIgraca() {
        HBox panel = new HBox(12);
        panel.setAlignment(Pos.CENTER);

        Label oznaka = new Label("Broj igraca:");
        oznaka.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 13px; -fx-text-fill: #2B2520;");

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
        listaIzbornika.clear();

        int brojac = 0;
        while (brojac < odabraniBrojIgraca) {
            HBox redak = new HBox(12);
            redak.setAlignment(Pos.CENTER);

            Label oznakaPozicije = new Label("Igrac " + (brojac + 1) + ":");
            oznakaPozicije.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 12px; -fx-text-fill: #2B2520; -fx-min-width: 70;");

            ComboBox<String> izbornikUloge = new ComboBox<>();
            izbornikUloge.getItems().addAll("Radnicka klasa", "Srednja klasa", "Kapitalisticka klasa", "Vlada");
            izbornikUloge.setValue(odrediZadanuUlogu(brojac));
            izbornikUloge.setPrefWidth(220);

            listaIzbornika.add(izbornikUloge);
            redak.getChildren().addAll(oznakaPozicije, izbornikUloge);
            panelIzbornikaUloga.getChildren().add(redak);
            brojac = brojac + 1;
        }
    }

    private String odrediZadanuUlogu(int pozicija) {
        if (pozicija == 0) {
            return "Radnicka klasa";
        } else if (pozicija == 1) {
            return "Vlada";
        } else if (pozicija == 2) {
            return "Srednja klasa";
        } else {
            return "Kapitalisticka klasa";
        }
    }

    private void obradiZapocniIgru() {
        List<String> odabraneUloge = new ArrayList<>();
        int brojac = 0;
        while (brojac < listaIzbornika.size()) {
            odabraneUloge.add(listaIzbornika.get(brojac).getValue());
            brojac = brojac + 1;
        }

        if (!provjeraOdabira.provjeriJesuLiUlogeRazlicite(odabraneUloge)) {
            prikaziUpozorenje("Svaka uloga mora biti odabrana samo jednom.");
            return;
        }

        if (!provjeraOdabira.sadrziVladu(odabraneUloge)) {
            prikaziUpozorenje("Igra mora imati barem jednog igraca s ulogom Vlada.");
            return;
        }

        List<KlasaIgraca> listaIgraca = provjeraOdabira.napraviIgraceOdUloga(odabraneUloge);
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