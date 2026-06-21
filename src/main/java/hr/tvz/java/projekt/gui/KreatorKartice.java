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
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class KreatorKartice {

    private static final String BOJA_RADNICKA = "#8C3A36";
    private static final String BOJA_SREDNJA = "#A6862C";
    private static final String BOJA_KAPITALISTICKA = "#2B4C70";
    private static final String BOJA_VLADA = "#4A4458";

    public VBox napraviZaglavljeKartice(KlasaIgraca igrac) {
        String bojaTraka = odrediBojuTrake(igrac);

        VBox zaglavlje = new VBox(4);
        zaglavlje.setAlignment(Pos.CENTER_LEFT);
        zaglavlje.setPadding(new Insets(14, 18, 14, 18));
        zaglavlje.setBackground(new Background(new BackgroundFill(Color.web(bojaTraka),
                new CornerRadii(12, 12, 0, 0, false), Insets.EMPTY)));

        HBox redIkoneINaziva = new HBox(10);
        redIkoneINaziva.setAlignment(Pos.CENTER_LEFT);

        SVGPath ikona = napraviIkonuKlase(igrac);
        ikona.setFill(Color.WHITE);

        Label oznakaUloge = new Label(odrediNazivUloge(igrac));
        oznakaUloge.setFont(Font.font("Georgia", FontWeight.BOLD, 17));
        oznakaUloge.setTextFill(Color.WHITE);

        redIkoneINaziva.getChildren().addAll(ikona, oznakaUloge);

        Label oznakaIgraca = new Label(igrac.getNaziv());
        oznakaIgraca.setFont(Font.font("Verdana", 11));
        oznakaIgraca.setTextFill(Color.web("#F0F0F0"));

        zaglavlje.getChildren().addAll(redIkoneINaziva, oznakaIgraca);
        return zaglavlje;
    }

    public void postaviOkvirIVanjskiStil(VBox kartica, boolean naPotezu) {
        kartica.setPadding(new Insets(0, 0, 18, 0));
        kartica.setAlignment(Pos.TOP_LEFT);
        kartica.setPrefWidth(260);
        kartica.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(12), Insets.EMPTY)));
        if (naPotezu) {
            kartica.setBorder(new Border(new BorderStroke(Color.web("#C9A227"), BorderStrokeStyle.SOLID,
                    new CornerRadii(12), new BorderWidths(3))));
        } else {
            kartica.setBorder(new Border(new BorderStroke(Color.web("#D8CDB8"), BorderStrokeStyle.SOLID,
                    new CornerRadii(12), new BorderWidths(1.5))));
        }
        kartica.setEffect(napraviSjenu());
    }

    public Label napraviOznaku(String tekst) {
        Label oznaka = new Label(tekst);
        oznaka.setTextFill(Color.web("#3A332B"));
        oznaka.setFont(Font.font("Verdana", 13));
        return oznaka;
    }

    public Label napraviOznakuBodova(KlasaIgraca igrac) {
        Label oznakaBodova = new Label("BODOVI: " + igrac.getBodoviPobjede());
        oznakaBodova.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        oznakaBodova.setTextFill(Color.web(odrediBojuTrake(igrac)));
        return oznakaBodova;
    }

    public VBox napraviMiniGraf(KlasaIgraca igrac, double trenutnaVrijednost, double maksimum, String oznakaGrafa) {
        VBox blok = new VBox(4);
        Label naslovGrafa = new Label(oznakaGrafa);
        naslovGrafa.setFont(Font.font("Verdana", 10));
        naslovGrafa.setTextFill(Color.web("#6B6357"));

        ProgressBar trakaNapretka = new ProgressBar();
        double udio = trenutnaVrijednost / maksimum;
        if (udio > 1.0) {
            udio = 1.0;
        }
        if (udio < 0.0) {
            udio = 0.0;
        }
        trakaNapretka.setProgress(udio);
        trakaNapretka.setPrefWidth(220);
        trakaNapretka.setStyle("-fx-accent: " + odrediBojuTrake(igrac) + ";");

        blok.getChildren().addAll(naslovGrafa, trakaNapretka);
        return blok;
    }

    private DropShadow napraviSjenu() {
        DropShadow sjena = new DropShadow();
        sjena.setRadius(12);
        sjena.setOffsetY(5);
        sjena.setColor(Color.color(0, 0, 0, 0.3));
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
        ikona.setScaleX(0.9);
        ikona.setScaleY(0.9);

        if (igrac instanceof RadnickaKlasa) {
            ikona.setContent("M12 2 L14 8 L20 8 L15 12 L17 18 L12 14 L7 18 L9 12 L4 8 L10 8 Z");
        } else if (igrac instanceof SrednjaKlasa) {
            ikona.setContent("M4 8 H20 V18 H4 Z M9 8 V5 H15 V8");
        } else if (igrac instanceof KapitalistickaKlasa) {
            ikona.setContent("M4 10 H20 V18 H4 Z M4 10 L12 4 L20 10 M8 13 H10 V16 H8 Z M14 13 H16 V16 H14 Z");
        } else {
            ikona.setContent("M4 20 V10 L12 4 L20 10 V20 M9 20 V14 H15 V20 M6 10 V20 M18 10 V20");
        }
        return ikona;
    }
}