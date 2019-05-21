package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerRotation {
	
	@FXML Button button;
	@FXML TextField rotation; 
	
	public void initialize() {
		
	}
	
	public void apply() {
		Stage stage  = (Stage) rotation.getScene().getWindow();
		stage.close();
	}

}
