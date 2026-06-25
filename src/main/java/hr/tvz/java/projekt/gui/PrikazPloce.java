package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.model.KapitalistickaKlasa;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.SrednjaKlasa;
import hr.tvz.java.projekt.model.Vlada;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class PrikazPloce {

    private static final String BOJA_PODLOGE = StilGumba.POZADINA_TAMNA;

    private KreatorKartice kreatorKartice;
    private List<List<Label>> listaOznakaPoIgracu;
    private List<VBox> listaKarticaIgraca;

    public PrikazPloce() {
        this.kreatorKartice = new KreatorKartice();
        this.listaOznakaPoIgracu = new ArrayList<>();
        this.listaKarticaIgraca = new ArrayList<>();
    }

    public HBox napraviDrzavnuPlocu(List<KlasaIgraca> listaIgraca) {
        HBox plocaIgre = new HBox(20);
        plocaIgre.setPadding(new Insets(30));
        plocaIgre.setBackground(new Background(new BackgroundFill(Color.web(BOJA_PODLOGE), CornerRadii.EMPTY, Insets.EMPTY)));

        listaOznakaPoIgracu.clear();
        listaKarticaIgraca.clear();

        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            VBox karticaIgraca = napraviKarticuIgraca(listaIgraca.get(brojac));
            listaKarticaIgraca.add(karticaIgraca);
            plocaIgre.getChildren().add(karticaIgraca);
            brojac = brojac + 1;
        }

        return plocaIgre;
    }

    private VBox napraviKarticuIgraca(KlasaIgraca igrac) {
        VBox kartica = new VBox(10);
        kreatorKartice.postaviOkvirIVanjskiStil(kartica, igrac.isNaPotezu());

        VBox zaglavljeKartice = kreatorKartice.napraviZaglavljeKartice(igrac);

        List<Label> oznakePodataka = napraviOznakePodataka(igrac);
        VBox sadrzajKartice = new VBox(8);
        sadrzajKartice.setPadding(new Insets(15, 18, 0, 18));
        sadrzajKartice.getChildren().addAll(oznakePodataka);

        VBox miniGraf = napraviMiniGrafZaIgraca(igrac);
        sadrzajKartice.getChildren().add(miniGraf);

        Label oznakaBodova = kreatorKartice.napraviOznakuBodova(igrac);
        oznakePodataka.add(oznakaBodova);
        sadrzajKartice.getChildren().add(oznakaBodova);

        kartica.getChildren().addAll(zaglavljeKartice, sadrzajKartice);
        listaOznakaPoIgracu.add(oznakePodataka);
        return kartica;
    }

    private VBox napraviMiniGrafZaIgraca(KlasaIgraca igrac) {
        if (igrac instanceof RadnickaKlasa) {
            RadnickaKlasa radnickaKlasa = (RadnickaKlasa) igrac;
            return kreatorKartice.napraviMiniGraf(igrac, radnickaKlasa.getStandardZivota(), 100, "Standard zivota");
        } else if (igrac instanceof SrednjaKlasa) {
            SrednjaKlasa srednjaKlasa = (SrednjaKlasa) igrac;
            return kreatorKartice.napraviMiniGraf(igrac, srednjaKlasa.getStandardZivota(), 100, "Standard zivota");
        } else if (igrac instanceof KapitalistickaKlasa) {
            KapitalistickaKlasa kapitalist = (KapitalistickaKlasa) igrac;
            return kreatorKartice.napraviMiniGraf(igrac, kapitalist.getUkupniKapital(), 400, "Ukupni kapital");
        } else {
            Vlada vlada = (Vlada) igrac;
            return kreatorKartice.napraviMiniGraf(igrac, vlada.getLegitimnost(), 100, "Legitimnost");
        }
    }

    private List<Label> napraviOznakePodataka(KlasaIgraca igrac) {
        List<Label> oznake = new ArrayList<>();

        if (igrac instanceof RadnickaKlasa) {
            RadnickaKlasa radnickaKlasa = (RadnickaKlasa) igrac;
            oznake.add(kreatorKartice.napraviOznaku("Standard zivota: " + radnickaKlasa.getStandardZivota()));
            oznake.add(kreatorKartice.napraviOznaku("Kolicina hrane: " + radnickaKlasa.getKolicinaHrane()));
            oznake.add(kreatorKartice.napraviOznaku("Zaposleni: " + radnickaKlasa.getZaposleniRadnici() + " / " + radnickaKlasa.getBrojRadnika()));
            oznake.add(kreatorKartice.napraviOznaku("U strajku: " + (radnickaKlasa.isJeUStrajku() ? "DA" : "Ne")));
        } else if (igrac instanceof SrednjaKlasa) {
            SrednjaKlasa srednjaKlasa = (SrednjaKlasa) igrac;
            oznake.add(kreatorKartice.napraviOznaku("Standard zivota: " + srednjaKlasa.getStandardZivota()));
            oznake.add(kreatorKartice.napraviOznaku("Poduzeca: " + srednjaKlasa.getBrojMalihPoduzeca()));
            oznake.add(kreatorKartice.napraviOznaku("Kapital: " + String.format("%.2f", srednjaKlasa.getUstedjeniKapital())));
        } else if (igrac instanceof KapitalistickaKlasa) {
            KapitalistickaKlasa kapitalistickaKlasa = (KapitalistickaKlasa) igrac;
            oznake.add(kreatorKartice.napraviOznaku("Kapital: " + String.format("%.2f", kapitalistickaKlasa.getUkupniKapital())));
            oznake.add(kreatorKartice.napraviOznaku("Tvornice: " + kapitalistickaKlasa.getBrojTvornica()));
            oznake.add(kreatorKartice.napraviOznaku("Dionice: " + String.format("%.2f", kapitalistickaKlasa.getVrijednostDionica())));
        } else {
            Vlada vlada = (Vlada) igrac;
            oznake.add(kreatorKartice.napraviOznaku("Proracun: " + String.format("%.2f", vlada.getDrzavniProracun())));
            oznake.add(kreatorKartice.napraviOznaku("Stopa poreza: " + String.format("%.2f", vlada.getStopaPoreza())));
            oznake.add(kreatorKartice.napraviOznaku("Min. placa: " + String.format("%.2f", vlada.getMinimalnaPlaca())));
            oznake.add(kreatorKartice.napraviOznaku("Legitimnost: " + vlada.getLegitimnost()));
        }
        return oznake;
    }

    public void azurirajPrikaz(List<KlasaIgraca> listaIgraca) {
        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            KlasaIgraca igrac = listaIgraca.get(brojac);
            List<Label> oznakePodataka = listaOznakaPoIgracu.get(brojac);
            List<Label> novePodatkovneOznake = napraviOznakePodataka(igrac);

            int drugiBrojac = 0;
            while (drugiBrojac < oznakePodataka.size() - 1 && drugiBrojac < novePodatkovneOznake.size()) {
                oznakePodataka.get(drugiBrojac).setText(novePodatkovneOznake.get(drugiBrojac).getText());
                drugiBrojac = drugiBrojac + 1;
            }
            oznakePodataka.get(oznakePodataka.size() - 1).setText("BODOVI: " + igrac.getBodoviPobjede());
            kreatorKartice.postaviOkvirIVanjskiStil(listaKarticaIgraca.get(brojac), igrac.isNaPotezu());
            brojac = brojac + 1;
        }
    }
}