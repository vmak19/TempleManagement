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
import assignment.model.RoomInfo;
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

    public MainApp() {}

    public void buildData() {
        try {
            System.out.println("buildData() run");
            DatabaseSetup.setupDatabase();
        } catch (Exception e) {
            System.out.println("buildData() ERROR!");
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Hotel Application");

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

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
