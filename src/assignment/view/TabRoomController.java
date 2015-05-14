/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.model.Room;
import assignment.model.RoomInfo;
import assignment.model.RoomType;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Initialize the person table with the two columns.
            roomNumColumn.setCellValueFactory(cellData -> cellData.getValue().roomIDProperty().asObject());
            roomTypeColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

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
            roomTypeLabel.setText(roomInfo.getDescription());
            costPerNightLabel.setText(Double.toString(roomInfo.getBaseRate()));
        } else {
            // Room is null, remove all the information.
            roomNoLabel.setText("");
            roomTypeLabel.setText("");
            costPerNightLabel.setText("");
        }
    }
}
