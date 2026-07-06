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

    private static final String HEX_CRVENA = "#FF3B5C";
    private static final String HEX_ZELENA = "#00F5A0";
    private static final String HEX_PLAVA = "#00D4FF";
    private static final String HEX_SVJETLO_PLAVA = "#5CE5FF";

    private static final String STIL_VELIKOG_GUMBA = " -fx-font-size: 14px; -fx-padding: 12 30 12 30;";

    private static final String OSNOVNI_STIL =
            "-fx-background-radius: 4; -fx-font-family: 'Verdana'; -fx-font-size: 12px; "
                    + "-fx-cursor: hand; -fx-padding: 8 16 8 16; -fx-font-weight: bold;";
    private static final String STIL_POZADINA = "-fx-background-color: ";
    private static final String STIL_TEKST = "; -fx-text-fill: ";
    private static final String STIL_BORDER = "; -fx-border-color: ";
    private static final String STIL_BORDER_WIDTH = "; -fx-border-width: 2;";

    private StilGumba() {
    }

    public static String dohvatiBojuKlase(KlasaIgraca igrac) {
        if (igrac instanceof RadnickaKlasa) {
            return HEX_CRVENA;
        } else if (igrac instanceof SrednjaKlasa) {
            return "#F5D400";
        } else if (igrac instanceof KapitalistickaKlasa) {
            return HEX_ZELENA;
        } else {
            return HEX_PLAVA;
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
                + STIL_POZADINA + POVRSINA_TAMNA + "; -fx-background-radius: 4;");
        panel.setEffect(napraviNeonSjenu(boja));
    }

    public static void primijeniAkcijskiGumb(Button gumb, KlasaIgraca igrac) {
        String boja = dohvatiBojuKlase(igrac);
        String stilOsnovni = OSNOVNI_STIL + STIL_POZADINA + POZADINA_TAMNA + STIL_TEKST + boja
                + STIL_BORDER + boja + STIL_BORDER_WIDTH;
        String stilHover = OSNOVNI_STIL + STIL_POZADINA + boja + STIL_TEKST + POZADINA_TAMNA
                + STIL_BORDER + boja + STIL_BORDER_WIDTH;
        gumb.setStyle(stilOsnovni);
        gumb.setOnMouseEntered(d -> gumb.setStyle(stilHover));
        gumb.setOnMouseExited(d -> gumb.setStyle(stilOsnovni));
    }

    public static void primijeniNeutralni(Button gumb) {
        String stilOsnovni = OSNOVNI_STIL + STIL_POZADINA + POVRSINA_TAMNA + STIL_TEKST + TEKST_SVIJETLI
                + STIL_BORDER + TEKST_SIVI + "; -fx-border-width: 1.5;";
        String stilHover = OSNOVNI_STIL + STIL_POZADINA + "#22222E" + STIL_TEKST + TEKST_SVIJETLI
                + STIL_BORDER + TEKST_SIVI + "; -fx-border-width: 1.5;";
        gumb.setStyle(stilOsnovni);
        gumb.setOnMouseEntered(d -> gumb.setStyle(stilHover));
        gumb.setOnMouseExited(d -> gumb.setStyle(stilOsnovni));
    }

    public static void primijeniPozitivni(Button gumb) {
        String stilOsnovni = OSNOVNI_STIL + STIL_POZADINA + POZADINA_TAMNA + STIL_TEKST + HEX_ZELENA
                + STIL_BORDER + HEX_ZELENA + STIL_BORDER_WIDTH;
        String stilHover = OSNOVNI_STIL + STIL_POZADINA + HEX_ZELENA + STIL_TEKST + POZADINA_TAMNA
                + STIL_BORDER + HEX_ZELENA + STIL_BORDER_WIDTH;
        gumb.setStyle(stilOsnovni);
        gumb.setOnMouseEntered(d -> gumb.setStyle(stilHover));
        gumb.setOnMouseExited(d -> gumb.setStyle(stilOsnovni));
    }

    public static void primijeniNegativni(Button gumb) {
        String stilOsnovni = OSNOVNI_STIL + STIL_POZADINA + POZADINA_TAMNA + STIL_TEKST + HEX_CRVENA
                + STIL_BORDER + HEX_CRVENA + STIL_BORDER_WIDTH;
        String stilHover = OSNOVNI_STIL + STIL_POZADINA + HEX_CRVENA + STIL_TEKST + POZADINA_TAMNA
                + STIL_BORDER + HEX_CRVENA + STIL_BORDER_WIDTH;
        gumb.setStyle(stilOsnovni);
        gumb.setOnMouseEntered(d -> gumb.setStyle(stilHover));
        gumb.setOnMouseExited(d -> gumb.setStyle(stilOsnovni));
    }

    public static void primijeniNaglaseni(Button gumb) {
        String stilOsnovni = OSNOVNI_STIL + STIL_POZADINA + HEX_PLAVA + STIL_TEKST + POZADINA_TAMNA
                + STIL_BORDER + HEX_PLAVA + STIL_BORDER_WIDTH + " -fx-font-size: 13px;";
        String stilHover = OSNOVNI_STIL + STIL_POZADINA + HEX_SVJETLO_PLAVA + STIL_TEKST + POZADINA_TAMNA
                + STIL_BORDER + HEX_SVJETLO_PLAVA + STIL_BORDER_WIDTH + " -fx-font-size: 13px;";
        gumb.setStyle(stilOsnovni);
        gumb.setOnMouseEntered(d -> gumb.setStyle(stilHover));
        gumb.setOnMouseExited(d -> gumb.setStyle(stilOsnovni));
    }

    public static void primijeniNaglaseniVeliki(Button gumb) {
        String stilOsnovni = OSNOVNI_STIL + STIL_POZADINA + HEX_PLAVA + STIL_TEKST + POZADINA_TAMNA
                + STIL_BORDER + HEX_PLAVA + STIL_BORDER_WIDTH + " -fx-font-size: 15px; -fx-padding: 12 30 12 30;";
        String stilHover = OSNOVNI_STIL + STIL_POZADINA + HEX_SVJETLO_PLAVA + STIL_TEKST + POZADINA_TAMNA
                + STIL_BORDER + HEX_SVJETLO_PLAVA + STIL_BORDER_WIDTH + " -fx-font-size: 15px; -fx-padding: 12 30 12 30;";
        gumb.setStyle(stilOsnovni);
        gumb.setOnMouseEntered(d -> gumb.setStyle(stilHover));
        gumb.setOnMouseExited(d -> gumb.setStyle(stilOsnovni));
    }

    public static void primijeniPozitivniVeliki(Button gumb) {
        String stilOsnovni = OSNOVNI_STIL + STIL_POZADINA + POZADINA_TAMNA + STIL_TEKST + HEX_ZELENA
                + STIL_BORDER + HEX_ZELENA + STIL_BORDER_WIDTH + STIL_VELIKOG_GUMBA;
        String stilHover = OSNOVNI_STIL + STIL_POZADINA + HEX_ZELENA + STIL_TEKST + POZADINA_TAMNA
                + STIL_BORDER + HEX_ZELENA + STIL_BORDER_WIDTH + STIL_VELIKOG_GUMBA;
        gumb.setStyle(stilOsnovni);
        gumb.setOnMouseEntered(d -> gumb.setStyle(stilHover));
        gumb.setOnMouseExited(d -> gumb.setStyle(stilOsnovni));
    }

    public static void primijeniNegativniVeliki(Button gumb) {
        String stilOsnovni = OSNOVNI_STIL + STIL_POZADINA + POZADINA_TAMNA + STIL_TEKST + HEX_CRVENA
                + STIL_BORDER + HEX_CRVENA + STIL_BORDER_WIDTH + STIL_VELIKOG_GUMBA;
        String stilHover = OSNOVNI_STIL + STIL_POZADINA + HEX_CRVENA + STIL_TEKST + POZADINA_TAMNA
                + STIL_BORDER + HEX_CRVENA + STIL_BORDER_WIDTH + STIL_VELIKOG_GUMBA;
        gumb.setStyle(stilOsnovni);
        gumb.setOnMouseEntered(d -> gumb.setStyle(stilHover));
        gumb.setOnMouseExited(d -> gumb.setStyle(stilOsnovni));
    }
}