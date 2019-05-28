package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ControllerMatrices {

	@FXML TextArea matriceArea;
	
	public void initialize() {
		matriceArea.setText(ControllerMain.matrices);
	}
	
}
