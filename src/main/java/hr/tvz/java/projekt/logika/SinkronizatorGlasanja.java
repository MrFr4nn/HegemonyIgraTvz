package hr.tvz.java.projekt.logika;

import hr.tvz.java.projekt.model.KlasaIgraca;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SinkronizatorGlasanja {

    // Uveden službeni loger umjesto System.err (rješava java:S106)
    private static final Logger LOG = Logger.getLogger(SinkronizatorGlasanja.class.getName());

    private CyclicBarrier prepreka;

    public SinkronizatorGlasanja(int brojIgraca) {
        this.prepreka = new CyclicBarrier(brojIgraca);
    }

    public void simulirajGlasanjeUNiti(List<Runnable> akcijeIgraca, List<KlasaIgraca> listaIgraca) {
        int brojac = 0;
        while (brojac < akcijeIgraca.size()) {
            Runnable akcijaIgraca = akcijeIgraca.get(brojac);
            String oznakaIgraca = listaIgraca.get(brojac).getNaziv();
            Thread nitIgraca = new Thread(() -> {
                akcijaIgraca.run();
                cekajNaPrepreci(oznakaIgraca);
            });
            nitIgraca.start();
            brojac = brojac + 1;
        }
    }

    private void cekajNaPrepreci(String oznakaIgraca) {
        try {
            // Zamijenjeno s LOG.info i placeholderom
            LOG.log(Level.INFO, "{0} ceka na ostale igrace da zavrse glasanje.", oznakaIgraca);
            prepreka.await();
            LOG.log(Level.INFO, "{0} je prosao prepreku, glasanje sinkronizirano.", oznakaIgraca);
        } catch (InterruptedException greska) {
            // Zamijenjeno s LOG.log(Level.SEVERE...)
            LOG.log(Level.SEVERE, "Nit je prekinuta tijekom cekanja: {0}", greska.getMessage());
            Thread.currentThread().interrupt();
        } catch (BrokenBarrierException greska) {
            LOG.log(Level.SEVERE, "Prepreka je slomljena tijekom glasanja: {0}", greska.getMessage());
        }
    }
}