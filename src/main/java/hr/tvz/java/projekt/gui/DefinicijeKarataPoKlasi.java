package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.logika.HegemonyEngine;
import hr.tvz.java.projekt.model.KapitalistickaKlasa;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.SrednjaKlasa;
import hr.tvz.java.projekt.model.Vlada;
import javafx.scene.layout.HBox;

import java.util.List;

public class DefinicijeKarataPoKlasi {

    private PoolKarata poolKarata;

    public DefinicijeKarataPoKlasi() {
        this.poolKarata = new PoolKarata();
    }

    public void osvjeziPoolZaNovuRundu() {
        poolKarata.izaberiBrzRandKarte();
    }

    public void postaviLimiteAkoNisuPostavljeni(HegemonyEngine engineIgre, KlasaIgraca igrac) {
        List<PoolKarata.PodaciKarte> aktivne = dohvatiAktivneKarte(igrac);
        int brojac = 0;
        while (brojac < aktivne.size()) {
            engineIgre.postaviLimitAkcijeTrenutnogIgraca(aktivne.get(brojac).nazivAkcije, 1);
            brojac = brojac + 1;
        }
        if (igrac instanceof Vlada) {
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("PokreniGlasanje", 1);
        }
    }

    private List<PoolKarata.PodaciKarte> dohvatiAktivneKarte(KlasaIgraca igrac) {
        if (igrac instanceof RadnickaKlasa) {
            return poolKarata.getAktivneRadnicke();
        } else if (igrac instanceof SrednjaKlasa) {
            return poolKarata.getAktivneSrednje();
        } else if (igrac instanceof KapitalistickaKlasa) {
            return poolKarata.getAktivneKapitalisticke();
        } else {
            return poolKarata.getAktivneVlade();
        }
    }

    public void dodajKarteRadnicke(HBox red, KontrolePoteza kontrolePoteza, HegemonyEngine engineIgre,
                                   RadnickaKlasa radnickaKlasa, Runnable akcija) {
        List<PoolKarata.PodaciKarte> aktivne = poolKarata.getAktivneRadnicke();
        int brojac = 0;
        while (brojac < aktivne.size()) {
            PoolKarata.PodaciKarte karta = aktivne.get(brojac);
            Runnable efekt = napraviEfektRadnicke(karta.nazivAkcije, radnickaKlasa, akcija);
            if (efekt != null) {
                kontrolePoteza.dodajKartu(red, engineIgre, radnickaKlasa, karta.naziv, karta.opis, karta.svgIkona, karta.nazivAkcije, efekt);
            }
            brojac = brojac + 1;
        }
    }

    private Runnable napraviEfektRadnicke(String nazivAkcije, RadnickaKlasa radnickaKlasa, Runnable akcija) {
        if (nazivAkcije.equals("Zaposljavanje")) {
            return () -> { radnickaKlasa.zaposliRadnika(1); akcija.run(); };
        } else if (nazivAkcije.equals("Obrazovanje")) {
            return () -> { radnickaKlasa.investirajUObrazovanje(10); akcija.run(); };
        } else if (nazivAkcije.equals("Strajk")) {
            return () -> { radnickaKlasa.pokreniStrajk(); akcija.run(); };
        } else if (nazivAkcije.equals("OtpustiRadnika")) {
            return () -> { radnickaKlasa.otpustiRadnika(1); akcija.run(); };
        } else if (nazivAkcije.equals("KolektivniUgovor")) {
            return () -> { radnickaKlasa.setStandardZivota(radnickaKlasa.getStandardZivota() + 8); akcija.run(); };
        } else if (nazivAkcije.equals("ZdravstvenaZastita")) {
            return () -> { radnickaKlasa.setStandardZivota(radnickaKlasa.getStandardZivota() + 6); radnickaKlasa.kupiHranu(3, 0); akcija.run(); };
        } else if (nazivAkcije.equals("PrekovremeniRad")) {
            return () -> { radnickaKlasa.kupiHranu(5, 0); radnickaKlasa.setStandardZivota(radnickaKlasa.getStandardZivota() - 2); akcija.run(); };
        } else if (nazivAkcije.equals("RegionalniRazvoj")) {
            return () -> { radnickaKlasa.zaposliRadnika(2); radnickaKlasa.setStandardZivota(radnickaKlasa.getStandardZivota() + 4); akcija.run(); };
        }
        return null;
    }

