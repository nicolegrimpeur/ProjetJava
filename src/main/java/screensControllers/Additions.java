package screensControllers;

import html.HtmlManager;
import html.ImpressionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import journee.JourneeManager;
import menu.Menu;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;


public class Additions {
    @FXML
    Button btnImprimer;
    @FXML
    ListView<String> tableAdditions;
    @FXML
    TreeTableView<Menu> treeTableViewAddition;

    @FXML
    void initialize() {
        initTableAddition();
        initColonnes();
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

    private void initColonnes() {
        treeTableViewAddition.getColumns().get(0).setCellValueFactory(new TreeItemPropertyValueFactory<>("plat"));
        treeTableViewAddition.getColumns().get(1).setCellValueFactory(new TreeItemPropertyValueFactory<>("boisson"));
        treeTableViewAddition.getColumns().get(2).setCellValueFactory(new TreeItemPropertyValueFactory<>("prix"));
    }

    public void clickTable() {
        String itemSelect = tableAdditions.getSelectionModel().getSelectedItem();
        if (itemSelect != null) {
            btnImprimer.setVisible(true);

            TreeItem<Menu> rootItem = new TreeItem<>(new Menu());
            rootItem.setExpanded(true);

            // table des menus classiques
            TreeItem<Menu> menuItem = new TreeItem<>(new Menu("Menus classiques"));
            TreeItem<Menu> menuCentAnsItem = null;
            menuItem.setExpanded(true);

            for (Menu menu : JourneeManager.getInstance().getMapAdditions().get(itemSelect))
                if (!menu.getPrix().equals(""))
                    menuItem.getChildren().add(new TreeItem<>(menu));
            rootItem.getChildren().addAll(menuItem);

            for (Menu menu : JourneeManager.getInstance().getMapAdditions().get(itemSelect))
                if (menu.getPrix().equals("")) {
                    if (menuCentAnsItem != null && menuCentAnsItem.getChildren().size() / 7 == 0)
                        rootItem.getChildren().add(menuCentAnsItem);

                    if (menuCentAnsItem == null || menuCentAnsItem.getChildren().size() / 7 == 0) {
                        menuCentAnsItem = new TreeItem<>(new Menu("Menu 100 ans", "", "100"));
                        menuItem.setExpanded(true);
                    }

                    menuCentAnsItem.getChildren().add(new TreeItem<>(menu));

                }

            if (menuCentAnsItem != null && menuCentAnsItem.getChildren().size() != 0)
                rootItem.getChildren().add(menuCentAnsItem);

            // on affiche les lignes
            treeTableViewAddition.setRoot(rootItem);
            treeTableViewAddition.setShowRoot(false); // permet de ne pas afficher le premier parent vide

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
