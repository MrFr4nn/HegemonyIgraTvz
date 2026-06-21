package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.logika.HegemonyEngine;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.Vlada;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GlavniProzor {

    private Stage glavnaScena;
    private HegemonyEngine engineIgre;
    private PrikazPloce prikazPloce;
    private KontrolePoteza kontrolePoteza;

    private BorderPane korijenskiLayout;
    private VBox panelKontrolaTrenutniIgrac;
    private Label oznakaFazeIgre;

    public GlavniProzor(Stage glavnaScena) {
        this.glavnaScena = glavnaScena;
        RadnickaKlasa radnickaKlasa = new RadnickaKlasa("Radnicka klasa - Igrac 1");
        Vlada vlada = new Vlada("Vlada - Igrac 2");
        this.engineIgre = new HegemonyEngine(radnickaKlasa, vlada);
        this.prikazPloce = new PrikazPloce();
        this.kontrolePoteza = new KontrolePoteza();
    }

    public void prikaziProzor() {
        korijenskiLayout = new BorderPane();
        korijenskiLayout.setCenter(prikazPloce.napraviDrzavnuPlocu(engineIgre.getRadnickaKlasa(), engineIgre.getVlada()));
        korijenskiLayout.setTop(napraviGornjiPanel());
        korijenskiLayout.setBottom(napraviDonjiPanel());

        azurirajKontrolePotezaTrenutnogIgraca();

        Scene glavnaScenaPrikaza = new Scene(korijenskiLayout, 900, 700);
        glavnaScena.setTitle("Hegemony: Lead Your Class to Victory - TVZ Projekt");
        glavnaScena.setScene(glavnaScenaPrikaza);
        glavnaScena.show();
    }

    private VBox napraviGornjiPanel() {
        VBox gornjiPanel = new VBox(8);
        gornjiPanel.setAlignment(Pos.CENTER);
        gornjiPanel.setPadding(new Insets(10));

        Label naslov = new Label("HEGEMONY: LEAD YOUR CLASS TO VICTORY");
        oznakaFazeIgre = new Label("Runda: " + engineIgre.getBrojRunde() + " | Faza: " + engineIgre.getTrenutnaFaza());

        gornjiPanel.getChildren().addAll(naslov, oznakaFazeIgre);
        return gornjiPanel;
    }

    private HBox napraviDonjiPanel() {
        HBox donjiPanel = new HBox(20);
        donjiPanel.setAlignment(Pos.CENTER);
        donjiPanel.setPadding(new Insets(10));

        panelKontrolaTrenutniIgrac = new VBox();

        Button gumbSljedecaFaza = new Button("Sljedeca faza");
        gumbSljedecaFaza.setOnAction(dogadjaj -> obradiSljedecuFazu());

        donjiPanel.getChildren().addAll(panelKontrolaTrenutniIgrac, gumbSljedecaFaza);
        return donjiPanel;
    }

    private void azurirajKontrolePotezaTrenutnogIgraca() {
        panelKontrolaTrenutniIgrac.getChildren().clear();
        if (engineIgre.isNaPotezuRadnickaKlasa()) {
            VBox kontrole = kontrolePoteza.napraviKontroleRadnickeKlase(engineIgre.getRadnickaKlasa(), this::obradiPotezIgraca);
            panelKontrolaTrenutniIgrac.getChildren().add(kontrole);
        } else {
            VBox kontrole = kontrolePoteza.napraviKontroleVlade(engineIgre.getVlada(), this::obradiPotezIgraca);
            panelKontrolaTrenutniIgrac.getChildren().add(kontrole);
        }
    }

    private void obradiPotezIgraca() {
        prikazPloce.azurirajPrikaz(engineIgre.getRadnickaKlasa(), engineIgre.getVlada());
        engineIgre.prebaciPotez();
        azurirajKontrolePotezaTrenutnogIgraca();
    }

    private void obradiSljedecuFazu() {
        engineIgre.prebaciNaSljedecuFazu();
        oznakaFazeIgre.setText("Runda: " + engineIgre.getBrojRunde() + " | Faza: " + engineIgre.getTrenutnaFaza());
        prikazPloce.azurirajPrikaz(engineIgre.getRadnickaKlasa(), engineIgre.getVlada());
    }
}