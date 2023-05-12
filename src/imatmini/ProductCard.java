package imatmini;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class ProductCard extends AnchorPane {
    @FXML ImageView foodImage;

    @FXML Label labelName;

    @FXML Label labelPrice;

    @FXML Button buyButton;



    private Product product;
    private Model model = Model.getInstance();

    public ProductCard(Product product ){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductCard.fxml"));
        fxmlLoader.setController(this);
        try {
            Parent root = fxmlLoader.load();
            this.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ProductCard controller = fxmlLoader.getController();

        this.product = product;
        this.foodImage.setImage(model.getImage(product));
        this.labelName.setText(product.getName());                    //kan också skapa error
        this.labelPrice.setText(String.valueOf(product.getPrice()));


    }




}
