package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.model.KapitalistickaKlasa;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.SrednjaKlasa;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.control.ProgressBar;

public class KreatorKartice {

    public VBox napraviZaglavljeKartice(KlasaIgraca igrac) {
        String bojaTraka = StilGumba.dohvatiBojuKlase(igrac);

        VBox zaglavlje = new VBox(4);
        zaglavlje.setAlignment(Pos.CENTER_LEFT);
        zaglavlje.setPadding(new Insets(14, 18, 14, 18));
        zaglavlje.setBackground(new Background(new BackgroundFill(Color.web(StilGumba.POZADINA_TAMNA),
                new CornerRadii(6, 6, 0, 0, false), Insets.EMPTY)));
        zaglavlje.setBorder(new Border(new BorderStroke(Color.web(bojaTraka), BorderStrokeStyle.SOLID,
                new CornerRadii(0), new BorderWidths(0, 0, 2, 0))));

        HBox redIkoneINaziva = new HBox(10);
        redIkoneINaziva.setAlignment(Pos.CENTER_LEFT);

        SVGPath ikona = napraviIkonuKlase(igrac);
        ikona.setFill(Color.web(bojaTraka));

        Label oznakaUloge = new Label(odrediNazivUloge(igrac).toUpperCase());
        oznakaUloge.setFont(Font.font("Arial Black", FontWeight.BOLD, 14));
        oznakaUloge.setTextFill(Color.web(bojaTraka));
        oznakaUloge.setWrapText(true);
        oznakaUloge.setMaxWidth(180);

        redIkoneINaziva.getChildren().addAll(ikona, oznakaUloge);

        Label oznakaIgraca = new Label(igrac.getNaziv());
        oznakaIgraca.setFont(Font.font("Verdana", 11));
        oznakaIgraca.setTextFill(Color.web(StilGumba.TEKST_SIVI));

        zaglavlje.getChildren().addAll(redIkoneINaziva, oznakaIgraca);
        return zaglavlje;
    }

    public void postaviOkvirIVanjskiStil(VBox kartica, boolean naPotezu) {
        kartica.setPadding(new Insets(0, 0, 18, 0));
        kartica.setAlignment(Pos.TOP_LEFT);
        kartica.setPrefWidth(260);
        kartica.setBackground(new Background(new BackgroundFill(Color.web(StilGumba.POVRSINA_TAMNA), new CornerRadii(6), Insets.EMPTY)));
        if (naPotezu) {
            kartica.setBorder(new Border(new BorderStroke(Color.web("#00D4FF"), BorderStrokeStyle.SOLID,
                    new CornerRadii(6), new BorderWidths(3))));
            kartica.setEffect(StilGumba.napraviNeonSjenu("#00D4FF"));
        } else {
            kartica.setBorder(new Border(new BorderStroke(Color.web(StilGumba.TEKST_SIVI), BorderStrokeStyle.SOLID,
                    new CornerRadii(6), new BorderWidths(1))));
            kartica.setEffect(null);
        }
    }

    public Label napraviOznaku(String tekst) {
        Label oznaka = new Label(tekst);
        oznaka.setTextFill(Color.web(StilGumba.TEKST_SVIJETLI));
        oznaka.setFont(Font.font("Verdana", 13));
        return oznaka;
    }

    public Label napraviOznakuBodova(KlasaIgraca igrac) {
        Label oznakaBodova = new Label("BODOVI: " + igrac.getBodoviPobjede());
        oznakaBodova.setFont(Font.font("Arial Black", FontWeight.BOLD, 15));
        oznakaBodova.setTextFill(Color.web(StilGumba.dohvatiBojuKlase(igrac)));
        return oznakaBodova;
    }

    public VBox napraviMiniGraf(KlasaIgraca igrac, double trenutnaVrijednost, double maksimum, String oznakaGrafa) {
        VBox blok = new VBox(4);
        Label naslovGrafa = new Label(oznakaGrafa.toUpperCase());
        naslovGrafa.setFont(Font.font("Verdana", FontWeight.BOLD, 9));
        naslovGrafa.setTextFill(Color.web(StilGumba.TEKST_SIVI));

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
        String bojaTrake = StilGumba.dohvatiBojuKlase(igrac);
        trakaNapretka.setStyle("-fx-accent: " + bojaTrake + "; -fx-control-inner-background: #0D0D12;");

        blok.getChildren().addAll(naslovGrafa, trakaNapretka);
        return blok;
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

    private SVGPath napraviIkonuKlase(KlasaIgraca igrac) {
        SVGPath ikona = new SVGPath();
        ikona.setScaleX(0.9);
        ikona.setScaleY(0.9);

        if (igrac instanceof RadnickaKlasa) {
            ikona.setContent("M12 2 L14 8 L20 8 L15 12 L17 18 L12 14 L7 18 L9 12 L4 8 L10 8 Z");
        } else if (igrac instanceof SrednjaKlasa) {
            ikona.setContent("M4 8 H20 V18 H4 Z M9 8 V5 H15 V8");
        } else if (igrac instanceof KapitalistickaKlasa) {
            ikona.setContent("M3 18 H21 V20 H3 Z M5 18 V10 H7 V18 Z M9 18 V6 H11 V18 Z M13 18 V11 H15 V18 Z M17 18 V8 H19 V18 Z");
        } else {
            ikona.setContent("M4 20 H20 V21 H4 Z M5 20 V11 H6.5 V20 Z M8 20 V11 H9.5 V20 Z M11.25 20 V11 H12.75 V20 Z "
                    + "M14.5 20 V11 H16 V20 Z M17.5 20 V11 H19 V20 Z M3 11 L12 4 L21 11 Z");
        }
        return ikona;
    }
}