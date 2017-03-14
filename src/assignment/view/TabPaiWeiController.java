package assignment.view;

//<editor-fold defaultstate="collapsed" desc="Imports">
import assignment.database.PaiWeiQueries;
import assignment.model.PaiWei;
import assignment.MainApp;
import assignment.database.EmployeeQueries;
import assignment.database.LogQueries;
import assignment.database.LoginQueries;
import assignment.model.Employee;
import assignment.model.Log;
import assignment.model.Member;
import assignment.util.DateUtil;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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

public class TabPaiWeiController implements Initializable {

    // <editor-fold defaultstate="collapsed" desc=" Declaring FXML elements ">
    @FXML
    AnchorPane pwAnchorPane;
    @FXML
    TableView<PaiWei> paiWeiTable;
    @FXML
    private TableColumn<PaiWei, String> paiWeiIDColumn;
    @FXML
    private TableColumn<PaiWei, Integer> memIDColumn;
    @FXML
    private TableColumn<PaiWei, String> fNameColumn;
    @FXML
    private TableColumn<PaiWei, String> lNameColumn;
    @FXML
    private TableColumn<PaiWei, String> cNameColumn;

    @FXML
    private Label paiWeiID;
    @FXML
    private Label memID;
    @FXML
    private Label engName;
    @FXML
    private Label cName;
    @FXML
    private Label type;
    @FXML
    private Label size;
    @FXML
    private Label dharmaService;
    @FXML
    private Label subEventType;
    @FXML
    private Label date;
    @FXML
    private Label payMethod;
    @FXML
    private Label amtPaid;
    @FXML
    private Label totalDon;
    @FXML
    private Label payDate;
    @FXML
    private Label bal;
    @FXML
    private TextArea note;
    @FXML
    private Label createdBy;
    @FXML
    private Label createdOn;
    @FXML
    private Label updateBy;
    @FXML
    private Label updateOn;
    @FXML
    private Label mis1;
    @FXML
    private Label mis2;
    @FXML
    private Label mis3;
    @FXML
    private Label mis4;
    @FXML
    private Label mis5;
    @FXML
    private Label mis6;
    @FXML
    private Label mis7;
    @FXML
    private Label mis8;
    @FXML
    private Label mis9;
    @FXML
    private Label mis10;
    @FXML
    private Label yang1;
    @FXML
    private Label yang2;
    @FXML
    private Label yang3;
    @FXML
    private Label yang4;
    @FXML
    private Label yang5;
    @FXML
    private Label yang6;
    @FXML
    private Label yang7;
    @FXML
    private Label yang8;
    @FXML
    private Label yang9;
    @FXML
    private Label yang10;
    @FXML
    private Label drenType;
    @FXML
    private Label clan1;
    @FXML
    private Label clan2;
    @FXML
    private Label clan3;
    @FXML
    private Label deceased1;
    @FXML
    private Label deceased2;
    @FXML
    private Label deceased3;
    @FXML
    private Label deceased4;
    @FXML
    private Label deceased5;
    @FXML
    private Label deceased6;
    @FXML
    private Label deceased7;
    @FXML
    private Label deceased8;
    @FXML
    private Label deceased9;
    @FXML
    private Label deceased10;
    @FXML
    private Label oldOwners1;
    @FXML
    private Label oldOwners2;

    @FXML
    private TextField paiWeiFilterField;
    @FXML
    private FileChooser fileChooser;

// </editor-fold>
    private File file;
    File existDirectory;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
    HotelOverviewController hotelOverview;
    MainApp mainApp;
    private ObservableList<PaiWei> paiWeiData = FXCollections.observableArrayList();
    private ObservableList<Employee> employeeData = FXCollections.observableArrayList();
    public PaiWeiQueries paiWeiQueries = new PaiWeiQueries();
    private EmployeeQueries employeeQueries = new EmployeeQueries();
    private LoginQueries loginQueries = new LoginQueries();
    private LogQueries logQueries = new LogQueries();
    private String sessionEmployeeName;
    private LoginScreenController loginScreenController = new LoginScreenController();

    //Grab 'notes' TextArea text and update changes to db
    private PaiWei selectedPaiWei = null;

