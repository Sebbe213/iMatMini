package imatmini;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Rectangle;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class History extends AnchorPane {
    iMatMiniController mainController;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Button closebutton;
    @FXML
    private FlowPane orderPane;
    @FXML
    private FlowPane orderItemsPane;
    @FXML
    private Label selectedOrderDateLabel;
    @FXML
    private Label orderPriceLabel;

    private HistoryItem activeItem;

    private final Map<Integer, HistoryItem> historyItemMap = new HashMap<Integer, HistoryItem>();


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
        //System.out.print("hehhahaha");
        this.orderList = IMatDataHandler.getInstance().getOrders();
        this.activeItem = null;

        fillHistory();
    }

    @FXML
    public void closeHistory() {
        mainController.closeNameView();
    }

    public void fillHistory() {
        this.orderList = IMatDataHandler.getInstance().getOrders();
     //   System.out.println(IMatDataHandler.getInstance().getOrders().size());
        orderPane.getChildren().clear();

        for (int i = orderList.size()-1; i>=0; i--) {
            HistoryItem item = new HistoryItem(orderList.get(i), mainController);
            orderPane.getChildren().add(item);
            historyItemMap.put(i, item);
        }
    }


    public void selectTheFirstOrder() {
        historyItemMap.get(Model.getInstance().getNumberOfOrders()-1).selectOrder();
    }

    public void fillHistoryProduct(Order order) {
        //System.out.println(order);

        double totalCost = 0;
        orderItemsPane.getChildren().clear();
        for (ShoppingItem item : order.getItems()) {

            OrderItem orderitem = new OrderItem(item);
            orderItemsPane.getChildren().add(orderitem);

            totalCost += item.getTotal();

        }
        selectedOrderDateLabel.setText("Order " + order.getDate().toString());
        //System.out.println(selectedOrderDateLabel);
        orderPriceLabel.setText( String.format("%.2f", totalCost + 40) + "kr");
    }

    public void updateActiveHistoryItem(HistoryItem item) {
        if(this.activeItem != null) {this.activeItem.unselectOrder();}
        this.activeItem = item;
    }

}
