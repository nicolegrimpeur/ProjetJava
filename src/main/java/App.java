import screens.ManagCourses;
import screens.ManagEffectif;
import screens.ManagEmployees;
import screens.ManagStocks;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        accueil();
    }
    public static void accueil() {
        System.out.println("Quel écran souhaitez vous afficher?");
        System.out.println("1- Ecran prise de commande");
        System.out.println("2- Ecran cuisine");
        System.out.println("3- Ecran bar");
        System.out.println("4- Ecran Monitoring");

        Scanner scanner = new Scanner(System.in);
        int choixEcran = scanner.nextInt();
        System.out.println("Vous avez choisi l'écran: " + choixEcran);

        switch (choixEcran) {
            case 1:
                screens.ScreenOne.ScreenOne();
                break;
            case 2:
                screens.ScreenTwo.screenTwo();
                break;
            case 3:
                screens.ScreenThree.screenThree();
                break;
            case 4:
                screens.ScreenFour.screenFour();
                break;
            default:
                System.err.println("Commande inconnue");
                break;
        }
    }
}