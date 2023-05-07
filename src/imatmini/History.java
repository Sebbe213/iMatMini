package imatmini;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class History extends AnchorPane {
    iMatMiniController mainController;
    @FXML
    private Rectangle rectangle;
    public History(iMatMiniController mainController) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("History.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("Här");
            throw new RuntimeException(exception);
        }
        this.mainController = mainController;
    }

    @FXML
    public void closeHistoryView() {
        mainController.closeNameView();
    }
}
