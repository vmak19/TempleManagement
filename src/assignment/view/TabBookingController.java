package assignment.view;

import assignment.MainApp;
import assignment.database.BookingQueries;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import assignment.model.Booking;
import assignment.util.DateUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class TabBookingController implements Initializable {

    @FXML
    TableView<Booking> bookingTable;
    @FXML
    private TableColumn<Booking, Integer> refCodeColumn;
    @FXML
    private TableColumn<Booking, String> custFirstNameColumn;
    @FXML
    private TableColumn<Booking, String> custLastNameColumn;

    @FXML
    private Label refCodeLabel;
    @FXML
    private Label custFirstNameLabel;
    @FXML
    private Label custLastNameLabel;
    @FXML
    private Label numPeopleLabel;
    @FXML
    private Label roomNumLabel;
    @FXML
    private Label createdDateLabel;
    @FXML
    private Label numBreakfastLabel;
    @FXML
    private Label checkInLabel;
    @FXML
    private Label checkOutLabel;
    @FXML
    private Label earlyCheckInLabel;
    @FXML
    private Label lateCheckOutLabel;
    @FXML
    private Label amountPaidLabel;
    @FXML
    private Label amountDueLabel;

    @FXML
    private Button deleteButton;

    MainApp mainApp;


    private ObservableList<Booking> bookingData = FXCollections.observableArrayList();

    private BookingQueries bookingQueries;

    public ObservableList<Booking> getBookingData() {
        return bookingData;
    }

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public TabBookingController() {
    }

    public List<Booking> getBookingsFromFile() {
        List<Booking> bookings = new ArrayList<Booking>();
        try {

            // Open the file
            Scanner scanner = new Scanner(new File("resources/bookings.csv"));

            //for all lines in file
            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                String[] refCode = s.split(",");
                String[] fname = s.split(",");
                String[] lname = s.split(",");
                String[] numPeople = s.split(",");
                String[] roomNum = s.split(",");
                String[] createdDate = s.split(",");
                String[] numBreakfast = s.split(",");
                String[] checkIn = s.split(",");
                String[] checkOut = s.split(",");
                String[] amountPaid = s.split(",");
                String[] amountDue = s.split(",");
                String[] earlyCheckIn = s.split(",");
                String[] lateCheckOut = s.split(",");

                Booking newBooking = new Booking(
                        Integer.parseInt(refCode[0]),
                        fname[1],
                        lname[2],
                        Integer.parseInt(numPeople[3]),
                        Integer.parseInt(roomNum[4]),    
                        DateUtil.parse(createdDate[5]),
                        Integer.parseInt(numBreakfast[6]),
                        DateUtil.parse(checkIn[7]),
                        DateUtil.parse(checkOut[8]),
                        Boolean.parseBoolean(earlyCheckIn[9]),
                        Boolean.parseBoolean(lateCheckOut[10]),
                        Double.parseDouble(amountPaid[11]),
                        Double.parseDouble(amountDue[12]));
                bookings.add(newBooking);
            }

            //create room
            //add to list
            // Close the file
            scanner.close();

        } catch (FileNotFoundException ex) {
            System.out.println("getBookingsFromFile() Error!");
            ex.printStackTrace();
        }
        return bookings;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            bookingQueries = new BookingQueries();

            bookingData.addAll(getBookingsFromFile());
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
               // mainApp.getBookingQueries().deleteBooking(bookingTable.getSelectionModel().getSelectedItem());

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
     * clicks OK, the changes are saved into the provided booking object and
     * true is returned.
     *
     * @param booking the booking object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    /*public boolean showFindRoomDialog(Booking booking) {
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
     }*/
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
