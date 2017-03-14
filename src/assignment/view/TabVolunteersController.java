package assignment.view;

//<editor-fold defaultstate="collapsed" desc="Imports">
import assignment.database.VolunteersQueries;
import assignment.model.Volunteers;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
//</editor-fold>

public class TabVolunteersController implements Initializable {

    // <editor-fold defaultstate="collapsed" desc=" Declaring FXML elements ">
    @FXML
    AnchorPane volAnchorPane;
    @FXML
    TableView<Volunteers> volunteersTable;
    @FXML
    private TableColumn<Volunteers, Integer> memIDColumn;

    @FXML
    private TableColumn<Volunteers, String> contributorColumn;
    /*@FXML
     private TableColumn<Volunteers, String> fNameColumn;
     @FXML
     private TableColumn<Volunteers, String> lNameColumn;*/

    @FXML
    private Label memID;
    @FXML
    private Label availableDays;
    @FXML
    private Label availableTime;
    @FXML
    private Label other;
    @FXML
    private Label group;
//***********************************************************************
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
    private TextField volunteersFilterField;
    @FXML
    private FileChooser fileChooser;

// </editor-fold>
    private File file;
    File existDirectory;
    HotelOverviewController hotelOverview;
    MainApp mainApp;
    private ObservableList<Volunteers> volunteersData = FXCollections.observableArrayList();
    private ObservableList<Employee> employeeData = FXCollections.observableArrayList();
    public VolunteersQueries volunteersQueries = new VolunteersQueries();
    private EmployeeQueries employeeQueries = new EmployeeQueries();
    private LoginQueries loginQueries = new LoginQueries();
    private LogQueries logQueries = new LogQueries();
    private String sessionEmployeeName;
    private LoginScreenController loginScreenController = new LoginScreenController();

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
            List<String> blueRow = volunteersQueries.getBlueRows(file.getPath());

