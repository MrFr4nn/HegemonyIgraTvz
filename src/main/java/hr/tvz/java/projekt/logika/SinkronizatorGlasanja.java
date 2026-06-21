package hr.tvz.java.projekt.logika;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class SinkronizatorGlasanja {

    private CyclicBarrier prepreka;

    public SinkronizatorGlasanja(int brojIgraca) {
        this.prepreka = new CyclicBarrier(brojIgraca);
    }

    public void simulirajGlasanjeUNiti(List<Runnable> akcijeIgraca, List<String> naziviIgraca) {
        int brojac = 0;
        while (brojac < akcijeIgraca.size()) {
            Runnable akcijaIgraca = akcijeIgraca.get(brojac);
            String oznakaIgraca = naziviIgraca.get(brojac);
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
            System.out.println(oznakaIgraca + " ceka na ostale igrace da zavrse glasanje.");
            prepreka.await();
            System.out.println(oznakaIgraca + " je prosao prepreku, glasanje sinkronizirano.");
        } catch (InterruptedException greska) {
            System.out.println("Nit je prekinuta tijekom cekanja: " + greska.getMessage());
            Thread.currentThread().interrupt();
        } catch (BrokenBarrierException greska) {
            System.out.println("Prepreka je slomljena tijekom glasanja: " + greska.getMessage());
        }
    }
}