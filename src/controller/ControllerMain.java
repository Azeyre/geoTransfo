package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import forme.Carre;
import forme.Triangle;
import graphics.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import transforms.Composition;
import transforms.IComposition;
import transforms.LibraryException;
import transforms.elementaires.Transformation;
import transforms.mobile.Maison;
import transforms.mobile.MaisonSansPorte;
import transforms.mobile.Motif;
import transforms.mobile.MotifConcret;

public class ControllerMain {
	
	@FXML Slider zoomSlider;
	@FXML CheckBox buttonGrille;
	@FXML StackPane grille;
	@FXML MenuBar menuBar;
	@FXML HBox saisie;
	@FXML TextField x;
	@FXML TextField y;
	@FXML RadioMenuItem menuHistorique;
	
	public static IComposition composition;
	private static List<Node> allNodes;
	private static int nbTransition = 0;
	private static ArrayList<Boolean> display;
	public static double zoomActuel = 40;
	public static ListView<String> list;
	public static double xSaisie, ySaisie;
	private static Pane pane;
	public static String matrices;
	private double lastX, lastY;
	private double zoomX = 300, zoomY = 200;
	
	public void initialize() {
		buttonGrille.setSelected(true);
		zoomSlider.setValue(50);
		pane = new Pane();
        composition = new Composition();
        pane.getChildren().add(0,composition.getGrille(pane));
        StackPane.setAlignment(menuBar, Pos.TOP_LEFT);
        StackPane.setAlignment(buttonGrille, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(zoomSlider, Pos.TOP_RIGHT);
        StackPane.setAlignment(saisie, Pos.BOTTOM_LEFT);
        composition.setZoom(zoomActuel, zoomX, zoomY);
        
        list = new ListView<>();
        list.setPadding(new Insets(28,0,0,0));
        list.maxWidth(200);
        list.setPrefSize(200, 300);
        pane.getChildren().add(list);
        list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        list.setOnKeyPressed(e -> {
        	KeyCode k = e.getCode();
        	List<Integer> listeSelectionnes = list.getSelectionModel().getSelectedIndices();
        	if(k.equals(KeyCode.DELETE) && listeSelectionnes.size() != 0) {
        		for(int i = listeSelectionnes.size()-1 ; i >= 0 ; i--) {
        			int index = listeSelectionnes.get(i);
        			pane.getChildren().removeAll(allNodes);
            		composition.getSequence().remove(index);
            		display.remove(index);
            		allNodes.remove(index + 1);
            		try {
            			allNodes = composition.draw(display);
            		} catch (LibraryException ex) {
            			ex.printStackTrace();
            		}
            		pane.getChildren().addAll(allNodes);
            		list.getItems().remove(index);
            		nbTransition--;
        		}
        	}
        });
        
        zoomSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
        	composition.setZoom((double) newValue, pane.getScene().getWindow().getWidth() / 2, pane.getScene().getWindow().getHeight() / 2);
        	zoomActuel = (double) newValue;
        });
        
        x.textProperty().addListener((observable, oldValue, newValue) -> {
        	if(!newValue.matches("[\\-]?[0-9]*[\\.]?[0-9]*")) {
        		x.setText(oldValue);
            } else {
            	xSaisie = Double.valueOf(newValue);
            }
        });
        
        y.textProperty().addListener((observable, oldValue, newValue) -> {
        	if(!newValue.matches("[\\-]?[0-9]*[\\.]?[0-9]*")) {
        		y.setText(oldValue);
            } else {
            	ySaisie = Double.valueOf(newValue);
            }
        });
        
        list.setPlaceholder(new Label("Historique vide...\n(Ctrl + Clic multiselection)"));
        
        display = new ArrayList<>();
        display.add(true);
        try {
			allNodes = composition.draw(display);
		} catch (LibraryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Pane : " + pane.getChildren().size());
        grille.getChildren().add(0, pane);
        pane.getChildren().addAll(allNodes);
        
        pane.setOnMousePressed(e -> {
        	pane.getScene().setCursor(Cursor.CLOSED_HAND);
        	lastX = e.getX();
        	lastY = e.getY();
        });
        
        pane.setOnMouseReleased(e -> {
        	pane.getScene().setCursor(Cursor.DEFAULT);
        });
        
        pane.setOnMouseDragged(e -> {
        	zoomX -= lastX - e.getX();
        	zoomY -= lastY - e.getY();
        	composition.setZoom(zoomActuel, zoomX, zoomY);
        	lastX = e.getX();
        	lastY = e.getY();
        });
	}
	
