package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.logika.HegemonyEngine;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.Vlada;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GlavniProzor {

    private Stage glavnaScena;
    private HegemonyEngine engineIgre;

    public GlavniProzor(Stage glavnaScena) {
        this.glavnaScena = glavnaScena;
        RadnickaKlasa radnickaKlasa = new RadnickaKlasa("Radnicka klasa - Igrac 1");
        Vlada vlada = new Vlada("Vlada - Igrac 2");
        this.engineIgre = new HegemonyEngine(radnickaKlasa, vlada);
    }

    public void prikaziProzor() {
        VBox korijenskiLayout = new VBox(15);
        korijenskiLayout.setPadding(new Insets(20));
        korijenskiLayout.setAlignment(Pos.CENTER);

        Label naslov = new Label("HEGEMONY: LEAD YOUR CLASS TO VICTORY");
        Label oznakaFaze = new Label("Runda: " + engineIgre.getBrojRunde() + " | Faza: " + engineIgre.getTrenutnaFaza());
        Label oznakaProvjere = new Label("Engine igre je uspjesno pokrenut.");

        korijenskiLayout.getChildren().addAll(naslov, oznakaFaze, oznakaProvjere);

        Scene glavnaScenaPrikaza = new Scene(korijenskiLayout, 700, 500);
        glavnaScena.setTitle("Hegemony: Lead Your Class to Victory - TVZ Projekt");
        glavnaScena.setScene(glavnaScenaPrikaza);
        glavnaScena.show();
    }
}