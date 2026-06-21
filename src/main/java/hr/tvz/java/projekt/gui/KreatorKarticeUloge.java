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

public class KreatorKarticeUloge {

    public VBox napraviKarticu(String nazivUloge, String bojaHex, String svgIkona, boolean odabrana) {
        VBox kartica = new VBox(10);
        kartica.setAlignment(Pos.CENTER);
        kartica.setPadding(new Insets(20));
        kartica.setPrefSize(150, 160);
        kartica.setCursor(javafx.scene.Cursor.HAND);

        primijeniStilKartice(kartica, bojaHex, odabrana);

        SVGPath ikona = new SVGPath();
        ikona.setContent(svgIkona);
        ikona.setFill(odabrana ? Color.WHITE : Color.web(bojaHex));
        ikona.setScaleX(1.6);
        ikona.setScaleY(1.6);

        Label naslov = new Label(nazivUloge);
        naslov.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        naslov.setTextFill(odabrana ? Color.WHITE : Color.web("#2B2520"));
        naslov.setWrapText(true);
        naslov.setAlignment(Pos.CENTER);
        naslov.setStyle("-fx-text-alignment: center;");

        kartica.getChildren().addAll(ikona, naslov);
        return kartica;
    }

    public void primijeniStilKartice(VBox kartica, String bojaHex, boolean odabrana) {
        if (odabrana) {
            kartica.setBackground(new Background(new BackgroundFill(Color.web(bojaHex), new CornerRadii(12), Insets.EMPTY)));
            kartica.setBorder(new Border(new BorderStroke(Color.web(bojaHex), BorderStrokeStyle.SOLID,
                    new CornerRadii(12), new BorderWidths(3))));
        } else {
            kartica.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(12), Insets.EMPTY)));
            kartica.setBorder(new Border(new BorderStroke(Color.web(bojaHex), BorderStrokeStyle.SOLID,
                    new CornerRadii(12), new BorderWidths(2))));
        }
        kartica.setEffect(napraviSjenu());
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
        return "M4 10 H20 V18 H4 Z M4 10 L12 4 L20 10 M8 13 H10 V16 H8 Z M14 13 H16 V16 H14 Z";
    }

    public String dohvatiIkonuVlade() {
        return "M4 20 V10 L12 4 L20 10 V20 M9 20 V14 H15 V20";
    }
}