package screensControllers.ecransMonitoring;

import employee.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import journee.JourneeManager;

public class Programmation {
    @FXML
    TableView<Employee> listEmployee;
    @FXML
    TableView<Employee> listJournee;
    @FXML
    Button lancerJournee;
    @FXML
    Button btnFinJournee;
    @FXML
    Button btnAjouter;
    @FXML
    Button btnSupprimer;
    @FXML
    Label textAffichage;

    @FXML
    void initialize() {
        initColonnes();
        afficheEmployees();

        manageButtons();
    }

    public void manageButtons() {
        if (!JourneeManager.getInstance().isJourneeEnCours) {
            listEmployee.setDisable(false);
            listJournee.setDisable(false);
            btnAjouter.setDisable(false);
            btnSupprimer.setDisable(false);
            gestionBoutonLancerJournee();
            btnFinJournee.setDisable(true);
        } else {
            listEmployee.setDisable(true);
            listJournee.setDisable(true);
            btnAjouter.setDisable(true);
            btnSupprimer.setDisable(true);
            lancerJournee.setDisable(true);
            btnFinJournee.setDisable(JourneeManager.getInstance().getListService().size() != 0);
            if (JourneeManager.getInstance().getListService().size() != 0)
                textAffichage.setText("Il y a encore des commandes en cours");
        }
    }

    private void initColonnes() {
        listEmployee.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("affichage"));
        listEmployee.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("job"));

        listJournee.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("affichage"));
        listJournee.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("job"));
    }

    private void afficheEmployees() {
        ObservableList<Employee> list = FXCollections.observableArrayList();

        for (Employee employee : ManagEmployees.getInstance().getListEmployes())
            // si l'employé n'est pas déjà dans le tableau de journée, et qu'il n'a pas fais plus de 3 jours consécutifs
            if (!JourneeManager.getInstance().listEmployes.contains(employee) &&
                    (employee.nbJoursConsecutifs < 2 || employee instanceof Manager))
                list.add(employee);

        listEmployee.setItems(list);


        listJournee.setItems(FXCollections.observableArrayList(JourneeManager.getInstance().listEmployes));


        // on affiche le texte permettant de dire le nombre d'employés nécessaires pour lancer la journée
        textAffichage.setText("Il faut au moins 4 cuisiniers, 2 serveurs, 1 manager et 1 barman pour commencer la journée");
    }

    public void clickAjouter() {
        if (listEmployee.getSelectionModel().getSelectedItem() != null)
            JourneeManager.getInstance().addEmployee(listEmployee.getSelectionModel().getSelectedItem());

        afficheEmployees();
        gestionBoutonLancerJournee();
    }

    public void clickSupprimer() {
        if (listEmployee.getSelectionModel().getSelectedItem() != null)
            JourneeManager.getInstance().removeEmployee(listJournee.getSelectionModel().getSelectedItem());

        afficheEmployees();
        gestionBoutonLancerJournee();
    }

    public void clickTableEmployees(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() >= 2) {
            Employee itemSelect = listEmployee.getSelectionModel().getSelectedItem();

            if (itemSelect != null)
                JourneeManager.getInstance().addEmployee(itemSelect);

            afficheEmployees();
            gestionBoutonLancerJournee();
        }
    }

    public void clickTableJournee(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() >= 2) {
            Employee itemSelect = listJournee.getSelectionModel().getSelectedItem();

            if (itemSelect != null)
                JourneeManager.getInstance().removeEmployee(itemSelect);

            afficheEmployees();
            gestionBoutonLancerJournee();
        }
    }

    private void gestionBoutonLancerJournee() {
        int nbManagers = 0;
        int nbServeurs = 0;
        int nbBarmans = 0;
        int nbCuisiniers = 0;

        for (Employee employee : JourneeManager.getInstance().listEmployes) {
            if (employee instanceof Manager) nbManagers++;
            if (employee instanceof Serveur) nbServeurs++;
            if (employee instanceof Barman) nbBarmans++;
            if (employee instanceof Cuisinier) nbCuisiniers++;
        }

        lancerJournee.setDisable(nbCuisiniers < 4 || nbServeurs < 2 || nbBarmans < 1 || nbManagers < 1);
    }

    public void clickLancerJournee() {
        JourneeManager.getInstance().debutJournee();

        initColonnes();
        afficheEmployees();
        manageButtons();
    }

    public void clickFinJournee() {
        JourneeManager.getInstance().finJournee();

        initColonnes();
        afficheEmployees();
        manageButtons();
    }
}
