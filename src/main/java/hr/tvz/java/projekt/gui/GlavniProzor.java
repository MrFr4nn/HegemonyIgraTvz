package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.logika.HegemonyEngine;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.util.Serijalizator;
import hr.tvz.java.projekt.util.XmlUpravitelj;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class GlavniProzor {

    private Stage glavnaScena;
    private HegemonyEngine engineIgre;
    private PrikazPloce prikazPloce;
    private KontrolePoteza kontrolePoteza;
    private UpraviteljAnimacija upraviteljAnimacija;
    private Serijalizator serijalizator;
    private UpraviteljTehnickeAnalize upraviteljTehnickeAnalize;
    private XmlUpravitelj xmlUpravitelj;
    private UpraviteljReplay upraviteljReplay;
    private UpraviteljGlasanja upraviteljGlasanja;

    private BorderPane korijenskiLayout;
    private VBox panelKontrolaTrenutniIgrac;
    private Label oznakaFazeIgre;
    private Label oznakaAnimacije;

    public GlavniProzor(Stage glavnaScena, List<KlasaIgraca> listaIgraca) {
        this.glavnaScena = glavnaScena;
        this.engineIgre = new HegemonyEngine(listaIgraca);
        this.prikazPloce = new PrikazPloce();
        this.kontrolePoteza = new KontrolePoteza();
        this.upraviteljAnimacija = new UpraviteljAnimacija();
        this.serijalizator = new Serijalizator();
        this.upraviteljTehnickeAnalize = new UpraviteljTehnickeAnalize(serijalizator);
        this.xmlUpravitelj = new XmlUpravitelj();
        this.xmlUpravitelj.pokreniNovuPovijest();
        this.upraviteljReplay = new UpraviteljReplay(xmlUpravitelj);
        this.upraviteljGlasanja = new UpraviteljGlasanja(engineIgre, kontrolePoteza, xmlUpravitelj);
    }

    public void prikaziProzor() {
        korijenskiLayout = new BorderPane();
        korijenskiLayout.setCenter(prikazPloce.napraviDrzavnuPlocu(engineIgre.getListaIgraca()));
        korijenskiLayout.setTop(napraviGornjiPanel());
        korijenskiLayout.setBottom(napraviDonjiPanel());

        azurirajPanelPotezaPremaFazi();

        Scene glavnaScenaPrikaza = new Scene(korijenskiLayout, 1000, 750);
        glavnaScena.setTitle("Hegemony: Lead Your Class to Victory - TVZ Projekt");
        glavnaScena.setScene(glavnaScenaPrikaza);
        glavnaScena.show();
    }

    private VBox napraviGornjiPanel() {
        VBox gornjiPanel = new VBox(8);
        gornjiPanel.setAlignment(Pos.CENTER);
        gornjiPanel.setPadding(new Insets(10));
        gornjiPanel.setStyle("-fx-background-color: #EFE7D8;");

        Label naslov = new Label("HEGEMONY: LEAD YOUR CLASS TO VICTORY");
        naslov.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2B2520;");

        oznakaFazeIgre = new Label(napraviTekstFaze());
        oznakaFazeIgre.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 12px; -fx-text-fill: #3A332B;");

        oznakaAnimacije = new Label("Spremno za pocetak igre.");
        oznakaAnimacije.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 11px; -fx-text-fill: #6B6357;");

        gornjiPanel.getChildren().addAll(naslov, oznakaFazeIgre, oznakaAnimacije);
        return gornjiPanel;
    }

    private String napraviTekstFaze() {
        return "Runda: " + engineIgre.getBrojRunde() + " / 5 | Faza: " + engineIgre.getTrenutnaFaza()
                + " | Na potezu: " + engineIgre.dohvatiIgracaNaPotezu().getNaziv();
    }

    private HBox napraviDonjiPanel() {
        HBox donjiPanel = new HBox(20);
        donjiPanel.setAlignment(Pos.CENTER);
        donjiPanel.setPadding(new Insets(15));
        donjiPanel.setStyle("-fx-background-color: #E3D9C4;");

        panelKontrolaTrenutniIgrac = new VBox();

        Button gumbSljedecaFaza = new Button("Sljedeca faza");
        StilGumba.primijeniNaglaseni(gumbSljedecaFaza);
        gumbSljedecaFaza.setOnAction(dogadjaj -> obradiSljedecuFazu());

        Button gumbSpremiStanje = new Button("Spremi stanje");
        StilGumba.primijeniNeutralni(gumbSpremiStanje);
        gumbSpremiStanje.setOnAction(dogadjaj -> serijalizator.spremiStanje(engineIgre.getListaIgraca()));

        Button gumbTehnickaAnaliza = new Button("Tehnicka usporedba");
        StilGumba.primijeniNeutralni(gumbTehnickaAnaliza);
        gumbTehnickaAnaliza.setOnAction(dogadjaj -> upraviteljTehnickeAnalize.otvoriProzorAnalize());

        Button gumbReplay = new Button("Pokreni Replay");
        StilGumba.primijeniNeutralni(gumbReplay);
        gumbReplay.setOnAction(dogadjaj -> upraviteljReplay.otvoriProzorReplaya());

        VBox blokGumbova = new VBox(8, gumbSljedecaFaza, gumbSpremiStanje, gumbTehnickaAnaliza, gumbReplay);

        donjiPanel.getChildren().addAll(panelKontrolaTrenutniIgrac, blokGumbova);
        return donjiPanel;
    }

    private void azurirajPanelPotezaPremaFazi() {
        panelKontrolaTrenutniIgrac.getChildren().clear();
        String faza = engineIgre.getTrenutnaFaza();

        if (faza.equals(HegemonyEngine.FAZA_PROIZVODNJA)) {
            prikaziIzvjestaj("Faza Proizvodnje", engineIgre.obradiFazuProizvodnje());
        } else if (faza.equals(HegemonyEngine.FAZA_POTROSNJA)) {
            prikaziIzvjestaj("Faza Potrosnje", engineIgre.obradiFazuPotrosnje());
        } else if (faza.equals(HegemonyEngine.FAZA_GLASANJE)) {
            upraviteljGlasanja.prikaziPanelGlasanja(panelKontrolaTrenutniIgrac, this::azurirajPanelPotezaPremaFazi);
        } else if (faza.equals(HegemonyEngine.FAZA_KRAJ_RUNDE) || faza.equals(HegemonyEngine.FAZA_PRIPREMA)) {
            Label oznaka = new Label("Faza " + faza + " - kliknite Sljedeca faza za nastavak.");
            panelKontrolaTrenutniIgrac.getChildren().add(oznaka);
        } else {
            KlasaIgraca igracNaPotezu = engineIgre.dohvatiIgracaNaPotezu();
            VBox kontrole = kontrolePoteza.napraviKontroleZaIgraca(engineIgre, igracNaPotezu,
                    () -> upraviteljGlasanja.obradiPrijedlogZakona(igracNaPotezu.getNaziv(), "Promjena ekonomske politike"),
                    this::obradiPotezIgraca);
            panelKontrolaTrenutniIgrac.getChildren().add(kontrole);
        }
    }

    private void prikaziIzvjestaj(String naslovIzvjestaja, String sadrzaj) {
        Label naslov = new Label(naslovIzvjestaja);
        TextArea poljeIzvjestaja = new TextArea(sadrzaj);
        poljeIzvjestaja.setEditable(false);
        poljeIzvjestaja.setPrefHeight(120);
        panelKontrolaTrenutniIgrac.getChildren().addAll(naslov, poljeIzvjestaja);
    }

    private void obradiPotezIgraca() {
        prikazPloce.azurirajPrikaz(engineIgre.getListaIgraca());
        if (engineIgre.jeIgracNaPotezuOdigraoSveApove()) {
            engineIgre.prebaciPotez();
        }
        azurirajPanelPotezaPremaFazi();
    }

    private void obradiSljedecuFazu() {
        engineIgre.prebaciNaSljedecuFazu();
        oznakaFazeIgre.setText(napraviTekstFaze());

        if (engineIgre.getTrenutnaFaza().equals(HegemonyEngine.FAZA_PRIPREMA)) {
            upraviteljAnimacija.pokreniAnimacijuDonosenjaZakona(oznakaAnimacije, "Nova runda zapoceta");
            upraviteljGlasanja.resetirajPoziciju();
        }

        prikazPloce.azurirajPrikaz(engineIgre.getListaIgraca());
        azurirajPanelPotezaPremaFazi();

        if (engineIgre.provjeriPobjedu()) {
            prikaziObavijestKraja();
        }
    }

    private void prikaziObavijestKraja() {
        Alert obavijest = new Alert(Alert.AlertType.INFORMATION);
        obavijest.setTitle("Kraj igre");
        obavijest.setHeaderText("Igra je zavrsena!");
        obavijest.setContentText("Pobjednik je: " + engineIgre.dohvatiPobjednika());
        obavijest.showAndWait();
    }
}