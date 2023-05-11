package imatmini;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class HomePage extends AnchorPane {
    iMatMiniController mainController;
    @FXML
    private TextField searchField;

    public HomePage(iMatMiniController mainController) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("Homepage");
            throw new RuntimeException(exception);
        }
        this.mainController = mainController;
    }

    @FXML
    private void handleClearCartAction(ActionEvent event) {
        mainController.clearShoppingCart();
    }

    @FXML
    private void search(ActionEvent event) {
        mainController.handleSearchAction(event);
    }
}
