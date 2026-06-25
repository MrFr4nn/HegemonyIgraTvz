package hr.tvz.java.projekt.logika;

import java.util.HashMap;
import java.util.Map;

public class SustavAkcijskihBodova {

    private static final int MAKSIMALNI_AP_PO_RUNDI = 1;

    private int potrosenAp;
    private Map<String, Integer> brojKoristenjaPoAkciji;
    private Map<String, Integer> limitPoAkciji;

    public SustavAkcijskihBodova() {
        this.potrosenAp = 0;
        this.brojKoristenjaPoAkciji = new HashMap<>();
        this.limitPoAkciji = new HashMap<>();
    }

    public void postaviLimitAkcije(String nazivAkcije, int maksimalniBrojKoristenja) {
        limitPoAkciji.put(nazivAkcije, maksimalniBrojKoristenja);
    }

    public boolean jeAkcijaDostupna(String nazivAkcije) {
        if (potrosenAp >= MAKSIMALNI_AP_PO_RUNDI) {
            return false;
        }
        int trenutnoKoristenja = dohvatiBrojKoristenja(nazivAkcije);
        int limit = dohvatiLimit(nazivAkcije);
        return trenutnoKoristenja < limit;
    }

    public boolean iskoristiAkciju(String nazivAkcije) {
        if (!jeAkcijaDostupna(nazivAkcije)) {
            System.out.println("Akcija " + nazivAkcije + " nije dostupna - limit ili AP potrosen.");
            return false;
        }
        int trenutnoKoristenja = dohvatiBrojKoristenja(nazivAkcije);
        brojKoristenjaPoAkciji.put(nazivAkcije, trenutnoKoristenja + 1);
        potrosenAp = potrosenAp + 1;
        return true;
    }

    private int dohvatiBrojKoristenja(String nazivAkcije) {
        if (brojKoristenjaPoAkciji.containsKey(nazivAkcije)) {
            return brojKoristenjaPoAkciji.get(nazivAkcije);
        } else {
            return 0;
        }
    }

    private int dohvatiLimit(String nazivAkcije) {
        if (limitPoAkciji.containsKey(nazivAkcije)) {
            return limitPoAkciji.get(nazivAkcije);
        } else {
            return MAKSIMALNI_AP_PO_RUNDI;
        }
    }

    public void resetirajZaNovuRundu() {
        potrosenAp = 0;
        brojKoristenjaPoAkciji.clear();
    }

    public boolean jePotrosioSveApove() {
        return potrosenAp >= MAKSIMALNI_AP_PO_RUNDI;
    }

    public int getPotrosenAp() {
        return potrosenAp;
    }

    public int getPreostaliAp() {
        return MAKSIMALNI_AP_PO_RUNDI - potrosenAp;
    }

    public int dohvatiPreostaliBrojKoristenja(String nazivAkcije) {
        int limit = dohvatiLimit(nazivAkcije);
        int koristeno = dohvatiBrojKoristenja(nazivAkcije);
        int preostalo = limit - koristeno;
        if (preostalo < 0) {
            return 0;
        }
        return preostalo;
    }
}