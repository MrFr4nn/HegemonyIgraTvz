package hr.tvz.java.projekt.model;

public class KapitalistickaKlasa extends KlasaIgraca {

    private static final long serialVersionUID = 1L;

    private double ukupniKapital;
    private int brojTvornica;
    private double vrijednostDionica;

    public KapitalistickaKlasa(String naziv) {
        super(naziv);
        this.ukupniKapital = 0.0;
        this.brojTvornica = 0;
        this.vrijednostDionica = 0.0;
    }

    @Override
    public void odigrajPotez() {
        zabrojOdigranihPoteza();
        if (brojTvornica > 0) {
            vrijednostDionica = vrijednostDionica + (brojTvornica * 5.0);
        }
    }

    @Override
    public String ispisiStanje() {
        String tekst = "";
        tekst = tekst + "Kapitalisticka klasa: " + naziv + "\n";
        tekst = tekst + "Kapital: " + String.format("%.2f", ukupniKapital) + "\n";
        tekst = tekst + "Tvornice: " + brojTvornica + "\n";
        tekst = tekst + "Dionice: " + String.format("%.2f", vrijednostDionica);
        return tekst;
    }

    @Override
    public int izracunajUkupniRezultat() {
        return (int) (ukupniKapital / 10) + (brojTvornica * 8) + (int) (vrijednostDionica / 10);
    }

    public void izgradiTvornicu(double trosak) {
        brojTvornica = brojTvornica + 1;
        ukupniKapital = ukupniKapital - trosak;
        if (ukupniKapital < 0) {
            ukupniKapital = 0;
        }
    }

    public void prodajTvornicu(double prihod) {
        if (brojTvornica > 0) {
            brojTvornica = brojTvornica - 1;
            ukupniKapital = ukupniKapital + prihod;
        }
    }

    public void ulozUInvesticiju(double iznos) {
        ukupniKapital = ukupniKapital + iznos;
        vrijednostDionica = vrijednostDionica + (iznos * 0.5);
    }

    public void platiPorez(double iznos) {
        ukupniKapital = ukupniKapital - iznos;
        if (ukupniKapital < 0) {
            ukupniKapital = 0;
        }
    }

    public double getUkupniKapital() { return ukupniKapital; }
    public void setUkupniKapital(double ukupniKapital) { this.ukupniKapital = ukupniKapital; }
    public int getBrojTvornica() { return brojTvornica; }
    public double getVrijednostDionica() { return vrijednostDionica; }
}