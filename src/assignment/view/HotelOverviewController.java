package assignment.view;

import assignment.MainApp;
import assignment.model.Booking;
import assignment.view.TabBookingController;
import javafx.scene.control.TableView;

public class HotelOverviewController {
    
    // Reference to the main application.
    MainApp mainApp;
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
