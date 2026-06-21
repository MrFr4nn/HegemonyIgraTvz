package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.logika.HegemonyEngine;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.Vlada;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GlavniProzor {

    private Stage glavnaScena;
    private HegemonyEngine engineIgre;
    private PrikazPloce prikazPloce;

    public GlavniProzor(Stage glavnaScena) {
        this.glavnaScena = glavnaScena;
        RadnickaKlasa radnickaKlasa = new RadnickaKlasa("Radnicka klasa - Igrac 1");
        Vlada vlada = new Vlada("Vlada - Igrac 2");
        this.engineIgre = new HegemonyEngine(radnickaKlasa, vlada);
        this.prikazPloce = new PrikazPloce();
    }

    public void prikaziProzor() {
        BorderPane korijenskiLayout = new BorderPane();
        korijenskiLayout.setCenter(prikazPloce.napraviDrzavnuPlocu(engineIgre.getRadnickaKlasa(), engineIgre.getVlada()));

        Scene glavnaScenaPrikaza = new Scene(korijenskiLayout, 900, 700);
        glavnaScena.setTitle("Hegemony: Lead Your Class to Victory - TVZ Projekt");
        glavnaScena.setScene(glavnaScenaPrikaza);
        glavnaScena.show();
    }
}