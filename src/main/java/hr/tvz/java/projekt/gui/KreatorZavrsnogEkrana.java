package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.model.KlasaIgraca;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class KreatorZavrsnogEkrana {

    public void prikaziEkranPobjede(String nazivPobjednika, List<KlasaIgraca> listaIgraca) {
        Stage prozorPobjede = new Stage();
        prozorPobjede.setTitle("Kraj igre");

        VBox korijenskiLayout = new VBox(20);
        korijenskiLayout.setAlignment(Pos.CENTER);
        korijenskiLayout.setPadding(new Insets(40));
        korijenskiLayout.setBackground(new Background(new BackgroundFill(Color.web(StilGumba.POZADINA_TAMNA), CornerRadii.EMPTY, Insets.EMPTY)));

        Label naslovIgraJeZavrsena = new Label("IGRA JE ZAVRSENA");
        naslovIgraJeZavrsena.setFont(Font.font("Arial Black", FontWeight.BOLD, 22));
        naslovIgraJeZavrsena.setStyle("-fx-text-fill: " + StilGumba.TEKST_SIVI + ";");

        Label naslovPobjednika = new Label("POBJEDNIK: " + nazivPobjednika.toUpperCase());
        naslovPobjednika.setFont(Font.font("Arial Black", FontWeight.BOLD, 26));
        naslovPobjednika.setWrapText(true);
        naslovPobjednika.setAlignment(Pos.CENTER);
        naslovPobjednika.setStyle("-fx-text-fill: #00D4FF; -fx-text-alignment: center; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,212,255,0.45), 12, 0.25, 0, 0);");

        VBox rangLista = napraviRangListu(listaIgraca);

        Button gumbZatvori = new Button("ZATVORI");
        StilGumba.primijeniNaglaseniVeliki(gumbZatvori);
        gumbZatvori.setOnAction(dogadjaj -> prozorPobjede.close());

        korijenskiLayout.getChildren().addAll(naslovIgraJeZavrsena, naslovPobjednika, rangLista, gumbZatvori);

        Scene scenaPobjede = new Scene(korijenskiLayout, 600, 600);
        prozorPobjede.setScene(scenaPobjede);
        prozorPobjede.show();
    }

    private VBox napraviRangListu(List<KlasaIgraca> listaIgraca) {
        VBox lista = new VBox(10);
        lista.setAlignment(Pos.CENTER);

        List<KlasaIgraca> sortiranaLista = new ArrayList<>(listaIgraca);
        sortiranaLista.sort((prvi, drugi) -> drugi.getBodoviPobjede() - prvi.getBodoviPobjede());

        int rang = 1;
        for (KlasaIgraca igrac : sortiranaLista) {
            lista.getChildren().add(napraviRedakRanga(rang, igrac));
            rang = rang + 1;
        }
        return lista;
    }

    private HBox napraviRedakRanga(int rang, KlasaIgraca igrac) {
        HBox redak = new HBox(15);
        redak.setAlignment(Pos.CENTER_LEFT);
        redak.setPadding(new Insets(12, 20, 12, 20));
        redak.setMaxWidth(480);

        String bojaHex = StilGumba.dohvatiBojuKlase(igrac);

        redak.setBackground(new Background(new BackgroundFill(Color.web(StilGumba.POVRSINA_TAMNA), new CornerRadii(6), Insets.EMPTY)));
        redak.setBorder(new Border(new BorderStroke(Color.web(bojaHex), BorderStrokeStyle.SOLID,
                new CornerRadii(6), new BorderWidths(2))));

        if (rang == 1) {
            DropShadow sjenaPrvog = new DropShadow();
            sjenaPrvog.setRadius(15);
            sjenaPrvog.setColor(Color.web(bojaHex, 0.6));
            redak.setEffect(sjenaPrvog);
        }

        Label oznakaRanga = new Label("#" + rang);
        oznakaRanga.setFont(Font.font("Arial Black", FontWeight.BOLD, 18));
        oznakaRanga.setStyle("-fx-text-fill: " + bojaHex + ";");
        oznakaRanga.setMinWidth(40);

        Label oznakaNaziva = new Label(igrac.getNaziv());
        oznakaNaziva.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        oznakaNaziva.setStyle("-fx-text-fill: " + StilGumba.TEKST_SVIJETLI + ";");
        oznakaNaziva.setMinWidth(260);

        Label oznakaBodova = new Label(igrac.getBodoviPobjede() + " BODOVA");
        oznakaBodova.setFont(Font.font("Arial Black", FontWeight.BOLD, 14));
        oznakaBodova.setStyle("-fx-text-fill: " + bojaHex + ";");

        redak.getChildren().addAll(oznakaRanga, oznakaNaziva, oznakaBodova);
        return redak;
    }
}