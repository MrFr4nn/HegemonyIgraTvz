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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class KontrolePoteza {

    public VBox napraviKontroleZaIgraca(HegemonyEngine engineIgre, KlasaIgraca igrac, Runnable akcijaPrijedlog, Runnable akcijaPonovnogPrikaza) {
        postaviLimiteAkoNisuPostavljeni(engineIgre, igrac);

        VBox panelKontrola = new VBox(8);
        panelKontrola.setPadding(new Insets(15));

        Label naslovPanela = napraviNaslovPanela("Potezi - " + odrediNazivUloge(igrac), odrediBoju(igrac));
        Label oznakaAp = new Label("Preostalo akcijskih bodova ove runde gleda se kroz limite gumbova.");
        oznakaAp.setStyle("-fx-font-size: 10px; -fx-text-fill: #6B6357;");

        panelKontrola.getChildren().addAll(naslovPanela, oznakaAp);

        if (igrac instanceof RadnickaKlasa) {
            dodajGumboveRadnicke(panelKontrola, engineIgre, (RadnickaKlasa) igrac, akcijaPonovnogPrikaza);
        } else if (igrac instanceof SrednjaKlasa) {
            dodajGumboveSrednje(panelKontrola, engineIgre, (SrednjaKlasa) igrac, akcijaPonovnogPrikaza);
        } else if (igrac instanceof KapitalistickaKlasa) {
            dodajGumboveKapitalisticke(panelKontrola, engineIgre, (KapitalistickaKlasa) igrac, akcijaPonovnogPrikaza);
        } else {
            dodajGumboveVlade(panelKontrola, engineIgre, (Vlada) igrac, akcijaPonovnogPrikaza);
        }

        dodajGumbPrijedlogaZakona(panelKontrola, engineIgre, akcijaPrijedlog);
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
        dodajAkcijskiGumb(panel, engineIgre, "Zapošljavanje", "Zaposljavanje", () -> {
            radnickaKlasa.zaposliRadnika(1);
            akcija.run();
        });
        dodajAkcijskiGumb(panel, engineIgre, "Obrazovanje (trosak 10)", "Obrazovanje", () -> {
            radnickaKlasa.investirajUObrazovanje(10);
            akcija.run();
        });
        dodajAkcijskiGumb(panel, engineIgre, "Sindikalni prosvjed (Strajk)", "Strajk", () -> {
            radnickaKlasa.pokreniStrajk();
            akcija.run();
        });
    }

    private void dodajGumboveSrednje(VBox panel, HegemonyEngine engineIgre, SrednjaKlasa srednjaKlasa, Runnable akcija) {
        dodajAkcijskiGumb(panel, engineIgre, "Otvori poduzece (trosak 15)", "OtvoriPoduzece", () -> {
            srednjaKlasa.otvoriNovoPoduzece(15.0);
            akcija.run();
        });
        dodajAkcijskiGumb(panel, engineIgre, "Obrazovanje (trosak 10)", "ObrazovanjeSrednja", () -> {
            srednjaKlasa.investirajUObrazovanje(10);
            akcija.run();
        });
    }

    private void dodajGumboveKapitalisticke(VBox panel, HegemonyEngine engineIgre, KapitalistickaKlasa kapitalist, Runnable akcija) {
        dodajAkcijskiGumb(panel, engineIgre, "Investicija - Izgradi tvornicu (50)", "IzgradiTvornicu", () -> {
            kapitalist.izgradiTvornicu(50.0);
            akcija.run();
        });
        dodajAkcijskiGumb(panel, engineIgre, "Lobiranje (trosak 30)", "Lobiranje", () -> {
            kapitalist.ulozUInvesticiju(30.0);
            akcija.run();
        });
    }

    private void dodajGumboveVlade(VBox panel, HegemonyEngine engineIgre, Vlada vlada, Runnable akcija) {
        dodajAkcijskiGumb(panel, engineIgre, "Javne investicije", "JavneInvesticije", () -> {
            vlada.povecajLegitimnost(5);
            akcija.run();
        });
        dodajAkcijskiGumb(panel, engineIgre, "Socijalni paket (trosak 15)", "SocijalniPaket", () -> {
            vlada.isplatiSubvenciju(15.0);
            akcija.run();
        });
    }

    private void dodajGumbPrijedlogaZakona(VBox panel, HegemonyEngine engineIgre, Runnable akcijaPrijedlog) {
        Button gumbPrijedlog = new Button("Politicki pritisak - Predlozi zakon");
        StilGumba.primijeniNaglaseni(gumbPrijedlog);
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

    private void dodajAkcijskiGumb(VBox panel, HegemonyEngine engineIgre, String tekst, String nazivAkcije, Runnable efekt) {
        int preostalo = engineIgre.jeAkcijaDostupnaTrenutnomIgracu(nazivAkcije) ? 1 : 0;
        Button gumb = new Button(tekst + (preostalo == 0 ? " (iskoristeno)" : ""));
        StilGumba.primijeniPozitivni(gumb);
        if (preostalo == 0) {
            gumb.setDisable(true);
        }
        gumb.setOnAction(dogadjaj -> {
            if (engineIgre.iskoristiAkcijuTrenutnogIgraca(nazivAkcije)) {
                efekt.run();
            }
        });
        panel.getChildren().add(gumb);
    }

    private Label napraviNaslovPanela(String tekst, String bojaHex) {
        Label naslov = new Label(tekst);
        naslov.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        naslov.setTextFill(Color.web(bojaHex));
        return naslov;
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

    private String odrediBoju(KlasaIgraca igrac) {
        if (igrac instanceof RadnickaKlasa) {
            return "#B0413E";
        } else if (igrac instanceof SrednjaKlasa) {
            return "#C9A227";
        } else if (igrac instanceof KapitalistickaKlasa) {
            return "#2E6E8E";
        } else {
            return "#454B52";
        }
    }

    public VBox napraviPanelGlasanja(String nazivZakona, Runnable akcijaZa, Runnable akcijaProtiv) {
        VBox panelKontrola = new VBox(8);
        panelKontrola.setPadding(new Insets(15));

        Label naslovPanela = napraviNaslovPanela("Glasajte o zakonu: " + nazivZakona, "#2B2520");

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