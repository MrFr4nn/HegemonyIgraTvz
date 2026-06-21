package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.logika.HegemonyEngine;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.util.Serijalizator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    private BorderPane korijenskiLayout;
    private VBox panelKontrolaTrenutniIgrac;
    private Label oznakaFazeIgre;
    private Label oznakaAnimacije;
    private int pozicijaGlasacaUNizu;

    public GlavniProzor(Stage glavnaScena, List<KlasaIgraca> listaIgraca) {
        this.glavnaScena = glavnaScena;
        this.engineIgre = new HegemonyEngine(listaIgraca);
        this.prikazPloce = new PrikazPloce();
        this.kontrolePoteza = new KontrolePoteza();
        this.upraviteljAnimacija = new UpraviteljAnimacija();
        this.serijalizator = new Serijalizator();
        this.upraviteljTehnickeAnalize = new UpraviteljTehnickeAnalize(serijalizator);
        this.pozicijaGlasacaUNizu = 0;
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

        Label naslov = new Label("HEGEMONY: LEAD YOUR CLASS TO VICTORY");
        oznakaFazeIgre = new Label(napraviTekstFaze());
        oznakaAnimacije = new Label("Spremno za pocetak igre.");

        gornjiPanel.getChildren().addAll(naslov, oznakaFazeIgre, oznakaAnimacije);
        return gornjiPanel;
    }

    private String napraviTekstFaze() {
        return "Runda: " + engineIgre.getBrojRunde() + " | Faza: " + engineIgre.getTrenutnaFaza()
                + " | Na potezu: " + engineIgre.dohvatiIgracaNaPotezu().getNaziv();
    }

    private HBox napraviDonjiPanel() {
        HBox donjiPanel = new HBox(20);
        donjiPanel.setAlignment(Pos.CENTER);
        donjiPanel.setPadding(new Insets(10));

        panelKontrolaTrenutniIgrac = new VBox();

        Button gumbSljedecaFaza = new Button("Sljedeca faza");
        gumbSljedecaFaza.setOnAction(dogadjaj -> obradiSljedecuFazu());

        Button gumbSpremiStanje = new Button("Spremi stanje");
        gumbSpremiStanje.setOnAction(dogadjaj -> serijalizator.spremiStanje(engineIgre.getListaIgraca()));

        Button gumbTehnickaAnaliza = new Button("Tehnicka usporedba");
        gumbTehnickaAnaliza.setOnAction(dogadjaj -> upraviteljTehnickeAnalize.otvoriProzorAnalize());

        VBox blokGumbova = new VBox(8, gumbSljedecaFaza, gumbSpremiStanje, gumbTehnickaAnaliza);

        donjiPanel.getChildren().addAll(panelKontrolaTrenutniIgrac, blokGumbova);
        return donjiPanel;
    }

    private void azurirajPanelPotezaPremaFazi() {
        panelKontrolaTrenutniIgrac.getChildren().clear();

        if (engineIgre.getTrenutnaFaza().equals(HegemonyEngine.FAZA_GLASANJE)) {
            prikaziPanelGlasanja();
        } else {
            KlasaIgraca igracNaPotezu = engineIgre.dohvatiIgracaNaPotezu();
            VBox kontrole = kontrolePoteza.napraviKontroleZaIgraca(igracNaPotezu, this::obradiPotezIgraca);
            panelKontrolaTrenutniIgrac.getChildren().add(kontrole);
        }
    }

    private void prikaziPanelGlasanja() {
        if (engineIgre.getTrenutnoGlasanje() == null) {
            VBox panelPrijedloga = kontrolePoteza.napraviPanelPrijedlogaZakona(
                    () -> obradiPrijedlogZakona("Zakon o porezu"),
                    () -> obradiPrijedlogZakona("Zakon o minimalnoj placi")
            );
            panelKontrolaTrenutniIgrac.getChildren().add(panelPrijedloga);
            return;
        }

        if (engineIgre.getTrenutnoGlasanje().isGlasanjeZavrseno()) {
            Label oznakaIshoda = new Label(engineIgre.getTrenutnoGlasanje().ispisiRezultatGlasanja());
            panelKontrolaTrenutniIgrac.getChildren().add(oznakaIshoda);
            return;
        }

        List<KlasaIgraca> listaIgraca = engineIgre.getListaIgraca();
        while (pozicijaGlasacaUNizu < listaIgraca.size() && listaIgraca.get(pozicijaGlasacaUNizu) == engineIgre.getVlada()) {
            pozicijaGlasacaUNizu = pozicijaGlasacaUNizu + 1;
        }

        if (pozicijaGlasacaUNizu >= listaIgraca.size()) {
            engineIgre.zatvoriTrenutnoGlasanje();
            azurirajPanelPotezaPremaFazi();
            return;
        }

        KlasaIgraca trenutniGlasac = listaIgraca.get(pozicijaGlasacaUNizu);
        VBox panelGlasanja = kontrolePoteza.napraviPanelGlasanja(
                engineIgre.getTrenutnoGlasanje().getNazivZakona(),
                () -> obradiGlas(trenutniGlasac.getNaziv(), true),
                () -> obradiGlas(trenutniGlasac.getNaziv(), false)
        );
        Label oznakaTko = new Label("Glasa: " + trenutniGlasac.getNaziv());
        panelKontrolaTrenutniIgrac.getChildren().clear();
        panelKontrolaTrenutniIgrac.getChildren().addAll(oznakaTko, panelGlasanja);
    }

    private void obradiPrijedlogZakona(String nazivZakona) {
        engineIgre.pokreniNovoGlasanje(nazivZakona);
        pozicijaGlasacaUNizu = 0;
        azurirajPanelPotezaPremaFazi();
    }

    private void obradiGlas(String nazivIgraca, boolean glasZa) {
        engineIgre.zabrojiGlasIgraca(nazivIgraca, glasZa);
        pozicijaGlasacaUNizu = pozicijaGlasacaUNizu + 1;
        azurirajPanelPotezaPremaFazi();
    }

    private void obradiPotezIgraca() {
        upraviteljAnimacija.pokreniAsinkronoAzuriranjeEkonomije(
                () -> System.out.println("Azuriranje ekonomskih podataka u pozadinskoj niti."),
                () -> prikazPloce.azurirajPrikaz(engineIgre.getListaIgraca())
        );
        engineIgre.prebaciPotez();
        oznakaFazeIgre.setText(napraviTekstFaze());
        azurirajPanelPotezaPremaFazi();
    }

    private void obradiSljedecuFazu() {
        engineIgre.prebaciNaSljedecuFazu();
        oznakaFazeIgre.setText(napraviTekstFaze());

        if (engineIgre.getTrenutnaFaza().equals(HegemonyEngine.FAZA_PRIPREMA)) {
            upraviteljAnimacija.pokreniAnimacijuDonosenjaZakona(oznakaAnimacije, "Nova runda zapoceta");
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