package hr.tvz.java.projekt.logika;

import hr.tvz.java.projekt.model.KlasaIgraca;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedoslijedPoteza {

    private List<KlasaIgraca> listaIgraca;
    private int pozicijaNaPotezu;
    private Map<KlasaIgraca, SustavAkcijskihBodova> apSustaviPoIgracu;

    public RedoslijedPoteza(List<KlasaIgraca> listaIgraca) {
        this.listaIgraca = listaIgraca;
        this.pozicijaNaPotezu = 0;
        this.apSustaviPoIgracu = new HashMap<>();
        napuniApSustave();
        postaviIgracaNaPotez();
    }

    private void napuniApSustave() {
        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            apSustaviPoIgracu.put(listaIgraca.get(brojac), new SustavAkcijskihBodova());
            brojac = brojac + 1;
        }
    }

    public void postaviIgracaNaPotez() {
        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            KlasaIgraca trenutniIgrac = listaIgraca.get(brojac);
            trenutniIgrac.setNaPotezu(brojac == pozicijaNaPotezu);
            brojac = brojac + 1;
        }
    }

    public void resetirajNaPocetak() {
        pozicijaNaPotezu = 0;
        postaviIgracaNaPotez();
        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            apSustaviPoIgracu.get(listaIgraca.get(brojac)).resetirajZaNovuRundu();
            brojac = brojac + 1;
        }
    }

    public void prebaciNaSljedecegIgraca() {
        int privremenaPozicija = pozicijaNaPotezu + 1;
        if (privremenaPozicija >= listaIgraca.size()) {
            privremenaPozicija = 0;
        }
        listaIgraca.get(pozicijaNaPotezu).setNaPotezu(false);
        pozicijaNaPotezu = privremenaPozicija;
        listaIgraca.get(pozicijaNaPotezu).setNaPotezu(true);
    }

    public boolean jeIgracNaPotezuOdigraoSveApove() {
        KlasaIgraca igracNaPotezu = dohvatiIgracaNaPotezu();
        return apSustaviPoIgracu.get(igracNaPotezu).jePotrosioSveApove();
    }

    public boolean jesuLiSviIgraciOdigraliSveApove() {
        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            if (!apSustaviPoIgracu.get(listaIgraca.get(brojac)).jePotrosioSveApove()) {
                return false;
            }
            brojac = brojac + 1;
        }
        return true;
    }

    public SustavAkcijskihBodova dohvatiApSustavTrenutnogIgraca() {
        return apSustaviPoIgracu.get(dohvatiIgracaNaPotezu());
    }

    public KlasaIgraca dohvatiIgracaNaPotezu() {
        return listaIgraca.get(pozicijaNaPotezu);
    }

    public int getPozicijaNaPotezu() {
        return pozicijaNaPotezu;
    }

    public String dohvatiPobjednika() {
        int brojac = 0;
        int najboljiBodovi = -1;
        String nazivPobjednika = "Nerijeseno";
        boolean izjednaceno = false;

        while (brojac < listaIgraca.size()) {
            KlasaIgraca trenutniIgrac = listaIgraca.get(brojac);
            if (trenutniIgrac.getBodoviPobjede() > najboljiBodovi) {
                najboljiBodovi = trenutniIgrac.getBodoviPobjede();
                nazivPobjednika = trenutniIgrac.getNaziv();
                izjednaceno = false;
            } else if (trenutniIgrac.getBodoviPobjede() == najboljiBodovi) {
                izjednaceno = true;
            }
            brojac = brojac + 1;
        }

        if (izjednaceno) {
            return "Nerijeseno";
        }
        return nazivPobjednika;
    }
}