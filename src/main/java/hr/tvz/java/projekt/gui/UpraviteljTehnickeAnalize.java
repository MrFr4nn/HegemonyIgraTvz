package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.util.Serijalizator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class UpraviteljTehnickeAnalize {

    private Serijalizator serijalizator;

    public UpraviteljTehnickeAnalize(Serijalizator serijalizator) {
        this.serijalizator = serijalizator;
    }

    public void otvoriProzorAnalize() {
        Stage prozorAnalize = new Stage();
        prozorAnalize.setTitle("Tehnicka analiza klasa (Reflection)");

        VBox korijenskiLayout = new VBox(15);
        korijenskiLayout.setPadding(new Insets(25));
        korijenskiLayout.setBackground(new Background(new BackgroundFill(
                Color.web(StilGumba.POZADINA_TAMNA), CornerRadii.EMPTY, Insets.EMPTY)));

        Label naslov = new Label("REFLECTION API — TEHNICKA ANALIZA KLASA");
        naslov.setFont(Font.font("Arial Black", FontWeight.BOLD, 14));
        naslov.setStyle("-fx-text-fill: #00D4FF; -fx-effect: dropshadow(gaussian, rgba(0,212,255,0.5), 10, 0.3, 0, 0);");

        String sadrzajAnalize = serijalizator.napraviTehnickuUsporedbu();

        TextArea poljeAnalize = new TextArea(sadrzajAnalize);
        poljeAnalize.setEditable(false);
        poljeAnalize.setPrefHeight(450);
        poljeAnalize.setStyle(
                "-fx-control-inner-background: " + StilGumba.POVRSINA_TAMNA + "; "
                        + "-fx-text-fill: " + StilGumba.TEKST_SVIJETLI + "; "
                        + "-fx-font-family: 'Verdana'; -fx-font-size: 12px; "
                        + "-fx-border-color: #00D4FF; -fx-border-width: 1;"
        );

        ScrollPane skrolnaLista = new ScrollPane(poljeAnalize);
        skrolnaLista.setFitToWidth(true);
        skrolnaLista.setStyle("-fx-background-color: transparent;");

        Button gumbZatvori = new Button("ZATVORI");
        StilGumba.primijeniNaglaseni(gumbZatvori);
        gumbZatvori.setOnAction(dogadjaj -> prozorAnalize.close());

        korijenskiLayout.setAlignment(Pos.TOP_CENTER);
        korijenskiLayout.getChildren().addAll(naslov, skrolnaLista, gumbZatvori);

        Scene scenaAnalize = new Scene(korijenskiLayout, 650, 580);
        prozorAnalize.setScene(scenaAnalize);
        prozorAnalize.show();
    }
}