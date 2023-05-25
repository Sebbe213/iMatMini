/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imatmini;

import java.net.URL;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import se.chalmers.cse.dat216.project.*;


/**
 *
 * @author oloft
 */
public class iMatMiniController implements Initializable, ShoppingCartListener {
    
    // Shopping Pane
    @FXML
    private AnchorPane shopPane;
    @FXML
    private AnchorPane detailPane;
    @FXML
    private TextField searchField;
    @FXML
    private Label itemsLabel;
    @FXML
    private Label costLabel;
    @FXML
    private FlowPane productsFlowPane;
    
    // Account Pane
    @FXML
    private AnchorPane accountPane;
    @FXML 
    ComboBox cardTypeCombo;

    @FXML
    private TextField numberTextField;

    @FXML
    private TextField nameTextField;

    @FXML 
    private ComboBox monthCombo;

    @FXML
    private ComboBox yearCombo;

    @FXML
    private TextField cvcField;

    @FXML
    private Label purchasesLabel;

    @FXML
    private AnchorPane dynamicPane;

    @FXML
    private AnchorPane historyPane;

    @FXML
    private AnchorPane profilePane;

    @FXML
    private AnchorPane carouselContainer;

    @FXML AnchorPane ProductsPane;

    @FXML AnchorPane  carouselPane;

    @FXML FlowPane flowCarousel;

    @FXML
    private AnchorPane cartPane;

    @FXML
    protected AnchorPane checkoutPane;
    @FXML
    private AnchorPane favoritesPane;

    @FXML
    private AnchorPane main;

    @FXML
    private Button fiskButton;

    @FXML
    private Button köttButton;

    @FXML
    private Button mejeriButton;

    @FXML
    private Button grönsaksButton;

    @FXML
    private Button dryckButton;

    @FXML
    private Button fruktButton;

    @FXML
    private Button skafferiButton;

    @FXML
    private AnchorPane CategoryAnchorpane;

    @FXML
    private GridPane categoryGridPane;
    //private FlowPane CategoryFlowpane;

    @FXML
    private Label categoryId;
    @FXML
    public Circle dot1;
    @FXML
    public Circle dot2;
    @FXML
    public Circle dot3;
    @FXML
    public Circle dot4;
    @FXML
    public Circle dot5;
    @FXML
    public Circle dot6;

    private final Favourites favourites = new Favourites(this);
    private final History history = new History(this);
    private final Profile  profile = new Profile(this);
    private final Cart  cart = new Cart(this);
    private final Checkout checkout = new Checkout(this);
    private final Carousel carousel = new Carousel(this);
    Map<String, DetailedView> detailedViewMap = new HashMap<String, DetailedView>();

    @FXML
    private ImageView redDotImageView;
    @FXML
    private Label numberInCartLabel;


    // Other variables
    private final Model model = Model.getInstance();

    // Shop pane actions
    @FXML
    private void handleShowAccountAction(ActionEvent event) {
        openAccountView();
    }

    @FXML
    private void handleShowHistoryAction(ActionEvent event) {
        openHistoryView();
    }

    @FXML
    private void handleShowFavouritesAction(ActionEvent event) {openFavourites();}

    @FXML
    private void handleShowShoppingCartAction(ActionEvent event) {openShoppingCart();}
    
    @FXML
    private void handleSearchAction(ActionEvent event) {
        List<Product> matches = model.findProducts(searchField.getText());
        updateProductList(matches);
        System.out.println("# matching products: " + matches.size());
        ProductsPane.toFront();
    }
    @FXML
    private void backButton(ActionEvent event){
        ProductsPane.toBack();
    }
    
    @FXML
    private void handleClearCartAction(ActionEvent event) {
        model.clearShoppingCart();
    }

    public void handleBuyItemsAction() {
        for(ShoppingItem item : IMatDataHandler.getInstance().getShoppingCart().getItems()) {
            if(item.getAmount() == 0) {
                model.getShoppingCart().fireShoppingCartChanged(item, true );
                IMatDataHandler.getInstance().getShoppingCart().removeItem(item);}
        }
        for(ShoppingItem item: IMatDataHandler.getInstance().getShoppingCart().getItems()) {
            double amount = item.getAmount();
            item.setAmount(0);
            model.getShoppingCart().fireShoppingCartChanged(item, true);
            item.setAmount(amount);
        }
        IMatDataHandler.getInstance().placeOrder();
        history.fillHistory();
        cart.fillCartFlowPane();
        checkout.fillCartFlowPane();
        updateNumberInCart();
        costLabel.setText("Köpet klart!");
    }

