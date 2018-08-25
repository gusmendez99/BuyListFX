package main;

import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../buylist/BuyListScene.fxml"));
        //primaryStage.initStyle(StageStyle.UNDECORATED);

        JFXDecorator decorator = new JFXDecorator(primaryStage, root, false, false, false);
        decorator.setCustomMaximize(false);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(decorator, 700, 550));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
