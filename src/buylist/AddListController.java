package buylist;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import manager.BuyListManager;

import java.io.IOException;

public class AddListController {

    @FXML
    private JFXTextField inputName;

    @FXML
    private JFXTextArea inputDescription;

    public void backToMainWindow(ActionEvent event) {
        Parent root;
        try {
            // Cargar la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BuyListScene.fxml"));
            root = loader.load();
            Stage stage = new Stage();

            JFXDecorator decorator = new JFXDecorator(stage, root, false, false, false);
            decorator.setCustomMaximize(false);
            stage.setTitle("Lista de Compras UVG - Gus");
            stage.setScene(new Scene(decorator, 700, 550));
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

        BuyListManager.getInstance().addBuyList(new BuyList(name, description));
        backToMainWindow(event);

    }



}
