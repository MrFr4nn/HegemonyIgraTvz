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
    private String nazivPredlagaca;

    public UpraviteljGlasanja(HegemonyEngine engineIgre, KontrolePoteza kontrolePoteza, XmlUpravitelj xmlUpravitelj) {
        this.engineIgre = engineIgre;
        this.kontrolePoteza = kontrolePoteza;
        this.xmlUpravitelj = xmlUpravitelj;
        this.pozicijaGlasacaUNizu = 0;
        this.nazivPredlagaca = "";
    }

    public void resetirajPoziciju() {
        pozicijaGlasacaUNizu = 0;
        nazivPredlagaca = "";
    }

    public void zabiljeziPredlagaca(String naziv) {
        this.nazivPredlagaca = naziv;
    }

    public void prikaziPanelGlasanja(VBox panelKontrolaTrenutniIgrac, Runnable akcijaPonovnogPrikaza) {
        if (engineIgre.getTrenutnoGlasanje() == null) {
            return;
        }

        if (engineIgre.getTrenutnoGlasanje().isGlasanjeZavrseno()) {
            Label oznakaIshoda = new Label(engineIgre.getTrenutnoGlasanje().ispisiRezultatGlasanja());
            oznakaIshoda.setStyle("-fx-text-fill: #E3D9C4; -fx-font-size: 13px;");
            panelKontrolaTrenutniIgrac.getChildren().add(oznakaIshoda);
            return;
        }

        List<KlasaIgraca> listaIgraca = engineIgre.getListaIgraca();
        while (pozicijaGlasacaUNizu < listaIgraca.size()
                && listaIgraca.get(pozicijaGlasacaUNizu).getNaziv().equals(nazivPredlagaca)) {
            pozicijaGlasacaUNizu = pozicijaGlasacaUNizu + 1;
        }

        if (pozicijaGlasacaUNizu >= listaIgraca.size()) {
            engineIgre.zatvoriTrenutnoGlasanje();
            akcijaPonovnogPrikaza.run();
            return;
        }

        KlasaIgraca trenutniGlasac = listaIgraca.get(pozicijaGlasacaUNizu);
        String bojaHex = StilGumba.dohvatiBojuKlase(trenutniGlasac);

        VBox panelGlasanja = kontrolePoteza.napraviPanelGlasanja(
                engineIgre.getTrenutnoGlasanje().getNazivZakona(),
                trenutniGlasac.getNaziv(),
                bojaHex,
                () -> obradiGlas(trenutniGlasac.getNaziv(), true, akcijaPonovnogPrikaza),
                () -> obradiGlas(trenutniGlasac.getNaziv(), false, akcijaPonovnogPrikaza)
        );
        panelKontrolaTrenutniIgrac.getChildren().clear();
        panelKontrolaTrenutniIgrac.getChildren().add(panelGlasanja);
    }

    public void obradiPrijedlogZakona(String naziviPredlagaca, String nazivZakona) {
        engineIgre.pokreniNovoGlasanje(nazivZakona);
        zabiljeziPredlagaca(naziviPredlagaca);
        pozicijaGlasacaUNizu = 0;
        xmlUpravitelj.dodajPotezUPovijest(engineIgre.getBrojRunde(), naziviPredlagaca, "Predlozen zakon: " + nazivZakona);
    }

    private void obradiGlas(String nazivIgraca, boolean glasZa, Runnable akcijaPonovnogPrikaza) {
        engineIgre.zabrojiGlasIgraca(nazivIgraca, glasZa);
        String odluka = glasZa ? "ZA" : "PROTIV";
        xmlUpravitelj.dodajPotezUPovijest(engineIgre.getBrojRunde(), nazivIgraca, "Glasao " + odluka);
        pozicijaGlasacaUNizu = pozicijaGlasacaUNizu + 1;
        akcijaPonovnogPrikaza.run();
    }
}