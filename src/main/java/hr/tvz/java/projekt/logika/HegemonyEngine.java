package hr.tvz.java.projekt.logika;

import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.Vlada;

import java.util.ArrayList;
import java.util.List;

public class HegemonyEngine {

    public static final String FAZA_PRIPREMA = "PRIPREMA";
    public static final String FAZA_AKCIJA = "AKCIJA";
    public static final String FAZA_GLASANJE = "GLASANJE";
    public static final String FAZA_KRAJ_RUNDE = "KRAJ_RUNDE";

    private List<KlasaIgraca> listaIgraca;
    private Vlada vlada;
    private String trenutnaFaza;
    private int brojRunde;
    private RedoslijedPoteza redoslijedPoteza;
    private SinkronizatorGlasanja sinkronizatorGlasanja;
    private boolean igraZavrsena;
    private Glasanje trenutnoGlasanje;
    private static final int MAKSIMALNI_BROJ_RUNDI = 10;
    private static final int BODOVI_ZA_POBJEDU = 5;

    public HegemonyEngine(List<KlasaIgraca> listaIgraca) {
        this.listaIgraca = listaIgraca;
        this.trenutnaFaza = FAZA_PRIPREMA;
        this.brojRunde = 1;
        this.igraZavrsena = false;
        this.redoslijedPoteza = new RedoslijedPoteza(listaIgraca);
        this.sinkronizatorGlasanja = new SinkronizatorGlasanja(listaIgraca.size());
        this.vlada = pronadjiVladu();
    }

    private Vlada pronadjiVladu() {
        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            KlasaIgraca trenutniIgrac = listaIgraca.get(brojac);
            if (trenutniIgrac instanceof Vlada) {
                return (Vlada) trenutniIgrac;
            }
            brojac = brojac + 1;
        }
        return null;
    }

    public void pokreniNovuRundu() {
        brojRunde = brojRunde + 1;
        trenutnaFaza = FAZA_PRIPREMA;
        redoslijedPoteza.resetirajNaPocetak();
        if (brojRunde > MAKSIMALNI_BROJ_RUNDI) {
            igraZavrsena = true;
        }
    }

    public void prebaciNaSljedecuFazu() {
        if (trenutnaFaza.equals(FAZA_PRIPREMA)) {
            trenutnaFaza = FAZA_AKCIJA;
        } else if (trenutnaFaza.equals(FAZA_AKCIJA)) {
            trenutnaFaza = FAZA_GLASANJE;
        } else if (trenutnaFaza.equals(FAZA_GLASANJE)) {
            trenutnaFaza = FAZA_KRAJ_RUNDE;
            obradiKrajRunde();
        } else {
            pokreniNovuRundu();
        }
    }

    public void prebaciPotez() {
        redoslijedPoteza.prebaciNaSljedecegIgraca();
    }

    public void obradiKrajRunde() {
        int brojac = 0;
        int najboljiRezultat = Integer.MIN_VALUE;
        KlasaIgraca najboljiIgrac = null;

        while (brojac < listaIgraca.size()) {
            KlasaIgraca trenutniIgrac = listaIgraca.get(brojac);
            trenutniIgrac.odigrajPotez();
            int trenutniRezultat = trenutniIgrac.izracunajUkupniRezultat();
            if (trenutniRezultat > najboljiRezultat) {
                najboljiRezultat = trenutniRezultat;
                najboljiIgrac = trenutniIgrac;
            }
            brojac = brojac + 1;
        }

        if (najboljiIgrac != null) {
            najboljiIgrac.povecajBodove(1);
        }

        if (trenutnoGlasanje != null && trenutnoGlasanje.isGlasanjeZavrseno() && trenutnoGlasanje.isZakonPrihvacen()) {
            vlada.donesiNoviZakon(trenutnoGlasanje.getNazivZakona());
        }
    }

    public void simulirajGlasanjeUNiti(List<Runnable> akcijeIgraca) {
        List<String> naziviIgraca = new ArrayList<>();
        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            naziviIgraca.add(listaIgraca.get(brojac).getNaziv());
            brojac = brojac + 1;
        }
        sinkronizatorGlasanja.simulirajGlasanjeUNiti(akcijeIgraca, naziviIgraca);
    }

    public void pokreniNovoGlasanje(String nazivZakona) {
        trenutnoGlasanje = new Glasanje(nazivZakona);
    }

    public void zabrojiGlasIgraca(String nazivIgraca, boolean glasZa) {
        if (trenutnoGlasanje != null) {
            trenutnoGlasanje.zabrojiGlas(nazivIgraca, glasZa);
        }
    }

    public void zatvoriTrenutnoGlasanje() {
        if (trenutnoGlasanje != null) {
            trenutnoGlasanje.zatvoriGlasanje(listaIgraca.size() - 1);
        }
    }

    public Glasanje getTrenutnoGlasanje() {
        return trenutnoGlasanje;
    }

    public boolean provjeriPobjedu() {
        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            if (listaIgraca.get(brojac).getBodoviPobjede() >= BODOVI_ZA_POBJEDU) {
                return true;
            }
            brojac = brojac + 1;
        }
        return igraZavrsena;
    }

    public String dohvatiPobjednika() {
        return redoslijedPoteza.dohvatiPobjednika();
    }

    public KlasaIgraca dohvatiIgracaNaPotezu() {
        return redoslijedPoteza.dohvatiIgracaNaPotezu();
    }

    public List<KlasaIgraca> getListaIgraca() {
        return listaIgraca;
    }

    public Vlada getVlada() {
        return vlada;
    }

    public String getTrenutnaFaza() {
        return trenutnaFaza;
    }

    public int getBrojRunde() {
        return brojRunde;
    }

    public int getPozicijaNaPotezu() {
        return redoslijedPoteza.getPozicijaNaPotezu();
    }

    public boolean isIgraZavrsena() {
        return igraZavrsena;
    }
}