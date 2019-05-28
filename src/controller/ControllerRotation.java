package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import transforms.elementaires.Rotation;

public class ControllerRotation {
	
	@FXML Button button;
	@FXML TextField rotation;
	@FXML Label erreur;
	@FXML TextField X;
	@FXML TextField Y;
	
	public void apply() {
		try {
			double degres = Double.valueOf(rotation.getText());
			double x = Double.valueOf(X.getText());
			double y = Double.valueOf(Y.getText());
			ControllerMain.ajouter(new Rotation(degres, x, y));
			Stage stage  = (Stage) rotation.getScene().getWindow();
			stage.close();
		} catch (NumberFormatException e) {
			erreur.setTextFill(Color.RED);
			erreur.setText("Entrée invalide !");
		}
	}
}
