package assignment;

import assignment.database.DatabaseQuery;
import assignment.database.BookingQueries;
import assignment.database.DatabaseSetup;
import assignment.database.EmployeeQueries;
import assignment.database.RoomQueries;
import assignment.database.RoomTypeQueries;
import assignment.model.Booking;
import assignment.model.Employee;
import assignment.model.Room;
import assignment.model.RoomType;
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
    private ObservableList<Room> roomData = FXCollections.observableArrayList();
    private ObservableList<RoomType> roomTypeData = FXCollections.observableArrayList();

    public ObservableList<Booking> getBookingData() {
        return bookingData;
    }

    public ObservableList<Employee> getEmployeeData() {
        return employeeData;
    }

    public ObservableList<Room> getRoomData() {
        return roomData;
    }

    public ObservableList<RoomType> getRoomTypeData() {
        return roomTypeData;
    }

    public MainApp() {
    }

    public void buildData() {
        try {
            System.out.println("buildData() run");
            DatabaseSetup.setupDatabase();
            BookingQueries bookingQueries = new BookingQueries();
            EmployeeQueries employeeQueries = new EmployeeQueries();
            RoomQueries roomQueries = new RoomQueries();
            RoomTypeQueries roomTypeQueries = new RoomTypeQueries();
            bookingQueries.insertBooking(new Booking("Hans", "Muster", 2, 12, LocalDate.of(1999, 2, 23), 3, LocalDate.of(2099, 2, 22), LocalDate.of(2199, 2, 2), true, false, 12.2, 12));
            employeeQueries.insertEmployee(new Employee("pw1", "Jane", "Chan", false));
          //  roomQueries.insertRoom(new Room(1, 2));
          //  roomTypeQueries.insertRoomType(new RoomType(2, "single", 2500.95, 2));

            // Copy data from database to ObeservableList
            System.out.println("Bookings in database (in buildData) :" + bookingQueries.getBookings());
            System.out.println("Employees in database (in buildData) :" + employeeQueries.getEmployees());
            System.out.println("Rooms in database (in buildData) :" + roomQueries.getRooms());
            System.out.println("RoomTypes in database (in buildData) :" + roomTypeQueries.getRoomTypes());
            //Sim edit: bookingData = FXCollections.observableArrayList(bookingQueries.getBookings());
            bookingData.addAll(bookingQueries.getBookings()); //Sim edit line added
            employeeData.addAll(employeeQueries.getEmployees());
            roomData.addAll(roomQueries.getRooms());
            roomTypeData.addAll(roomTypeQueries.getRoomTypes());
            //System.out.println("Booking Data (in buildData) is: " + bookingData); //for testing purposes
        } catch (Exception e) {
            System.out.println("buildData() ERROR!");
        }
    }

    ;
    
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
    /**
     * public boolean showPersonEditDialog(Person person) { try { // Load the
     * fxml file and create a new stage for the popup dialog. FXMLLoader loader
     * = new FXMLLoader();
     * loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
     * AnchorPane page = (AnchorPane) loader.load();
     *
     * // Create the dialog Stage. Stage dialogStage = new Stage();
     * dialogStage.setTitle("Edit Person");
     * dialogStage.initModality(Modality.WINDOW_MODAL);
     * dialogStage.initOwner(primaryStage); Scene scene = new Scene(page);
     * dialogStage.setScene(scene);
     *
     * // Set the person into the controller. PersonEditDialogController
     * controller = loader.getController();
     * controller.setDialogStage(dialogStage); controller.setPerson(person);
     *
     * // Show the dialog and wait until the user closes it
     * dialogStage.showAndWait();
     *
     * return controller.isOkClicked(); } catch (IOException e) {
     * e.printStackTrace(); return false; } }
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
