package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import transforms.elementaires.Translation;

public class ControllerTranslation {
	
	@FXML TextField X;
	@FXML TextField Y;
	@FXML Label erreur;
	
	public void initialize() {
		X.setText(""+ControllerMain.xSaisie);
		Y.setText(""+ControllerMain.ySaisie);
	}
	
	public void apply() {
		try {
			double x = Double.valueOf(X.getText());
			double y = Double.valueOf(Y.getText());
			ControllerMain.ajouter(new Translation(x,y));
			Stage s = (Stage) X.getScene().getWindow();
			s.close();
		} catch(NumberFormatException e) {
			erreur.setTextFill(Color.RED);
			erreur.setText("Entrée invalide !");
		}
	}

}
