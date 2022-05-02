package screensControllers.ecransMonitoring;

import employee.Barman;
import employee.Cuisinier;
import employee.Employee;
import employee.Serveur;
import ingredients.EnumIngredients;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import journee.JourneeManager;
import menu.Menu;
import menuBoissons.EnumBoissons;
import menuPlats.EnumPlats;

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
    TableView<AffichagePerformance> tableIngredients;
    @FXML
    TableView<AffichagePerformance> tablePlats;
    @FXML
    TableView<AffichagePerformance> tableBoissons;

    @FXML
    void initialize() {
        calculs();
        initColonnes();
        donneesColonnes();
    }

    /**
     * Calcul le nombre de plats vendus et le bénéfice total
     */
    private void calculs() {
        int nbPlatsVendus = 0;
        int nbBoissonsVendus = 0;
        int nbMenusVendus = 0;
        int beneficeTotal = 0;
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

//        System.out.println("Nombre plats réalisés : " + nbPlatsVendus);
//        System.out.println("Nombre boissons réalisés : " + nbBoissonsVendus);
//        System.out.println("Nombre menus réalisés : " + nbMenusVendus);
//        System.out.println("Bénéfice total : " + beneficeTotal);

        labelNbPlats.setText("Nombre de plats vendus : " + nbPlatsVendus);
        labelNbBoissons.setText("Nombre de boissons vendus : " + nbBoissonsVendus);
        labelBenefices.setText("Bénéfice total : " + beneficeTotal);
    }

    /**
     * Initialise les colonnes des TableView
     */
    private void initColonnes() {
        tableBarmans.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("affichage"));
        tableBarmans.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("nbItemsVendus"));

        tableCuisiniers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("affichage"));
        tableCuisiniers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("nbItemsVendus"));

        tableServeurs.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("affichage"));
        tableServeurs.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("nbItemsVendus"));

        tableIngredients.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nom"));
        tableIngredients.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("nombre"));

        tablePlats.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nom"));
        tablePlats.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("nombre"));

        tableBoissons.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nom"));
        tableBoissons.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }

    /**
     * Ajoute les données aux colonnes
     */
    private void donneesColonnes() {
        // affichage des données sur les employés
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


        // affichage des données sur les ingrédients
        ObservableList<AffichagePerformance> tabIngredients = FXCollections.observableArrayList();

        for (EnumIngredients ingredient: EnumIngredients.values())
            tabIngredients.add(new AffichagePerformance(ingredient.getName(), ingredient.ingredientsManquants()));

        tableIngredients.setItems(tabIngredients);


        // affichage des données sur les plats et les boissons
        ObservableList<AffichagePerformance> tabPlats = FXCollections.observableArrayList();
        ObservableList<AffichagePerformance> tabBoissons = FXCollections.observableArrayList();

        for (EnumPlats plat: EnumPlats.values())
            tabPlats.add(new AffichagePerformance(plat.getName(), 0));
        for (EnumBoissons boisson: EnumBoissons.values())
            tabBoissons.add(new AffichagePerformance(boisson.getName(), 0));

        for (ArrayList<Menu> menuArrayList: JourneeManager.getInstance().getMenusVendus().values()) {
            for (Menu menu: menuArrayList) {
                for (AffichagePerformance affichagePerformance: tabPlats)
                    if (menu.getPlat().equals(affichagePerformance.getNom()))
                        affichagePerformance.nombre++;
                for (AffichagePerformance affichagePerformance: tabBoissons)
                    if (menu.getBoisson().equals(affichagePerformance.getNom()))
                        affichagePerformance.nombre++;
            }
        }

        tablePlats.setItems(tabPlats);
        tableBoissons.setItems(tabBoissons);
    }
}
