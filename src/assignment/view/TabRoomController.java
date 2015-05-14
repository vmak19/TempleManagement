/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.model.Room;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author z5018077
 */
public class TabRoomController extends HotelOverviewController implements Initializable {

    @FXML
    TableView<Room> roomTable;
    @FXML
    private TableColumn<Room, Integer> roomIDColumn;
    @FXML
    private TableColumn<Room, Integer> roomTypeIDColumn;

    @FXML
    private Label roomNoLabel;  //aka roomID
    @FXML
    private Label roomTypeLabel;    //aka roomTypeID
    @FXML
    private Label costPerNightLabel;    //aka base rate + additional

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Initialize the person table with the two columns.
            roomIDColumn.setCellValueFactory(cellData -> cellData.getValue().roomIDProperty().asObject());
            roomTypeIDColumn.setCellValueFactory(cellData -> cellData.getValue().roomTypeIDProperty().asObject());

            roomTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showRoomDetails(newValue));
            // Add observable list data to the table
            //employeeTable.setItems(mainApp.getBookingData());
            System.out.println("TabRoom initialized!");
        } catch (Exception e) {
            System.out.println("Initilize error!");
        }
    }

    private void showRoomDetails(Room room) {
        if (room != null) {
            // Fill the labels with info from the room object.
            roomNoLabel.setText(Integer.toString(room.getRoomID()));
            roomTypeLabel.setText(Integer.toString(room.getRoomTypeID()));
            costPerNightLabel.setText(Double.toString(room.getBaseRate()));
        } else {
            // Room is null, remove all the information.
            roomNoLabel.setText("");
            roomTypeLabel.setText("");
            costPerNightLabel.setText("");
        }
    }
}
