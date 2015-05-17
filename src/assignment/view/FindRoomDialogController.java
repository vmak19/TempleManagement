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
import java.util.ResourceBundle;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 *
 * @author z3470029
 */
public class FindRoomDialogController implements Initializable {
    
    @FXML private TableView availableRoomTable;
    @FXML private TableColumn<RoomInfo, String> availableRoomTypeColumn;
    @FXML private TableColumn<RoomInfo, Double> availableCostColumn;
    
    @FXML private TableView selectedRoomTable;
    @FXML private TableColumn<RoomInfo, String> selectedRoomTypeColumn;
    @FXML private TableColumn<RoomInfo, Double> selectedCostColumn;
    @FXML private TableColumn<Booking, Integer> selectedNumPeopleColumn;
    
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
    @FXML private Button addButton;
    @FXML private Button removeButton;
    @FXML private Button bookButton;
    @FXML private Button cancelButton;
    
    private ObservableList<RoomInfo> availableRoomData = FXCollections.observableArrayList();
    private ObservableList<RoomInfo> selectedRoomData = FXCollections.observableArrayList();
    
    private RoomQueries availableRoomQueries = new RoomQueries();
    private Stage bookingDialogStage;
    private boolean confirmClicked = false;
    private final LocalDate today = LocalDate.now();
    MainApp mainApp;
    private Booking booking;
    
    public FindRoomDialogController() {}
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setDatePickerFormat();
        setDisableDatePicker();
        
        // Clear the both tables whenever the checkInDate or checkOutDate is modified
        checkInField.focusedProperty().addListener(
                (observable, oldValue, newValue) -> {
                    // Clear selected room list and update the table
                    selectedRoomData.clear();
                    selectedRoomTable.setItems(selectedRoomData);
                    
                    // Keep check-out date after check-in date
                    if (checkOutField.getValue() == null || 
                            !checkOutField.getValue().isAfter(checkInField.getValue())) {
                        checkOutField.setValue(checkInField.getValue().plusDays(1));
                    }
                });
        
        checkOutField.focusedProperty().addListener(
                (observable, oldValue, newValue) -> {
                    // Clear selected room list and update the table
                    selectedRoomData.clear();
                    selectedRoomTable.setItems(selectedRoomData);});
        
    }
    
    /**
     * Returns true if the user clicked OK, false otherwise.
     */
    public boolean isConfirmClicked() {
        return confirmClicked;
    }
    
    @FXML
    public void handleSearch() {
        if (isInputValidToSearch()) {
            availableRoomQueries.setFindRoomDialogController(this);
            if (availableRoomQueries.getAvailableRooms().isEmpty()) {
                // Show a message if no room is available
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.initOwner(bookingDialogStage);
                alert.setTitle("Unavailable room");
                alert.setContentText("No room is available!");

                alert.showAndWait();
            } else {
                try {
                    availableRoomData.clear();
                    availableRoomData.addAll(availableRoomQueries.getAvailableRooms()); // TO-DO: Create this method in the query
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
    
    @FXML
    public void handleAdd() {
        if (isInputValidToAdd()) {
            int selectedIndex = availableRoomTable.getSelectionModel().getSelectedIndex();
            
            // Copy data from one table to another
            RoomInfo selectedRoom = availableRoomData.get(selectedIndex);
            selectedRoomData.add(selectedRoom);
            selectedRoomTable.setItems(selectedRoomData);
            availableRoomData.remove(selectedIndex);
            
            // Set values for selected room table.
            selectedRoomTypeColumn.setCellValueFactory(
                    cellData -> cellData.getValue().roomTypeIDProperty());
            selectedCostColumn.setCellValueFactory(
                    cellData -> cellData.getValue().baseRateProperty().asObject());
        }
    }
    
    @FXML
    public void handleRemove() {
        if (isInputValidToRemove()) {
            int selectedIndex = selectedRoomTable.getSelectionModel().getSelectedIndex();
            
            // Copy data from one table to another
            RoomInfo selectedRoom = selectedRoomData.get(selectedIndex);
            availableRoomData.add(selectedRoom);
            availableRoomTable.setItems(availableRoomData);
            selectedRoomData.remove(selectedIndex);
        }
    }
    
    /**
     * Called when the user clicks book to pass all values to EditBookingController.
     */
    @FXML
    public void handleBook (ActionEvent event) throws IOException {
        
        if (isInputValidToBook()) {
            
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/EditBookingDialog.fxml"));
                AnchorPane editBookingDialog = (AnchorPane) loader.load();
                EditBookingDialogController controller = loader.getController();
                controller.setFoundRoom(this);
                controller.setBookingDialogStage(bookingDialogStage);
                Scene scene = new Scene(editBookingDialog);
                bookingDialogStage.setScene(scene);
                bookingDialogStage.show();
            } catch (IOException ex) {
                System.out.println("ERROR! handleBook()!");
                ex.printStackTrace();
            }
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
            alert.initOwner(bookingDialogStage);
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
            alert.initOwner(bookingDialogStage);
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
        
        if (selectedRoomTable.getSelectionModel().getSelectedItem() == null) {
            errorMessage = "No room is selected!\n";
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
    
    /**
     * Validates the user input in the selected room table view.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValidToBook() {
        String errorMessage = "";
        
        if (selectedRoomData.isEmpty()) {
            errorMessage += "No room is added!\n";
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
    
    /**
     * Is called by the room queries.
     * 
     * @return String of selected room types
     */
    public String getSearchRoomType() {
        String searchRoomType = "";
        String separator =  ", ";
        if (singleBox.isSelected()) {
            searchRoomType += "Single";
        }
        if (doubleBox.isSelected()) {
            if (searchRoomType.length() != 0) {
                searchRoomType += separator;
            }
            searchRoomType += "Double";
        }
        if (queenBox.isSelected()) {
            if (searchRoomType.length() != 0) {
                searchRoomType += separator;
            }
            searchRoomType += "Queen";
        }
        if (kingBox.isSelected()) {
            if (searchRoomType.length() != 0) {
                searchRoomType += separator;
            }
            searchRoomType += "King";
        }
        if (twinBox.isSelected()) {
            if (searchRoomType.length() != 0) {
                searchRoomType += separator;
            }
            searchRoomType += "Twin";
        }
        if (doubleDoubleBox.isSelected()) {
            if (searchRoomType.length() != 0) {
                searchRoomType += separator;
            }
            searchRoomType += "Double-double";
        }
        if (suiteBox.isSelected()) {
            if (searchRoomType.length() != 0) {
                searchRoomType += separator;
            }
            searchRoomType += "Suite";
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
                            
                            if (item.isBefore(checkInField.getValue().plusDays(1))) {
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
     * @param bookingDialogStage
     */
    public void setBookingDialogStage(Stage bookingDialogStage) {
        this.bookingDialogStage = bookingDialogStage;
    }
    
    /**
     * Is called by tab booking controller.
     */
    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}