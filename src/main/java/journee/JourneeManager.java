package journee;

import employee.Employee;
import employee.ManagEmployees;

import java.util.ArrayList;

public class JourneeManager {
    private static JourneeManager instance = null;
    public ArrayList<Employee> listEmployes = new ArrayList<>();
    public boolean isJourneeEnCours = false;

    private JourneeManager() {}

    public static JourneeManager getInstance() {
        if (instance == null)
            instance = new JourneeManager();
        return instance;
    }

    public void debutJournee() {
        ManagEmployees.getInstance().addJourConsecutif(listEmployes);
        isJourneeEnCours = true;

        ManagEmployees.getInstance().resetVentes();
    }

    public void addEmployee(Employee employee) {
        listEmployes.add(employee);
    }
    public void removeEmployee(Employee employee) {
        listEmployes.remove(employee);
    }

    public void finJournee() {
        isJourneeEnCours = false;
        listEmployes.clear();
    }
}
