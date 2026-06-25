package hr.tvz.java.projekt.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.List;

public class KreatorSazetkaOdabira {

    public Label napraviOznakuBrojaca() {
        Label oznaka = new Label();
        oznaka.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #00D4FF;");
        return oznaka;
    }

    public Label napraviOznakuSazetka() {
        Label oznaka = new Label();
        oznaka.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 12px; -fx-text-fill: " + StilGumba.TEKST_SVIJETLI + ";");
        oznaka.setWrapText(true);
        return oznaka;
    }

    public void azurirajBrojacPopunjenosti(Label oznakaBrojaca, int odabraniBrojIgraca) {
        oznakaBrojaca.setText("POSTAVKE ODABRANE ZA " + odabraniBrojIgraca + " / " + odabraniBrojIgraca + " IGRACA");
    }

    public void azurirajSazetak(Label oznakaSazetka, List<String> odabraneUlogePoPoziciji) {
        String sazetak = "";
        int brojac = 0;
        while (brojac < odabraneUlogePoPoziciji.size()) {
            sazetak = sazetak + "Igrac " + (brojac + 1) + ": " + odabraneUlogePoPoziciji.get(brojac);
            if (brojac < odabraneUlogePoPoziciji.size() - 1) {
                sazetak = sazetak + "   |   ";
            }
            brojac = brojac + 1;
        }
        oznakaSazetka.setText(sazetak);
    }

    public Background napraviGradijentnuPodlogu() {
        return new Background(new BackgroundFill(Color.web(StilGumba.POZADINA_TAMNA), CornerRadii.EMPTY, Insets.EMPTY));
    }

    public HBox napraviPanelBrojaIgraca(int pocetnaVrijednost, java.util.function.IntConsumer akcijaPromjene) {
        HBox panel = new HBox(12);
        panel.setAlignment(Pos.CENTER);

        Label oznaka = new Label("BROJ IGRACA:");
        oznaka.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: "
                + StilGumba.TEKST_SVIJETLI + ";");

        ComboBox<Integer> izbornikBrojaIgraca = new ComboBox<>();
        izbornikBrojaIgraca.getItems().addAll(2, 3, 4);
        izbornikBrojaIgraca.setValue(pocetnaVrijednost);
        izbornikBrojaIgraca.setStyle(
                "-fx-background-color: " + StilGumba.POVRSINA_TAMNA + "; "
                        + "-fx-border-color: #00D4FF; -fx-border-width: 2; -fx-border-radius: 4; -fx-background-radius: 4; "
                        + "-fx-mark-color: #00D4FF; -fx-text-fill: " + StilGumba.TEKST_SVIJETLI + "; "
                        + "-fx-font-size: 13px; -fx-font-weight: bold;"
        );
        izbornikBrojaIgraca.setCellFactory(listaIzbornika -> napraviCelijuBrojaIgraca());
        izbornikBrojaIgraca.setButtonCell(napraviCelijuBrojaIgraca());
        izbornikBrojaIgraca.setOnAction(dogadjaj -> akcijaPromjene.accept(izbornikBrojaIgraca.getValue()));

        panel.getChildren().addAll(oznaka, izbornikBrojaIgraca);
        return panel;
    }

    private ListCell<Integer> napraviCelijuBrojaIgraca() {
        return new ListCell<Integer>() {
            @Override
            protected void updateItem(Integer broj, boolean prazno) {
                super.updateItem(broj, prazno);
                if (prazno || broj == null) {
                    setText(null);
                } else {
                    setText(String.valueOf(broj));
                    setStyle("-fx-text-fill: " + StilGumba.TEKST_SVIJETLI + "; -fx-background-color: "
                            + StilGumba.POVRSINA_TAMNA + ";");
                }
            }
        };
    }
}