package employee;

import java.util.ArrayList;
import java.util.Scanner;

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

    public void gestionEmploye() {
        int i = 1;
        for (Employee employe : listEmployes) {
            System.out.println(i + " - " + employe);
            i++;
        }
        System.out.println("Que souhaitez vous faire ?");
        System.out.println("1 - Ajouter un employé");
        System.out.println("2 - Supprimer un employé");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();

        switch (choix) {
            case 1 -> ajoutEmploye();
            case 2 -> suppressionEmploye();
            default -> System.out.println("Commande inconnue");
        }
    }

    public void ajoutEmploye() {
        System.out.println("Nom de l'employe : ");
        Scanner scanner1 = new Scanner(System.in);
        String nomEmploye = scanner1.nextLine();

        System.out.println("Prenom de l'employe : ");
        Scanner scanner2 = new Scanner(System.in);
        String prenomEmploye = scanner2.nextLine();

        System.out.println("Salaire de l'employe : ");
        Scanner scanner3 = new Scanner(System.in);
        int salaireEmploye = scanner3.nextInt();

        System.out.println("Poste de l'employe : ");
        System.out.println("1 - Serveur");
        System.out.println("2 - Barman");
        System.out.println("3 - Cuisinier");
        System.out.println("4 - Manager");
        Scanner scanner = new Scanner(System.in);
        int posteEmployee = scanner.nextInt();

        switch (posteEmployee) {
            case 1:
                Serveur newServeur = new Serveur(nomEmploye, prenomEmploye, salaireEmploye);
                listEmployes.add(newServeur);
                break;
            case 2:
                Barman newBarman = new Barman(nomEmploye, prenomEmploye, salaireEmploye);
                listEmployes.add(newBarman);
                break;
            case 3:
                Cuisinier newCuisinier = new Cuisinier(nomEmploye, prenomEmploye, salaireEmploye);
                listEmployes.add(newCuisinier);
                break;
            case 4:
                Manager newManager = new Manager(nomEmploye, prenomEmploye, salaireEmploye);
                listEmployes.add(newManager);
            default:
                System.out.println("Commande inconnue");
        }
    }

    public void suppressionEmploye() {
        System.out.println("Quel employé souhaitez vous supprimer ? (Entrez son numéro)");
        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();
        listEmployes.remove(index);
    }

    public void addEmploye(Employee newEmploye) {
        listEmployes.add(newEmploye);
    }

    public ArrayList<Employee> getListEmployes() {
        return listEmployes;
    }

    public ArrayList<String> getListEmploi() {
        return listEmploi;
    }

    public void supprimerEmploye(Employee employee) {
        listEmployes.remove(employee);
    }
}
