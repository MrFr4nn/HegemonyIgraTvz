package hr.tvz.java.projekt.logika;

import hr.tvz.java.projekt.model.KlasaIgraca;

import java.util.List;

public class RedoslijedPoteza {

    private List<KlasaIgraca> listaIgraca;
    private int pozicijaNaPotezu;

    public RedoslijedPoteza(List<KlasaIgraca> listaIgraca) {
        this.listaIgraca = listaIgraca;
        this.pozicijaNaPotezu = 0;
        postaviIgracaNaPotez();
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