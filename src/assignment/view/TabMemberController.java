package assignment.view;

//<editor-fold defaultstate="collapsed" desc="Imports">
import assignment.database.MemberQueries;
import assignment.model.Member;
import assignment.MainApp;
import assignment.database.AccommodationQueries;
import assignment.database.DonationQueries;
import assignment.database.EmployeeQueries;
import assignment.database.LogQueries;
import assignment.database.LoginQueries;
import assignment.database.PaiWeiQueries;
import assignment.database.VolunteersQueries;
import assignment.model.Accommodation;
import assignment.model.Donation;
import assignment.model.Employee;
import assignment.model.Log;
import assignment.model.PaiWei;
import assignment.model.Volunteers;
import assignment.util.DateUtil;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
//</editor-fold>

public class TabMemberController implements Initializable {

    // <editor-fold defaultstate="collapsed" desc=" FXML items ">
    //************************** MEMBER PANE ***********************************
    //<editor-fold defaultstate="collapsed" desc="MemberFXML">    
    @FXML
    private VBox memVBox;
    @FXML
    private Accordion accord;
    @FXML
    private TitledPane memberPane;
    @FXML
    TableView<Member> memberTable;
    @FXML
    private TextField memberFilterField;
    @FXML
    private TableColumn<Member, String> memIDColumn;
    @FXML
    private TableColumn<Member, String> cNameColumn;
    @FXML
    private TableColumn<Member, String> fNameColumn;
    @FXML
    private TableColumn<Member, String> lNameColumn;

    @FXML
    private Label memIDLabel;
    @FXML
    private Label cNameLabel;
    @FXML
    private Label fNameLabel;
    @FXML
    private Label lNameLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label dobLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label hPhoneLabel;
    @FXML
    private Label mobLabel;
    @FXML
    private Label wPhoneLabel;
    @FXML
    private Label faxLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label wechatLabel;
    @FXML
    private Label prefContact;
    @FXML
    private Label lang1;
    @FXML
    private Label lang2;
    @FXML
    private Label eduEmp;
    @FXML
    private Label nation;
    @FXML
    private Label address;
    @FXML
    private Label suburb;
    @FXML
    private Label postcode;
    @FXML
    private Label state;
    @FXML
    private Label dharma;
    @FXML
    private Label dateRefuge;
    @FXML
    private TextArea note;
    @FXML
    private Label createDate;
    @FXML
    private Label updateOn;
    @FXML
    private Label createBy;
    @FXML
    private Label updateBy;
//</editor-fold>
    //************************** PAI WEI PANE **********************************    
    //<editor-fold defaultstate="collapsed" desc="PaiWeiFXML">
    @FXML
    private MenuButton pwOptionsBtn;
    @FXML
    private VBox pwVBox;
    @FXML
    private HBox pwHBox;
    @FXML
    private Label pwMessage;
    @FXML
    TableView<PaiWei> paiWeiTable;
    @FXML
    private TableColumn<PaiWei, String> pwIDColumn;
    @FXML
    private TableColumn<PaiWei, LocalDate> pwDateColumn;
    @FXML
    private Label paiWeiID;
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
    private TextArea pwNote;
    @FXML
    private Label createdBy;
    @FXML
    private Label createdOn;
    @FXML
    private Label pwUpdateBy;
    @FXML
    private Label pwUpdateOn;
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
//</editor-fold>    
    //************************** DONATION PANE *********************************
    //<editor-fold defaultstate="collapsed" desc="DonationFXML">
    @FXML
    private MenuButton donOptionsBtn;
    @FXML
    private HBox donHBox;
    @FXML
    private Label donMessage;
    @FXML
    TableView<Donation> donationTable;
    @FXML
    private TableColumn<Donation, Integer> donIDColumn;
    @FXML
    private TableColumn<Donation, LocalDate> donDateColumn;

    @FXML
    private Label donID;
    @FXML
    private Label donType;
    @FXML
    private Label donDharmaService;
    @FXML
    private Label donDate;
    @FXML
    private Label donPayMethod;
    @FXML
    private Label donAmtPaid;
    @FXML
    private Label donTotalDon;
    @FXML
    private Label donPayDate;
    @FXML
    private Label donBal;
    @FXML
    private TextArea donNote;
    @FXML
    private Label donCreatedBy;
    @FXML
    private Label donCreatedOn;
    @FXML
    private Label donUpdateBy;
    @FXML
    private Label donUpdateOn;
//</editor-fold>
    //************************** VOLUNTEERING PANE *****************************
    //<editor-fold defaultstate="collapsed" desc="VolunteerFXML">
    @FXML
    private MenuButton volOptionsBtn;
    @FXML
    private GridPane volGPane;
    @FXML
    private Label volMessage;
    @FXML
    private Label availableDays;
    @FXML
    private Label availableTime;
    @FXML
    private Label other;
    @FXML
    private Label group;
//</editor-fold>
    //************************** ACCOMMODATION PANE ****************************
    //<editor-fold defaultstate="collapsed" desc="AccommodationFXML">    
    @FXML
    private MenuButton aOptionsBtn;
    @FXML
    private HBox aHBox;
    @FXML
    private Label aMessage;
    @FXML
    TableView<Accommodation> accommodationTable;
    @FXML
    private TableColumn<Accommodation, Integer> aIDColumn;
    @FXML
    private TableColumn<Accommodation, LocalDate> aDateColumn;
    @FXML
    private Label aID;
    @FXML
    private Label checkInDate;
    @FXML
    private Label checkOutDate;
    @FXML
    private TextArea aNote;
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
//</editor-fold>

