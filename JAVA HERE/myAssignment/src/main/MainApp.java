/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Mak
 */
public class MainApp extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Create login screen
        primaryStage.setTitle("Application");
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
        Pane myPane = (Pane) myLoader.load();

        //------------------------
        // Get the controller of login screen        
       // LoginScreenController controller = (LoginScreenController) myLoader.getController();
        
        // Give the controller access to the stage it's on
       // controller.setMyStage(primaryStage);
        //------------------------

        // Display
        Scene myScene = new Scene(myPane);
        primaryStage.setScene(myScene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
