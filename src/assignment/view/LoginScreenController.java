/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.MainApp;
import assignment.database.LoginQueries;
import assignment.model.Employee;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

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


    public String getUserID() {
        String myUser = userIDField.getText();
        return myUser;
    }    

    public String getPassword() {
        String myPassword = passwordField.getText();
        return myPassword;
    }

    @FXML
    private void checkPassword(ActionEvent event) throws IOException {

        loginQueries = new LoginQueries();
        loginQueries.setLoginScreenController(this);
        if (!loginQueries.getLoginDetails().isEmpty()) {
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
    }

    //TO DELETE AFTER
    @FXML
    private void switchToMainPage(ActionEvent event) throws IOException {

        if (event.getSource() == loginBtnOverride) {
            //get reference to the button's stage         
            primaryStage = (Stage) loginBtn.getScene().getWindow();
            //load up OTHER FXML document
            showHotelOverview();
        }
    }
    /*if (event.getSource() == loginBtn) {
     //TODO change FXML fxid reference from switchTOMainPage to checkPassword
     String x = tf.getText();
     String y = pf.getText();
            
     if (x.equals(rec.getString("users_name"))) {
     if (y.equals(rec.getString("users_password"))  {
     //get reference to the button's stage         
     primaryStage = (Stage) loginBtn.getScene().getWindow();
     //load up OTHER FXML document
     showHotelOverview();
     }
     //else 
     }*/

    public void showHotelOverview() {
        try {
            //get reference to the button's stage  
            primaryStage = (Stage) loginBtn.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/HotelOverview.fxml"));
            hotelOverview = (AnchorPane) loader.load();
            
            HotelOverviewController controller = loader.getController();
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
