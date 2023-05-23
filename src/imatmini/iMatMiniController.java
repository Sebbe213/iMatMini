/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imatmini;

import java.net.URL;
import java.util.*;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import se.chalmers.cse.dat216.project.*;

import  imatmini.ProductPanel;


/**
 *
 * @author oloft
 */
public class iMatMiniController implements Initializable, ShoppingCartListener {
    
    // Shopping Pane
    @FXML
    private AnchorPane shopPane;
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
    private FlowPane CategoryFlowpane;

    @FXML
    private Label categoryId;

    @FXML
    private Label kundvagnLabel;

    @FXML private Circle circle;

   public int quantity =0;





    private final Favourites favourites = new Favourites(this);
    private final History history = new History(this);
    private final Profile  profile = new Profile(this);
    private final Cart  cart = new Cart(this);
    private final Checkout checkout = new Checkout(this);
    private final Carousel carousel = new Carousel(this);


    // Other variables
    private final Model model = Model.getInstance();




    public void KundVagnLable() {
        circle.toBack();
        kundvagnLabel.toBack();
        int totalQuantity = 0;
        List<ShoppingItem> items = model.getShoppingCart().getItems();
        for (ShoppingItem item : items) {
            if(item.getAmount() >= 1){
                circle.toFront();
                kundvagnLabel.toFront();
            }
            totalQuantity += item.getAmount();
        }

        kundvagnLabel.setText(String.valueOf(totalQuantity));
    }




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
            if(item.getAmount() == 0) {IMatDataHandler.getInstance().getShoppingCart().removeItem(item);}
        }
        model.placeOrder();
        history.fillHistory();
        cart.fillCartFlowPane();
        checkout.fillCartFlowPane();
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
        // TODO
        model.getShoppingCart().addShoppingCartListener(this);

        updateProductList(model.getProducts());

        updateBottomPanel();
        
        setupAccountPane();

        //KundVagnLable();



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


    }


    
    // Navigation
    public void openAccountView() {
        updateAccountPanel();
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

    public void openFavourites() {
       favourites.fillGridPane();
       favoritesPane.toFront();
    }


    public void openShoppingCart() {
        cart.fillCartFlowPane();
        cartPane.toFront();
    }
    public void closeCartPane() {cartPane.toBack();}

    public void updateCheckoutPane() {
        checkout.fillCartFlowPane();
    }

    public void closeNameView() {
        shopPane.toFront();
    }
    // Shope pane methods
    @Override
     public void shoppingCartChanged(CartEvent evt) {
        updateBottomPanel();
    }
   
    
    private void updateProductList(List<Product> products) {

        System.out.println("updateProductList " + products.size());
        productsFlowPane.getChildren().clear();


        for (Product product : products) {

            productsFlowPane.getChildren().add(new ProductPanel(product));


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
        CategoryFlowpane.getChildren().clear();

        for (Product product : products) {
            if (selectedCategory.contains(product.getCategory())) {
                CategoryFlowpane.getChildren().add(new ProductPanel(product));
            }

        }

        CategoryAnchorpane.toFront();
    }
   /* @FXML
    public void updatLable(){
        kundvagnLabel.toBack();
        for (Node node : productsFlowPane.getChildren()) {
            if (node instanceof ProductPanel) {
                ProductPanel productPanel = (ProductPanel) node;
                Button button = productPanel.buyButton;
                    if()
                    kundvagnLabel.toFront();
                    quantity++;
                    kundvagnLabel.setText(String.valueOf(quantity));
                    break;
                }
            }
        }*/


    
    private void updateBottomPanel() {
        
        ShoppingCart shoppingCart = model.getShoppingCart();
        
        itemsLabel.setText("Antal varor: " + shoppingCart.getItems().size());
        costLabel.setText("Kostnad: " + String.format("%.2f",shoppingCart.getTotal()));

        KundVagnLable();
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
}
