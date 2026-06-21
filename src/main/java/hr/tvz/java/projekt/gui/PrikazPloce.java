package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.Vlada;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;

public class PrikazPloce {

    private Rectangle stupacProracuna;
    private Rectangle stupacStandarda;
    private Label oznakaProracuna;
    private Label oznakaStandarda;
    private Label oznakaPoreza;
    private Label oznakaMinPlace;
    private Label oznakaHrane;
    private Label oznakaBodova;

    public GridPane napraviDrzavnuPlocu(RadnickaKlasa radnickaKlasa, Vlada vlada) {
        GridPane plocaIgre = new GridPane();
        plocaIgre.setHgap(20);
        plocaIgre.setVgap(15);
        plocaIgre.setPadding(new Insets(20));
        plocaIgre.setBackground(new Background(new BackgroundFill(Color.web("#2b3a42"), CornerRadii.EMPTY, Insets.EMPTY)));

        Label naslovPloce = napraviNaslov("DRZAVNA PLOCA - HEGEMONY TVZ");
        plocaIgre.add(naslovPloce, 0, 0, 2, 1);

        VBox blokVlade = napraviBlokVlade(vlada);
        VBox blokRadnika = napraviBlokRadnickeKlase(radnickaKlasa);

        plocaIgre.add(blokVlade, 0, 1);
        plocaIgre.add(blokRadnika, 1, 1);

        HBox blokGrafova = napraviBlokGrafova(radnickaKlasa, vlada);
        plocaIgre.add(blokGrafova, 0, 2, 2, 1);

        return plocaIgre;
    }

    private Label napraviNaslov(String tekst) {
        Label naslov = new Label(tekst);
        naslov.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        naslov.setTextFill(Color.GOLD);
        return naslov;
    }

    private VBox napraviBlokVlade(Vlada vlada) {
        VBox blok = new VBox(8);
        blok.setPadding(new Insets(15));
        blok.setBackground(new Background(new BackgroundFill(Color.web("#3a4a52"), new CornerRadii(10), Insets.EMPTY)));
        blok.setAlignment(Pos.TOP_LEFT);

        Label naslovBloka = new Label("VLADA: " + vlada.getNaziv());
        naslovBloka.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        naslovBloka.setTextFill(Color.LIGHTBLUE);

        oznakaProracuna = new Label("Proracun: " + vlada.getDrzavniProracun());
        oznakaPoreza = new Label("Stopa poreza: " + vlada.getStopaPoreza());
        oznakaMinPlace = new Label("Minimalna placa: " + vlada.getMinimalnaPlaca());
        oznakaBodova = new Label("Bodovi: " + vlada.getBodoviPobjede());

        postaviBijeliTekst(oznakaProracuna);
        postaviBijeliTekst(oznakaPoreza);
        postaviBijeliTekst(oznakaMinPlace);
        postaviBijeliTekst(oznakaBodova);

        blok.getChildren().addAll(naslovBloka, oznakaProracuna, oznakaPoreza, oznakaMinPlace, oznakaBodova);
        return blok;
    }

    private VBox napraviBlokRadnickeKlase(RadnickaKlasa radnickaKlasa) {
        VBox blok = new VBox(8);
        blok.setPadding(new Insets(15));
        blok.setBackground(new Background(new BackgroundFill(Color.web("#52423a"), new CornerRadii(10), Insets.EMPTY)));
        blok.setAlignment(Pos.TOP_LEFT);

        Label naslovBloka = new Label("RADNICKA KLASA: " + radnickaKlasa.getNaziv());
        naslovBloka.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        naslovBloka.setTextFill(Color.ORANGE);

        oznakaStandarda = new Label("Standard zivota: " + radnickaKlasa.getStandardZivota());
        oznakaHrane = new Label("Kolicina hrane: " + radnickaKlasa.getKolicinaHrane());
        Label oznakaRadnika = new Label("Zaposleni: " + radnickaKlasa.getZaposleniRadnici() + " / " + radnickaKlasa.getBrojRadnika());
        Label oznakaBodovaRadnika = new Label("Bodovi: " + radnickaKlasa.getBodoviPobjede());

        postaviBijeliTekst(oznakaStandarda);
        postaviBijeliTekst(oznakaHrane);
        postaviBijeliTekst(oznakaRadnika);
        postaviBijeliTekst(oznakaBodovaRadnika);

        blok.getChildren().addAll(naslovBloka, oznakaStandarda, oznakaHrane, oznakaRadnika, oznakaBodovaRadnika);
        return blok;
    }

    private HBox napraviBlokGrafova(RadnickaKlasa radnickaKlasa, Vlada vlada) {
        HBox blokGrafova = new HBox(40);
        blokGrafova.setPadding(new Insets(15));
        blokGrafova.setAlignment(Pos.BOTTOM_CENTER);

        stupacProracuna = new Rectangle(40, izracunajVisinuStupca(vlada.getDrzavniProracun(), 200));
        stupacProracuna.setFill(Color.LIGHTBLUE);

        stupacStandarda = new Rectangle(40, izracunajVisinuStupca(radnickaKlasa.getStandardZivota(), 100));
        stupacStandarda.setFill(Color.ORANGE);

        VBox grafProracuna = new VBox(5, stupacProracuna, new Label("Proracun"));
        VBox grafStandarda = new VBox(5, stupacStandarda, new Label("Standard"));
        grafProracuna.setAlignment(Pos.BOTTOM_CENTER);
        grafStandarda.setAlignment(Pos.BOTTOM_CENTER);

        blokGrafova.getChildren().addAll(grafProracuna, grafStandarda);
        return blokGrafova;
    }

    private double izracunajVisinuStupca(double vrijednost, double maksimum) {
        double omjer = vrijednost / maksimum;
        double visina = omjer * 150;
        if (visina < 5) {
            visina = 5;
        }
        if (visina > 250) {
            visina = 250;
        }
        return visina;
    }

    private void postaviBijeliTekst(Label oznaka) {
        oznaka.setTextFill(Color.WHITE);
        oznaka.setFont(Font.font("Arial", 13));
    }

    public void azurirajPrikaz(RadnickaKlasa radnickaKlasa, Vlada vlada) {
        oznakaProracuna.setText("Proracun: " + String.format("%.2f", vlada.getDrzavniProracun()));
        oznakaPoreza.setText("Stopa poreza: " + vlada.getStopaPoreza());
        oznakaMinPlace.setText("Minimalna placa: " + vlada.getMinimalnaPlaca());
        oznakaBodova.setText("Bodovi: " + vlada.getBodoviPobjede());
        oznakaStandarda.setText("Standard zivota: " + radnickaKlasa.getStandardZivota());
        oznakaHrane.setText("Kolicina hrane: " + radnickaKlasa.getKolicinaHrane());

        stupacProracuna.setHeight(izracunajVisinuStupca(vlada.getDrzavniProracun(), 200));
        stupacStandarda.setHeight(izracunajVisinuStupca(radnickaKlasa.getStandardZivota(), 100));
    }
}