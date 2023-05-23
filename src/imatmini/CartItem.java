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
import se.chalmers.cse.dat216.project.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class CartItem extends AnchorPane implements ShoppingCartListener {
    private final ShoppingItem item;
    private final Cart cart;
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
    @FXML
    private AnchorPane mainAnchor;

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
        this.minusImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imatmini/pics/Ellipse 3removeButton.png")));
        this.plusImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imatmini/pics/plusButtonImage.png")));

        this.productNameLabel.setText(item.getProduct().getName());
        this.productCostLabel.setText(String.format("%.2f", item.getTotal()) + "kr");
        this.amountLabel.setText(String.format("%d",(int)item.getAmount()) + "st");

        dataHandler.getShoppingCart().addShoppingCartListener(this);
    }


    @FXML
    private void removeProductFromCart() {
        dataHandler.getShoppingCart().removeItem(this.item);
        cart.fillCartFlowPane();
    }

    @FXML
    private void addProduct() {
        dataHandler.getShoppingCart().addProduct(this.item.getProduct());  //lägger till en shopping item i kundvagnen, om redan finns så +1.
    }

    @FXML
    private void removeProduct() {
        if(this.item.getAmount() > 0) { this.item.setAmount(this.item.getAmount() - 1); }
        else { this.item.setAmount(0); }
        dataHandler.getShoppingCart().fireShoppingCartChanged(this.item, true);
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        if(this.item.getAmount()<=0) {this.amountLabel.setText("0");}
        else {this.amountLabel.setText(String.format("%d", (int)this.item.getAmount()) + "st");}

        this.productCostLabel.setText(String.format("%.2f", item.getTotal()) + "kr");
    }
}