    public void dodajKarteSrednje(HBox red, KontrolePoteza kontrolePoteza, HegemonyEngine engineIgre,
                                  SrednjaKlasa srednjaKlasa, Runnable akcija) {
        List<PoolKarata.PodaciKarte> aktivne = poolKarata.getAktivneSrednje();
        int brojac = 0;
        while (brojac < aktivne.size()) {
            PoolKarata.PodaciKarte karta = aktivne.get(brojac);
            Runnable efekt = napraviEfektSrednje(karta.nazivAkcije, srednjaKlasa, akcija);
            if (efekt != null) {
                kontrolePoteza.dodajKartu(red, engineIgre, srednjaKlasa, karta.naziv, karta.opis, karta.svgIkona, karta.nazivAkcije, efekt);
            }
            brojac = brojac + 1;
        }
    }

    private Runnable napraviEfektSrednje(String nazivAkcije, SrednjaKlasa srednjaKlasa, Runnable akcija) {
        if (nazivAkcije.equals("OtvoriPoduzece")) {
            return () -> { srednjaKlasa.otvoriNovoPoduzece(15.0); akcija.run(); };
        } else if (nazivAkcije.equals("ObrazovanjeSrednja")) {
            return () -> { srednjaKlasa.investirajUObrazovanje(10); akcija.run(); };
        } else if (nazivAkcije.equals("OstvariPrihod")) {
            return () -> { srednjaKlasa.ostvariPrihod(20.0); akcija.run(); };
        } else if (nazivAkcije.equals("ZatvoriPoduzece")) {
            return () -> { srednjaKlasa.zatvoriPoduzece(); akcija.run(); };
        } else if (nazivAkcije.equals("Digitalizacija")) {
            return () -> { srednjaKlasa.ostvariPrihod(25.0); akcija.run(); };
        } else if (nazivAkcije.equals("IzvozRobe")) {
            return () -> { srednjaKlasa.ostvariPrihod(30.0); akcija.run(); };
        } else if (nazivAkcije.equals("Partnerstvo")) {
            return () -> { srednjaKlasa.setStandardZivota(srednjaKlasa.getStandardZivota() + 7); akcija.run(); };
        } else if (nazivAkcije.equals("Stednja")) {
            return () -> { srednjaKlasa.setUstedjeniKapital(srednjaKlasa.getUstedjeniKapital() + 15.0); akcija.run(); };
        }
        return null;
    }

    public void dodajKarteKapitalisticke(HBox red, KontrolePoteza kontrolePoteza, HegemonyEngine engineIgre,
                                         KapitalistickaKlasa kapitalist, Runnable akcija) {
        List<PoolKarata.PodaciKarte> aktivne = poolKarata.getAktivneKapitalisticke();
        int brojac = 0;
        while (brojac < aktivne.size()) {
            PoolKarata.PodaciKarte karta = aktivne.get(brojac);
            Runnable efekt = napraviEfektKapitalisticke(karta.nazivAkcije, kapitalist, akcija);
            if (efekt != null) {
                kontrolePoteza.dodajKartu(red, engineIgre, kapitalist, karta.naziv, karta.opis, karta.svgIkona, karta.nazivAkcije, efekt);
            }
            brojac = brojac + 1;
        }
    }

