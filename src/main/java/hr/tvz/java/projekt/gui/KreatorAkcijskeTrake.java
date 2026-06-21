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

    public VBox napraviAkcijskuTraku(Label oznakaApBrojaca, VBox panelKontrolaTrenutniIgrac, HBox redAlatnihGumbova) {
        VBox akcijskaTraka = new VBox(12);
        akcijskaTraka.setPadding(new Insets(20, 30, 25, 30));
        akcijskaTraka.setStyle("-fx-background-color: linear-gradient(to bottom, #2B2520, #3A332B); "
                + "-fx-background-radius: 18 18 0 0;");
        akcijskaTraka.setEffect(napraviSjenuTrake());

        akcijskaTraka.getChildren().addAll(oznakaApBrojaca, panelKontrolaTrenutniIgrac, redAlatnihGumbova);
        return akcijskaTraka;
    }

    private DropShadow napraviSjenuTrake() {
        DropShadow sjena = new DropShadow();
        sjena.setRadius(15);
        sjena.setOffsetY(-4);
        sjena.setColor(Color.color(0, 0, 0, 0.4));
        return sjena;
    }

    public HBox napraviRedAlatnihGumbova(Runnable akcijaSljedecaFaza, Runnable akcijaSpremiStanje,
                                         Runnable akcijaTehnickaAnaliza, Runnable akcijaReplay) {
        HBox red = new HBox(10);
        red.setAlignment(Pos.CENTER);

        Button gumbSljedecaFaza = new Button("Sljedeca faza ►");
        StilGumba.primijeniNaglaseni(gumbSljedecaFaza);
        gumbSljedecaFaza.setStyle(gumbSljedecaFaza.getStyle() + "-fx-font-size: 14px; -fx-padding: 10 24 10 24;");
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