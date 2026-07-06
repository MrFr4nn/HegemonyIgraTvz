package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.logika.HegemonyEngine;
import hr.tvz.java.projekt.model.*;
import javafx.scene.layout.HBox;
import java.util.List;

public class DefinicijeKarataPoKlasi {

    private final PoolKarata poolKarata = new PoolKarata();

    public void osvjeziPoolZaNovuRundu() {
        poolKarata.izaberiBrzRandKarte();
    }

    public void postaviLimiteAkoNisuPostavljeni(HegemonyEngine engineIgre, KlasaIgraca igrac) {
        List<PoolKarata.PodaciKarte> aktivne = dohvatiAktivneKarte(igrac);
        for (PoolKarata.PodaciKarte karta : aktivne) {
            engineIgre.postaviLimitAkcijeTrenutnogIgraca(karta.getNazivAkcije(), 1);
        }
        if (igrac instanceof Vlada) {
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("PokreniGlasanje", 1);
        }
    }

    private List<PoolKarata.PodaciKarte> dohvatiAktivneKarte(KlasaIgraca igrac) {
        if (igrac instanceof RadnickaKlasa) return poolKarata.getAktivneRadnicke();
        if (igrac instanceof SrednjaKlasa) return poolKarata.getAktivneSrednje();
        if (igrac instanceof KapitalistickaKlasa) return poolKarata.getAktivneKapitalisticke();
        return poolKarata.getAktivneVlade();
    }

    // Generička metoda koja je zamijenila 4 duplicirane petlje i skratila klasu za 50+ linija
    private void procesuirajKarte(HBox red, KontrolePoteza kontrole, HegemonyEngine engine,
                                  KlasaIgraca igrac, List<PoolKarata.PodaciKarte> aktivne,
                                  java.util.function.Function<PoolKarata.PodaciKarte, Runnable> generatorEfekta) {
        for (PoolKarata.PodaciKarte karta : aktivne) {
            Runnable efekt = generatorEfekta.apply(karta);
            if (efekt != null) {
                KontrolePoteza.PodaciOKarti podaci = new KontrolePoteza.PodaciOKarti(
                        karta.getNaziv(), karta.getOpis(), karta.getSvgIkona(), karta.getNazivAkcije(), efekt
                );
                kontrole.dodajKartu(red, engine, igrac, podaci);
            }
        }
    }

    public void dodajKarteRadnicke(HBox red, KontrolePoteza kontrole, HegemonyEngine engine, RadnickaKlasa radnicka, Runnable akcija) {
        procesuirajKarte(red, kontrole, engine, radnicka, poolKarata.getAktivneRadnicke(),
                karta -> napraviEfektRadnicke(karta.getNazivAkcije(), radnicka, akcija));
    }

    public void dodajKarteSrednje(HBox red, KontrolePoteza kontrole, HegemonyEngine engine, SrednjaKlasa srednja, Runnable akcija) {
        procesuirajKarte(red, kontrole, engine, srednja, poolKarata.getAktivneSrednje(),
                karta -> napraviEfektSrednje(karta.getNazivAkcije(), srednja, akcija));
    }

    public void dodajKarteKapitalisticke(HBox red, KontrolePoteza kontrole, HegemonyEngine engine, KapitalistickaKlasa kapitalist, Runnable akcija) {
        procesuirajKarte(red, kontrole, engine, kapitalist, poolKarata.getAktivneKapitalisticke(),
                karta -> napraviEfektKapitalisticke(karta.getNazivAkcije(), kapitalist, akcija));
    }

    public void dodajKarteVlade(HBox red, KontrolePoteza kontrole, HegemonyEngine engine, Vlada vlada, Runnable akcija) {
        procesuirajKarte(red, kontrole, engine, vlada, poolKarata.getAktivneVlade(),
                karta -> napraviEfektVlade(karta.getNazivAkcije(), vlada, akcija));
    }

