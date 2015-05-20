/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.database.BookingQueries;
import assignment.model.Booking;
import assignment.model.Room;
import assignment.model.RoomInfo;
import assignment.util.DateUtil;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class EditBookingDialogController implements Initializable {
    
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

    private Booking booking;
    private boolean confirmClicked = false;
    private FindRoomDialogController foundRoom;
    private Stage bookingDialogStage;
    private ObservableList<RoomInfo> selectedRoomData = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    /**
     * Called when the user clicks confirm.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            
            booking.setCustFirstName(firstNameField.getText());
            booking.setCustLastName(lastNameField.getText());
            booking.setNumBreakfast(Integer.parseInt(numBreakfastField.getText()));
            booking.setCheckIn(DateUtil.parse(checkInField.getText()));
            booking.setCheckOut(DateUtil.parse(checkOutField.getText()));
            booking.setEarlyCheckIn(earlyCheckInBox.isSelected());
            booking.setLateCheckOut(lateCheckOutBox.isSelected());
            booking.setAmountPaid(Double.parseDouble(amountPaidField.getText()));
            booking.setAmountDue(Double.parseDouble(amountDueField.getText()));
            
            confirmClicked = true;
            bookingDialogStage.close();
        }
    }
    
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n"; 
        }
        if (numBreakfastField.getText() == null || numBreakfastField.getText().length() == 0) {
            errorMessage += "No valid number of breakfast days!\n"; 
        } else {
            // Try to parse number of breakfast days into an int.
            try {
                Integer.parseInt(numBreakfastField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid number of breakfast days (must be an integer)!\n"; 
            }
        }
        if (amountPaidField.getText() == null || amountPaidField.getText().length() == 0) {
            errorMessage += "No valid amount paid!\n"; 
        } else {
            // Try to parse number of breakfast days into a double.
            try {
                Double.parseDouble(amountPaidField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid amount paid (must be a number)!\n"; 
            }
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(bookingDialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
        
    public void setRoomData() {
        checkInField.setText(DateUtil.format(foundRoom.checkInField.getValue()));
        checkOutField.setText(DateUtil.format(foundRoom.checkOutField.getValue()));
        
        earlyCheckInBox.setSelected(foundRoom.getSearchEarlyCheckIn());
        lateCheckOutBox.setSelected(foundRoom.getSearchLateCheckOut());
        
        // TO-DO dsiplay selectedRoomTable.
        selectedRoomData = foundRoom.getSelectedRoomData();
        selectedRoomTable.setItems(selectedRoomData);
    }
    
    /**
     * Is called by FindRoomDialogController to give a reference back to itself
     * 
     * @param foundRoom 
     */
    public void setFoundRoom(FindRoomDialogController foundRoom) {
        this.foundRoom = foundRoom;
        setRoomData();
    }
    
    /**
     * Sets the stage of this dialog.
     * 
     * @param bookingDialogStage
     */
    public void setBookingDialogStage(Stage bookingDialogStage) {
        this.bookingDialogStage = bookingDialogStage;
    }
}
