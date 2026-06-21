package hr.tvz.java.projekt.logika;

import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.Vlada;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class HegemonyEngine {

    public static final String FAZA_PLANIRANJE = "PLANIRANJE";
    public static final String FAZA_AKCIJA = "AKCIJA";
    public static final String FAZA_GLASANJE = "GLASANJE";
    public static final String FAZA_KRAJ_RUNDE = "KRAJ_RUNDE";

    private RadnickaKlasa radnickaKlasa;
    private Vlada vlada;
    private String trenutnaFaza;
    private int brojRunde;
    private boolean naPotezuRadnickaKlasa;
    private CyclicBarrier prepreka;
    private boolean igraZavrsena;
    private static final int MAKSIMALNI_BROJ_RUNDI = 10;

    public HegemonyEngine(RadnickaKlasa radnickaKlasa, Vlada vlada) {
        this.radnickaKlasa = radnickaKlasa;
        this.vlada = vlada;
        this.trenutnaFaza = FAZA_PLANIRANJE;
        this.brojRunde = 1;
        this.naPotezuRadnickaKlasa = true;
        this.igraZavrsena = false;
        this.prepreka = new CyclicBarrier(2);
        radnickaKlasa.setNaPotezu(true);
        vlada.setNaPotezu(false);
    }

    public void pokreniNovuRundu() {
        brojRunde = brojRunde + 1;
        trenutnaFaza = FAZA_PLANIRANJE;
        naPotezuRadnickaKlasa = true;
        radnickaKlasa.setNaPotezu(true);
        vlada.setNaPotezu(false);
        if (brojRunde > MAKSIMALNI_BROJ_RUNDI) {
            igraZavrsena = true;
        }
    }

    public void prebaciNaSljedecuFazu() {
        if (trenutnaFaza.equals(FAZA_PLANIRANJE)) {
            trenutnaFaza = FAZA_AKCIJA;
        } else if (trenutnaFaza.equals(FAZA_AKCIJA)) {
            trenutnaFaza = FAZA_GLASANJE;
        } else if (trenutnaFaza.equals(FAZA_GLASANJE)) {
            trenutnaFaza = FAZA_KRAJ_RUNDE;
            obradiKrajRunde();
        } else {
            pokreniNovuRundu();
        }
    }

    public void prebaciPotez() {
        if (naPotezuRadnickaKlasa) {
            naPotezuRadnickaKlasa = false;
            radnickaKlasa.setNaPotezu(false);
            vlada.setNaPotezu(true);
        } else {
            naPotezuRadnickaKlasa = true;
            radnickaKlasa.setNaPotezu(true);
            vlada.setNaPotezu(false);
        }
    }

    public void obradiKrajRunde() {
        radnickaKlasa.odigrajPotez();
        vlada.odigrajPotez();
        int privremenaOcjenaRadnika = radnickaKlasa.izracunajUkupniRezultat();
        int privremenaOcjenaVlade = vlada.izracunajUkupniRezultat();
        if (privremenaOcjenaRadnika > privremenaOcjenaVlade) {
            radnickaKlasa.povecajBodove(1);
        } else if (privremenaOcjenaVlade > privremenaOcjenaRadnika) {
            vlada.povecajBodove(1);
        } else {
            System.out.println("Runda " + brojRunde + " zavrsena izjednaceno.");
        }
    }

    public void simulirajGlasanjeUNiti(Runnable akcijaRadnickaKlasa, Runnable akcijaVlada) {
        Thread nitRadnickaKlasa = new Thread(() -> {
            akcijaRadnickaKlasa.run();
            cekajNaPrepreci("Radnicka klasa");
        });
        Thread nitVlada = new Thread(() -> {
            akcijaVlada.run();
            cekajNaPrepreci("Vlada");
        });
        nitRadnickaKlasa.start();
        nitVlada.start();
    }

    private void cekajNaPrepreci(String oznakaIgraca) {
        try {
            System.out.println(oznakaIgraca + " ceka na drugog igraca da zavrsi glasanje.");
            prepreka.await();
            System.out.println(oznakaIgraca + " je prosao prepreku, glasanje sinkronizirano.");
        } catch (InterruptedException greska) {
            System.out.println("Nit je prekinuta tijekom cekanja: " + greska.getMessage());
            Thread.currentThread().interrupt();
        } catch (BrokenBarrierException greska) {
            System.out.println("Prepreka je slomljena tijekom glasanja: " + greska.getMessage());
        }
    }

    public boolean provjeriPobjedu() {
        if (radnickaKlasa.getBodoviPobjede() >= 5) {
            return true;
        }
        if (vlada.getBodoviPobjede() >= 5) {
            return true;
        }
        return igraZavrsena;
    }

    public String dohvatiPobjednika() {
        if (radnickaKlasa.getBodoviPobjede() > vlada.getBodoviPobjede()) {
            return radnickaKlasa.getNaziv();
        } else if (vlada.getBodoviPobjede() > radnickaKlasa.getBodoviPobjede()) {
            return vlada.getNaziv();
        } else {
            return "Nerijeseno";
        }
    }

    public RadnickaKlasa getRadnickaKlasa() {
        return radnickaKlasa;
    }

    public Vlada getVlada() {
        return vlada;
    }

    public String getTrenutnaFaza() {
        return trenutnaFaza;
    }

    public int getBrojRunde() {
        return brojRunde;
    }

    public boolean isNaPotezuRadnickaKlasa() {
        return naPotezuRadnickaKlasa;
    }

    public boolean isIgraZavrsena() {
        return igraZavrsena;
    }
}