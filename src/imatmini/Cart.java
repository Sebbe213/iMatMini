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

public class Cart extends AnchorPane implements ShoppingCartListener {
    private final iMatMiniController mainController;

    private final IMatDataHandler dataHandler = IMatDataHandler.getInstance();

    @FXML
    private FlowPane cartFlowPane;
    @FXML
    private Label priceLabel;
    @FXML
    private Button backButton;
    @FXML
    private Button toCheckoutButton;


    public Cart(iMatMiniController mainController) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Cart.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("Cart");
            throw new RuntimeException(exception);
        }
        this.mainController = mainController;
        this.priceLabel.setText(String.format("%.2f", dataHandler.getShoppingCart().getTotal()));

        dataHandler.getShoppingCart().addShoppingCartListener(this);
    }
    @FXML
    public void handleShowCheckoutAction(ActionEvent event) {openCheckout();}

    @FXML
    public void handlePressBackButton(ActionEvent event) {goBack();}

    public void goBack() {
        mainController.closeCartPane();
    }

    public void openCheckout() {
        mainController.updateCheckoutPane();
        mainController.checkoutPane.toFront();
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
            cartFlowPane.getChildren().add(new CartItem(item,this));
        }
    }

    public void updatePriceLabel() {
        this.priceLabel.setText(String.format("%.2f", dataHandler.getShoppingCart().getTotal()));
    }
    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        updatePriceLabel();
    }
}
