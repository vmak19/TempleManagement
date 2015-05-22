/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.MainApp;
import assignment.database.RoomQueries;
import assignment.model.AvailableRoom;
import assignment.model.Booking;
import assignment.model.Room;
import assignment.model.RoomInfo;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 *
 * @author z3470029
 */
public class FindAvailableRoomDialogController implements Initializable {
    
    @FXML private TableView availableRoomTable;
    @FXML private TableColumn<RoomInfo, String> availableRoomTypeColumn;
    @FXML private TableColumn<RoomInfo, Double> availableCostColumn;    
    
    @FXML public DatePicker checkInField;
    @FXML public DatePicker checkOutField;
    
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
    @FXML private Button backButton;
    
    private ObservableList<RoomInfo> availableRoomData = FXCollections.observableArrayList();
    private ObservableList<RoomInfo> selectedRoomData = FXCollections.observableArrayList();
    
    private RoomQueries availableRoomQueries = new RoomQueries();
    private Stage bookingAvailableDialogStage;
    private boolean confirmClicked = false;
    private final LocalDate today = LocalDate.now();
    MainApp mainApp;
    private Booking booking;
    
    public FindAvailableRoomDialogController() {}
    
    public ObservableList<RoomInfo> getSelectedRoomData() {
        return selectedRoomData;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setDatePickerFormat();
        setDisableDatePicker();
        
        // Clear the both tables whenever the checkInDate or checkOutDate is modified
        checkInField.focusedProperty().addListener(
                (observable, oldValue, newValue) -> {
                    // Keep check-out date after check-in date
                    if (checkInField.getValue() != null && 
                            (checkOutField.getValue() == null || 
                            !checkOutField.getValue().isAfter(checkInField.getValue()))) {
                        checkOutField.setValue(checkInField.getValue().plusDays(1));
                    }
                });
    }
    
    /**
     * Returns true if the user clicked OK, false otherwise.
     */
    public boolean isConfirmClicked() {
        System.out.println("Get confirm from findRoom");
        return confirmClicked;
    }
    
    @FXML
    public void handleSearch() {
        if (isInputValidToSearch()) {
            availableRoomData.clear();
            Set<RoomInfo> roomData = availableRoomQueries.getAvailableRoomsByType(
                    checkInField.getValue(), checkOutField.getValue(), 
                    getSearchRoomType(), getSearchEarlyCheckIn(), 
                    getSearchLateCheckOut(), selectedRoomData);
            if (roomData.isEmpty()) {
                // Show a message if no room is available
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.initOwner(bookingAvailableDialogStage);
                alert.setTitle("Unavailable room");
                alert.setContentText("No room is available!");

                alert.showAndWait();
            } else {
                try {
                    availableRoomData.addAll(roomData);
                    availableRoomTable.setItems(availableRoomData);
                    
                    // Set values for available room table.
                    availableRoomTypeColumn.setCellValueFactory(
                            cellData -> cellData.getValue().roomTypeIDProperty());
                    availableCostColumn.setCellValueFactory(
                            cellData -> cellData.getValue().baseRateProperty().asObject());

                } catch (Exception e) {
                    System.out.println("Error! handleSearch()!");
                }
            }
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    public void closeDialog() {
        try{
            System.out.println(bookingAvailableDialogStage);
        
        bookingAvailableDialogStage.close();
        System.out.println("2");
    }
    catch (Exception e){
        System.out.println("3");
    }
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
            errorMessage += "No room type is selected!\n"; 
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(bookingAvailableDialogStage);
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
        
        if (availableRoomTable.getSelectionModel().getSelectedItem() == null) {
            errorMessage = "No available room is selected!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(bookingAvailableDialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    
    /**
     * Is called by the room queries.
     * 
     * @return List of selected room types
     */
    public List<String> getSearchRoomType() {
        List<String> searchRoomType = new ArrayList<>();
        if (singleBox.isSelected()) {
            searchRoomType.add("Single");
        }
        if (doubleBox.isSelected()) {
           searchRoomType.add("Double");
        }
        if (queenBox.isSelected()) {
            searchRoomType.add("Queen");
        }
        if (kingBox.isSelected()) {
            searchRoomType.add("King");
        }
        if (twinBox.isSelected()) {
            searchRoomType.add("Twin");
        }
        if (doubleDoubleBox.isSelected()) {
            searchRoomType.add("Double-double");
        }
        if (suiteBox.isSelected()) {
            searchRoomType.add("Suite");
        }
        return searchRoomType;
    }
    
    /**
     * Is called by the roomQueries and editBookingDialog to check if the user 
     * choose early check-in.
     */
    public boolean getSearchEarlyCheckIn() {
        return earlyCheckInBox.isSelected();
    }
    
    /**
     * Is called by the roomQueries and editBookingDialog to check if the user 
     * choose late check-out.
     */
    public boolean getSearchLateCheckOut() {
        return lateCheckOutBox.isSelected();
    }
    
    /**
     * Format DatePicker as yyyy-MM-dd.
     */
    public void setDatePickerFormat() {
        StringConverter converter = new StringConverter<LocalDate>() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        checkInField.setConverter(converter);
        checkOutField.setConverter(converter);
    }
    
    /**
     * Disable all dates that are before today for check-in date 
     * and before check-in date for check-out date.
     */
    public void setDisableDatePicker() {
        checkInField.setValue(LocalDate.now());
        final Callback<DatePicker, DateCell> checkInDayCellFactory = 
            new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            
                            if (item.isBefore(today)) {
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                            }   
                        }
                    };
                }
            };
        checkInField.setDayCellFactory(checkInDayCellFactory);
        
        final Callback<DatePicker, DateCell> checkOutDayCellFactory = 
            new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                           
                            if (checkInField.getValue() != null) {
                                if (item.isBefore(checkInField.getValue().plusDays(1))) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            } else {
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                            }
                        }
                    };
                }
            };
        checkOutField.setDayCellFactory(checkOutDayCellFactory);
    }
    
    /**
     * Sets the stage of this dialog.
     * 
     * @param bookingAvailableDialogStage
     */
    public void setBookingDialogStage(Stage bookingAvailableDialogStage) {
        System.out.println(bookingAvailableDialogStage);
        this.bookingAvailableDialogStage = bookingAvailableDialogStage;
    }        
}