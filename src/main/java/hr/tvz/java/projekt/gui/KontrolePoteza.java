package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.logika.HegemonyEngine;
import hr.tvz.java.projekt.model.KapitalistickaKlasa;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.SrednjaKlasa;
import hr.tvz.java.projekt.model.Vlada;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class KontrolePoteza {

    private KreatorIgraceKarte kreatorIgraceKarte;

    public KontrolePoteza() {
        this.kreatorIgraceKarte = new KreatorIgraceKarte();
    }

    public VBox napraviKontroleZaIgraca(HegemonyEngine engineIgre, KlasaIgraca igrac, Runnable akcijaPrijedlog, Runnable akcijaPonovnogPrikaza) {
        postaviLimiteAkoNisuPostavljeni(engineIgre, igrac);

        VBox panelKontrola = new VBox(10);
        panelKontrola.setPadding(new Insets(10));
        StilGumba.primijeniObrubAktivneKlase(panelKontrola, igrac);

        Label naslovPanela = new Label(odrediNazivUloge(igrac).toUpperCase() + " - ODABERITE KARTU");
        naslovPanela.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        naslovPanela.setStyle("-fx-text-fill: " + StilGumba.dohvatiBojuKlase(igrac) + ";");

        HBox redKarata = new HBox(10);
        redKarata.setAlignment(Pos.CENTER);

        if (igrac instanceof RadnickaKlasa) {
            dodajKarteRadnicke(redKarata, engineIgre, (RadnickaKlasa) igrac, akcijaPonovnogPrikaza);
        } else if (igrac instanceof SrednjaKlasa) {
            dodajKarteSrednje(redKarata, engineIgre, (SrednjaKlasa) igrac, akcijaPonovnogPrikaza);
        } else if (igrac instanceof KapitalistickaKlasa) {
            dodajKarteKapitalisticke(redKarata, engineIgre, (KapitalistickaKlasa) igrac, akcijaPonovnogPrikaza);
        } else {
            dodajKarteVlade(redKarata, engineIgre, (Vlada) igrac, akcijaPonovnogPrikaza);
        }

        dodajKartuPrijedlogaZakona(redKarata, engineIgre, igrac, akcijaPrijedlog);

        panelKontrola.getChildren().addAll(naslovPanela, redKarata);
        return panelKontrola;
    }

    private void postaviLimiteAkoNisuPostavljeni(HegemonyEngine engineIgre, KlasaIgraca igrac) {
        if (igrac instanceof RadnickaKlasa) {
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("Zaposljavanje", 3);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("Obrazovanje", 2);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("Strajk", 1);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("PrijedlogZakona", 1);
        } else if (igrac instanceof SrednjaKlasa) {
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("OtvoriPoduzece", 2);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("ObrazovanjeSrednja", 2);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("PrijedlogZakona", 1);
        } else if (igrac instanceof KapitalistickaKlasa) {
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("IzgradiTvornicu", 3);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("Lobiranje", 2);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("PrijedlogZakona", 1);
        } else {
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("JavneInvesticije", 2);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("SocijalniPaket", 3);
            engineIgre.postaviLimitAkcijeTrenutnogIgraca("PrijedlogZakona", 2);
        }
    }

    private void dodajKarteRadnicke(HBox red, HegemonyEngine engineIgre, RadnickaKlasa radnickaKlasa, Runnable akcija) {
        dodajKartu(red, engineIgre, radnickaKlasa, "Zaposljavanje",
                "Posalji radnika u firmu", "M4 8 H20 V18 H4 Z M9 8 V5 H15 V8", "Zaposljavanje", () -> {
                    radnickaKlasa.zaposliRadnika(1);
                    akcija.run();
                });
        dodajKartu(red, engineIgre, radnickaKlasa, "Obrazovanje",
                "Podigni kvalifikaciju (trosak 10)", "M4 18 L12 14 L20 18 L12 22 Z M12 14 V4", "Obrazovanje", () -> {
                    radnickaKlasa.investirajUObrazovanje(10);
                    akcija.run();
                });
        dodajKartu(red, engineIgre, radnickaKlasa, "Sindikalni prosvjed",
                "Pokreni strajk u tvrtki", "M12 2 L13 10 L21 10 L14 14 L17 22 L12 17 L7 22 L10 14 L3 10 L11 10 Z", "Strajk", () -> {
                    radnickaKlasa.pokreniStrajk();
                    akcija.run();
                });
    }

    private void dodajKarteSrednje(HBox red, HegemonyEngine engineIgre, SrednjaKlasa srednjaKlasa, Runnable akcija) {
        dodajKartu(red, engineIgre, srednjaKlasa, "Otvori poduzece",
                "Pokreni novi posao (trosak 15)", "M4 10 H20 V20 H4 Z M9 10 V6 H15 V10", "OtvoriPoduzece", () -> {
                    srednjaKlasa.otvoriNovoPoduzece(15.0);
                    akcija.run();
                });
        dodajKartu(red, engineIgre, srednjaKlasa, "Obrazovanje",
                "Podigni kvalifikaciju (trosak 10)", "M4 18 L12 14 L20 18 L12 22 Z M12 14 V4", "ObrazovanjeSrednja", () -> {
                    srednjaKlasa.investirajUObrazovanje(10);
                    akcija.run();
                });
    }

    private void dodajKarteKapitalisticke(HBox red, HegemonyEngine engineIgre, KapitalistickaKlasa kapitalist, Runnable akcija) {
        dodajKartu(red, engineIgre, kapitalist, "Investicija",
                "Izgradi tvornicu (trosak 50)", "M3 18 H21 V20 H3 Z M5 18 V10 H7 V18 Z M9 18 V6 H11 V18 Z M13 18 V11 H15 V18 Z", "IzgradiTvornicu", () -> {
                    kapitalist.izgradiTvornicu(50.0);
                    akcija.run();
                });
        dodajKartu(red, engineIgre, kapitalist, "Lobiranje",
                "Plati za politicki utjecaj (trosak 30)", "M4 20 H20 V21 H4 Z M12 4 L20 10 H4 Z", "Lobiranje", () -> {
                    kapitalist.ulozUInvesticiju(30.0);
                    akcija.run();
                });
    }

    private void dodajKarteVlade(HBox red, HegemonyEngine engineIgre, Vlada vlada, Runnable akcija) {
        dodajKartu(red, engineIgre, vlada, "Javne investicije",
                "Izgradi javnu ustanovu", "M4 20 H20 V21 H4 Z M6 20 V11 H8 V20 Z M11 20 V11 H13 V20 Z M16 20 V11 H18 V20 Z M3 11 L12 4 L21 11 Z", "JavneInvesticije", () -> {
                    vlada.povecajLegitimnost(5);
                    akcija.run();
                });
        dodajKartu(red, engineIgre, vlada, "Socijalni paket",
                "Isplati pomoc (trosak 15)", "M12 2 C16 2 19 5 19 9 C19 13 12 22 12 22 C12 22 5 13 5 9 C5 5 8 2 12 2 Z", "SocijalniPaket", () -> {
                    vlada.isplatiSubvenciju(15.0);
                    akcija.run();
                });
    }

    private void dodajKartuPrijedlogaZakona(HBox red, HegemonyEngine engineIgre, KlasaIgraca igrac, Runnable akcijaPrijedlog) {
        boolean dostupna = engineIgre.jeAkcijaDostupnaTrenutnomIgracu("PrijedlogZakona");
        String bojaHex = StilGumba.dohvatiBojuKlase(igrac);
        VBox karta = kreatorIgraceKarte.napraviKartu("Politicki pritisak",
                "Predlozi promjenu zakona", "M5 21 H19 M12 3 L19 9 H5 Z M7 9 V21 M17 9 V21", bojaHex, !dostupna);

        if (dostupna) {
            kreatorIgraceKarte.omoguciHover(karta, bojaHex);
            karta.setOnMouseClicked(dogadjaj -> {
                if (engineIgre.iskoristiAkcijuTrenutnogIgraca("PrijedlogZakona")) {
                    akcijaPrijedlog.run();
                }
            });
        }
        red.getChildren().add(karta);
    }

    private void dodajKartu(HBox red, HegemonyEngine engineIgre, KlasaIgraca igrac, String naziv, String opis,
                            String svgIkona, String nazivAkcije, Runnable efekt) {
        boolean dostupna = engineIgre.jeAkcijaDostupnaTrenutnomIgracu(nazivAkcije);
        String bojaHex = StilGumba.dohvatiBojuKlase(igrac);
        VBox karta = kreatorIgraceKarte.napraviKartu(naziv, opis, svgIkona, bojaHex, !dostupna);

        if (dostupna) {
            kreatorIgraceKarte.omoguciHover(karta, bojaHex);
            karta.setOnMouseClicked(dogadjaj -> {
                if (engineIgre.iskoristiAkcijuTrenutnogIgraca(nazivAkcije)) {
                    efekt.run();
                }
            });
        }
        red.getChildren().add(karta);
    }

    private String odrediNazivUloge(KlasaIgraca igrac) {
        if (igrac instanceof RadnickaKlasa) {
            return "Radnicka klasa";
        } else if (igrac instanceof SrednjaKlasa) {
            return "Srednja klasa";
        } else if (igrac instanceof KapitalistickaKlasa) {
            return "Kapitalisticka klasa";
        } else {
            return "Vlada";
        }
    }

    public VBox napraviPanelGlasanja(String nazivZakona, Runnable akcijaZa, Runnable akcijaProtiv) {
        VBox panelKontrola = new VBox(8);
        panelKontrola.setPadding(new Insets(15));

        Label naslovPanela = new Label("Glasajte o zakonu: " + nazivZakona);
        naslovPanela.setFont(Font.font("Georgia", FontWeight.BOLD, 14));

        Button gumbZa = new Button("ZA");
        StilGumba.primijeniPozitivni(gumbZa);
        gumbZa.setOnAction(dogadjaj -> akcijaZa.run());

        Button gumbProtiv = new Button("PROTIV");
        StilGumba.primijeniNegativni(gumbProtiv);
        gumbProtiv.setOnAction(dogadjaj -> akcijaProtiv.run());

        panelKontrola.getChildren().addAll(naslovPanela, gumbZa, gumbProtiv);
        return panelKontrola;
    }
}