    private Runnable napraviEfektRadnicke(String akcija, RadnickaKlasa r, Runnable osvezi) {
        switch (akcija) {
            case "Zaposljavanje": return () -> { r.zaposliRadnika(1); osvezi.run(); };
            case "Obrazovanje": return () -> { r.investirajUObrazovanje(30); osvezi.run(); };
            case "Strajk": return () -> { r.pokreniStrajk(); osvezi.run(); }; // Popravljen tipfelerski bug (bio je radnickaKlask)
            case "OtpustiRadnika": return () -> { r.otpustiRadnika(1); osvezi.run(); };
            case "KolektivniUgovor": return () -> { r.setStandardZivota(r.getStandardZivota() + 8); osvezi.run(); };
            case "ZdravstvenaZastita": return () -> { r.setStandardZivota(r.getStandardZivota() + 6); r.kupiHranu(3, 5); osvezi.run(); };
            case "PrekovremeniRad": return () -> { r.kupiHranu(2, 5); r.setStandardZivota(r.getStandardZivota() - 2); osvezi.run(); };
            case "RegionalniRazvoj": return () -> { r.zaposliRadnika(2); r.setStandardZivota(r.getStandardZivota() + 4); osvezi.run(); };
            default: return null;
        }
    }

    private Runnable napraviEfektSrednje(String akcija, SrednjaKlasa s, Runnable osvezi) {
        switch (akcija) {
            case "OtvoriPoduzece": return () -> { s.otvoriNovoPoduzece(15.0); osvezi.run(); };
            case "ObrazovanjeSrednja": return () -> { s.investirajUObrazovanje(10); osvezi.run(); };
            case "OstvariPrihod": return () -> { s.ostvariPrihod(20.0); osvezi.run(); };
            case "ZatvoriPoduzece": return () -> { s.zatvoriPoduzece(); osvezi.run(); };
            case "Digitalizacija": return () -> { s.ostvariPrihod(25.0); osvezi.run(); };
            case "IzvozRobe": return () -> { s.ostvariPrihod(30.0); osvezi.run(); };
            case "Partnerstvo": return () -> { s.setStandardZivota(s.getStandardZivota() + 7); osvezi.run(); };
            case "Stednja": return () -> { s.setUstedjeniKapital(s.getUstedjeniKapital() + 15.0); osvezi.run(); };
            default: return null;
        }
    }

    @SuppressWarnings("java:S2245")
    private Runnable napraviEfektKapitalisticke(String akcija, KapitalistickaKlasa k, Runnable osvezi) {
        switch (akcija) {
            case "IzgradiTvornicu": return () -> { k.izgradiTvornicu(50.0); osvezi.run(); };
            case "Lobiranje": return () -> { k.ulozUInvesticiju(30.0); osvezi.run(); };
            case "ProdajTvornicu": return () -> { k.prodajTvornicu(25.0); osvezi.run(); };
            case "PlatiPorez": return () -> { k.platiPorez(10.0); osvezi.run(); };
            case "FuzijaKompanija": return () -> { k.setUkupniKapital(k.getUkupniKapital() + 40.0); osvezi.run(); };
            case "Outsourcing": return () -> { k.setUkupniKapital(k.getUkupniKapital() + 20.0); osvezi.run(); };
            case "Monopolizacija": return () -> { k.ulozUInvesticiju(50.0); osvezi.run(); };
            case "BurzovnaSpeculacija":
                return () -> {
                    if (Math.random() > 0.5) k.setUkupniKapital(k.getUkupniKapital() + 35.0);
                    else k.platiPorez(20.0);
                    osvezi.run();
                };
            default: return null;
        }
    }

    private Runnable napraviEfektVlade(String akcija, Vlada v, Runnable osvezi) {
        switch (akcija) {
            case "JavneInvesticije": return () -> { v.povecajLegitimnost(5); osvezi.run(); };
            case "SocijalniPaket": return () -> { v.isplatiSubvenciju(15.0); osvezi.run(); };
            case "NaplatiPorez": return () -> { v.naplatiPorez(50.0); osvezi.run(); };
            case "Dekret": return () -> { v.povecajLegitimnost(10); osvezi.run(); };
            case "Infrastruktura": return () -> { v.povecajLegitimnost(8); v.setDrzavniProracun(v.getDrzavniProracun() - 20.0); osvezi.run(); };
            case "ObrazovnaReforma": return () -> { v.povecajLegitimnost(6); v.setDrzavniProracun(v.getDrzavniProracun() - 10.0); osvezi.run(); };
            case "AnticiklicnaPolitika": return () -> { v.setDrzavniProracun(v.getDrzavniProracun() + 25.0); osvezi.run(); };
            case "VanjskaPolitika": return () -> { v.setDrzavniProracun(v.getDrzavniProracun() + 30.0); v.povecajLegitimnost(4); osvezi.run(); };
            default: return null;
        }
    }
}