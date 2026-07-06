package hr.tvz.java.projekt.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.logging.Logger;

public class UpraviteljAnimacija {

    private static final Logger LOG = Logger.getLogger(UpraviteljAnimacija.class.getName());

    public void pokreniAnimacijuDonosenjaZakona(Label oznakaPoruke, String tekstZakona) {
        Thread nitAnimacije = new Thread(() -> {
            for (int brojac = 0; brojac < 3; brojac++) {
                String konacniTekst = "Donosenje zakona u tijeku..." + napraviTockice(brojac);
                Platform.runLater(() -> oznakaPoruke.setText(konacniTekst));
                pauzirajNit(300);
            }
            Platform.runLater(() -> oznakaPoruke.setText("Zakon donesen: " + tekstZakona));
        });
        nitAnimacije.setDaemon(true);
        nitAnimacije.start();
    }

    private String napraviTockice(int brojac) {
        StringBuilder rezultat = new StringBuilder();
        for (int privremena = 0; privremena <= brojac; privremena++) {
            rezultat.append(".");
        }
        return rezultat.toString();
    }

    private void pauzirajNit(long milisekunde) {
        try {
            Thread.sleep(milisekunde);
        } catch (InterruptedException greska) {
            LOG.warning("Animacijska nit je prekinuta: " + greska.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void animirajPromjenuStupca(Rectangle stupac, double novaVisina) {
        double staraVisina = stupac.getHeight();
        Timeline animacijaRasta = new Timeline();
        // Definiranjem koraka kao double, rješavamo se potrebe za castanjem pri dijeljenju
        double brojKoraka = 10.0;
        double razlika = novaVisina - staraVisina;

        for (int brojac = 0; brojac <= brojKoraka; brojac++) {
            double udio = brojac / brojKoraka;
            final double trenutnaVisina = staraVisina + (razlika * udio);

            // Micanjem (double) casta rješavamo trenutnu Sonar grešku, long automatski prelazi u double
            long trajanje = brojac * 30L;
            KeyFrame okvirAnimacije = new KeyFrame(Duration.millis(trajanje),
                    dogadjaj -> stupac.setHeight(trenutnaVisina));
            animacijaRasta.getKeyFrames().add(okvirAnimacije);
        }
        animacijaRasta.play();
    }

    public void pokreniAsinkronoAzuriranjeEkonomije(Runnable logikaAzuriranja, Runnable akcijaNakonAzuriranja) {
        Thread nitEkonomije = new Thread(() -> {
            LOG.info("Pokrenuto azuriranje u zasebnoj niti");
            logikaAzuriranja.run();
            pauzirajNit(500);
            Platform.runLater(akcijaNakonAzuriranja);
        });
        nitEkonomije.setDaemon(true);
        nitEkonomije.start();
    }
}