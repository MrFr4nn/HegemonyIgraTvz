package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.logika.HegemonyEngine;
import hr.tvz.java.projekt.model.KapitalistickaKlasa;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.SrednjaKlasa;
import hr.tvz.java.projekt.model.Vlada;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class KontrolePoteza {

    public VBox napraviKontroleZaIgraca(HegemonyEngine engineIgre, KlasaIgraca igrac, Runnable akcijaPrijedlog, Runnable akcijaPonovnogPrikaza) {
        postaviLimiteAkoNisuPostavljeni(engineIgre, igrac);

        VBox panelKontrola = new VBox(8);
        panelKontrola.setPadding(new Insets(15));
        StilGumba.primijeniObrubAktivneKlase(panelKontrola, igrac);

        Label naslovPanela = new Label(odrediNazivUloge(igrac).toUpperCase() + " - AKCIJSKA FAZA");
        naslovPanela.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        naslovPanela.setStyle("-fx-text-fill: " + StilGumba.dohvatiBojuKlase(igrac) + ";");

        panelKontrola.getChildren().add(naslovPanela);

        if (igrac instanceof RadnickaKlasa) {
            dodajGumboveRadnicke(panelKontrola, engineIgre, (RadnickaKlasa) igrac, akcijaPonovnogPrikaza);
        } else if (igrac instanceof SrednjaKlasa) {
            dodajGumboveSrednje(panelKontrola, engineIgre, (SrednjaKlasa) igrac, akcijaPonovnogPrikaza);
        } else if (igrac instanceof KapitalistickaKlasa) {
            dodajGumboveKapitalisticke(panelKontrola, engineIgre, (KapitalistickaKlasa) igrac, akcijaPonovnogPrikaza);
        } else {
            dodajGumboveVlade(panelKontrola, engineIgre, (Vlada) igrac, akcijaPonovnogPrikaza);
        }

        dodajGumbPrijedlogaZakona(panelKontrola, engineIgre, igrac, akcijaPrijedlog);
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

    private void dodajGumboveRadnicke(VBox panel, HegemonyEngine engineIgre, RadnickaKlasa radnickaKlasa, Runnable akcija) {
        dodajAkcijskiGumb(panel, engineIgre, radnickaKlasa, "Zaposljavanje", "Zaposljavanje", () -> {
            radnickaKlasa.zaposliRadnika(1);
            akcija.run();
        });
        dodajAkcijskiGumb(panel, engineIgre, radnickaKlasa, "Obrazovanje (trosak 10)", "Obrazovanje", () -> {
            radnickaKlasa.investirajUObrazovanje(10);
            akcija.run();
        });
        dodajAkcijskiGumb(panel, engineIgre, radnickaKlasa, "Sindikalni prosvjed (Strajk)", "Strajk", () -> {
            radnickaKlasa.pokreniStrajk();
            akcija.run();
        });
    }

    private void dodajGumboveSrednje(VBox panel, HegemonyEngine engineIgre, SrednjaKlasa srednjaKlasa, Runnable akcija) {
        dodajAkcijskiGumb(panel, engineIgre, srednjaKlasa, "Otvori poduzece (trosak 15)", "OtvoriPoduzece", () -> {
            srednjaKlasa.otvoriNovoPoduzece(15.0);
            akcija.run();
        });
        dodajAkcijskiGumb(panel, engineIgre, srednjaKlasa, "Obrazovanje (trosak 10)", "ObrazovanjeSrednja", () -> {
            srednjaKlasa.investirajUObrazovanje(10);
            akcija.run();
        });
    }

    private void dodajGumboveKapitalisticke(VBox panel, HegemonyEngine engineIgre, KapitalistickaKlasa kapitalist, Runnable akcija) {
        dodajAkcijskiGumb(panel, engineIgre, kapitalist, "Investicija - Izgradi tvornicu (50)", "IzgradiTvornicu", () -> {
            kapitalist.izgradiTvornicu(50.0);
            akcija.run();
        });
        dodajAkcijskiGumb(panel, engineIgre, kapitalist, "Lobiranje (trosak 30)", "Lobiranje", () -> {
            kapitalist.ulozUInvesticiju(30.0);
            akcija.run();
        });
    }

    private void dodajGumboveVlade(VBox panel, HegemonyEngine engineIgre, Vlada vlada, Runnable akcija) {
        dodajAkcijskiGumb(panel, engineIgre, vlada, "Javne investicije", "JavneInvesticije", () -> {
            vlada.povecajLegitimnost(5);
            akcija.run();
        });
        dodajAkcijskiGumb(panel, engineIgre, vlada, "Socijalni paket (trosak 15)", "SocijalniPaket", () -> {
            vlada.isplatiSubvenciju(15.0);
            akcija.run();
        });
    }

    private void dodajGumbPrijedlogaZakona(VBox panel, HegemonyEngine engineIgre, KlasaIgraca igrac, Runnable akcijaPrijedlog) {
        Button gumbPrijedlog = new Button("Politicki pritisak - Predlozi zakon");
        StilGumba.primijeniAkcijskiGumb(gumbPrijedlog, igrac);
        if (!engineIgre.jeAkcijaDostupnaTrenutnomIgracu("PrijedlogZakona")) {
            gumbPrijedlog.setDisable(true);
        }
        gumbPrijedlog.setOnAction(dogadjaj -> {
            if (engineIgre.iskoristiAkcijuTrenutnogIgraca("PrijedlogZakona")) {
                akcijaPrijedlog.run();
            }
        });
        panel.getChildren().add(gumbPrijedlog);
    }

    private void dodajAkcijskiGumb(VBox panel, HegemonyEngine engineIgre, KlasaIgraca igrac, String tekst, String nazivAkcije, Runnable efekt) {
        boolean dostupna = engineIgre.jeAkcijaDostupnaTrenutnomIgracu(nazivAkcije);
        Button gumb = new Button(tekst + (!dostupna ? " (iskoristeno)" : ""));
        StilGumba.primijeniAkcijskiGumb(gumb, igrac);
        if (!dostupna) {
            gumb.setDisable(true);
        }
        gumb.setOnAction(dogadjaj -> {
            if (engineIgre.iskoristiAkcijuTrenutnogIgraca(nazivAkcije)) {
                efekt.run();
            }
        });
        panel.getChildren().add(gumb);
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