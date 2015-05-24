/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.MainApp;
import assignment.database.RoomQueries;
import assignment.model.Booking;
import assignment.model.Room;
import assignment.model.RoomInfo;
import assignment.model.RoomType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author z5018077
 */
public class TabRoomController implements Initializable {

    @FXML
    TableView<RoomInfo> roomTable;
    @FXML
    private TableColumn<RoomInfo, Integer> roomNumColumn;
    @FXML
    private TableColumn<RoomInfo, String> roomTypeColumn;
    @FXML
    private Label roomNoLabel;
    @FXML
    private Label roomTypeLabel;
    @FXML
    private Label roomTypeDescriptionLabel;
    @FXML
    private Label costPerNightLabel;
    @FXML
    private Label capacityLabel;
    @FXML
    private Label noOfBedsLabel;
    @FXML
    private Button findAvailableRoomBtn;
    
    
    MainApp mainApp;
    private ObservableList<RoomInfo> roomData = FXCollections.observableArrayList();
    private RoomQueries roomQueries = new RoomQueries();

    public ObservableList<RoomInfo> getRoomData() {
        return roomData;
    }
    
    @FXML
    public void showRoomDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FindAvailableRoomDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage findAvailableRoomDialogStage = new Stage();
            findAvailableRoomDialogStage.setTitle("Find Available Rooms");
            findAvailableRoomDialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            findAvailableRoomDialogStage.setScene(scene);
            
            // Show the dialog and wait until the user closes it
            findAvailableRoomDialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Opens a dialog to edit details for the specified booking. If the user
     * clicks OK, the changes are saved into the provided booking object and
     * true is returned.
     *
     * @param booking the booking object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showFindRoomDialog(Booking booking) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FindRoomDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage bookingDialogStage = new Stage();
            bookingDialogStage.setTitle("Find Room");
            bookingDialogStage.initModality(Modality.WINDOW_MODAL);
            //bookingDialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            bookingDialogStage.setScene(scene);

            // Set the person into the controller.
            FindRoomDialogController controller = loader.getController();
            controller.setBookingDialogStage(bookingDialogStage);
            controller.setBooking(booking);
            
            // Show the dialog and wait until the user closes it
            bookingDialogStage.showAndWait();
            System.out.println("Check confirm: " + controller.isConfirmClicked());
            return controller.isConfirmClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Initialize the person table with the two columns.
            roomData.addAll(roomQueries.getRooms());
            roomTable.setItems(roomData);

            roomNumColumn.setCellValueFactory(cellData -> cellData.getValue().roomIDProperty().asObject());
            roomTypeColumn.setCellValueFactory(cellData -> cellData.getValue().roomTypeIDProperty());

            showRoomDetails(null);

            roomTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showRoomDetails(newValue));
        } catch (Exception e) {
            System.out.println("Initilize error!");
        }
    }

    private void showRoomDetails(RoomInfo roomInfo) {
        if (roomInfo != null) {
            // Fill the labels with info from the room object.
            roomNoLabel.setText(Integer.toString(roomInfo.getRoomID()));
            roomTypeLabel.setText(roomInfo.getRoomTypeID());
            roomTypeDescriptionLabel.setText(roomInfo.getDescription());
            costPerNightLabel.setText(Double.toString(roomInfo.getBaseRate()));
            capacityLabel.setText(Integer.toString(roomInfo.getCapacity()));
            noOfBedsLabel.setText(Integer.toString(roomInfo.getNoOfBeds()));
        } else {
            // Room is null, remove all the information.
            roomNoLabel.setText("");
            roomTypeLabel.setText("");
            roomTypeDescriptionLabel.setText("");
            costPerNightLabel.setText("");
            capacityLabel.setText("");
            noOfBedsLabel.setText("");
            
        }
    }
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     *//*
    @FXML
    private void findAvailableRooms() {
        //Booking tempBooking = new Booking();
        boolean confirmClicked = showFindRoomDialog(tempBooking);
        if (confirmClicked) {
           System.out.println("Print out first Name: " + tempBooking.getCustFirstName());
           bookingQueries.insertBooking(tempBooking);           
           
        }        
    }*/
    
    /**
     * Is called by hotel overview controller to give a reference back to the
     * main application.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
