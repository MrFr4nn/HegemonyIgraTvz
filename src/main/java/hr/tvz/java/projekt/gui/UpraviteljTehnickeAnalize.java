package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.util.Serijalizator;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UpraviteljTehnickeAnalize {

    private Serijalizator serijalizator;

    public UpraviteljTehnickeAnalize(Serijalizator serijalizator) {
        this.serijalizator = serijalizator;
    }

    public void otvoriProzorAnalize() {
        Stage prozorAnalize = new Stage();
        prozorAnalize.setTitle("Tehnicka usporedba klasa (Reflection)");

        VBox korijenskiLayout = new VBox(10);
        korijenskiLayout.setPadding(new Insets(15));

        TextArea poljeTeksta = new TextArea();
        poljeTeksta.setEditable(false);
        poljeTeksta.setWrapText(true);
        poljeTeksta.setPrefHeight(450);

        String tekstAnalize = serijalizator.napraviTehnickuUsporedbu();
        poljeTeksta.setText(tekstAnalize);

        Button gumbZatvori = new Button("Zatvori prozor");
        gumbZatvori.setOnAction(dogadjaj -> prozorAnalize.close());

        korijenskiLayout.getChildren().addAll(poljeTeksta, gumbZatvori);

        Scene scenaAnalize = new Scene(korijenskiLayout, 550, 520);
        prozorAnalize.setScene(scenaAnalize);
        prozorAnalize.show();
    }
}