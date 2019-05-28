package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import transforms.elementaires.Translation;

public class ControllerTranslation {
	
	@FXML TextField X;
	@FXML TextField Y;
	
	public void apply() {
		try {
			double x = Double.valueOf(X.getText());
			double y = Double.valueOf(Y.getText());
			ControllerMain.ajouter(new Translation(x,y));
			Stage s = (Stage) X.getScene().getWindow();
			s.close();
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
	}

}
