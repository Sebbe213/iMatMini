package imatmini;

import imatmini.iMatMiniController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Rectangle;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Flow;

public class Checkout extends AnchorPane implements ShoppingCartListener {
    iMatMiniController mainController;

    IMatDataHandler dataHandler = IMatDataHandler.getInstance();

    @FXML
    private FlowPane cartFlowPane;
    @FXML
    private Label totalCostLabel;
    @FXML
    private AnchorPane receiptPane;
    @FXML
    private Label orderDate;

    public Checkout(iMatMiniController mainController) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Checkout.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("Checkout");
            throw new RuntimeException(exception);
        }
        this.mainController = mainController;

        this.totalCostLabel.setText(String.format("%.2f", dataHandler.getShoppingCart().getTotal() + 40) + "kr");

        dataHandler.getShoppingCart().addShoppingCartListener(this);
    }

    @FXML
    public void handlePressBackButton(ActionEvent event) {goBack();}

    public void goBack() {
        mainController.openShoppingCart();
    }

    public void fillCartFlowPane() {
        cartFlowPane.getChildren().clear();
        if(Model.getInstance().getShoppingCart().getItems().isEmpty()) {
            System.out.println("Kundvagn Tom");
            return;
        }
        for(ShoppingItem item : Model.getInstance().getShoppingCart().getItems()) {
            Product product = item.getProduct();
            if(item.getAmount() == 0) {continue;}
            cartFlowPane.getChildren().add(new CheckoutItem(item,this));
        }
    }

    private void updatePriceLabel() {
        this.totalCostLabel.setText(String.format("%.2f", dataHandler.getShoppingCart().getTotal() + 40) + "kr");
    }

    @FXML
    private void completePurchase() {
        mainController.handleBuyItemsAction();
        receiptPane.toFront();
    }

    @FXML
    private void keepShopping() {;
        mainController.closeNameView();
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        updatePriceLabel();
    }
}
