package assignment.view;

//<editor-fold defaultstate="collapsed" desc="Imports">
import assignment.MainApp;
import assignment.database.LogQueries;
import assignment.model.Log;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
//</editor-fold>

public class TabLogController implements Initializable {

    @FXML
    AnchorPane logAnchorPane;
    @FXML
    TableView<Log> logTable;
    @FXML
    private TableColumn<Log, Integer> logIDColumn;
    @FXML
    private TableColumn<Log, Integer> userIDColumn;
    @FXML
    private TableColumn<Log, String> activityColumn;

    @FXML
    private Label logIDLabel;
    @FXML
    private Label userIDLabel;
    @FXML
    private Label fNameLabel;
    @FXML
    private Label lNameLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label activityLabel;
    @FXML
    private TextField logFilterField;

    HotelOverviewController hotelOverview;
    MainApp mainApp;
    private ObservableList<Log> logData = FXCollections.observableArrayList();
    private ObservableList<Log> sessionData = FXCollections.observableArrayList();
    private LogQueries logQueries = new LogQueries();

    public ObservableList<Log> getLogData() {
        return logData;
    }

    public ObservableList<Log> getSessionData() {
        return sessionData;
    }

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public TabLogController() {
    }

    private void showLogDetails(Log log) {
        if (log != null) {
            logAnchorPane.setOpacity(100);
            // Fill the labels with info from the log object.
            logIDLabel.setText(Integer.toString(log.getLogID()));
            userIDLabel.setText(Integer.toString(log.getUserID()));
            fNameLabel.setText(log.getEmpFirstName());
            lNameLabel.setText(log.getEmpLastName());
            activityLabel.setText(log.getItemModified());
            dateLabel.setText(log.getDateMod());
        } else {
            // Log is null, remove all the information.
            logIDLabel.setText("");
            userIDLabel.setText("");
            fNameLabel.setText("");
            lNameLabel.setText("");
            activityLabel.setText("");
            dateLabel.setText("");
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<Log> filteredData = new FilteredList<>(logData, p -> true);

            // Set the filter Predicate whenever the filter changes.
            logFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(log -> {
                    // If filter text is empty, display all log.
                    if (newValue == null || newValue.isEmpty()) {
                        logTable.setItems(logData);
                        return true;
                    }

                    // Compare log ID, user ID and activity of every log with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (Integer.toString(log.getLogID()).contains(lowerCaseFilter)) {
                        return true; // Filter matches log ID.
                    } else if (Integer.toString(log.getUserID()).toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches user ID.
                    } else if (log.getItemModified().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches activity.
                    }
                    return false; // Does not match.
                });
            });

            logFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
                // Wrap the FilteredList in a SortedList. 
                SortedList<Log> sortedData = new SortedList<>(filteredData);

                // Bind the SortedList comparator to the TableView comparator.
                sortedData.comparatorProperty().bind(logTable.comparatorProperty());

                // Add sorted (and filtered) data to the table.
                logTable.setItems(sortedData);
            });

            logData.addAll(logQueries.getLogs());
            logTable.setItems(logData);

            // Initialize the log table with the three columns.
            logIDColumn.setCellValueFactory(cellData -> cellData.getValue().logIDProperty().asObject());
            userIDColumn.setCellValueFactory(cellData -> cellData.getValue().userIDProperty().asObject());
            activityColumn.setCellValueFactory(cellData -> cellData.getValue().itemModifiedProperty());

            //Set items to be shown descending, newest records at top
            logIDColumn.setSortType(TableColumn.SortType.DESCENDING);
            logTable.getSortOrder().add(logIDColumn);

            // Hide whole detail pane
            logAnchorPane.setOpacity(0);

            logTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showLogDetails(newValue));
        } catch (Exception e) {
            System.out.println("TabLog initilize error!");
        }
    }

    /**
     * To refresh the table.
     */
    public void refreshTable() {
        logData.clear();
        logData.addAll(logQueries.getLogs());
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
