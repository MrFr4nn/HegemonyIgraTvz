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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import java.util.List;

public class KreatorIzvjestaja {

    public VBox napraviPanelIzvjestaja(String naslov, List<String> redoviIzvjestaja, List<KlasaIgraca> listaIgraca) {
        VBox panel = new VBox(8);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(5));

        Label oznakaNaslova = new Label(naslov.toUpperCase());
        oznakaNaslova.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        oznakaNaslova.setStyle("-fx-text-fill: #E3D9C4;");

        VBox blokRedova = new VBox(6);
        blokRedova.setAlignment(Pos.CENTER);

        int brojac = 0;
        while (brojac < redoviIzvjestaja.size() && brojac < listaIgraca.size()) {
            HBox redak = napraviRedakIzvjestaja(listaIgraca.get(brojac), redoviIzvjestaja.get(brojac));
            blokRedova.getChildren().add(redak);
            brojac = brojac + 1;
        }

        ScrollPane skrolnaLista = new ScrollPane(blokRedova);
        skrolnaLista.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        skrolnaLista.setPrefHeight(110);
        skrolnaLista.setFitToWidth(true);

        panel.getChildren().addAll(oznakaNaslova, skrolnaLista);
        return panel;
    }

    private HBox napraviRedakIzvjestaja(KlasaIgraca igrac, String tekstRetka) {
        HBox redak = new HBox(12);
        redak.setAlignment(Pos.CENTER_LEFT);
        redak.setPadding(new Insets(8, 14, 8, 14));
        redak.setMaxWidth(650);

        String bojaHex = StilGumba.dohvatiBojuKlase(igrac);

        redak.setBackground(new Background(new BackgroundFill(Color.web("#3A332B"), new CornerRadii(8), Insets.EMPTY)));
        redak.setBorder(new Border(new BorderStroke(Color.web(bojaHex), BorderStrokeStyle.SOLID,
                new CornerRadii(8), new BorderWidths(0, 0, 0, 4))));

        Label oznakaIgraca = new Label(skratiNazivIgraca(igrac));
        oznakaIgraca.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
        oznakaIgraca.setStyle("-fx-text-fill: " + bojaHex + ";");
        oznakaIgraca.setMinWidth(140);

        Label oznakaTeksta = new Label(skratiOpisRetka(tekstRetka));
        oznakaTeksta.setFont(Font.font("Verdana", 11));
        oznakaTeksta.setStyle("-fx-text-fill: #E3D9C4;");
        oznakaTeksta.setWrapText(true);

        redak.getChildren().addAll(oznakaIgraca, oznakaTeksta);
        return redak;
    }

    private String skratiNazivIgraca(KlasaIgraca igrac) {
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

    private String skratiOpisRetka(String tekstRetka) {
        int pozicijaCrtice = tekstRetka.indexOf(" - Igrac");
        if (pozicijaCrtice == -1) {
            return tekstRetka;
        }
        int pozicijaTocke = tekstRetka.indexOf(". ", pozicijaCrtice);
        if (pozicijaTocke == -1) {
            int pozicijaJe = tekstRetka.indexOf(" je ");
            if (pozicijaJe != -1) {
                return tekstRetka.substring(pozicijaJe + 4);
            }
            int pozicijaNema = tekstRetka.indexOf(" nema ");
            if (pozicijaNema != -1) {
                return "Nema " + tekstRetka.substring(pozicijaNema + 6);
            }
            return tekstRetka;
        }
        return tekstRetka.substring(pozicijaTocke + 2);
    }
}