    @FXML
    private void handleNameAction(ActionEvent event) {
        openNameView();
    }

    // Account pane actions
     @FXML
    private void handleDoneAction(ActionEvent event) {
        closeAccountView();
    }
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //IMatDataHandler.getInstance().isFirstRun();
        //IMatDataHandler.getInstance().reset();
        // TODO
        model.getShoppingCart().addShoppingCartListener(this);

        updateProductList(model.getProducts());

        for(Product product : Model.getInstance().getProducts()) {
            detailedViewMap.put(product.getName(), new DetailedView(product,this));
        }

        updateBottomPanel();
        
        setupAccountPane();

        // Load the NamePanel and add it to dynamicPane
        // This shows how one can develop a view in a separate
        // FXML-file and then load it into on of the panes in the main interface
        // There is an fxml file NamePanel.fxml and a corresponding class NamePanel.java
        // Simply create a new NamePanel object and add it as a child of dynamicPane
        // The NamePanel holds a reference to the main controller (this class)

        historyPane.getChildren().add(history);

        profilePane.getChildren().add(profile);

        favoritesPane.getChildren().add(favourites);

        cartPane.getChildren().add(cart);

        checkoutPane.getChildren().add(checkout);

        flowCarousel.getChildren().add(carousel);

        historyPane.setTranslateY(94);
        profilePane.setTranslateY(94);
        cartPane.setTranslateY(94);
        favoritesPane.setTranslateY(94);
        checkoutPane.setTranslateY(94);
        detailPane.setTranslateY(94);
        CategoryAnchorpane.setTranslateX(238);

