package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.model.KapitalistickaKlasa;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.Vlada;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class PrikazPloce {

    private static final String BOJA_PODLOGE = StilGumba.POZADINA_TAMNA;

    private final KreatorKartice kreatorKartice = new KreatorKartice();
    private final List<List<Label>> listaOznakaPoIgracu = new ArrayList<>();
    private final List<VBox> listaKarticaIgraca = new ArrayList<>();

    public HBox napraviDrzavnuPlocu(List<KlasaIgraca> listaIgraca) {
        HBox plocaIgre = new HBox(20);
        plocaIgre.setPadding(new Insets(30));
        plocaIgre.setBackground(new Background(new BackgroundFill(Color.web(BOJA_PODLOGE), CornerRadii.EMPTY, Insets.EMPTY)));

        listaOznakaPoIgracu.clear();
        listaKarticaIgraca.clear();

        for (KlasaIgraca igrac : listaIgraca) {
            VBox karticaIgraca = napraviKarticuIgraca(igrac);
            listaKarticaIgraca.add(karticaIgraca);
            plocaIgre.getChildren().add(karticaIgraca);
        }
        return plocaIgre;
    }

    private VBox napraviKarticuIgraca(KlasaIgraca igrac) {
        VBox kartica = new VBox(10);
        kreatorKartice.postaviOkvirIVanjskiStil(kartica, igrac.isNaPotezu());

        List<Label> oznakePodataka = napraviOznakePodataka(igrac);
        VBox sadrzajKartice = new VBox(8);
        sadrzajKartice.setPadding(new Insets(15, 18, 0, 18));
        sadrzajKartice.getChildren().addAll(oznakePodataka);
        sadrzajKartice.getChildren().add(napraviMiniGrafZaIgraca(igrac));

        Label oznakaBodova = kreatorKartice.napraviOznakuBodova(igrac);
        oznakePodataka.add(oznakaBodova);
        sadrzajKartice.getChildren().add(oznakaBodova);

        kartica.getChildren().addAll(kreatorKartice.napraviZaglavljeKartice(igrac), sadrzajKartice);
        listaOznakaPoIgracu.add(oznakePodataka);
        return kartica;
    }

    private VBox napraviMiniGrafZaIgraca(KlasaIgraca igrac) {
        if (igrac instanceof RadnickaKlasa radnicka) {
            return kreatorKartice.napraviMiniGraf(igrac, radnicka.getStandardZivota(), 100, "Standard zivota");
        } else if (igrac instanceof hr.tvz.java.projekt.model.SrednjaKlasa srednja) {
            return kreatorKartice.napraviMiniGraf(igrac, srednja.getStandardZivota(), 100, "Standard zivota");
        } else if (igrac instanceof KapitalistickaKlasa kapitalist) {
            return kreatorKartice.napraviMiniGraf(igrac, kapitalist.getUkupniKapital(), 400, "Ukupni kapital");
        } else if (igrac instanceof Vlada vlada) {
            return kreatorKartice.napraviMiniGraf(igrac, vlada.getLegitimnost(), 100, "Legitimnost");
        }
        return new VBox();
    }

    private List<Label> napraviOznakePodataka(KlasaIgraca igrac) {
        List<Label> oznake = new ArrayList<>();

        if (igrac instanceof RadnickaKlasa radnicka) {
            oznake.add(kreatorKartice.napraviOznaku("Standard zivota: " + radnicka.getStandardZivota()));
            oznake.add(kreatorKartice.napraviOznaku("Kolicina hrane: " + radnicka.getKolicinaHrane()));
            oznake.add(kreatorKartice.napraviOznaku("Zaposleni: " + radnicka.getZaposleniRadnici() + " / " + radnicka.getBrojRadnika()));
            oznake.add(kreatorKartice.napraviOznaku("U strajku: " + (radnicka.isJeUStrajku() ? "DA" : "Ne")));
        } else if (igrac instanceof hr.tvz.java.projekt.model.SrednjaKlasa srednja) {
            oznake.add(kreatorKartice.napraviOznaku("Standard zivota: " + srednja.getStandardZivota()));
            oznake.add(kreatorKartice.napraviOznaku("Poduzeca: " + srednja.getBrojMalihPoduzeca()));
            oznake.add(kreatorKartice.napraviOznaku("Kapital: " + String.format("%.2f", srednja.getUstedjeniKapital())));
        } else if (igrac instanceof KapitalistickaKlasa kapitalist) {
            oznake.add(kreatorKartice.napraviOznaku("Kapital: " + String.format("%.2f", kapitalist.getUkupniKapital())));
            oznake.add(kreatorKartice.napraviOznaku("Tvornice: " + kapitalist.getBrojTvornica()));
            oznake.add(kreatorKartice.napraviOznaku("Dionice: " + String.format("%.2f", kapitalist.getVrijednostDionica())));
        } else if (igrac instanceof Vlada vlada) {
            oznake.add(kreatorKartice.napraviOznaku("Proracun: " + String.format("%.2f", vlada.getDrzavniProracun())));
            oznake.add(kreatorKartice.napraviOznaku("Stopa poreza: " + String.format("%.2f", vlada.getStopaPoreza())));
            oznake.add(kreatorKartice.napraviOznaku("Min. placa: " + String.format("%.2f", vlada.getMinimalnaPlaca())));
            oznake.add(kreatorKartice.napraviOznaku("Legitimnost: " + vlada.getLegitimnost()));
        }
        return oznake;
    }

    public void azurirajPrikaz(List<KlasaIgraca> listaIgraca) {
        for (int i = 0; i < listaIgraca.size(); i++) {
            KlasaIgraca igrac = listaIgraca.get(i);
            List<Label> oznakePodataka = listaOznakaPoIgracu.get(i);
            List<Label> noveOznake = napraviOznakePodataka(igrac);

            for (int j = 0; j < oznakePodataka.size() - 1 && j < noveOznake.size(); j++) {
                oznakePodataka.get(j).setText(noveOznake.get(j).getText());
            }
            oznakePodataka.get(oznakePodataka.size() - 1).setText("BODOVI: " + igrac.getBodoviPobjede());
            kreatorKartice.postaviOkvirIVanjskiStil(listaKarticaIgraca.get(i), igrac.isNaPotezu());
        }
    }
}