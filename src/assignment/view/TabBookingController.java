package assignment.view;

import assignment.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import assignment.model.Booking;
import assignment.util.DateUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TabBookingController implements Initializable {

    @FXML TableView<Booking> bookingTable;
    @FXML private TableColumn<Booking, Integer> refCodeColumn;
    @FXML private TableColumn<Booking, String> custFirstNameColumn;
    @FXML private TableColumn<Booking, String> custLastNameColumn;

    @FXML private Label refCodeLabel;
    @FXML private Label custFirstNameLabel;
    @FXML private Label custLastNameLabel;
    @FXML private Label numPeopleLabel;
    @FXML private Label roomNumLabel;
    @FXML private Label createdDateLabel;
    @FXML private Label numBreakfastLabel;
    @FXML private Label checkInLabel;
    @FXML private Label checkOutLabel;
    @FXML private Label earlyCheckInLabel;
    @FXML private Label lateCheckOutLabel;
    @FXML private Label amountPaidLabel;
    @FXML private Label amountDueLabel;
    
    @FXML private Button deleteButton;
    
    MainApp mainApp;
    
    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public TabBookingController() {}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Initialize the booking table with the three columns.
            refCodeColumn.setCellValueFactory(cellData -> cellData.getValue().refCodeProperty().asObject());
            custFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().custFirstNameProperty());
            custLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().custLastNameProperty());
            
            // Clear booking details.
            showBookingDetails(null);
            
            // Listen for selection changes and show the booking details when changed.
            bookingTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showBookingDetails(newValue));
        } catch (Exception e) {
            System.out.println("Initilize error!");
        }
    }

    /**
     * Fills all text fields to show details about the person. If the specified
     * person is null, all text fields are cleared.
     */
    private void showBookingDetails(Booking booking) {
        if (booking != null) {
            // Fill the labels with info from the booking object.
            refCodeLabel.setText(Integer.toString(booking.getRefCode()));
            custFirstNameLabel.setText(booking.getCustFirstName());
            custLastNameLabel.setText(booking.getCustLastName());
            numPeopleLabel.setText(Integer.toString(booking.getNumPeople()));
            roomNumLabel.setText(Integer.toString(booking.getRoomNum()));
            createdDateLabel.setText(DateUtil.format(booking.getCreatedDate()));
            numBreakfastLabel.setText(Integer.toString(booking.getNumBreakfast()));
            checkInLabel.setText(DateUtil.format(booking.getCheckIn()));
            checkOutLabel.setText(DateUtil.format(booking.getCheckOut()));
            if (booking.getEarlyCheckIn()) {
                earlyCheckInLabel.setText("Early Check In");
            } else {
                earlyCheckInLabel.setText("");
            }
            if (booking.getLateCheckOut()) {
                lateCheckOutLabel.setText("Late Check Out");
            } else {
                lateCheckOutLabel.setText("");
            }
            amountPaidLabel.setText(Double.toString(booking.getAmountPaid()));
            amountDueLabel.setText(Double.toString(booking.getAmountDue()));
        } else {
            // Person is null, remove all the text.
            refCodeLabel.setText("");
            custFirstNameLabel.setText("");
            custLastNameLabel.setText("");
            numPeopleLabel.setText("");
            roomNumLabel.setText("");
            createdDateLabel.setText("");
            numBreakfastLabel.setText("");
            checkInLabel.setText("");
            checkOutLabel.setText("");
            earlyCheckInLabel.setText("");
            lateCheckOutLabel.setText("");
            amountPaidLabel.setText("");
            amountDueLabel.setText("");
        }
    }
    
    // Called when the user clicks on the delete button.
    @FXML
    private void handleDeleteBooking() {
        try {
            int selectedIndex = bookingTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                // Delete record in the database
                mainApp.getBookingQueries().deleteBooking(bookingTable.getSelectionModel().getSelectedItem());
                
                // Delete record on the table
                bookingTable.getItems().remove(selectedIndex);
            } else {
                // Nothing selected.
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("No Selection");
                alert.setHeaderText("No Person Selected");
                alert.setContentText("Please select a person in the table.");

                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("Error! handleDeleteBooking()!");
        }
    }
    
    /**
     * Opens a dialog to edit details for the specified booking. If the user
     * clicks OK, the changes are saved into the provided booking object and true
     * is returned.
     *
     * @param booking the booking object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showFindRoomDialog(Booking booking) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FindRoomDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage findRoomDialogStage = new Stage();
            findRoomDialogStage.setTitle("Find Room");
            findRoomDialogStage.initModality(Modality.WINDOW_MODAL);
            //findRoomDialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            findRoomDialogStage.setScene(scene);

            // Set the person into the controller.
            FindRoomDialogController controller = loader.getController();
            controller.setFindRoomDialogStage(findRoomDialogStage);
            controller.setBooking(booking); // TO-DO: Add setBooking() in EditBookingDialogController

            // Show the dialog and wait until the user closes it
            findRoomDialogStage.showAndWait();

            return controller.isBookClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}