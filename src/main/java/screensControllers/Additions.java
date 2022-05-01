package screensControllers;

import html.HtmlManager;
import html.ImpressionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.File;


public class Additions {
    @FXML
    Button btnImprimer;
    @FXML
    ListView<String> tableAdditions;

    @FXML
    void initialize() {
        initTableAddition();
    }

    /**
     * Initialise la table d'affichage des additions
     */
    private void initTableAddition() {
        File dir = new File("Additions/");
        File[] liste = dir.listFiles();


        ObservableList<String> tabAddition = FXCollections.observableArrayList();
        if (liste != null) {
            if (liste.length != 0)
                for (int i = tabAddition.size(); i >= 0; i--)
                    tabAddition.add(liste[i].getName().replace(".pdf", ""));
        } else
            HtmlManager.getInstance().createDirectories();

        tableAdditions.setItems(tabAddition);
    }

    /**
     * Permet d'imprimer l'addition sélectionnée
     */
    public void clickImpression() {
        String itemSelect = tableAdditions.getSelectionModel().getSelectedItem();
        if (itemSelect != null)
            ImpressionManager.imprimerFichier(HtmlManager.getInstance().getAdditionOutput() + tableAdditions.getSelectionModel().getSelectedItem() + ".pdf");
    }
}