    @FXML
    private void handleSaveNote() {
        //Clicking 'saveBtn' will loses focus on member in memberTable. This uses previous saved mem from first click
        if (paiWeiTable.getSelectionModel().getSelectedItem() != null) {
            selectedPaiWei = paiWeiTable.getSelectionModel().getSelectedItem();
        } else if (selectedPaiWei == null) { //No member was ever selected from the beginning
            return;
        }
        //Checks if any changes has been made; if saved != textAreaBox ==> save it
        if (!(selectedPaiWei.getNote()).equals(note.getText())) {
            sessionEmployeeName = loginQueries.getSessionEmployeeName(hotelOverview.getUserID());
            selectedPaiWei.setUpdateBy(sessionEmployeeName);
            selectedPaiWei.setNote(note.getText());
            paiWeiQueries.updatePaiWei(selectedPaiWei);
            hotelOverview.refreshLogTable();
            //Generate new log record
            Log log = new Log("Edited note of paiWeiID " + selectedPaiWei.getPaiWeiID());
            logQueries.insertLog(log, hotelOverview.getUserID());
            hotelOverview.refreshLogTable();
            refreshTable(selectedPaiWei);
        }
        //For refocusing the selected don. later (because don. gets defocused when a btn is clicked)
        paiWeiTable.getSelectionModel().select(paiWeiTable.getSelectionModel().getFocusedIndex());
    }

    //<editor-fold defaultstate="collapsed" desc="handleImportExcel">
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
            List<String> blueRow = paiWeiQueries.getBlueRows(file.getPath());

