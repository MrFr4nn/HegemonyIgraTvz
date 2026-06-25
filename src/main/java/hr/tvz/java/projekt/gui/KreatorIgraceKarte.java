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
            primijeniStilIskoristene(karta, bojaHex);
        } else {
            primijeniStilDostupne(karta, bojaHex);
            karta.setCursor(javafx.scene.Cursor.HAND);
        }

        SVGPath ikona = new SVGPath();
        ikona.setContent(svgIkona);
        Color bojaIkone = iskoristena ? Color.web("#A8A096") : Color.web(bojaHex);
        ikona.setFill(bojaIkone);
        ikona.setScaleX(1.3);
        ikona.setScaleY(1.3);

        Label naslovKarte = new Label(nazivKarte);
        naslovKarte.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        naslovKarte.setTextFill(iskoristena ? Color.web("#A8A096") : Color.web("#2B2520"));
        naslovKarte.setWrapText(true);
        naslovKarte.setAlignment(Pos.CENTER);
        naslovKarte.setStyle("-fx-text-alignment: center;");

        Label opisKarte = new Label(opisEfekta);
        opisKarte.setFont(Font.font("Verdana", 9));
        opisKarte.setTextFill(iskoristena ? Color.web("#B8B0A6") : Color.web("#6B6357"));
        opisKarte.setWrapText(true);
        opisKarte.setAlignment(Pos.CENTER);
        opisKarte.setStyle("-fx-text-alignment: center;");

        VBox.setMargin(naslovKarte, new Insets(8, 0, 0, 0));

        karta.getChildren().addAll(ikona, naslovKarte, opisKarte);

        if (iskoristena) {
            Label oznakaIskoristeno = new Label("ODIGRANO");
            oznakaIskoristeno.setFont(Font.font("Verdana", FontWeight.BOLD, 8));
            oznakaIskoristeno.setTextFill(Color.web("#A8A096"));
            karta.getChildren().add(oznakaIskoristeno);
        }

        return karta;
    }

    private void primijeniStilDostupne(VBox karta, String bojaHex) {
        karta.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(12), Insets.EMPTY)));
        karta.setBorder(new Border(new BorderStroke(Color.web(bojaHex), BorderStrokeStyle.SOLID,
                new CornerRadii(12), new BorderWidths(2.5))));
        karta.setEffect(napraviSjenu(0.3));
    }

    private void primijeniStilIskoristene(VBox karta, String bojaHex) {
        karta.setBackground(new Background(new BackgroundFill(Color.web("#E8E3D8"), new CornerRadii(12), Insets.EMPTY)));
        karta.setBorder(new Border(new BorderStroke(Color.web("#C9C0B0"), BorderStrokeStyle.SOLID,
                new CornerRadii(12), new BorderWidths(1.5))));
        karta.setOpacity(0.6);
        karta.setEffect(napraviSjenu(0.1));
    }

    private void dodajHoverEfekt(VBox karta, String bojaHex) {
        Color bojaPozadine = Color.web(bojaHex);
        Color svijetlaVerzija = bojaPozadine.deriveColor(0, 1, 1, 0.12);
        karta.setOnMouseEntered(dogadjaj -> {
            karta.setBackground(new Background(new BackgroundFill(svijetlaVerzija, new CornerRadii(12), Insets.EMPTY)));
            karta.setScaleX(1.05);
            karta.setScaleY(1.05);
        });
        karta.setOnMouseExited(dogadjaj -> {
            karta.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(12), Insets.EMPTY)));
            karta.setScaleX(1.0);
            karta.setScaleY(1.0);
        });
    }

    public void omoguciHover(VBox karta, String bojaHex) {
        dodajHoverEfekt(karta, bojaHex);
    }

    private DropShadow napraviSjenu(double intenzitet) {
        DropShadow sjena = new DropShadow();
        sjena.setRadius(8);
        sjena.setOffsetY(3);
        sjena.setColor(Color.color(0, 0, 0, intenzitet));
        return sjena;
    }
}