package imatmini;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Profile extends AnchorPane {

    @FXML AnchorPane pane;
    @FXML TextField cardNumber1;

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
    public Profile(iMatMiniController mainController) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Profile.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("Profile");
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
        numericOnly(cardMonth);
        numericOnly(cardYear);
        numericOnly(cardCVC);

        this.mainController = mainController;
        this.customer = IMatDataHandler.getInstance().getCustomer();
        this.creditcard = IMatDataHandler.getInstance().getCreditCard();

        init();
    }

   void init() {
        firstNameField.setText(customer.getFirstName());
        lastNameField.setText(customer.getLastName());
        addressField.setText(customer.getAddress());
        postalCodeField.setText(customer.getPostCode());
        cityField.setText(customer.getPostAddress());
        phoneNumberField.setText(customer.getMobilePhoneNumber());

        if(!creditcard.getCardNumber().isEmpty()) {
            String[] number = creditcard.getCardNumber().split("-");
            if (!(number[1].equals(""))) {cardNumber1.setText(number[1]);}
            if (!(number[2].equals(""))) {cardNumber2.setText(number[2]);}
            if (!(number[3].equals(""))) {cardNumber3.setText(number[3]);}
            if (!(number[4].equals(""))) {cardNumber4.setText(number[4]);}
        }

        if (creditcard.getValidMonth() < 10) {
            cardMonth.setText("0" + String.format("%d", creditcard.getValidMonth()));
        } else {
            cardMonth.setText(String.format("%d", creditcard.getValidMonth()));
        }
        if (creditcard.getValidYear() < 10) {
            cardYear.setText("0" + String.format("%d", creditcard.getValidYear()));
        } else {
            cardYear.setText(String.format("%d", creditcard.getValidYear()));
        }

        //System.out.println(creditcard.getVerificationCode() + "FÖRE");
        if ((creditcard.getVerificationCode() < 100) && (creditcard.getVerificationCode() >= 10)) {
            cardCVC.setText("0" + String.format("%d", creditcard.getVerificationCode()));
        } else if (creditcard.getVerificationCode() < 10) {
            cardCVC.setText("00" + String.format("%d", creditcard.getVerificationCode()));
        } else {
            cardCVC.setText(String.format("%d", creditcard.getVerificationCode()));
        }
        //System.out.println(creditcard.getVerificationCode());
        cardCVC.setText(String.format("%d", creditcard.getVerificationCode()));
    }

    @FXML
    public void closeHistory() {
        mainController.closeNameView();
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
       // System.out.println(creditcard.getCardNumber());

        creditcard.setHoldersName(customer.getFirstName() + " " + customer.getLastName());
        if(!cardYear.getText().isBlank()) {creditcard.setValidYear(Integer.parseInt(cardYear.getText()));}
        else {cardYear.clear();}
        if(!cardMonth.getText().isBlank()) {creditcard.setValidMonth(Integer.parseInt(cardMonth.getText()));}
        else {creditcard.setValidMonth(Integer.parseInt(""));}


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
}
