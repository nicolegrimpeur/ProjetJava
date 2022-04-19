package screensControllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import menuBoissons.BoissonsManager;
import menuPlats.PlatsManager;

public class Serveurs {
    @FXML
    public ListView<String> listPlats;
    @FXML
    public ListView<String> listBoissons;

    @FXML
    void initialize() {
        ajouterMenu();
    }

    private void ajouterMenu() {
        listPlats.setItems(FXCollections.observableArrayList(PlatsManager.getInstance().listNoms.keySet()));
        listBoissons.setItems(FXCollections.observableArrayList(BoissonsManager.getInstance().listNoms.keySet()));
        listPlats.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listBoissons.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    // TODO : lorsque l'on clique sur un plat, ça l'ajoute au panier
    private void clickPlat() {

    }

    // TODO : lorsque l'on clique sur une boisson, ça l'ajoute au panier
    private void clickBoisson() {

    }

    // TODO : lorsque l'on click sur un élément du panier, ça le supprime
    private void clickPanier() {

    }

    // TODO : lors du click sur submit, on envoie la commande sur les écrans barmans et cuisines
    public void submit() {
        Stage stage = (Stage) listBoissons.getScene().getWindow();
        stage.close();
    }
}
