package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.model.KapitalistickaKlasa;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.SrednjaKlasa;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class StilGumba {

    private static final String OSNOVNI_STIL =
            "-fx-background-radius: 6; -fx-font-family: 'Verdana'; -fx-font-size: 12px; "
                    + "-fx-cursor: hand; -fx-padding: 8 16 8 16;";

    public static String dohvatiBojuKlase(KlasaIgraca igrac) {
        if (igrac instanceof RadnickaKlasa) {
            return "#8C3A36";
        } else if (igrac instanceof SrednjaKlasa) {
            return "#A6862C";
        } else if (igrac instanceof KapitalistickaKlasa) {
            return "#2B4C70";
        } else {
            return "#4A4458";
        }
    }

    public static void primijeniObrubAktivneKlase(VBox panel, KlasaIgraca igrac) {
        String boja = dohvatiBojuKlase(igrac);
        panel.setStyle("-fx-border-color: " + boja + "; -fx-border-width: 2; -fx-border-radius: 8; "
                + "-fx-background-color: white; -fx-background-radius: 8;");
    }

    public static void primijeniAkcijskiGumb(Button gumb, KlasaIgraca igrac) {
        String boja = dohvatiBojuKlase(igrac);
        String stilOsnovni = OSNOVNI_STIL + "-fx-background-color: #F4F1EA; -fx-text-fill: #2B2520; "
                + "-fx-border-color: " + boja + "; -fx-border-width: 0 0 0 4;";
        String stilHover = OSNOVNI_STIL + "-fx-background-color: " + boja + "; -fx-text-fill: white; "
                + "-fx-border-color: " + boja + "; -fx-border-width: 0 0 0 4;";
        gumb.setStyle(stilOsnovni);
        gumb.setOnMouseEntered(dogadjaj -> gumb.setStyle(stilHover));
        gumb.setOnMouseExited(dogadjaj -> gumb.setStyle(stilOsnovni));
    }

    public static void primijeniNeutralni(Button gumb) {
        gumb.setStyle(OSNOVNI_STIL + "-fx-background-color: #45525C; -fx-text-fill: white;");
        dodajHoverEfekt(gumb, "#45525C", "#5A6B78", "white");
    }

    public static void primijeniPozitivni(Button gumb) {
        gumb.setStyle(OSNOVNI_STIL + "-fx-background-color: #4C7A50; -fx-text-fill: white;");
        dodajHoverEfekt(gumb, "#4C7A50", "#5E9463", "white");
    }

    public static void primijeniNegativni(Button gumb) {
        gumb.setStyle(OSNOVNI_STIL + "-fx-background-color: #A8453F; -fx-text-fill: white;");
        dodajHoverEfekt(gumb, "#A8453F", "#C25750", "white");
    }

    public static void primijeniNaglaseni(Button gumb) {
        gumb.setStyle(OSNOVNI_STIL + "-fx-background-color: #C9A227; -fx-text-fill: #2B2520; -fx-font-weight: bold;");
        dodajHoverEfekt(gumb, "#C9A227", "#DBB940", "#2B2520");
    }

    private static void dodajHoverEfekt(Button gumb, String bojaOsnovna, String bojaHover, String bojaTeksta) {
        String stilOsnovni = OSNOVNI_STIL + "-fx-background-color: " + bojaOsnovna + "; -fx-text-fill: " + bojaTeksta + ";";
        String stilHover = OSNOVNI_STIL + "-fx-background-color: " + bojaHover + "; -fx-text-fill: " + bojaTeksta + ";";
        gumb.setOnMouseEntered(dogadjaj -> gumb.setStyle(stilHover));
        gumb.setOnMouseExited(dogadjaj -> gumb.setStyle(stilOsnovni));
    }
}