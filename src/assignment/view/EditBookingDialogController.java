/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.MainApp;
import assignment.database.AssignmentQueries;
import assignment.model.Assignment;
import assignment.model.Booking;
import assignment.model.RoomInfo;
import assignment.util.DateUtil;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author SONY
 */
public class EditBookingDialogController implements Initializable {
    
    @FXML private TableView selectedRoomTable;
    @FXML private TableColumn<RoomInfo, String> selectedRoomTypeColumn;
    @FXML private TableColumn<RoomInfo, Double> selectedCostColumn;
    @FXML private TableColumn<Booking, Integer> selectedNumPeopleColumn;
    
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField numBreakfastField;
    @FXML private TextField checkInField;
    @FXML private TextField checkOutField;
    @FXML private TextField amountPaidField;
    @FXML private TextField amountDueField;
    
    @FXML private CheckBox earlyCheckInBox;
    @FXML private CheckBox lateCheckOutBox;
    
    @FXML private Button selectRoomButton;
    @FXML private Button confirmButton;
    @FXML private Button cancelButton;
    
    @FXML private Label createdDateLabel;
    
    private Booking booking;
    private Assignment assignment;
    private boolean confirmClicked = false;
    private FindRoomDialogController foundRoom;
    private Stage bookingDialogStage;
    private ObservableList<RoomInfo> selectedRoomData;
    private AssignmentQueries assignmentQueries = new AssignmentQueries();
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set values for selected room table.
        selectedRoomTypeColumn.setCellValueFactory(
                cellData -> cellData.getValue().roomTypeIDProperty());
        selectedCostColumn.setCellValueFactory(
                cellData -> cellData.getValue().baseRateProperty().asObject());
    }
    
    /**
     * Returns true if the user clicked confirm, false otherwise.
     * 
     * @return
     */
    public boolean isConfirmClicked() {
        return confirmClicked;
    }
    
    /**
     * Called when the user clicks select room.
     */
    @FXML
    public void handleSelectRoom(ActionEvent event) throws IOException {
        
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/FindRoomDialog.fxml"));
                AnchorPane findRoomDialog = (AnchorPane) loader.load();
                
                FindRoomDialogController controller = loader.getController();
                controller.setFoundRoomData(foundRoom);
                controller.setBookingDialogStage(bookingDialogStage);
                controller.setBooking(booking, selectedRoomData);
                Scene scene = new Scene(findRoomDialog);
                bookingDialogStage.setScene(scene);
                bookingDialogStage.show();
            } catch (IOException ex) {
                System.out.println("ERROR! handleSelectRoom()!");
                ex.printStackTrace();
            }
        }
    
    
    /**
     * Called when the user clicks confirm.
     */
    @FXML
    private void handleConfirm() {
        if (isInputValid()) {
            
            booking.setCustFirstName(firstNameField.getText());
            booking.setCustLastName(lastNameField.getText());
            booking.setCreatedDate(DateUtil.parse(createdDateLabel.getText()));
            booking.setNumBreakfast(Integer.parseInt(numBreakfastField.getText()));
            booking.setCheckIn(DateUtil.parse(checkInField.getText()));
            booking.setCheckOut(DateUtil.parse(checkOutField.getText()));
            booking.setEarlyCheckIn(earlyCheckInBox.isSelected());
            booking.setLateCheckOut(lateCheckOutBox.isSelected());
            booking.setAmountPaid(Double.parseDouble(amountPaidField.getText()));
            booking.setAmountDue(Double.parseDouble(amountDueField.getText()));
            
            foundRoom.setConfirmClicked(true);
            
            bookingDialogStage.close();
        }
    }
    
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    public void handleCancel() {
        bookingDialogStage.close();
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
                if (Integer.parseInt(numBreakfastField.getText()) < 0) {
                    errorMessage += "No valid number of breakfast days (must be a positive integer)!\n"; 
                }
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
                if (Double.parseDouble(amountPaidField.getText()) < 0) {
                    errorMessage += "No valid amount paid (must be a positive number)!\n"; 
                }
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
        
        selectedRoomData = foundRoom.getSelectedRoomData();
        selectedRoomTable.setItems(selectedRoomData);
        
        double amountDue = 0;
        for(RoomInfo room : selectedRoomData) {
            amountDue += room.getBaseRate();
        }
        amountDueField.setText(Double.toString(amountDue));
        amountPaidField.setText(Double.toString(amountDue * 0.5));
        
        if (createdDateLabel.getText().matches("Label")) {
            createdDateLabel.setText(LocalDate.now().toString());
        }
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
    
    /**
     * Is called by find room dialog controller.
     */
    public void setBooking(Booking booking, ObservableList<RoomInfo> rooms) {
        this.booking = booking;
        this.selectedRoomData = rooms;
    }
}
