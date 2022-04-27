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
    private String typeItem = null;                 // type de la page (boisson ou plat)
    @FXML
    public ChoiceBox<String> employeeChoiceBox;     // choice box permettant de saisir l'employé
    @FXML
    public TreeTableView<Menu> itemsTreeTable;      // tableau affichant les plats / boissons à préparer
    @FXML
    public Button btnEtatSuivant;                   // bouton pour passer un item à l'état suivant
    @FXML
    public Button btnServi;                         // bouton pour marquer un plat comme servi
    public Employee currentEmployee = null;         // stocke l'employé sélectionné

    /**
     * Initialise l'écran
     */
    public void initScreen() {
        initEmployes();
        initColonnes();
        afficheItems();
    }

    /**
     * Permet de modifier typeItem
     * @param typeItem boisson ou plat, selon si l'on est sur l'écran Cuisinier ou Barmans
     */
    public void setTypeItem(String typeItem) {
        this.typeItem = typeItem;
    }

    /**
     * Remplit la choice box d'employé
     */
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

    /**
     * Initialise les colonnes du tableau
     */
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

        // on ajoute les colonnes à la table
        itemsTreeTable.getColumns().addAll(itemsCol, statusCol);
    }

    /**
     * Ajoute chaque item dans le tableau
     */
    public void afficheItems() {
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

    /**
     * Permet de récupérer l'état global d'un serveur en fonction des items qu'il doit gérer
     * @param serveur le nom d'affichage du serveur
     * @return le status de celui ci
     */
    private String recupStatusServeur(String serveur) {
        boolean auMoinsUnEnPreparation, everyStatusAServir, commandeTermine, enAttente;

        auMoinsUnEnPreparation = false;     // stocke si au moins un item est en préparation
        everyStatusAServir = true;          // stocke si tous les items sont affichés comme à servir
        commandeTermine = false;            // stocke si toutes les commandes sont terminés ou non
        enAttente = false;                  // stocke s'il faut passer la commande comme en attente

        // on parcourt les commandes de ce serveur
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

        // si tous les items sont marqués comme à servir
        if (everyStatusAServir)
            // on parcourt les menus affiliés à ce serveur
            for (Menu menu : JourneeManager.getInstance().getListService().get(serveur)) {
                // si un seul élément opposé n'est pas à servir, la commande n'est pas prète à être servi
                if ((typeItem.equals("boisson") && !menu.getStatusPlat().equals(EnumStatus.ASERVIR.getAffichage())) ||
                        (typeItem.equals("plat") && !menu.getStatusBoisson().equals(EnumStatus.ASERVIR.getAffichage()))) {
                    everyStatusAServir = false;
                    enAttente = true;
                    break;
                }
            }

        // on return le status correspondant
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

    /**
     * Affiche le bouton état suivant
     */
    private void afficheBtnEtatSuivant() {
        btnEtatSuivant.setVisible(true);
        btnEtatSuivant.setDisable(true);
    }

    /**
     * Gère le click sur la table
     */
    public void clickTable() {
        // si un item est sélectionné
        if (itemsTreeTable.getSelectionModel().getSelectedItem() != null) {
            TreeItem<Menu> item = itemsTreeTable.getSelectionModel().getSelectedItem();
            TreeItem<Menu> parent = item.getParent();
            if (typeItem.equals("boisson")) {
                // disable si
                // - tous les plats sont prêts à être servis
                // - si l'on clique sur le nom du serveur
                btnEtatSuivant.setDisable(
                        Objects.equals(parent.getValue().getStatusBoisson(), EnumStatus.ENATTENTE.getAffichage()) ||
                                Objects.equals(item.getValue().getStatusBoisson(), EnumStatus.ASERVIR.getAffichage()) ||
                                parent == itemsTreeTable.getRoot());

                // visible si l'on clique sur un serveur et que toutes les boissons peuvent être servis
                btnServi.setVisible(
                        Objects.equals(item.getValue().getStatusBoisson(), EnumStatus.ASERVIR.getAffichage()) &&
                                parent == itemsTreeTable.getRoot());
            } else {
                // disable si
                // - tous les plats sont prêts à être servis
                // - si l'on clique sur le nom du serveur
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

    /**
     * Gère le click sur le bouton suivant
     */
    public void clickBtnEtatSuivant() {
        // on récupère l'élément sélectionné ainsi que son parent
        TreeItem<Menu> itemSelect = itemsTreeTable.getSelectionModel().getSelectedItem();

        if (itemSelect != null) {
            // on récupère le parent correspondant
            TreeItem<Menu> parent = itemSelect.getParent();

            // on passe l'élément à l'état suivant
            // puis on modifie le parent si besoin
            if (typeItem.equals("boisson")) {
                JourneeManager.getInstance().nextStatusBoisson(parent.getValue().getBoisson(), itemSelect.getValue(), currentEmployee);
                parent.getValue().setStatusBoisson(recupStatusServeur(parent.getValue().getBoisson()));
            } else {
                JourneeManager.getInstance().nextStatusPlat(parent.getValue().getPlat(), itemSelect.getValue(), currentEmployee);
                parent.getValue().setStatusPlat(recupStatusServeur(parent.getValue().getPlat()));
            }

            // on rafraichit la table et on désactive le bouton état suivant
            itemsTreeTable.refresh();
            btnEtatSuivant.setDisable(true);

            // si un nouvel élément a été rajouté en arrière-plan, on rajoute ces éléments au tableau
            if (parent.getParent().getChildren().size() != JourneeManager.getInstance().getListService().size())
                afficheItems();
        }
    }

    /**
     * Gère le click sur le bouton servi
     */
    public void clickBtnServi() {
        // on récupère l'élément sélectionné
        TreeItem<Menu> itemSelect = itemsTreeTable.getSelectionModel().getSelectedItem();

        if (itemSelect != null) {
            // on précise qu'on a terminé la commande de ce serveur
            if (typeItem.equals("boisson"))
                JourneeManager.getInstance().boissonTermine(itemSelect.getValue().getBoisson());
            else
                JourneeManager.getInstance().platTermine(itemSelect.getValue().getPlat());
        }

        // on rafraichit le tableau
        afficheItems();
        btnServi.setVisible(false);
    }
}
