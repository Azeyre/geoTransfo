package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import transforms.Composition;
import transforms.IComposition;
import transforms.LibraryException;
import transforms.mobile.Motif;
import transforms.élémentaires.Transformation;
import transforms.élémentaires.Translation;

public class ControllerMain {
	
	@FXML Slider zoomSlider;
	@FXML CheckBox buttonGrille;
	@FXML StackPane grille;
	@FXML MenuBar menuBar;

	private static IComposition composition;
	private static List<Node> allNodes;
	private static int nbTransition = 0;
	private static ArrayList<Boolean> display;
	
	public void initialize() {
		buttonGrille.setSelected(true);
		zoomSlider.setValue(50);
		
        composition = new Composition();
        composition.setZoom(50, 0, 29);
        grille.getChildren().add(0,composition.getGrille());
        StackPane.setAlignment(menuBar, Pos.TOP_LEFT);
        StackPane.setAlignment(buttonGrille, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(zoomSlider, Pos.TOP_RIGHT);
        
        zoomSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
        	composition.setZoom((double) newValue, 0, 0);
        });
        display = new ArrayList<>();
        display.add(true);
        ajouter(new Translation(0,0));
        try {
			allNodes = composition.draw(display);
		} catch (LibraryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        grille.getChildren().addAll(allNodes);
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
		try {
            final Motif mobile = composition.getStep(1);
            mobile.setStroke(Color.BLUE);
            grille.getChildren().add(mobile.toGroup());
            composition.animate(
                    mobile.toGroup(),
                    1,
                    nbTransition,
                    e -> grille.getChildren().remove(mobile.toGroup())
            ).play();    // Animation entre les étapes 1 et 3
        } catch (LibraryException e) {
            e.printStackTrace();
        }
	}
	
	public void matrice() {
		System.out.println("Matrice");
	}
	
	public void fenetreTranslation() {
		Stage s;
		try {
			s = new Stage();
			@SuppressWarnings("deprecation")
			URL url = new File("ressources/fxml/translation.fxml").toURL();
			Parent settings = FXMLLoader.load(url);
			s.setScene(new Scene(settings));
			s.initModality(Modality.APPLICATION_MODAL);
			s.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void fenetreRotation() {
		System.out.println("Rotation");
	}
	
	public void fenetreHomothetie() {
		System.out.println("Homothetie");
	}
	
	public void ajoutPoint() {
		
	}
	
	public void ajoutCarre() {
		
	}
	
	public void showCoord(MouseEvent mouseEvent) {
		Stage stage = (Stage) zoomSlider.getScene().getWindow();
        double xMouse = mouseEvent.getX() - stage.getWidth() / 2;
        double yMouse = mouseEvent.getY() - stage.getHeight() / 2 + 29;
        System.out.println("X vaut : " + composition.xMouseToMath(xMouse));
        System.out.println("Y vaut : " + composition.yMouseToMath(yMouse));
    }
	
	public static void ajouter(Transformation t) {
		nbTransition++;
		composition.add(t);
		display.add(true);
		try {
			allNodes = composition.draw(display);
		} catch (LibraryException e) {
			e.printStackTrace();
		}
	}
}
