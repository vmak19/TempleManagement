package assignment.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import assignment.model.Booking;
import assignment.util.DateUtil;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class TabBookingController extends HotelOverviewController implements Initializable {

    @FXML TableView<Booking> bookingTable;
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
            // Initialize the person table with the two columns.
            custFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().custFirstNameProperty());
            custLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().custLastNameProperty());

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
     *
     * @param person the person or null
     */
    private void showBookingDetails(Booking booking) {
        if (booking != null) {
            // Fill the labels with info from the person object.
            custFirstNameLabel.setText(booking.getCustFirstName());
            custLastNameLabel.setText(booking.getCustLastName());
            numPeopleLabel.setText(Integer.toString(booking.getNumPeople()));
            roomNumLabel.setText(Integer.toString(booking.getRoomNum()));
            createdDateLabel.setText(DateUtil.format(booking.getCreatedDate()));
            numBreakfastLabel.setText(Integer.toString(booking.getNumBreakfast()));
            checkInLabel.setText(DateUtil.format(booking.getCheckIn()));
            checkOutLabel.setText(DateUtil.format(booking.getCheckOut()));
            amountPaidLabel.setText(Double.toString(booking.getAmountPaid()));
            
        // TODO: We need a way to convert the birthday into a String! And set early ch/in, late ch/out 
            // And Ref. Code
            // birthdayLabel.setText(...);
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
