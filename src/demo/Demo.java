package demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Demo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("demo.fxml"));
        primaryStage.setTitle("Projet");
        primaryStage.setScene(new Scene(root, 300, 600));
        primaryStage.setMinHeight(600.0);
        primaryStage.setMinWidth(600.0);
        primaryStage.setWidth(800.0);
        primaryStage.setHeight(800.0);
        primaryStage.setMaxHeight(Double.MAX_VALUE);
        primaryStage.setMaxWidth(Double.MAX_VALUE);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
