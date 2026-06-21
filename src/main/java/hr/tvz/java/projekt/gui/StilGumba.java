package hr.tvz.java.projekt.gui;

import javafx.scene.control.Button;

public class StilGumba {

    private static final String OSNOVNI_STIL =
            "-fx-background-radius: 6; -fx-font-family: 'Verdana'; -fx-font-size: 12px; "
                    + "-fx-cursor: hand; -fx-padding: 8 16 8 16;";

    public static void primijeniNeutralni(Button gumb) {
        gumb.setStyle(OSNOVNI_STIL + "-fx-background-color: #45525C; -fx-text-fill: white;");
        dodajHoverEfekt(gumb, "#45525C", "#5A6B78");
    }

    public static void primijeniPozitivni(Button gumb) {
        gumb.setStyle(OSNOVNI_STIL + "-fx-background-color: #4C7A50; -fx-text-fill: white;");
        dodajHoverEfekt(gumb, "#4C7A50", "#5E9463");
    }

    public static void primijeniNegativni(Button gumb) {
        gumb.setStyle(OSNOVNI_STIL + "-fx-background-color: #A8453F; -fx-text-fill: white;");
        dodajHoverEfekt(gumb, "#A8453F", "#C25750");
    }

    public static void primijeniNaglaseni(Button gumb) {
        gumb.setStyle(OSNOVNI_STIL + "-fx-background-color: #C9A227; -fx-text-fill: #2B2520; -fx-font-weight: bold;");
        dodajHoverEfekt(gumb, "#C9A227", "#DBB940");
    }

    private static void dodajHoverEfekt(Button gumb, String bojaOsnovna, String bojaHover) {
        String stilOsnovni = OSNOVNI_STIL + "-fx-background-color: " + bojaOsnovna + ";"
                + odrediBojuTeksta(gumb);
        String stilHover = OSNOVNI_STIL + "-fx-background-color: " + bojaHover + ";"
                + odrediBojuTeksta(gumb);

        gumb.setOnMouseEntered(dogadjaj -> gumb.setStyle(stilHover));
        gumb.setOnMouseExited(dogadjaj -> gumb.setStyle(stilOsnovni));
    }

    private static String odrediBojuTeksta(Button gumb) {
        if (gumb.getStyle().contains("#2B2520")) {
            return "-fx-text-fill: #2B2520; -fx-font-weight: bold;";
        }
        return "-fx-text-fill: white;";
    }
}