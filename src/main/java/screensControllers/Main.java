package screensControllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public class Main {

    public void closeFenetre() {
        System.exit(0);
    }

    @FXML
    private MenuItem aboutBtn;

    public void about() throws IOException {
        // on récupère le fichier de propriétés du projet (src/main/resources)
        final Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("project.properties"));

        // on crée un popup avec le numéro de version
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(aboutBtn.getParentPopup().getOwnerWindow());

        // Voir https://formations.imt-atlantique.fr/bd_ihm/ihm/listeners/_index.fr.files/Les%20e%CC%81ve%CC%80nements%20%20en%20JavaFX.pdf
        // pour les types de zones
        StackPane dialogStackPane = new StackPane();
        dialogStackPane.getChildren().add(new Text("Version : " + properties.getProperty("version")));
        Scene dialogScene = new Scene(dialogStackPane, 250, 150);
        dialog.setTitle("A propos");
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
