package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.model.KapitalistickaKlasa;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.SrednjaKlasa;
import hr.tvz.java.projekt.model.Vlada;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

    private List<VBox> listaKarticaIgraca;
    private List<List<Label>> listaOznakaPoIgracu;

    public PrikazPloce() {
        this.listaKarticaIgraca = new ArrayList<>();
        this.listaOznakaPoIgracu = new ArrayList<>();
    }

    public GridPane napraviDrzavnuPlocu(List<KlasaIgraca> listaIgraca) {
        GridPane plocaIgre = new GridPane();
        plocaIgre.setHgap(15);
        plocaIgre.setVgap(15);
        plocaIgre.setPadding(new Insets(20));
        plocaIgre.setBackground(new Background(new BackgroundFill(Color.web("#2b3a42"), CornerRadii.EMPTY, Insets.EMPTY)));

        Label naslovPloce = new Label("DRZAVNA PLOCA - HEGEMONY TVZ");
        naslovPloce.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        naslovPloce.setTextFill(Color.GOLD);
        plocaIgre.add(naslovPloce, 0, 0, listaIgraca.size(), 1);

        listaKarticaIgraca.clear();
        listaOznakaPoIgracu.clear();

        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            VBox karticaIgraca = napraviKarticuIgraca(listaIgraca.get(brojac));
            listaKarticaIgraca.add(karticaIgraca);
            plocaIgre.add(karticaIgraca, brojac, 1);
            brojac = brojac + 1;
        }

        return plocaIgre;
    }

    private VBox napraviKarticuIgraca(KlasaIgraca igrac) {
        VBox kartica = new VBox(8);
        kartica.setPadding(new Insets(15));
        kartica.setAlignment(Pos.TOP_LEFT);
        kartica.setBackground(new Background(new BackgroundFill(odrediBojuPodloge(igrac), new CornerRadii(10), Insets.EMPTY)));

        Label naslovKartice = new Label(igrac.getNaziv());
        naslovKartice.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        naslovKartice.setTextFill(odrediBojuNaslova(igrac));

        List<Label> oznakePodataka = napraviOznakePodataka(igrac);
        Label oznakaBodova = new Label("Bodovi: " + igrac.getBodoviPobjede());
        oznakaBodova.setTextFill(Color.WHITE);
        oznakePodataka.add(oznakaBodova);

        kartica.getChildren().add(naslovKartice);
        kartica.getChildren().addAll(oznakePodataka);

        listaOznakaPoIgracu.add(oznakePodataka);
        return kartica;
    }

    private List<Label> napraviOznakePodataka(KlasaIgraca igrac) {
        List<Label> oznake = new ArrayList<>();

        if (igrac instanceof RadnickaKlasa) {
            RadnickaKlasa radnickaKlasa = (RadnickaKlasa) igrac;
            oznake.add(napraviOznaku("Standard zivota: " + radnickaKlasa.getStandardZivota()));
            oznake.add(napraviOznaku("Kolicina hrane: " + radnickaKlasa.getKolicinaHrane()));
            oznake.add(napraviOznaku("Zaposleni: " + radnickaKlasa.getZaposleniRadnici() + " / " + radnickaKlasa.getBrojRadnika()));
        } else if (igrac instanceof SrednjaKlasa) {
            SrednjaKlasa srednjaKlasa = (SrednjaKlasa) igrac;
            oznake.add(napraviOznaku("Standard zivota: " + srednjaKlasa.getStandardZivota()));
            oznake.add(napraviOznaku("Poduzeca: " + srednjaKlasa.getBrojMalihPoduzeca()));
            oznake.add(napraviOznaku("Kapital: " + srednjaKlasa.getUstedjeniKapital()));
        } else if (igrac instanceof KapitalistickaKlasa) {
            KapitalistickaKlasa kapitalistickaKlasa = (KapitalistickaKlasa) igrac;
            oznake.add(napraviOznaku("Kapital: " + kapitalistickaKlasa.getUkupniKapital()));
            oznake.add(napraviOznaku("Tvornice: " + kapitalistickaKlasa.getBrojTvornica()));
            oznake.add(napraviOznaku("Dionice: " + kapitalistickaKlasa.getVrijednostDionica()));
        } else {
            Vlada vlada = (Vlada) igrac;
            oznake.add(napraviOznaku("Proracun: " + String.format("%.2f", vlada.getDrzavniProracun())));
            oznake.add(napraviOznaku("Stopa poreza: " + vlada.getStopaPoreza()));
            oznake.add(napraviOznaku("Min. placa: " + vlada.getMinimalnaPlaca()));
        }
        return oznake;
    }

    private Label napraviOznaku(String tekst) {
        Label oznaka = new Label(tekst);
        oznaka.setTextFill(Color.WHITE);
        oznaka.setFont(Font.font("Arial", 12));
        return oznaka;
    }

    private Color odrediBojuPodloge(KlasaIgraca igrac) {
        if (igrac instanceof RadnickaKlasa) {
            return Color.web("#52423a");
        } else if (igrac instanceof SrednjaKlasa) {
            return Color.web("#3a523a");
        } else if (igrac instanceof KapitalistickaKlasa) {
            return Color.web("#52473a");
        } else {
            return Color.web("#3a4a52");
        }
    }

    private Color odrediBojuNaslova(KlasaIgraca igrac) {
        if (igrac instanceof RadnickaKlasa) {
            return Color.ORANGE;
        } else if (igrac instanceof SrednjaKlasa) {
            return Color.LIGHTGREEN;
        } else if (igrac instanceof KapitalistickaKlasa) {
            return Color.GOLD;
        } else {
            return Color.LIGHTBLUE;
        }
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
            oznakePodataka.get(oznakePodataka.size() - 1).setText("Bodovi: " + igrac.getBodoviPobjede());
            brojac = brojac + 1;
        }
    }
}