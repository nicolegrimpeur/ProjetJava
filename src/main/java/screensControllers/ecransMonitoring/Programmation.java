package screensControllers.ecransMonitoring;

import employee.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import journee.JourneeManager;

public class Programmation {
    @FXML
    TreeTableView<Employee> listEmployee;
    @FXML
    TreeTableView<Employee> listJournee;
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
            lancerJournee.setDisable(false);
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
        // on crée les colonnes
        TreeTableColumn<Employee, String> employeeCol = new TreeTableColumn<>("Employés");
        TreeTableColumn<Employee, String> jobCol = new TreeTableColumn<>("Jobs");

        // on leur assigne la valeur à laquelle chaque colonne correspond
        employeeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("affichage"));
        jobCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("job"));

        // on set la taille de chaque colonne
        employeeCol.setPrefWidth(200);
        jobCol.setPrefWidth(100);

        // on empêche l'utilisateur de trier
        employeeCol.setSortable(false);
        jobCol.setSortable(false);

        // on ajoute les colonnes
        listEmployee.getColumns().setAll(employeeCol, jobCol);

        // on duplique pour éviter un bug lié à la duplication des colonnes entre les deux tableaux
        // on crée les colonnes
        employeeCol = new TreeTableColumn<>("Employés");
        jobCol = new TreeTableColumn<>("Jobs");

        // on leur assigne la valeur à laquelle chaque colonne correspond
        employeeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("affichage"));
        jobCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("job"));

        // on set la taille de chaque colonne
        employeeCol.setPrefWidth(200);
        jobCol.setPrefWidth(100);

        // on empêche l'utilisateur de trier
        employeeCol.setSortable(false);
        jobCol.setSortable(false);

        listJournee.getColumns().setAll(employeeCol, jobCol);
    }

    private void afficheEmployees() {
        TreeItem<Employee> rootItem = new TreeItem<>(new Manager());
        rootItem.setExpanded(true);

        for (Employee employee : ManagEmployees.getInstance().getListEmployes())
            if (!JourneeManager.getInstance().listEmployes.contains(employee) &&
                    (employee.nbJoursConsecutifs < 2 || employee instanceof Manager))
                rootItem.getChildren().add(new TreeItem<>(employee));

        // on affiche les lignes
        listEmployee.setRoot(rootItem);
        listEmployee.setShowRoot(false); // permet de ne pas afficher le premier parent vide


        rootItem = new TreeItem<>(new Manager());
        rootItem.setExpanded(true);

        for (Employee employee : JourneeManager.getInstance().listEmployes) {
            rootItem.getChildren().add(new TreeItem<>(employee));
        }

        // on affiche les lignes
        listJournee.setRoot(rootItem);
        listJournee.setShowRoot(false); // permet de ne pas afficher le premier parent vide


        // on affiche le texte permettant de dire le nombre d'employés nécessaires pour lancer la journée
        textAffichage.setText("Il faut au moins 4 cuisiniers, 2 serveurs, 1 manager et 1 barman pour commencer la journée");
    }

    public void clickAjouter() {
        JourneeManager.getInstance().addEmployee(listEmployee.getSelectionModel().getSelectedItem().getValue());

        afficheEmployees();
        gestionBoutonLancerJournee();
    }

    public void clickSupprimer() {
        JourneeManager.getInstance().removeEmployee(listJournee.getSelectionModel().getSelectedItem().getValue());

        afficheEmployees();
        gestionBoutonLancerJournee();
    }

    public void clickTableEmployees(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() >= 2) {
            TreeItem<Employee> itemSelect = listEmployee.getSelectionModel().getSelectedItem();

            if (itemSelect != null)
                JourneeManager.getInstance().addEmployee(itemSelect.getValue());

            afficheEmployees();
            gestionBoutonLancerJournee();
        }
    }

    public void clickTableJournee(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() >= 2) {
            TreeItem<Employee> itemSelect = listJournee.getSelectionModel().getSelectedItem();

            if (itemSelect != null)
                JourneeManager.getInstance().removeEmployee(itemSelect.getValue());

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
