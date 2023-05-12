package imatmini;

import imatmini.iMatMiniController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Rectangle;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;

import java.io.IOException;
import java.util.List;

public class Favourites extends AnchorPane {
    iMatMiniController mainController;

    public Favourites(iMatMiniController mainController) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Favourites.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("Favourites");
            throw new RuntimeException(exception);
        }
        this.mainController = mainController;
    }
}
