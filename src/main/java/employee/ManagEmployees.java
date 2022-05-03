package employee;

import java.util.ArrayList;

public class ManagEmployees {
    private static ManagEmployees instance = null;
    private static final ArrayList<Employee> listEmployes = new ArrayList<>();
    private final static ArrayList<String> listEmploi = new ArrayList<>() {
        {
            add("Barman");
            add("Cuisinier");
            add("Manager");
            add("Serveur");
        }
    };

    private ManagEmployees() {
    }

    public static ManagEmployees getInstance() {
        if (instance == null) {
            instance = new ManagEmployees();
        }
        return instance;
    }

    public ArrayList<String> getListEmploi() {
        return listEmploi;
    }


    public void addJourConsecutif(ArrayList<Employee> listEmployeJournee) {
        int nbJoursConsecutifs;
        for (Employee employee : listEmployes) {
            nbJoursConsecutifs = employee.nbJoursConsecutifs;
            for (Employee employeeJournee : listEmployeJournee)
                if (employee.getAffichage().equals(employeeJournee.getAffichage()))
                    employee.nbJoursConsecutifs++;

            if (employee.nbJoursConsecutifs == nbJoursConsecutifs)
                employee.nbJoursConsecutifs = 0;
        }
    }

    public void addEmploye(Employee newEmploye) {
        listEmployes.add(newEmploye);
    }

    public ArrayList<Employee> getListEmployes() {
        return listEmployes;
    }


    public void supprimerEmploye(Employee employee) {
        listEmployes.remove(employee);
    }
}
