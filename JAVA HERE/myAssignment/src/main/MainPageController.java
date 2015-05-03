/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mak
 */
public class MainPageController implements Initializable {

    //private Stage loginScreenStage;
    //private Stage myStage;
    @FXML
    private Button logoutBtn;
    @FXML
    private Button editBillingBtn;
    @FXML
    private Button editBookingBtn;
    @FXML
    private Button editServiceBtn;
    @FXML
    private Button editEmployeeBtn;
    @FXML
    private Button cancelBtn;

    @FXML
    private void goToEditTab(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        if (event.getSource() == editBillingBtn) {
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("EditBillingDialog.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Billing");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(editBillingBtn.getScene().getWindow());
            stage.showAndWait();
        } else if (event.getSource() == editBookingBtn) {
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("EditBookingDialog.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Booking");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(editBookingBtn.getScene().getWindow());
            stage.showAndWait();
        }  else if (event.getSource() == editServiceBtn) {
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("EditServiceDialog.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Service");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(editServiceBtn.getScene().getWindow());
            stage.showAndWait();
        }  else if (event.getSource() == editEmployeeBtn) {
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("EditEmployeeDialog.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Employee");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(editEmployeeBtn.getScene().getWindow());
            stage.showAndWait();
        } else {
            stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        }
    }
    
    @FXML
    private void goToLoginScreen(ActionEvent e) throws IOException {
        //Stage stage;
        //Parent root;       

        if (e.getSource() == logoutBtn) {
            //get reference to the button's stage         
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            //load up OTHER FXML document
            Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        /*if (e.getSource() == logoutBtn) {
         //get reference to the button's stage         
         stage = (Stage) logoutBtn.getScene().getWindow();
         //load up OTHER FXML document
         root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
         } else if (e.getSource() == editBookingBtn) {
         stage = (Stage) editBookingBtn.getScene().getWindow();
         root = FXMLLoader.load(getClass().getResource("EditBookingDialog.fxml"));
         } else if (e.getSource() == editServiceBtn) {
         stage = (Stage) editServiceBtn.getScene().getWindow();
         root = FXMLLoader.load(getClass().getResource("EditServiceDialog.fxml"));
         } else if (e.getSource() == editBillingBtn) {
         stage = (Stage) editBillingBtn.getScene().getWindow();
         root = FXMLLoader.load(getClass().getResource("EditBillingDialog.fxml"));
         } else if (e.getSource() == editEmployeeBtn) {
         stage = (Stage) editEmployeeBtn.getScene().getWindow();
         root = FXMLLoader.load(getClass().getResource("EditEmployeeDialog.fxml"));
         } */
    }
    /*
     @FXML
     private void goBackToLoginScreen(ActionEvent e) {
     myStage.close();
     loginScreenStage.show();
     }

     void setBackToLoginScreenStage(Stage myStage) {
     loginScreenStage = myStage;
     }

     void setMyStage(Stage newStageForMainPage) {
     myStage = newStageForMainPage;
     }
     */

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
