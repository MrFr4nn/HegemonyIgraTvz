package hr.tvz.java.projekt.logika;

import java.util.HashMap;
import java.util.Map;

public class Glasanje {

    private String nazivZakona;
    private Map<String, Boolean> glasovi;
    private boolean glasanjeZavrseno;
    private boolean zakonPrihvacen;

    public Glasanje(String nazivZakona) {
        this.nazivZakona = nazivZakona;
        this.glasovi = new HashMap<>();
        this.glasanjeZavrseno = false;
        this.zakonPrihvacen = false;
    }

    public void zabrojiGlas(String nazivIgraca, boolean glasZa) {
        glasovi.put(nazivIgraca, glasZa);
    }

    public boolean jeIgracVecGlasao(String nazivIgraca) {
        return glasovi.containsKey(nazivIgraca);
    }

    public void zatvoriGlasanje(int ukupanBrojGlasaca) {
        int brojGlasovaZa = 0;
        int brojGlasovaProtiv = 0;

        for (Map.Entry<String, Boolean> jedanGlas : glasovi.entrySet()) {
            if (jedanGlas.getValue()) {
                brojGlasovaZa = brojGlasovaZa + 1;
            } else {
                brojGlasovaProtiv = brojGlasovaProtiv + 1;
            }
        }

        if (brojGlasovaZa > brojGlasovaProtiv) {
            zakonPrihvacen = true;
        } else {
            zakonPrihvacen = false;
        }
        glasanjeZavrseno = true;
    }

    public String ispisiRezultatGlasanja() {
        String tekst = "";
        tekst = tekst + "Zakon: " + nazivZakona + "\n";

        int brojac = 0;
        for (Map.Entry<String, Boolean> jedanGlas : glasovi.entrySet()) {
            String odluka;
            if (jedanGlas.getValue()) {
                odluka = "ZA";
            } else {
                odluka = "PROTIV";
            }
            tekst = tekst + jedanGlas.getKey() + ": " + odluka + "\n";
            brojac = brojac + 1;
        }

        if (zakonPrihvacen) {
            tekst = tekst + "Ishod: Zakon je PRIHVACEN.";
        } else {
            tekst = tekst + "Ishod: Zakon je ODBIJEN.";
        }
        return tekst;
    }

    public String getNazivZakona() {
        return nazivZakona;
    }

    public boolean isGlasanjeZavrseno() {
        return glasanjeZavrseno;
    }

    public boolean isZakonPrihvacen() {
        return zakonPrihvacen;
    }

    public int dohvatiBrojGlasova() {
        return glasovi.size();
    }
}