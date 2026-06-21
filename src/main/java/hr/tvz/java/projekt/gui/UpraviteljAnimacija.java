package hr.tvz.java.projekt.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class UpraviteljAnimacija {

    public void pokreniAnimacijuDonosenjaZakona(Label oznakaPoruke, String tekstZakona) {
        Thread nitAnimacije = new Thread(() -> {
            int brojac = 0;
            while (brojac < 3) {
                String konacniTekst = "Donosenje zakona u tijeku..." + napraviTockice(brojac);
                Platform.runLater(() -> oznakaPoruke.setText(konacniTekst));
                pauzirajNit(300);
                brojac = brojac + 1;
            }
            Platform.runLater(() -> oznakaPoruke.setText("Zakon donesen: " + tekstZakona));
        });
        nitAnimacije.setDaemon(true);
        nitAnimacije.start();
    }

    private String napraviTockice(int brojac) {
        String rezultat = "";
        int privremena = 0;
        while (privremena <= brojac) {
            rezultat = rezultat + ".";
            privremena = privremena + 1;
        }
        return rezultat;
    }

    private void pauzirajNit(long milisekunde) {
        try {
            Thread.sleep(milisekunde);
        } catch (InterruptedException greska) {
            System.out.println("Animacijska nit je prekinuta: " + greska.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void animirajPromjenuStupca(Rectangle stupac, double novaVisina) {
        double staraVisina = stupac.getHeight();
        Timeline animacijaRasta = new Timeline();
        int brojKoraka = 10;
        double razlika = novaVisina - staraVisina;

        int brojac = 0;
        while (brojac <= brojKoraka) {
            double udio = (double) brojac / (double) brojKoraka;
            double trenutnaVisina = staraVisina + (razlika * udio);
            double konacnaVisina = trenutnaVisina;
            KeyFrame okvirAnimacije = new KeyFrame(Duration.millis(brojac * 30L), dogadjaj -> stupac.setHeight(konacnaVisina));
            animacijaRasta.getKeyFrames().add(okvirAnimacije);
            brojac = brojac + 1;
        }
        animacijaRasta.play();
    }

    public void pokreniAsinkronoAzuriranjeEkonomije(Runnable logikaAzuriranja, Runnable akcijaNakonAzuriranja) {
        Thread nitEkonomije = new Thread(() -> {
            System.out.println("Pokrenuto azuriranje u zasebnoj niti");
            logikaAzuriranja.run();
            pauzirajNit(500);
            Platform.runLater(akcijaNakonAzuriranja);
        });
        nitEkonomije.setDaemon(true);
        nitEkonomije.start();
    }
}