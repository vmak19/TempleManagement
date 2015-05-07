package assignment.view;

import assignment.MainApp;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class HotelOverviewController implements Initializable {
    
    @FXML TabBookingController tabBookingController;
    
    MainApp mainApp;
    
    public HotelOverviewController() {}
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        
        //Add observable list data to the table
        tabBookingController.bookingTable.setItems(mainApp.getBookingData());
    }
    
    public void initialize(URL url, ResourceBundle rb) {
    
    }
}
