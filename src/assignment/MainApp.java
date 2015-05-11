package assignment;

import assignment.database.DatabaseQuery;
import assignment.database.BookingQueries;
import assignment.database.DatabaseSetup;
import assignment.model.Booking;
import assignment.model.Employee;
import assignment.view.HotelOverviewController;
import assignment.view.LoginScreenController;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
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
    private AnchorPane loginScreen;
    private ObservableList<Booking> bookingData = FXCollections.observableArrayList(); //Sim edit
    private ObservableList<Employee> employeeData = FXCollections.observableArrayList();
    
    public ObservableList<Booking> getBookingData() {
        return bookingData;
    }
    
    public ObservableList<Employee> getEmployeeData() {
        return employeeData;
    }
    
    public MainApp() {
        employeeData.add(new Employee(12345, "p1", "Jane", "Chan", false));
        employeeData.add(new Employee(12245, "p1", "Bill", "Bob", false));
        employeeData.add(new Employee(12246, "p1", "Mary", "Kim", false));
        employeeData.add(new Employee(12247, "p1", "Mi", "T", false));
        employeeData.add(new Employee(12355, "p1", "L", "D", true));
        employeeData.add(new Employee(12346, "p1", "F", "C", true));
    }
    
    public void buildData(){
        try {
            System.out.println("buildData() run");
            DatabaseSetup.setupDatabase();
            BookingQueries bookingQueries = new BookingQueries();
        
            //bookingQueries.insertBooking(new Booking("Hans", "Muster", 2, 12, LocalDate.of(1999, 2, 23), 3, LocalDate.of(2099, 2, 22) ,LocalDate.of(2199, 2, 2) , true, false, 12.2,12));
        
            // Copy data from database to ObeservableList
            System.out.println("Bookings in database (in buildData) :" + bookingQueries.getBookings());
            //Sim edit: bookingData = FXCollections.observableArrayList(bookingQueries.getBookings());
            bookingData.addAll(bookingQueries.getBookings()); //Sim edit line added
            System.out.println("Booking Data (in buildData) is: " + bookingData);
        } catch (Exception e) {
            System.out.println("buildData() ERROR!");
        }
    };
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");
        
        buildData();
        showLoginScreen();
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LoginScreen.fxml"));
            loginScreen = (AnchorPane) loader.load();
           
            LoginScreenController controller = loader.getController();
            controller.setMainApp(this);
            
            Scene scene = new Scene(loginScreen);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("showLoginScreen() Error!");
            e.printStackTrace();
        }
    }
    
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