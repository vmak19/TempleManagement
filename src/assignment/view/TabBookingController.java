package assignment.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import assignment.MainApp;
import assignment.model.Booking;

public class TabBookingController extends HotelOverviewController {
    @FXML public TableView<Booking> bookingTable;
    @FXML private TableColumn<Booking, String> custFirstNameColumn;
    @FXML private TableColumn<Booking, String> custLastNameColumn;

    @FXML private Label refCodeLabel;
    @FXML private Label custFirstNameLabel;
    @FXML private Label custLastNameLabel;
    @FXML private Label numPeopleLabel;
    @FXML private Label roomNum;
    @FXML private Label createdDate;
    @FXML private Label numBreakfast;
    @FXML private Label checkIn;
    @FXML private Label checkOut;
    @FXML private Label amountPaid;
    @FXML private Label amountDue;

    // Reference to the main application.
    // private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public TabBookingController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        custFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().custFirstNameProperty());
        custLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().custLastNameProperty());
        
        // Add observable list data to the table
        bookingTable.setItems(mainApp.getBookingData());
    }
}