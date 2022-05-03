import employee.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import journee.JourneeManager;

import java.util.Objects;
import java.util.Scanner;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public static void accueil() {
        System.out.println("Quel écran souhaitez vous afficher?");
        System.out.println("1- Ecran prise de commande");
        System.out.println("2- Ecran cuisine");
        System.out.println("3- Ecran bar");
        System.out.println("4- Ecran screensControllers.Monitoring");

        Scanner scanner = new Scanner(System.in);
        int choixEcran = scanner.nextInt();
        System.out.println("Vous avez choisi l'écran: " + choixEcran);

        switch (choixEcran) {
            case 1 -> screens.ScreenOne.ScreenOne();
            case 2 -> screens.ScreenTwo.screenTwo();
            case 3 -> screens.ScreenThree.screenThree();
            case 4 -> screens.ScreenFour.screenFour();
            default -> System.err.println("Commande inconnue");
        }
    }

    public void start(Stage stage) {
        ManagEmployees.getInstance().addEmploye(new Serveur("Barrat", "Nicolas", 2000));
        ManagEmployees.getInstance().addEmploye(new Serveur("Lecocq", "Judith", 2000));
        ManagEmployees.getInstance().addEmploye(new Barman("The", "Barman", 2000));
        ManagEmployees.getInstance().addEmploye(new Cuisinier("The", "Cuisinier", 2000));
        ManagEmployees.getInstance().addEmploye(new Cuisinier("Un", "Cuisinier", 2000));
        ManagEmployees.getInstance().addEmploye(new Cuisinier("L'autre", "Cuisinier", 2000));
        ManagEmployees.getInstance().addEmploye(new Cuisinier("Dernier", "Cuisinier", 2000));
        ManagEmployees.getInstance().addEmploye(new Manager("The", "Manager", 2000));

        JourneeManager.getInstance().delete("Additions/");

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/main.fxml")));
            stage.setTitle("Page principale");

            stage.setScene(new Scene(root, 800, 700));
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("logo.png"))));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}