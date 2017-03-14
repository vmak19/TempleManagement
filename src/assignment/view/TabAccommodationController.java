/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

//<editor-fold defaultstate="collapsed" desc="Imports">
import assignment.database.AccommodationQueries;
import assignment.model.Accommodation;
import assignment.MainApp;
import assignment.database.EmployeeQueries;
import assignment.database.LogQueries;
import assignment.database.LoginQueries;
import assignment.model.Employee;
import assignment.model.Log;
import assignment.util.DateUtil;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
//</editor-fold>

public class TabAccommodationController implements Initializable {

    // <editor-fold defaultstate="collapsed" desc=" Declaring FXML elements ">
    @FXML
    AnchorPane aAnchorPane;
    @FXML
    TableView<Accommodation> accommodationTable;
    @FXML
    private TableColumn<Accommodation, Integer> accommodationIDColumn;
    @FXML
    private TableColumn<Accommodation, Integer> contributorColumn;

    @FXML
    private Label aID;
    @FXML
    private Label memID;
    @FXML
    private Label checkInDate;
    @FXML
    private Label checkOutDate;
    @FXML
    private TextArea note;
    @FXML
    private Label room;
    @FXML
    private Label gender;
    @FXML
    private Label typeOfID;
    @FXML
    private Label IDNum;
    @FXML
    private Label reason;
    @FXML
    private Label emgContactPerson;
    @FXML
    private Label emgContactNum;
    @FXML
    private Label relationship;

    @FXML
    private Button importExcelBtn;
    @FXML
    private Button exportExcelBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button newBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private TextField accommodationFilterField;
    @FXML
    private FileChooser fileChooser;

// </editor-fold>
    private File file;
    File existDirectory;
    HotelOverviewController hotelOverview;
    MainApp mainApp;
    private ObservableList<Accommodation> accommodationData = FXCollections.observableArrayList();
    private ObservableList<Employee> employeeData = FXCollections.observableArrayList();
    public AccommodationQueries accommodationQueries = new AccommodationQueries();
    private EmployeeQueries employeeQueries = new EmployeeQueries();
    private LoginQueries loginQueries = new LoginQueries();
    private LogQueries logQueries = new LogQueries();
    private String sessionEmployeeName;
    private LoginScreenController loginScreenController = new LoginScreenController();

    //Grab 'notes' TextArea text and update changes to db
    private Accommodation selectedAccommodation = null;

    @FXML
    private void handleSaveNote() {
        //Clicking 'saveBtn' will loses focus on member in memberTable. This uses previous saved mem from first click
        if (accommodationTable.getSelectionModel().getSelectedItem() != null) {
            selectedAccommodation = accommodationTable.getSelectionModel().getSelectedItem();
        } else if (selectedAccommodation == null) { //No accommodation was ever selected from the beginning
            return;
        }
        //Checks if any changes has been made; if saved != textAreaBox ==> save it
        if (!(selectedAccommodation.getNote()).equals(note.getText())) {
            sessionEmployeeName = loginQueries.getSessionEmployeeName(hotelOverview.getUserID());
            selectedAccommodation.setNote(note.getText());
            accommodationQueries.updateAccommodation(selectedAccommodation);
            hotelOverview.refreshLogTable();
            //Generate new log record
            Log log = new Log("Edited note of memID " + selectedAccommodation.getMemID());
            logQueries.insertLog(log, hotelOverview.getUserID());
            hotelOverview.refreshLogTable();
            refreshTable(selectedAccommodation);
        }
        //For refocusing the selected accom. later (because accom. gets defocused when a btn is clicked)
        accommodationTable.getSelectionModel().select(accommodationTable.getSelectionModel().getFocusedIndex());
    }

