package screensControllers;

import employee.Barman;
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

public class BarmansEtCuisiniers {
    private String typeItem = null;
    @FXML
    public ChoiceBox<String> employeeChoiceBox;
    @FXML
    public TreeTableView<Menu> itemsTreeTable;
    @FXML
    public Button btnEtatSuivant;
    @FXML
    public Button btnServi;
    public Employee currentEmployee = null;

    public void initScreen() {
        initEmployes();
        initColonnes();
        afficheItems();
    }

    public void setTypeItem(String typeItem) {
        this.typeItem = typeItem;
    }

    private void initEmployes() {
        ObservableList<String> list = FXCollections.observableArrayList();

        // parcours tous les employés
        for (Employee employee : JourneeManager.getInstance().listEmployes) {
            // si l'employé est un serveur, on l'ajoute
            if ((typeItem.equals("boisson") && employee instanceof Barman) ||
                    typeItem.equals("plat") && employee instanceof Cuisinier)
                list.add(employee.getAffichage());
        }

        employeeChoiceBox.setItems(list);
    }

    private void initColonnes() {
        // on crée les colonnes
        TreeTableColumn<Menu, String> itemsCol = new TreeTableColumn<>(typeItem.equals("boisson") ? "Boissons" : "Plats");
        TreeTableColumn<Menu, String> statusCol = new TreeTableColumn<>("Status");

        // on leur assigne la valeur à laquelle chaque colonne correspond
        itemsCol.setCellValueFactory(new TreeItemPropertyValueFactory<>(typeItem));
        statusCol.setCellValueFactory(new TreeItemPropertyValueFactory<>(typeItem.equals("boisson") ? "statusBoisson" : "statusPlat"));

        // on set la taille de chaque colonne
        itemsCol.setPrefWidth(450);
        statusCol.setPrefWidth(150);

        // on ajoute les colonnes
        itemsTreeTable.getColumns().addAll(itemsCol, statusCol);
    }

