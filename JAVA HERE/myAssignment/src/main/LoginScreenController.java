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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Mak
 */
public class LoginScreenController implements Initializable {
    @FXML 
    private Button loginBtn;
   
    @FXML
    private void switchToMainPage(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;
        if (event.getSource() == loginBtn) {
            //get reference to the button's stage         
            stage = (Stage) loginBtn.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        } 
        
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
     
    /*Stage myStage;

    @FXML
    private void goToMainPage(ActionEvent event) throws IOException {
        // Create Main page
        Stage newStageForMainPage = new Stage();
        newStageForMainPage.setTitle("Application");
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
        Pane newPaneForMainPage = myLoader.load();
        Scene newSceneForMainPage = new Scene(newPaneForMainPage);
        newStageForMainPage.setScene(newSceneForMainPage);

        // Tell main page about my stage
        MainPageController mainPageController = myLoader.getController();
        mainPageController.setBackToLoginScreenStage(myStage);
        mainPageController.setMyStage(newStageForMainPage);

        // Close window of login in
        myStage.hide();

        // Display main page
        newStageForMainPage.show(); 
    }

    void setMyStage(Stage primaryStage) {
        this.myStage = primaryStage;
    }  */  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
