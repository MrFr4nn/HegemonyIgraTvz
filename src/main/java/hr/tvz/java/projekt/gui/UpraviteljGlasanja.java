package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.logika.HegemonyEngine;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.util.XmlUpravitelj;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class UpraviteljGlasanja {

    private HegemonyEngine engineIgre;
    private KontrolePoteza kontrolePoteza;
    private XmlUpravitelj xmlUpravitelj;
    private int pozicijaGlasacaUNizu;

    public UpraviteljGlasanja(HegemonyEngine engineIgre, KontrolePoteza kontrolePoteza, XmlUpravitelj xmlUpravitelj) {
        this.engineIgre = engineIgre;
        this.kontrolePoteza = kontrolePoteza;
        this.xmlUpravitelj = xmlUpravitelj;
        this.pozicijaGlasacaUNizu = 0;
    }

    public void resetirajPoziciju() {
        pozicijaGlasacaUNizu = 0;
    }

    public void prikaziPanelGlasanja(VBox panelKontrolaTrenutniIgrac, Runnable akcijaPonovnogPrikaza) {
        if (engineIgre.getTrenutnoGlasanje() == null) {
            VBox panelPrijedloga = kontrolePoteza.napraviPanelPrijedlogaZakona(
                    () -> obradiPrijedlogZakona("Zakon o porezu", akcijaPonovnogPrikaza),
                    () -> obradiPrijedlogZakona("Zakon o minimalnoj placi", akcijaPonovnogPrikaza)
            );
            panelKontrolaTrenutniIgrac.getChildren().add(panelPrijedloga);
            return;
        }

        if (engineIgre.getTrenutnoGlasanje().isGlasanjeZavrseno()) {
            Label oznakaIshoda = new Label(engineIgre.getTrenutnoGlasanje().ispisiRezultatGlasanja());
            panelKontrolaTrenutniIgrac.getChildren().add(oznakaIshoda);
            return;
        }

        List<KlasaIgraca> listaIgraca = engineIgre.getListaIgraca();
        while (pozicijaGlasacaUNizu < listaIgraca.size() && listaIgraca.get(pozicijaGlasacaUNizu) == engineIgre.getVlada()) {
            pozicijaGlasacaUNizu = pozicijaGlasacaUNizu + 1;
        }

        if (pozicijaGlasacaUNizu >= listaIgraca.size()) {
            engineIgre.zatvoriTrenutnoGlasanje();
            akcijaPonovnogPrikaza.run();
            return;
        }

        KlasaIgraca trenutniGlasac = listaIgraca.get(pozicijaGlasacaUNizu);
        VBox panelGlasanja = kontrolePoteza.napraviPanelGlasanja(
                engineIgre.getTrenutnoGlasanje().getNazivZakona(),
                () -> obradiGlas(trenutniGlasac.getNaziv(), true, akcijaPonovnogPrikaza),
                () -> obradiGlas(trenutniGlasac.getNaziv(), false, akcijaPonovnogPrikaza)
        );
        Label oznakaTko = new Label("Glasa: " + trenutniGlasac.getNaziv());
        panelKontrolaTrenutniIgrac.getChildren().clear();
        panelKontrolaTrenutniIgrac.getChildren().addAll(oznakaTko, panelGlasanja);
    }

    private void obradiPrijedlogZakona(String nazivZakona, Runnable akcijaPonovnogPrikaza) {
        engineIgre.pokreniNovoGlasanje(nazivZakona);
        pozicijaGlasacaUNizu = 0;
        xmlUpravitelj.dodajPotezUPovijest(engineIgre.getBrojRunde(), engineIgre.getVlada().getNaziv(),
                "Predlozen zakon: " + nazivZakona);
        akcijaPonovnogPrikaza.run();
    }

    private void obradiGlas(String nazivIgraca, boolean glasZa, Runnable akcijaPonovnogPrikaza) {
        engineIgre.zabrojiGlasIgraca(nazivIgraca, glasZa);
        String odluka;
        if (glasZa) {
            odluka = "ZA";
        } else {
            odluka = "PROTIV";
        }
        xmlUpravitelj.dodajPotezUPovijest(engineIgre.getBrojRunde(), nazivIgraca, "Glasao " + odluka);
        pozicijaGlasacaUNizu = pozicijaGlasacaUNizu + 1;
        akcijaPonovnogPrikaza.run();
    }
}