    private void afficheItems() {
        TreeItem<Menu> rootItem = new TreeItem<>(new Menu());
        rootItem.setExpanded(true);

        TreeItem<Menu> itemAffichageServeur, item;
        Menu affichageServeur;

        // parcours tous les employés
        for (String serveur : JourneeManager.getInstance().getListService().keySet()) {
            affichageServeur = new Menu(serveur, serveur);

            if (typeItem.equals("boisson"))
                affichageServeur.setStatusBoisson(recupStatusServeur(serveur));
            else
                affichageServeur.setStatusPlat(recupStatusServeur(serveur));

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
        itemsTreeTable.setRoot(rootItem);
        itemsTreeTable.setShowRoot(false); // permet de ne pas afficher le premier parent vide
    }

    private String recupStatusServeur(String serveur) {
        boolean auMoinsUnEnPreparation, everyStatusAServir, commandeTermine, enAttente;

        auMoinsUnEnPreparation = false;
        everyStatusAServir = true;
        commandeTermine = false;
        enAttente = false;
        for (Menu commande : JourneeManager.getInstance().getListService().get(serveur)) {
            if (typeItem.equals("boisson")) {
                if (Objects.equals(commande.getStatusBoisson(), EnumStatus.ENCOURS.getAffichage()))
                    auMoinsUnEnPreparation = true;
                if (!Objects.equals(commande.getStatusBoisson(), EnumStatus.ASERVIR.getAffichage()))
                    everyStatusAServir = false;
                if (Objects.equals(commande.getStatusBoisson(), EnumStatus.TERMINE.getAffichage()))
                    commandeTermine = true;
            } else {
                if (Objects.equals(commande.getStatusPlat(), EnumStatus.ENCOURS.getAffichage()))
                    auMoinsUnEnPreparation = true;
                if (!Objects.equals(commande.getStatusPlat(), EnumStatus.ASERVIR.getAffichage()))
                    everyStatusAServir = false;
                if (Objects.equals(commande.getStatusPlat(), EnumStatus.TERMINE.getAffichage()))
                    commandeTermine = true;
            }
        }

        if (everyStatusAServir)
            for (Menu menu : JourneeManager.getInstance().getListService().get(serveur)) {
                if ((typeItem.equals("boisson") && !menu.getStatusPlat().equals(EnumStatus.ASERVIR.getAffichage())) ||
                        (typeItem.equals("plat") && !menu.getStatusBoisson().equals(EnumStatus.ASERVIR.getAffichage()))) {
                    everyStatusAServir = false;
                    enAttente = true;
                    break;
                }
            }

        if (auMoinsUnEnPreparation) return EnumStatus.ENCOURS.getAffichage();
        if (everyStatusAServir) return EnumStatus.ASERVIR.getAffichage();
        if (enAttente) return EnumStatus.ENATTENTE.getAffichage();
        if (commandeTermine) return EnumStatus.TERMINE.getAffichage();
        return EnumStatus.APREPARER.getAffichage();
    }

    /**
     * Récupère le barman sélectionné
     */
    public void changeChoiceBox() {
        if (employeeChoiceBox.getValue() != null) {
            for (Employee employee : ManagEmployees.getInstance().getListEmployes()) {
                if (employee.getAffichage().equals(employeeChoiceBox.getValue()))
                    currentEmployee = employee;
            }
        }

        afficheBtnEtatSuivant();
    }

    private void afficheBtnEtatSuivant() {
        btnEtatSuivant.setVisible(true);
        btnEtatSuivant.setDisable(true);
    }

    public void clickTable() {
        if (itemsTreeTable.getSelectionModel().getSelectedItem() != null) {
            TreeItem<Menu> item = itemsTreeTable.getSelectionModel().getSelectedItem();
            TreeItem<Menu> parent = item.getParent();
            if (typeItem.equals("boisson")) {
                // disable si tous les plats liés à un serveur peuvent être servi
                btnEtatSuivant.setDisable(
                        Objects.equals(parent.getValue().getStatusBoisson(), EnumStatus.ENATTENTE.getAffichage()) ||
                                Objects.equals(item.getValue().getStatusBoisson(), EnumStatus.ASERVIR.getAffichage()) ||
                                parent == itemsTreeTable.getRoot());

                // visible si l'on clique sur un serveur et que toutes les boissons peuvent être servis
                btnServi.setVisible(
                        Objects.equals(item.getValue().getStatusBoisson(), EnumStatus.ASERVIR.getAffichage()) &&
                                parent == itemsTreeTable.getRoot());
            } else {
                // disable si tous les plats liés à un serveur peuvent être servi
                btnEtatSuivant.setDisable(
                        Objects.equals(parent.getValue().getStatusPlat(), EnumStatus.ENATTENTE.getAffichage()) ||
                                Objects.equals(item.getValue().getStatusPlat(), EnumStatus.ASERVIR.getAffichage()) ||
                                parent == itemsTreeTable.getRoot());

                // visible si l'on clique sur un serveur et que tous les plats peuvent être servis
                btnServi.setVisible(
                        Objects.equals(item.getValue().getStatusPlat(), EnumStatus.ASERVIR.getAffichage()) &&
                                parent == itemsTreeTable.getRoot());
            }
        }
    }

    public void clickBtnEtatSuivant() {
        // on récupère l'élément sélectionné ainsi que son parent
        TreeItem<Menu> itemSelect = itemsTreeTable.getSelectionModel().getSelectedItem();

        if (itemSelect != null) {
            TreeItem<Menu> parent = itemSelect.getParent();

            if (typeItem.equals("boisson"))
                JourneeManager.getInstance().nextStatusBoisson(parent.getValue().getBoisson(), itemSelect.getValue(), currentEmployee);
            else
                JourneeManager.getInstance().nextStatusPlat(parent.getValue().getPlat(), itemSelect.getValue(), currentEmployee);

            parent.getValue().setStatusBoisson(recupStatusServeur(parent.getValue().getBoisson()));

            itemsTreeTable.refresh();

//            afficheBoissons();
            btnEtatSuivant.setDisable(true);
        }
    }

    public void clickBtnServi() {
        // on récupère l'élément sélectionné
        TreeItem<Menu> itemSelect = itemsTreeTable.getSelectionModel().getSelectedItem();

        if (itemSelect != null) {
            if (typeItem.equals("boisson"))
                JourneeManager.getInstance().boissonTermine(itemSelect.getValue().getBoisson());
            else
                JourneeManager.getInstance().platTermine(itemSelect.getValue().getPlat());
        }

        afficheItems();
        btnServi.setVisible(false);
    }
}
