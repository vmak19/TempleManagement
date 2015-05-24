/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.MainApp;
import assignment.database.BillingQueries;
import assignment.model.Billing;
import assignment.model.BookingInfo;
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

    MainApp mainApp;
    private Billing billing;
    private ObservableList<Billing> billingData = FXCollections.observableArrayList();
    public BillingQueries billingQueries = new BillingQueries();

    public ObservableList<Billing> getBillingData() {
        return billingData;
    }

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public TabBillingController() {
    }    

    public void showBillingDetails(Billing billing) {
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
     * To refresh the table.
     */
    public void refreshTable() {
        billingData.clear();
        billingData.addAll(billingQueries.getBillings());  
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
