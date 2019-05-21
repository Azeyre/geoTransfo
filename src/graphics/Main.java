package graphics;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			
			URL url = new File("ressources/fxml/mainWindow.fxml").toURL();
			Parent settings = FXMLLoader.load(url);
			primaryStage.setScene(new Scene(settings));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Echec lors du chargement du fichier ressources/fxml/mainWindow.fxml");
			System.exit(1);
		}
	}
	
}
