package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.logika.KatalogZakona;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.function.IntConsumer;

public class KreatorIzbornikaZakona {

    public VBox napraviIzbornikZakona(KatalogZakona katalogZakona, IntConsumer akcijaOdabira) {
        VBox panel = new VBox(10);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(8));

        Label naslov = new Label("VLADA - ODABERITE ZAKON ZA GLASANJE");
        naslov.setFont(Font.font("Arial Black", FontWeight.BOLD, 13));
        naslov.setStyle("-fx-text-fill: #00D4FF;");

        VBox listaZakona = new VBox(8);
        listaZakona.setAlignment(Pos.CENTER);

        int brojac = 0;
        while (brojac < katalogZakona.dohvatiBrojZakona()) {
            int indeks = brojac;
            if (!katalogZakona.jeIskoristen(indeks)) {
                HBox redak = napraviRedakZakona(katalogZakona, indeks, akcijaOdabira);
                listaZakona.getChildren().add(redak);
            }
            brojac = brojac + 1;
        }

        ScrollPane skrolnaLista = new ScrollPane(listaZakona);
        skrolnaLista.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        skrolnaLista.setPrefHeight(150);
        skrolnaLista.setFitToWidth(true);

        panel.getChildren().addAll(naslov, skrolnaLista);
        return panel;
    }

    private HBox napraviRedakZakona(KatalogZakona katalogZakona, int indeks, IntConsumer akcijaOdabira) {
        HBox redak = new HBox(12);
        redak.setAlignment(Pos.CENTER_LEFT);
        redak.setPadding(new Insets(10, 16, 10, 16));
        redak.setMaxWidth(650);
        redak.setCursor(javafx.scene.Cursor.HAND);

        redak.setBackground(new Background(new BackgroundFill(Color.web(StilGumba.POVRSINA_TAMNA), new CornerRadii(6), Insets.EMPTY)));
        redak.setBorder(new Border(new BorderStroke(Color.web("#00D4FF"), BorderStrokeStyle.SOLID,
                new CornerRadii(6), new BorderWidths(2))));

        Label oznakaNaziva = new Label(katalogZakona.dohvatiNaziv(indeks));
        oznakaNaziva.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        oznakaNaziva.setStyle("-fx-text-fill: " + StilGumba.TEKST_SVIJETLI + ";");
        oznakaNaziva.setMinWidth(220);

        Label oznakaOpisa = new Label(katalogZakona.dohvatiOpis(indeks));
        oznakaOpisa.setFont(Font.font("Verdana", 10));
        oznakaOpisa.setStyle("-fx-text-fill: " + StilGumba.TEKST_SIVI + ";");
        oznakaOpisa.setWrapText(true);

        redak.getChildren().addAll(oznakaNaziva, oznakaOpisa);

        redak.setOnMouseEntered(dogadjaj -> redak.setBackground(
                new Background(new BackgroundFill(Color.web("#00D4FF", 0.15), new CornerRadii(6), Insets.EMPTY))));
        redak.setOnMouseExited(dogadjaj -> redak.setBackground(
                new Background(new BackgroundFill(Color.web(StilGumba.POVRSINA_TAMNA), new CornerRadii(6), Insets.EMPTY))));
        redak.setOnMouseClicked(dogadjaj -> akcijaOdabira.accept(indeks));

        return redak;
    }
}