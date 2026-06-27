package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.logika.HegemonyEngine;
import hr.tvz.java.projekt.model.KapitalistickaKlasa;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.SrednjaKlasa;
import hr.tvz.java.projekt.model.Vlada;
import javafx.scene.layout.HBox;

public class DefinicijeKarataPoKlasi {

    public void postaviLimiteAkoNisuPostavljeni(HegemonyEngine engineIgre, KlasaIgraca igrac) {
        if (igrac instanceof RadnickaKlasa) {
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("Zaposljavanje", 1);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("Obrazovanje", 1);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("Strajk", 1);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("OtpustiRadnika", 1);
        } else if (igrac instanceof SrednjaKlasa) {
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("OtvoriPoduzece", 1);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("ObrazovanjeSrednja", 1);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("OstvariPrihod", 1);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("ZatvoriPoduzece", 1);
        } else if (igrac instanceof KapitalistickaKlasa) {
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("IzgradiTvornicu", 1);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("Lobiranje", 1);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("ProdajTvornicu", 1);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("PlatiPorez", 1);
        } else {
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("JavneInvesticije", 1);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("SocijalniPaket", 1);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("NaplatiPorez", 1);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("Dekret", 1);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("PokreniGlasanje", 1);
        }
    }

    public void dodajKarteRadnicke(HBox red, KontrolePoteza kontrolePoteza, HegemonyEngine engineIgre,
                                   RadnickaKlasa radnickaKlasa, Runnable akcija) {
        kontrolePoteza.dodajKartu(red, engineIgre, radnickaKlasa, "Zaposljavanje",
                "Posalji radnika u firmu", "M4 8 H20 V18 H4 Z M9 8 V5 H15 V8", "Zaposljavanje", () -> {
                    radnickaKlasa.zaposliRadnika(1);
                    akcija.run();
                });
        kontrolePoteza.dodajKartu(red, engineIgre, radnickaKlasa, "Obrazovanje",
                "Podigni kvalifikaciju (trosak 10)", "M4 18 L12 14 L20 18 L12 22 Z M12 14 V4", "Obrazovanje", () -> {
                    radnickaKlasa.investirajUObrazovanje(10);
                    akcija.run();
                });
        kontrolePoteza.dodajKartu(red, engineIgre, radnickaKlasa, "Sindikalni prosvjed",
                "Pokreni strajk u tvrtki", "M12 2 L13 10 L21 10 L14 14 L17 22 L12 17 L7 22 L10 14 L3 10 L11 10 Z", "Strajk", () -> {
                    radnickaKlasa.pokreniStrajk();
                    akcija.run();
                });
        kontrolePoteza.dodajKartu(red, engineIgre, radnickaKlasa, "Otpusti radnika",
                "Smanji broj zaposlenih", "M19 13 H5 V11 H19 Z", "OtpustiRadnika", () -> {
                    radnickaKlasa.otpustiRadnika(1);
                    akcija.run();
                });
    }

    public void dodajKarteSrednje(HBox red, KontrolePoteza kontrolePoteza, HegemonyEngine engineIgre,
                                  SrednjaKlasa srednjaKlasa, Runnable akcija) {
        kontrolePoteza.dodajKartu(red, engineIgre, srednjaKlasa, "Otvori poduzece",
                "Pokreni novi posao (trosak 15)", "M4 10 H20 V20 H4 Z M9 10 V6 H15 V10", "OtvoriPoduzece", () -> {
                    srednjaKlasa.otvoriNovoPoduzece(15.0);
                    akcija.run();
                });
        kontrolePoteza.dodajKartu(red, engineIgre, srednjaKlasa, "Obrazovanje",
                "Podigni kvalifikaciju (trosak 10)", "M4 18 L12 14 L20 18 L12 22 Z M12 14 V4", "ObrazovanjeSrednja", () -> {
                    srednjaKlasa.investirajUObrazovanje(10);
                    akcija.run();
                });
        kontrolePoteza.dodajKartu(red, engineIgre, srednjaKlasa, "Ostvari prihod",
                "Prodaj robu na trzistu (+20)", "M4 20 L12 4 L20 20 Z", "OstvariPrihod", () -> {
                    srednjaKlasa.ostvariPrihod(20.0);
                    akcija.run();
                });
        kontrolePoteza.dodajKartu(red, engineIgre, srednjaKlasa, "Zatvori poduzece",
                "Smanji broj poduzeca", "M19 13 H5 V11 H19 Z", "ZatvoriPoduzece", () -> {
                    srednjaKlasa.zatvoriPoduzece();
                    akcija.run();
                });
    }

