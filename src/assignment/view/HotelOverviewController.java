package assignment.view;

import assignment.MainApp;
import assignment.model.Booking;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class HotelOverviewController implements Initializable {

    @FXML
    TabBookingController tabBookingController;
    @FXML
    TabEmployeeController tabEmployeeController;
    @FXML
    TabRoomController tabRoomController;
    @FXML
    TabServiceController tabServiceController;
    @FXML
    TabRoomController tabBillingController;
    @FXML
    private Button logoutBtn;

    MainApp mainApp;

    @FXML
    private void switchToLoginScreen(ActionEvent event) throws IOException {

        if (event.getSource() == logoutBtn) {
            //get reference to the button's stage  
            mainApp.showLoginScreen();

           // primaryStage = (Stage) logoutBtn.getScene().getWindow();
            //load up OTHER FXML document
            // showLoginScreen();
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

            tabBookingController.setMainApp(mainApp);
            tabRoomController.setMainApp(mainApp);
            tabServiceController.setMainApp(mainApp);
            tabEmployeeController.setMainApp(mainApp);
        } catch (Exception e) {
            System.out.println("ERROR! setMainApp!");
            e.printStackTrace();
        }
    }

    public void initialize(URL url, ResourceBundle rb) {

    }
}
