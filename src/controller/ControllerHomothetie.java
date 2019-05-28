package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import transforms.elementaires.Homothetie;

public class ControllerHomothetie {
	
	@FXML Button button;
	@FXML TextField X;
	@FXML TextField Y;
	@FXML TextField distance;
	@FXML Label erreur;
	
	public void apply() {
		try {
			double dist = Double.valueOf(distance.getText());
			double x = Double.valueOf(X.getText());
			double y = Double.valueOf(Y.getText());
			ControllerMain.ajouter(new Homothetie(dist,x,y));
			Stage stage  = (Stage) distance.getScene().getWindow();
			stage.close();
		} catch (NumberFormatException e) {
			erreur.setTextFill(Color.RED);
			erreur.setText("Entrée invalide !");
		}
	}
}
