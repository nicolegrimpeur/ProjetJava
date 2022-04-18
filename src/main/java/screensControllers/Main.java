package screensControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class Main {

    public void closeFenetre() {
        System.exit(0);
    }

    @FXML private MenuItem aboutBtn;

    public void about(ActionEvent event) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(aboutBtn.getParentPopup().getOwnerWindow());
        StackPane dialogStackPane = new StackPane();
        dialogStackPane.getChildren().add(new Text("Version : 0.1"));
        Scene dialogScene = new Scene(dialogStackPane, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();

    }
}
