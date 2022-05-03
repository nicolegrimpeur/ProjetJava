package screensControllers;

import html.HtmlManager;
import html.ImpressionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;

import java.io.File;


public class Additions {
    @FXML
    Button btnImprimer;
    @FXML
    ListView<String> tableAdditions;
    @FXML
    WebView webViewAddition;
    @FXML
    Pane pane;

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
                for (int i = liste.length - 1; i >= 0; i--)
                    tabAddition.add(liste[i].getName().replace(".pdf", ""));
        } else
            HtmlManager.getInstance().createDirectories();

        tableAdditions.setItems(tabAddition);
    }

    public void clickTable() {
        if (tableAdditions.getSelectionModel().getSelectedItem() != null) {
            webViewAddition.setVisible(true);
            btnImprimer.setVisible(true);

            String pathToPdf = "file:///" + System.getProperty("user.dir") + "/Additions/" + tableAdditions.getSelectionModel().getSelectedItem() + ".pdf";

            HtmlManager.getInstance().afficherPdf(webViewAddition, pathToPdf);
        }
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