    private Runnable napraviEfektKapitalisticke(String nazivAkcije, KapitalistickaKlasa kapitalist, Runnable akcija) {
        if (nazivAkcije.equals("IzgradiTvornicu")) {
            return () -> { kapitalist.izgradiTvornicu(50.0); akcija.run(); };
        } else if (nazivAkcije.equals("Lobiranje")) {
            return () -> { kapitalist.ulozUInvesticiju(30.0); akcija.run(); };
        } else if (nazivAkcije.equals("ProdajTvornicu")) {
            return () -> { kapitalist.prodajTvornicu(25.0); akcija.run(); };
        } else if (nazivAkcije.equals("PlatiPorez")) {
            return () -> { kapitalist.platiPorez(10.0); akcija.run(); };
        } else if (nazivAkcije.equals("FuzijaKompanija")) {
            return () -> { kapitalist.setUkupniKapital(kapitalist.getUkupniKapital() + 40.0); akcija.run(); };
        } else if (nazivAkcije.equals("BurzovnaSpeculacija")) {
            double random = Math.random();
            if (random > 0.5) {
                return () -> { kapitalist.setUkupniKapital(kapitalist.getUkupniKapital() + 35.0); akcija.run(); };
            } else {
                return () -> { kapitalist.platiPorez(20.0); akcija.run(); };
            }
        } else if (nazivAkcije.equals("Outsourcing")) {
            return () -> { kapitalist.setUkupniKapital(kapitalist.getUkupniKapital() + 20.0); akcija.run(); };
        } else if (nazivAkcije.equals("Monopolizacija")) {
            return () -> { kapitalist.ulozUInvesticiju(50.0); akcija.run(); };
        }
        return null;
    }

    public void dodajKarteVlade(HBox red, KontrolePoteza kontrolePoteza, HegemonyEngine engineIgre,
                                Vlada vlada, Runnable akcija) {
        List<PoolKarata.PodaciKarte> aktivne = poolKarata.getAktivneVlade();
        int brojac = 0;
        while (brojac < aktivne.size()) {
            PoolKarata.PodaciKarte karta = aktivne.get(brojac);
            Runnable efekt = napraviEfektVlade(karta.nazivAkcije, vlada, akcija);
            if (efekt != null) {
                kontrolePoteza.dodajKartu(red, engineIgre, vlada, karta.naziv, karta.opis, karta.svgIkona, karta.nazivAkcije, efekt);
            }
            brojac = brojac + 1;
        }
    }

    private Runnable napraviEfektVlade(String nazivAkcije, Vlada vlada, Runnable akcija) {
        if (nazivAkcije.equals("JavneInvesticije")) {
            return () -> { vlada.povecajLegitimnost(5); akcija.run(); };
        } else if (nazivAkcije.equals("SocijalniPaket")) {
            return () -> { vlada.isplatiSubvenciju(15.0); akcija.run(); };
        } else if (nazivAkcije.equals("NaplatiPorez")) {
            return () -> { vlada.naplatiPorez(50.0); akcija.run(); };
        } else if (nazivAkcije.equals("Dekret")) {
            return () -> { vlada.povecajLegitimnost(10); akcija.run(); };
        } else if (nazivAkcije.equals("Infrastruktura")) {
            return () -> { vlada.povecajLegitimnost(8); vlada.setDrzavniProracun(vlada.getDrzavniProracun() - 20.0); akcija.run(); };
        } else if (nazivAkcije.equals("ObrazovnaReforma")) {
            return () -> { vlada.povecajLegitimnost(6); vlada.setDrzavniProracun(vlada.getDrzavniProracun() - 10.0); akcija.run(); };
        } else if (nazivAkcije.equals("AnticiklicnaPolitika")) {
            return () -> { vlada.setDrzavniProracun(vlada.getDrzavniProracun() + 25.0); akcija.run(); };
        } else if (nazivAkcije.equals("VanjskaPolitika")) {
            return () -> { vlada.setDrzavniProracun(vlada.getDrzavniProracun() + 30.0); vlada.povecajLegitimnost(4); akcija.run(); };
        }
        return null;
    }
}