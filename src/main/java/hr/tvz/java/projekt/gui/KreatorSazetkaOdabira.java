package hr.tvz.java.projekt.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import java.util.List;

public class KreatorSazetkaOdabira {

    public Label napraviOznakuBrojaca() {
        Label oznaka = new Label();
        oznaka.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #4A4438;");
        return oznaka;
    }

    public Label napraviOznakuSazetka() {
        Label oznaka = new Label();
        oznaka.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 12px; -fx-text-fill: #2B2520;");
        oznaka.setWrapText(true);
        return oznaka;
    }

    public void azurirajBrojacPopunjenosti(Label oznakaBrojaca, int odabraniBrojIgraca) {
        oznakaBrojaca.setText("Postavke odabrane za " + odabraniBrojIgraca + " / " + odabraniBrojIgraca + " igraca.");
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
        Stop[] tockeBoje = {
                new Stop(0, Color.web("#EFE7D8")),
                new Stop(0.5, Color.web("#E3D6BE")),
                new Stop(1, Color.web("#D8C9A8"))
        };
        LinearGradient gradijent = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, tockeBoje);
        return new Background(new BackgroundFill(gradijent, CornerRadii.EMPTY, Insets.EMPTY));
    }

    public HBox napraviPanelBrojaIgraca(int pocetnaVrijednost, java.util.function.IntConsumer akcijaPromjene) {
        HBox panel = new HBox(12);
        panel.setAlignment(Pos.CENTER);

        Label oznaka = new Label("Broj igraca:");
        oznaka.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 14px; -fx-text-fill: #2B2520;");

        ComboBox<Integer> izbornikBrojaIgraca = new ComboBox<>();
        izbornikBrojaIgraca.getItems().addAll(2, 3, 4);
        izbornikBrojaIgraca.setValue(pocetnaVrijednost);
        izbornikBrojaIgraca.setOnAction(dogadjaj -> akcijaPromjene.accept(izbornikBrojaIgraca.getValue()));

        panel.getChildren().addAll(oznaka, izbornikBrojaIgraca);
        return panel;
    }
}