    @FXML
    private void handleImportExcel() throws IOException {
        int importedFlag = -1;
        int importedFlag2 = 1;

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        //Single file selection
        file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if (file != null) {
            File existDirectory = file.getParentFile();
            fileChooser.setInitialDirectory(existDirectory);

            //Then set file as source for import & get excel headers
            List<String> blueRow = accommodationQueries.getBlueRows(file.getPath());

            //Open 'Link Database Field' dialoge & wait till user closes. Also return list of fields to import      
            ArrayList<String> results = showDbLinkAccommodationDialog(blueRow);
            List<Integer> accommodationIDList = new ArrayList<>(), newAccommodationIDList = new ArrayList<>(), newAccommodationList = new ArrayList<>();
            if (results != null && results.contains("Accommodation ID")) { //Will contain clashes
                //Grab memID column and sort into ‘accommodationIDList’ and 'newAccommodationList'
                accommodationIDList = accommodationQueries.getAccommodationID(file.getPath());
                newAccommodationIDList = accommodationQueries.getNewAccommodationIndexList();
                //************************** TO FIX: 
                //Run check against db for existing memID ==> further sort out non existing memID ==> remove and add to (newAccommodationList)                
                for (int i = 0; i < accommodationIDList.size(); i++) {
                    if (!contains(accommodationTable, accommodationIDList.get(i))) {
                        //Move to newAccommodationList
                        newAccommodationList.add(accommodationIDList.get(i));
                        accommodationIDList.remove(i);
                        i--;
                    }
                }
                //************************** TO FIX: 
                System.out.println("CONTENT of accommodationIDList:");
                for (int n : accommodationIDList) {
                    System.out.println(n);
                }
                System.out.println("CONTENT of newAccommodationIDList:");
                if (!(newAccommodationIDList == null)) {
                    for (int n : newAccommodationIDList) {
                        System.out.println(n);
                    }
                }
                System.out.println("CONTENT of newAccommodationList:");
                if (!(newAccommodationList == null)) {
                    for (int n : newAccommodationList) {
                        System.out.println(n);
                    }
                }
                System.out.println("END CONTENT ^^^^^^^^^^^");
                //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                //Ask user if they want to overwrite old records in db
                //<editor-fold defaultstate="collapsed" desc="Show Alert dialog">
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("");
                alert.setHeaderText("Some accommodations may already exist");
                alert.setContentText("Import updated data from Excel? This will overwrite old data.");

                ButtonType yesBtn = new ButtonType("Yes");
                ButtonType noBtn = new ButtonType("No");
                ButtonType cancelBtn = new ButtonType("Cancel");

                alert.getButtonTypes().setAll(yesBtn, noBtn, cancelBtn);

                Optional<ButtonType> result = alert.showAndWait();
//</editor-fold>
                if (result.get() == yesBtn) {
                    importedFlag2 = -1;
                    //'REPLACE INTO' Updates existing records and inserts new ones if pkID not found
                    if (!(newAccommodationIDList == null) || !(newAccommodationList == null)) {
                        importedFlag = accommodationQueries.importNewAccommodation(newAccommodationList, newAccommodationIDList, file.getPath(), results);
                    }
                    if (!(accommodationIDList == null)) {
                        importedFlag2 = accommodationQueries.importOldAccommodation(accommodationIDList, file.getPath(), results);
                    }
                    refreshTable();
                } else if (result.get() == noBtn) {
                    // user chose no, dont import clashing accommodations
                    //'INSERT IGNORE INTO' import the rest according to fields selected, ignoring records with existing pkID
                    if (!(newAccommodationIDList == null) || !(newAccommodationList == null)) {
                        importedFlag = accommodationQueries.importNewAccommodation(newAccommodationList, newAccommodationIDList, file.getPath(), results);
                    }
                    refreshTable();
                }
            } else if (results != null) {
                //no clashes, straight import accommodations according to fields selected
                importedFlag = accommodationQueries.importAccommodation(file.getPath(), results);
                refreshTable();
            }
        }
        if (importedFlag == 1 && importedFlag2 == 1) {
            importedFlag = -1;
            //Shows notice dialog of success
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Success:\nAccommodation Details imported from Excel Sheet to database");
            alert.showAndWait();
            // Generate new log record
            Log log = new Log("Imported Accommodation Details from excel " + file.getPath());
            logQueries.insertLog(log, hotelOverview.getUserID());
            hotelOverview.refreshLogTable();
        } else if (importedFlag == 0 || importedFlag2 == 0) {
            //Export not succesful from SQLException       
            importedFlag = -1;
            //Show error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Import File Failed");
            alert.setContentText("Error:\nCheck if the file is right type.\n");
            alert.showAndWait();
        } else if (importedFlag == 2 || importedFlag2 == 2) {
            //Export not succesful            
            importedFlag = -1;
            //Show error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Failed File Import");
            alert.setContentText("Error:\nNot all Accommodation Details may be imported from Excel Sheet.\nCheck if the file is right type.\n(This error maybe from extra blank rows in excel)");
            alert.showAndWait();
        }
        //For refocusing the selected accom. later (because accom. gets defocused when a btn is clicked)
        accommodationTable.getSelectionModel().select(accommodationTable.getSelectionModel().getFocusedIndex());
    }

