package imatmini;

import imatmini.iMatMiniController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Rectangle;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Flow;

public class Checkout extends AnchorPane implements ShoppingCartListener {
    @FXML AnchorPane pane;
    @FXML
    private ComboBox<String> timeCombo;
    @FXML
    TextField cardNumber1;

    private String cardNumber1String;
    @FXML TextField cardNumber2;
    private String cardNumber2String;
    @FXML TextField cardNumber3;
    private String cardNumber3String;
    @FXML TextField cardNumber4;
    private String cardNumber4String;
    @FXML TextField cardMonth;
    @FXML TextField cardYear;
    @FXML TextField cardCVC;
    @FXML TextField firstNameField;
    @FXML TextField lastNameField;
    @FXML TextField addressField;
    @FXML TextField cityField;
    @FXML TextField postalCodeField;
    @FXML TextField phoneNumberField;

    iMatMiniController mainController;
    Customer customer;
    CreditCard creditcard;

    IMatDataHandler dataHandler = IMatDataHandler.getInstance();

    @FXML
    private FlowPane cartFlowPane;
    @FXML
    private Label totalCostLabel;
    @FXML
    private AnchorPane receiptPane;
    @FXML
    private Label orderDate;

    @FXML
    private Label orderId;

    @FXML private Button buyButton;

    @FXML private Label error1;

    @FXML private Label error2;

    @FXML private Label error3;

    private Model model = Model.getInstance();
    boolean purchaseCompleted = false;

