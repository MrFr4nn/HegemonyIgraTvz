package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.logika.HegemonyEngine;
import hr.tvz.java.projekt.model.*;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefinicijeKarataPoKlasi {

    private static final int BROJ_KARATA_ZA_PRIKAZ = 2;

    private final PoolKarata poolKarata = new PoolKarata();

    public void prekiniSveStrajkoveNaPocetkuRunde(List<KlasaIgraca> listaIgraca) {
        for (KlasaIgraca igrac : listaIgraca) {
            if (igrac instanceof RadnickaKlasa radnicka && radnicka.isJeUStrajku()) {
                radnicka.prekiniStrajk();
            }
        }
    }

    public void postaviLimiteAkoNisuPostavljeni(HegemonyEngine engineIgre, KlasaIgraca igrac) {
        List<PoolKarata.PodaciKarte> cijeliPool = dohvatiCijeliPool(igrac);
        for (PoolKarata.PodaciKarte karta : cijeliPool) {
            engineIgre.postaviLimitAkcijeTrenutnogIgraca(karta.getNazivAkcije(), 1);
        }
        if (igrac instanceof Vlada) {
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("PokreniGlasanje", 1);
        }
    }

    private List<PoolKarata.PodaciKarte> dohvatiCijeliPool(KlasaIgraca igrac) {
        if (igrac instanceof RadnickaKlasa) return poolKarata.getPoolRadnicke();
        if (igrac instanceof SrednjaKlasa) return poolKarata.getPoolSrednje();
        if (igrac instanceof KapitalistickaKlasa) return poolKarata.getPoolKapitalisticke();
        return poolKarata.getPoolVlade();
    }

    private List<PoolKarata.PodaciKarte> dohvatiDvijeSlucajneDostupneKarte(HegemonyEngine engine, KlasaIgraca igrac) {
        List<PoolKarata.PodaciKarte> cijeliPool = dohvatiCijeliPool(igrac);
        List<PoolKarata.PodaciKarte> dostupne = new ArrayList<>();

        for (PoolKarata.PodaciKarte karta : cijeliPool) {
            if (engine.jeAkcijaDostupnaTrenutnomIgracu(karta.getNazivAkcije())) {
                dostupne.add(karta);
            }
        }

        Collections.shuffle(dostupne);

        List<PoolKarata.PodaciKarte> rezultat = new ArrayList<>();
        int brojac = 0;
        while (brojac < BROJ_KARATA_ZA_PRIKAZ && brojac < dostupne.size()) {
            rezultat.add(dostupne.get(brojac));
            brojac = brojac + 1;
        }
        return rezultat;
    }

    private void procesuirajKarte(HBox red, KontrolePoteza kontrole, HegemonyEngine engine,
                                  KlasaIgraca igrac, List<PoolKarata.PodaciKarte> zaPrikaz,
                                  java.util.function.Function<PoolKarata.PodaciKarte, Runnable> generatorEfekta) {
        for (PoolKarata.PodaciKarte karta : zaPrikaz) {
            Runnable efekt = generatorEfekta.apply(karta);
            if (efekt != null) {
                KontrolePoteza.PodaciOKarti podaci = new KontrolePoteza.PodaciOKarti(
                        karta.getNaziv(), karta.getOpis(), karta.getEmojiIkona(), karta.getNazivAkcije(), efekt
                );
                kontrole.dodajKartu(red, engine, igrac, podaci);
            }
        }
    }

    public void dodajKarteRadnicke(HBox red, KontrolePoteza kontrole, HegemonyEngine engine, RadnickaKlasa radnicka, Runnable akcija) {
        List<PoolKarata.PodaciKarte> zaPrikaz = dohvatiDvijeSlucajneDostupneKarte(engine, radnicka);
        procesuirajKarte(red, kontrole, engine, radnicka, zaPrikaz,
                karta -> napraviEfektRadnicke(karta.getNazivAkcije(), radnicka, akcija));
    }

    public void dodajKarteSrednje(HBox red, KontrolePoteza kontrole, HegemonyEngine engine, SrednjaKlasa srednja, Runnable akcija) {
        List<PoolKarata.PodaciKarte> zaPrikaz = dohvatiDvijeSlucajneDostupneKarte(engine, srednja);
        procesuirajKarte(red, kontrole, engine, srednja, zaPrikaz,
                karta -> napraviEfektSrednje(karta.getNazivAkcije(), srednja, akcija));
    }

    public void dodajKarteKapitalisticke(HBox red, KontrolePoteza kontrole, HegemonyEngine engine, KapitalistickaKlasa kapitalist, Runnable akcija) {
        List<PoolKarata.PodaciKarte> zaPrikaz = dohvatiDvijeSlucajneDostupneKarte(engine, kapitalist);
        procesuirajKarte(red, kontrole, engine, kapitalist, zaPrikaz,
                karta -> napraviEfektKapitalisticke(karta.getNazivAkcije(), kapitalist, akcija));
    }

    public void dodajKarteVlade(HBox red, KontrolePoteza kontrole, HegemonyEngine engine, Vlada vlada,
                                List<KlasaIgraca> listaIgraca, Runnable akcija) {
        List<PoolKarata.PodaciKarte> zaPrikaz = dohvatiDvijeSlucajneDostupneKarte(engine, vlada);
        procesuirajKarte(red, kontrole, engine, vlada, zaPrikaz,
                karta -> napraviEfektVlade(karta.getNazivAkcije(), vlada, listaIgraca, akcija));
    }

    private Runnable napraviEfektRadnicke(String akcija, RadnickaKlasa r, Runnable osvjezi) {
        switch (akcija) {
            case "Zaposljavanje": return () -> { r.zaposliRadnika(1); osvjezi.run(); };
            case "Obrazovanje": return () -> { r.investirajUObrazovanje(30); r.setStandardZivota(r.getStandardZivota() + 3); osvjezi.run(); };
            case "Strajk": return () -> { r.pokreniStrajk(); osvjezi.run(); };
            case "OtpustiRadnika": return () -> { r.otpustiRadnika(1); osvjezi.run(); };
            case "KolektivniUgovor": return () -> { r.setStandardZivota(r.getStandardZivota() + 8); osvjezi.run(); };
            case "RegionalniRazvoj": return () -> { r.zaposliRadnika(2); r.setStandardZivota(r.getStandardZivota() + 4); osvjezi.run(); };
            default: return null;
        }
    }

    private Runnable napraviEfektSrednje(String akcija, SrednjaKlasa s, Runnable osvjezi) {
        switch (akcija) {
            case "OtvoriPoduzece": return () -> { s.otvoriNovoPoduzece(15.0); osvjezi.run(); };
            case "ObrazovanjeSrednja": return () -> { s.investirajUObrazovanje(10); osvjezi.run(); };
            case "Marketing": return () -> { s.setUstedjeniKapital(s.getUstedjeniKapital() - 8.0); s.ostvariPrihod(18.0); osvjezi.run(); };
            case "ZatvoriPoduzece": return () -> { s.zatvoriPoduzece(); osvjezi.run(); };
            case "IzvozRobe": return () -> { s.ostvariPrihod(30.0); osvjezi.run(); };
            case "Stednja": return () -> { s.setUstedjeniKapital(s.getUstedjeniKapital() + 15.0); osvjezi.run(); };
            default: return null;
        }
    }

    private Runnable napraviEfektKapitalisticke(String akcija, KapitalistickaKlasa k, Runnable osvjezi) {
        switch (akcija) {
            case "TraziInvestitora": return () -> { k.ulozUInvesticiju(15.0); osvjezi.run(); };
            case "IzgradiTvornicu": return () -> { k.izgradiTvornicu(50.0); osvjezi.run(); };
            case "Lobiranje": return () -> { k.ulozUInvesticiju(30.0); osvjezi.run(); };
            case "ProdajTvornicu": return () -> { k.prodajTvornicu(25.0); osvjezi.run(); };
            case "Diverzifikacija": return () -> { k.setUkupniKapital(k.getUkupniKapital() - 20.0); k.ulozUInvesticiju(15.0); osvjezi.run(); };
            case "FuzijaKompanija": return () -> { k.setUkupniKapital(k.getUkupniKapital() + 40.0); osvjezi.run(); };
            case "BurzovnaSpeculacija":
                return () -> {
                    if (Math.random() > 0.5) k.setUkupniKapital(k.getUkupniKapital() + 35.0);
                    else k.platiPorez(20.0);
                    osvjezi.run();
                };
            default: return null;
        }
    }

    private Runnable napraviEfektVlade(String akcija, Vlada v, List<KlasaIgraca> listaIgraca, Runnable osvjezi) {
        switch (akcija) {
            case "JavneInvesticije": return () -> { v.povecajLegitimnost(5); osvjezi.run(); };
            case "SocijalniPaket": return () -> { v.isplatiSubvenciju(15.0); osvjezi.run(); };
            case "NaplatiPorez": return () -> { v.naplatiPorezOdIgraca(listaIgraca); osvjezi.run(); };
            case "Dekret": return () -> { v.povecajLegitimnost(10); osvjezi.run(); };
            case "Infrastruktura": return () -> { v.povecajLegitimnost(8); v.setDrzavniProracun(v.getDrzavniProracun() - 20.0); osvjezi.run(); };
            case "VanjskaPolitika": return () -> { v.setDrzavniProracun(v.getDrzavniProracun() + 30.0); v.povecajLegitimnost(4); osvjezi.run(); };
            default: return null;
        }
    }
}