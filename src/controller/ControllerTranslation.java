package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerTranslation {
	
	@FXML Button button;
	@FXML TextField X;
	@FXML TextField Y;
	
	public void affiche() {
		
	}
	
	public void apply() {
		Stage stage  = (Stage) X.getScene().getWindow();
		stage.close();
	}

}
