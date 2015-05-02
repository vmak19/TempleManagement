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
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Mak
 */
public class LoginScreenController implements Initializable {
    
    Stage myStage;
    /*
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    @FXML
    private void gotoMainPage(ActionEvent event) throws IOException {
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
    }
}
