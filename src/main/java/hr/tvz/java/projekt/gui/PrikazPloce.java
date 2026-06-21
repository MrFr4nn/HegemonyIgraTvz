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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class PrikazPloce {

    private static final String BOJA_PODLOGE = "#EFE7D8";

    private KreatorKartice kreatorKartice;
    private List<List<Label>> listaOznakaPoIgracu;

    public PrikazPloce() {
        this.kreatorKartice = new KreatorKartice();
        this.listaOznakaPoIgracu = new ArrayList<>();
    }

    public GridPane napraviDrzavnuPlocu(List<KlasaIgraca> listaIgraca) {
        GridPane plocaIgre = new GridPane();
        plocaIgre.setHgap(18);
        plocaIgre.setVgap(18);
        plocaIgre.setPadding(new Insets(25));
        plocaIgre.setBackground(new Background(new BackgroundFill(Color.web(BOJA_PODLOGE), CornerRadii.EMPTY, Insets.EMPTY)));

        Label naslovPloce = new Label("DRZAVNA PLOCA");
        naslovPloce.setFont(Font.font("Georgia", FontWeight.BOLD, 26));
        naslovPloce.setTextFill(Color.web("#2B2520"));
        plocaIgre.add(naslovPloce, 0, 0, listaIgraca.size(), 1);

        listaOznakaPoIgracu.clear();

        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            VBox karticaIgraca = napraviKarticuIgraca(listaIgraca.get(brojac));
            plocaIgre.add(karticaIgraca, brojac, 1);
            brojac = brojac + 1;
        }

        return plocaIgre;
    }

    private VBox napraviKarticuIgraca(KlasaIgraca igrac) {
        VBox kartica = new VBox(10);
        kreatorKartice.postaviOkvirIVanjskiStil(kartica);

        VBox zaglavljeKartice = kreatorKartice.napraviZaglavljeKartice(igrac);

        List<Label> oznakePodataka = napraviOznakePodataka(igrac);
        VBox sadrzajKartice = new VBox(7);
        sadrzajKartice.setPadding(new Insets(12, 15, 0, 15));
        sadrzajKartice.getChildren().addAll(oznakePodataka);

        Label oznakaBodova = kreatorKartice.napraviOznakuBodova(igrac);
        oznakePodataka.add(oznakaBodova);
        sadrzajKartice.getChildren().add(oznakaBodova);

        kartica.getChildren().addAll(zaglavljeKartice, sadrzajKartice);
        listaOznakaPoIgracu.add(oznakePodataka);
        return kartica;
    }

    private List<Label> napraviOznakePodataka(KlasaIgraca igrac) {
        List<Label> oznake = new ArrayList<>();

        if (igrac instanceof RadnickaKlasa) {
            RadnickaKlasa radnickaKlasa = (RadnickaKlasa) igrac;
            oznake.add(kreatorKartice.napraviOznaku("Standard zivota: " + radnickaKlasa.getStandardZivota()));
            oznake.add(kreatorKartice.napraviOznaku("Kolicina hrane: " + radnickaKlasa.getKolicinaHrane()));
            oznake.add(kreatorKartice.napraviOznaku("Zaposleni: " + radnickaKlasa.getZaposleniRadnici() + " / " + radnickaKlasa.getBrojRadnika()));
        } else if (igrac instanceof SrednjaKlasa) {
            SrednjaKlasa srednjaKlasa = (SrednjaKlasa) igrac;
            oznake.add(kreatorKartice.napraviOznaku("Standard zivota: " + srednjaKlasa.getStandardZivota()));
            oznake.add(kreatorKartice.napraviOznaku("Poduzeca: " + srednjaKlasa.getBrojMalihPoduzeca()));
            oznake.add(kreatorKartice.napraviOznaku("Kapital: " + srednjaKlasa.getUstedjeniKapital()));
        } else if (igrac instanceof KapitalistickaKlasa) {
            KapitalistickaKlasa kapitalistickaKlasa = (KapitalistickaKlasa) igrac;
            oznake.add(kreatorKartice.napraviOznaku("Kapital: " + kapitalistickaKlasa.getUkupniKapital()));
            oznake.add(kreatorKartice.napraviOznaku("Tvornice: " + kapitalistickaKlasa.getBrojTvornica()));
            oznake.add(kreatorKartice.napraviOznaku("Dionice: " + kapitalistickaKlasa.getVrijednostDionica()));
        } else {
            Vlada vlada = (Vlada) igrac;
            oznake.add(kreatorKartice.napraviOznaku("Proracun: " + String.format("%.2f", vlada.getDrzavniProracun())));
            oznake.add(kreatorKartice.napraviOznaku("Stopa poreza: " + vlada.getStopaPoreza()));
            oznake.add(kreatorKartice.napraviOznaku("Min. placa: " + vlada.getMinimalnaPlaca()));
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
            brojac = brojac + 1;
        }
    }
}