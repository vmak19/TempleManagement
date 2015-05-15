/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.model.Booking;
import assignment.model.Room;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author SONY
 */
public class EditBookingDialogController {
    
    @FXML private TableView selectedRoomTable;
    @FXML private TableColumn<Room, String> RoomTypeColumn;
    @FXML private TableColumn<Booking, Double> TotalCostColumn;
    @FXML private TableColumn<Booking, Integer> NumPeopleColumn;
    
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField numBreakfastField;
    @FXML private TextField checkInField;
    @FXML private TextField checkOutField;
    @FXML private TextField amountPaidField;
    @FXML private TextField amountDueField;
    
    @FXML private CheckBox earlyCheckInBox;
    @FXML private CheckBox lateCheckOutBox;
    
    @FXML private Button confirmButton;
    @FXML private Button cancelButton;

    private Stage dialogStage;
    private Booking booking;
    private boolean okClicked = false;
  //  private FindRoomDialogController findRoomDialogController;
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    //public void setFoundRoom(FindRoomDialogController findRoomDialogController) {
    //    this.findRoomDialogController = findRoomDialogController;
    //}
}
