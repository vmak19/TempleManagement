/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.MainApp;
import assignment.database.BillingQueries;
import assignment.model.Billing;
import assignment.util.DateUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 *
 * @author Mak
 */
public class TabBillingController implements Initializable {
    @FXML
    TableView<Billing> billingTable;
    @FXML
    private TableColumn<Billing, Integer> refCodeColumn;
    @FXML
    private TableColumn<Billing, String> amountPaidColumn;
    @FXML
    private TableColumn<Billing, String> amountDueColumn;

    @FXML
    private Label refCodeLabel;
    @FXML
    private Label amountPaidLabel;
    @FXML
    private Label amountDueLabel; 
    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;

    MainApp mainApp;
    private Billing billing;
    private ObservableList<Billing> billingData = FXCollections.observableArrayList();
    private BillingQueries billingQueries = new BillingQueries();

    public ObservableList<Billing> getBillingData() {
        return billingData;
    }

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public TabBillingController() {
    }

    
    @FXML
    private void handleDeleteBilling() {
        try {
            int selectedIndex = billingTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                // Delete record in the database
                billingQueries.deleteBilling(billingTable.getSelectionModel().getSelectedItem());

                // Delete record on the table
                billingTable.getItems().remove(selectedIndex);
            } else {
                // Nothing selected.
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("No Selection");
                alert.setHeaderText("No Billing Selected");
                alert.setContentText("Please select an billing in the table.");

                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("Error! handleDeleteBilling()!");
        }
    }
    
    @FXML
    private void handleEditBilling() {
        Billing selectedBilling = billingTable.getSelectionModel().getSelectedItem();
        //System.out.println("Selected row is: "+selectedBilling);             //SIM TESTING      
        if (selectedBilling != null) {
            boolean okClicked = showEditBillingDialog(selectedBilling);
            if (okClicked) {
                showBillingDetails(selectedBilling);
            }
        } else {
            
             // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);            
            System.out.println("Mainapp is: "+mainApp);             //SIM TESTING            
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Billing Selected");
            alert.setContentText("Please select a billing in the table.");

            alert.showAndWait();

        }
    }

    public boolean showEditBillingDialog(Billing billing) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditBillingDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage billingDialogStage = new Stage();
            billingDialogStage.setTitle("Edit Billing");
            billingDialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            billingDialogStage.setScene(scene);

            // Set the person into the controller.
            EditBillingDialogController controller = loader.getController();
            controller.setBillingDialogStage(billingDialogStage);
            System.out.println("setting billing "+billing);
            controller.setBilling(billing);

            // Show the dialog and wait until the user closes it
            billingDialogStage.showAndWait();

            return controller.isConfirmClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showBillingDetails(Billing billing) {
        if (billing != null) {
            // Fill the labels with info from the billing object.
            refCodeLabel.setText(Integer.toString(billing.getRefCode()));            
            amountPaidLabel.setText(Double.toString(billing.getAmountPaid()));
            amountDueLabel.setText(Double.toString(billing.getAmountDue()));
        } else {
            // Billing is null, remove all the information.
            refCodeLabel.setText("");
            amountPaidLabel.setText("");
            amountDueLabel.setText("");
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            billingQueries = new BillingQueries();
            billingData.addAll(billingQueries.getBillings());
            
            billingTable.setItems(billingData);
            
            // Initialize the person table with the two columns.
            refCodeColumn.setCellValueFactory(cellData -> cellData.getValue().refCodeProperty().asObject());
            amountPaidColumn.setCellValueFactory(cellData -> cellData.getValue().amountPaidProperty().asString());
            amountDueColumn.setCellValueFactory(cellData -> cellData.getValue().amountDueProperty().asString());

            // Clear booking details.
            showBillingDetails(null);

            billingTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showBillingDetails(newValue));
            
        } catch (Exception e) {
            System.out.println("TabBilling initilize error!");
            e.printStackTrace();
        }
    }
    
    

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
}
