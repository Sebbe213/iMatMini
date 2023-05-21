package imatmini;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.List;

public class OrderItem extends AnchorPane {
    @FXML
    private ImageView productImage;
    @FXML
    private Label productNameLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label productWeightLabel;
    @FXML
    private Label productPriceLabel;

    private ShoppingItem item;
    private Product product;
    private Model model = Model.getInstance();

    public OrderItem(ShoppingItem item) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.item = item;
        this.product = item.getProduct();
        System.out.println(product);

        this.productImage.setImage(model.getImage(item.getProduct()));
        this.productNameLabel.setText(item.getProduct().getName());
        this.productPriceLabel.setText(String.format("%d", (int)item.getTotal()));
        this.productWeightLabel.setText(item.getProduct().getUnitSuffix());
        this.amountLabel.setText(String.format("%d", (int)item.getAmount()) + "st");
    }
}
