package screensControllers;

import employee.Barman;
import employee.Employee;
import employee.ManagEmployees;
import employee.Serveur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.text.Text;
import menu.Menu;
import status.EnumStatus;
import status.StatusCommande;

import java.util.ArrayList;
import java.util.Objects;

public class Barmans {
    @FXML
    public ChoiceBox<String> barmanChoiceBox;
    @FXML
    public TreeTableView<StatusCommande> boissonsTreeTable;
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
        TreeTableColumn<StatusCommande, String> boissonsCol = new TreeTableColumn<>("Boissons");
        TreeTableColumn<StatusCommande, String> statusCol = new TreeTableColumn<>("Status");

        // on leur assigne la valeur à laquelle chaque colonne correspond
        boissonsCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("platOuBoisson"));
        statusCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("status"));

        // on set la taille de chaque colonne
        boissonsCol.setPrefWidth(450);
        statusCol.setPrefWidth(150);

        // on ajoute les colonnes
        boissonsTreeTable.getColumns().addAll(boissonsCol, statusCol);
    }

    private void afficheBoissons() {
        TreeItem<StatusCommande> rootItem = new TreeItem<>(new StatusCommande());
        rootItem.setExpanded(true);

        TreeItem<StatusCommande> listCommandeServeur, item;
        StatusCommande statusCommande;
        boolean auMoinsUnEnPreparation, everyStatusAServir;

        // parcours tous les employés
        for (String serveur : ManagEmployees.getInstance().getListBoissonsService().keySet()) {
            statusCommande = new StatusCommande(serveur);

            auMoinsUnEnPreparation = false;
            everyStatusAServir = true;
            for (StatusCommande commande : ManagEmployees.getInstance().getListBoissonsService().get(serveur)) {
                if (Objects.equals(commande.getStatus(), EnumStatus.ENCOURS.getAffichage()))
                    auMoinsUnEnPreparation = true;
                if (!Objects.equals(commande.getStatus(), EnumStatus.ASERVIR.getAffichage()))
                    everyStatusAServir = false;
            }

//            if (everyStatusAServir)
//                for (StatusCommande commande : ManagEmployees.getInstance().getListPlatsService().get(serveur)) {
//                    if (!Objects.equals(commande.getStatus(), EnumStatus.ASERVIR.getAffichage())) {
//                        everyStatusAServir = false;
//                        break;
//                    }
//                }

            if (auMoinsUnEnPreparation) statusCommande.setStatus(EnumStatus.ENCOURS.getAffichage());
            if (everyStatusAServir) statusCommande.setStatus(EnumStatus.ASERVIR.getAffichage());

            listCommandeServeur = new TreeItem<>(statusCommande);
            listCommandeServeur.setExpanded(true);

            // on ajoute une ligne pour chaque menu
            for (StatusCommande commande : ManagEmployees.getInstance().getListBoissonsService().get(serveur)) {
                item = new TreeItem<>(commande);
                listCommandeServeur.getChildren().add(item);
            }

            rootItem.getChildren().add(listCommandeServeur);
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

    private void afficheBtnServi() {
        btnServi.setVisible(true);
    }

    public void clickTable() {
        // disable si tous les plats liés à un serveur peuvent être servi
        btnEtatSuivant.setDisable(
                (Objects.equals(boissonsTreeTable.getSelectionModel().getSelectedItem().getValue().getStatus(), EnumStatus.ASERVIR.getAffichage()) &&
                        boissonsTreeTable.getSelectionModel().getSelectedItem().getParent() == boissonsTreeTable.getRoot()) ||
                        Objects.equals(boissonsTreeTable.getSelectionModel().getSelectedItem().getParent().getValue().getStatus(), EnumStatus.ASERVIR.getAffichage()) ||
                        boissonsTreeTable.getSelectionModel().getSelectedItem().getParent() == boissonsTreeTable.getRoot());

        // visible si l'on clique sur un serveur et que tous les plats peuvent être servis
        btnServi.setVisible(
                Objects.equals(boissonsTreeTable.getSelectionModel().getSelectedItem().getValue().getStatus(), EnumStatus.ASERVIR.getAffichage()) &&
                        boissonsTreeTable.getSelectionModel().getSelectedItem().getParent() == boissonsTreeTable.getRoot());
    }

    public void clickBtnEtatSuivant() {
        // on récupère l'élément sélectionné ainsi que son parent
        TreeItem<StatusCommande> itemSelect = boissonsTreeTable.getSelectionModel().getSelectedItem();
        TreeItem<StatusCommande> parent = itemSelect.getParent();

        int index = boissonsTreeTable.getSelectionModel().getSelectedIndex() - (parent.getParent().getChildren().indexOf(parent) + 1);

        ManagEmployees.getInstance().nextStatusBoissonService(parent.getValue().getPlatOuBoisson(), index);

        afficheBoissons();
        btnEtatSuivant.setDisable(true);
    }

    public void clickBtnServi() {
        // on récupère l'élément sélectionné ainsi que son parent
        TreeItem<StatusCommande> itemSelect = boissonsTreeTable.getSelectionModel().getSelectedItem();

        if (itemSelect != null)
            ManagEmployees.getInstance().boissonTermine(itemSelect.getValue().getPlatOuBoisson());

        afficheBoissons();
    }
}
