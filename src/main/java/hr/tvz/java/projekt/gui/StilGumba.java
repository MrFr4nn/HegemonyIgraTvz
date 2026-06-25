package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.model.KapitalistickaKlasa;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.SrednjaKlasa;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class StilGumba {

    public static final String POZADINA_TAMNA = "#0D0D12";
    public static final String POVRSINA_TAMNA = "#16161D";
    public static final String TEKST_SVIJETLI = "#E8E8F0";
    public static final String TEKST_SIVI = "#8A8A9A";

    private static final String OSNOVNI_STIL =
            "-fx-background-radius: 4; -fx-font-family: 'Verdana'; -fx-font-size: 12px; "
                    + "-fx-cursor: hand; -fx-padding: 8 16 8 16; -fx-font-weight: bold;";

    public static String dohvatiBojuKlase(KlasaIgraca igrac) {
        if (igrac instanceof RadnickaKlasa) {
            return "#FF3B5C";
        } else if (igrac instanceof SrednjaKlasa) {
            return "#F5D400";
        } else if (igrac instanceof KapitalistickaKlasa) {
            return "#00F5A0";
        } else {
            return "#00D4FF";
        }
    }

    public static DropShadow napraviNeonSjenu(String bojaHex) {
        DropShadow sjena = new DropShadow();
        sjena.setRadius(18);
        sjena.setSpread(0.15);
        sjena.setColor(Color.web(bojaHex, 0.55));
        return sjena;
    }

    public static void primijeniObrubAktivneKlase(VBox panel, KlasaIgraca igrac) {
        String boja = dohvatiBojuKlase(igrac);
        panel.setStyle("-fx-border-color: " + boja + "; -fx-border-width: 2; -fx-border-radius: 4; "
                + "-fx-background-color: " + POVRSINA_TAMNA + "; -fx-background-radius: 4;");
        panel.setEffect(napraviNeonSjenu(boja));
    }

    public static void primijeniAkcijskiGumb(Button gumb, KlasaIgraca igrac) {
        String boja = dohvatiBojuKlase(igrac);
        String stilOsnovni = OSNOVNI_STIL + "-fx-background-color: " + POZADINA_TAMNA + "; -fx-text-fill: " + boja + "; "
                + "-fx-border-color: " + boja + "; -fx-border-width: 2;";
        String stilHover = OSNOVNI_STIL + "-fx-background-color: " + boja + "; -fx-text-fill: " + POZADINA_TAMNA + "; "
                + "-fx-border-color: " + boja + "; -fx-border-width: 2;";
        gumb.setStyle(stilOsnovni);
        gumb.setOnMouseEntered(dogadjaj -> gumb.setStyle(stilHover));
        gumb.setOnMouseExited(dogadjaj -> gumb.setStyle(stilOsnovni));
    }

    public static void primijeniNeutralni(Button gumb) {
        gumb.setStyle(OSNOVNI_STIL + "-fx-background-color: " + POVRSINA_TAMNA + "; -fx-text-fill: " + TEKST_SVIJETLI
                + "; -fx-border-color: " + TEKST_SIVI + "; -fx-border-width: 1.5;");
        dodajHoverEfekt(gumb, POVRSINA_TAMNA, "#22222E", TEKST_SVIJETLI);
    }

    public static void primijeniPozitivni(Button gumb) {
        gumb.setStyle(OSNOVNI_STIL + "-fx-background-color: " + POZADINA_TAMNA + "; -fx-text-fill: #00F5A0; "
                + "-fx-border-color: #00F5A0; -fx-border-width: 2;");
        dodajHoverEfekt(gumb, POZADINA_TAMNA, "#00F5A0", POZADINA_TAMNA);
    }

    public static void primijeniNegativni(Button gumb) {
        gumb.setStyle(OSNOVNI_STIL + "-fx-background-color: " + POZADINA_TAMNA + "; -fx-text-fill: #FF3B5C; "
                + "-fx-border-color: #FF3B5C; -fx-border-width: 2;");
        dodajHoverEfekt(gumb, POZADINA_TAMNA, "#FF3B5C", POZADINA_TAMNA);
    }

    public static void primijeniNaglaseni(Button gumb) {
        gumb.setStyle(OSNOVNI_STIL + "-fx-background-color: #00D4FF; -fx-text-fill: " + POZADINA_TAMNA + "; "
                + "-fx-border-color: #00D4FF; -fx-border-width: 2; -fx-font-size: 13px;");
        dodajHoverEfekt(gumb, "#00D4FF", "#5CE5FF", POZADINA_TAMNA);
    }

    private static void dodajHoverEfekt(Button gumb, String bojaOsnovna, String bojaHover, String bojaTeksta) {
        String bojaObrubaOsnovna = bojaOsnovna.equals(POVRSINA_TAMNA) ? TEKST_SIVI : bojaOsnovna;
        String stilOsnovni = OSNOVNI_STIL + "-fx-background-color: " + bojaOsnovna + "; -fx-text-fill: " + bojaTeksta + ";"
                + "-fx-border-color: " + bojaObrubaOsnovna + "; -fx-border-width: 2;";
        String stilHover = OSNOVNI_STIL + "-fx-background-color: " + bojaHover + "; -fx-text-fill: " + bojaTeksta + ";"
                + "-fx-border-color: " + bojaHover + "; -fx-border-width: 2;";
        gumb.setOnMouseEntered(dogadjaj -> gumb.setStyle(stilHover));
        gumb.setOnMouseExited(dogadjaj -> gumb.setStyle(stilOsnovni));
    }
}