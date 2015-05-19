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
    @FXML
    private Label incorrectUsernameLabel;
    @FXML
    private Label incorrectPasswordLabel;

    private AnchorPane hotelOverview;
    private Stage primaryStage;

    private MainApp mainApp;
    private ObservableList<Employee> loginData = FXCollections.observableArrayList();
    private LoginQueries loginQueries;

    public ObservableList<Employee> getLoginData() {
        return loginData;
    }

    @FXML
    private void checkPassword(ActionEvent event) throws IOException {
        

        String myUser = userIDField.getText();
        String myPassword = passwordField.getText();

        if (loginData.contains(myUser)) {
            if (loginData.contains(myPassword)) {
                primaryStage = (Stage) loginBtn.getScene().getWindow();
                //load up OTHER FXML document
                showHotelOverview();
            } else {
                incorrectPasswordLabel.setVisible(true);
            }
        } else {
            incorrectUsernameLabel.setVisible(true);
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
            loader
                    .setLocation(MainApp.class
                            .getResource("view/HotelOverview.fxml"));
            hotelOverview = (AnchorPane) loader.load();

            HotelOverviewController controller = loader.getController();

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
        incorrectUsernameLabel.setVisible(false);
        incorrectPasswordLabel.setVisible(false);
        loginQueries = new LoginQueries();
        
        
        try {
            loginData.addAll(loginQueries.getLoginDetails());
            //bookingTable.setItems(bookingData);
            System.out.println("Login data in database: " + loginQueries.getLoginDetails());
        
        } catch (Exception e) {
            System.out.println("LoginDetails Initilize error!");
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
