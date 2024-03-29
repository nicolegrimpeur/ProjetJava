package screensControllers;

import files.HtmlManager;
import files.ImpressionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.text.Text;
import journee.JourneeManager;
import menu.Menu;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;


public class Additions {
    @FXML
    Button btnImprimer;
    @FXML
    Button btnPayer;
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
            HtmlManager.createDirectories();

        tableAdditions.setItems(tabAddition);
    }

    /**
     * Permet d'initialiser les colonnes de la tree table view
     */
    private void initColonnes() {
        TreeTableColumn<Menu, String> platsCol = new TreeTableColumn<>("Plats");
        platsCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("plat"));
        platsCol.setPrefWidth(225);

        // permet un retour à la ligne pour les plats
        setCellFactory(platsCol);

        treeTableViewAddition.getColumns().set(0, platsCol);

        treeTableViewAddition.getColumns().get(1).setCellValueFactory(new TreeItemPropertyValueFactory<>("boisson"));
        treeTableViewAddition.getColumns().get(2).setCellValueFactory(new TreeItemPropertyValueFactory<>("prix"));
    }

    public static void setCellFactory(TreeTableColumn<Menu, String> platsCol) {
        platsCol.setCellFactory(tc -> {
            TreeTableCell<Menu, String> cell = new TreeTableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            text.wrappingWidthProperty().bind(platsCol.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
    }

    /**
     * Affiche l'addition nécessaire au click sur la table d'addition
     */
    public void clickTable() {
        String itemSelect = tableAdditions.getSelectionModel().getSelectedItem();
        if (itemSelect != null) {
            TreeItem<Menu> rootItem = new TreeItem<>(new Menu());
            rootItem.setExpanded(true);

            // table des menus classiques
            TreeItem<Menu> menuItem = new TreeItem<>(new Menu("Menus classiques"));
            menuItem.setExpanded(true);
            for (Menu menu : JourneeManager.getInstance().getMapAdditions().get(itemSelect))
                // si le menu possède un prix
                if (!menu.getPrix().equals(""))
                    menuItem.getChildren().add(new TreeItem<>(menu));

            if (menuItem.getChildren().size() != 0)
                rootItem.getChildren().addAll(menuItem);

            // table des menus cent ans
            TreeItem<Menu> menuCentAnsItem = null;
            ArrayList<Menu> tabMenusServeurs = JourneeManager.getInstance().getMapAdditions().get(itemSelect);
            for (Menu menu : tabMenusServeurs)
                // si le menu ne possède pas de prix
                if (menu.getPrix().equals("")) {
                    // ajoute l'item s'il a ses septs éléments
                    if (menuCentAnsItem != null && menuCentAnsItem.getChildren().size() % 7 == 0)
                        rootItem.getChildren().add(menuCentAnsItem);

                    // on crée un nouvel item si celui ci est null ou s'il a 7 éléments
                    if (menuCentAnsItem == null || menuCentAnsItem.getChildren().size() % 7 == 0) {
                        menuCentAnsItem = new TreeItem<>(new Menu("Menu 100 ans", "", "100"));
                        menuCentAnsItem.setExpanded(true);
                    }

                    menuCentAnsItem.getChildren().add(new TreeItem<>(menu));
                }

            // si la table n'a pas encore été affiché
            if (menuCentAnsItem != null && menuCentAnsItem.getChildren().size() != 0)
                rootItem.getChildren().add(menuCentAnsItem);

            // on affiche le prix total
            Map<String, Double> mapSorti = JourneeManager.getInstance().calculTva(tabMenusServeurs);
            TreeItem<Menu> itemPrixTotal = new TreeItem<>();
            rootItem.getChildren().add(itemPrixTotal);
            itemPrixTotal = new TreeItem<>(new Menu("", "Total Hors Taxe",  mapSorti.get("Prix hors taxes") + "€"));
            rootItem.getChildren().add(itemPrixTotal);
            itemPrixTotal = new TreeItem<>(new Menu("", "Total", mapSorti.get("Prix total") + "€"));
            rootItem.getChildren().add(itemPrixTotal);
            itemPrixTotal = new TreeItem<>(new Menu("", "", (JourneeManager.getInstance().getIsPaye(itemSelect) ? "Déjà payé" : "Pas encore payé")));
            rootItem.getChildren().add(itemPrixTotal);

            // on affiche les lignes
            treeTableViewAddition.setRoot(rootItem);
            treeTableViewAddition.setShowRoot(false); // permet de ne pas afficher le premier parent vide

            btnImprimer.setVisible(true);
            btnPayer.setVisible(true);
            btnPayer.setDisable(JourneeManager.getInstance().getIsPaye(itemSelect));
        }
    }

    /**
     * Permet d'imprimer l'addition sélectionnée
     */
    public void clickImpression() {
        String itemSelect = tableAdditions.getSelectionModel().getSelectedItem();
        if (itemSelect != null)
            ImpressionManager.imprimerFichier(HtmlManager.getAdditionOutput() + tableAdditions.getSelectionModel().getSelectedItem() + ".pdf");
    }

    /**
     * Gère le click sur le bouton "Marquer comme payé"
     * Permet de marquer une addition comme payé
     */
    public void clickBtnPayer() {
        String itemSelect = tableAdditions.getSelectionModel().getSelectedItem();
        if (itemSelect != null) {
            JourneeManager.getInstance().setIsPaye(itemSelect);

            clickTable();
        }
    }
}
