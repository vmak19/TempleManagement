/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.MainApp;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Mak
 */
public class LoginScreenController implements Initializable {
    @FXML 
    private Button loginBtn;
    
    AnchorPane hotelOverview;
    
    Stage primaryStage;
    
    //Sim added this field
    private MainApp mainApp;
   
    @FXML
    private void switchToMainPage(ActionEvent event) throws IOException {

        if (event.getSource() == loginBtn) {
            //get reference to the button's stage         
            primaryStage = (Stage) loginBtn.getScene().getWindow();
            //load up OTHER FXML document
            showHotelOverview();
        } 
    }
     
    public void showHotelOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/HotelOverview.fxml"));
            hotelOverview  = (AnchorPane) loader.load();
           
            HotelOverviewController controller = loader.getController();
            // Sim commented this out: MainApp mainApp = new MainApp();
            //initializes records
            controller.setMainApp(mainApp);
            
            Scene scene = new Scene(hotelOverview);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("showHotelOverview() Error!");
            e.printStackTrace();
        }
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

    // Sim added this method
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
