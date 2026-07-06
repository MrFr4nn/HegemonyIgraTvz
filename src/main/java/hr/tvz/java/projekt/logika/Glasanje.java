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

    public void zatvoriGlasanje() {
        int brojGlasovaZa = 0;
        int brojGlasovaProtiv = 0;
        for (Map.Entry<String, Boolean> jedanGlas : glasovi.entrySet()) {
            if (Boolean.TRUE.equals(jedanGlas.getValue())) {
                brojGlasovaZa = brojGlasovaZa + 1;
            } else {
                brojGlasovaProtiv = brojGlasovaProtiv + 1;
            }
        }
        zakonPrihvacen = brojGlasovaZa > brojGlasovaProtiv;
        glasanjeZavrseno = true;
    }

    public String ispisiRezultatGlasanja() {
        StringBuilder tekst = new StringBuilder();
        tekst.append("Zakon: ").append(nazivZakona).append("\n");
        for (Map.Entry<String, Boolean> jedanGlas : glasovi.entrySet()) {
            String odluka = Boolean.TRUE.equals(jedanGlas.getValue()) ? "ZA" : "PROTIV";
            tekst.append(jedanGlas.getKey()).append(": ").append(odluka).append("\n");
        }
        if (zakonPrihvacen) {
            tekst.append("Ishod: Zakon je PRIHVACEN.");
        } else {
            tekst.append("Ishod: Zakon je ODBIJEN.");
        }
        return tekst.toString();
    }

    public String getNazivZakona() { return nazivZakona; }
    public boolean isGlasanjeZavrseno() { return glasanjeZavrseno; }
    public boolean isZakonPrihvacen() { return zakonPrihvacen; }
    public int dohvatiBrojGlasova() { return glasovi.size(); }
}