            //Open 'Link Database Field' dialoge & wait till user closes. Also return list of fields to import      
            ArrayList<String> results = showDbLinkVolunteersDialog(blueRow);
            List<Integer> volunteersIDList = new ArrayList<>(), newVolunteersIDList = new ArrayList<>(), newVolunteersList = new ArrayList<>();
            if (results != null && results.contains("Volunteers ID")) { //Will contain clashes
                //Grab memID column and sort into ‘volunteersIDList’ and 'newVolunteersList'
                volunteersIDList = volunteersQueries.getVolunteersID(file.getPath());
                newVolunteersIDList = volunteersQueries.getNewVolunteersIndexList();
                //************************** TO FIX: 
                //Run check against db for existing memID ==> further sort out non existing memID ==> remove and add to (newVolunteersList)                
                for (int i = 0; i < volunteersIDList.size(); i++) {
                    if (!contains(volunteersTable, volunteersIDList.get(i))) {
                        //Move to newVolunteersList
                        newVolunteersList.add(volunteersIDList.get(i));
                        volunteersIDList.remove(i);
                        i--;
                    }
                }
                //************************** TO FIX: 
                System.out.println("CONTENT of volunteersIDList:");
                for (int n : volunteersIDList) {
                    System.out.println(n);
                }
                System.out.println("CONTENT of newVolunteersIDList:");
                if (!(newVolunteersIDList == null)) {
                    for (int n : newVolunteersIDList) {
                        System.out.println(n);
                    }
                }
                System.out.println("CONTENT of newVolunteersList:");
                if (!(newVolunteersList == null)) {
                    for (int n : newVolunteersList) {
                        System.out.println(n);
                    }
                }
                System.out.println("END CONTENT ^^^^^^^^^^^");
                //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                //Ask user if they want to overwrite old records in db
                //<editor-fold defaultstate="collapsed" desc="Show Alert dialog">
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("");
                alert.setHeaderText("Some members may already exist");
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
                    if (!(newVolunteersIDList == null) || !(newVolunteersList == null)) {
                        importedFlag = volunteersQueries.importNewVolunteers(newVolunteersList, newVolunteersIDList, file.getPath(), results);
                    }
                    if (!(volunteersIDList == null)) {
                        importedFlag2 = volunteersQueries.importOldVolunteers(volunteersIDList, file.getPath(), results);
                    }
                    refreshTable();
                } else if (result.get() == noBtn) {
                    // user chose no, dont import clashing members
                    //'INSERT IGNORE INTO' import the rest according to fields selected, ignoring records with existing pkID
                    if (!(newVolunteersIDList == null) || !(newVolunteersList == null)) {
                        importedFlag = volunteersQueries.importNewVolunteers(newVolunteersList, newVolunteersIDList, file.getPath(), results);
                    }
                    refreshTable();
                }
            } else if (results != null) {
                //no clashes, straight import members according to fields selected
                importedFlag = volunteersQueries.importVolunteers(file.getPath(), results);
                refreshTable();
            }
        }
        if (importedFlag == 1 && importedFlag2 == 1) {
            importedFlag = -1;
            //Shows notice dialog of success
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Success:\nVolunteers Details imported from Excel Sheet to database");
            alert.showAndWait();
            // Generate new log record
            Log log = new Log("Imported Volunteers Details from excel " + file.getPath());
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
            alert.setContentText("Error:\nNot all Volunteers Details may be imported from Excel Sheet.\nCheck if the file is right type.\n(This error maybe from extra blank rows in excel)");
            alert.showAndWait();
        }
        //For refocusing the selected don. later (because don. gets defocused when a btn is clicked)
        volunteersTable.getSelectionModel().select(volunteersTable.getSelectionModel().getFocusedIndex());
    }

    public static boolean contains(TableView<Volunteers> table, int volunteersID) {
        for (Volunteers m : table.getItems()) {
            if (m.getMemID() == (volunteersID)) {
                return true;
            }
        }
        return false;
    }

    //For Import excel function
    public ArrayList<String> showDbLinkVolunteersDialog(List<String> blueRow) {
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
                //Information is saved in handleOk() from EditVolunteersDialogController            
                return controller.getCheckBoxesResults();
            }
        } catch (IOException e) {
            return null;
        }
    }

    @FXML
    private void handleExportExcel() throws IOException {
        //volunteersQueries.exportVolunteers();
        System.out.println("Not implemented yet");

        //For refocusing the selected don. later (because don. gets defocused when a btn is clicked)
        volunteersTable.getSelectionModel().select(volunteersTable.getSelectionModel().getFocusedIndex());
    }

    public ObservableList<Volunteers> getVolunteersData() {
        return volunteersData;
    }

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public TabVolunteersController() {
    }

    @FXML
    private void handleDeleteVolunteers() {
        try {
            int selectedIndex = volunteersTable.getSelectionModel().getSelectedIndex();
            Volunteers selectedVolunteers = volunteersTable.getSelectionModel().getSelectedItem();

            if (selectedIndex >= 0) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Delete Record");
                alert.setHeaderText("Delete Record");
                alert.setContentText("Are you sure you want to delete this record?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // ... user chose OK
                    // Delete record in the database
                    volunteersQueries.deleteVolunteers(volunteersTable.getSelectionModel().getSelectedItem());
                    // Delete record on the table
                    volunteersTable.getItems().remove(selectedIndex);
                    // Generate new log record
                    Log log = new Log("Deleted Volunteers: " + selectedVolunteers.getMemID());
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
            System.out.println("Error! handleDeleteVolunteers()!");
        }
        //For refocusing the selected don. later (because don. gets defocused when a btn is clicked)
        volunteersTable.getSelectionModel().select(volunteersTable.getSelectionModel().getFocusedIndex());
    }

    @FXML
    private void handleNewVolunteers() {
        //Volunteers tempVolunteers = new Volunteers("", "", "", "", null, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", null, "", null, null, "", "");
        Volunteers tempVolunteers = new Volunteers();

        boolean okClicked = showNewVolunteersDialog(tempVolunteers);
        if (okClicked) {
            sessionEmployeeName = loginQueries.getSessionEmployeeName(hotelOverview.getUserID());
            volunteersQueries.insertVolunteers(tempVolunteers);
            volunteersTable.getItems().add(tempVolunteers);

            // Generate new log record
            Log log = new Log("Added New Volunteers: " + tempVolunteers.getMemID());
            logQueries.insertLog(log, hotelOverview.getUserID());
            hotelOverview.refreshLogTable();
        }
        //For refocusing the selected don. later (because don. gets defocused when a btn is clicked)
        volunteersTable.getSelectionModel().select(volunteersTable.getSelectionModel().getFocusedIndex());
    }

    @FXML
    private void handleEditVolunteers() {
        try {
            Volunteers selectedVolunteers = volunteersTable.getSelectionModel().getSelectedItem();

            if (selectedVolunteers != null) {
                sessionEmployeeName = loginQueries.getSessionEmployeeName(hotelOverview.getUserID());
                boolean okClicked = showEditVolunteersDialog(selectedVolunteers);
                if (okClicked) {
                    volunteersQueries.updateVolunteers(selectedVolunteers);
                    //hotelOverview.refreshLogTable();

                    //Generate new log record
                    Log log = new Log("Edited Volunteers of ID " + selectedVolunteers.getMemID());
                    logQueries.insertLog(log, hotelOverview.getUserID());
                    hotelOverview.refreshLogTable();
                    refreshTable(selectedVolunteers);
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
            System.out.println("handleEditVolunteers() error");
        }
        //For refocusing the selected don. later (because don. gets defocused when a btn is clicked)
        volunteersTable.getSelectionModel().select(volunteersTable.getSelectionModel().getFocusedIndex());
    }

    /**
     * To refresh the table AND show updated info of certain pai wei.
     */
    public void refreshTable(Volunteers selectedVolunteers) {   //REDISPLAY ITEMS AFTER REFRESH
        volunteersData.clear(); //Clears table
        volunteersData.addAll(volunteersQueries.getVolunteers()); //Repopulates with new data
        showVolunteersDetails(selectedVolunteers); //Redisplays selected with new data
    }

    public void refreshTable() {   //REDISPLAY ITEMS AFTER REFRESH
        volunteersData.clear(); //Clears table
        volunteersData.addAll(volunteersQueries.getVolunteers()); //Repopulates with new data
    }

    public boolean showEditVolunteersDialog(Volunteers volunteers) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditVolunteersDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage volunteersDialogStage = new Stage();
            volunteersDialogStage.setTitle("Edit Volunteers");
            volunteersDialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            volunteersDialogStage.setScene(scene);

            // Set the person into the controller.
            EditVolunteersDialogController controller = loader.getController();
            controller.setVolunteersDialogStage(volunteersDialogStage);
            controller.setVolunteers(volunteers);

            // Show the dialog and wait until the user closes it
            volunteersDialogStage.showAndWait();

            return controller.isConfirmClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showNewVolunteersDialog(Volunteers volunteers) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditVolunteersDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage volunteersDialogStage = new Stage();
            volunteersDialogStage.setTitle("Add New Volunteers");
            volunteersDialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            volunteersDialogStage.setScene(scene);

            // Set the person into the controller.
            EditVolunteersDialogController controller = loader.getController();
            controller.setVolunteersDialogStage(volunteersDialogStage);
            controller.setNewVolunteers(volunteers);

            // Show the dialog and wait until the user closes it
            volunteersDialogStage.showAndWait();

            return controller.isConfirmClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showVolunteersDetails(Volunteers volunteers) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        if (volunteers != null) {            
            volAnchorPane.setOpacity(100);
            // Fill the labels with info from the volunteers object.
            memID.setText(Integer.toString(volunteers.getMemID()));            
            if (volunteers.getAvailableDays().isEqual(LocalDate.parse("9999-01-01", formatter))) {
                availableDays.setText("");
            } else {
                availableDays.setText(DateUtil.format(volunteers.getAvailableDays()));
            }
            
            if (volunteers.getAvailableTime().isEqual(LocalDate.parse("9999-01-01", formatter))) {
                availableTime.setText("");
            } else {
                availableTime.setText(DateUtil.format(volunteers.getAvailableTime()));
            }
            other.setText(volunteers.getOther());
            group.setText(volunteers.getGroup());

        } else {
            // Volunteers is null, remove all the information. 
            memID.setText("");
            availableDays.setText("");
            availableTime.setText("");
            other.setText("");
            group.setText("");
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<Volunteers> filteredData = new FilteredList<>(volunteersData, p -> true);

            // Set the filter Predicate whenever the filter changes.
            volunteersFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(volunteers -> {
                    // If filter text is empty, display all volunteers.
                    if (newValue == null || newValue.isEmpty()) {
                        volunteersTable.setItems(volunteersData);
                        return true;
                    }

                    // Compare user ID, first name and last name of every booking with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (Integer.toString(volunteers.getMemID()).contains(lowerCaseFilter)) {
                        return true; // Filter matches user ID.
                    } /*else if (member.getFName().toLowerCase().contains(lowerCaseFilter)) {
                     return true; // Filter matches first name.
                     } else if (member.getLName().toLowerCase().contains(lowerCaseFilter)) {
                     return true; // Filter matches last name.
                     }*/

                    return false; // Does not match.
                });
            });

            volunteersFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
                // Wrap the FilteredList in a SortedList. 
                SortedList<Volunteers> sortedData = new SortedList<>(filteredData);

                // Bind the SortedList comparator to the TableView comparator.
                sortedData.comparatorProperty().bind(volunteersTable.comparatorProperty());

                // Add sorted (and filtered) data to the table.
                volunteersTable.setItems(sortedData);
            });

            volunteersData.addAll(volunteersQueries.getVolunteers());
            volunteersTable.setItems(volunteersData);

            // Initialize the member table with the two columns.
            memIDColumn.setCellValueFactory(cellData -> cellData.getValue().memIDProperty().asObject());//************************** TO FIX: 
            /*fNameColumn.setCellValueFactory(cellData -> cellData.getValue().fNameProperty());
             lNameColumn.setCellValueFactory(cellData -> cellData.getValue().lNameProperty());*/

            //Hide new, edit and delete buttons
            /*editBtn.setVisible(false);
             newBtn.setVisible(false);
             deleteBtn.setVisible(false);*/
            // Clear member details.
            volAnchorPane.setOpacity(0);

            volunteersTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showVolunteersDetails(newValue));
        } catch (Exception e) {
            System.out.println("TabVolunteers initilize error!");
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
