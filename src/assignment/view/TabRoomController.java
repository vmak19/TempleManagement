/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.database.RoomQueries;
import assignment.model.Room;
import assignment.model.RoomInfo;
import assignment.model.RoomType;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private Label costPerNightLabel;
    @FXML
    private Label capacity;

    private ObservableList<RoomInfo> roomData = FXCollections.observableArrayList();
    private RoomQueries roomQueries = new RoomQueries();

    public ObservableList<RoomInfo> getRoomData() {
        return roomData;
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
            System.out.println("TabRoom initialized!");
        } catch (Exception e) {
            System.out.println("Initilize error!");
        }
    }

    private void showRoomDetails(RoomInfo roomInfo) {
        if (roomInfo != null) {
            // Fill the labels with info from the room object.
            roomNoLabel.setText(Integer.toString(roomInfo.getRoomID()));
            roomTypeLabel.setText(roomInfo.getRoomTypeID());
            costPerNightLabel.setText(Double.toString(roomInfo.getBaseRate()));
            capacity.setText(Integer.toString(roomInfo.getCapacity()));
        } else {
            // Room is null, remove all the information.
            roomNoLabel.setText("");
            roomTypeLabel.setText("");
            costPerNightLabel.setText("");
            capacity.setText("");
        }
    }
}
