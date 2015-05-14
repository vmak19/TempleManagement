/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.MainApp;
import assignment.model.Booking;
import assignment.util.DateUtil;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author z3470029
 */
public class FindRoomDialogController implements Initializable {
    
    @FXML private TableView availableRoomTable;
    @FXML private TableColumn<Room, String> availableRoomTypeColumn;
    @FXML private TableColumn<Room, Double> availableCostColumn;
    
    @FXML private TableView selectedRoomTable;
    @FXML private TableColumn<Room, String> selectedRoomTypeColumn;
    @FXML private TableColumn<Booking, Double> selectedCostColumn;
    @FXML private TableColumn<Booking, Integer> selectedNumPeopleColumn;
    
    @FXML private DatePicker checkInField;
    @FXML private DatePicker checkOutField;
    
    @FXML private CheckBox singleBox;
    @FXML private CheckBox doubleBox;
    @FXML private CheckBox queenBox;
    @FXML private CheckBox kingBox;
    @FXML private CheckBox twinBox;
    @FXML private CheckBox doubleDoubleBox;
    @FXML private CheckBox suiteBox;
    @FXML private CheckBox earlyCheckInBox;
    @FXML private CheckBox lateCheckOutBox;
    
    @FXML private Button searchButton;
    @FXML private Button addButton;
    @FXML private Button removeButton;
    @FXML private Button bookingButton;
    @FXML private Button cancelButton;
    
    private ObservableList<Room> availableRoomData = FXCollections.observableArrayList();
    private ObservableList<Booking> selectedRoomData = FXCollections.observableArrayList();
    
    private AvailableRoomQueries availableRoomQueries; // TO-DO: Create this query
    private Stage editBookingDialogStage;
    private Stage findRoomDialogStage;
    private Room room;
    private boolean bookClicked = false;
    private final LocalDate today = LocalDate.now();
    MainApp mainApp;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Clear the both tables whenever the checkInDate or checkOutDate is modified
        //checkInField.pressedProperty().addListener(
        checkInField.focusedProperty().addListener(
                (observable, oldValue, newValue) -> {
                    // Clear ObservableLists and update the tables
                    availableRoomData.clear();
                    selectedRoomData.clear();
                    availableRoomTable.setItems(availableRoomData);
                    selectedRoomTable.setItems(selectedRoomData);});
        
        checkOutField.focusedProperty().addListener(
                (observable, oldValue, newValue) -> {
                    // Clear ObservableLists and update the tables
                    availableRoomData.clear();
                    selectedRoomData.clear();
                    availableRoomTable.setItems(availableRoomData);
                    selectedRoomTable.setItems(selectedRoomData);});
    }
    
    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isBookClicked() {
        return bookClicked;
    }
    
    @FXML
    public void handleSearch() {
        if (isInputValidToSearch()) {
            if (availableRoomQueries.getAvailableRooms() == null) {
                // Show a message if no room is available
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.initOwner(findRoomDialogStage);
                alert.setTitle("Unavailble room");
                alert.setContentText("No room is available!");

                alert.showAndWait();
            } else {
                try {
                    availableRoomData.addAll(availableRoomQueries.getAvailableRooms()); // TO-DO: Create this method in the query

                    availableRoomTable.setItems(availableRoomData);

                    // Set values for available room table.
                    availableRoomTypeColumn.setCellValueFactory(cellData -> cellData.getValue().roomTypeProperty());
                    availableCostColumn.setCellValueFactory(cellData -> cellData.getValue().costProperty().asObject());

                } catch (Exception e) {
                    System.out.println("Error! handleSearch()!");
                }
            }
        }
    }
    
    @FXML
    public void handleAdd() {
        if (isInputValidToAdd()) {
            // TO-DO: Add selected room type to selectedRoomData and delete it in availableRoomData. (in Database)
            // TO-DO (Second thought): Delete selectedRoomData from observableList and add it into the other side.
        }
    }
    
    @FXML
    public void handleRemove() {
        if (isInputValidToRemove()) {
            // TO-DO: Opposite with handleAdd()
        }
    }
    
    /**
     * Called when the user clicks book to pass all values to EditBookingController.
     */
    @FXML
    public void handleBook() {
        if (isInputValidToBook()) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/EditBookingDialog.fxml"));
                AnchorPane editBookingDialog = (AnchorPane) loader.load();
                EditBookingDialogController controller = loader.getController();
                controller.setFoundRoom(this);
                
                bookClicked = true;
                findRoomDialogStage.close();
                // Show the scene containing the root layout.
                Scene scene = new Scene(editBookingDialog);
                editBookingDialogStage.setScene(scene);
                editBookingDialogStage.show();
            } catch (IOException ex) {
                System.out.println("ERROR! handleBook()!");
            }
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    public void handleCancel() {
        findRoomDialogStage.close();
    }

    /**
     * Validates the user input in the text fields and check boxes.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValidToSearch() {
        String errorMessage = "";

        if (checkInField.getValue() == null || checkInField.getValue().isBefore(today)) {
            errorMessage += "No valid check-in!\n"; 
        }
        if (checkOutField.getValue() == null || checkOutField.getValue().isBefore(checkInField.getValue())
                || checkOutField.getValue().isEqual(checkInField.getValue())) {
            errorMessage += "No valid check-out!\n"; 
        }
        if (!singleBox.isSelected() && !doubleBox.isSelected() && !queenBox.isSelected() && 
                !kingBox.isSelected() && !twinBox.isSelected() && 
                !doubleDoubleBox.isSelected() && !suiteBox.isSelected()) {
            errorMessage += "No valid room type!\n"; 
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(findRoomDialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    
    /**
     * Validates the user selection in the available room table view.
     * 
     * @return true if the selection is valid
     */
    private boolean isInputValidToAdd() {
        String errorMessage = "";
        
        if (availableRoomTable.getSelectionModel().selectedItemProperty() == null) {
            errorMessage = "No room is selected!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(findRoomDialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    
    /**
     * Validates the user selection in the selected room table view.
     * 
     * @return true if the selection is valid
     */
    private boolean isInputValidToRemove() {
        String errorMessage = "";
        
        if (selectedRoomTable.getSelectionModel().selectedItemProperty() == null) {
            errorMessage = "No room is selected!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(findRoomDialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    
    /**
     * Validates the user input in the selected room table view.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValidToBook() {
        String errorMessage = "";
        
        if (selectedRoomData == null) {
            errorMessage += "No room is added!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(findRoomDialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    
    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setFindRoomDialogStage(Stage findRoomDialogStage) {
        this.findRoomDialogStage = findRoomDialogStage;
    }
    
    /**
     * Is called by the hotel overview controller to give a reference back to main appplication.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
