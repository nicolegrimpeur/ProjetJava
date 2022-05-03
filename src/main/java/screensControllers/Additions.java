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
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import journee.JourneeManager;
import menu.Menu;

import java.io.File;


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

    /**
     * Permet d'initialiser les colonnes de la tree table view
     */
    private void initColonnes() {
        treeTableViewAddition.getColumns().get(0).setCellValueFactory(new TreeItemPropertyValueFactory<>("plat"));
        treeTableViewAddition.getColumns().get(1).setCellValueFactory(new TreeItemPropertyValueFactory<>("boisson"));
        treeTableViewAddition.getColumns().get(2).setCellValueFactory(new TreeItemPropertyValueFactory<>("prix"));
    }

    /**
     * Affiche l'addition nécessaire au click sur la table d'addition
     */
    public void clickTable() {
        String itemSelect = tableAdditions.getSelectionModel().getSelectedItem();
        if (itemSelect != null) {
            btnImprimer.setVisible(true);

            TreeItem<Menu> rootItem = new TreeItem<>(new Menu());
            rootItem.setExpanded(true);

            int prixTotal = 0;

            // table des menus classiques
            TreeItem<Menu> menuItem = new TreeItem<>(new Menu("Menus classiques"));
            menuItem.setExpanded(true);
            for (Menu menu : JourneeManager.getInstance().getMapAdditions().get(itemSelect))
                // si le menu possède un prix
                if (!menu.getPrix().equals("")) {
                    menuItem.getChildren().add(new TreeItem<>(menu));
                    prixTotal += Integer.parseInt(menu.getPrix());
                }

            if (menuItem.getChildren().size() != 0)
                rootItem.getChildren().addAll(menuItem);

            // table des menus cent ans
            TreeItem<Menu> menuCentAnsItem = null;
            for (Menu menu : JourneeManager.getInstance().getMapAdditions().get(itemSelect))
                // si le menu ne possède pas de prix
                if (menu.getPrix().equals("")) {
                    // ajoute l'item s'il a ses septs éléments
                    if (menuCentAnsItem != null && menuCentAnsItem.getChildren().size() % 7 == 0)
                        rootItem.getChildren().add(menuCentAnsItem);

                    // on crée un nouvel item si celui ci est null ou s'il a 7 éléments
                    if (menuCentAnsItem == null || menuCentAnsItem.getChildren().size() % 7 == 0) {
                        menuCentAnsItem = new TreeItem<>(new Menu("Menu 100 ans", "", "100"));
                        menuCentAnsItem.setExpanded(true);
                        prixTotal += 100;
                    }

                    menuCentAnsItem.getChildren().add(new TreeItem<>(menu));
                }

            // si la table n'a pas encore été affiché
            if (menuCentAnsItem != null && menuCentAnsItem.getChildren().size() != 0)
                rootItem.getChildren().add(menuCentAnsItem);

            // on affiche le prix total
            TreeItem<Menu> itemPrixTotal = new TreeItem<>();
            rootItem.getChildren().add(itemPrixTotal);
            itemPrixTotal = new TreeItem<>(new Menu("", "Total", prixTotal + "€"));
            rootItem.getChildren().add(itemPrixTotal);

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
