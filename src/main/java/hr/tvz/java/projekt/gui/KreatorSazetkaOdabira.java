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

    private static final String FX_FONT_VERDANA = "-fx-font-family: 'Verdana'; -fx-font-size: 12px; ";

    public Label napraviOznakuBrojaca() {
        Label oznaka = new Label();
        oznaka.setStyle(FX_FONT_VERDANA + "-fx-font-weight: bold; -fx-text-fill: #00D4FF;");
        return oznaka;
    }

    public Label napraviOznakuSazetka() {
        Label oznaka = new Label();
        oznaka.setStyle(FX_FONT_VERDANA + "-fx-text-fill: " + StilGumba.TEKST_SVIJETLI + ";");
        oznaka.setWrapText(true);
        return oznaka;
    }

    public void azurirajBrojacPopunjenosti(Label oznakaBrojaca, int odabraniBrojIgraca) {
        oznakaBrojaca.setText("POSTAVKE ODABRANE ZA " + odabraniBrojIgraca + " / " + odabraniBrojIgraca + " IGRACA");
    }

    // Riješeno Sonar upozorenje korištenjem StringBuilder-a i elegantnije for petlje
    public void azurirajSazetak(Label oznakaSazetka, List<String> odabraneUlogePoPoziciji) {
        StringBuilder sazetakBuilder = new StringBuilder();

        for (int i = 0; i < odabraneUlogePoPoziciji.size(); i++) {
            sazetakBuilder.append("Igrac ").append(i + 1).append(": ").append(odabraneUlogePoPoziciji.get(i));
            if (i < odabraneUlogePoPoziciji.size() - 1) {
                sazetakBuilder.append("   |   ");
            }
        }

        oznakaSazetka.setText(sazetakBuilder.toString());
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
        return new ListCell<>() {
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