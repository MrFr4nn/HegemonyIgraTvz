package hr.tvz.java.projekt.model;

public class RadnickaKlasa extends KlasaIgraca {

    private static final long serialVersionUID = 1L;

    private int brojRadnika;
    private int zaposleniRadnici;
    private int kolicinaHrane;
    private int razinaObrazovanja;
    private int standardZivota;
    private boolean jeUStrajku;

    public RadnickaKlasa(String naziv) {
        super(naziv);
        this.brojRadnika = 10;
        this.zaposleniRadnici = 0;
        this.kolicinaHrane = 0;
        this.razinaObrazovanja = 0;
        this.standardZivota = 0;
        this.jeUStrajku = false;
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
        tekst = tekst + "Standard zivota: " + standardZivota + "\n";
        tekst = tekst + "U strajku: " + jeUStrajku;
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
        if (jeUStrajku) {
            return;
        }
        if (zaposleniRadnici > 5) {
            standardZivota = standardZivota + (zaposleniRadnici - 5) * 2;
        }
        if (kolicinaHrane > 0) {
            standardZivota = standardZivota + 3;
        }
        if (razinaObrazovanja > 0) {
            standardZivota = standardZivota + razinaObrazovanja * 2;
        }
        if (standardZivota > 100) {
            standardZivota = 100;
        }
    }

    public void pokreniStrajk() {
        jeUStrajku = true;
    }

    public void prekiniStrajk() {
        jeUStrajku = false;
    }

    public double izracunajStopuNezaposlenosti() {
        if (brojRadnika == 0) {
            return 0.0;
        }
        int nezaposleni = brojRadnika - zaposleniRadnici;
        return (double) nezaposleni / (double) brojRadnika;
    }

    public void potrosiHranu(int kolicina) {
        int privremena = kolicinaHrane - kolicina;
        if (privremena < 0) {
            kolicinaHrane = 0;
        } else {
            kolicinaHrane = privremena;
        }
    }

    public void kupiHranu(int kolicina, int trosak) {
        kolicinaHrane = kolicinaHrane + kolicina;
    }

    public void investirajUObrazovanje(int trosak) {
        razinaObrazovanja = razinaObrazovanja + 1;
    }

    public void zaposliRadnika(int brojNovih) {
        int privremena = zaposleniRadnici + brojNovih;
        if (privremena > brojRadnika) {
            zaposleniRadnici = brojRadnika;
        } else {
            zaposleniRadnici = privremena;
        }
        standardZivota = standardZivota + 5;
    }

    public void otpustiRadnika(int brojOtpustenih) {
        int privremena = zaposleniRadnici - brojOtpustenih;
        if (privremena < 0) {
            zaposleniRadnici = 0;
        } else {
            zaposleniRadnici = privremena;
        }
        standardZivota = standardZivota - 3;
        if (standardZivota < 0) {
            standardZivota = 0;
        }
    }

    public int getBrojRadnika() { return brojRadnika; }
    public void setBrojRadnika(int brojRadnika) { this.brojRadnika = brojRadnika; }
    public int getZaposleniRadnici() { return zaposleniRadnici; }
    public int getKolicinaHrane() { return kolicinaHrane; }
    public void setKolicinaHrane(int kolicinaHrane) { this.kolicinaHrane = kolicinaHrane; }
    public int getRazinaObrazovanja() { return razinaObrazovanja; }
    public int getStandardZivota() { return standardZivota; }
    public void setStandardZivota(int standardZivota) { this.standardZivota = standardZivota; }
    public boolean isJeUStrajku() { return jeUStrajku; }
}