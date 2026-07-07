package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.model.KlasaIgraca;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PocetniEkran {

    private static final String[] NAZIVI_ULOGA = {"Radnicka klasa", "Srednja klasa", "Kapitalisticka klasa", "Vlada"};
    private static final String[] BOJE_ULOGA = {"#FF3B5C", "#F5D400", "#00F5A0", "#00D4FF"};

    @FXML
    private HBox panelBrojaIgracaDrzac;
    @FXML
    private VBox panelIzbornikaUloga;
    @FXML
    private Label oznakaBrojacaPopunjenosti;
    @FXML
    private Label oznakaSazetka;
    @FXML
    private Button gumbZapocni;

    private Stage glavnaScena;
    private Consumer<List<KlasaIgraca>> akcijaPoOdabiru;
    private ProvjeraOdabira provjeraOdabira;
    private KreatorKarticeUloge kreatorKarticeUloge;
    private KreatorSazetkaOdabira kreatorSazetka;
    private int odabraniBrojIgraca;
    private List<String> odabraneUlogePoPoziciji;

    public PocetniEkran() {
        this.provjeraOdabira = new ProvjeraOdabira();
        this.kreatorKarticeUloge = new KreatorKarticeUloge();
        this.kreatorSazetka = new KreatorSazetkaOdabira();
        this.odabraniBrojIgraca = 2;
        this.odabraneUlogePoPoziciji = new ArrayList<>();
    }

    public void prikaziEkran(Stage glavnaScena, Consumer<List<KlasaIgraca>> akcijaPoOdabiru) {
        try {
            FXMLLoader ucitavac = new FXMLLoader(getClass().getResource("PocetniEkran.fxml"));
            Parent korijen = ucitavac.load();

            PocetniEkran kontroler = ucitavac.getController();
            kontroler.postaviPocetnePodatke(glavnaScena, akcijaPoOdabiru);

            Scene scenaPocetnogEkrana = new Scene(korijen, 1000, 780);
            glavnaScena.setTitle("Hegemony - Postavke igre");
            glavnaScena.setScene(scenaPocetnogEkrana);
            glavnaScena.show();
        } catch (IOException greska) {
            throw new IllegalStateException("Ne mogu ucitati PocetniEkran.fxml", greska);
        }
    }

    private void postaviPocetnePodatke(Stage glavnaScena, Consumer<List<KlasaIgraca>> akcijaPoOdabiru) {
        this.glavnaScena = glavnaScena;
        this.akcijaPoOdabiru = akcijaPoOdabiru;
        StilGumba.primijeniNaglaseniVeliki(gumbZapocni);
        inicijalizirajSucelje();
    }

    private void postaviPocetneUloge() {
        odabraneUlogePoPoziciji.clear();
        int brojac = 0;
        while (brojac < odabraniBrojIgraca) {
            odabraneUlogePoPoziciji.add(NAZIVI_ULOGA[brojac % NAZIVI_ULOGA.length]);
            brojac = brojac + 1;
        }
    }

    private void inicijalizirajSucelje() {
        postaviPocetneUloge();

        HBox panelBrojaIgraca = kreatorSazetka.napraviPanelBrojaIgraca(odabraniBrojIgraca, novaVrijednost -> {
            odabraniBrojIgraca = novaVrijednost;
            postaviPocetneUloge();
            azurirajIzbornikeUloga();
        });
        panelBrojaIgracaDrzac.getChildren().add(panelBrojaIgraca);

        azurirajIzbornikeUloga();
    }

    private void azurirajIzbornikeUloga() {
        panelIzbornikaUloga.getChildren().clear();

        int brojac = 0;
        while (brojac < odabraniBrojIgraca) {
            int pozicija = brojac;
            Label oznakaPozicije = new Label("IGRAC " + (pozicija + 1) + ":");
            oznakaPozicije.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: "
                    + StilGumba.TEKST_SVIJETLI + ";");

            HBox redKartica = napraviRedKarticaZaPoziciju(pozicija);
            VBox blokPozicije = new VBox(8, oznakaPozicije, redKartica);
            blokPozicije.setAlignment(Pos.CENTER);

            panelIzbornikaUloga.getChildren().add(blokPozicije);

            if (brojac < odabraniBrojIgraca - 1) {
                Separator separator = new Separator();
                separator.setStyle("-fx-background-color: " + StilGumba.TEKST_SIVI + ";");
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
        return switch (indeks) {
            case 0 -> kreatorKarticeUloge.dohvatiIkonuRadnicke();
            case 1 -> kreatorKarticeUloge.dohvatiIkonuSrednje();
            case 2 -> kreatorKarticeUloge.dohvatiIkonuKapitalisticke();
            default -> kreatorKarticeUloge.dohvatiIkonuVlade();
        };
    }

    private String dohvatiOpisZaUlogu(int indeks) {
        return switch (indeks) {
            case 0 -> kreatorKarticeUloge.dohvatiOpisRadnicke();
            case 1 -> kreatorKarticeUloge.dohvatiOpisSrednje();
            case 2 -> kreatorKarticeUloge.dohvatiOpisKapitalisticke();
            default -> kreatorKarticeUloge.dohvatiOpisVlade();
        };
    }

    @FXML
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