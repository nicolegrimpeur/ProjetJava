package screensControllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import menuBoissons.BoissonsManager;
import menuPlats.PlatsManager;

public class CommandeClassique {
    @FXML
    public ChoiceBox<String> choixPlats;
    @FXML public ChoiceBox<String> choixBoissons;

    @FXML void initialize() {
        ajouterMenu();
    }

    private void ajouterMenu() {
        choixPlats.setItems(FXCollections.observableArrayList(PlatsManager.getInstance().listNoms.keySet()));
        choixBoissons.setItems(FXCollections.observableArrayList(BoissonsManager.getInstance().listNoms.keySet()));
    }
}