    @FXML
    private Button editBtn;
    @FXML
    private Button newBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button exportExcelBtn;
    @FXML
    private Button importExcelBtn;
    @FXML
    private Button saveNoteBtn;
    @FXML
    private FileChooser fileChooser;
// </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variable declarations">
    private File file;
    File existDirectory;

    HotelOverviewController hotelOverview;
    MainApp mainApp;
    private ObservableList<Member> memberData = FXCollections.observableArrayList();
    private ObservableList<Employee> employeeData = FXCollections.observableArrayList();
    private ObservableList<PaiWei> paiWeiData = FXCollections.observableArrayList();
    private ObservableList<Donation> donationData = FXCollections.observableArrayList();
    private ObservableList<Accommodation> accommodationData = FXCollections.observableArrayList();
    private MemberQueries memberQueries = new MemberQueries();
    private PaiWeiQueries paiWeiQueries = new PaiWeiQueries();
    private DonationQueries donationQueries = new DonationQueries();
    private VolunteersQueries volunteersQueries = new VolunteersQueries();
    private AccommodationQueries accommodationQueries = new AccommodationQueries();
    private EmployeeQueries employeeQueries = new EmployeeQueries();
    private String sessionEmployeeName;
    private LoginQueries loginQueries = new LoginQueries();
    private LogQueries logQueries = new LogQueries();
    private LoginScreenController loginScreenController = new LoginScreenController();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
//</editor-fold>

    public ObservableList<Member> getMemberData() {
        return memberData;
    }

    @FXML
    private void handleImportExcel() throws IOException {
        //For refocusing the selected member later (because mem gets defocused when a btn is clicked)
        memberTable.getSelectionModel().select(memberTable.getSelectionModel().getFocusedIndex());//Get empName for filling in created by (for records without a created by data)
        List<Log> sessionDetail = memberQueries.getSessionDetails(hotelOverview.getUserID());
        String empName = sessionDetail.get(0).getEmpFirstName() + " " + sessionDetail.get(0).getEmpLastName();

        int importedFlag = -1;
        int importedFlag2 = 1;

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Excel Files", "*.xlsx"),
                new ExtensionFilter("All Files", "*.*")
        );

