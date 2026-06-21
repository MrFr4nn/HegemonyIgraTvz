package hr.tvz.java.projekt.gui;

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

    public VBox napraviKontroleZaIgraca(KlasaIgraca igrac, Runnable akcijaNakonPoteza) {
        if (igrac instanceof RadnickaKlasa) {
            return napraviKontroleRadnickeKlase((RadnickaKlasa) igrac, akcijaNakonPoteza);
        } else if (igrac instanceof SrednjaKlasa) {
            return napraviKontroleSrednjeKlase((SrednjaKlasa) igrac, akcijaNakonPoteza);
        } else if (igrac instanceof KapitalistickaKlasa) {
            return napraviKontroleKapitalisticke((KapitalistickaKlasa) igrac, akcijaNakonPoteza);
        } else {
            return napraviKontroleVlade((Vlada) igrac, akcijaNakonPoteza);
        }
    }

    private Label napraviNaslovPanela(String tekst, String bojaHex) {
        Label naslov = new Label(tekst);
        naslov.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        naslov.setTextFill(Color.web(bojaHex));
        return naslov;
    }

    private VBox napraviKontroleRadnickeKlase(RadnickaKlasa radnickaKlasa, Runnable akcijaNakonPoteza) {
        VBox panelKontrola = new VBox(8);
        panelKontrola.setPadding(new Insets(15));

        Label naslovPanela = napraviNaslovPanela("Potezi - Radnicka klasa", "#B0413E");

        Button gumbKupiHranu = new Button("Kupi hranu (trosak 8)");
        StilGumba.primijeniPozitivni(gumbKupiHranu);
        gumbKupiHranu.setOnAction(dogadjaj -> {
            radnickaKlasa.kupiHranu(10, 8);
            akcijaNakonPoteza.run();
        });

        Button gumbInvestirajObrazovanje = new Button("Investiraj u obrazovanje (trosak 10)");
        StilGumba.primijeniNeutralni(gumbInvestirajObrazovanje);
        gumbInvestirajObrazovanje.setOnAction(dogadjaj -> {
            radnickaKlasa.investirajUObrazovanje(10);
            akcijaNakonPoteza.run();
        });

        Button gumbZaposliRadnika = new Button("Zaposli radnika");
        StilGumba.primijeniNeutralni(gumbZaposliRadnika);
        gumbZaposliRadnika.setOnAction(dogadjaj -> {
            radnickaKlasa.zaposliRadnika(1);
            akcijaNakonPoteza.run();
        });

        panelKontrola.getChildren().addAll(naslovPanela, gumbKupiHranu, gumbInvestirajObrazovanje, gumbZaposliRadnika);
        return panelKontrola;
    }

    private VBox napraviKontroleSrednjeKlase(SrednjaKlasa srednjaKlasa, Runnable akcijaNakonPoteza) {
        VBox panelKontrola = new VBox(8);
        panelKontrola.setPadding(new Insets(15));

        Label naslovPanela = napraviNaslovPanela("Potezi - Srednja klasa", "#C9A227");

        Button gumbOtvoriPoduzece = new Button("Otvori poduzece (trosak 15)");
        StilGumba.primijeniPozitivni(gumbOtvoriPoduzece);
        gumbOtvoriPoduzece.setOnAction(dogadjaj -> {
            srednjaKlasa.otvoriNovoPoduzece(15.0);
            akcijaNakonPoteza.run();
        });

        Button gumbInvestirajObrazovanje = new Button("Investiraj u obrazovanje (trosak 10)");
        StilGumba.primijeniNeutralni(gumbInvestirajObrazovanje);
        gumbInvestirajObrazovanje.setOnAction(dogadjaj -> {
            srednjaKlasa.investirajUObrazovanje(10);
            akcijaNakonPoteza.run();
        });

        Button gumbOstvariPrihod = new Button("Ostvari prihod (+20)");
        StilGumba.primijeniPozitivni(gumbOstvariPrihod);
        gumbOstvariPrihod.setOnAction(dogadjaj -> {
            srednjaKlasa.ostvariPrihod(20.0);
            akcijaNakonPoteza.run();
        });

        panelKontrola.getChildren().addAll(naslovPanela, gumbOtvoriPoduzece, gumbInvestirajObrazovanje, gumbOstvariPrihod);
        return panelKontrola;
    }

    private VBox napraviKontroleKapitalisticke(KapitalistickaKlasa kapitalistickaKlasa, Runnable akcijaNakonPoteza) {
        VBox panelKontrola = new VBox(8);
        panelKontrola.setPadding(new Insets(15));

        Label naslovPanela = napraviNaslovPanela("Potezi - Kapitalisticka klasa", "#2E6E8E");

        Button gumbIzgradiTvornicu = new Button("Izgradi tvornicu (trosak 50)");
        StilGumba.primijeniPozitivni(gumbIzgradiTvornicu);
        gumbIzgradiTvornicu.setOnAction(dogadjaj -> {
            kapitalistickaKlasa.izgradiTvornicu(50.0);
            akcijaNakonPoteza.run();
        });

        Button gumbUloziInvesticiju = new Button("Ulozi u investiciju (trosak 30)");
        StilGumba.primijeniNeutralni(gumbUloziInvesticiju);
        gumbUloziInvesticiju.setOnAction(dogadjaj -> {
            kapitalistickaKlasa.ulozUInvesticiju(30.0);
            akcijaNakonPoteza.run();
        });

        panelKontrola.getChildren().addAll(naslovPanela, gumbIzgradiTvornicu, gumbUloziInvesticiju);
        return panelKontrola;
    }

    private VBox napraviKontroleVlade(Vlada vlada, Runnable akcijaNakonPoteza) {
        VBox panelKontrola = new VBox(8);
        panelKontrola.setPadding(new Insets(15));

        Label naslovPanela = napraviNaslovPanela("Potezi - Vlada", "#454B52");

        Button gumbPovecajPorez = new Button("Povecaj porez (+0.05)");
        StilGumba.primijeniNeutralni(gumbPovecajPorez);
        gumbPovecajPorez.setOnAction(dogadjaj -> {
            vlada.promijeniStopuPoreza(vlada.getStopaPoreza() + 0.05);
            akcijaNakonPoteza.run();
        });

        Button gumbSmanjiPorez = new Button("Smanji porez (-0.05)");
        StilGumba.primijeniNeutralni(gumbSmanjiPorez);
        gumbSmanjiPorez.setOnAction(dogadjaj -> {
            vlada.promijeniStopuPoreza(vlada.getStopaPoreza() - 0.05);
            akcijaNakonPoteza.run();
        });

        Button gumbNaplatiPorez = new Button("Naplati porez (od 50 prihoda)");
        StilGumba.primijeniPozitivni(gumbNaplatiPorez);
        gumbNaplatiPorez.setOnAction(dogadjaj -> {
            vlada.naplatiPorez(50.0);
            akcijaNakonPoteza.run();
        });

        panelKontrola.getChildren().addAll(naslovPanela, gumbPovecajPorez, gumbSmanjiPorez, gumbNaplatiPorez);
        return panelKontrola;
    }

    public VBox napraviPanelPrijedlogaZakona(Runnable akcijaPrijedlogPoreza, Runnable akcijaPrijedlogPlace) {
        VBox panelKontrola = new VBox(8);
        panelKontrola.setPadding(new Insets(15));

        Label naslovPanela = napraviNaslovPanela("Vlada predlaze zakon:", "#454B52");

        Button gumbPrijedlogPoreza = new Button("Predlozi zakon o porezu");
        StilGumba.primijeniNaglaseni(gumbPrijedlogPoreza);
        gumbPrijedlogPoreza.setOnAction(dogadjaj -> akcijaPrijedlogPoreza.run());

        Button gumbPrijedlogPlace = new Button("Predlozi zakon o minimalnoj placi");
        StilGumba.primijeniNaglaseni(gumbPrijedlogPlace);
        gumbPrijedlogPlace.setOnAction(dogadjaj -> akcijaPrijedlogPlace.run());

        panelKontrola.getChildren().addAll(naslovPanela, gumbPrijedlogPoreza, gumbPrijedlogPlace);
        return panelKontrola;
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