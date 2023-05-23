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
import javafx.scene.paint.Color;
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

   private int offset;

    private ArrayList<Integer> productIndexList = new ArrayList<Integer>();
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



    @FXML
    public void moveRight(ActionEvent event) {
        offset--;
        if(offset == (-1)) {
            offset = 5;
        }

        populateCarousel(model.getProducts(), offset);
    }

    @FXML
    public void moveLeft(ActionEvent event) {

        offset++;
        if(offset == 6){
           offset = 0;
        }

        populateCarousel(model.getProducts(), offset);
    }


    private void updateProductList(List<Product> products) {

        System.out.println("updateProductList " + products.size());


        randomizeProducts(products);
        populateCarousel(products,0);
    }

    private void populateCarousel(List<Product> products,int offset) {
        productBox.getChildren().clear();
        if(offset == 4){
            for (int i=0+offset; i<2+offset; i++){
                int randomIndex = productIndexList.get(i);
                Product product = (products.get(randomIndex));
                productBox.getChildren().add(new ProductPanel(product));
            }
            int randomIndex = productIndexList.get(0);
            Product product = (products.get(randomIndex));
            productBox.getChildren().add(new ProductPanel(product));
        }

        else if (offset == 5) {
            int randomIndex = productIndexList.get(5);
            Product product = (products.get(randomIndex));
            productBox.getChildren().add(new ProductPanel(product));
            for (int i=0; i<2; i++){
                randomIndex = productIndexList.get(i);
                product = (products.get(randomIndex));
                productBox.getChildren().add(new ProductPanel(product));
            }
        }
        else {
            for (int i = 0 + offset; i < 3 + offset; i++) {
                int randomIndex = productIndexList.get(i);
                Product product = (products.get(randomIndex));
                productBox.getChildren().add(new ProductPanel(product));
            }
        }
    }

    private void randomizeProducts(List<Product> products) {
        for (int i = 0; i<6; i++) {
            int randomIndex= rand.nextInt(products.size());
            productIndexList.add(randomIndex);
        }
    }

    public void initialize() {

        updateProductList(model.getProducts());

    }
}