    public void dodajKarteKapitalisticke(HBox red, KontrolePoteza kontrolePoteza, HegemonyEngine engineIgre,
                                         KapitalistickaKlasa kapitalist, Runnable akcija) {
        kontrolePoteza.dodajKartu(red, engineIgre, kapitalist, "Investicija",
                "Izgradi tvornicu (trosak 50)", "M3 18 H21 V20 H3 Z M5 18 V10 H7 V18 Z M9 18 V6 H11 V18 Z M13 18 V11 H15 V18 Z", "IzgradiTvornicu", () -> {
                    kapitalist.izgradiTvornicu(50.0);
                    akcija.run();
                });
        kontrolePoteza.dodajKartu(red, engineIgre, kapitalist, "Lobiranje",
                "Plati za politicki utjecaj (trosak 30)", "M4 20 H20 V21 H4 Z M12 4 L20 10 H4 Z", "Lobiranje", () -> {
                    kapitalist.ulozUInvesticiju(30.0);
                    akcija.run();
                });
        kontrolePoteza.dodajKartu(red, engineIgre, kapitalist, "Prodaj tvornicu",
                "Otkupna vrijednost (+25)", "M19 13 H5 V11 H19 Z", "ProdajTvornicu", () -> {
                    kapitalist.prodajTvornicu(25.0);
                    akcija.run();
                });
        kontrolePoteza.dodajKartu(red, engineIgre, kapitalist, "Plati porez",
                "Podmiri poreznu obvezu (10)", "M12 6 V18 M9 9 H15 M9 15 H15", "PlatiPorez", () -> {
                    kapitalist.platiPorez(10.0);
                    akcija.run();
                });
    }

    public void dodajKarteVlade(HBox red, KontrolePoteza kontrolePoteza, HegemonyEngine engineIgre,
                                Vlada vlada, Runnable akcija) {
        kontrolePoteza.dodajKartu(red, engineIgre, vlada, "Javne investicije",
                "Izgradi javnu ustanovu",
                "M4 20 H20 V21 H4 Z M6 20 V11 H8 V20 Z M11 20 V11 H13 V20 Z M16 20 V11 H18 V20 Z M3 11 L12 4 L21 11 Z",
                "JavneInvesticije", () -> {
                    vlada.povecajLegitimnost(5);
                    akcija.run();
                });
        kontrolePoteza.dodajKartu(red, engineIgre, vlada, "Socijalni paket",
                "Isplati pomoc (trosak 15)",
                "M12 2 C16 2 19 5 19 9 C19 13 12 22 12 22 C12 22 5 13 5 9 C5 5 8 2 12 2 Z",
                "SocijalniPaket", () -> {
                    vlada.isplatiSubvenciju(15.0);
                    akcija.run();
                });
        kontrolePoteza.dodajKartu(red, engineIgre, vlada, "Naplati porez",
                "Prihod od poreza (od 50 baze)", "M12 6 V18 M9 9 H15 M9 15 H15", "NaplatiPorez", () -> {
                    vlada.naplatiPorez(50.0);
                    akcija.run();
                });
        kontrolePoteza.dodajKartu(red, engineIgre, vlada, "Dekret",
                "Pojacaj legitimnost prisilno", "M12 3 L20 8 V16 L12 21 L4 16 V8 Z", "Dekret", () -> {
                    vlada.povecajLegitimnost(10);
                    akcija.run();
                });
    }
}