package controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;

public class ControllerMain {
	
	@FXML Slider zoomSlider;
	@FXML CheckBox buttonGrille;
	
	public void initialize() {
		buttonGrille.setSelected(true);
		zoomSlider.setValue(50);
	}
	
	public void apply() {
		System.out.println(buttonGrille.isSelected());
	}
	
	public void nouveau() {
		System.out.println("Nouveau");
	}
	
	public void sauvegarder() {
		System.out.println("Sauvegarde");
	}
	
	public void sauvegarderSous() {
		System.out.println("Sauvegarder sous");
	}
	
	public void quitter() {
		System.out.println("Quitter");
	}
	
	public void animer() {
		System.out.println("Animer");
	}
	
	public void matrice() {
		System.out.println("Matrice");
	}
	
	public void fenetreTranslation() {
		System.out.println("Translation");
	}
	
	public void fenetreRotation() {
		System.out.println("Rotation");
	}
	
	public void fenetreHomothetie() {
		System.out.println("Homothetie");
	}

}
