package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.model.KapitalistickaKlasa;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.SrednjaKlasa;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;

public class KreatorKartice {

    private static final String BOJA_RADNICKA = "#B0413E";
    private static final String BOJA_SREDNJA = "#C9A227";
    private static final String BOJA_KAPITALISTICKA = "#2E6E8E";
    private static final String BOJA_VLADA = "#454B52";

    public VBox napraviZaglavljeKartice(KlasaIgraca igrac) {
        String bojaTraka = odrediBojuTrake(igrac);

        VBox zaglavlje = new VBox(4);
        zaglavlje.setAlignment(Pos.CENTER_LEFT);
        zaglavlje.setPadding(new Insets(10, 15, 10, 15));
        zaglavlje.setBackground(new Background(new BackgroundFill(Color.web(bojaTraka),
                new CornerRadii(8, 8, 0, 0, false), Insets.EMPTY)));

        HBox redIkoneINaziva = new HBox(8);
        redIkoneINaziva.setAlignment(Pos.CENTER_LEFT);

        SVGPath ikona = napraviIkonuKlase(igrac);
        ikona.setFill(Color.WHITE);

        Label oznakaUloge = new Label(odrediNazivUloge(igrac));
        oznakaUloge.setFont(Font.font("Georgia", FontWeight.BOLD, 15));
        oznakaUloge.setTextFill(Color.WHITE);

        redIkoneINaziva.getChildren().addAll(ikona, oznakaUloge);

        Label oznakaIgraca = new Label(igrac.getNaziv());
        oznakaIgraca.setFont(Font.font("Verdana", 10));
        oznakaIgraca.setTextFill(Color.web("#F0F0F0"));

        zaglavlje.getChildren().addAll(redIkoneINaziva, oznakaIgraca);
        return zaglavlje;
    }

    public void postaviOkvirIVanjskiStil(VBox kartica) {
        kartica.setPadding(new Insets(0, 0, 15, 0));
        kartica.setAlignment(Pos.TOP_LEFT);
        kartica.setPrefWidth(220);
        kartica.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(8), Insets.EMPTY)));
        kartica.setBorder(new Border(new BorderStroke(Color.web("#D8CDB8"), BorderStrokeStyle.SOLID,
                new CornerRadii(8), new BorderWidths(1.5))));
        kartica.setEffect(napraviSjenu());
    }

    public Label napraviOznaku(String tekst) {
        Label oznaka = new Label(tekst);
        oznaka.setTextFill(Color.web("#3A332B"));
        oznaka.setFont(Font.font("Verdana", 12));
        return oznaka;
    }

    public Label napraviOznakuBodova(KlasaIgraca igrac) {
        Label oznakaBodova = new Label("BODOVI: " + igrac.getBodoviPobjede());
        oznakaBodova.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        oznakaBodova.setTextFill(Color.web(odrediBojuTrake(igrac)));
        return oznakaBodova;
    }

    private DropShadow napraviSjenu() {
        DropShadow sjena = new DropShadow();
        sjena.setRadius(8);
        sjena.setOffsetY(3);
        sjena.setColor(Color.color(0, 0, 0, 0.25));
        return sjena;
    }

    private String odrediNazivUloge(KlasaIgraca igrac) {
        if (igrac instanceof RadnickaKlasa) {
            return "Radnicka klasa";
        } else if (igrac instanceof SrednjaKlasa) {
            return "Srednja klasa";
        } else if (igrac instanceof KapitalistickaKlasa) {
            return "Kapitalisticka klasa";
        } else {
            return "Vlada";
        }
    }

    private String odrediBojuTrake(KlasaIgraca igrac) {
        if (igrac instanceof RadnickaKlasa) {
            return BOJA_RADNICKA;
        } else if (igrac instanceof SrednjaKlasa) {
            return BOJA_SREDNJA;
        } else if (igrac instanceof KapitalistickaKlasa) {
            return BOJA_KAPITALISTICKA;
        } else {
            return BOJA_VLADA;
        }
    }

    private SVGPath napraviIkonuKlase(KlasaIgraca igrac) {
        SVGPath ikona = new SVGPath();
        ikona.setScaleX(0.8);
        ikona.setScaleY(0.8);

        if (igrac instanceof RadnickaKlasa) {
            ikona.setContent("M12 2 L14 8 L20 8 L15 12 L17 18 L12 14 L7 18 L9 12 L4 8 L10 8 Z");
        } else if (igrac instanceof SrednjaKlasa) {
            ikona.setContent("M4 8 H20 V18 H4 Z M9 8 V5 H15 V8");
        } else if (igrac instanceof KapitalistickaKlasa) {
            ikona.setContent("M4 10 H20 V18 H4 Z M4 10 L12 4 L20 10");
        } else {
            ikona.setContent("M4 20 V10 L12 4 L20 10 V20 M9 20 V14 H15 V20");
        }
        return ikona;
    }
}