package imatmini;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Rectangle;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.List;

public class History extends AnchorPane {
    iMatMiniController mainController;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Button closebutton;
    @FXML
    public FlowPane orderPane;
    private List<Order> orderList;
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
        System.out.print("hehhahaha");
        this.orderList = IMatDataHandler.getInstance().getOrders();

        fillHistory();
    }

    @FXML
    public void closeHistory() {
        mainController.closeNameView();
    }

    public void fillHistory() {
        this.orderList = IMatDataHandler.getInstance().getOrders();
        System.out.println(IMatDataHandler.getInstance().getOrders());
        orderPane.getChildren().clear();
        for (Order order : orderList) {
            HistoryItem item = new HistoryItem(order, mainController);
            orderPane.getChildren().add(item);
        }
    }
}
