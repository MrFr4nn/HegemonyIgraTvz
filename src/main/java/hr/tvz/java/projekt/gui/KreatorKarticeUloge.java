package hr.tvz.java.projekt.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

public class KreatorKarticeUloge {

    public VBox napraviKarticu(String nazivUloge, String opisUloge, String bojaHex, String svgIkona, boolean odabrana) {
        VBox kartica = new VBox(8);
        kartica.setAlignment(Pos.CENTER);
        kartica.setPadding(new Insets(18));
        kartica.setPrefSize(170, 190);
        kartica.setCursor(javafx.scene.Cursor.HAND);

        primijeniStilKartice(kartica, bojaHex, odabrana);
        dodajHoverEfekt(kartica, bojaHex, odabrana);

        SVGPath ikona = new SVGPath();
        ikona.setContent(svgIkona);
        Color bojaIkone = odabrana ? Color.WHITE : Color.web(bojaHex);
        ikona.setFill(bojaIkone);
        ikona.setStroke(bojaIkone);
        ikona.setStrokeWidth(0.5);
        ikona.setOpacity(1.0);
        ikona.setScaleX(1.7);
        ikona.setScaleY(1.7);

        Label naslov = new Label(nazivUloge);
        naslov.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        naslov.setTextFill(odabrana ? Color.WHITE : Color.web("#2B2520"));
        naslov.setWrapText(true);
        naslov.setAlignment(Pos.CENTER);
        naslov.setStyle("-fx-text-alignment: center;");

        Label opis = new Label(opisUloge);
        opis.setFont(Font.font("Verdana", 10));
        opis.setTextFill(odabrana ? Color.web("#F0F0F0") : Color.web("#6B6357"));
        opis.setWrapText(true);
        opis.setAlignment(Pos.CENTER);
        opis.setStyle("-fx-text-alignment: center;");

        kartica.getChildren().addAll(ikona, naslov, opis);
        return kartica;
    }

    public void primijeniStilKartice(VBox kartica, String bojaHex, boolean odabrana) {
        if (odabrana) {
            kartica.setBackground(new Background(new BackgroundFill(Color.web(bojaHex), new CornerRadii(14), Insets.EMPTY)));
            kartica.setBorder(new Border(new BorderStroke(Color.web(bojaHex), BorderStrokeStyle.SOLID,
                    new CornerRadii(14), new BorderWidths(3))));
        } else {
            kartica.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(14), Insets.EMPTY)));
            kartica.setBorder(new Border(new BorderStroke(Color.web(bojaHex), BorderStrokeStyle.SOLID,
                    new CornerRadii(14), new BorderWidths(2.5))));
        }
        kartica.setEffect(napraviSjenu());
    }

    private void dodajHoverEfekt(VBox kartica, String bojaHex, boolean odabrana) {
        if (odabrana) {
            return;
        }
        Color bojaPozadine = Color.web(bojaHex);
        Color svijetlaVerzija = bojaPozadine.deriveColor(0, 1, 1, 0.15);
        kartica.setOnMouseEntered(dogadjaj -> kartica.setBackground(
                new Background(new BackgroundFill(svijetlaVerzija, new CornerRadii(14), Insets.EMPTY))));
        kartica.setOnMouseExited(dogadjaj -> kartica.setBackground(
                new Background(new BackgroundFill(Color.WHITE, new CornerRadii(14), Insets.EMPTY))));
    }

    public void animirajOdabir(VBox kartica) {
        Timeline animacija = new Timeline();
        KeyFrame uvecaj = new KeyFrame(Duration.millis(80), dogadjaj -> {
            kartica.setScaleX(1.08);
            kartica.setScaleY(1.08);
        });
        KeyFrame vratiNazad = new KeyFrame(Duration.millis(160), dogadjaj -> {
            kartica.setScaleX(1.0);
            kartica.setScaleY(1.0);
        });
        animacija.getKeyFrames().addAll(uvecaj, vratiNazad);
        animacija.play();
    }

    private DropShadow napraviSjenu() {
        DropShadow sjena = new DropShadow();
        sjena.setRadius(10);
        sjena.setOffsetY(4);
        sjena.setColor(Color.color(0, 0, 0, 0.3));
        return sjena;
    }

    public String dohvatiIkonuRadnicke() {
        return "M12 2 L14 8 L20 8 L15 12 L17 18 L12 14 L7 18 L9 12 L4 8 L10 8 Z";
    }

    public String dohvatiIkonuSrednje() {
        return "M4 8 H20 V18 H4 Z M9 8 V5 H15 V8";
    }

    public String dohvatiIkonuKapitalisticke() {
        return "M3 18 H21 V20 H3 Z M5 18 V10 H7 V18 Z M9 18 V6 H11 V18 Z M13 18 V11 H15 V18 Z M17 18 V8 H19 V18 Z";
    }

    public String dohvatiIkonuVlade() {
        return "M4 20 H20 V21 H4 Z M5 20 V11 H6.5 V20 Z M8 20 V11 H9.5 V20 Z M11.25 20 V11 H12.75 V20 Z "
                + "M14.5 20 V11 H16 V20 Z M17.5 20 V11 H19 V20 Z M3 11 L12 4 L21 11 Z";
    }

    public String dohvatiOpisRadnicke() {
        return "Cilj: puna zaposlenost i visok standard zivota";
    }

    public String dohvatiOpisSrednje() {
        return "Cilj: balans izmedju rada i vlastitog poduzeca";
    }

    public String dohvatiOpisKapitalisticke() {
        return "Cilj: maksimizirati profit i kapital";
    }

    public String dohvatiOpisVlade() {
        return "Cilj: stabilan proracun i legitimnost";
    }
}