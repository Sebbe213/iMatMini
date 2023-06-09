package imatmini;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Rectangle;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.util.List;

public class DetailedView extends AnchorPane implements ShoppingCartListener {
    private Product product;
    private final iMatMiniController mainController;
    private Model model = Model.getInstance();

    @FXML ImageView imageView;
    @FXML Label ecoLabel;
    @FXML Label howLabel;
    @FXML
    Button addButton;
    @FXML Button addButton1;
    @FXML
    Button buyButton;
    @FXML Button favorite;
    @FXML ImageView favImage;

    @FXML
    private ImageView productImage;

    @FXML
    private Label priceLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private ImageView closeImage;

    @FXML
    private Label suffixLabel;

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

        this.suffixLabel.setText(product.getUnit());
        this.priceLabel.setText(String.format("%.2f", product.getPrice()));
        this.nameLabel.setText(product.getName());
        this.productImage.setImage(IMatDataHandler.getInstance().getFXImage(product));
        this.addButton.setText("+");
        if (!product.isEcological()) {
            ecoLabel.setText("");
        }
        if (model.isFavorite(product)){
            favImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imatmini/pics/favorite.png")));
        }
    }

    @FXML
    private void closeDetailView() {
        mainController.getDetailPane().toBack();
    }

    @FXML
    public void mouseTrap(Event event){
        event.consume();
    }

    @FXML
    private void closeImageHover() {
        closeImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imatmini/pics/close_hover.png")));
    }
    @FXML
    private void closeImageExited() {
        closeImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imatmini/pics/close.png")));
    }
    @FXML
    private void closeImagePressed() {
        closeImage.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imatmini/pics/close.png")));
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
            if(quantity==0){
                buyButton.toFront();
            }
            howLabel.setText(String.valueOf(quantity));
        }
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

        for (ShoppingItem item : items) {
            if (item.getProduct().equals(product)) {
                if (item.getAmount() > 1) {
                    item.setAmount(item.getAmount() - 1);


                } else {
                    item.setAmount(0);
                    model.getShoppingCart().fireShoppingCartChanged(item,true);
                    model.getShoppingCart().removeProduct(product);
                }
                model.getShoppingCart().fireShoppingCartChanged(item, true);
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
            else if (item.getProduct().equals(product)) {
                howLabel.setText(String.format("%d",(int)item.getAmount()));
                buyButton.toBack();
            }
        }
    }
}