	public void apply() {
		if(buttonGrille.isSelected()) {
			pane.getChildren().addAll(allNodes);
		} else pane.getChildren().removeAll(allNodes);
	}
	
	public void nouveau() {
		Main newWindow = new Main();
		Stage s = new Stage();
		try {
			newWindow.start(s);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Impossible de créer une nouvelle fenêtre !");
		}
	}
	
	public void quitter() {
		Stage s = (Stage) zoomSlider.getScene().getWindow();
		s.close();
	}
	
	public void animer() {
		final int firstStep = 0;
        try {
            final Motif mobile = composition.getStep(firstStep);
            mobile.setStroke(Color.BLUE);
            pane.getChildren().add(mobile.toGroup());
            composition.animate(
                    mobile.toGroup(),
                    firstStep,
                    nbTransition,
                    e -> pane.getChildren().remove(mobile.toGroup())
            ).play();    // Animation entre les étapes firstStep et lastStep
        } catch (LibraryException e) {
            e.printStackTrace();
        }
	}
	
	public void matrice() {
		List<Integer> listeSelectionnes = list.getSelectionModel().getSelectedIndices();
		matrices = "";
		try {
			if(listeSelectionnes.size() != 0) {
				for(Integer i: listeSelectionnes) {
					matrices += "Transformation n°" + (i+1)  + " : " + Arrays.deepToString(composition.getAtomicMatrix(i)) + "\n";
				}
			} else matrices = "Toutes les transformations : " + Arrays.deepToString(composition.getComposedMatrix(nbTransition));
        } catch (LibraryException e) {
            e.printStackTrace();
        }
		Stage s;
		try {
			s = new Stage();
			@SuppressWarnings("deprecation")
			URL url = new File("ressources/fxml/matrices.fxml").toURL();
			Parent settings = FXMLLoader.load(url);
			s.setScene(new Scene(settings));
			s.initModality(Modality.APPLICATION_MODAL);
			s.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		Stage s;
		try {
			s = new Stage();
			@SuppressWarnings("deprecation")
			URL url = new File("ressources/fxml/rotation.fxml").toURL();
			Parent settings = FXMLLoader.load(url);
			s.setScene(new Scene(settings));
			s.initModality(Modality.APPLICATION_MODAL);
			s.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void fenetreHomothetie() {
		Stage s;
		try {
			s = new Stage();
			@SuppressWarnings("deprecation")
			URL url = new File("ressources/fxml/homothetie.fxml").toURL();
			Parent settings = FXMLLoader.load(url);
			s.setScene(new Scene(settings));
			s.initModality(Modality.APPLICATION_MODAL);
			s.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void redraw() {
		pane.getChildren().removeAll(allNodes);
		try {
			allNodes = composition.draw(display);
		} catch (LibraryException e) {
			e.printStackTrace();
		}
		pane.getChildren().addAll(allNodes);
	}
	
	public void afficherHistorique() {
		if(menuHistorique.isSelected()) {
			pane.getChildren().add(list);
		} else pane.getChildren().remove(list);
	}
	
	public void maison() {
		composition.setMotif(new Maison((Composition) this.composition));
		redraw();
	}
	
	public void carre() {
		composition.setMotif(new Carre(this.composition));
		redraw();
	}
	
	public void triangle() {
		composition.setMotif(new Triangle(this.composition));
		redraw();
	}
	
	public void showCoord(MouseEvent mouseEvent) {
        double xMouse = mouseEvent.getX();
        double yMouse = mouseEvent.getY();
        x.setText("" + composition.xMouseToMath(xMouse));
        y.setText("" + composition.yMouseToMath(yMouse));
    }
	
	public static void ajouter(Transformation t) {
		pane.getChildren().removeAll(allNodes);
		nbTransition++;
		composition.add(t);
		list.getItems().add(t.toString());
		display.add(true);
		try {
			allNodes = composition.draw(display);
		} catch (LibraryException e) {
			e.printStackTrace();
		}
		pane.getChildren().addAll(allNodes);
	}
}
