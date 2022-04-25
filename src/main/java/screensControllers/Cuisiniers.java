package screensControllers;

import employee.Cuisinier;
import employee.Employee;
import employee.ManagEmployees;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import journee.JourneeManager;
import menu.Menu;
import status.EnumStatus;

import java.util.Objects;

public class Cuisiniers {

    @FXML
    public ChoiceBox<String> cuisinierChoiceBox;
    @FXML
    public TreeTableView<Menu> platsTreeTable;
    @FXML
    public Button btnEtatSuivant;
    @FXML
    public Button btnServi;
    public Cuisinier currentCuisinier = null;

    @FXML
    void initialize() {
        initCuisiniers();
        initColonnes();
        affichePlats();
    }

    private void initCuisiniers() {
        ObservableList<String> listCuisiniers = FXCollections.observableArrayList();

        // parcours tous les employés
        for (Employee employee : JourneeManager.getInstance().listEmployes)
            // si l'employé est un serveur, on l'ajoute
            if (employee instanceof Cuisinier)
                listCuisiniers.add(employee.getAffichage());

        cuisinierChoiceBox.setItems(listCuisiniers);
    }

    private void initColonnes() {
        // on crée les colonnes
        TreeTableColumn<Menu, String> platsCol = new TreeTableColumn<>("Plats");
        TreeTableColumn<Menu, String> statusCol = new TreeTableColumn<>("Status");

        // on leur assigne la valeur à laquelle chaque colonne correspond
        platsCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("plat"));
        statusCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("statusPlat"));

        // on set la taille de chaque colonne
        platsCol.setPrefWidth(450);
        statusCol.setPrefWidth(150);

        // on empêche le tri
        platsCol.setSortable(false);
        statusCol.setSortable(false);

        // on ajoute les colonnes
        platsTreeTable.getColumns().addAll(platsCol, statusCol);
    }

    private void affichePlats() {
        TreeItem<Menu> rootItem = new TreeItem<>(new Menu());
        rootItem.setExpanded(true);

        TreeItem<Menu> itemAffichageServeur, item;
        Menu affichageServeur;
        boolean auMoinsUnEnPreparation, everyStatusAServir, commandeTermine, enAttente;

        // parcours tous les employés
        for (String serveur : JourneeManager.getInstance().getListService().keySet()) {
            affichageServeur = new Menu(serveur, serveur);

            auMoinsUnEnPreparation = false;
            everyStatusAServir = true;
            commandeTermine = false;
            enAttente = false;
            for (Menu commande : JourneeManager.getInstance().getListService().get(serveur)) {
                if (Objects.equals(commande.getStatusPlat(), EnumStatus.ENCOURS.getAffichage()))
                    auMoinsUnEnPreparation = true;
                if (!Objects.equals(commande.getStatusPlat(), EnumStatus.ASERVIR.getAffichage()))
                    everyStatusAServir = false;
                if (Objects.equals(commande.getStatusPlat(), EnumStatus.TERMINE.getAffichage()))
                    commandeTermine = true;
            }

            if (everyStatusAServir)
                for (Menu menu : JourneeManager.getInstance().getListService().get(serveur)) {
                    if (!Objects.equals(menu.getStatusPlat(), EnumStatus.ASERVIR.getAffichage())) {
                        everyStatusAServir = false;
                        enAttente = true;
                        break;
                    }
                }

            if (auMoinsUnEnPreparation) affichageServeur.setStatusPlat(EnumStatus.ENCOURS.getAffichage());
            if (everyStatusAServir) affichageServeur.setStatusPlat(EnumStatus.ASERVIR.getAffichage());
            if (enAttente) affichageServeur.setStatusPlat(EnumStatus.ENATTENTE.getAffichage());
            if (commandeTermine) affichageServeur.setStatusPlat(EnumStatus.TERMINE.getAffichage());

            itemAffichageServeur = new TreeItem<>(affichageServeur);
            itemAffichageServeur.setExpanded(true);

            // on ajoute une ligne pour chaque menu
            for (Menu commande : JourneeManager.getInstance().getListService().get(serveur)) {
                item = new TreeItem<>(commande);
                itemAffichageServeur.getChildren().add(item);
            }

            rootItem.getChildren().add(itemAffichageServeur);
        }

        // on affiche les lignes
        platsTreeTable.setRoot(rootItem);
        platsTreeTable.setShowRoot(false); // permet de ne pas afficher le premier parent vide
    }

    /**
     * Récupère le cuisinier sélectionné
     */
    public void changeCuisinierChoiceBox() {
        if (cuisinierChoiceBox.getValue() != null) {
            for (Employee employee : ManagEmployees.getInstance().getListEmployes()) {
                if (employee.getAffichage().equals(cuisinierChoiceBox.getValue()))
                    currentCuisinier = (Cuisinier) employee;
            }
        }

        afficheBtnEtatSuivant();
    }

    private void afficheBtnEtatSuivant() {
        btnEtatSuivant.setVisible(true);
        btnEtatSuivant.setDisable(true);
    }

    public void clickTable() {
        if (platsTreeTable.getSelectionModel().getSelectedItem() != null) {
            // disable si tous les plats liés à un serveur peuvent être servi
            btnEtatSuivant.setDisable(
                    Objects.equals(platsTreeTable.getSelectionModel().getSelectedItem().getParent().getValue().getStatusPlat(), EnumStatus.ENATTENTE.getAffichage()) ||
                            Objects.equals(platsTreeTable.getSelectionModel().getSelectedItem().getValue().getStatusPlat(), EnumStatus.ASERVIR.getAffichage()) ||
                            platsTreeTable.getSelectionModel().getSelectedItem().getParent() == platsTreeTable.getRoot());

            // visible si l'on clique sur un serveur et que tous les plats peuvent être servis
            btnServi.setVisible(
                    Objects.equals(platsTreeTable.getSelectionModel().getSelectedItem().getValue().getStatusPlat(), EnumStatus.ASERVIR.getAffichage()) &&
                            platsTreeTable.getSelectionModel().getSelectedItem().getParent() == platsTreeTable.getRoot());
        }
    }

    public void clickBtnEtatSuivant() {
        // on récupère l'élément sélectionné ainsi que son parent
        TreeItem<Menu> itemSelect = platsTreeTable.getSelectionModel().getSelectedItem();
        TreeItem<Menu> parent = itemSelect.getParent();

        int index = platsTreeTable.getSelectionModel().getSelectedIndex() - (parent.getParent().getChildren().indexOf(parent) + 1);

        JourneeManager.getInstance().nextStatusPlat(parent.getValue().getPlat(), index, currentCuisinier);

        affichePlats();
        btnEtatSuivant.setDisable(true);
    }

    public void clickBtnServi() {
        // on récupère l'élément sélectionné
        TreeItem<Menu> itemSelect = platsTreeTable.getSelectionModel().getSelectedItem();

        if (itemSelect != null)
            JourneeManager.getInstance().platTermine(itemSelect.getValue().getPlat());

        affichePlats();
        btnServi.setVisible(false);
    }
}
