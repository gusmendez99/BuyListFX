package shopping_item;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.sun.xml.internal.ws.util.StringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import manager.ShoppingListManager;
import shopping_list.AddListController;
import shopping_list.ShoppingListController;

import java.io.IOException;

public class AddItemController {
    @FXML
    private JFXTextField inputName;

    @FXML
    private JFXTextField inputPrice;

    @FXML
    private JFXTextField inputQuantity;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label toolbarTitle;

    private String currentShoppingListName;

    public void setInitUI(String nameList){
        this.currentShoppingListName = nameList;
        this.toolbarTitle.setText(nameList);
    }

    public void addNewShoppingItem(ActionEvent event){


        if(validateParams()){
            String name = inputName.getText();
            double price = Double.valueOf(inputPrice.getText());
            int quantity = Integer.valueOf(inputQuantity.getText());
            ShoppingItem item = new ShoppingItem(name, quantity, price);
            ShoppingListManager.getInstance().addShoppingItem(currentShoppingListName, item);
            backToMainWindow(event);
        } else {
            JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
            snackbar.show("Error en creacion, intenta de nuevo", 2500);
        }
    }

    private boolean validateParams() {
        try {
            Double.valueOf(inputPrice.getText());
            Integer.valueOf(inputQuantity.getText());
            return !inputName.getText().isEmpty();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void backToMainWindow(ActionEvent event) {
        Parent root;
        try {
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
            controller.setInitUI(this.currentShoppingListName);

            stage.setResizable(false);
            // Muestra la ventana
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
