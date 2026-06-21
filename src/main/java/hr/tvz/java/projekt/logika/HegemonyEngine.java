package hr.tvz.java.projekt.logika;

import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.SrednjaKlasa;
import hr.tvz.java.projekt.model.Vlada;

import java.util.ArrayList;
import java.util.List;

public class HegemonyEngine {

    public static final String FAZA_PRIPREMA = "PRIPREMA";
    public static final String FAZA_AKCIJA = "AKCIJA";
    public static final String FAZA_PROIZVODNJA = "PROIZVODNJA";
    public static final String FAZA_POTROSNJA = "POTROSNJA";
    public static final String FAZA_GLASANJE = "GLASANJE";
    public static final String FAZA_KRAJ_RUNDE = "KRAJ_RUNDE";

    private List<KlasaIgraca> listaIgraca;
    private Vlada vlada;
    private String trenutnaFaza;
    private int brojRunde;
    private RedoslijedPoteza redoslijedPoteza;
    private SinkronizatorGlasanja sinkronizatorGlasanja;
    private ObradaProizvodnje obradaProizvodnje;
    private boolean igraZavrsena;
    private Glasanje trenutnoGlasanje;
    private static final int MAKSIMALNI_BROJ_RUNDI = 5;

    public HegemonyEngine(List<KlasaIgraca> listaIgraca) {
        this.listaIgraca = listaIgraca;
        this.trenutnaFaza = FAZA_PRIPREMA;
        this.brojRunde = 1;
        this.igraZavrsena = false;
        this.redoslijedPoteza = new RedoslijedPoteza(listaIgraca);
        this.sinkronizatorGlasanja = new SinkronizatorGlasanja(listaIgraca.size());
        this.obradaProizvodnje = new ObradaProizvodnje();
        Vlada pronadjenaVlada = null;
        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            if (listaIgraca.get(brojac) instanceof Vlada) {
                pronadjenaVlada = (Vlada) listaIgraca.get(brojac);
            }
            brojac = brojac + 1;
        }
        this.vlada = pronadjenaVlada;
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
            trenutnaFaza = FAZA_PROIZVODNJA;
        } else if (trenutnaFaza.equals(FAZA_PROIZVODNJA)) {
            trenutnaFaza = FAZA_POTROSNJA;
        } else if (trenutnaFaza.equals(FAZA_POTROSNJA) && trenutnoGlasanje != null) {
            trenutnaFaza = FAZA_GLASANJE;
        } else if (trenutnaFaza.equals(FAZA_POTROSNJA)) {
            trenutnaFaza = FAZA_KRAJ_RUNDE;
            obradiKrajRunde();
        } else if (trenutnaFaza.equals(FAZA_GLASANJE)) {
            trenutnaFaza = FAZA_KRAJ_RUNDE;
            obradiKrajRunde();
        } else {
            pokreniNovuRundu();
        }
    }

    public String obradiFazuProizvodnje() {
        return obradaProizvodnje.obradiFazuProizvodnje(listaIgraca);
    }

    public String obradiFazuPotrosnje() {
        return obradaProizvodnje.obradiFazuPotrosnje(listaIgraca);
    }

    public boolean iskoristiAkcijuTrenutnogIgraca(String nazivAkcije) {
        return redoslijedPoteza.dohvatiApSustavTrenutnogIgraca().iskoristiAkciju(nazivAkcije);
    }

    public boolean jeAkcijaDostupnaTrenutnomIgracu(String nazivAkcije) {
        return redoslijedPoteza.dohvatiApSustavTrenutnogIgraca().jeAkcijaDostupna(nazivAkcije);
    }

    public void postaviLimitAkcijeTrenutnogIgraca(String nazivAkcije, int limit) {
        redoslijedPoteza.dohvatiApSustavTrenutnogIgraca().postaviLimitAkcije(nazivAkcije, limit);
    }

    public void prebaciPotez() {
        redoslijedPoteza.prebaciNaSljedecegIgraca();
    }

    public boolean jeIgracNaPotezuOdigraoSveApove() {
        return redoslijedPoteza.jeIgracNaPotezuOdigraoSveApove();
    }

    public int dohvatiPreostaleApTrenutnogIgraca() {
        return redoslijedPoteza.dohvatiApSustavTrenutnogIgraca().getPreostaliAp();
    }

    public void obradiKrajRunde() {
        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            KlasaIgraca trenutniIgrac = listaIgraca.get(brojac);
            trenutniIgrac.odigrajPotez();
            if (trenutniIgrac instanceof SrednjaKlasa) {
                trenutniIgrac.povecajBodove(((SrednjaKlasa) trenutniIgrac).getStandardZivota() / 20);
            }
            brojac = brojac + 1;
        }

        vlada.preracunajLegitimnostUBodove();
        obradaProizvodnje.primijeniMmfProvjeru(vlada);

        if (trenutnoGlasanje != null && trenutnoGlasanje.isGlasanjeZavrseno() && trenutnoGlasanje.isZakonPrihvacen()) {
            vlada.donesiNoviZakon(trenutnoGlasanje.getNazivZakona());
        }
        trenutnoGlasanje = null;

        if (brojRunde == MAKSIMALNI_BROJ_RUNDI) {
            obradaProizvodnje.primijeniFinalnoBodovanje(listaIgraca);
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

    public boolean isIgraZavrsena() {
        return igraZavrsena;
    }
}