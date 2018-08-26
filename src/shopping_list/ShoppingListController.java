package shopping_list;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
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
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import manager.ShoppingListManager;
import shopping_item.ShoppingItemController;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class ShoppingListController {

    //Properties
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXTreeTableView<ShoppingList> shoppingListTable;

    @FXML
    private JFXTreeTableColumn<ShoppingList, String> nameColumn;

    @FXML
    private JFXTreeTableColumn<ShoppingList, LocalDate> dateColumn;

    @FXML
    private JFXTreeTableColumn<ShoppingList, Integer> pendingColumn;

    @FXML
    private JFXTreeTableColumn<ShoppingList, Double> estimateColumn;

    @FXML
    private JFXButton addButton;

    private ObservableList<ShoppingList> data;

    @FXML
    public void initialize() {

        DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        this.data = FXCollections.observableArrayList(ShoppingListManager.getInstance().getShoppingLists());

        setupCellValueFactory(nameColumn, ShoppingList::nameProperty);
        setupCellValueFactory(dateColumn, ShoppingList::dateProperty);
        setupCellValueFactory(pendingColumn, bl -> bl.pendingProductsProperty().asObject());
        setupCellValueFactory(estimateColumn, bl -> bl.estimatePendingProperty().asObject());
        estimateColumn.setCellFactory(tc -> new JFXTreeTableCell<ShoppingList, Double>(){
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if(!empty){
                    DecimalFormat df = new DecimalFormat("#.00");
                    String valueStr = df.format(item);
                    setText("Q" + valueStr);
                }
            }
        });

        shoppingListTable.setRoot(new RecursiveTreeItem<>(this.data, RecursiveTreeObject::getChildren));
        shoppingListTable.setShowRoot(false);

    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<ShoppingList, T> column, Function<ShoppingList, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<ShoppingList, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    public void openAddShoppingListWindow(ActionEvent event) {
        Parent root;
        try {
            // Cargar la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddListScene.fxml"));
            root = loader.load();
            Stage stage = new Stage();

            JFXDecorator decorator = new JFXDecorator(stage, root, false, false, false);
            decorator.setCustomMaximize(false);
            stage.setTitle("Lista de Compras UVG - Gus");
            Scene scene = new Scene(decorator, 700, 550);
            scene.getStylesheets().add(ShoppingListController.class.getResource("../main/main.css").toExternalForm());
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

    public void openEditShoppingListWindow(ActionEvent event) {
        Parent root;
        try {
            ShoppingList shoppingListToEdit = shoppingListTable.getSelectionModel().selectedItemProperty().get().getValue();
            if (shoppingListToEdit != null) {
                // Cargar la nueva ventana
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../shopping_item/ShoppingItemScene.fxml"));
                root = loader.load();
                Stage stage = new Stage();

                JFXDecorator decorator = new JFXDecorator(stage, root, false, false, false);
                decorator.setCustomMaximize(false);
                stage.setTitle("Lista de Compras UVG - Gus");
                Scene scene = new Scene(decorator, 700, 550);
                scene.getStylesheets().add(ShoppingListController.class.getResource("../main/main.css").toExternalForm());
                stage.setScene(scene);

                //Setting first
                ShoppingItemController controller = loader.getController();
                controller.setInitUI(shoppingListToEdit.getName());

                stage.setResizable(false);
                // Muestra la ventana
                stage.show();
                // Hide this current window (if this is what you want)
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } else {
                JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
                snackbar.show("Por favor, selecciona una lista", 2500);
            }
        } catch (NullPointerException ex){
            JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
            snackbar.show("Por favor, selecciona una lista", 2500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteBuyList(){
        try {
            ShoppingList shoppingListToDelete = shoppingListTable.getSelectionModel().selectedItemProperty().get().getValue();
            if (shoppingListToDelete != null) {
                this.data.remove(shoppingListToDelete);
                ShoppingListManager.getInstance().deleteShoppingList(shoppingListToDelete);
                JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
                snackbar.show("Lista eliminada exitosamente", 2500);
                shoppingListTable.getSelectionModel().clearSelection();
            } else {
                JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
                snackbar.show("Por favor, selecciona una lista", 2500);
            }
        } catch (NullPointerException ex) {
            JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
            snackbar.show("Por favor, selecciona una lista", 2500);
        }
    }

    public void backToMainWindow(ActionEvent event) {
        Parent root;
        try {
            // Cargar la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShoppingListScene.fxml"));
            root = loader.load();
            Stage stage = new Stage();

            JFXDecorator decorator = new JFXDecorator(stage, root, false, false, false);
            decorator.setCustomMaximize(false);
            stage.setTitle("Lista de Compras UVG - Gus");
            Scene scene = new Scene(decorator, 700, 550);
            scene.getStylesheets().add(AddListController.class.getResource("../main/main.css").toExternalForm());
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
}
