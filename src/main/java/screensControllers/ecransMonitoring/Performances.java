package screensControllers.ecransMonitoring;

import employee.Barman;
import employee.Cuisinier;
import employee.Employee;
import employee.Serveur;
import javafx.fxml.FXML;
import journee.JourneeManager;

public class Performances {
    @FXML
    void initialize() {
        calculs();
    }

    private void calculs() {
        int nbPlatsVendus = 0;
        int nbBoissonsVendus = 0;
        Cuisinier cuisinier;
        Barman barman;
        Serveur serveur;

        for (Employee employee: JourneeManager.getInstance().listEmployes) {
            if (employee instanceof Cuisinier) {
                cuisinier = (Cuisinier) employee;
                nbPlatsVendus += cuisinier.nombrePlatsRealises();
            }
            else if (employee instanceof Barman) {
                barman = (Barman) employee;
                nbBoissonsVendus += barman.nombreBoissonsRealises();
            }
            else if (employee instanceof Serveur) {
            }
        }

        System.out.println("Nombre plats réalisés : " + nbPlatsVendus);
        System.out.println("Nombre boissons réalisés : " + nbBoissonsVendus);

    }
}
