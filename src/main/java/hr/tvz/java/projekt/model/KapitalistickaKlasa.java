package hr.tvz.java.projekt.model;

public class KapitalistickaKlasa extends KlasaIgraca {

    private static final long serialVersionUID = 1L;

    private double ukupniKapital;
    private int brojTvornica;
    private double profitnaStopa;
    private int brojUlozenihInvesticija;
    private double vrijednostDionica;

    public KapitalistickaKlasa(String naziv) {
        super(naziv);
        this.ukupniKapital = 200.0;
        this.brojTvornica = 4;
        this.profitnaStopa = 0.15;
        this.brojUlozenihInvesticija = 1;
        this.vrijednostDionica = 100.0;
    }

    @Override
    public void odigrajPotez() {
        zabrojOdigranihPoteza();
        azurirajVrijednostDionica();
    }

    @Override
    public String ispisiStanje() {
        String tekst = "";
        tekst = tekst + "Kapitalisticka klasa: " + naziv + "\n";
        tekst = tekst + "Ukupni kapital: " + ukupniKapital + "\n";
        tekst = tekst + "Broj tvornica: " + brojTvornica + "\n";
        tekst = tekst + "Profitna stopa: " + profitnaStopa + "\n";
        tekst = tekst + "Broj ulozenih investicija: " + brojUlozenihInvesticija + "\n";
        tekst = tekst + "Vrijednost dionica: " + vrijednostDionica;
        return tekst;
    }

    @Override
    public int izracunajUkupniRezultat() {
        int privremena = (int) (ukupniKapital / 2) + (brojTvornica * 6) + (int) (vrijednostDionica / 5);
        return privremena;
    }

    public boolean izracunajPolitickiRezultat(Vlada vlada) {
        return vlada.getStopaPoreza() <= 0.15;
    }

    public void azurirajVrijednostDionica() {
        double privremena = vrijednostDionica;
        if (profitnaStopa > 0.2) {
            privremena = privremena + 15;
        } else if (profitnaStopa < 0.05) {
            privremena = privremena - 10;
        }

        if (brojTvornica > 5) {
            privremena = privremena + 5;
        }

        if (privremena < 0) {
            privremena = 0;
        }
        vrijednostDionica = privremena;
    }

    public void izgradiTvornicu(double trosakIzgradnje) {
        if (ukupniKapital >= trosakIzgradnje) {
            ukupniKapital = ukupniKapital - trosakIzgradnje;
            brojTvornica = brojTvornica + 1;
        } else {
            System.out.println("Nedovoljno kapitala za izgradnju nove tvornice.");
        }
    }

    public void prodajTvornicu(double otkupnaVrijednost) {
        if (brojTvornica > 0) {
            brojTvornica = brojTvornica - 1;
            ukupniKapital = ukupniKapital + otkupnaVrijednost;
        } else {
            System.out.println("Nema tvornica koje je moguce prodati.");
        }
    }

    public void ulozUInvesticiju(double iznosUlaganja) {
        if (ukupniKapital >= iznosUlaganja) {
            ukupniKapital = ukupniKapital - iznosUlaganja;
            brojUlozenihInvesticija = brojUlozenihInvesticija + 1;
            profitnaStopa = profitnaStopa + 0.02;
        } else {
            System.out.println("Nedovoljno kapitala za ulaganje u investiciju.");
        }
    }

    public void platiPorez(double iznos) {
        double privremena = ukupniKapital - iznos;
        if (privremena < 0) {
            ukupniKapital = 0;
        } else {
            ukupniKapital = privremena;
        }
    }

    public double getUkupniKapital() {
        return ukupniKapital;
    }

    public void setUkupniKapital(double ukupniKapital) {
        this.ukupniKapital = ukupniKapital;
    }

    public int getBrojTvornica() {
        return brojTvornica;
    }

    public double getProfitnaStopa() {
        return profitnaStopa;
    }

    public int getBrojUlozenihInvesticija() {
        return brojUlozenihInvesticija;
    }

    public double getVrijednostDionica() {
        return vrijednostDionica;
    }
}