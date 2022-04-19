package screensControllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Main {
    @FXML private MenuItem aboutBtn;
    @FXML private AnchorPane rootPane;
    @FXML private Button clearAnchorBtn;

    public void launchPriseDeCommande() {
        Parent pane = null;
        try {
            pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("serveurs.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        rootPane.getChildren().setAll(pane);
        clearAnchorBtn.setVisible(true);
    }

    public void launchEcranBarmans() {
        Parent pane = null;
        try {
            pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("barmans.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        rootPane.getChildren().setAll(pane);
        clearAnchorBtn.setVisible(true);
    }

    public void launchEcranCuisiniers() {
        Parent pane = null;
        try {
            pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("cuisiniers.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        rootPane.getChildren().setAll(pane);
        clearAnchorBtn.setVisible(true);
    }

    public void launchMonitoring() {
        Parent pane = null;
        try {
            pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("monitoring.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        rootPane.getChildren().setAll(pane);
        clearAnchorBtn.setVisible(true);
    }

    public void retour() {
        rootPane.getChildren().clear();
        clearAnchorBtn.setVisible(false);
    }

    public void closeFenetre() {
        Platform.exit();;
    }

    public void about(ActionEvent event) throws IOException {
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
