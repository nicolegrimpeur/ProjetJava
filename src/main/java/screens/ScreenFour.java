package screens;

import java.util.Scanner;

public class ScreenFour {
    //Monitoring
    public void choixAction(){
        System.out.println("Que souhaitez-vous faire ?");
        System.out.println("1- Liste des courses");
        System.out.println("2- Gerer les stocks");
        System.out.println("3- Gestion des employes");
        System.out.println("4- Programme Effectif");

        Scanner scanner = new Scanner(System.in);
        int choixEcran = scanner.nextInt();
    }

}
