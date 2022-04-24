package screensControllers.ecransMonitoring;

import com.sun.source.tree.Tree;
import employee.Employee;
import employee.ManagEmployees;
import employee.Manager;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import journee.JourneeManager;

public class Programmation {
    @FXML
    TreeTableView<Employee> listEmployee;
    @FXML
    TreeTableView<Employee> listJournee;

    @FXML
    void initialize() {
        initColonnes();
        afficheEmployees();
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
            if (!JourneeManager.getInstance().listEmployes.contains(employee))
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
    }

    public void clickAjouter() {
        JourneeManager.getInstance().addEmployee(listEmployee.getSelectionModel().getSelectedItem().getValue());

        afficheEmployees();
    }

    public void clickSupprimer() {
        JourneeManager.getInstance().removeEmployee(listJournee.getSelectionModel().getSelectedItem().getValue());
        afficheEmployees();
    }
}
