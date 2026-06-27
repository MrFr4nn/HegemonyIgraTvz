package hr.tvz.java.projekt.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class KreatorAkcijskeTrake {

    public VBox napraviGornjiPanel(Label oznakaFazeIgre, Label oznakaAnimacije) {
        VBox gornjiPanel = new VBox(6);
        gornjiPanel.setAlignment(Pos.CENTER);
        gornjiPanel.setPadding(new Insets(15, 10, 25, 10));
        gornjiPanel.setStyle("-fx-background-color: " + StilGumba.POZADINA_TAMNA + ";");

        Label naslov = new Label("HEGEMONY: LEAD YOUR CLASS TO VICTORY");
        naslov.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #00D4FF; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,212,255,0.5), 15, 0.3, 0, 0);");

        oznakaFazeIgre.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: "
                + StilGumba.TEKST_SVIJETLI + ";");

        oznakaAnimacije.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 11px; -fx-text-fill: " + StilGumba.TEKST_SIVI + ";");

        gornjiPanel.getChildren().addAll(naslov, oznakaFazeIgre, oznakaAnimacije);
        return gornjiPanel;
    }

    public VBox napraviAkcijskuTraku(Label oznakaApBrojaca, VBox panelKontrolaTrenutniIgrac, HBox redAlatnihGumbova) {
        VBox akcijskaTraka = new VBox(12);
        akcijskaTraka.setPadding(new Insets(20, 30, 25, 30));
        akcijskaTraka.setStyle("-fx-background-color: " + StilGumba.POVRSINA_TAMNA + "; "
                + "-fx-border-color: #00D4FF; -fx-border-width: 2 0 0 0;");
        akcijskaTraka.setEffect(napraviSjenuTrake());

        akcijskaTraka.getChildren().addAll(oznakaApBrojaca, panelKontrolaTrenutniIgrac, redAlatnihGumbova);
        return akcijskaTraka;
    }

    private DropShadow napraviSjenuTrake() {
        DropShadow sjena = new DropShadow();
        sjena.setRadius(20);
        sjena.setOffsetY(-5);
        sjena.setColor(Color.color(0, 0.83, 1, 0.25));
        return sjena;
    }

    public HBox napraviRedAlatnihGumbova(Runnable akcijaSljedecaFaza, Runnable akcijaSpremiStanje,
                                         Runnable akcijaTehnickaAnaliza, Runnable akcijaReplay) {
        HBox red = new HBox(10);
        red.setAlignment(Pos.CENTER);

        Button gumbSljedecaFaza = new Button("SLJEDECA FAZA ►");
        StilGumba.primijeniNaglaseniVeliki(gumbSljedecaFaza);
        gumbSljedecaFaza.setOnAction(dogadjaj -> akcijaSljedecaFaza.run());

        Button gumbSpremiStanje = new Button("Spremi stanje");
        StilGumba.primijeniNeutralni(gumbSpremiStanje);
        gumbSpremiStanje.setOnAction(dogadjaj -> akcijaSpremiStanje.run());

        Button gumbTehnickaAnaliza = new Button("Tehnicka usporedba");
        StilGumba.primijeniNeutralni(gumbTehnickaAnaliza);
        gumbTehnickaAnaliza.setOnAction(dogadjaj -> akcijaTehnickaAnaliza.run());

        Button gumbReplay = new Button("Pokreni Replay");
        StilGumba.primijeniNeutralni(gumbReplay);
        gumbReplay.setOnAction(dogadjaj -> akcijaReplay.run());

        red.getChildren().addAll(gumbSljedecaFaza, gumbSpremiStanje, gumbTehnickaAnaliza, gumbReplay);
        return red;
    }
}