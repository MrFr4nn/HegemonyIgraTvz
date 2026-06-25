package hr.tvz.java.projekt.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;

public class KreatorIgraceKarte {

    public VBox napraviKartu(String nazivKarte, String opisEfekta, String svgIkona, String bojaHex, boolean iskoristena) {
        VBox karta = new VBox(8);
        karta.setAlignment(Pos.TOP_CENTER);
        karta.setPadding(new Insets(14, 10, 14, 10));
        karta.setPrefSize(150, 190);

        if (iskoristena) {
            primijeniStilIskoristene(karta);
        } else {
            primijeniStilDostupne(karta, bojaHex);
            karta.setCursor(javafx.scene.Cursor.HAND);
        }

        SVGPath ikona = new SVGPath();
        ikona.setContent(svgIkona);
        Color bojaIkone = iskoristena ? Color.web(StilGumba.TEKST_SIVI) : Color.web(bojaHex);
        ikona.setFill(bojaIkone);
        ikona.setScaleX(1.3);
        ikona.setScaleY(1.3);

        Label naslovKarte = new Label(nazivKarte.toUpperCase());
        naslovKarte.setFont(Font.font("Arial Black", FontWeight.BOLD, 12));
        naslovKarte.setTextFill(iskoristena ? Color.web(StilGumba.TEKST_SIVI) : Color.web(StilGumba.TEKST_SVIJETLI));
        naslovKarte.setWrapText(true);
        naslovKarte.setAlignment(Pos.CENTER);
        naslovKarte.setStyle("-fx-text-alignment: center;");

        Label opisKarte = new Label(opisEfekta);
        opisKarte.setFont(Font.font("Verdana", 9));
        opisKarte.setTextFill(Color.web(StilGumba.TEKST_SIVI));
        opisKarte.setWrapText(true);
        opisKarte.setAlignment(Pos.CENTER);
        opisKarte.setStyle("-fx-text-alignment: center;");

        VBox.setMargin(naslovKarte, new Insets(8, 0, 0, 0));

        karta.getChildren().addAll(ikona, naslovKarte, opisKarte);

        if (iskoristena) {
            Label oznakaIskoristeno = new Label("ODIGRANO");
            oznakaIskoristeno.setFont(Font.font("Verdana", FontWeight.BOLD, 8));
            oznakaIskoristeno.setTextFill(Color.web(StilGumba.TEKST_SIVI));
            karta.getChildren().add(oznakaIskoristeno);
        }

        return karta;
    }

    private void primijeniStilDostupne(VBox karta, String bojaHex) {
        karta.setBackground(new Background(new BackgroundFill(Color.web(StilGumba.POZADINA_TAMNA), new CornerRadii(6), Insets.EMPTY)));
        karta.setBorder(new Border(new BorderStroke(Color.web(bojaHex), BorderStrokeStyle.SOLID,
                new CornerRadii(6), new BorderWidths(2))));
        karta.setEffect(StilGumba.napraviNeonSjenu(bojaHex));
    }

    private void primijeniStilIskoristene(VBox karta) {
        karta.setBackground(new Background(new BackgroundFill(Color.web("#1A1A22"), new CornerRadii(6), Insets.EMPTY)));
        karta.setBorder(new Border(new BorderStroke(Color.web("#3A3A45"), BorderStrokeStyle.SOLID,
                new CornerRadii(6), new BorderWidths(1.5))));
        karta.setOpacity(0.55);
        karta.setEffect(null);
    }

    private void dodajHoverEfekt(VBox karta, String bojaHex) {
        karta.setOnMouseEntered(dogadjaj -> {
            karta.setBorder(new Border(new BorderStroke(Color.web(bojaHex), BorderStrokeStyle.SOLID,
                    new CornerRadii(6), new BorderWidths(3))));
            karta.setEffect(StilGumba.napraviNeonSjenu(bojaHex));
            karta.setScaleX(1.06);
            karta.setScaleY(1.06);
        });
        karta.setOnMouseExited(dogadjaj -> {
            karta.setBorder(new Border(new BorderStroke(Color.web(bojaHex), BorderStrokeStyle.SOLID,
                    new CornerRadii(6), new BorderWidths(2))));
            karta.setEffect(StilGumba.napraviNeonSjenu(bojaHex));
            karta.setScaleX(1.0);
            karta.setScaleY(1.0);
        });
    }

    public void omoguciHover(VBox karta, String bojaHex) {
        dodajHoverEfekt(karta, bojaHex);
    }
}