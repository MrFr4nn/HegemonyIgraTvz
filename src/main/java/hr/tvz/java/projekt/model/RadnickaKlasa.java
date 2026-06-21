package hr.tvz.java.projekt.model;

public class RadnickaKlasa extends KlasaIgraca {

    private static final long serialVersionUID = 1L;

    private int brojRadnika;
    private int zaposleniRadnici;
    private int kolicinaHrane;
    private int razinaObrazovanja;
    private int standardZivota;

    public RadnickaKlasa(String naziv) {
        super(naziv);
        this.brojRadnika = 10;
        this.zaposleniRadnici = 6;
        this.kolicinaHrane = 20;
        this.razinaObrazovanja = 1;
        this.standardZivota = 50;
    }

    @Override
    public void odigrajPotez() {
        zabrojOdigranihPoteza();
        if (zaposleniRadnici > brojRadnika) {
            zaposleniRadnici = brojRadnika;
        }
        azurirajStandardZivota();
    }

    @Override
    public String ispisiStanje() {
        String tekst = "";
        tekst = tekst + "Radnicka klasa: " + naziv + "\n";
        tekst = tekst + "Ukupno radnika: " + brojRadnika + "\n";
        tekst = tekst + "Zaposleni radnici: " + zaposleniRadnici + "\n";
        tekst = tekst + "Kolicina hrane: " + kolicinaHrane + "\n";
        tekst = tekst + "Razina obrazovanja: " + razinaObrazovanja + "\n";
        tekst = tekst + "Standard zivota: " + standardZivota;
        return tekst;
    }

    @Override
    public int izracunajUkupniRezultat() {
        int privremena = standardZivota + (zaposleniRadnici * 2) + (razinaObrazovanja * 5);
        return privremena;
    }

    public boolean izracunajPolitickiRezultat(Vlada vlada) {
        boolean placaDobra = vlada.getMinimalnaPlaca() >= 6.0;
        boolean porezDobar = vlada.getStopaPoreza() <= 0.25;
        return placaDobra && porezDobar;
    }

    public void azurirajStandardZivota() {
        int privremena = standardZivota;
        if (kolicinaHrane > 15) {
            privremena = privremena + 5;
        } else if (kolicinaHrane < 5) {
            privremena = privremena - 10;
        }

        if (razinaObrazovanja > 2) {
            privremena = privremena + 3;
        }

        double stopaNezaposlenosti = izracunajStopuNezaposlenosti();
        if (stopaNezaposlenosti > 0.4) {
            privremena = privremena - 8;
        }

        if (privremena > 100) {
            privremena = 100;
        }
        if (privremena < 0) {
            privremena = 0;
        }
        standardZivota = privremena;
    }

    public double izracunajStopuNezaposlenosti() {
        if (brojRadnika == 0) {
            return 0.0;
        }
        int nezaposleni = brojRadnika - zaposleniRadnici;
        double stopa = (double) nezaposleni / (double) brojRadnika;
        return stopa;
    }

    public void potrosiHranu(int kolicina) {
        int privremena = kolicinaHrane - kolicina;
        if (privremena < 0) {
            kolicinaHrane = 0;
            System.out.println("Upozorenje: Nema dovoljno hrane, standard zivota ce pasti.");
        } else {
            kolicinaHrane = privremena;
        }
    }

    public void kupiHranu(int kolicina, int trosak) {
        kolicinaHrane = kolicinaHrane + kolicina;
        System.out.println("Radnicka klasa je kupila hranu za iznos: " + trosak);
    }

    public void investirajUObrazovanje(int trosak) {
        if (trosak >= 10) {
            razinaObrazovanja = razinaObrazovanja + 1;
        } else {
            System.out.println("Nedovoljno sredstava za povecanje razine obrazovanja.");
        }
    }

    public void zaposliRadnika(int brojNovih) {
        int privremena = zaposleniRadnici + brojNovih;
        if (privremena > brojRadnika) {
            zaposleniRadnici = brojRadnika;
        } else {
            zaposleniRadnici = privremena;
        }
    }

    public void otpustiRadnika(int brojOtpustenih) {
        int privremena = zaposleniRadnici - brojOtpustenih;
        if (privremena < 0) {
            zaposleniRadnici = 0;
        } else {
            zaposleniRadnici = privremena;
        }
    }

    public int getBrojRadnika() {
        return brojRadnika;
    }

    public void setBrojRadnika(int brojRadnika) {
        this.brojRadnika = brojRadnika;
    }

    public int getZaposleniRadnici() {
        return zaposleniRadnici;
    }

    public int getKolicinaHrane() {
        return kolicinaHrane;
    }

    public void setKolicinaHrane(int kolicinaHrane) {
        this.kolicinaHrane = kolicinaHrane;
    }

    public int getRazinaObrazovanja() {
        return razinaObrazovanja;
    }

    public int getStandardZivota() {
        return standardZivota;
    }

    public void setStandardZivota(int standardZivota) {
        this.standardZivota = standardZivota;
    }
}