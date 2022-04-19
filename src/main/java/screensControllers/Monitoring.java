package screensControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class Monitoring {
    @FXML
    private AnchorPane monitoringPane;

    public void launchGestionEmployes() {
        Parent pane = null;
        try {
            pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ecransMonitoring/gestionEmployes.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        monitoringPane.getChildren().setAll(pane);
    }

    public void launchPerformances() {
        Parent pane = null;
        try {
            pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ecransMonitoring/performances.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        monitoringPane.getChildren().setAll(pane);
    }

    public void launchProgrammation() {
        Parent pane = null;
        try {
            pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ecransMonitoring/programmation.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        monitoringPane.getChildren().setAll(pane);
    }

    public void launchReserves() {
        Parent pane = null;
        try {
            pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ecransMonitoring/reserves.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        monitoringPane.getChildren().setAll(pane);
    }
}