        //Single file selection
        file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if (file != null) {
            fileChooser.setInitialDirectory(existDirectory);
            //Then set file as source for import & get excel headers
            List<String> blueRow = memberQueries.getBlueRows(file.getPath());
            //Open 'Link Database Field' dialoge & wait till user closes. Also return list of fields to import      
            ArrayList<String> results = showDbLinkDialog(blueRow);
            List<Integer> memIDList = new ArrayList<>(), newMemIndexList = new ArrayList<>(), newMemList = new ArrayList<>();
            if (results != null && results.contains("Member ID")) { //Will contain clashes
                //Grab memID column and sort into ‘memIDList’ and 'newMemList'
                memIDList = memberQueries.getMemID(file.getPath());
                newMemIndexList = memberQueries.getNewMemIndexList();
                //************************** TO FIX: 
                //Run check against db for existing memID ==> further sort out non existing memID ==> remove and add to (newMemList)                
                for (int i = 0; i < memIDList.size(); i++) {
                    if (!contains(memberTable, memIDList.get(i))) {
                        //Move to newMemList
                        newMemList.add(memIDList.get(i));
                        memIDList.remove(i);
                        i--;
                    }
                }
                //Ask user if they want to overwrite old records in db
                //<editor-fold defaultstate="collapsed" desc="Show Alert dialog">
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("");
                alert.setHeaderText("Some members already exist");
                alert.setContentText("Overwrite old member details with this import?");

                ButtonType yesBtn = new ButtonType("Yes");
                ButtonType noBtn = new ButtonType("No");
                ButtonType cancelBtn = new ButtonType("Cancel");

                alert.getButtonTypes().setAll(yesBtn, noBtn, cancelBtn);

                Optional<ButtonType> result = alert.showAndWait();
//</editor-fold>

                if (result.get() == yesBtn) {
                    importedFlag2 = -1;
                    //'REPLACE INTO' Updates existing records and inserts new ones if pkID not found
                    if (!(newMemIndexList == null) || !(newMemList == null)) {
                        importedFlag = memberQueries.importNewMember(newMemList, newMemIndexList, file.getPath(), results, empName);
                    }
                    if (!(memIDList == null)) {
                        importedFlag2 = memberQueries.importOldMember(memIDList, file.getPath(), results, empName);
                    }
                    refreshTable();
                } else if (result.get() == noBtn) {
                    // user chose no, dont import clashing members
                    //'INSERT IGNORE INTO' import the rest according to fields selected, ignoring records with existing pkID
                    if (!(newMemIndexList == null) || !(newMemList == null)) {
                        importedFlag = memberQueries.importNewMember(newMemList, newMemIndexList, file.getPath(), results, empName);
                    }
                    refreshTable();
                }
            } else if (results != null) {
                //no clashes, straight import members according to fields selected
                importedFlag = memberQueries.importMember(file.getPath(), results, empName);
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
    }

    public static boolean contains(TableView<Member> table, int memID) {
        for (Member m : table.getItems()) {
            if (m.getMemID() == (memID)) {
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

    @FXML
    private void handleExportExcel() throws IOException {
        //For refocusing the selected member later (because mem gets defocused when a btn is clicked)
        memberTable.getSelectionModel().select(memberTable.getSelectionModel().getFocusedIndex());
        int exportedFlag = -1;

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Excel Files", "*.xlsx"),
                new ExtensionFilter("All Files", "*.*")
        );

        //Single file selection
        file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
        if (file != null) {
            File existDirectory = file.getParentFile();
            fileChooser.setInitialDirectory(existDirectory);

            //Then set file as source for import & get excel headers
            List<String> blueRow = Arrays.asList(
                    "Database Member ID", "Member ID", "First Name",
                    "Last Name", "Other Name", "Gender", "Date of birth",
                    "Home Phone", "Mobile", "Work Phone", "Fax", "Email",
                    "WeChat ID", "Preferred Contact Method", "Language",
                    "Skills/Hobbies", "Education", "Employment", "Nationality",
                    "Address", "Suburb", "Postcode", "State", "Dharma Name",
                    "Date of Taking Refuge", "Note", "Create Date", "Update Date",
                    "Created By", "Updated By");
            //Open 'Link Database Field' dialoge & wait till user closes. Also return list of fields to import      
            ArrayList<String> results = showDbLinkDialog(blueRow);

            //Then grab file name from dialog as fileName for export
            exportedFlag = memberQueries.exportMember(results, file.getPath());
        }

        if (exportedFlag == 1) {
            exportedFlag = -1;
            //Shows notice dialog of success
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notice");
            alert.setHeaderText(null);
            alert.setContentText("Member Details Exported in Excel Sheet");
            alert.showAndWait();
        } else if (exportedFlag == 0) { //Export not succesful            
            exportedFlag = -1;
            //Show error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Export Failed");
            alert.setContentText("Member Details failed to export in Excel Sheet. \nCheck if the file is right type.");
            alert.showAndWait();
        } else {
            // Generate new log record
            Log log = new Log("Exported Member into excel " + file.getPath());
            logQueries.insertLog(log, hotelOverview.getUserID());
            hotelOverview.refreshLogTable();
        }
    }

    public TabMemberController() {
    }

    //This additionally adds CREATE_BY field in this method and changeDate within another method
    TabPaiWeiController tabPWClass = new TabPaiWeiController();

    //<editor-fold defaultstate="collapsed" desc="handle Pai Wei stuff">
    @FXML
    private void handleNewPaiWei() {
        int index = memberTable.getSelectionModel().getFocusedIndex(); //(1/2) For refocusing mem record later        
        int indexPW = 0;
        /*if (!Bindings.isEmpty(paiWeiTable.getItems())) { //Only grab index if pw table has records in it
            indexPW = paiWeiTable.getSelectionModel().getFocusedIndex();
        }*/
        try {
            indexPW = paiWeiTable.getSelectionModel().getFocusedIndex();
        } catch (Exception e) {
            indexPW = 0;
        }
        //PaiWei tempPaiWei = new PaiWei("", "", "", "", null, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", null, "", null, null, "", "");
        PaiWei tempPaiWei = new PaiWei();
        Member selectedMember = memberTable.getSelectionModel().getSelectedItem();

        if (selectedMember != null) {
            tempPaiWei.setMemID(selectedMember.getMemID());
            boolean okClicked = tabPWClass.showNewPaiWeiDialog(tempPaiWei);
            if (okClicked) {
                sessionEmployeeName = loginQueries.getSessionEmployeeName(hotelOverview.getUserID());
                paiWeiQueries.insertPaiWeis(tempPaiWei, sessionEmployeeName);
                //tabPWClass.paiWeiTable.getItems().add(tempPaiWei); //TRYING TO CHANGE TO REFRESH PAI WEI TABLE INSTEAD OF ADDING TO IT

                // Generate new log record
                Log log = new Log("Added New PaiWei: " + tempPaiWei.getPaiWeiID());
                logQueries.insertLog(log, hotelOverview.getUserID());
                hotelOverview.refreshLogTable();//refresh main pane log
                hotelOverview.refreshPaiWeiTable(); //refresh main pane Pai Wei
                refreshTable(selectedMember); //refresh main pane Member
                showAccordianDetails(selectedMember); //refreshes accordian records within main Member pane
            }
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Member Selected");
            alert.setContentText("Please select a member in the table.");

            alert.showAndWait();
        }
        //(2/2) For refocusing the selected member later (because mem gets defocused when a btn is clicked)
        memberTable.getSelectionModel().select(index);
        paiWeiTable.getSelectionModel().select(indexPW);
    }

    @FXML
    private void handleEditPaiWei() {
        int index = memberTable.getSelectionModel().getFocusedIndex(); //(1/2) For refocusing mem record later
        int indexPW = 0;
        try {
            indexPW = paiWeiTable.getSelectionModel().getFocusedIndex();
        } catch (Exception e) {
            indexPW = 0;
        }
        try {
            PaiWei selectedPaiWei = paiWeiTable.getSelectionModel().getSelectedItem();
            Member selectedMember = memberTable.getSelectionModel().getSelectedItem();

            if (selectedPaiWei != null) {
                sessionEmployeeName = loginQueries.getSessionEmployeeName(hotelOverview.getUserID());
                boolean okClicked = tabPWClass.showEditPaiWeiDialog(selectedPaiWei);
                if (okClicked) {
                    paiWeiQueries.updatePaiWei(selectedPaiWei);
                    //hotelOverview.refreshLogTable();

                    //Generate new log record
                    Log log = new Log("Edited PaiWei of ID " + selectedPaiWei.getPaiWeiID());
                    logQueries.insertLog(log, hotelOverview.getUserID());
                    hotelOverview.refreshLogTable();//refresh main pane log
                    hotelOverview.refreshPaiWeiTable(); //refresh main pane Pai Wei
                    refreshTable(selectedMember); //refresh main pane Member
                    showAccordianDetails(selectedMember); //refreshes accordian records within main Member pane
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
        //For refocusing the selected member later (because mem gets defocused when a btn is clicked)
        memberTable.getSelectionModel().select(index);
        paiWeiTable.getSelectionModel().select(indexPW);
    }

    @FXML
    private void handleDeletePaiWei() {
        int index = memberTable.getSelectionModel().getFocusedIndex(); //(1/2) For refocusing mem record later
        try {
            int selectedIndex = paiWeiTable.getSelectionModel().getSelectedIndex();
            PaiWei selectedPaiWei = paiWeiTable.getSelectionModel().getSelectedItem();
            Member selectedMember = memberTable.getSelectionModel().getSelectedItem();

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
                    hotelOverview.refreshLogTable();//refresh main pane log
                    hotelOverview.refreshPaiWeiTable(); //refresh main pane Pai Wei
                    refreshTable(selectedMember); //refresh main pane Member
                    showAccordianDetails(selectedMember); //refreshes accordian records within main Member pane
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
        //For refocusing the selected member later (because mem gets defocused when a btn is clicked)
        memberTable.getSelectionModel().select(index);
        hideInnerPWPane();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="handle Member stuff">
//This additionally adds CREATE_BY field in this method and changeDate within another method
    @FXML
    private void handleNewMember() {

        //Member tempMember = new Member("", "", "", "", null, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", null, "", LocalDate.now(), null, "", "");
        Member tempMember = new Member();
        boolean okClicked = showNewMemberDialog(tempMember);
        if (okClicked) {
            //Get current user's name and set into member's CREATE_BY field
            sessionEmployeeName = loginQueries.getSessionEmployeeName(hotelOverview.getUserID());
            tempMember.setCreateBy(sessionEmployeeName);
            tempMember.setUpdateBy("");
            //Get current timestamp and set into create date
            Calendar calendar = Calendar.getInstance();
            Timestamp stamp = new Timestamp(System.currentTimeMillis());
            Date date = new Date(stamp.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+10"));
            String formattedDate = sdf.format(date);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
            tempMember.setCreateDate(LocalDate.parse(formattedDate, formatter));
            tempMember.setUpdateOn(LocalDate.parse("9999-01-01", formatter));

            memberQueries.insertMember(tempMember);
            memberTable.getItems().add(tempMember);

            // Generate new log record
            Log log = new Log("Added New Member: " + tempMember.getFName()
                    + " " + tempMember.getLName());
            logQueries.insertLog(log, hotelOverview.getUserID());
            hotelOverview.refreshLogTable();
        }
        //For refocusing the selected member later (because mem gets defocused when a btn is clicked)
        memberTable.getSelectionModel().select(memberTable.getSelectionModel().getFocusedIndex());
    }

    //This additionally adds/edits UPDATE_BY field in this method and updateDate within another method
    @FXML
    private void handleEditMember() {
        //(1/2) For refocusing the selected member later (because mem gets defocused when a btn is clicked)
        int index = memberTable.getSelectionModel().getFocusedIndex();

        try {
            Member selectedMember = memberTable.getSelectionModel().getSelectedItem();

            if (selectedMember != null) {
                boolean okClicked = showEditMemberDialog(selectedMember);
                if (okClicked) {
                    sessionEmployeeName = loginQueries.getSessionEmployeeName(hotelOverview.getUserID());
                    selectedMember.setUpdateBy(sessionEmployeeName);

                    memberQueries.updateMember(selectedMember);
                    hotelOverview.refreshLogTable();
                    //Generate new log record
                    Log log = new Log("Edited Member of memID " + selectedMember.getMemID());
                    logQueries.insertLog(log, hotelOverview.getUserID());
                    hotelOverview.refreshLogTable();
                    refreshTable(selectedMember);
                }
            } else {
                // Nothing selected.
                Alert alert = new Alert(AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("No Selection");
                alert.setHeaderText("No Member Selected");
                alert.setContentText("Please select a member in the table.");

                alert.showAndWait();
            }

        } catch (Exception e) {
            System.out.println("handleEditMember() error");
            e.printStackTrace();
        }

        //(2/2) For refocusing the selected member later (because mem gets defocused when a btn is clicked)
        memberTable.getSelectionModel().select(index);
    }

    @FXML
    private void handleDeleteMember() {
        try {
            int selectedIndex = memberTable.getSelectionModel().getSelectedIndex();
            Member selectedMember = memberTable.getSelectionModel().getSelectedItem();

            System.out.println("Confirm delete all pai wei associated with this member!");

            if (selectedIndex >= 0) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Delete Record");
                alert.setHeaderText("Delete Record");
                alert.setContentText("Are you sure you want to delete this record?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // ... user chose OK
                    // Delete record in the database
                    memberQueries.deleteMember(memberTable.getSelectionModel().getSelectedItem());
                    // Delete record on the table
                    memberTable.getItems().remove(selectedIndex);
                    // Generate new log record
                    Log log = new Log("Deleted Member: " + selectedMember.getFName()
                            + " " + selectedMember.getLName());
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
                alert.setHeaderText("No Member Selected");
                alert.setContentText("Please select an member in the table.");

                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("Error! handleDeleteMember()!");
        }

        //Defocuses selection from table AND blank out all inner panes and messages
        hideInnerPane();
    }
//</editor-fold>

    //Grab 'notes' TextArea text and update changes to db
    private Member selectedMember = null;

    //<editor-fold defaultstate="collapsed" desc="handleSaveNotes">
    @FXML
    private void handleSaveNote() {
        int index = memberTable.getSelectionModel().getFocusedIndex(); //(1/2) For refocusing the selected member later (because mem gets defocused when a btn is clicked)
        //Clicking 'saveBtn' will loses focus on member in memberTable. This uses previous saved mem from first click
        if (memberTable.getSelectionModel().getSelectedItem() != null) {
            selectedMember = memberTable.getSelectionModel().getSelectedItem();
        } else if (selectedMember == null) { //No member was ever selected from the beginning
            return;
        }
        //Checks if any changes has been made; if saved != textAreaBox ==> save it
        if (!(selectedMember.getNote()).equals(note.getText())) {
            sessionEmployeeName = loginQueries.getSessionEmployeeName(hotelOverview.getUserID());
            selectedMember.setUpdateBy(sessionEmployeeName);
            selectedMember.setNote(note.getText());
            memberQueries.updateMember(selectedMember);
            hotelOverview.refreshLogTable();
            //Generate new log record
            Log log = new Log("Edited note of memID " + selectedMember.getMemID());
            logQueries.insertLog(log, hotelOverview.getUserID());
            hotelOverview.refreshLogTable();
            refreshTable(selectedMember);
        }
        memberTable.getSelectionModel().select(index); //(2/2)
    }

    private PaiWei selectedPaiWei = null;

    @FXML
    private void handleSaveNotePW() { //For Pai Wei inner tab's save note
        //Clicking 'saveBtn' will loses focus on member in memberTable. This uses previous saved mem from first click
        if (paiWeiTable.getSelectionModel().getSelectedItem() != null) {
            selectedPaiWei = paiWeiTable.getSelectionModel().getSelectedItem();
        } else if (selectedPaiWei == null) { //No member was ever selected from the beginning
            return;
        }
        //Checks if any changes has been made; if saved != textAreaBox ==> save it
        if (!(selectedPaiWei.getNote()).equals(pwNote.getText())) {
            sessionEmployeeName = loginQueries.getSessionEmployeeName(hotelOverview.getUserID());
            selectedPaiWei.setUpdateBy(sessionEmployeeName);
            selectedPaiWei.setNote(pwNote.getText());
            paiWeiQueries.updatePaiWei(selectedPaiWei);
            hotelOverview.refreshLogTable();
            //Generate new log record
            Log log = new Log("Edited note of paiWeiID " + selectedPaiWei.getMemID());
            logQueries.insertLog(log, hotelOverview.getUserID());
            hotelOverview.refreshLogTable();
            refreshPWTable(selectedPaiWei);
        }
        //For refocusing the selected member later (because mem gets defocused when a btn is clicked)
        memberTable.getSelectionModel().select(memberTable.getSelectionModel().getFocusedIndex());
    }

    private Donation selectedDonation = null;

    @FXML
    private void handleSaveNoteDon() { //For Donation inner tab's save note
        //Clicking 'saveBtn' will loses focus on member in memberTable. This uses previous saved mem from first click
        if (donationTable.getSelectionModel().getSelectedItem() != null) {
            selectedDonation = donationTable.getSelectionModel().getSelectedItem();
        } else if (selectedDonation == null) { //No member was ever selected from the beginning
            return;
        }
        //Checks if any changes has been made; if saved != textAreaBox ==> save it
        if (!(selectedDonation.getNote()).equals(donNote.getText())) {
            sessionEmployeeName = loginQueries.getSessionEmployeeName(hotelOverview.getUserID());
            selectedDonation.setUpdateBy(sessionEmployeeName);
            selectedDonation.setNote(donNote.getText());
            donationQueries.updateDonation(selectedDonation);
            hotelOverview.refreshLogTable();
            //Generate new log record
            Log log = new Log("Edited note of donID " + selectedDonation.getMemID());
            logQueries.insertLog(log, hotelOverview.getUserID());
            hotelOverview.refreshLogTable();
            refreshDonTable(selectedDonation);
        }
        //For refocusing the selected member later (because mem gets defocused when a btn is clicked)
        memberTable.getSelectionModel().select(memberTable.getSelectionModel().getFocusedIndex());
    }

    private Accommodation selectedAccommodation = null;

    @FXML
    private void handleSaveNoteA() { //For Accommodation inner tab's save note
        //Clicking 'saveBtn' will loses focus on member in memberTable. This uses previous saved mem from first click
        if (accommodationTable.getSelectionModel().getSelectedItem() != null) {
            selectedAccommodation = accommodationTable.getSelectionModel().getSelectedItem();
        } else if (selectedAccommodation == null) { //No member was ever selected from the beginning
            return;
        }
        //Checks if any changes has been made; if saved != textAreaBox ==> save it
        if (!(selectedAccommodation.getNote()).equals(aNote.getText())) {
            sessionEmployeeName = loginQueries.getSessionEmployeeName(hotelOverview.getUserID());
            selectedAccommodation.setNote(aNote.getText());
            accommodationQueries.updateAccommodation(selectedAccommodation);
            //Generate new log record
            Log log = new Log("Edited note of accommodationID " + selectedAccommodation.getMemID());
            logQueries.insertLog(log, hotelOverview.getUserID());
            hotelOverview.refreshLogTable();
            refreshATable(selectedAccommodation);
        }
        //For refocusing the selected member later (because mem gets defocused when a btn is clicked)
        memberTable.getSelectionModel().select(memberTable.getSelectionModel().getFocusedIndex());
    }
//</editor-fold>

    /**
     * To refresh the table.
     */
    //<editor-fold defaultstate="collapsed" desc="refresh tables">
    public void refreshTable(Member selectedMember) {   //REDISPLAY ITEMS AFTER REFRESH
        memberData.clear(); //Clears table
        memberData.addAll(memberQueries.getMembers()); //Repopulates with new data
        showMemberDetails(selectedMember); //Redisplays with new data
    }

    public void refreshTable() {   //REDISPLAY ITEMS AFTER REFRESH
        memberData.clear(); //Clears table
        memberData.addAll(memberQueries.getMembers()); //Repopulates with new data
    }

    public void refreshPWTable(PaiWei selectedPaiWei) {   //REDISPLAY ITEMS AFTER REFRESH
        paiWeiData.clear(); //Clears table
        paiWeiData.addAll(paiWeiQueries.getPaiWeis()); //Repopulates with new data
        showPaiWeiDetails(selectedPaiWei); //Redisplays with new data
    }

    public void refreshDonTable(Donation selectedDonation) {   //REDISPLAY ITEMS AFTER REFRESH
        donationData.clear(); //Clears table
        donationData.addAll(donationQueries.getDonations()); //Repopulates with new data
        showDonationDetails(selectedDonation); //Redisplays with new data
    }

    public void refreshATable(Accommodation selectedAccommodation) {   //REDISPLAY ITEMS AFTER REFRESH
        accommodationData.clear(); //Clears table
        accommodationData.addAll(accommodationQueries.getAccommodation()); //Repopulates with new data
        showAccommodationDetails(selectedAccommodation); //Redisplays with new data
    }
//</editor-fold>

    public boolean showEditMemberDialog(Member member) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditMemberDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage memberDialogStage = new Stage();
            memberDialogStage.setTitle("Edit Member");
            memberDialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            memberDialogStage.setScene(scene);

            // Set the person into the controller.
            EditMemberDialogController controller = loader.getController();
            controller.setMemberDialogStage(memberDialogStage);
            controller.setMember(member);

            // Show the dialog and wait until the user closes it
            memberDialogStage.showAndWait();

            return controller.isConfirmClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showNewMemberDialog(Member member) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditMemberDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage memberDialogStage = new Stage();
            memberDialogStage.setTitle("Add New Member");
            memberDialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            memberDialogStage.setScene(scene);

            // Set the person into the controller.
            EditMemberDialogController controller = loader.getController();
            controller.setMemberDialogStage(memberDialogStage);
            controller.setNewMember(member);

            // Show the dialog and wait until the user closes it
            memberDialogStage.showAndWait();

            //Information is saved in handleOk() from EditMemberDialogController
            return controller.isConfirmClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showMemberDetails(Member member) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        if (member != null) {
            memVBox.setOpacity(100); //Show whole inner pai wei accordian pane
            //Show all menu option btns (in all accord panes) with only 'new' option
            pwOptionsBtn.setVisible(true); //Show pw menu options btn (only show 'new' option)
            pwOptionsBtn.getItems().get(1).setVisible(false); //Hide 'edit' btn
            pwOptionsBtn.getItems().get(2).setVisible(false); //Hide 'del' btn
            donOptionsBtn.setVisible(true); //Show pw menu options btn (only show 'new' option)
            donOptionsBtn.getItems().get(1).setVisible(false); //Hide 'edit' btn
            donOptionsBtn.getItems().get(2).setVisible(false); //Hide 'del' btn
            volOptionsBtn.setVisible(true); //Show pw menu options btn (only show 'new' option)
            volOptionsBtn.getItems().get(1).setVisible(false); //Hide 'edit' btn
            volOptionsBtn.getItems().get(2).setVisible(false); //Hide 'del' btn
            aOptionsBtn.setVisible(true); //Show pw menu options btn (only show 'new' option)
            aOptionsBtn.getItems().get(1).setVisible(false); //Hide 'edit' btn
            aOptionsBtn.getItems().get(2).setVisible(false); //Hide 'del' btn
            // Fill the labels with info from the member object.
            memIDLabel.setText(member.getMID());
            cNameLabel.setText(member.getCName());
            fNameLabel.setText(member.getFName());
            lNameLabel.setText(member.getLName());
            genderLabel.setText(member.getGender());
            dobLabel.setText(member.getDob());
            hPhoneLabel.setText(member.getHPhone());
            mobLabel.setText(member.getMob());
            wPhoneLabel.setText(member.getWPhone());
            faxLabel.setText(member.getFax());
            emailLabel.setText(member.getEmail());
            wechatLabel.setText(member.getWechat());
            prefContact.setText(member.getPrefContact());
            lang1.setText(member.getLang1());
            lang2.setText(member.getLang2());
            eduEmp.setText(member.getEduEmp());
            nation.setText(member.getNation());
            address.setText(member.getAddress());
            suburb.setText(member.getSuburb());
            postcode.setText(member.getPostcode());
            state.setText(member.getState());
            dharma.setText(member.getDharma());
            if (member.getDateRefuge().isEqual(LocalDate.parse("9999-01-01", formatter))) {
                dateRefuge.setText("");
            } else {
                dateRefuge.setText(DateUtil.format(member.getDateRefuge()));
            }
            note.setText(member.getNote());
            if (member.getCreateDate().isEqual(LocalDate.parse("9999-01-01", formatter))) {
                createDate.setText("");
            } else {
                createDate.setText(DateUtil.format(member.getCreateDate()));
            }
            if (member.getUpdateOn().isEqual(LocalDate.parse("9999-01-01", formatter))) {
                updateOn.setText("");
            } else {
                updateOn.setText(DateUtil.format(member.getUpdateOn()));
            }
            createBy.setText(member.getCreateBy());
            updateBy.setText(member.getUpdateBy());

            //****** Loads all accordians' tableView with selected member's details *******
            showAccordianDetails(member);
        }
    }

    private Member previousMember = null;

    private void showAccordianDetails(Member member) {
        if (previousMember != member) { //this only load accordian details if it's a different member selected
            previousMember = member;

            //Clears table of old records from previous member
            paiWeiTable.getItems().clear();
            donationTable.getItems().clear();
            accommodationTable.getItems().clear();

            //Hides back all inner panes 
            //************************** TO FIX: add other panes later            
            pwVBox.setOpacity(0); //Inner records within pai wei pane

            //Load up inner Pai Wei tab with records
            paiWeiData.addAll(paiWeiQueries.getAccordionPaiWei(member.getMemID()));
            if (paiWeiData.size() != 0) {
                paiWeiTable.setItems(paiWeiData);
                pwIDColumn.setCellValueFactory(cellData -> cellData.getValue().pwIDProperty());
                pwDateColumn.setCellValueFactory(cellData -> cellData.getValue().updateOnProperty());
                paiWeiTable.getSelectionModel().selectedItemProperty().addListener(
                        (observable, oldValue, newValue) -> showPaiWeiDetails(newValue));

                //Resets hbox visibility and hide message
                pwHBox.setOpacity(100);
                pwMessage.setVisible(false);
            } else { //This member has no records
                //Hide pane, show empty notice message and show options menu button (with only 'new' option)
                pwHBox.setOpacity(0);
                pwMessage.setVisible(true);
                pwOptionsBtn.setVisible(true);
                pwOptionsBtn.getItems().get(1).setVisible(false); //Hide edit btn
                pwOptionsBtn.getItems().get(2).setVisible(false); //Hide del btn
            }

            //Load up inner Donation tab with records
            donationData.addAll(donationQueries.getAccordionDonation(member.getMemID()));
            if (donationData.size() != 0) {
                donationTable.setItems(donationData);
                donIDColumn.setCellValueFactory(cellData -> cellData.getValue().donIDProperty().asObject());
                donDateColumn.setCellValueFactory(cellData -> cellData.getValue().updateOnProperty());
                donationTable.getSelectionModel().selectedItemProperty().addListener(
                        (observable, oldValue, newValue) -> showDonationDetails(newValue));

                //Resets hbox visibility and hide message
                donHBox.setOpacity(100);
                donMessage.setVisible(false);
            } else {
                //Hide pane and show message
                donHBox.setOpacity(0);
                donMessage.setVisible(true);
                donOptionsBtn.setVisible(true);
                donOptionsBtn.getItems().get(1).setVisible(false); //Hide edit btn
                donOptionsBtn.getItems().get(2).setVisible(false); //Hide del btn
            }

            //Load up inner Accommodation tab with records
            accommodationData.addAll(accommodationQueries.getAccordionAccommodation(member.getMemID()));
            if (accommodationData.size() != 0) {
                accommodationTable.setItems(accommodationData);
                aIDColumn.setCellValueFactory(cellData -> cellData.getValue().aIDProperty().asObject());
                aDateColumn.setCellValueFactory(cellData -> cellData.getValue().checkInDateProperty());
                accommodationTable.getSelectionModel().selectedItemProperty().addListener(
                        (observable, oldValue, newValue) -> showAccommodationDetails(newValue));

                //Resets hbox visibility and hide message
                aHBox.setOpacity(100);
                aMessage.setVisible(false);
            } else {
                //Hide pane and show message
                aHBox.setOpacity(0);
                aMessage.setVisible(true);
                aOptionsBtn.setVisible(true);
                aOptionsBtn.getItems().get(1).setVisible(false); //Hide edit btn
                aOptionsBtn.getItems().get(2).setVisible(false); //Hide del btn
            }

            //Fill in inner Volunteers tab with data
            Volunteers v = volunteersQueries.getVolunteer(member.getMemID());
            if (v != null) { //means database query returned valid record for this memID, aka isnt null
                showVolunteersDetails(v);

                //Resets hbox visibility and hide message
                volGPane.setOpacity(100);
                volMessage.setVisible(false);
            } else {
                //Hide pane and show message
                volGPane.setOpacity(0);
                volMessage.setVisible(true);
                volOptionsBtn.setVisible(true);
                volOptionsBtn.getItems().get(1).setVisible(false); //Hide edit btn
                volOptionsBtn.getItems().get(2).setVisible(false); //Hide del btn
            }
        }
    }

    private void showPaiWeiDetails(PaiWei paiWei) {
        if (paiWei != null) {
            pwOptionsBtn.getItems().get(1).setVisible(true); //Show edit btn
            pwOptionsBtn.getItems().get(2).setVisible(true); //Show del btn
            pwVBox.setOpacity(100);
            paiWeiID.setText(paiWei.getPwID());
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
            pwNote.setText(paiWei.getNote());
            createdBy.setText(paiWei.getCreatedBy());
            if (paiWei.getCreatedOn().isEqual(LocalDate.parse("9999-01-01", formatter))) {
                createdOn.setText("");
            } else {
                createdOn.setText(DateUtil.format(paiWei.getCreatedOn()));
            }
            pwUpdateBy.setText(paiWei.getUpdateBy());
            if (paiWei.getUpdateOn().isEqual(LocalDate.parse("9999-01-01", formatter))) {
                pwUpdateOn.setText("");
            } else {
                pwUpdateOn.setText(DateUtil.format(paiWei.getUpdateOn()));
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
        }
    }

    private void showDonationDetails(Donation donation) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

        if (donation != null) {
            donOptionsBtn.getItems().get(1).setVisible(true); //Show edit btn
            donOptionsBtn.getItems().get(2).setVisible(true); //Show del btn
            // Fill the labels with info from the donation object.
            donID.setText(Integer.toString(donation.getDonID()));
            donType.setText(donation.getDonType());
            donDharmaService.setText(donation.getDharmaService());
            if (donation.getDate().isEqual(LocalDate.parse("9999-01-01", formatter))) {
                donDate.setText("");
            } else {
                donDate.setText(DateUtil.format(donation.getDate()));
            }
            donPayMethod.setText(donation.getPayMethod());
            donAmtPaid.setText(Double.toString(donation.getAmtPaid()));
            donTotalDon.setText(Double.toString(donation.getTotalDon()));
            if (donation.getPayDate().isEqual(LocalDate.parse("9999-01-01", formatter))) {
                donPayDate.setText("");
            } else {
                donPayDate.setText(DateUtil.format(donation.getPayDate()));
            }
            donBal.setText(Double.toString(donation.getBal()));
            donNote.setText(donation.getNote());
            donCreatedBy.setText(donation.getCreateBy());
            donUpdateBy.setText(donation.getUpdateBy());
            if (donation.getCreateDate().isEqual(LocalDate.parse("9999-01-01", formatter))) {
                donCreatedOn.setText("");
            } else {
                donCreatedOn.setText(DateUtil.format(donation.getCreateDate()));
            }
            if (donation.getUpdateOn().isEqual(LocalDate.parse("9999-01-01", formatter))) {
                donUpdateOn.setText("");
            } else {
                donUpdateOn.setText(DateUtil.format(donation.getUpdateOn()));
            }

        }
    }

    private void showVolunteersDetails(Volunteers volunteers) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        if (volunteers != null) {
            volOptionsBtn.getItems().get(1).setVisible(true); //Show edit btn
            volOptionsBtn.getItems().get(2).setVisible(true); //Show del btn
            // Fill the labels with info from the volunteers object.        
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
        }
    }

    private void showAccommodationDetails(Accommodation accommodation) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        if (accommodation != null) {
            aOptionsBtn.getItems().get(1).setVisible(true); //Show edit btn
            aOptionsBtn.getItems().get(2).setVisible(true); //Show del btn
            // Fill the labels with info from the accommodation object.
            aID.setText(Integer.toString(accommodation.getAID()));
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
            aNote.setText(accommodation.getNote());
            room.setText(accommodation.getRoom());
            gender.setText(accommodation.getGender());
            typeOfID.setText(accommodation.getTypeOfID());
            IDNum.setText(accommodation.getIDNum());
            reason.setText(accommodation.getReason());
            emgContactPerson.setText(accommodation.getEmgContactPerson());
            emgContactNum.setText(accommodation.getEmgContactNum());
            relationship.setText(accommodation.getRelationship());
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<Member> filteredData = new FilteredList<>(memberData, p -> true);

            // Set the filter Predicate whenever the filter changes.
            memberFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(member -> {
                    // If filter text is empty, display all members.
                    if (newValue == null || newValue.isEmpty()) {
                        memberTable.setItems(memberData);
                        return true;
                    }

                    // Compare user ID, first name and last name of every booking with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (member.getFName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    } else if (member.getLName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (member.getMID().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches formatted memID.
                    }
                    return false; // Does not match.
                });
            });

            memberFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
                // Wrap the FilteredList in a SortedList. 
                SortedList<Member> sortedData = new SortedList<>(filteredData);

                // Bind the SortedList comparator to the TableView comparator.
                sortedData.comparatorProperty().bind(memberTable.comparatorProperty());

                // Add sorted (and filtered) data to the table.
                memberTable.setItems(sortedData);
            });

            memberData.addAll(memberQueries.getMembers());
            memberTable.setItems(memberData);
            // Initialize the member table with the three columns.
            memIDColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getMID()));
            cNameColumn.setCellValueFactory(cellData -> cellData.getValue().cNameProperty());
            fNameColumn.setCellValueFactory(cellData -> cellData.getValue().fNameProperty());
            lNameColumn.setCellValueFactory(cellData -> cellData.getValue().lNameProperty());
            accord.setExpandedPane(memberPane);

            //Blank out all inner panes and messages
            hideInnerPane();

            memberTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showMemberDetails(newValue));
        } catch (Exception e) {
            System.out.println("TabMember initilize error!");
            e.printStackTrace();
        }
    }

    private void hideInnerPane() {
        //Blank out all inner panes and messages
        memVBox.setOpacity(0);
        pwHBox.setOpacity(0); //Whole pai wei pane
        pwVBox.setOpacity(0); //Inner records within pai wei pane
        pwMessage.setVisible(false);
        pwOptionsBtn.setVisible(false);
        donHBox.setOpacity(0);
        donMessage.setVisible(false);
        donOptionsBtn.setVisible(false);
        volGPane.setOpacity(0);
        volMessage.setVisible(false);
        volOptionsBtn.setVisible(false);
        aHBox.setOpacity(0);
        aMessage.setVisible(false);
        aOptionsBtn.setVisible(false);
    }

    private void hideInnerPWPane() {
        pwVBox.setOpacity(0); //Inner records within pai wei pane
        pwMessage.setVisible(false);
        pwOptionsBtn.setVisible(false);
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
