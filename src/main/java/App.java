import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
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
        System.out.println("4- Ecran Monitoring");

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

    @Override
    public void start(Stage stage) {
        try {
            Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
            stage.setTitle("Page principale");;
            stage.setScene(new Scene(root,600,400));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}