            //Open 'Link Database Field' dialoge & wait till user closes. Also return list of fields to import
            ArrayList<String> results = showDbLinkDialog(blueRow);
            List<Integer> paiWeiIDList = new ArrayList<>(), newPaiWeiIDList = new ArrayList<>(), newPaiWeiList = new ArrayList<>();
            if (results != null && results.contains("Member ID")) { //Will contain clashes
                //Grab memID column and sort into ‘paiWeiIDList’ and 'newPaiWeiList'
                paiWeiIDList = paiWeiQueries.getPaiWeiID(file.getPath());
                newPaiWeiIDList = paiWeiQueries.getNewPaiWeiIndexList();
                //************************** TO FIX:
                //Run check against db for existing memID ==> further sort out non existing memID ==> remove and add to (newPaiWeiList)
                for (int i = 0; i < paiWeiIDList.size(); i++) {
                    if (!contains(paiWeiTable, paiWeiIDList.get(i))) {
                        //Move to newPaiWeiList
                        newPaiWeiList.add(paiWeiIDList.get(i));
                        paiWeiIDList.remove(i);
                        i--;
                    }
                }
                //************************** TO FIX:
                System.out.println("CONTENT of paiWeiIDList:");
                for (int n : paiWeiIDList) {
                    System.out.println(n);
                }
                System.out.println("CONTENT of newPaiWeiIDList:");
                if (!(newPaiWeiIDList == null)) {
                    for (int n : newPaiWeiIDList) {
                        System.out.println(n);
                    }
                }
                System.out.println("CONTENT of newPaiWeiList:");
                if (!(newPaiWeiList == null)) {
                    for (int n : newPaiWeiList) {
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
                    if (!(newPaiWeiIDList == null) || !(newPaiWeiList == null)) {
                        importedFlag = paiWeiQueries.importNewMember(newPaiWeiList, newPaiWeiIDList, file.getPath(), results);
                    }
                    if (!(paiWeiIDList == null)) {
                        importedFlag2 = paiWeiQueries.importOldMember(paiWeiIDList, file.getPath(), results);
                    }
                    refreshTable();
                } else if (result.get() == noBtn) {
                    // user chose no, dont import clashing members
                    //'INSERT IGNORE INTO' import the rest according to fields selected, ignoring records with existing pkID
                    if (!(newPaiWeiIDList == null) || !(newPaiWeiList == null)) {
                        importedFlag = paiWeiQueries.importNewMember(newPaiWeiList, newPaiWeiIDList, file.getPath(), results);
                    }
                    refreshTable();
                }
            } else if (results != null) {
                //no clashes, straight import members according to fields selected
                importedFlag = paiWeiQueries.importPaiWei(file.getPath(), results);
                refreshTable();
            }
        }
        if (importedFlag == 1 && importedFlag2 == 1) {
            importedFlag = -1;
            //Shows notice dialog of success
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Success:\nMember Details imported from Excel Sheet to database");
            alert.showAndWait();
            // Generate new log record
            Log log = new Log("Imported Member Details from excel " + file.getPath());
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
            alert.setContentText("Error:\nNot all Member Details may be imported from Excel Sheet.\nCheck if the file is right type.\n(This error maybe from extra blank rows in excel)");
            alert.showAndWait();
        }
        //For refocusing the selected don. later (because don. gets defocused when a btn is clicked)
        paiWeiTable.getSelectionModel().select(paiWeiTable.getSelectionModel().getFocusedIndex());
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="handleExportExcel">
    @FXML
    private void handleExportExcel() throws IOException {
        int exportedFlag = -1;

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        //Single file selection
        file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
        if (file != null) {
            File existDirectory = file.getParentFile();
            fileChooser.setInitialDirectory(existDirectory);

            //Then set file as source for import & get excel headers
            List<String> blueRow = Arrays.asList("Database Pai Wei ID", "Pai Wei ID", "Member ID",
                    "Type", "Size", "Dharma Service", "Sub Event Type",
                    "Date of Donation", "Payment Method", "Amount Paid", "Total Donation",
                    "Paid On", "Balance", "Note", "Create Date",
                    "Update Date", "Created By", "Updated By", "消災1",
                    "消災2", "消災3", "消災4", "消災5",
                    "消災6", "消災7", "消災8", "消災9",
                    "消災10", "陽上1", "陽上2", "陽上3",
                    "陽上4", "陽上5", "陽上6", "陽上7",
                    "陽上8", "陽上9", "陽上10", "超薦類型",
                    "門氏1", "門氏2", "門氏3", "亡者1",
                    "亡者2", "亡者3", "亡者4", "亡者5",
                    "亡者6", "亡者7", "亡者8", "亡者9",
                    "亡者10", "舊業主1", "舊業主2");
            //Open 'Link Database Field' dialoge & wait till user closes. Also return list of fields to import
            ArrayList<String> results = showDbLinkDialog(blueRow);

            //Then grab file name from dialog as fileName for export
            exportedFlag = paiWeiQueries.exportPaiWei(results, file.getPath());
        }

        if (exportedFlag == 1) {
            exportedFlag = -1;
            //Shows notice dialog of success
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notice");
            alert.setHeaderText(null);
            alert.setContentText("Pai Wei Details Exported in Excel Sheet");
            alert.showAndWait();
        } else if (exportedFlag == 0) { //Export not succesful
            exportedFlag = -1;
            //Show error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Export Failed");
            alert.setContentText("Pai Wei Details failed to export in Excel Sheet. \nCheck if the file is right type.");
            alert.showAndWait();
        } else {
            // Generate new log record
            Log log = new Log("Exported Pai Wei into excel " + file.getPath());
            logQueries.insertLog(log, hotelOverview.getUserID());
            hotelOverview.refreshLogTable();
        }
        //For refocusing the selected don. later (because don. gets defocused when a btn is clicked)
        paiWeiTable.getSelectionModel().select(paiWeiTable.getSelectionModel().getFocusedIndex());
    }
//</editor-fold>

    public static boolean contains(TableView<PaiWei> table, int paiWeiID) {
        for (PaiWei m : table.getItems()) {
            if (m.getPaiWeiID() == (paiWeiID)) {
                return true;
            }
        }
        return false;
    }

