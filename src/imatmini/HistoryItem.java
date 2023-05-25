package imatmini;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Rectangle;
import se.chalmers.cse.dat216.project.*;

import javax.swing.*;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class HistoryItem extends AnchorPane {
    @FXML
    private Label orderDateLabel;
    @FXML
    private Label adressLabel;
    @FXML
    private Button showOrderButton;
    @FXML
    private Rectangle rect;

    private Model model = Model.getInstance();
    protected Order order;
    private final iMatMiniController mainController;

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

        Date date = order.getDate();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Stockholm"));
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        String monthString = String.valueOf(month);
        if(month < 10) {monthString = "0" + String.valueOf(month);}
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String dayString = String.valueOf(day);
        if(day < 10) {dayString = "0" + String.valueOf(day);}
        String dateS = (String.valueOf(String.valueOf(year) + "-" + monthString + "-" + dayString));
        this.orderDateLabel.setText(dateS);

        this.adressLabel.setText(Model.getInstance().getCustomer().getAddress());

    }


    @FXML
    public void selectTheOrder(ActionEvent event) {
        History history = mainController.getHistory();
        history.fillHistoryProduct(order);
        this.rect.getStyleClass().add("green");
        history.updateActiveHistoryItem(this);
    }

    @FXML
    public void addHistoryToCart(ActionEvent event) {

        ShoppingCart shoppingCart = model.getShoppingCart();

        List<ShoppingItem> items = order.getItems();
        for (ShoppingItem item : items) {
            Product product = item.getProduct();
            int quantity = (int) item.getAmount();
            shoppingCart.addItem(new ShoppingItem(product, quantity));

        }

        shoppingCart.fireShoppingCartChanged(null, true);

    }

    public  void unselectOrder() {
        this.rect.getStyleClass().remove("green");
    }


}
