package demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.NonInvertibleTransformException;
import transforms.*;
import transforms.mobile.Motif;
import transforms.elementaires.AxialSymetry;
import transforms.elementaires.Rotation;
import transforms.elementaires.Translation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoController {

    @FXML
    Pane pane;

    @FXML
    ToggleButton displayAll;

    @FXML
    ToggleButton zoom;

    @FXML
    ToggleButton geometry;

    private Composition composition;
    private List<Node> allNodes;

    public void initialize() {
        /*
        Construction de la composition
         */
        try {
            composition = new Composition();
            composition.add(new Translation(1.0, 1.0));                // Etape 0 -> Etape 1
            composition.add(new AxialSymetry(0.0, 0.0, 1.0, 0.0, 0.0, 1.0));
            composition.add(new Rotation(180.0, 3.0, 2.0));     // Etape 1 -> Etape 2
            composition.add(new Translation(2.0, -2.0));                // Etape 2 -> Etape 3
        } catch (NonInvertibleTransformException e) {
            e.printStackTrace();
        }

        pane.getChildren().add(composition.getGrille(pane));
    }

    public void doGeometry(ActionEvent actionEvent) {
        if (geometry.isSelected()) {
            composition.offsetXProperty().set(600.0);
            composition.offsetYProperty().set(300.0);
            composition.scaleProperty().set(30.0);
        } else {
            composition.offsetXProperty().set(400.0);
            composition.offsetYProperty().set(400.0);
            composition.scaleProperty().set(40.0);
        }
    }

    /*
    Affichage de l'éta initial (0) et des étapes 1 et 4
     */
    public void doMontrer(ActionEvent actionEvent) {
        if (allNodes == null) {
            ArrayList<Boolean> display = new ArrayList<>(
                    Arrays.asList(true, true, true, true, true)                 // Affichage des états 0 (initial), 1, 3 et 4
            );
            try {
                allNodes = composition.draw(display);
            } catch (LibraryException e) {
                e.printStackTrace();
            }
        }
        if (displayAll.isSelected()) {
            pane.getChildren().addAll(allNodes);
        } else {
            pane.getChildren().removeAll(allNodes);
        }
    }

    /*
    Affichage de quelques matrices
     */
    public void doMatrices(ActionEvent actionEvent) {
        try {
            System.out.println(Arrays.deepToString(composition.getAtomicMatrix(1)));     // Affiche la matrice associée à la transformation 1
            System.out.println(Arrays.deepToString(composition.getComposedMatrix(2)));   // Affiche la matrice associée à la composition (étape 1, étape 2)
        } catch (LibraryException e) {
            e.printStackTrace();
        }
    }

    /*
    Animation entre l'étape firstStep et l'étape lastStep
     */
    public void doAnimer(ActionEvent actionEvent) {
        final int firstStep = 0;
        final int lastStep = 4;
        try {
            final Motif mobile = composition.getStep(firstStep);
            mobile.setStroke(Color.BLUE);
            pane.getChildren().add(mobile.toGroup());
            composition.animate(
                    mobile.toGroup(),
                    firstStep,
                    lastStep,
                    e -> pane.getChildren().remove(mobile.toGroup())
            ).play();    // Animation entre les étapes firstStep et lastStep
        } catch (LibraryException e) {
            e.printStackTrace();
        }
    }

    public void showCoord(MouseEvent mouseEvent) {
        double xMouse = mouseEvent.getX();
        double yMouse = mouseEvent.getY();
        System.out.println("X vaut : " + composition.xMouseToMath(xMouse));
        System.out.println("Y vaut : " + composition.yMouseToMath(yMouse));
    }

    /*
    Démonstation de changement d'échelle et d'origine
     */
    public void doZoom(ActionEvent actionEvent) {
        if (! zoom.isSelected()) {
            composition.setZoom(40.0, 400.0, 400.0);
        } else {
            composition.setZoom(20.0, 200.0, 300.0);
        }
    }
}
