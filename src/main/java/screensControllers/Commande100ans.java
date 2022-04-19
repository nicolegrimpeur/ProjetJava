package screensControllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import menuBoissons.BoissonsManager;
import menuPlats.PlatsManager;

public class Commande100ans {
    @FXML private Button ajouterBtn;


    @FXML void initialize() {
        AnchorPane root = (AnchorPane) ajouterBtn.getParent();
        Label labelPlat = new Label();
        Label labelBoisson = new Label();
        ChoiceBox<String> choiceBoxPlats = new ChoiceBox<>(FXCollections.observableArrayList(PlatsManager.getInstance().listNoms.keySet()));
        ChoiceBox<String> choiceBoxBoissons = new ChoiceBox<>(FXCollections.observableArrayList(BoissonsManager.getInstance().listNoms.keySet()));

        for (int i = 0; i < 7; i++) {
            root.setPadding(new Insets(20));
            labelPlat.setText("Plat " + i + " : ");
            labelBoisson.setText("Boisson " + i + " : ");

            root.getChildren().addAll(labelPlat, choiceBoxPlats, labelBoisson, choiceBoxBoissons);
        }
    }
}
