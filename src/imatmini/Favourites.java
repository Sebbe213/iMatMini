package imatmini;

import imatmini.iMatMiniController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Favourites extends AnchorPane {
    iMatMiniController mainController;

    @FXML
    private GridPane favoritesGridPane;

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

    public void fillGridPane() {
        List<Product> favoritesList = IMatDataHandler.getInstance().favorites();
        favoritesGridPane.getChildren().clear();
        if(favoritesList.isEmpty()) {return;}
        int i = 0;
        for(int col = 0; col<3; col++) {
            for(int row = 0; row<favoritesList.size()/3; row++) {
                favoritesGridPane.add(new ProductPanel(favoritesList.get(i)),col,row);
                        i++;
            }
        }
    }
}
