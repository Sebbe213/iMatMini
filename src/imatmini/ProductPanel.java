/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imatmini;


import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.*;

/**
 *
 * @author oloft
 */
public class ProductPanel extends AnchorPane {

    @FXML ImageView imageView;
    @FXML Label nameLabel;
    @FXML Label prizeLabel;
    @FXML Label ecoLabel;

    @FXML Label howLabel;

    @FXML
    Button addButton;

    @FXML
    Button buyButton;

    
    private Model model = Model.getInstance();


    private Product product;
    
    private final static double kImageWidth = 100.0;
    private final static double kImageRatio = 0.75;

    public ProductPanel(Product product) {
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductPanel.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.product = product;
        nameLabel.setText(product.getName());
        prizeLabel.setText(String.format("%.2f", product.getPrice()) + " " + product.getUnit());
        imageView.setImage(model.getImage(product, kImageWidth, kImageWidth*kImageRatio));
        if (!product.isEcological()) {
            ecoLabel.setText("");
        }
    }


    @FXML
    public void addProduct() {
        int quantity = Integer.parseInt(howLabel.getText());
        quantity++;
        howLabel.setText(String.valueOf(quantity));
    }

    @FXML
    public void removeProduct(){

        int quantity = Integer.parseInt(howLabel.getText());
        if(quantity>0) {
            quantity--;
            howLabel.setText(String.valueOf(quantity));
        }

    }

    @FXML
    public void sendBuyButtonBack(){
        buyButton.toBack();
        buyButton.setStyle("-fx-background-color: #FFFFFF;");
    }



   @FXML
    public void handleRemoveAction(ActionEvent event) {
        List<ShoppingItem> items = model.getShoppingCart().getItems();      //en lista med alla våra varor

       for (int i = 0; i < items.size(); i++) {
           ShoppingItem item = items.get(i);
            if (item.getProduct().equals(product)) {
                if (item.getAmount() > 1) {
                    item.setAmount(item.getAmount() - 1);


                } else {
                    model.getShoppingCart().removeProduct(product);
                }
                model.getShoppingCart().fireShoppingCartChanged(item ,true);
                break;
            }


        }

        removeProduct();
    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        System.out.println("Add " + product.getName());
        model.addToShoppingCart(product);
        addProduct();


        }
    }

