package screensControllers.ecransMonitoring;

import employee.Barman;
import employee.Cuisinier;
import employee.Employee;
import employee.Serveur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import journee.JourneeManager;
import menu.Menu;

import java.util.ArrayList;

public class Performances {
    @FXML
    Label labelNbPlats;
    @FXML
    Label labelNbBoissons;
    @FXML
    Label labelBenefices;
    @FXML
    TableView<Employee> tableBarmans;
    @FXML
    TableView<Employee> tableCuisiniers;
    @FXML
    TableView<Employee> tableServeurs;
    @FXML
    TableView tableIngredients;
    @FXML
    TableView tablePlats;
    @FXML
    TableView tableBoissons;

    @FXML
    void initialize() {
        calculs();
        initColonnes();
        donneesColonnes();
    }

    private void calculs() {
        int nbPlatsVendus = 0;
        int nbBoissonsVendus = 0;
        int nbMenusVendus = 0;
        int beneficeTotal = 0;
        Cuisinier cuisinier;
        Barman barman;
        Serveur serveur;
        ArrayList<Menu> menusServeur;
        int nbMenus100Ans = 0;

        for (Employee employee : JourneeManager.getInstance().listEmployes) {
            if (employee instanceof Cuisinier) {
                nbPlatsVendus += employee.getNbItemsVendus();
            } else if (employee instanceof Barman) {
                nbBoissonsVendus += employee.getNbItemsVendus();
            }

            menusServeur = JourneeManager.getInstance().getMenusVendus().get(employee.getAffichage());

            if (menusServeur != null) {
                nbMenusVendus += menusServeur.size();

                for (Menu menu : menusServeur) {
                    if (!menu.getPrix().equals(""))
                        beneficeTotal += Integer.parseInt(menu.getPrix());
                    else
                        nbMenus100Ans++;
                }

            }
        }

        if ((nbMenus100Ans * 10) / 7 != (nbMenus100Ans / 7) * 10)
            beneficeTotal += 100 * ((nbMenus100Ans / 7) + 1);
        else
            beneficeTotal += 100 * (nbMenus100Ans / 7);

        System.out.println("Nombre plats réalisés : " + nbPlatsVendus);
        System.out.println("Nombre boissons réalisés : " + nbBoissonsVendus);
        System.out.println("Nombre menus réalisés : " + nbMenusVendus);
        System.out.println("Bénéfice total : " + beneficeTotal);

        labelNbPlats.setText("Nombre de plats vendus : " + nbPlatsVendus);
        labelNbBoissons.setText("Nombre de boissons vendus : " + nbBoissonsVendus);
        labelBenefices.setText("Bénéfice total : " + beneficeTotal);
    }

    private void initColonnes() {
        // on crée les colonnes
        TableColumn<Employee, String> itemsCol = new TableColumn<>("Barman");
        TableColumn<Employee, String> statusCol = new TableColumn<>("Préparations");

        // on leur assigne la valeur à laquelle chaque colonne correspond
        itemsCol.setCellValueFactory(new PropertyValueFactory<>("affichage"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("nbItemsVendus"));

        // on set la taille de chaque colonne
        itemsCol.setPrefWidth(150);
        statusCol.setPrefWidth(100);

        // on ajoute les colonnes à la table
        tableBarmans.getColumns().setAll(itemsCol, statusCol);


        // on crée les colonnes
        itemsCol = new TableColumn<>("Cuisinier");
        statusCol = new TableColumn<>("Nombre préparé");

        // on leur assigne la valeur à laquelle chaque colonne correspond
        itemsCol.setCellValueFactory(new PropertyValueFactory<>("affichage"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("Préparations"));

        // on set la taille de chaque colonne
        itemsCol.setPrefWidth(150);
        statusCol.setPrefWidth(100);

        // on ajoute les colonnes à la table
        tableCuisiniers.getColumns().setAll(itemsCol, statusCol);


        // on crée les colonnes
        itemsCol = new TableColumn<>("Serveur");
        statusCol = new TableColumn<>("Servis");

        // on leur assigne la valeur à laquelle chaque colonne correspond
        itemsCol.setCellValueFactory(new PropertyValueFactory<>("affichage"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("nbItemsVendus"));

        // on set la taille de chaque colonne
        itemsCol.setPrefWidth(150);
        statusCol.setPrefWidth(100);

        // on ajoute les colonnes à la table
        tableServeurs.getColumns().setAll(itemsCol, statusCol);
    }

    private void donneesColonnes() {
        ObservableList<Employee> tabBarmans = FXCollections.observableArrayList();
        ObservableList<Employee> tabCuisiniers = FXCollections.observableArrayList();
        ObservableList<Employee> tabServeurs = FXCollections.observableArrayList();

        for (Employee employee: JourneeManager.getInstance().listEmployes) {
            if (employee instanceof Barman)
                tabBarmans.add(employee);
            if (employee instanceof Cuisinier)
                tabCuisiniers.add(employee);
            if (employee instanceof Serveur)
                tabServeurs.add(employee);
        }

        tableBarmans.setItems(tabBarmans);
        tableCuisiniers.setItems(tabCuisiniers);
        tableServeurs.setItems(tabServeurs);
    }
}
