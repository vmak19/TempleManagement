package assignment.view;

import assignment.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HotelOverviewController implements Initializable {

    @FXML
    TabMemberController tabMemberController;
    @FXML
    TabPaiWeiController tabPaiWeiController;    
    @FXML
    TabDonationController tabDonationController;
    @FXML
    TabAccommodationController tabAccommodationController;
    @FXML
    TabVolunteersController tabVolunteersController;
    @FXML
    TabEmployeeController tabEmployeeController;
    @FXML
    TabLogController tabLogController;
    
    @FXML
    private Button logoutBtn;

    MainApp mainApp;
    private int userID;
    private Stage primaryStage;

    @FXML
    private void switchToLoginScreen(ActionEvent event) throws IOException {

        if (event.getSource() == logoutBtn) {
            //get reference to the button's stage  
            mainApp.showLoginScreen();
        }
    }

    public HotelOverviewController() {
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        try {
            this.mainApp = mainApp;

            tabEmployeeController.setMainApp(mainApp, this);
            tabLogController.setMainApp(mainApp, this);
            tabMemberController.setMainApp(mainApp, this);
            tabPaiWeiController.setMainApp(mainApp, this);
            tabDonationController.setMainApp(mainApp, this);
            //tabVolunteersController.setMainApp(mainApp, this);
            //tabAccommodationController.setMainApp(mainApp, this);
            
        } catch (Exception e) {
            System.out.println("ERROR! setMainApp!");
            e.printStackTrace();
        }
    }
        
    public void refreshPaiWeiTable() {
        tabPaiWeiController.refreshTable();
    }
        
    public void refreshLogTable() {
        tabLogController.refreshTable();
    }     

    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    public int getUserID() {
        return userID;
    }
    
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}