    public static boolean contains(TableView<Accommodation> table, int accommodationID) {
        for (Accommodation m : table.getItems()) {
            if (m.getMemID() == (accommodationID)) {
                return true;
            }
        }
        return false;
    }

    //For Import excel function
    public ArrayList<String> showDbLinkAccommodationDialog(List<String> blueRow) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("DbLinkDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage memberDialogStage = new Stage();
            memberDialogStage.setTitle("Link Database Fields");
            memberDialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            memberDialogStage.setScene(scene);

            // Set the person into the controller.
            DbLinkDialogController controller = new DbLinkDialogController();
            controller = loader.getController();
            controller.setMemberDialogStage(memberDialogStage);
            //Load comboboxes with excel headers
            controller.setComboBoxes(blueRow);
            // Show the dialog and wait until the user closes it
            memberDialogStage.showAndWait();
            if (controller.isConfirmClicked() == false) {
                return null;
            } else {
                //Information is saved in handleOk() from EditAccommodationDialogController            
                return controller.getCheckBoxesResults();
            }
        } catch (IOException e) {
            return null;
        }
    }

    @FXML
    private void handleExportExcel() throws IOException {
        //accommodationQueries.exportAccommodation();
        
        //For refocusing the selected accom. later (because accom. gets defocused when a btn is clicked)
        accommodationTable.getSelectionModel().select(accommodationTable.getSelectionModel().getFocusedIndex());

    }

    public ObservableList<Accommodation> getAccommodationData() {
        return accommodationData;
    }

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public TabAccommodationController() {
    }

    @FXML
    private void handleDeleteAccommodation() {
        try {
            int selectedIndex = accommodationTable.getSelectionModel().getSelectedIndex();
            Accommodation selectedAccommodation = accommodationTable.getSelectionModel().getSelectedItem();

            if (selectedIndex >= 0) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Delete Record");
                alert.setHeaderText("Delete Record");
                alert.setContentText("Are you sure you want to delete this record?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // ... user chose OK
                    // Delete record in the database
                    accommodationQueries.deleteAccommodation(accommodationTable.getSelectionModel().getSelectedItem());
                    // Delete record on the table
                    accommodationTable.getItems().remove(selectedIndex);
                    // Generate new log record
                    Log log = new Log("Deleted Accommodation: " + selectedAccommodation.getMemID());
                    logQueries.insertLog(log, hotelOverview.getUserID());
                    hotelOverview.refreshLogTable();
                    refreshTable();
                } else {
                    // ... user chose CANCEL or closed the dialog                    
                }
            } else {
                // Nothing selected.
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("No Selection");
                alert.setHeaderText("No Pai Wei Selected");
                alert.setContentText("Please select a Pai Wei in the table.");

                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("Error! handleDeleteAccommodation()!");
        }
        //For refocusing the selected accom. later (because accom. gets defocused when a btn is clicked)
        accommodationTable.getSelectionModel().select(accommodationTable.getSelectionModel().getFocusedIndex());
    }

    @FXML
    private void handleNewAccommodation() {
        //Accommodation tempAccommodation = new Accommodation("", "", "", "", null, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", null, "", null, null, "", "");
        Accommodation tempAccommodation = new Accommodation();

        boolean okClicked = showNewAccommodationDialog(tempAccommodation);
        if (okClicked) {
            sessionEmployeeName = loginQueries.getSessionEmployeeName(hotelOverview.getUserID());
            accommodationQueries.insertAccommodation(tempAccommodation);
            accommodationTable.getItems().add(tempAccommodation);

            // Generate new log record
            Log log = new Log("Added New Accommodation: " + tempAccommodation.getMemID());
            logQueries.insertLog(log, hotelOverview.getUserID());
            hotelOverview.refreshLogTable();
        }
        //For refocusing the selected accom. later (because accom. gets defocused when a btn is clicked)
        accommodationTable.getSelectionModel().select(accommodationTable.getSelectionModel().getFocusedIndex());
    }

    @FXML
    private void handleEditAccommodation() {
        try {
            Accommodation selectedAccommodation = accommodationTable.getSelectionModel().getSelectedItem();

            if (selectedAccommodation != null) {
                sessionEmployeeName = loginQueries.getSessionEmployeeName(hotelOverview.getUserID());
                boolean okClicked = showEditAccommodationDialog(selectedAccommodation);
                if (okClicked) {
                    accommodationQueries.updateAccommodation(selectedAccommodation);
                    //hotelOverview.refreshLogTable();

                    //Generate new log record
                    Log log = new Log("Edited Accommodation of ID " + selectedAccommodation.getMemID());
                    logQueries.insertLog(log, hotelOverview.getUserID());
                    hotelOverview.refreshLogTable();
                    refreshTable(selectedAccommodation);
                }
            } else {
                // Nothing selected.
                Alert alert = new Alert(AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("No Selection");
                alert.setHeaderText("No Pai Wei Selected");
                alert.setContentText("Please select a Pai Wei in the table.");

                alert.showAndWait();
            }

        } catch (Exception e) {
            System.out.println("handleEditAccommodation() error");
        }
        //For refocusing the selected accom. later (because accom. gets defocused when a btn is clicked)
        accommodationTable.getSelectionModel().select(accommodationTable.getSelectionModel().getFocusedIndex());
    }

    /**
     * To refresh the table AND show updated info of certain pai wei.
     */
    public void refreshTable(Accommodation selectedAccommodation) {   //REDISPLAY ITEMS AFTER REFRESH
        accommodationData.clear(); //Clears table
        accommodationData.addAll(accommodationQueries.getAccommodation()); //Repopulates with new data
        showAccommodationDetails(selectedAccommodation); //Redisplays selected with new data
    }

    public void refreshTable() {   //REDISPLAY ITEMS AFTER REFRESH
        accommodationData.clear(); //Clears table
        accommodationData.addAll(accommodationQueries.getAccommodation()); //Repopulates with new data
    }

    public boolean showEditAccommodationDialog(Accommodation accommodation) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditAccommodationDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage accommodationDialogStage = new Stage();
            accommodationDialogStage.setTitle("Edit Accommodation");
            accommodationDialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            accommodationDialogStage.setScene(scene);

            // Set the person into the controller.
            EditAccommodationDialogController controller = loader.getController();
            controller.setAccommodationDialogStage(accommodationDialogStage);
            controller.setAccommodation(accommodation);

            // Show the dialog and wait until the user closes it
            accommodationDialogStage.showAndWait();

            return controller.isConfirmClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showNewAccommodationDialog(Accommodation accommodation) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditAccommodationDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage accommodationDialogStage = new Stage();
            accommodationDialogStage.setTitle("Add New Accommodation");
            accommodationDialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            accommodationDialogStage.setScene(scene);

            // Set the person into the controller.
            EditAccommodationDialogController controller = loader.getController();
            controller.setAccommodationDialogStage(accommodationDialogStage);
            controller.setNewAccommodation(accommodation);

            // Show the dialog and wait until the user closes it
            accommodationDialogStage.showAndWait();

            return controller.isConfirmClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAccommodationDetails(Accommodation accommodation) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        if (accommodation != null) {
            aAnchorPane.setOpacity(100);
            // Fill the labels with info from the accommodation object.
            aID.setText(Integer.toString(accommodation.getAID()));
            memID.setText(Integer.toString(accommodation.getMemID()));
            if (accommodation.getCheckInDate().isEqual(LocalDate.parse("9999-01-01", formatter))) {
                checkInDate.setText("");
            } else {
                checkInDate.setText(DateUtil.format(accommodation.getCheckInDate()));
            }
            if (accommodation.getCheckOutDate().isEqual(LocalDate.parse("9999-01-01", formatter))) {
                checkOutDate.setText("");
            } else {
                checkOutDate.setText(DateUtil.format(accommodation.getCheckOutDate()));
            }
            note.setText(accommodation.getNote());
            room.setText(accommodation.getRoom());
            gender.setText(accommodation.getGender());
            typeOfID.setText(accommodation.getTypeOfID());
            IDNum.setText(accommodation.getIDNum());
            reason.setText(accommodation.getReason());
            emgContactPerson.setText(accommodation.getEmgContactPerson());
            emgContactNum.setText(accommodation.getEmgContactNum());
            relationship.setText(accommodation.getRelationship());
        } else {
            // Accommodation is null, remove all the information.  
            aID.setText("");
            memID.setText("");
            checkInDate.setText("");
            checkOutDate.setText("");
            note.setText("");
            room.setText("");
            gender.setText("");
            typeOfID.setText("");
            IDNum.setText("");
            reason.setText("");
            emgContactPerson.setText("");
            emgContactNum.setText("");
            relationship.setText("");
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<Accommodation> filteredData = new FilteredList<>(accommodationData, p -> true);

            // Set the filter Predicate whenever the filter changes.
            accommodationFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(accommodation -> {
                    // If filter text is empty, display all accommodation.
                    if (newValue == null || newValue.isEmpty()) {
                        accommodationTable.setItems(accommodationData);
                        return true;
                    }

                    // Compare user ID, first name and last name of every booking with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (Integer.toString(accommodation.getMemID()).contains(lowerCaseFilter)) {
                        return true; // Filter matches user ID.
                    }
                    /*else if (member.getFName().toLowerCase().contains(lowerCaseFilter)) {
                     return true; // Filter matches first name.
                     } else if (member.getLName().toLowerCase().contains(lowerCaseFilter)) {
                     return true; // Filter matches last name.
                     }*/

                    return false; // Does not match.
                });
            });

            accommodationFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
                // Wrap the FilteredList in a SortedList. 
                SortedList<Accommodation> sortedData = new SortedList<>(filteredData);

                // Bind the SortedList comparator to the TableView comparator.
                sortedData.comparatorProperty().bind(accommodationTable.comparatorProperty());

                // Add sorted (and filtered) data to the table.
                accommodationTable.setItems(sortedData);
            });

            accommodationData.addAll(accommodationQueries.getAccommodation());
            accommodationTable.setItems(accommodationData);

            // Initialize the member table with the two columns.
            accommodationIDColumn.setCellValueFactory(cellData -> cellData.getValue().aIDProperty().asObject());
            contributorColumn.setCellValueFactory(cellData -> cellData.getValue().memIDProperty().asObject());//************************** TO FIX: 
            /*fNameColumn.setCellValueFactory(cellData -> cellData.getValue().fNameProperty());
             lNameColumn.setCellValueFactory(cellData -> cellData.getValue().lNameProperty());*/

            // Hide whole detail pane
            aAnchorPane.setOpacity(0);

            accommodationTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showAccommodationDetails(newValue));
        } catch (Exception e) {
            System.out.println("TabAccommodation initilize error!");
            e.printStackTrace();
        }
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
