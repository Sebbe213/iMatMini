package imatmini;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Product product;

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

    public CartItem(Product product) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CartItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.product = product;

        this.productImage.setImage(Model.getInstance().getImage(product));
        this.trashCanImage.setImage("imatmini/pics/");
    }
}
