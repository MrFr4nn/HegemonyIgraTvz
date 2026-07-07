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
    }

    @Override
    public String ispisiStanje() {
        StringBuilder tekstBuilder = new StringBuilder();
        tekstBuilder.append("Radnicka klasa: ").append(naziv).append("\n");
        tekstBuilder.append("Ukupno radnika: ").append(brojRadnika).append("\n");
        tekstBuilder.append("Zaposleni radnici: ").append(zaposleniRadnici).append("\n");
        tekstBuilder.append("Kolicina hrane: ").append(kolicinaHrane).append("\n");
        tekstBuilder.append("Razina obrazovanja: ").append(razinaObrazovanja).append("\n");
        tekstBuilder.append("Standard zivota: ").append(standardZivota).append("\n");
        tekstBuilder.append("U strajku: ").append(jeUStrajku);
        return tekstBuilder.toString();
    }

    @Override
    public int izracunajUkupniRezultat() {
        return standardZivota + (zaposleniRadnici * 2) + (razinaObrazovanja * 5);
    }

    public void pokreniStrajk() {
        jeUStrajku = true;
    }

    public void prekiniStrajk() {
        jeUStrajku = false;
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
        if (kolicina > 0) {
            kolicinaHrane = kolicinaHrane + kolicina;
        }
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
        if (standardZivota > 100) {
            standardZivota = 100;
        }
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