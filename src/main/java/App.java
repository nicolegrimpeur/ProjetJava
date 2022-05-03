import employee.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import journee.JourneeManager;

import java.util.Objects;
import java.util.Properties;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
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
            // on récupère le fichier de propriétés du projet (src/main/resources)
            final Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("project.properties"));

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/main.fxml")));
            stage.setTitle(properties.getProperty("nomApplication"));
            stage.setResizable(false);

            stage.setScene(new Scene(root, 800, 700));
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("logo.png"))));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}