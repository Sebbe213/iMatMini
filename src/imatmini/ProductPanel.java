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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.*;

/**
 *
 * @author oloft
 */
public class ProductPanel extends AnchorPane implements ShoppingCartListener {

    @FXML ImageView imageView;
    @FXML Label nameLabel;
    @FXML Label prizeLabel;
    @FXML Label ecoLabel;

    @FXML Label howLabel;

    @FXML
    Button addButton;
    @FXML Button addButton1;

    @FXML
    Button buyButton;
    @FXML Button favorite;
    @FXML ImageView favImage;

    
    private Model model = Model.getInstance();


    private Product product;

    private iMatMiniController mainController;

    
    private final static double kImageWidth = 200.0;
    private final static double kImageRatio = 0.75;

    public ProductPanel(Product product, iMatMiniController mainController) {
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductPanel.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        model.getShoppingCart().addShoppingCartListener(this);

        this.mainController = mainController;
        addButton.setText("+");
        this.product = product;
        nameLabel.setText(product.getName());
        prizeLabel.setText(String.format("%.2f", product.getPrice()) + " " + product.getUnit());
        imageView.setImage(model.getImage(product, kImageWidth, kImageWidth*kImageRatio));
        if (!product.isEcological()) {
            ecoLabel.setText("");
        }
        if (model.isFavorite(product)){
            favImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imatmini/pics/favorite.png")));
        }

    }


    @FXML
    public void addProduct() {
        howLabel.setText(String.valueOf((int)mainController.getShoppingItem(product).getAmount()));
    }

    @FXML
    public void removeProduct(){

        int quantity = (int)mainController.getShoppingItem(product).getAmount();
        if(quantity<=0){
            buyButton.toFront();
        }
        howLabel.setText(String.valueOf(quantity));
    }

    @FXML
    public void addFavorite(){
        if(model.isFavorite(product)){
            model.removeFavorite(product);
            System.out.println("Remove fav: " + product.getProductId());
            favImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imatmini/pics/unfavorite.png")));
        }
        else if(!model.isFavorite(product)) {
            model.addFavorite(product);
            favImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imatmini/pics/favorite.png")));
        }
        favImage.setOpacity(1);
    }

    @FXML
    public void onMouseEnteredFavorite(){
        if(!model.isFavorite((product))){
            favImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imatmini/pics/favorite.png")));
            favImage.setOpacity(0.5);
        } else if(model.isFavorite(product)) {
            favImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imatmini/pics/unfavorite.png")));
            favImage.setOpacity(0.5);
        }
    }

    @FXML
    public void onMouseExitedFavorite(){
        if(!model.isFavorite(product)) {
            favImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imatmini/pics/unfavorite.png")));
            favImage.setOpacity(1);
        } else if (model.isFavorite(product)) {
            favImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imatmini/pics/favorite.png")));
            favImage.setOpacity(1);
        }
    }

    @FXML
    public void sendBuyButtonBack(){
        buyButton.toBack();
        int quantity = Integer.parseInt(howLabel.getText());
        quantity++;
        howLabel.setText(String.valueOf(quantity));

        //TODO LÄGGER TILL I SHOPPINGCART
        model.addToShoppingCart(product);

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
                    item.setAmount(0);
                    model.getShoppingCart().fireShoppingCartChanged(item,true);
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
        model.getShoppingCart().addProduct(product);
        addProduct();


        }
    @FXML
    private void openDetailView() {
        mainController.openDetailView(product);
    }

    public void openWheProductInShoppingCart() {
        howLabel.setText(String.format("%d",(int) mainController.getShoppingItem(product).getAmount()));
        buyButton.toBack();
    }


    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        ShoppingItem item = mainController.getShoppingItem(this.product);
        if(item != null) {
            if(item.getAmount() <= 0) {
                System.out.println("uppdaterar " + item.getProduct() + " mängd till 0");
                howLabel.setText("0");
                buyButton.toFront();
            }
            else if (Model.getInstance().isFavorite(product)) {
                buyButton.toBack();
                howLabel.setText(String.format("%d",(int)item.getAmount()));
            }
            if(item.getAmount() > 0) {
                howLabel.setText(String.format("%d",(int)item.getAmount()));
                buyButton.toBack();
            }
        }
    }
}