    public Checkout(iMatMiniController mainController) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Checkout.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("Checkout");
            throw new RuntimeException(exception);
        }
        cardListener(cardNumber1,cardNumber2, 4);
        cardListener(cardNumber2,cardNumber3, 4);
        cardListener(cardNumber3,cardNumber4, 4);
        cardListener(cardNumber4,cardMonth, 4);
        cardListener(cardMonth,cardYear, 2);
        cardListener(cardYear,cardCVC, 2);
        cardListener(cardCVC, 3);
        addTextLimiter(cardNumber1,4);
        addTextLimiter(cardNumber2,4);
        addTextLimiter(cardNumber3,4);
        addTextLimiter(cardNumber4,4);
        addTextLimiter(cardMonth,2);
        addTextLimiter(cardYear,2);
        addTextLimiter(cardCVC,3);
        numericOnly(cardNumber1);
        numericOnly(cardNumber2);
        numericOnly(cardNumber3);
        numericOnly(cardNumber4);
        numericOnly(cardYear);
        numericOnly(cardMonth);
        numericOnly(cardCVC);

        this.mainController = mainController;
        this.customer = dataHandler.getCustomer();
        this.creditcard = dataHandler.getCreditCard();

        this.totalCostLabel.setText(String.format("%.2f", dataHandler.getShoppingCart().getTotal() + 40) + "kr");
        this.timeCombo.getItems().addAll("imorgon 9:00-12:00", "imorgon 13:00-16:00","imorgon 17:00-20:00");

        dataHandler.getShoppingCart().addShoppingCartListener(this);

        timeCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                orderId.setText(newValue);
            }
        });

        init();
    }

    @FXML
    public void handlePressBackButton(ActionEvent event) {goBack();}

    public void goBack() {
        mainController.openShoppingCart();
    }

    public void fillCartFlowPane() {
        cartFlowPane.getChildren().clear();
        if(Model.getInstance().getShoppingCart().getItems().isEmpty()) {
            System.out.println("Kundvagn Tom");
            return;
        }
        for(ShoppingItem item : Model.getInstance().getShoppingCart().getItems()) {
            Product product = item.getProduct();
            if(item.getAmount() == 0) {continue;}
            cartFlowPane.getChildren().add(new CheckoutItem(item,this));
        }
    }

    private void updatePriceLabel() {
        this.totalCostLabel.setText(String.format("%.2f", dataHandler.getShoppingCart().getTotal() + 40) + "kr");
    }

    @FXML
    private void completePurchase() {
        mainController.handleBuyItemsAction();
        receiptPane.toFront();
        purchaseCompleted = true;
    }

    @FXML
    private void keepShopping() {;
        receiptPane.toBack();
        mainController.closeNameView();
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        updatePriceLabel();
    }


    public void cardListener(TextField tf1, TextField tf2, int length) {
        tf1.textProperty().addListener((obs, oldText, newText) -> {

            if (!tf1.getText().isEmpty() && oldText.length() < length && newText.length() >= length) {
                tf2.requestFocus();
            }

        });
    }

    public void cardListener(TextField tf, int length) {
        tf.textProperty().addListener((obs, oldText, newText) -> {

            if (!tf.getText().isEmpty() && oldText.length() < length && newText.length() >= length) {
                if(!cardCVC.getText().isBlank()) {creditcard.setVerificationCode(Integer.parseInt(newText));}
                pane.requestFocus();
            }

        });
    }
    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }

    @FXML
    public void saveInfo() {
        customer.setFirstName(firstNameField.getText());
        customer.setLastName(lastNameField.getText());
        customer.setAddress(addressField.getText());
        customer.setPostCode(postalCodeField.getText());
        customer.setPostAddress(cityField.getText());
        customer.setMobilePhoneNumber(phoneNumberField.getText());

        creditcard.setCardNumber( "start-" + cardNumber1.getText() + "-" + cardNumber2.getText() + "-" + cardNumber3.getText() + "-" + cardNumber4.getText() + "-end");
        //System.out.println(creditcard.getCardNumber());

        creditcard.setHoldersName(customer.getFirstName() + " " + customer.getLastName());
        if(!cardYear.getText().isBlank()) {creditcard.setValidYear(Integer.parseInt(cardYear.getText()));}
        if(!cardMonth.getText().isBlank()) {creditcard.setValidMonth(Integer.parseInt(cardMonth.getText()));}
    }

    @FXML
    public void errorMessage(ActionEvent event) {
        boolean hasError = false;

        for (Node node : pane.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;

                textField.setOnMouseClicked(e -> {
                    if (textField.getStyleClass().contains("error-textfield")) {
                        textField.clear();
                        textField.getStyleClass().remove("error-textfield");
                    }
                });

                if (textField.getText().isEmpty()) {
                    System.out.println("empty");
                    textField.getStyleClass().add("error-textfield");
                    textField.setText("Fyll i denna rutan");
                    hasError = true;
                    error1.toFront();
                } else if ((textField == cardNumber1 || textField == cardNumber2 || textField == cardNumber3 || textField == cardNumber4 || textField == cardCVC || textField == cardYear || textField == cardMonth || textField == phoneNumberField || textField == postalCodeField ) && textField.getText().matches("[a-zA-Z]+")) {
                    textField.getStyleClass().add("error-textfield");
                    textField.setText("fel");
                    hasError = true;
                } else if (textField == cardNumber1 && cardNumber1.getText().length() < 4) {
                    textField.getStyleClass().add("error-textfield");
                    textField.setText("fel");
                    hasError = true;
                    error2.toFront();
                } else if ((textField == cardNumber2 || textField == cardNumber3 || textField == cardNumber4) && textField.getText().length() != 4) {
                    textField.getStyleClass().add("error-textfield");
                    textField.setText("fel");
                    hasError = true;
                    error2.toFront();
                } else if ((textField == cardYear || textField == cardMonth) && textField.getText().length() != 2) {
                    textField.getStyleClass().add("error-textfield");
                    textField.setText("fel");
                    hasError = true;
                    error2.toFront();
                } else if (textField == cardCVC && textField.getText().length() != 3) {
                    textField.getStyleClass().add("error-textfield");
                    textField.setText("fel");
                    hasError = true;
                    error2.toFront();
                }
            }



            if (timeCombo.getValue() == null) {
                timeCombo.getStyleClass().add("error-textfield");
                hasError = true;
            } else {
                timeCombo.getStyleClass().remove("error-textfield");
            }

        }

        if(model.getShoppingCart().getItems().isEmpty()){
            error3.toFront();
        }

        if (!hasError && !model.getShoppingCart().getItems().isEmpty()) {
            completePurchase();
            error2.toBack();
            error1.toBack();
            error3.toBack();
            check();

        }
    }
    @FXML
    public void check() {
        for (Node node : pane.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField.getText().equals("Fyll i denna rutan") || textField.getText().equals("fel") || textField.getText().isBlank()) {
                    textField.clear();
                    textField.getStyleClass().remove("error-textfield");
                }
            }
        }
    }

    public static void numericOnly(final TextField field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    field.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
    void init() {
        firstNameField.setText(customer.getFirstName());
        lastNameField.setText(customer.getLastName());
        addressField.setText(customer.getAddress());
        postalCodeField.setText(customer.getPostCode());
        cityField.setText(customer.getPostAddress());
        phoneNumberField.setText(customer.getMobilePhoneNumber());

       // System.out.println("Is not first run");
        if (!creditcard.getCardNumber().isEmpty()) {
            String[] number = creditcard.getCardNumber().split("-");
            if (!(number[1].equals(""))) {cardNumber1.setText(number[1]);}
            if (!(number[2].equals(""))) {cardNumber2.setText(number[2]);}
            if (!(number[3].equals(""))) {cardNumber3.setText(number[3]);}
            if (!(number[4].equals(""))) {cardNumber4.setText(number[4]);}
        }


        System.out.println(creditcard.getValidMonth() + "  " + creditcard.getValidYear() + "  " + creditcard.getVerificationCode());
        if(creditcard.getValidMonth() < 10) {cardMonth.setText("0" + String.format("%d",creditcard.getValidMonth()));}
        else {cardMonth.setText(String.format("%d",creditcard.getValidMonth()));}
        if(creditcard.getValidYear() < 10) {cardYear.setText("0" + String.format("%d",creditcard.getValidYear()));}
        else {cardYear.setText(String.format("%d",creditcard.getValidYear()));}

       // System.out.println(creditcard.getVerificationCode() + "FÖRE");
        if((creditcard.getVerificationCode() < 100) && (creditcard.getVerificationCode() >= 10)) {cardCVC.setText("0" + String.format("%d",creditcard.getVerificationCode()));}
        else if (creditcard.getVerificationCode() < 10) {cardCVC.setText("00" + String.format("%d",creditcard.getVerificationCode()));}
        else {cardCVC.setText(String.format("%d",creditcard.getVerificationCode()));}
       // System.out.println(creditcard.getVerificationCode());
        cardCVC.setText(String.format("%d",creditcard.getVerificationCode()));
    }
}
