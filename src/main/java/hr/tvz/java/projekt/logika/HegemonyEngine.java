package hr.tvz.java.projekt.logika;

import hr.tvz.java.projekt.model.KapitalistickaKlasa;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.RadnickaKlasa;
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
    private static final int MAKSIMALNI_BROJ_RUNDI = 5;

    private final List<KlasaIgraca> listaIgraca;
    private Vlada vlada;
    private String trenutnaFaza;
    private int brojRunde;
    private final RedoslijedPoteza redoslijedPoteza;
    private final SinkronizatorGlasanja sinkronizatorGlasanja;
    private final ObradaProizvodnje obradaProizvodnje;
    private final KatalogZakona katalogZakona;
    private boolean igraZavrsena;
    private Glasanje trenutnoGlasanje;

    public HegemonyEngine(List<KlasaIgraca> listaIgraca) {
        this.listaIgraca = listaIgraca;
        this.trenutnaFaza = FAZA_PRIPREMA;
        this.brojRunde = 1;
        this.igraZavrsena = false;
        this.redoslijedPoteza = new RedoslijedPoteza(listaIgraca);
        this.sinkronizatorGlasanja = new SinkronizatorGlasanja(listaIgraca.size());
        this.obradaProizvodnje = new ObradaProizvodnje();
        this.katalogZakona = new KatalogZakona();

        for (KlasaIgraca igrac : listaIgraca) {
            if (igrac instanceof Vlada pronadjenaVlada) {
                this.vlada = pronadjenaVlada;
            }
        }
    }

    public void pokreniNovuRundu() {
        brojRunde++;
        trenutnaFaza = FAZA_PRIPREMA;
        redoslijedPoteza.resetirajNaPocetak();
        if (brojRunde > MAKSIMALNI_BROJ_RUNDI) {
            igraZavrsena = true;
        }
    }

    public void prebaciNaSljedecuFazu() {
        switch (trenutnaFaza) {
            case FAZA_PRIPREMA    -> trenutnaFaza = FAZA_AKCIJA;
            case FAZA_AKCIJA      -> trenutnaFaza = FAZA_PROIZVODNJA;
            case FAZA_PROIZVODNJA -> trenutnaFaza = FAZA_POTROSNJA;
            case FAZA_POTROSNJA   -> {
                if (trenutnoGlasanje != null) {
                    trenutnaFaza = FAZA_GLASANJE;
                } else {
                    trenutnaFaza = FAZA_KRAJ_RUNDE;
                    obradiKrajRunde();
                }
            }
            // TOČAN PRIJELAZ: Nakon faze glasanja, igra ide na obradu kraja runde
            case FAZA_GLASANJE    -> {
                trenutnaFaza = FAZA_KRAJ_RUNDE;
                obradiKrajRunde();
            }
            default -> pokreniNovuRundu();
        }
    }

    public void prebaciNaSljedecuFazuDokNijeGlasanjeIliKraj() {
        while (!trenutnaFaza.equals(FAZA_GLASANJE) && !trenutnaFaza.equals(FAZA_KRAJ_RUNDE)) {
            prebaciNaSljedecuFazu();
        }
    }

    public String obradiFazuProizvodnje() { return obradaProizvodnje.obradiFazuProizvodnje(listaIgraca); }
    public String obradiFazuPotrosnje() { return obradaProizvodnje.obradiFazuPotrosnje(listaIgraca); }
    public boolean iskoristiAkcijuTrenutnogIgraca(String nazivAkcije) { return redoslijedPoteza.dohvatiApSustavTrenutnogIgraca().iskoristiAkciju(nazivAkcije); }

    public boolean jeAkcijaDostupnaTrenutnomIgracu(String nazivAkcije) {
        // Prva provjera: ima li uopće preostalih AP-ova u AP sustavu
        boolean imaAp = redoslijedPoteza.dohvatiApSustavTrenutnogIgraca().jeAkcijaDostupna(nazivAkcije);
        if (!imaAp) {
            return false;
        }

        // Druga provjera: ekonomska provjera resursa na temelju točnog nazivAkcije iz PoolKarata
        KlasaIgraca trenutniIgrac = dohvatiIgracaNaPotezu();

        if (trenutniIgrac instanceof SrednjaKlasa srednja) {
            switch (nazivAkcije) {
                case "ZatvoriPoduzece" -> { return srednja.getBrojMalihPoduzeca() > 0; }
                case "IzvozRobe"       -> { return srednja.getBrojMalihPoduzeca() > 0; }
                case "OtvoriPoduzece"  -> { return srednja.getUstedjeniKapital() >= 15.0; }
            }
        }

        else if (trenutniIgrac instanceof KapitalistickaKlasa kapitalist) {
            switch (nazivAkcije) {
                case "ProdajTvornicu"   -> { return kapitalist.getBrojTvornica() > 0; }
                case "FuzijaKompanija"  -> { return kapitalist.getBrojTvornica() >= 2; }
                case "Lobiranje"        -> { return kapitalist.getUkupniKapital() >= 10.0; }
            }
        }

        else if (trenutniIgrac instanceof RadnickaKlasa radnicka) {
            switch (nazivAkcije) {
                case "PotrosiHranu"     -> { return radnicka.getKolicinaHrane() > 0; }
                // POPRAVAK: nazivAkcije iz PoolKarata za ovu kartu je "OtpustiRadnika" bez razmaka!
                case "OtpustiRadnika"   -> { return radnicka.getZaposleniRadnici() > 0; }
                // POPRAVAK: nazivAkcije iz PoolKarata za prosvjed je "Strajk"
                case "Strajk"           -> { return !radnicka.isJeUStrajku() && radnicka.getZaposleniRadnici() > 0; }
            }
        }

        return true;
    }

    public void postaviLimitAkcijeTrenutnogIgraca(String nazivAkcije, int limit) { redoslijedPoteza.dohvatiApSustavTrenutnogIgraca().postaviLimitAkcije(nazivAkcije, limit); }
    public void prebaciPotez() { redoslijedPoteza.prebaciNaSljedecegIgraca(); }
    public boolean jeIgracNaPotezuOdigraoSveApove() { return redoslijedPoteza.jeIgracNaPotezuOdigraoSveApove(); }
    public boolean jesuLiSviIgraciOdigrali() { return redoslijedPoteza.jesuLiSviIgraciOdigraliSveApove(); }
    public int dohvatiPreostaleApTrenutnogIgraca() { return redoslijedPoteza.dohvatiApSustavTrenutnogIgraca().getPreostaliAp(); }
    public KatalogZakona getKatalogZakona() { return katalogZakona; }

    public void pokreniGlasanjeOZakonu(int indeksZakona) {
        pokreniNovoGlasanje(katalogZakona.dohvatiNaziv(indeksZakona));
        katalogZakona.oznaciIskoristenim(indeksZakona);
        iskoristiAkcijuTrenutnogIgraca("PokreniGlasanje");
    }

    public void obradiKrajRunde() {
        obradaProizvodnje.obradiKrajRunde(listaIgraca, vlada, trenutnoGlasanje, katalogZakona, brojRunde, MAKSIMALNI_BROJ_RUNDI);
        trenutnoGlasanje = null;
    }

    public void simulirajGlasanjeUNiti(List<Runnable> akcijeIgraca) { sinkronizatorGlasanja.simulirajGlasanjeUNiti(akcijeIgraca, listaIgraca); }
    public void pokreniNovoGlasanje(String nazivZakona) { trenutnoGlasanje = new Glasanje(nazivZakona); }

    public void zabrojiGlasIgraca(String nazivIgraca, boolean glasZa) {
        if (trenutnoGlasanje != null) trenutnoGlasanje.zabrojiGlas(nazivIgraca, glasZa);
    }

    public void zatvoriTrenutnoGlasanje() {
        if (trenutnoGlasanje != null) trenutnoGlasanje.zatvoriGlasanje();
    }

    public Glasanje getTrenutnoGlasanje() { return trenutnoGlasanje; }
    public boolean provjeriPobjedu() { return igraZavrsena; }
    public String dohvatiPobjednika() { return redoslijedPoteza.dohvatiPobjednika(); }
    public KlasaIgraca dohvatiIgracaNaPotezu() { return redoslijedPoteza.dohvatiIgracaNaPotezu(); }
    public List<KlasaIgraca> getListaIgraca() { return listaIgraca; }
    public Vlada getVlada() { return vlada; }
    public String getTrenutnaFaza() { return trenutnaFaza; }
    public int getBrojRunde() { return brojRunde; }
    public boolean isIgraZavrsena() { return igraZavrsena; }
}