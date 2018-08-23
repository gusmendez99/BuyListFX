package sample;

import buylist.BuyList;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class Controller {

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



}
