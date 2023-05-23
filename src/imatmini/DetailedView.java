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
import javafx.scene.shape.Rectangle;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.List;

public class DetailedView extends AnchorPane {
    private Product product;

    @FXML
    private ImageView productImage;

    @FXML
    private Label priceLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private ImageView closeImage;

    private final iMatMiniController mainController;

    public DetailedView(Product product, iMatMiniController mainController) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetailedView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("DetailedView");
            throw new RuntimeException(exception);
        }
        this.product = product;
        this.mainController = mainController;

        this.priceLabel.setText(String.format("%.2f", product.getPrice()));
        this.nameLabel.setText(product.getName());
        this.productImage.setImage(IMatDataHandler.getInstance().getFXImage(product));
    }

    @FXML
    private void closeDetailView() {
        mainController.getDetailPane().toBack();
    }
}
