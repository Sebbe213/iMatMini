/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imatmini;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;
import se.chalmers.cse.dat216.project.ShoppingCartListener;

import java.io.IOException;

/**
 *
 * @author oloft
 */
public class NamePanel extends AnchorPane implements ShoppingCartListener {

    @FXML
    TextField fnameTextField;

    @FXML
    TextField lnameTextField;

    @FXML
    TextArea cartTextArea;

    private Model model = Model.getInstance();

    private Customer customer;
    private iMatMiniController mainController;

    public NamePanel(iMatMiniController mainController) {
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NamePanel.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.mainController = mainController;
        this.customer = model.getCustomer();
        model.getShoppingCart().addShoppingCartListener(this);

        updateView();
    }

    @FXML
    public void closeNameView(){

        // Save values
        customer.setFirstName(fnameTextField.getText());
        customer.setLastName(lnameTextField.getText());
        mainController.closeNameView();

    }

    @Override
    public void shoppingCartChanged(CartEvent evt) {
        updateView();
    }

    private void updateView() {

        System.out.println("uodateView");

        fnameTextField.setText(customer.getFirstName());
        lnameTextField.setText(customer.getLastName());

        String cartText = "";

        ShoppingCart shoppingCart = Model.getInstance().getShoppingCart();

        System.out.println("before " + cartText + shoppingCart.getItems().size());


        for (ShoppingItem item: shoppingCart.getItems()) {

            cartText = cartText + item.getProduct().getName() + " " + item.getAmount() + "\n";

            System.out.println("during " + cartText);

        }
        cartTextArea.setText(cartText);
    }

}
