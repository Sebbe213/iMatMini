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
import javafx.scene.layout.HBox;
import se.chalmers.cse.dat216.project.Product;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Carousel extends AnchorPane {


    @FXML Button leftButton;

    @FXML Button rightButton;

    @FXML HBox productBox;

    @FXML AnchorPane carouselPane;

    //public ProductCard cards = new ProductCard(new Product());


    private double currentPos;

    private Model model = Model.getInstance();

    public void moveRight(ActionEvent event){
        currentPos -= 10;
        carouselPane.setLayoutX(currentPos);

    }

    public void moveLeft(ActionEvent event){
        currentPos += 10;
        carouselPane.setLayoutX(currentPos);
    }

    public void initialize()  {

        for (Product product : model.getProducts()) {
            ProductCard card = new ProductCard(product);
            productBox.getChildren().add(card);
        }

        carouselPane.getChildren().add(productBox);
    }


    }







