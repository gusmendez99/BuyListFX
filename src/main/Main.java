package main;

import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../buylist/ShoppingListScene.fxml"));
        //primaryStage.initStyle(StageStyle.UNDECORATED);

        JFXDecorator decorator = new JFXDecorator(primaryStage, root, false, false, false);
        decorator.setCustomMaximize(false);
        primaryStage.setTitle("Lista de Compras UVG - Gus");
        Scene scene = new Scene(decorator, 700, 550);
        scene.getStylesheets().add(Main.class.getResource("main.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
