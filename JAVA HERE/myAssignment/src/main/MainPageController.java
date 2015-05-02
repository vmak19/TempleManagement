/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sim
 */
public class MainPageController implements Initializable {

    private Stage loginScreenStage;
    private Stage myStage;
    private Button goToLoginScreen;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
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
    
}