        updateNumberInCart();
    }    
    
    // Navigation
    public void openAccountView() {
        updateAccountPanel();
        profile.init();
        profilePane.toFront();
    }

    public void closeAccountView() {
        updateCreditCard();
        shopPane.toFront();
    }
    public void closeCategory() {
        CategoryAnchorpane.toBack();
    }

    public void openMyProfile() { profilePane.toFront(); }

    public void openNameView() {
        dynamicPane.toFront();
    }

    public void openHistoryView() {
        history.fillHistory();
        historyPane.toFront();
    }

    public History getHistory() {
        return history;
    }

    public AnchorPane getDetailPane() {
        return detailPane;
    }

    public void openDetailView(Product product) {
        detailPane.getChildren().clear();
        detailPane.getChildren().add(detailedViewMap.get(product.getName()));
        if(getShoppingItem(product) != null) {
            detailedViewMap.get(product.getName()).openWheProductInShoppingCart();
        }
        detailPane.toFront();
    }

    public void openFavourites() {
        updateFavorites();
       favoritesPane.toFront();
    }

    public void updateFavorites() {
        favourites.fillGridPane();
    }

    public void openShoppingCart() {
        cart.fillCartFlowPane();
        cartPane.toFront();
    }
    public void closeCartPane() {cartPane.toBack();}

    public void updateCheckoutPane() {
        checkout.init();
        checkout.fillCartFlowPane();
    }

    public void closeNameView() {
        shopPane.toFront();
    }
    // Shope pane methods
    @Override
     public void shoppingCartChanged(CartEvent evt) {
        updateBottomPanel();
        updateNumberInCart();
    }

    private void updateProductList(List<Product> products) {

        System.out.println("updateProductList " + products.size());
        productsFlowPane.getChildren().clear();


        for (Product product : products) {

            productsFlowPane.getChildren().add(new ProductPanel(product,this));


        }



    }
    /*private void updateCarousel(){
        Carousel carousel = new Carousel();
        carouselContainer.getChildren().add(carousel);
        carouselContainer.setTopAnchor(carousel, 5.0);
        carouselContainer.setLeftAnchor(carousel, 5.0);
        carouselContainer.setRightAnchor(carousel, 5.0);
        carouselContainer.setBottomAnchor(carousel, 5.0);
       //Kan skapa error


    }*/

    @FXML
    public void openCategory(ActionEvent event) {
        List<Product> products = model.getProducts();
        Map<Button, List<ProductCategory>> categoryMap = new HashMap<>();

        categoryMap.put(fiskButton,  Arrays.asList(ProductCategory.FISH));
        categoryMap.put(köttButton, Arrays.asList(ProductCategory.MEAT));
        categoryMap.put(grönsaksButton, Arrays.asList(ProductCategory.ROOT_VEGETABLE, ProductCategory.VEGETABLE_FRUIT));
        categoryMap.put(fruktButton, Arrays.asList(ProductCategory.BERRY, ProductCategory.CITRUS_FRUIT, ProductCategory.EXOTIC_FRUIT, ProductCategory.MELONS));
        categoryMap.put(mejeriButton, Arrays.asList(ProductCategory.DAIRIES));
        categoryMap.put(skafferiButton, Arrays.asList(ProductCategory.FLOUR_SUGAR_SALT, ProductCategory.BREAD, ProductCategory.PASTA, ProductCategory.POTATO_RICE, ProductCategory.NUTS_AND_SEEDS));
        categoryMap.put(dryckButton, Arrays.asList(ProductCategory.COLD_DRINKS));


        Button clickedButton = (Button) event.getSource();
        List<ProductCategory> selectedCategory = categoryMap.get(clickedButton);
        categoryGridPane.getChildren().clear();

/*        for (Product product : products) {
            if (selectedCategory.contains(product.getCategory())) {
                categoryGridPane.getChildren().add(new ProductPanel(product,this));
            }
        }*/
        int col = 0; int row = 0;
        for(Product product : products) {
            if(selectedCategory.contains(product.getCategory())) {
                categoryGridPane.add(new ProductPanel(product, this), col, row);
                col++;
                if (col % 4 == 0) {
                    col = 0;
                    row++;
                }
                model.getShoppingCart().fireShoppingCartChanged();
            }
        }

        CategoryAnchorpane.toFront();
    }

    @FXML
    public void categoryLabel() {
        if(fiskButton.isPressed()) {
            categoryId.setText("Fisk");
        }
        if(köttButton.isPressed()) {
            categoryId.setText("Kött");
        }
        if(grönsaksButton.isPressed()) {
            categoryId.setText("Grönsaker");
        }
        if(fruktButton.isPressed()) {
            categoryId.setText("Frukt");
        }
        if(mejeriButton.isPressed()) {
            categoryId.setText("Mejeri");
        }
        if(skafferiButton.isPressed()) {
            categoryId.setText("Skafferi");
        }
    }
    
    private void updateBottomPanel() {
        
        ShoppingCart shoppingCart = model.getShoppingCart();
        
        itemsLabel.setText("Antal varor: " + shoppingCart.getItems().size());
        costLabel.setText("Kostnad: " + String.format("%.2f",shoppingCart.getTotal()));
        
    }
    
    private void updateAccountPanel() {
        
        CreditCard card = model.getCreditCard();
        
        numberTextField.setText(card.getCardNumber());
        nameTextField.setText(card.getHoldersName());
        
        cardTypeCombo.getSelectionModel().select(card.getCardType());
        monthCombo.getSelectionModel().select(""+card.getValidMonth());
        yearCombo.getSelectionModel().select(""+card.getValidYear());

        cvcField.setText(""+card.getVerificationCode());
        
        purchasesLabel.setText(model.getNumberOfOrders()+ " tidigare inköp hos iMat");
        
    }
    
    private void updateCreditCard() {
        
        CreditCard card = model.getCreditCard();
        
        card.setCardNumber(numberTextField.getText());
        card.setHoldersName(nameTextField.getText());
        
        String selectedValue = (String) cardTypeCombo.getSelectionModel().getSelectedItem();
        card.setCardType(selectedValue);
        
        selectedValue = (String) monthCombo.getSelectionModel().getSelectedItem();
        card.setValidMonth(Integer.parseInt(selectedValue));
        
        selectedValue = (String) yearCombo.getSelectionModel().getSelectedItem();
        card.setValidYear(Integer.parseInt(selectedValue));
        
        card.setVerificationCode(Integer.parseInt(cvcField.getText()));

    }
    
    private void setupAccountPane() {
                
        cardTypeCombo.getItems().addAll(model.getCardTypes());
        
        monthCombo.getItems().addAll(model.getMonths());
        
        yearCombo.getItems().addAll(model.getYears());
        
    }

    private void updateNumberInCart() {
        if(Model.getInstance().getShoppingCart().getItems().isEmpty()) {
            numberInCartLabel.setText("0");
            numberInCartLabel.setVisible(false);
        }
        else {
            redDotImageView.setVisible(true);
            numberInCartLabel.setVisible(true);
            int amount = 0;
            for(ShoppingItem item : Model.getInstance().getShoppingCart().getItems()) {
                amount += item.getAmount();
            }
            if(amount == 0) {
                redDotImageView.setVisible(false);
                numberInCartLabel.setVisible(false);
            }
            numberInCartLabel.setText(String.format("%d",amount));
        }
    }

    public ShoppingItem getShoppingItem(Product product) {
        for(ShoppingItem cartItem : model.getShoppingCart().getItems()) {
            if(cartItem.getProduct().equals(product)){
                return cartItem;
            }
        }
        return null;
    }
}
