package imatmini;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.List;

public class HistoryItem extends AnchorPane {
    @FXML
    private Label orderDateLabel;
    @FXML
    private Label adressLabel;
    @FXML
    private Button showOrderButton;

    private Order order;
    private iMatMiniController mainController;

    public HistoryItem(Order order, iMatMiniController mainController) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HistoryItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.order = order;
        this.mainController = mainController;
        this.orderDateLabel.setText(order.getDate().toString());
        this.adressLabel.setText("EN ADRESS");

        List<ShoppingItem> orderList = order.getItems();
    }
}
