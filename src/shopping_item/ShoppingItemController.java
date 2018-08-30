package shopping_item;

import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.scene.control.Button;
import javafx.scene.control.TreeTableCell;
import javafx.util.Callback;
import shopping_list.ShoppingList;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import manager.ShoppingListManager;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.function.Function;

public class ShoppingItemController {

    //Properties
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXTreeTableView<ShoppingItem> shoppingItemTable;

    @FXML
    private JFXTreeTableColumn<ShoppingItem, String> nameColumn;

    @FXML
    private JFXTreeTableColumn<ShoppingItem, Integer> quantityColumn;

    @FXML
    private JFXTreeTableColumn<ShoppingItem, Double> unitPriceColumn;

    @FXML
    private JFXTreeTableColumn<ShoppingItem, Double> totalColumn;

    @FXML
    private JFXTreeTableColumn<ShoppingItem, Boolean> stateColumn;

    @FXML
    private JFXButton addButton;

    @FXML
    private Label listTitleLabel;

    @FXML
    private Label listDescriptionLabel;

    @FXML
    private Label grandTotalLabel;

    private ObservableList<ShoppingItem> data;
    private String currentShoppingListName;

    @FXML
    public void initialize() {
        //Setting values of the columns
        setupCellValueFactory(nameColumn, ShoppingItem::nameProperty);
        setupCellValueFactory(quantityColumn, si -> si.quantityProperty().asObject());
        setupCellValueFactory(unitPriceColumn, si -> si.priceProperty().asObject());
        unitPriceColumn.setCellFactory(tc -> new JFXTreeTableCell<ShoppingItem, Double>(){
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if(!empty){
                    // Setting Double as String with currency
                    DecimalFormat df = new DecimalFormat("#.00");
                    String valueStr = df.format(item);
                    setText(item == 0.00 ? "Q0.00" : "Q" + valueStr);
                }
            }
        });

        setupCellValueFactory(totalColumn, si -> si.totalProperty().asObject());
        totalColumn.setCellFactory(tc -> new JFXTreeTableCell<ShoppingItem, Double>(){
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if(!empty){
                    // Setting Double as String with currency
                    DecimalFormat df = new DecimalFormat("#.00");
                    String valueStr = df.format(item);
                    setText(item == 0.00 ? "Q0.00" : "Q" + valueStr);
                }
            }
        });

        setupCellValueFactory(stateColumn, si -> si.isCompleteProperty().asObject());
        stateColumn.setCellFactory(tc -> new JFXTreeTableCell<ShoppingItem, Boolean>(){
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                // Setting Boolean as String
                setText(empty ? null : item ? "Comprado" : "Pendiente" );
            }
        });
    }

    public void setInitUI(String listName){
        this.currentShoppingListName = listName;
        //Get list from the Shopping Manager for shows shopping items of a specific list
        ShoppingList currentShoppingList = ShoppingListManager.getInstance().getShoppingListByName(listName);

        DecimalFormat df = new DecimalFormat("#.00");
        String grandTotal = df.format(currentShoppingList.getEstimatePendingTotal());

        //
        this.listTitleLabel.setText(currentShoppingList.getName());
        this.listDescriptionLabel.setText(currentShoppingList.getDescription());
        this.grandTotalLabel.setText(currentShoppingList.getEstimatePendingTotal() == 0.00 ? "Q0.00" : "Q" + grandTotal);

        this.data = FXCollections.observableArrayList(currentShoppingList.getAllShoppingItems());
        shoppingItemTable.setRoot(new RecursiveTreeItem<>(this.data, RecursiveTreeObject::getChildren));
        shoppingItemTable.setShowRoot(false);
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<ShoppingItem, T> column, Function<ShoppingItem, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<ShoppingItem, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    public void backToMainWindow(ActionEvent event) {
        Parent root;
        try {
            // Cargar la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../shopping_list/ShoppingListScene.fxml"));
            root = loader.load();
            Stage stage = new Stage();

            JFXDecorator decorator = new JFXDecorator(stage, root, false, false, false);
            decorator.setCustomMaximize(false);
            stage.setTitle("Lista de Compras UVG - Gus");
            Scene scene = new Scene(decorator, 700, 550);
            scene.getStylesheets().add(ShoppingItemController.class.getResource("../main/main.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
            // Muestra la ventana
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openAddShoppingItemWindow(ActionEvent event) {
        Parent root;
        try {
            // Cargar la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddItemScene.fxml"));
            root = loader.load();
            Stage stage = new Stage();

            JFXDecorator decorator = new JFXDecorator(stage, root, false, false, false);
            decorator.setCustomMaximize(false);
            stage.setTitle("Lista de Compras UVG - Gus");
            Scene scene = new Scene(decorator, 700, 550);
            scene.getStylesheets().add(ShoppingItemController.class.getResource("../main/main.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);

            AddItemController controller = loader.getController();
            controller.setInitUI(this.currentShoppingListName);
            // Muestra la ventana
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeShoppingItemState(ActionEvent event){
        try {
            ShoppingItem shoppingItemToUpdate = shoppingItemTable.getSelectionModel().selectedItemProperty().get().getValue();
            if (shoppingItemToUpdate != null) {
                boolean isComplete = ((JFXButton)event.getSource()).getText().equals("COMPRADO");
                ShoppingListManager.getInstance().updateShoppingItemState(currentShoppingListName, shoppingItemToUpdate, isComplete);
                updateUI();
            } else {
                JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
                snackbar.show("Por favor, selecciona un articulo", 2500);
            }
        } catch (NullPointerException ex){
            JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
            snackbar.show("Por favor, selecciona un articulo", 2500);
        }
    }

    public void updateUI(){
        ShoppingList currentShoppingList = ShoppingListManager.getInstance().getShoppingListByName(currentShoppingListName);
        DecimalFormat df = new DecimalFormat("#.00");
        String grandTotal = df.format(currentShoppingList.getEstimatePendingTotal());
        this.grandTotalLabel.setText(currentShoppingList.getEstimatePendingTotal() == 0.00 ? "Q0.00" : "Q" + grandTotal);
    }

}
