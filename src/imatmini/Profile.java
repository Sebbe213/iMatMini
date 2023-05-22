package imatmini;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class Profile extends AnchorPane {

    @FXML AnchorPane pane;
    @FXML TextField cardNumber1;
    @FXML TextField cardNumber2;
    @FXML TextField cardNumber3;
    @FXML TextField cardNumber4;
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
        cardNumberListener(cardNumber1,cardNumber2);
        cardNumberListener(cardNumber2,cardNumber3);
        cardNumberListener(cardNumber3,cardNumber4);
        cardNumberListener(cardNumber4,cardMonth);
        dateListener(cardMonth,cardYear);
        dateListener(cardYear,cardCVC);
        lastThreeNumbersListener(cardCVC);
        addTextLimiter(cardNumber1,4);
        addTextLimiter(cardNumber2,4);
        addTextLimiter(cardNumber3,4);
        addTextLimiter(cardNumber4,4);
        addTextLimiter(cardMonth,2);
        addTextLimiter(cardYear,2);
        addTextLimiter(cardCVC,3);


        this.mainController = mainController;

    }

    @FXML
    public void closeHistory() {
        mainController.closeNameView();
    }

    public void cardNumberListener(TextField tf1, TextField tf2) {
        tf1.textProperty().addListener((obs, oldText, newText) -> {
            if (!tf1.getText().isEmpty()) {
                if (oldText.length() < 4 && newText.length() >= 4) {
                    tf2.requestFocus();
                }
            }
        });
    }
    public void dateListener(TextField tf1, TextField tf2) {
        tf1.textProperty().addListener((obs, oldText, newText) -> {
            if (!tf1.getText().isEmpty()) {
                if (oldText.length() < 2 && newText.length() >= 2) {
                    tf2.requestFocus();
                }
            }
        });
    }
    public void lastThreeNumbersListener(TextField tf) {
        tf.textProperty().addListener((obs, oldText, newText) -> {
            if (!tf.getText().isEmpty()) {
                if (oldText.length() < 3 && newText.length() >= 3) {
                    pane.requestFocus();
                }
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
}
