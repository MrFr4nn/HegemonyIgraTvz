package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.model.KapitalistickaKlasa;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.SrednjaKlasa;
import hr.tvz.java.projekt.model.Vlada;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PocetniEkran {

    private Stage glavnaScena;
    private Consumer<List<KlasaIgraca>> akcijaPoOdabiru;
    private int odabraniBrojIgraca;
    private List<ComboBox<String>> listaIzbornika;
    private VBox panelIzbornikaUloga;

    public PocetniEkran(Stage glavnaScena, Consumer<List<KlasaIgraca>> akcijaPoOdabiru) {
        this.glavnaScena = glavnaScena;
        this.akcijaPoOdabiru = akcijaPoOdabiru;
        this.odabraniBrojIgraca = 2;
        this.listaIzbornika = new ArrayList<>();
    }

    public void prikaziEkran() {
        VBox korijenskiLayout = new VBox(15);
        korijenskiLayout.setPadding(new Insets(25));
        korijenskiLayout.setAlignment(Pos.TOP_CENTER);

        Label naslov = new Label("HEGEMONY: LEAD YOUR CLASS TO VICTORY");
        Label podnaslov = new Label("Odaberite broj igraca i ulogu za svakog igraca");

        HBox panelBrojaIgraca = napraviPanelBrojaIgraca();

        panelIzbornikaUloga = new VBox(8);
        panelIzbornikaUloga.setAlignment(Pos.CENTER);
        azurirajIzbornikeUloga();

        Button gumbZapocni = new Button("Zapocni igru");
        gumbZapocni.setOnAction(dogadjaj -> obradiZapocniIgru());

        korijenskiLayout.getChildren().addAll(naslov, podnaslov, panelBrojaIgraca, panelIzbornikaUloga, gumbZapocni);

        Scene scenaPocetnogEkrana = new Scene(korijenskiLayout, 600, 500);
        glavnaScena.setTitle("Hegemony - Postavke igre");
        glavnaScena.setScene(scenaPocetnogEkrana);
        glavnaScena.show();
    }

    private HBox napraviPanelBrojaIgraca() {
        HBox panel = new HBox(10);
        panel.setAlignment(Pos.CENTER);

        Label oznaka = new Label("Broj igraca:");
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
            HBox redak = new HBox(10);
            redak.setAlignment(Pos.CENTER);

            Label oznakaPozicije = new Label("Igrac " + (brojac + 1) + ":");
            ComboBox<String> izbornikUloge = new ComboBox<>();
            izbornikUloge.getItems().addAll("Radnicka klasa", "Srednja klasa", "Kapitalisticka klasa", "Vlada");
            izbornikUloge.setValue(odrediZadanuUlogu(brojac));

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

        if (!provjeriJesuLiUlogeRazlicite(odabraneUloge)) {
            prikaziUpozorenje("Svaka uloga mora biti odabrana samo jednom.");
            return;
        }

        if (!odabraneUloge.contains("Vlada")) {
            prikaziUpozorenje("Igra mora imati barem jednog igraca s ulogom Vlada.");
            return;
        }

        List<KlasaIgraca> listaIgraca = napraviIgraceOdUloga(odabraneUloge);
        akcijaPoOdabiru.accept(listaIgraca);
    }

    private boolean provjeriJesuLiUlogeRazlicite(List<String> odabraneUloge) {
        int brojac = 0;
        while (brojac < odabraneUloge.size()) {
            int drugiBrojac = brojac + 1;
            while (drugiBrojac < odabraneUloge.size()) {
                if (odabraneUloge.get(brojac).equals(odabraneUloge.get(drugiBrojac))) {
                    return false;
                }
                drugiBrojac = drugiBrojac + 1;
            }
            brojac = brojac + 1;
        }
        return true;
    }

    private List<KlasaIgraca> napraviIgraceOdUloga(List<String> odabraneUloge) {
        List<KlasaIgraca> listaIgraca = new ArrayList<>();
        int brojac = 0;
        while (brojac < odabraneUloge.size()) {
            String uloga = odabraneUloge.get(brojac);
            String nazivIgraca = uloga + " - Igrac " + (brojac + 1);

            if (uloga.equals("Radnicka klasa")) {
                listaIgraca.add(new RadnickaKlasa(nazivIgraca));
            } else if (uloga.equals("Srednja klasa")) {
                listaIgraca.add(new SrednjaKlasa(nazivIgraca));
            } else if (uloga.equals("Kapitalisticka klasa")) {
                listaIgraca.add(new KapitalistickaKlasa(nazivIgraca));
            } else {
                listaIgraca.add(new Vlada(nazivIgraca));
            }
            brojac = brojac + 1;
        }
        return listaIgraca;
    }

    private void prikaziUpozorenje(String poruka) {
        Alert upozorenje = new Alert(Alert.AlertType.WARNING);
        upozorenje.setTitle("Neispravan odabir");
        upozorenje.setHeaderText(null);
        upozorenje.setContentText(poruka);
        upozorenje.showAndWait();
    }
}