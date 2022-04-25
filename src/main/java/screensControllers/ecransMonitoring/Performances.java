package screensControllers.ecransMonitoring;

import employee.Barman;
import employee.Cuisinier;
import employee.Employee;
import employee.Serveur;
import javafx.fxml.FXML;
import journee.JourneeManager;
import menu.Menu;

import java.util.ArrayList;

public class Performances {
    @FXML
    void initialize() {
        calculs();
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
                cuisinier = (Cuisinier) employee;
                nbPlatsVendus += cuisinier.nombrePlatsRealises();
            } else if (employee instanceof Barman) {
                barman = (Barman) employee;
                nbBoissonsVendus += barman.nombreBoissonsRealises();
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

    }
}
