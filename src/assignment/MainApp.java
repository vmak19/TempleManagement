package assignment;

import assignment.model.Booking;
import assignment.view.HotelOverviewController;
import assignment.view.LoginScreenController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SONY
 */
public class MainApp extends Application {

    private Stage primaryStage;
    //private BorderPane rootLayout;
    private AnchorPane loginScreen;
    private ObservableList<Booking> bookingData = FXCollections.observableArrayList();
    
    public ObservableList<Booking> getBookingData() {
        return bookingData;
    }
    
    public MainApp() {
        bookingData.add(new Booking("Hans", "Muster"));
        bookingData.add(new Booking("Ruth", "Mueller"));
        bookingData.add(new Booking("Heinz", "Kurz"));
        bookingData.add(new Booking("Cornelia", "Meier"));
        bookingData.add(new Booking("Werner", "Meyer"));
        bookingData.add(new Booking("Lydia", "Kunz"));
        bookingData.add(new Booking("Anna", "Best"));
        bookingData.add(new Booking("Stefan", "Meier"));
        bookingData.add(new Booking("Martin", "Mueller"));
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        showLoginScreen();
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LoginScreen.fxml"));
            loginScreen = (AnchorPane) loader.load();
           
            LoginScreenController controller = loader.getController();
            //controller.setMainApp(this);
            
            Scene scene = new Scene(loginScreen);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("showLoginScreen() Error!");
            e.printStackTrace();
        }
    }
    /*
    public void showHotelOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/HotelOverview.fxml"));
            Hotel  = (AnchorPane) loader.load();
           
            HotelOverviewController controller = loader.getController();
            //controller.setMainApp(this);
            
            Scene scene = new Scene(loginScreen);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("showLoginScreen() Error!");
            e.printStackTrace();
        }
    }
*/
    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    /**public boolean showPersonEditDialog(Person person) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }*/

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}