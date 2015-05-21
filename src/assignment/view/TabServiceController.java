/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.MainApp;
import assignment.database.BookingQueries;
import assignment.database.ProvidesQueries;
import assignment.database.ServiceQueries;
import assignment.model.Booking;
import assignment.model.BookingInfo;
import assignment.model.Provides;
import assignment.model.ServiceInfo;
import assignment.util.DateUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SONY
 */
public class TabServiceController implements Initializable {
    
    @FXML private TableView<ServiceInfo> serviceTable;
    @FXML private TableColumn<ServiceInfo, Integer> refCodeColumn;
    @FXML private TableColumn<ServiceInfo, String> serviceIDColumn;
    @FXML private TableColumn<ServiceInfo, String> createdDateColumn;
    
    @FXML private Label refCodeLabel;
    @FXML private Label roomIDLabel;
    @FXML private Label serviceIDLabel;
    @FXML private Label costLabel;
    @FXML private Label dateLabel;
    
    @FXML private Button newButton;
    @FXML private Button deleteButton;
    
    MainApp mainApp;

    private ObservableList<ServiceInfo> serviceData = FXCollections.observableArrayList();

    private ServiceQueries serviceQueries = new ServiceQueries();
    private ProvidesQueries providesQueries = new ProvidesQueries();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            /*serviceData.addAll(serviceQueries.getServices());
            serviceTable.setItems(serviceData);

            // Initialize the booking table with the three columns.
            refCodeColumn.setCellValueFactory(cellData -> cellData.getValue().refCodeProperty().asObject());
            serviceIDColumn.setCellValueFactory(cellData -> cellData.getValue().serviceDescProperty());
            createdDateColumn.setCellValueFactory(cellData -> cellData.getValue().createdDateProperty().asString());*/

            // Clear booking details.
            showSeviceDetails(null);

            // Listen for selection changes and show the booking details when changed.
            serviceTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showSeviceDetails(newValue));
        } catch (Exception e) {
            System.out.println("Service Initilize error!");
            e.printStackTrace();
        }
    }    
    
    /**
     * Fills all text fields to show details about the service. If the specified
     * service is null, all text fields are cleared.
     */
    private void showSeviceDetails(ServiceInfo service) {
        if (service != null) {
            // Fill the labels with info from the service object.
            refCodeLabel.setText(Integer.toString(service.getRefCode()));
            roomIDLabel.setText(Integer.toString(service.getRoomID()));
            serviceIDLabel.setText(Integer.toString(service.getServiceID()));
            costLabel.setText(Double.toString(service.getCost()));
            dateLabel.setText(DateUtil.format(service.getCreatedDate()));
        } else {
            // Person is null, remove all the text.
            refCodeLabel.setText("");
            roomIDLabel.setText("");
            serviceIDLabel.setText("");
            costLabel.setText("");
            dateLabel.setText("");
        }
    }
    
    // Called when the user clicks on the delete button.
    @FXML
    private void handleDeleteService() {
        try {
            int selectedIndex = serviceTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                // Delete record in the database
                providesQueries.deleteProvides(serviceTable.getSelectionModel().getSelectedItem());

                // Delete record on the table
                serviceTable.getItems().remove(selectedIndex);
            } else {
                // Nothing selected.
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("No Selection");
                alert.setHeaderText("No Service Selected");
                alert.setContentText("Please select a service in the table.");

                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("Error! handleDeleteService()!");
        }
    }
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewBooking() {
        Provides tempProvide = new Provides();
        boolean okClicked = showEditProvideDialog(tempProvide);
        if (okClicked) {
           providesQueries.insertProvides(tempProvide);
           
           serviceData.clear();
           serviceData.addAll(serviceQueries.getServices());
           //bookingTable.setItems(bookingData);
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
    public boolean showEditProvideDialog(Provides provide) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditServiceDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage editProvideDialogStage = new Stage();
            editProvideDialogStage.setTitle("Edit Service");
            editProvideDialogStage.initModality(Modality.WINDOW_MODAL);
            //bookingDialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            editProvideDialogStage.setScene(scene);

            // Set the person into the controller.
            EditServiceDialogController controller = loader.getController();
            controller.setEditProvideDialogStage(editProvideDialogStage);
            controller.setProvide(provide);
            
            // Show the dialog and wait until the user closes it
            editProvideDialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Is called by hotel overview controller to give a reference back to the
     * main application.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
