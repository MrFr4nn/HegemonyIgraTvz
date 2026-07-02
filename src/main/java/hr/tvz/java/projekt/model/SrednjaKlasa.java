package hr.tvz.java.projekt.model;

public class SrednjaKlasa extends KlasaIgraca {

    private static final long serialVersionUID = 1L;

    private int brojMalihPoduzeca;
    private double ustedjeniKapital;
    private int standardZivota;

    public SrednjaKlasa(String naziv) {
        super(naziv);
        this.brojMalihPoduzeca = 0;
        this.ustedjeniKapital = 0.0;
        this.standardZivota = 0;
    }

    @Override
    public void odigrajPotez() {
        zabrojOdigranihPoteza();
        azurirajStandard();
    }

    @Override
    public String ispisiStanje() {
        String tekst = "";
        tekst = tekst + "Srednja klasa: " + naziv + "\n";
        tekst = tekst + "Poduzeca: " + brojMalihPoduzeca + "\n";
        tekst = tekst + "Kapital: " + String.format("%.2f", ustedjeniKapital) + "\n";
        tekst = tekst + "Standard zivota: " + standardZivota;
        return tekst;
    }

    @Override
    public int izracunajUkupniRezultat() {
        return standardZivota + (int) (ustedjeniKapital / 10) + (brojMalihPoduzeca * 5);
    }

    private void azurirajStandard() {
        if (brojMalihPoduzeca > 0) {
            standardZivota = standardZivota + brojMalihPoduzeca * 3;
        }
        if (ustedjeniKapital > 20) {
            standardZivota = standardZivota + 2;
        }
        if (standardZivota > 100) {
            standardZivota = 100;
        }
    }

    public void otvoriNovoPoduzece(double trosak) {
        if (ustedjeniKapital >= trosak) {
            ustedjeniKapital = ustedjeniKapital - trosak;
            brojMalihPoduzeca = brojMalihPoduzeca + 1;
            standardZivota = standardZivota + 5;
        } else {
            brojMalihPoduzeca = brojMalihPoduzeca + 1;
            standardZivota = standardZivota + 2;
        }
    }

    public void zatvoriPoduzece() {
        if (brojMalihPoduzeca > 0) {
            brojMalihPoduzeca = brojMalihPoduzeca - 1;
            standardZivota = standardZivota - 3;
            if (standardZivota < 0) {
                standardZivota = 0;
            }
        }
    }

    public void ostvariPrihod(double iznos) {
        ustedjeniKapital = ustedjeniKapital + iznos;
        standardZivota = standardZivota + 4;
        if (standardZivota > 100) {
            standardZivota = 100;
        }
    }

    public void investirajUObrazovanje(int trosak) {
        standardZivota = standardZivota + 3;
        if (standardZivota > 100) {
            standardZivota = 100;
        }
    }

    public int getBrojMalihPoduzeca() { return brojMalihPoduzeca; }
    public double getUstedjeniKapital() { return ustedjeniKapital; }
    public void setUstedjeniKapital(double ustedjeniKapital) { this.ustedjeniKapital = ustedjeniKapital; }
    public int getStandardZivota() { return standardZivota; }
    public void setStandardZivota(int standardZivota) { this.standardZivota = standardZivota; }
}