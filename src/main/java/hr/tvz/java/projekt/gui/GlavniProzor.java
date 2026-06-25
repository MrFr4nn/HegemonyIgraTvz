package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.logika.HegemonyEngine;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.util.Serijalizator;
import hr.tvz.java.projekt.util.XmlUpravitelj;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
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
    private KreatorAkcijskeTrake kreatorAkcijskeTrake;
    private KreatorIzvjestaja kreatorIzvjestaja;
    private KreatorZavrsnogEkrana kreatorZavrsnogEkrana;

    private VBox panelKontrolaTrenutniIgrac;
    private Label oznakaFazeIgre;
    private Label oznakaApBrojaca;
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
        this.kreatorAkcijskeTrake = new KreatorAkcijskeTrake();
        this.kreatorIzvjestaja = new KreatorIzvjestaja();
        this.kreatorZavrsnogEkrana = new KreatorZavrsnogEkrana();
    }

    public void prikaziProzor() {
        BorderPane korijenskiLayout = new BorderPane();
        korijenskiLayout.setStyle("-fx-background-color: " + StilGumba.POZADINA_TAMNA + ";");

        HBox plocaIgraca = prikazPloce.napraviDrzavnuPlocu(engineIgre.getListaIgraca());
        plocaIgraca.setAlignment(Pos.CENTER);
        HBox omotacPloce = new HBox(plocaIgraca);
        omotacPloce.setAlignment(Pos.CENTER);
        omotacPloce.setStyle("-fx-background-color: " + StilGumba.POZADINA_TAMNA + ";");

        ScrollPane skrolnaPloca = new ScrollPane(omotacPloce);
        skrolnaPloca.setFitToHeight(true);
        skrolnaPloca.setFitToWidth(true);
        skrolnaPloca.setStyle("-fx-background-color: transparent;");

        oznakaFazeIgre = new Label(napraviTekstFaze());
        oznakaAnimacije = new Label("Spremno za pocetak igre.");

        korijenskiLayout.setCenter(skrolnaPloca);
        korijenskiLayout.setTop(kreatorAkcijskeTrake.napraviGornjiPanel(oznakaFazeIgre, oznakaAnimacije));
        korijenskiLayout.setBottom(napraviAkcijskuTraku());

        azurirajPanelPotezaPremaFazi();

        Scene glavnaScenaPrikaza = new Scene(korijenskiLayout, 1100, 800);
        glavnaScena.setTitle("Hegemony: Lead Your Class to Victory - TVZ Projekt");
        glavnaScena.setScene(glavnaScenaPrikaza);
        glavnaScena.show();
    }

    private String napraviTekstFaze() {
        return "RUNDA: " + engineIgre.getBrojRunde() + " / 5  |  FAZA: " + pretvoriNazivFazeZaPrikaz(engineIgre.getTrenutnaFaza())
                + "  |  NA POTEZU: " + engineIgre.dohvatiIgracaNaPotezu().getNaziv().toUpperCase();
    }

    private String pretvoriNazivFazeZaPrikaz(String nazivFaze) {
        return nazivFaze.replace("_", " ");
    }

    private VBox napraviAkcijskuTraku() {
        oznakaApBrojaca = new Label(napraviTekstApBrojaca());
        azurirajStilApBrojaca();

        panelKontrolaTrenutniIgrac = new VBox(10);
        panelKontrolaTrenutniIgrac.setAlignment(Pos.CENTER);

        HBox redAlatnihGumbova = kreatorAkcijskeTrake.napraviRedAlatnihGumbova(
                this::obradiSljedecuFazu,
                () -> serijalizator.spremiStanje(engineIgre.getListaIgraca()),
                () -> upraviteljTehnickeAnalize.otvoriProzorAnalize(),
                () -> upraviteljReplay.otvoriProzorReplaya()
        );

        return kreatorAkcijskeTrake.napraviAkcijskuTraku(oznakaApBrojaca, panelKontrolaTrenutniIgrac, redAlatnihGumbova);
    }

    private String napraviTekstApBrojaca() {
        if (!engineIgre.getTrenutnaFaza().equals(HegemonyEngine.FAZA_AKCIJA)) {
            return "";
        }
        return "AKCIJSKI BODOVI PREOSTALO: " + engineIgre.dohvatiPreostaleApTrenutnogIgraca() + " / 5";
    }

    private void azurirajStilApBrojaca() {
        String boja = StilGumba.dohvatiBojuKlase(engineIgre.dohvatiIgracaNaPotezu());
        oznakaApBrojaca.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + boja
                + "; -fx-effect: dropshadow(gaussian, " + boja + ", 10, 0.4, 0, 0);");
    }

    private void azurirajPanelPotezaPremaFazi() {
        panelKontrolaTrenutniIgrac.getChildren().clear();
        String faza = engineIgre.getTrenutnaFaza();
        oznakaApBrojaca.setText(napraviTekstApBrojaca());
        azurirajStilApBrojaca();

        if (faza.equals(HegemonyEngine.FAZA_PROIZVODNJA)) {
            prikaziVizualniIzvjestaj("Faza Proizvodnje", engineIgre.obradiFazuProizvodnje());
        } else if (faza.equals(HegemonyEngine.FAZA_POTROSNJA)) {
            prikaziVizualniIzvjestaj("Faza Potrosnje", engineIgre.obradiFazuPotrosnje());
        } else if (faza.equals(HegemonyEngine.FAZA_GLASANJE)) {
            upraviteljGlasanja.prikaziPanelGlasanja(panelKontrolaTrenutniIgrac, this::azurirajPanelPotezaPremaFazi);
        } else if (faza.equals(HegemonyEngine.FAZA_KRAJ_RUNDE) || faza.equals(HegemonyEngine.FAZA_PRIPREMA)) {
            Label oznaka = new Label("FAZA " + pretvoriNazivFazeZaPrikaz(faza) + " — KLIKNITE 'SLJEDECA FAZA' ZA NASTAVAK");
            oznaka.setStyle("-fx-text-fill: " + StilGumba.TEKST_SVIJETLI + "; -fx-font-size: 13px; -fx-font-weight: bold;");
            panelKontrolaTrenutniIgrac.getChildren().add(oznaka);
        } else {
            KlasaIgraca igracNaPotezu = engineIgre.dohvatiIgracaNaPotezu();
            VBox kontrole = kontrolePoteza.napraviKontroleZaIgraca(engineIgre, igracNaPotezu,
                    () -> upraviteljGlasanja.obradiPrijedlogZakona(igracNaPotezu.getNaziv(), "Promjena ekonomske politike"),
                    this::obradiPotezIgraca);
            panelKontrolaTrenutniIgrac.getChildren().add(kontrole);
        }
    }

    private void prikaziVizualniIzvjestaj(String naslovIzvjestaja, String sadrzaj) {
        List<String> redovi = new ArrayList<>(Arrays.asList(sadrzaj.split("\n")));
        redovi.removeIf(String::isBlank);
        VBox panelIzvjestaja = kreatorIzvjestaja.napraviPanelIzvjestaja(naslovIzvjestaja, redovi, engineIgre.getListaIgraca());
        panelKontrolaTrenutniIgrac.getChildren().add(panelIzvjestaja);
    }

    private void obradiPotezIgraca() {
        prikazPloce.azurirajPrikaz(engineIgre.getListaIgraca());
        oznakaApBrojaca.setText(napraviTekstApBrojaca());
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
        kreatorZavrsnogEkrana.prikaziEkranPobjede(engineIgre.dohvatiPobjednika(), engineIgre.getListaIgraca());
    }
}