package screensControllers.ecransMonitoring;

import files.HtmlManager;
import ingredients.EnumIngredients;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import journee.JourneeManager;

public class Reserves {
    @FXML
    Spinner<Integer> spinnerNbIngredient;
    @FXML
    TableView<EnumIngredients> tableIngredients;

    @FXML
    void initialize() {
        initTable();
        initSpinner();
    }

    private void initTable() {
        tableIngredients.setDisable(false);
        tableIngredients.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        tableIngredients.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("stocks"));

        tableIngredients.setItems(FXCollections.observableArrayList(EnumIngredients.values()));
    }

    private void initSpinner() {
        spinnerNbIngredient.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer oldValue, Integer newValue) {
                if (newValue == null)
                    spinnerNbIngredient.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));

                EnumIngredients ingredient = tableIngredients.getSelectionModel().getSelectedItem();
                ingredient.setStocks(spinnerNbIngredient.getValue());
                tableIngredients.refresh();
            }
        });
    }

    public void clickTable() {
        if (!JourneeManager.getInstance().isJourneeEnCours) {
            spinnerNbIngredient.setDisable(false);
            spinnerNbIngredient.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, tableIngredients.getSelectionModel().getSelectedItem().getStocksInitial()));
        }
    }

    public void impressionListeCourse() {
        HtmlManager.generateListeDeCourse();
    }
}
