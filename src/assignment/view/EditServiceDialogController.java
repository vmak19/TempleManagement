/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.database.BookingQueries;
import assignment.database.ServiceQueries;
import assignment.model.BookingInfo;
import assignment.model.Provides;
import assignment.model.Service;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author SONY
 */
public class EditServiceDialogController implements Initializable {
    @FXML private TableView availableGuestTable;
    @FXML private TableColumn<BookingInfo, String> custFirstNameColumn;
    @FXML private TableColumn<BookingInfo, String> custLastNameColumn;
    @FXML private TableColumn<BookingInfo, LocalDate> checkInColumn;
    @FXML private TableColumn<BookingInfo, LocalDate> checkOutColumn;
    
    @FXML private TextField roomIDField;
    @FXML private ComboBox serviceBox;
    @FXML private Label createdDateLabel;
    @FXML private RadioButton currentGuestButton;
    @FXML private RadioButton futureGuestButton;
    @FXML private Button searchButton;
    @FXML private Button okButton;
    @FXML private Button cancelButton;
    
    private LocalDate date;
    private boolean okClicked = false;
    private Stage editProvideDialogStage;
    private Provides service;
    private ServiceQueries serviceQueries = new ServiceQueries();
    private BookingQueries bookingQueries = new BookingQueries();
    private ObservableList<Service> serviceData = FXCollections.observableArrayList();
    private ObservableList<BookingInfo> availableGuestData = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        date = LocalDate.now();
        createdDateLabel.setText(date.toString());
        
        serviceBox.getItems().addAll(serviceQueries.getServiceList());
    }
    
    

    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return okClicked
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    public void handleSearch() {
        if (isInputValidToSearch()) {
            if (bookingQueries.getBookingsByRoom(
                    Integer.parseInt(roomIDField.getText())).isEmpty()) {
                // Show a message if no booking is found
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(editProvideDialogStage);
                alert.setTitle("No booking");
                alert.setContentText("No booking is found!");

                alert.showAndWait();
            } else {
                try {
                    availableGuestData.clear();
                    availableGuestData.addAll(bookingQueries.getBookingsByRoom(
                            Integer.parseInt(roomIDField.getText())));
                    availableGuestTable.setItems(availableGuestData);
                    
                    // Set values for available guest booking table.
                    custFirstNameColumn.setCellValueFactory(
                            cellData -> cellData.getValue().custFirstNameProperty());
                    custLastNameColumn.setCellValueFactory(
                            cellData -> cellData.getValue().custLastNameProperty());
                    checkInColumn.setCellValueFactory(
                            cellData -> cellData.getValue().checkInProperty());
                    checkOutColumn.setCellValueFactory(
                            cellData -> cellData.getValue().checkOutProperty());

                } catch (Exception e) {
                    System.out.println("Error! handleSearch()!");
                }
            }
        }
    }
    
    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValidToSearch()) {
            BookingInfo selectedBooking = (BookingInfo) 
                    availableGuestTable.getSelectionModel().getSelectedItem();
            Service selectedService = (Service) 
                    serviceBox.getSelectionModel().getSelectedItem();
            
            
            service.setRefCode(selectedBooking.getRefCode());
            service.setRoomID(selectedBooking.getRoomID());
            service.setServiceID(selectedService.getServiceID());
            service.setCreatedDate(date);

            okClicked = true;
            editProvideDialogStage.close();
        }
    }
    
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValidToSearch() {
        String errorMessage = "";

        if (roomIDField.getText() == null || roomIDField.getText().length() == 0) {
            errorMessage += "No valid room number!\n"; 
        } 
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(editProvideDialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    public void handleCancel() {
        editProvideDialogStage.close();
    }
    
    /**
     * Sets the stage of this dialog.
     * 
     * @param editProvideDialogStage
     */
    public void setEditProvideDialogStage(Stage editProvideDialogStage) {
        this.editProvideDialogStage = editProvideDialogStage;
    }
    
    /**
     * Sets the service to be edited in the dialog.
     * 
     * @param service
     */
    public void setProvide(Provides provide) {
        this.service = service;
    }
}
