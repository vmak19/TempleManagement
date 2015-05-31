package assignment.view;

import assignment.MainApp;
import assignment.database.AssignmentQueries;
import assignment.database.BillingQueries;
import assignment.database.BookingQueries;
import assignment.database.LogQueries;
import assignment.model.Assignment;
import assignment.model.Billing;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import assignment.model.Booking;
import assignment.model.BookingInfo;
import assignment.model.Log;
import assignment.model.RoomInfo;
import assignment.util.DateUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TabBookingController implements Initializable {

    @FXML TableView<BookingInfo> bookingTable;
    @FXML private TableColumn<BookingInfo, Integer> refCodeColumn;
    @FXML private TableColumn<BookingInfo, String> custFirstNameColumn;
    @FXML private TableColumn<BookingInfo, String> custLastNameColumn;

    @FXML private Label refCodeLabel;
    @FXML private Label custFirstNameLabel;
    @FXML private Label custLastNameLabel;
    @FXML private Label numPeopleLabel;
    @FXML private Label roomIDLabel;
    @FXML private Label createdDateLabel;
    @FXML private Label numBreakfastLabel;
    @FXML private Label checkInLabel;
    @FXML private Label checkOutLabel;
    @FXML private Label amountPaidLabel;
    @FXML private Label amountDueLabel;
    
    @FXML private CheckBox earlyChInBox;
    @FXML private CheckBox lateChOutBox;

    @FXML private TextField bookingFilterField;

    HotelOverviewController hotelOverview;
    MainApp mainApp;
    private Stage primaryStage;

    private ObservableList<BookingInfo> bookingData = FXCollections.observableArrayList();

    private BookingQueries bookingQueries = new BookingQueries();
    private LogQueries logQueries = new LogQueries();
    private AssignmentQueries assignmentQueries = new AssignmentQueries();

    public ObservableList<BookingInfo> getBookingData() {
        return bookingData;
    }

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public TabBookingController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<BookingInfo> filteredData = new FilteredList<>(bookingData, p -> true);

            // Set the filter Predicate whenever the filter changes.
            bookingFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(booking -> {
                    // If filter text is empty, display all bookings.
                    if (newValue == null || newValue.isEmpty()) {
                        bookingTable.setItems(bookingData);
                        return true;
                    }

                    // Compare ref. code, first name and last name of every booking with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (Integer.toString(booking.getRefCode()).contains(lowerCaseFilter)) {
                        return true; // Filter matches ref. code.
                    } else if (booking.getCustFirstName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    } else if (booking.getCustLastName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    }
                    return false; // Does not match.
                });
            });

            bookingFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
                // Wrap the FilteredList in a SortedList. 
                SortedList<BookingInfo> sortedData = new SortedList<>(filteredData);

                // Bind the SortedList comparator to the TableView comparator.
                sortedData.comparatorProperty().bind(bookingTable.comparatorProperty());

                // Add sorted (and filtered) data to the table.
                bookingTable.setItems(sortedData);
            });

            bookingData.addAll(bookingQueries.getBookings());
            bookingTable.setItems(bookingData);

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
            System.out.println("Booking Initilize error!");
        }
    }

    /**
     * Fills all text fields to show details about the booking. If the specified
     * booking is null, all text fields are cleared.
     */
    private void showBookingDetails(BookingInfo booking) {
        if (booking != null) {
            // Fill the labels with info from the booking object.
            refCodeLabel.setText(Integer.toString(booking.getRefCode()));
            custFirstNameLabel.setText(booking.getCustFirstName());
            custLastNameLabel.setText(booking.getCustLastName());
            int numPeople = 0;
            for (int i : booking.getNumPeopleList()) {
                numPeople += i;
            }
            numPeopleLabel.setText(Integer.toString(numPeople));
            String roomID = "";
            for (int i : booking.getRoomIDList()) {
                roomID += Integer.toString(i) + " ";
            }
            roomIDLabel.setText(roomID);
            createdDateLabel.setText(DateUtil.format(booking.getCreatedDate()));
            numBreakfastLabel.setText(Integer.toString(booking.getNumBreakfast()));
            checkInLabel.setText(DateUtil.format(booking.getCheckIn()));
            checkOutLabel.setText(DateUtil.format(booking.getCheckOut()));
            earlyChInBox.setVisible(true);
            lateChOutBox.setVisible(true);
            if (booking.getEarlyCheckIn()) {
                earlyChInBox.setSelected(true);
            } else {
                earlyChInBox.setSelected(false);
            }
            if (booking.getLateCheckOut()) {
                lateChOutBox.setSelected(true);
            } else {
                lateChOutBox.setSelected(false);
            }
            amountPaidLabel.setText(Double.toString(booking.getAmountPaid()));
            amountDueLabel.setText(Double.toString(booking.getAmountDue()));
        } else {
            // Booking is null, remove all the text.
            refCodeLabel.setText("");
            custFirstNameLabel.setText("");
            custLastNameLabel.setText("");
            numPeopleLabel.setText("");
            roomIDLabel.setText("");
            createdDateLabel.setText("");
            numBreakfastLabel.setText("");
            checkInLabel.setText("");
            checkOutLabel.setText("");
            earlyChInBox.setVisible(false);
            lateChOutBox.setVisible(false);
            amountPaidLabel.setText("");
            amountDueLabel.setText("");
        }
    }

    @FXML
    public void handleGenPdf() {
        BookingInfo selectedBooking = bookingTable.getSelectionModel().getSelectedItem();
        if (selectedBooking != null) {
            PdfReport pdfReport = new PdfReport();
            pdfReport.main(selectedBooking);
            // Generate log record
            Log log = new Log("Generated PDF for Booking Ref. Code: " + selectedBooking.getRefCode());
            logQueries.insertLog(log, hotelOverview.getUserID());
            hotelOverview.refreshLogTable();

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Booking Selected");
            alert.setContentText("Please select a booking in the table.");

            alert.showAndWait();
        }
    }

    // Called when the user clicks on the delete button.
    @FXML
    private void handleDeleteBooking() {
        try {
            BookingInfo selectedBooking = bookingTable.getSelectionModel().getSelectedItem();
            int selectedIndex = bookingTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                // Delete record in the database
                bookingQueries.deleteBooking(bookingTable.getSelectionModel().getSelectedItem());

                // Delete record on the table
                bookingTable.getItems().remove(selectedIndex);

                // Generate new log record
                Log log = new Log("Deleted Booking for Ref. Code: " + selectedBooking.getRefCode());
                logQueries.insertLog(log, hotelOverview.getUserID());
                hotelOverview.refreshLogTable();
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
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewBooking() {
        Booking tempBooking = new Booking();
        ObservableList<RoomInfo> tempRooms = FXCollections.observableArrayList();
        boolean confirmClicked = showFindRoomDialog(tempBooking, tempRooms);

        if (confirmClicked) {
            bookingQueries.insertBooking(tempBooking);
            for (RoomInfo room : tempRooms) {
                assignmentQueries.insertAssignment(new Assignment(
                        bookingQueries.getLatestRefCode(), room.getRoomID(), room.getCapacity()));
            }
            refreshTable();
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditBooking() {
        BookingInfo selectedBooking = bookingTable.getSelectionModel().getSelectedItem();
        if (selectedBooking != null) {
            boolean okClicked = showEditBookingDialog(selectedBooking);
            if (okClicked) {
                showBookingDetails(selectedBooking);
            }

            // Generate new log record
            Log log = new Log("Added New Booking for Ref. Code: " + bookingQueries.getLatestRefCode());
            logQueries.insertLog(log, hotelOverview.getUserID());
            hotelOverview.refreshLogTable();
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No booking Selected");
            alert.setContentText("Please select a booking in the table.");

            alert.showAndWait();
        }
    }

    /**
     * Opens a dialog to edit details for the specified booking. If the user
     * clicks OK, the changes are saved into the provided booking object and
     * true is returned.
     *
     * @param booking the booking object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showFindRoomDialog(Booking booking, ObservableList<RoomInfo> rooms) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FindRoomDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage bookingDialogStage = new Stage();
            bookingDialogStage.setTitle("Find Room");
            bookingDialogStage.initModality(Modality.WINDOW_MODAL);
            bookingDialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            bookingDialogStage.setScene(scene);

            // Set the booking into the controller.
            FindRoomDialogController controller = loader.getController();
            controller.setBookingDialogStage(bookingDialogStage);
            controller.setBooking(booking, rooms);

            // Show the dialog and wait until the user closes it
            bookingDialogStage.showAndWait();
            return controller.isConfirmClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Opens a dialog to edit details for the specified booking. If the user
     * clicks OK, the changes are saved into the provided booking object and
     * true is returned.
     *
     * @param booking the booking object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showEditBookingDialog(BookingInfo booking) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditBookingDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage bookingDialogStage = new Stage();
            bookingDialogStage.setTitle("Edit Booking");
            bookingDialogStage.initModality(Modality.WINDOW_MODAL);
            bookingDialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            bookingDialogStage.setScene(scene);

            // Set the booking into the controller.
            EditBookingDialogController controller = loader.getController();
            controller.setBookingDialogStage(bookingDialogStage);
            controller.setEditBooking(booking);

            // Show the dialog and wait until the user closes it
            bookingDialogStage.showAndWait();
            return controller.isConfirmClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void handlePayBilling() {
        List<Billing> billings = new ArrayList<Billing>();

        TabBookingController tabBookingController = new TabBookingController();
        int selectedIndex = -1;
        selectedIndex = bookingTable.getSelectionModel().getSelectedIndex();

        BookingInfo selectedBooking = bookingTable.getSelectionModel().getSelectedItem();
        if (selectedBooking != null) {
            int myRefCode = refCodeColumn.getCellData(selectedIndex);

            double myAmountPaid = Double.parseDouble(amountPaidLabel.getText());
            double myAmountDue = Double.parseDouble(amountDueLabel.getText());

            BillingQueries billingQueries = new BillingQueries();
            billings = billingQueries.getSpecificBilling(myRefCode);

            Billing billing = new Billing(myRefCode, myAmountPaid, myAmountDue);

            if (selectedIndex != -1) {
                boolean okClicked = showPayBillingDialog(billing);
                if (okClicked) {
                    // Generate new log record
                    Log log = new Log("Paid" + " amount for Ref. Code: " + selectedBooking.getRefCode());
                    logQueries.insertLog(log, hotelOverview.getUserID());
                    hotelOverview.refreshLogTable();

                    //Refresh booking table
                    refreshTable();
                }
            }
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Booking Selected");
            alert.setContentText("Please select a booking in the table.");

            alert.showAndWait();

        }
    }

    public boolean showPayBillingDialog(Billing selectedBooking) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditBillingDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage billingDialogStage = new Stage();
            billingDialogStage.setTitle("Edit Billing");
            billingDialogStage.initModality(Modality.WINDOW_MODAL);
            billingDialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            billingDialogStage.setScene(scene);

            // Set the person into the controller.
            EditBillingDialogController controller = loader.getController();
            controller.setBillingDialogStage(billingDialogStage);
            controller.setBilling(selectedBooking);

            // Show the dialog and wait until the user closes it
            billingDialogStage.showAndWait();

            return controller.isConfirmClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showBillingDetails(BookingInfo selectedBooking) {
        if (selectedBooking != null) {
            // Fill the labels with info from the billing object.
            refCodeLabel.setText(Integer.toString(selectedBooking.getRefCode()));
            amountPaidLabel.setText(Double.toString(selectedBooking.getAmountPaid()));
            amountDueLabel.setText(Double.toString(selectedBooking.getAmountDue()));
        } else {
            // Billing is null, remove all the information.
            refCodeLabel.setText("");
            amountPaidLabel.setText("");
            amountDueLabel.setText("");
        }
    }

    /**
     * To refresh the table.
     */
    public void refreshTable() {
        bookingData.clear();
        bookingData.addAll(bookingQueries.getBookings());
    }

    /**
     * Is called by hotel overview controller to give a reference back to the
     * main application.
     */
    public void setMainApp(MainApp mainApp, HotelOverviewController hotelOverview) {
        this.mainApp = mainApp;
        this.hotelOverview = hotelOverview;
    }
}
