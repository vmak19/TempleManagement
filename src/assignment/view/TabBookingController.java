package assignment.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import assignment.model.Booking;
import assignment.util.DateUtil;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class TabBookingController extends HotelOverviewController implements Initializable {

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
}
