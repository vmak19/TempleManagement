package assignment.view;

import assignment.MainApp;
import assignment.database.LogQueries;
import assignment.database.LoginQueries;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Mak
 */
public class LoginScreenController implements Initializable {

    @FXML
    private Button loginBtn;
    @FXML
    private Button loginBtnOverride;
    @FXML
    public TextField userIDField;
    @FXML
    public PasswordField passwordField;

    private AnchorPane hotelOverview;
    private Stage primaryStage;

    private MainApp mainApp;

    private LoginQueries loginQueries;
    private LogQueries logQueries = new LogQueries();
    private LoginScreenController loginScreenController;

    int myUser;
    String myPassword;

    public int getUserID() {
        return myUser;
    }

    public String getPassword() {
        return myPassword;
    }

    @FXML   //Used when ENTER key is pressed in a field
    public void buttonPressed(KeyEvent e) throws IOException {
        if (e.getCode().toString().equals("ENTER")) {
            checkPassword();
        }
    }

    @FXML   //Used when login btn is clicked
    private void checkPassword(ActionEvent event) throws IOException {
        checkPassword();
    }

    private void checkPassword() {
        try {
            loginQueries = new LoginQueries();
            myUser = Integer.parseInt(userIDField.getText());
            myPassword = passwordField.getText();
            if (!loginQueries.getLoginDetails(myUser, myPassword).isEmpty()) {
                primaryStage = (Stage) loginBtn.getScene().getWindow();
                //load up OTHER FXML document
                showHotelOverview();
            } else {
                // Nothing selected.
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("Incorrect Input");
                alert.setHeaderText("UserID or Password is incorrect");
                alert.setContentText("Please check your UserID or Password.");

                alert.showAndWait();

            }
        } catch (Exception e) {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Empty Field");
            alert.setHeaderText("UserID or Password is Empty");
            alert.setContentText("Please enter your UserID and Password.");

            alert.showAndWait();
        }
    }

    public void showHotelOverview() {
        try {
            //get reference to the button's stage  
            primaryStage = (Stage) loginBtn.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/HotelOverview.fxml"));
            hotelOverview = (AnchorPane) loader.load();

            HotelOverviewController controller = loader.getController();
            controller.setUserID(myUser);
            controller.setPrimaryStage(primaryStage);
            controller.setMainApp(mainApp);

            Scene scene = new Scene(hotelOverview);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("showHotelOverview() Error!");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
