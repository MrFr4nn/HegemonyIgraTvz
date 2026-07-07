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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class KreatorKarticeUloge {

    public VBox napraviKarticu(String nazivUloge, String opisUloge, String bojaHex, String emojiIkona, boolean odabrana) {
        VBox kartica = new VBox(8);
        kartica.setAlignment(Pos.CENTER);
        kartica.setPadding(new Insets(18));
        kartica.setPrefSize(175, 195);
        kartica.setCursor(javafx.scene.Cursor.HAND);

        primijeniStilKartice(kartica, bojaHex, odabrana);
        dodajHoverEfekt(kartica, bojaHex, odabrana);

        Label ikona = new Label(emojiIkona);
        ikona.setFont(Font.font(42));

        Label naslov = new Label(nazivUloge.toUpperCase());
        naslov.setFont(Font.font("Arial Black", FontWeight.BOLD, 14));
        naslov.setTextFill(Color.web(StilGumba.TEKST_SVIJETLI));
        naslov.setWrapText(true);
        naslov.setAlignment(Pos.CENTER);
        naslov.setStyle("-fx-text-alignment: center;");

        Label opis = new Label(opisUloge);
        opis.setFont(Font.font("Verdana", 10));
        opis.setTextFill(Color.web(StilGumba.TEKST_SIVI));
        opis.setWrapText(true);
        opis.setAlignment(Pos.CENTER);
        opis.setStyle("-fx-text-alignment: center;");

        kartica.getChildren().addAll(ikona, naslov, opis);
        return kartica;
    }

    public void primijeniStilKartice(VBox kartica, String bojaHex, boolean odabrana) {
        if (odabrana) {
            kartica.setBackground(new Background(new BackgroundFill(Color.web(StilGumba.POVRSINA_TAMNA), new CornerRadii(6), Insets.EMPTY)));
            kartica.setBorder(new Border(new BorderStroke(Color.web(bojaHex), BorderStrokeStyle.SOLID,
                    new CornerRadii(6), new BorderWidths(3))));
            kartica.setEffect(StilGumba.napraviNeonSjenu(bojaHex));
        } else {
            kartica.setBackground(new Background(new BackgroundFill(Color.web(StilGumba.POVRSINA_TAMNA), new CornerRadii(6), Insets.EMPTY)));
            kartica.setBorder(new Border(new BorderStroke(Color.web(StilGumba.TEKST_SIVI), BorderStrokeStyle.SOLID,
                    new CornerRadii(6), new BorderWidths(1.5))));
            kartica.setEffect(napraviObicnuSjenu());
        }
    }

    private void dodajHoverEfekt(VBox kartica, String bojaHex, boolean odabrana) {
        if (odabrana) {
            return;
        }
        kartica.setOnMouseEntered(dogadjaj -> {
            kartica.setBorder(new Border(new BorderStroke(Color.web(bojaHex), BorderStrokeStyle.SOLID,
                    new CornerRadii(6), new BorderWidths(2.5))));
            kartica.setEffect(StilGumba.napraviNeonSjenu(bojaHex));
        });
        kartica.setOnMouseExited(dogadjaj -> {
            kartica.setBorder(new Border(new BorderStroke(Color.web(StilGumba.TEKST_SIVI), BorderStrokeStyle.SOLID,
                    new CornerRadii(6), new BorderWidths(1.5))));
            kartica.setEffect(napraviObicnuSjenu());
        });
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

    private DropShadow napraviObicnuSjenu() {
        DropShadow sjena = new DropShadow();
        sjena.setRadius(8);
        sjena.setOffsetY(3);
        sjena.setColor(Color.color(0, 0, 0, 0.6));
        return sjena;
    }

    public String dohvatiIkonuRadnicke() {
        return "👷";
    }

    public String dohvatiIkonuSrednje() {
        return "🏪";
    }

    public String dohvatiIkonuKapitalisticke() {
        return "🏭";
    }

    public String dohvatiIkonuVlade() {
        return "🏛️";
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