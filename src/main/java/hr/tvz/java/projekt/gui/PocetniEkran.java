package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.model.KlasaIgraca;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PocetniEkran {

    private Stage glavnaScena;
    private Consumer<List<KlasaIgraca>> akcijaPoOdabiru;
    private ProvjeraOdabira provjeraOdabira;
    private KreatorKarticeUloge kreatorKarticeUloge;
    private KreatorSazetkaOdabira kreatorSazetka;
    private int odabraniBrojIgraca;
    private List<String> odabraneUlogePoPoziciji;
    private VBox panelIzbornikaUloga;
    private Label oznakaBrojacaPopunjenosti;
    private Label oznakaSazetka;

    private static final String[] NAZIVI_ULOGA = {"Radnicka klasa", "Srednja klasa", "Kapitalisticka klasa", "Vlada"};
    private static final String[] BOJE_ULOGA = {"#8C3A36", "#A6862C", "#2B4C70", "#4A4458"};

    public PocetniEkran(Stage glavnaScena, Consumer<List<KlasaIgraca>> akcijaPoOdabiru) {
        this.glavnaScena = glavnaScena;
        this.akcijaPoOdabiru = akcijaPoOdabiru;
        this.provjeraOdabira = new ProvjeraOdabira();
        this.kreatorKarticeUloge = new KreatorKarticeUloge();
        this.kreatorSazetka = new KreatorSazetkaOdabira();
        this.odabraniBrojIgraca = 2;
        this.odabraneUlogePoPoziciji = new ArrayList<>();
        postaviPocetneUloge();
    }

    private void postaviPocetneUloge() {
        odabraneUlogePoPoziciji.clear();
        int brojac = 0;
        while (brojac < odabraniBrojIgraca) {
            odabraneUlogePoPoziciji.add(NAZIVI_ULOGA[brojac % NAZIVI_ULOGA.length]);
            brojac = brojac + 1;
        }
    }

    public void prikaziEkran() {
        VBox korijenskiLayout = new VBox(18);
        korijenskiLayout.setPadding(new Insets(30));
        korijenskiLayout.setAlignment(Pos.TOP_CENTER);
        korijenskiLayout.setBackground(kreatorSazetka.napraviGradijentnuPodlogu());

        Label naslov = new Label("HEGEMONY: LEAD YOUR CLASS TO VICTORY");
        naslov.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #2B2520;");

        Label podnaslov = new Label("Odaberite broj igraca, zatim kliknite karticu za odabir uloge svakog igraca");
        podnaslov.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 13px; -fx-text-fill: #4A4438;");

        HBox panelBrojaIgraca = kreatorSazetka.napraviPanelBrojaIgraca(odabraniBrojIgraca, novaVrijednost -> {
            odabraniBrojIgraca = novaVrijednost;
            postaviPocetneUloge();
            azurirajIzbornikeUloga();
        });

        oznakaBrojacaPopunjenosti = kreatorSazetka.napraviOznakuBrojaca();
        panelIzbornikaUloga = new VBox(15);
        panelIzbornikaUloga.setAlignment(Pos.CENTER);
        oznakaSazetka = kreatorSazetka.napraviOznakuSazetka();

        Button gumbZapocni = new Button("Zapocni igru");
        StilGumba.primijeniNaglaseni(gumbZapocni);
        gumbZapocni.setStyle(gumbZapocni.getStyle() + "-fx-font-size: 15px; -fx-padding: 12 30 12 30;");
        gumbZapocni.setOnAction(dogadjaj -> obradiZapocniIgru());

        azurirajIzbornikeUloga();

        korijenskiLayout.getChildren().addAll(naslov, podnaslov, panelBrojaIgraca, oznakaBrojacaPopunjenosti,
                panelIzbornikaUloga, oznakaSazetka, gumbZapocni);

        Scene scenaPocetnogEkrana = new Scene(korijenskiLayout, 1000, 780);
        glavnaScena.setTitle("Hegemony - Postavke igre");
        glavnaScena.setScene(scenaPocetnogEkrana);
        glavnaScena.show();
    }

    private void azurirajIzbornikeUloga() {
        panelIzbornikaUloga.getChildren().clear();

        int brojac = 0;
        while (brojac < odabraniBrojIgraca) {
            int pozicija = brojac;
            Label oznakaPozicije = new Label("Igrac " + (pozicija + 1) + ":");
            oznakaPozicije.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #2B2520;");

            HBox redKartica = napraviRedKarticaZaPoziciju(pozicija);
            VBox blokPozicije = new VBox(8, oznakaPozicije, redKartica);
            blokPozicije.setAlignment(Pos.CENTER);

            panelIzbornikaUloga.getChildren().add(blokPozicije);

            if (brojac < odabraniBrojIgraca - 1) {
                Separator separator = new Separator();
                separator.setStyle("-fx-background-color: #C9B896;");
                panelIzbornikaUloga.getChildren().add(separator);
            }
            brojac = brojac + 1;
        }

        kreatorSazetka.azurirajBrojacPopunjenosti(oznakaBrojacaPopunjenosti, odabraniBrojIgraca);
        kreatorSazetka.azurirajSazetak(oznakaSazetka, odabraneUlogePoPoziciji);
    }

    private HBox napraviRedKarticaZaPoziciju(int pozicija) {
        HBox red = new HBox(12);
        red.setAlignment(Pos.CENTER);

        int brojac = 0;
        while (brojac < NAZIVI_ULOGA.length) {
            String nazivUloge = NAZIVI_ULOGA[brojac];
            String bojaHex = BOJE_ULOGA[brojac];
            String svgIkona = dohvatiSvgZaUlogu(brojac);
            String opisUloge = dohvatiOpisZaUlogu(brojac);
            boolean odabrana = odabraneUlogePoPoziciji.get(pozicija).equals(nazivUloge);

            VBox kartica = kreatorKarticeUloge.napraviKarticu(nazivUloge, opisUloge, bojaHex, svgIkona, odabrana);
            kartica.setOnMouseClicked(dogadjaj -> {
                odabraneUlogePoPoziciji.set(pozicija, nazivUloge);
                kreatorKarticeUloge.animirajOdabir(kartica);
                azurirajIzbornikeUloga();
            });

            red.getChildren().add(kartica);
            brojac = brojac + 1;
        }
        return red;
    }

    private String dohvatiSvgZaUlogu(int indeks) {
        if (indeks == 0) {
            return kreatorKarticeUloge.dohvatiIkonuRadnicke();
        } else if (indeks == 1) {
            return kreatorKarticeUloge.dohvatiIkonuSrednje();
        } else if (indeks == 2) {
            return kreatorKarticeUloge.dohvatiIkonuKapitalisticke();
        } else {
            return kreatorKarticeUloge.dohvatiIkonuVlade();
        }
    }

    private String dohvatiOpisZaUlogu(int indeks) {
        if (indeks == 0) {
            return kreatorKarticeUloge.dohvatiOpisRadnicke();
        } else if (indeks == 1) {
            return kreatorKarticeUloge.dohvatiOpisSrednje();
        } else if (indeks == 2) {
            return kreatorKarticeUloge.dohvatiOpisKapitalisticke();
        } else {
            return kreatorKarticeUloge.dohvatiOpisVlade();
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