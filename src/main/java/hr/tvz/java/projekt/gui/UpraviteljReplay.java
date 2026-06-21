package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.util.XmlUpravitelj;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class UpraviteljReplay {

    private XmlUpravitelj xmlUpravitelj;
    private List<String> listaPoteza;
    private int trenutnaPozicija;
    private Label oznakaTrenutnogPoteza;
    private ListView<String> prikazPovijesti;

    public UpraviteljReplay(XmlUpravitelj xmlUpravitelj) {
        this.xmlUpravitelj = xmlUpravitelj;
        this.trenutnaPozicija = 0;
    }

    public void otvoriProzorReplaya() {
        boolean formatIspravan = xmlUpravitelj.provjeriOsnovniFormat();
        if (!formatIspravan) {
            System.out.println("Upozorenje: XML format povijesti nije potpuno ispravan, ali pokusavamo prikazati replay.");
        }

        listaPoteza = xmlUpravitelj.ucitajPovijestZaReplay();
        trenutnaPozicija = 0;

        Stage prozorReplaya = new Stage();
        prozorReplaya.setTitle("Replay - Povijest Igre");

        VBox korijenskiLayout = new VBox(12);
        korijenskiLayout.setPadding(new Insets(15));

        Label naslov = new Label("Replay odigrane partije");
        oznakaTrenutnogPoteza = new Label("Pritisnite 'Sljedeci korak' za pocetak.");

        prikazPovijesti = new ListView<>();
        if (listaPoteza.isEmpty()) {
            prikazPovijesti.getItems().add("Nema zabiljezenih poteza u povijesti.");
        }

        Button gumbSljedeci = new Button("Sljedeci korak");
        Button gumbAutomatski = new Button("Automatski Replay");

        gumbSljedeci.setOnAction(dogadjaj -> prikaziSljedeciKorak());
        gumbAutomatski.setOnAction(dogadjaj -> pokreniAutomatskiReplay());

        HBox redGumbova = new HBox(10, gumbSljedeci, gumbAutomatski);

        korijenskiLayout.getChildren().addAll(naslov, oznakaTrenutnogPoteza, prikazPovijesti, redGumbova);

        Scene scenaReplaya = new Scene(korijenskiLayout, 500, 450);
        prozorReplaya.setScene(scenaReplaya);
        prozorReplaya.show();
    }

    private void prikaziSljedeciKorak() {
        if (listaPoteza == null || listaPoteza.isEmpty()) {
            oznakaTrenutnogPoteza.setText("Nema dostupnih poteza za prikaz.");
            return;
        }
        if (trenutnaPozicija < listaPoteza.size()) {
            String trenutniPotez = listaPoteza.get(trenutnaPozicija);
            oznakaTrenutnogPoteza.setText("Korak " + (trenutnaPozicija + 1) + ": " + trenutniPotez);
            prikazPovijesti.getItems().add(trenutniPotez);
            trenutnaPozicija = trenutnaPozicija + 1;
        } else {
            oznakaTrenutnogPoteza.setText("Replay je zavrsen, nema vise poteza.");
        }
    }

    private void pokreniAutomatskiReplay() {
        Thread nitAutomatskogReplaya = new Thread(() -> {
            while (trenutnaPozicija < listaPoteza.size()) {
                int pozicijaPrijeAzuriranja = trenutnaPozicija;
                Platform.runLater(this::prikaziSljedeciKorak);
                try {
                    Thread.sleep(800);
                } catch (InterruptedException greska) {
                    System.out.println("Automatski replay je prekinut: " + greska.getMessage());
                    Thread.currentThread().interrupt();
                    break;
                }
                if (pozicijaPrijeAzuriranja == trenutnaPozicija) {
                    break;
                }
            }
        });
        nitAutomatskogReplaya.setDaemon(true);
        nitAutomatskogReplaya.start();
    }
}