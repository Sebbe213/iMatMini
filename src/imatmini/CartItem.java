package imatmini;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class CartItem extends AnchorPane {
    private final ShoppingItem item;
    private Cart cart;
    @FXML
    private ImageView productImage;
    @FXML
    private ImageView trashCanImage;
    @FXML
    private ImageView minusImage;
    @FXML
    private ImageView plusImage;
    @FXML
    private Label productNameLabel;
    @FXML
    private Label productCostLabel;
    @FXML
    private Label amountLabel;

    private final IMatDataHandler dataHandler = IMatDataHandler.getInstance();

    public CartItem(ShoppingItem shoppingItem, Cart cart) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CartItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.item = shoppingItem;
        this.cart = cart;

        productImage.setImage(dataHandler.getFXImage(item.getProduct()));
        this.trashCanImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imatmini/pics/deleteIcon.png")));
        this.minusImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imatmini/pics/removeFromCart.png")));
        this.plusImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imatmini/pics/addInCart.png")));

        this.productNameLabel.setText(item.getProduct().getName());
        this.productCostLabel.setText(String.format("%.2f", item.getTotal()));
        this.amountLabel.setText(String.format("%d",(int)item.getAmount()));
    }

    @FXML
    private void removeProductFromCart() {
        dataHandler.getShoppingCart().removeItem(this.item);
        cart.fillCartFlowPane();
    }

    @FXML
    private void addProduct() {
        dataHandler.getShoppingCart().addProduct(this.item.getProduct());  //lägger till en shopping item i kundvagnen, om redan finns så +1.
        updateAmountLabel();
    }

    @FXML
    private void removeProduct() {
        dataHandler.getShoppingCart().removeProduct(item.getProduct());
    }

    private void updateAmountLabel() {
        this.amountLabel.setText(String.format("%d", (int)this.item.getAmount()));
    }
}
