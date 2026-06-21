package hr.tvz.java.projekt.model;

public class SrednjaKlasa extends KlasaIgraca {

    private static final long serialVersionUID = 1L;

    private int brojMalihPoduzeca;
    private double ukupniPrihod;
    private int razinaObrazovanja;
    private int standardZivota;
    private double ustedjeniKapital;

    public SrednjaKlasa(String naziv) {
        super(naziv);
        this.brojMalihPoduzeca = 3;
        this.ukupniPrihod = 40.0;
        this.razinaObrazovanja = 2;
        this.standardZivota = 60;
        this.ustedjeniKapital = 20.0;
    }

    @Override
    public void odigrajPotez() {
        zabrojOdigranihPoteza();
        azurirajStandardZivota();
    }

    @Override
    public String ispisiStanje() {
        String tekst = "";
        tekst = tekst + "Srednja klasa: " + naziv + "\n";
        tekst = tekst + "Broj malih poduzeca: " + brojMalihPoduzeca + "\n";
        tekst = tekst + "Ukupni prihod: " + ukupniPrihod + "\n";
        tekst = tekst + "Razina obrazovanja: " + razinaObrazovanja + "\n";
        tekst = tekst + "Standard zivota: " + standardZivota + "\n";
        tekst = tekst + "Ustedjeni kapital: " + ustedjeniKapital;
        return tekst;
    }

    @Override
    public int izracunajUkupniRezultat() {
        int privremena = standardZivota + (brojMalihPoduzeca * 4) + (int) (ustedjeniKapital / 2);
        return privremena;
    }

    public boolean izracunajPolitickiRezultat(Vlada vlada) {
        double porez = vlada.getStopaPoreza();
        return porez >= 0.15 && porez <= 0.35;
    }

    public void azurirajStandardZivota() {
        int privremena = standardZivota;
        if (ukupniPrihod > 50) {
            privremena = privremena + 5;
        } else if (ukupniPrihod < 20) {
            privremena = privremena - 7;
        }

        if (razinaObrazovanja > 3) {
            privremena = privremena + 4;
        }

        if (privremena > 100) {
            privremena = 100;
        }
        if (privremena < 0) {
            privremena = 0;
        }
        standardZivota = privremena;
    }

    public void otvoriNovoPoduzece(double trosakOsnivanja) {
        if (ustedjeniKapital >= trosakOsnivanja) {
            ustedjeniKapital = ustedjeniKapital - trosakOsnivanja;
            brojMalihPoduzeca = brojMalihPoduzeca + 1;
        } else {
            System.out.println("Nedovoljno kapitala za otvaranje novog poduzeca.");
        }
    }

    public void zatvoriPoduzece() {
        if (brojMalihPoduzeca > 0) {
            brojMalihPoduzeca = brojMalihPoduzeca - 1;
        } else {
            System.out.println("Nema poduzeca koje je moguce zatvoriti.");
        }
    }

    public void investirajUObrazovanje(int trosak) {
        if (ustedjeniKapital >= trosak) {
            ustedjeniKapital = ustedjeniKapital - trosak;
            razinaObrazovanja = razinaObrazovanja + 1;
        } else {
            System.out.println("Nedovoljno sredstava za povecanje razine obrazovanja.");
        }
    }

    public void ostvariPrihod(double iznos) {
        ukupniPrihod = ukupniPrihod + iznos;
        ustedjeniKapital = ustedjeniKapital + (iznos * 0.3);
    }

    public void platiPorez(double iznos) {
        double privremena = ustedjeniKapital - iznos;
        if (privremena < 0) {
            ustedjeniKapital = 0;
        } else {
            ustedjeniKapital = privremena;
        }
    }

    public int getBrojMalihPoduzeca() {
        return brojMalihPoduzeca;
    }

    public double getUkupniPrihod() {
        return ukupniPrihod;
    }

    public void setUkupniPrihod(double ukupniPrihod) {
        this.ukupniPrihod = ukupniPrihod;
    }

    public int getRazinaObrazovanja() {
        return razinaObrazovanja;
    }

    public int getStandardZivota() {
        return standardZivota;
    }

    public double getUstedjeniKapital() {
        return ustedjeniKapital;
    }

    public void setUstedjeniKapital(double ustedjeniKapital) {
        this.ustedjeniKapital = ustedjeniKapital;
    }
}