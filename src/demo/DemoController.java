package demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import transforms.*;
import transforms.mobile.Motif;
import transforms.élémentaires.Rotation;
import transforms.élémentaires.Translation;
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

    private IComposition composition;
    private List<Node> allNodes;

    public void initialize() {
        /*
        Construction de la composition
         */
        composition = new Composition();
        composition.add(new Translation(1.0, 1.0));        // Etape 0 -> Etape 1
        composition.add(new Rotation(180.0, 3.0, 2.0));    // Etape 1 -> Etape 2
        composition.add(new Translation(2.0, -2.0));       // Etape 2 -> Etape 3

        pane.getChildren().add(composition.getGrille());
    }

    /*
    Affichage de l'éta initial (0) et des étapes 1 et 3
     */
    public void doMontrer(ActionEvent actionEvent) {
        if (allNodes == null) {
            ArrayList<Boolean> display = new ArrayList<>(
                    Arrays.asList(true, true, true, true)                 // Affichage des états 0 (initial), 1 et 3
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
    Animation entre l'étape 1 et l'étape 3
     */
    public void doAnimer(ActionEvent actionEvent) {
        try {
            final Motif mobile = composition.getStep(1);
            mobile.setStroke(Color.BLUE);
            pane.getChildren().add(mobile.toGroup());
            composition.animate(
                    mobile.toGroup(),
                    1,
                    3,
                    e -> pane.getChildren().remove(mobile.toGroup())
            ).play();    // Animation entre les étapes 1 et 3
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

    public void doZoom(ActionEvent actionEvent) {
        if (! zoom.isSelected()) {
            composition.setZoom(40.0, 400.0, 400.0);
        } else {
            composition.setZoom(20.0, 200.0, 300.0);
        }
    }
}
