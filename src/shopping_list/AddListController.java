package shopping_list;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import manager.ShoppingListManager;
import shopping_item.ShoppingItemController;

import java.io.IOException;

public class AddListController {

    @FXML
    private JFXTextField inputName;

    @FXML
    private JFXTextArea inputDescription;

    @FXML
    private AnchorPane anchorPane;

    public void backToMainWindow(ActionEvent event) {
        Parent root;
        try {
            // Cargar la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ShoppingListScene.fxml"));
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

    public void addNewBuyList(ActionEvent event){
        String name = inputName.getText();
        String description = inputDescription.getText();

        if(ShoppingListManager.getInstance().addShoppingList(name, description)){
            //backToMainWindow(event);
            openEditShoppingListWindow(event);
        } else {
            JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
            snackbar.show("Ya existe una lista con el mismo nombre", 2500);
        }
    }

    public void openEditShoppingListWindow(ActionEvent event) {
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
            controller.setInitUI(inputName.getText());

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