    //For Import and Export excel function
    public ArrayList<String> showDbLinkDialog(List<String> blueRow) {
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
                //Information is saved in handleOk() from EditMemberDialogController            
                return controller.getCheckBoxesResults();
            }
        } catch (IOException e) {
            return null;
        }
    }

    public ObservableList<PaiWei> getPaiWeiData() {
        return paiWeiData;
    }

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public TabPaiWeiController() {
    }

    //<editor-fold defaultstate="collapsed" desc="handleDeletePaiWei">
    @FXML
    private void handleDeletePaiWei() {
        try {
            int selectedIndex = paiWeiTable.getSelectionModel().getSelectedIndex();
            PaiWei selectedPaiWei = paiWeiTable.getSelectionModel().getSelectedItem();

            if (selectedIndex >= 0) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Delete Record");
                alert.setHeaderText("Delete Record");
                alert.setContentText("Are you sure you want to delete this record?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // ... user chose OK
                    // Delete record in the database
                    paiWeiQueries.deletePaiWei(paiWeiTable.getSelectionModel().getSelectedItem());
                    // Delete record on the table
                    paiWeiTable.getItems().remove(selectedIndex);
                    // Generate new log record
                    Log log = new Log("Deleted PaiWei: " + selectedPaiWei.getPaiWeiID());
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
            System.out.println("Error! handleDeletePaiWei()!");
        }
        //For refocusing the selected don. later (because don. gets defocused when a btn is clicked)
        paiWeiTable.getSelectionModel().select(paiWeiTable.getSelectionModel().getFocusedIndex());
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="handleNewPaiWei">
    @FXML
    private void handleNewPaiWei() {
        //PaiWei tempPaiWei = new PaiWei("", "", "", "", null, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", null, "", null, null, "", "");
        PaiWei tempPaiWei = new PaiWei();

        boolean okClicked = showNewPaiWeiDialog(tempPaiWei);
        if (okClicked) {
            sessionEmployeeName = loginQueries.getSessionEmployeeName(hotelOverview.getUserID());
            paiWeiQueries.insertPaiWeis(tempPaiWei, sessionEmployeeName);
            paiWeiTable.getItems().add(tempPaiWei);

            // Generate new log record
            Log log = new Log("Added New PaiWei: " + tempPaiWei.getPaiWeiID());
            logQueries.insertLog(log, hotelOverview.getUserID());
            hotelOverview.refreshLogTable();
        }
        //For refocusing the selected don. later (because don. gets defocused when a btn is clicked)
        paiWeiTable.getSelectionModel().select(paiWeiTable.getSelectionModel().getFocusedIndex());
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="handleEditPaiWei">
    @FXML
    private void handleEditPaiWei() {
        try {
            PaiWei selectedPaiWei = paiWeiTable.getSelectionModel().getSelectedItem();

            if (selectedPaiWei != null) {
                sessionEmployeeName = loginQueries.getSessionEmployeeName(hotelOverview.getUserID());
                boolean okClicked = showEditPaiWeiDialog(selectedPaiWei);
                if (okClicked) {
                    paiWeiQueries.updatePaiWei(selectedPaiWei);
                    //hotelOverview.refreshLogTable();

                    //Generate new log record
                    Log log = new Log("Edited PaiWei of ID " + selectedPaiWei.getPaiWeiID());
                    logQueries.insertLog(log, hotelOverview.getUserID());
                    hotelOverview.refreshLogTable();
                    refreshTable(selectedPaiWei);
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
            System.out.println("handleEditPaiWei() error");
            e.printStackTrace();
        }
        //For refocusing the selected don. later (because don. gets defocused when a btn is clicked)
        paiWeiTable.getSelectionModel().select(paiWeiTable.getSelectionModel().getFocusedIndex());
    }
//</editor-fold>

    /**
     * To refresh the table AND show updated info of certain pai wei.
     */
    public void refreshTable(PaiWei selectedPaiWei) {   //REDISPLAY ITEMS AFTER REFRESH
        paiWeiData.clear(); //Clears table
        paiWeiData.addAll(paiWeiQueries.getPaiWeis()); //Repopulates with new data
        showPaiWeiDetails(selectedPaiWei); //Redisplays selected with new data
    }

    public void refreshTable() {   //REDISPLAY ITEMS AFTER REFRESH
        paiWeiData.clear(); //Clears table
        paiWeiData.addAll(paiWeiQueries.getPaiWeis()); //Repopulates with new data
    }

    public boolean showEditPaiWeiDialog(PaiWei paiWei) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditPaiWeiDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage paiWeiDialogStage = new Stage();
            paiWeiDialogStage.setTitle("Edit PaiWei");
            paiWeiDialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            paiWeiDialogStage.setScene(scene);

            // Set the person into the controller.
            EditPaiWeiDialogController controller = loader.getController();
            controller.setPaiWeiDialogStage(paiWeiDialogStage);
            controller.setPaiWei(paiWei);

            // Show the dialog and wait until the user closes it
            paiWeiDialogStage.showAndWait();

            return controller.isConfirmClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showNewPaiWeiDialog(PaiWei paiWei) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditPaiWeiDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage paiWeiDialogStage = new Stage();
            paiWeiDialogStage.setTitle("Add New PaiWei");
            paiWeiDialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            paiWeiDialogStage.setScene(scene);

            // Set the person into the controller.
            EditPaiWeiDialogController controller = loader.getController();
            controller.setPaiWeiDialogStage(paiWeiDialogStage);
            controller.setNewPaiWei(paiWei);

            // Show the dialog and wait until the user closes it
            paiWeiDialogStage.showAndWait();

            return controller.isConfirmClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showPaiWeiDetails(PaiWei paiWei) {
        if (paiWei != null) {
            pwAnchorPane.setOpacity(100);
            // Fill the labels with info from the paiWei object.
            paiWeiID.setText(paiWei.getPwID());
            memID.setText(paiWeiQueries.getMID(paiWei.getMemID()));
            Member m = paiWeiQueries.getMemberName(paiWei.getMemID());
            engName.setText(m.getLName() + ", " + m.getFName());
            cName.setText(m.getCName());
            type.setText(paiWei.getType());
            size.setText(paiWei.getSize());
            dharmaService.setText(paiWei.getDharmaService());
            subEventType.setText(paiWei.getSubEventType());
            if (paiWei.getDate().isEqual(LocalDate.parse("9999-01-01", formatter))) {
                date.setText("");
            } else {
                date.setText(DateUtil.format(paiWei.getDate()));
            }
            payMethod.setText(paiWei.getPayMethod());
            amtPaid.setText(Double.toString(paiWei.getAmtPaid()));
            totalDon.setText(Double.toString(paiWei.getTotalDon()));
            if (paiWei.getPayDate().isEqual(LocalDate.parse("9999-01-01", formatter))) {
                payDate.setText("");
            } else {
                payDate.setText(DateUtil.format(paiWei.getPayDate()));
            }
            bal.setText(Double.toString(paiWei.getBal()));
            note.setText(paiWei.getNote());
            createdBy.setText(paiWei.getCreatedBy());
            if (paiWei.getCreatedOn().isEqual(LocalDate.parse("9999-01-01", formatter))) {
                createdOn.setText("");
            } else {
                createdOn.setText(DateUtil.format(paiWei.getCreatedOn()));
            }
            updateBy.setText(paiWei.getUpdateBy());
            if (paiWei.getUpdateOn().isEqual(LocalDate.parse("9999-01-01", formatter))) {
                updateOn.setText("");
            } else {
                updateOn.setText(DateUtil.format(paiWei.getUpdateOn()));
            }
            mis1.setText(paiWei.getMis1());
            mis2.setText(paiWei.getMis2());
            mis3.setText(paiWei.getMis3());
            mis4.setText(paiWei.getMis4());
            mis5.setText(paiWei.getMis5());
            mis6.setText(paiWei.getMis6());
            mis7.setText(paiWei.getMis7());
            mis8.setText(paiWei.getMis8());
            mis9.setText(paiWei.getMis9());
            mis10.setText(paiWei.getMis10());
            yang1.setText(paiWei.getYang1());
            yang2.setText(paiWei.getYang2());
            yang3.setText(paiWei.getYang3());
            yang4.setText(paiWei.getYang4());
            yang5.setText(paiWei.getYang5());
            yang6.setText(paiWei.getYang6());
            yang7.setText(paiWei.getYang7());
            yang8.setText(paiWei.getYang8());
            yang9.setText(paiWei.getYang9());
            yang10.setText(paiWei.getYang10());
            drenType.setText(paiWei.getDrenType());
            clan1.setText(paiWei.getClan1());
            clan2.setText(paiWei.getClan2());
            clan3.setText(paiWei.getClan3());
            deceased1.setText(paiWei.getDeceased1());
            deceased2.setText(paiWei.getDeceased2());
            deceased3.setText(paiWei.getDeceased3());
            deceased4.setText(paiWei.getDeceased4());
            deceased5.setText(paiWei.getDeceased5());
            deceased6.setText(paiWei.getDeceased6());
            deceased7.setText(paiWei.getDeceased7());
            deceased8.setText(paiWei.getDeceased8());
            deceased9.setText(paiWei.getDeceased9());
            deceased10.setText(paiWei.getDeceased10());
            oldOwners1.setText(paiWei.getOldOwners1());
            oldOwners2.setText(paiWei.getOldOwners2());
        } else {
            // PaiWei is null, remove all the information.            
            paiWeiID.setText("");
            memID.setText("");
            engName.setText("");
            cName.setText("");
            type.setText("");
            size.setText("");
            dharmaService.setText("");
            subEventType.setText("");
            date.setText("");
            payMethod.setText("");
            amtPaid.setText("");
            totalDon.setText("");
            payDate.setText("");
            bal.setText("");
            note.setText("");
            createdBy.setText("");
            createdOn.setText("");
            updateBy.setText("");
            updateOn.setText("");
            mis1.setText("");
            mis2.setText("");
            mis3.setText("");
            mis4.setText("");
            mis5.setText("");
            mis6.setText("");
            mis7.setText("");
            mis8.setText("");
            mis9.setText("");
            mis10.setText("");
            yang1.setText("");
            yang2.setText("");
            yang3.setText("");
            yang4.setText("");
            yang5.setText("");
            yang6.setText("");
            yang7.setText("");
            yang8.setText("");
            yang9.setText("");
            yang10.setText("");
            drenType.setText("");
            clan1.setText("");
            clan2.setText("");
            clan3.setText("");
            deceased1.setText("");
            deceased2.setText("");
            deceased3.setText("");
            deceased4.setText("");
            deceased5.setText("");
            deceased6.setText("");
            deceased7.setText("");
            deceased8.setText("");
            deceased9.setText("");
            deceased10.setText("");
            oldOwners1.setText("");
            oldOwners2.setText("");
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<PaiWei> filteredData = new FilteredList<>(paiWeiData, p -> true);

            // Set the filter Predicate whenever the filter changes.
            paiWeiFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(paiWei -> {
                    // If filter text is empty, display all paiWei.
                    if (newValue == null || newValue.isEmpty()) {
                        paiWeiTable.setItems(paiWeiData);
                        return true;
                    }

                    // Compare user ID, first name and last name of every booking with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (Integer.toString(paiWei.getPaiWeiID()).contains(lowerCaseFilter)) {
                        return true; // Filter matches pai wei ID.
                    } else if (Integer.toString(paiWei.getMemID()).contains(lowerCaseFilter)) {//************************** TO FIX: change to fName,lName & cName
                        return true; // Filter matches memID.
                    } else if (paiWei.getEngName(paiWei.getMemID(), 1).toLowerCase().contains(lowerCaseFilter)) {//************************** TO FIX: change to fName,lName & cName
                        return true; // Filter matches eng name.
                    } else if (paiWei.getEngName(paiWei.getMemID(), 2).toLowerCase().contains(lowerCaseFilter)) {//************************** TO FIX: change to fName,lName & cName
                        return true; // Filter matches eng name.
                    } else if (paiWei.getCName(paiWei.getMemID()).toLowerCase().contains(lowerCaseFilter)) {//************************** TO FIX: change to fName,lName & cName
                        return true; // Filter matches chinese name.
                    }

                    return false; // Does not match.
                });
            });

            paiWeiFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
                // Wrap the FilteredList in a SortedList. 
                SortedList<PaiWei> sortedData = new SortedList<>(filteredData);

                // Bind the SortedList comparator to the TableView comparator.
                sortedData.comparatorProperty().bind(paiWeiTable.comparatorProperty());

                // Add sorted (and filtered) data to the table.
                paiWeiTable.setItems(sortedData);
            });

            paiWeiData.addAll(paiWeiQueries.getPaiWeis());
            paiWeiTable.setItems(paiWeiData);

            // Initialize the member table with the two columns.
            paiWeiIDColumn.setCellValueFactory(cellData -> cellData.getValue().pwIDProperty());
            memIDColumn.setCellValueFactory(cellData -> cellData.getValue().memIDProperty().asObject());
            fNameColumn.setCellValueFactory(cellData -> cellData.getValue().engNameProperty(cellData.getValue().memIDProperty().getValue().intValue(), 1));
            lNameColumn.setCellValueFactory(cellData -> cellData.getValue().engNameProperty(cellData.getValue().memIDProperty().getValue().intValue(), 2));
            cNameColumn.setCellValueFactory(cellData -> cellData.getValue().cNameProperty(cellData.getValue().memIDProperty().getValue().intValue()));

            // Hide whole detail pane
            pwAnchorPane.setOpacity(0);

            paiWeiTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showPaiWeiDetails(newValue));
        } catch (Exception e) {
            System.out.println("TabPaiWei initilize error!");
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
