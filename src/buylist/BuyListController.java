package buylist;

import buylist.BuyList;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class BuyListController {

    //Properties
    @FXML
    private JFXTreeTableView<BuyList> buyListTable;

    @FXML
    private JFXTreeTableColumn<BuyList, String> nameColumn;

    @FXML
    private JFXTreeTableColumn<BuyList, LocalDate> dateColumn;

    @FXML
    private JFXTreeTableColumn<BuyList, Integer> pendingColumn;

    @FXML
    private JFXTreeTableColumn<BuyList, Double> estimateColumn;

    @FXML
    public void initialize() {

        DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        ObservableList<BuyList> data = FXCollections.observableArrayList(
                new BuyList("ToDos"),
                new BuyList("Amazon Wish List")
        );

        setupCellValueFactory(nameColumn, BuyList::nameProperty);
        setupCellValueFactory(dateColumn, BuyList::dateProperty);
        setupCellValueFactory(pendingColumn, bl -> bl.pendingProductsProperty().asObject());
        setupCellValueFactory(estimateColumn, bl -> bl.estimatePendingProperty().asObject());

        buyListTable.setRoot(new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren));
        buyListTable.setShowRoot(false);

    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<BuyList, T> column, Function<BuyList, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<BuyList, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }
    /*
    public void openNewWindow(ActionEvent event) {
        Parent root;
        try {
            // Cargar la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddListScene.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Nueva lista");
            stage.setScene(new Scene(root, 450, 450));

            // Manda la persona seleccionada
            TestSceneController testSceneController = loader.getController();
            Person selectedPerson = peopleTable.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                testSceneController.setName("" + selectedPerson);
            } else {
                testSceneController.setName("Mano, no hay nadie seleccionado!");
            }

            // Muestra la ventana
            stage.show();
            // Hide this current window (if this is what you want)
            // ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
