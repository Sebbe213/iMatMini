/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imatmini;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.*;

import javax.swing.*;


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
    private AnchorPane carouselContainer;

    @FXML AnchorPane ProductsPane;

    @FXML
    private AnchorPane main;


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
    private void handleShowHomePageAction(ActionEvent event) {openHomePageView();}

    public void handleSearchAction(ActionEvent event) {
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
    private void handleBuyItemsAction(ActionEvent event) {
        model.placeOrder();
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
        
        setupAccountPane();

        // Load the NamePanel and add it to dynamicPane
        // This shows how one can develop a view in a separate
        // FXML-file and then load it into on of the panes in the main interface
        // There is an fxml file NamePanel.fxml and a corresponding class NamePanel.java
        // Simply create a new NamePanel object and add it as a child of dynamicPane
        // The NamePanel holds a reference to the main controller (this class)

        Product product = new Product();
        ShoppingItem shoppingItem = new ShoppingItem(product, 2);
        Model.getInstance().getShoppingCart().addItem(shoppingItem);
        Model.getInstance().placeOrder();

        AnchorPane namePane = new NamePanel(this);
        dynamicPane.getChildren().add(namePane);
        AnchorPane history = new History(this);
        historyPane.getChildren().add(history);
        AnchorPane home = new HomePage(this);
        shopPane.getChildren().add(home);




    }    
    
    // Navigation
    public void openAccountView() {
        updateAccountPanel();
        accountPane.toFront();
    }

    public void closeAccountView() {
        updateCreditCard();
        shopPane.toFront();
    }

    public void openNameView() {
        dynamicPane.toFront();
    }

    public void openHistoryView() {
        updateHistory();
        historyPane.toFront();
    }

    public void openHomePageView() {
        shopPane.toFront();
    }

    public void closeNameView() {
        shopPane.toFront();
    }
    // Shope pane methods
    @Override
     public void shoppingCartChanged(CartEvent evt) {

    }


    private void updateHistory() {
        List<Order >orderList = IMatDataHandler.getInstance().getOrders();
        System.out.println(IMatDataHandler.getInstance().getOrders());
        for (Order order : orderList) {
            HistoryItem item = new HistoryItem(order, this);
        }
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

    public void clearShoppingCart() {
        model.clearShoppingCart();
    }
}
