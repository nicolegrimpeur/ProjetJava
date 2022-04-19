package screensControllers.ecransMonitoring;

import employee.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

import java.util.Objects;

public class GestionEmployes {

    @FXML
    public TextField textNom;
    @FXML
    public TextField textPrenom;
    @FXML
    public ChoiceBox<String> choiceEmploi;
    @FXML
    public TextField textSalaire;
    @FXML
    public Label textErreur;
    @FXML
    public TreeTableView<Employee> listEmploye;

    @FXML
    public void initialize() {
        choiceEmploi.setItems(FXCollections.observableArrayList(ManagEmployees.listEmploi));
        afficheListEmploye();
    }

    /**
     * Permet d'ajouter un employé à partir des informations rentrées dans les champs correspondants
     */
    public void ajoutEmploye() {
        textErreur.setVisible(false);

        try {
            String nom = textNom.getText();
            String prenom = textPrenom.getText();
            int salaire = Integer.parseInt(textSalaire.getText());

            if (!Objects.equals(nom, "") && !Objects.equals(prenom, "") && salaire != 0 && choiceEmploi.getValue() != null) {
                switch (choiceEmploi.getValue()) {
                    case "Barman" -> ManagEmployees.addEmploye(new Barman(nom, prenom, salaire));
                    case "Cuisinier" -> ManagEmployees.addEmploye(new Cuisinier(nom, prenom, salaire));
                    case "Manager" -> ManagEmployees.addEmploye(new Manager(nom, prenom, salaire));
                    case "Serveur" -> ManagEmployees.addEmploye(new Serveur(nom, prenom, salaire));
                    default -> afficheErreur("Merci d'implémenter ce type d'employé");
                }

                textNom.setText("");
                textPrenom.setText("");
                textSalaire.setText("");
            } else {
                String erreur = "Merci d'ajouter : ";
                if (Objects.equals(nom, "")) erreur += "nom ";
                if (Objects.equals(prenom, "")) erreur += "prenom ";
                if (choiceEmploi.getValue() == null) erreur += "métier ";
                if (salaire == 0) erreur += "salaire ";

                afficheErreur(erreur);
            }
        } catch (NumberFormatException e) {
            afficheErreur("Merci de rentrer un nombre entier pour le salaire");
        }

        afficheListEmploye();
    }

    /**
     * Permet d'afficher une erreur lors de l'ajout d'employés
     * @param erreur l'erreur à afficher
     */
    public void afficheErreur(String erreur) {
        textErreur.setVisible(true);
        textErreur.setText("Une erreur a eu lieu : " + erreur);
    }

    /**
     * Ajoute à la table view la liste des employés
     */
    public void afficheListEmploye() {
        // on initialise les colonnes
        TreeTableColumn<Employee, String> nomCol = new TreeTableColumn<>("Nom");
        TreeTableColumn<Employee, String> prenomCol = new TreeTableColumn<>("Prénom");
        TreeTableColumn<Employee, String> jobCol = new TreeTableColumn<>("Job");

        // on ajuste la taille des colonnes
        nomCol.setPrefWidth(100);
        prenomCol.setPrefWidth(100);
        jobCol.setPrefWidth(100);

        // on fait correspondre chaque colonne à un attribut de la class Employee
        nomCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("prenom"));
        jobCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("job"));

        // on ajoute les colonnes
        listEmploye.getColumns().clear();
        listEmploye.getColumns().addAll(nomCol, prenomCol, jobCol);

        // root element --> correspond à l'élément en haut du tableau
        TreeItem<Employee> rootElement = new TreeItem<>();
        TreeItem<Employee> element;
        // on ajoute tous les employés
        for (int i = 0; i < ManagEmployees.listEmployes.size(); i++) {
            element = new TreeItem<Employee>(ManagEmployees.listEmployes.get(i));
            rootElement.getChildren().add(element);
        }

        // permet d'étendre le root
        rootElement.setExpanded(true);

        // on ajoute les éléments à la table
        listEmploye.setRoot(rootElement);
    }

    /**
     * Permet de supprimer l'employé sélectionné
     */
    public void supprimerEmploye() {
        int employeeIndex = listEmploye.getSelectionModel().getFocusedIndex();

        // l'indice 0 correspond au root
        if (employeeIndex != 0)
            ManagEmployees.listEmployes.remove(employeeIndex - 1);

        afficheListEmploye();
    }
}
