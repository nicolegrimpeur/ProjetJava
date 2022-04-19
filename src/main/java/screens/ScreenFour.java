package screens;
import employee.ManagEmployees;

import java.util.Scanner;

public class ScreenFour {
    //screensControllers.Monitoring
    public static void screenFour() {
        System.out.println("Que souhaitez-vous faire ?");
        System.out.println("1- Liste des courses");
        System.out.println("2- Gerer les stocks");
        System.out.println("3- Gestion des employes");
        System.out.println("4- Programme Effectif");
        System.out.println("5- Retour à l'accueil");

        Scanner scanner = new Scanner(System.in);
        int choixEcran = scanner.nextInt();

        switch (choixEcran) {
            case 1:
//                ManagCourses.gestionCourses();
                break;
            case 2:
                ManagStocks.gestionStocks();
                break;
            case 3:
                ManagEmployees.gestionEmploye();
                break;

            case 4:
                ManagEffectif.gestionEffectif();
                break;

            case 5:
                // je vois pas comment faire la lol App.accueil();
            default:
                System.err.println("Commande inconnue");
                break;
        }
    }
}
