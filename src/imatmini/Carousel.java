package imatmini;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import se.chalmers.cse.dat216.project.Product;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Carousel extends AnchorPane {


    @FXML
    Button leftButton;

    @FXML
    Button rightButton;

    @FXML
    HBox productBox;

    @FXML
    AnchorPane manPane;

    @FXML
    FlowPane flowPane;

    //public ProductCard cards = new ProductCard(new Product());


    private double currentPos;
    iMatMiniController mainController;
    Random rand = new Random();

    private Model model = Model.getInstance();


    public Carousel(iMatMiniController mainController) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("carousel.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("Här");
            throw new RuntimeException(exception);
        }
        this.mainController = mainController;
    }


    public void moveRight(ActionEvent event) {
            if(currentPos <0) {
                currentPos += 720;
                if (currentPos > (productBox.getWidth() - flowPane.getWidth())) {
                    currentPos =  flowPane.getWidth()/5;
                }
                flowPane.setLayoutX(currentPos);
            }

    }

    public void moveLeft(ActionEvent event) {

        currentPos -= 720;
        flowPane.setLayoutX(currentPos);



    }

    private void updateProductList(List<Product> products) {

        System.out.println("updateProductList " + products.size());
        productBox.getChildren().clear();


        for (int i = 0; i<5; i++) {
            int randomIndex= rand.nextInt(products.size());
            Product product = (products.get(randomIndex));


            productBox.getChildren().add(new ProductPanel(product));


        }


    }

    public void initialize() {

        updateProductList(model.getProducts());

    }
}





