package screensControllers;

import employee.Barman;
import employee.Employee;
import employee.ManagEmployees;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import menu.Menu;
import status.EnumStatus;

import java.util.Objects;

public class Barmans {
    @FXML
    public ChoiceBox<String> barmanChoiceBox;
    @FXML
    public TreeTableView<Menu> boissonsTreeTable;
    @FXML
    public Button btnEtatSuivant;
    @FXML
    public Button btnServi;
    public Barman currentBarman = null;

    @FXML
    void initialize() {
        initBarmans();
        initColonnes();
        afficheBoissons();
    }

    private void initBarmans() {
        ObservableList<String> listBarmans = FXCollections.observableArrayList();

        // parcours tous les employés
        for (Employee employee : ManagEmployees.getInstance().getListEmployes())
            // si l'employé est un serveur, on l'ajoute
            if (employee instanceof Barman)
                listBarmans.add(employee.getNom() + " " + employee.getPrenom());

        barmanChoiceBox.setItems(listBarmans);
    }

    private void initColonnes() {
        // on crée les colonnes
        TreeTableColumn<Menu, String> boissonsCol = new TreeTableColumn<>("Boissons");
        TreeTableColumn<Menu, String> statusCol = new TreeTableColumn<>("Status");

        // on leur assigne la valeur à laquelle chaque colonne correspond
        boissonsCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("boisson"));
        statusCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("statusBoisson"));

        // on set la taille de chaque colonne
        boissonsCol.setPrefWidth(450);
        statusCol.setPrefWidth(150);

        // on empêche le tri
        boissonsCol.setSortable(false);
        statusCol.setSortable(false);

        // on ajoute les colonnes
        boissonsTreeTable.getColumns().addAll(boissonsCol, statusCol);
    }

    private void afficheBoissons() {
        TreeItem<Menu> rootItem = new TreeItem<>(new Menu());
        rootItem.setExpanded(true);

        TreeItem<Menu> itemAffichageServeur, item;
        Menu affichageServeur;
        boolean auMoinsUnEnPreparation, everyStatusAServir, commandeTermine, enAttente;

        // parcours tous les employés
        for (String serveur : ManagEmployees.getInstance().getListService().keySet()) {
            affichageServeur = new Menu(serveur, serveur);

            auMoinsUnEnPreparation = false;
            everyStatusAServir = true;
            commandeTermine = false;
            enAttente = false;
            for (Menu commande : ManagEmployees.getInstance().getListService().get(serveur)) {
                if (Objects.equals(commande.getStatusBoisson(), EnumStatus.ENCOURS.getAffichage()))
                    auMoinsUnEnPreparation = true;
                if (!Objects.equals(commande.getStatusBoisson(), EnumStatus.ASERVIR.getAffichage()))
                    everyStatusAServir = false;
                if (Objects.equals(commande.getStatusBoisson(), EnumStatus.TERMINE.getAffichage()))
                    commandeTermine = true;
            }

            if (everyStatusAServir)
                for (Menu menu : ManagEmployees.getInstance().getListService().get(serveur)) {
                    if (!Objects.equals(menu.getStatusPlat(), EnumStatus.ASERVIR.getAffichage())) {
                        everyStatusAServir = false;
                        enAttente = true;
                        break;
                    }
                }

            if (auMoinsUnEnPreparation) affichageServeur.setStatusBoisson(EnumStatus.ENCOURS.getAffichage());
            if (everyStatusAServir) affichageServeur.setStatusBoisson(EnumStatus.ASERVIR.getAffichage());
            if (enAttente) affichageServeur.setStatusBoisson(EnumStatus.ENATTENTE.getAffichage());
            if (commandeTermine) affichageServeur.setStatusBoisson(EnumStatus.TERMINE.getAffichage());

            itemAffichageServeur = new TreeItem<>(affichageServeur);
            itemAffichageServeur.setExpanded(true);

            // on ajoute une ligne pour chaque menu
            for (Menu commande : ManagEmployees.getInstance().getListService().get(serveur)) {
                item = new TreeItem<>(commande);
                itemAffichageServeur.getChildren().add(item);
            }

            rootItem.getChildren().add(itemAffichageServeur);
        }

        // on affiche les lignes
        boissonsTreeTable.setRoot(rootItem);
        boissonsTreeTable.setShowRoot(false); // permet de ne pas afficher le premier parent vide
    }

    /**
     * Récupère le barman sélectionné
     */
    public void changeBarmanChoiceBox() {
        if (barmanChoiceBox.getValue() != null) {
            for (Employee employee : ManagEmployees.getInstance().getListEmployes()) {
                if ((employee.getNom() + " " + employee.getPrenom()).equals(barmanChoiceBox.getValue()))
                    currentBarman = (Barman) employee;
            }
        }

        afficheBtnEtatSuivant();
    }

    private void afficheBtnEtatSuivant() {
        btnEtatSuivant.setVisible(true);
        btnEtatSuivant.setDisable(true);
    }

    public void clickTable() {
        // disable si tous les plats liés à un serveur peuvent être servi
        btnEtatSuivant.setDisable(
                (Objects.equals(boissonsTreeTable.getSelectionModel().getSelectedItem().getValue().getStatusBoisson(), EnumStatus.ENATTENTE.getAffichage()) &&
                        boissonsTreeTable.getSelectionModel().getSelectedItem().getParent() == boissonsTreeTable.getRoot()) ||
                        Objects.equals(boissonsTreeTable.getSelectionModel().getSelectedItem().getParent().getValue().getStatusBoisson(), EnumStatus.ENATTENTE.getAffichage()) ||
                        boissonsTreeTable.getSelectionModel().getSelectedItem().getParent() == boissonsTreeTable.getRoot());

        // visible si l'on clique sur un serveur et que tous les plats peuvent être servis
        btnServi.setVisible(
                Objects.equals(boissonsTreeTable.getSelectionModel().getSelectedItem().getValue().getStatusBoisson(), EnumStatus.ASERVIR.getAffichage()) &&
                        boissonsTreeTable.getSelectionModel().getSelectedItem().getParent() == boissonsTreeTable.getRoot());
    }

    public void clickBtnEtatSuivant() {
        // on récupère l'élément sélectionné ainsi que son parent
        TreeItem<Menu> itemSelect = boissonsTreeTable.getSelectionModel().getSelectedItem();
        TreeItem<Menu> parent = itemSelect.getParent();

        int index = boissonsTreeTable.getSelectionModel().getSelectedIndex() - (parent.getParent().getChildren().indexOf(parent) + 1);

        ManagEmployees.getInstance().nextStatusBoisson(parent.getValue().getBoisson(), index);

        afficheBoissons();
        btnEtatSuivant.setDisable(true);
    }

    public void clickBtnServi() {
        // on récupère l'élément sélectionné
        TreeItem<Menu> itemSelect = boissonsTreeTable.getSelectionModel().getSelectedItem();

        if (itemSelect != null)
            ManagEmployees.getInstance().boissonTermine(itemSelect.getValue().getBoisson(), currentBarman);

        afficheBoissons();
    }
}
