/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.model.Employee;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author SONY
 */
public class TabEmployeeController extends HotelOverviewController {
    @FXML public TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, String> userIDColumn;
    @FXML private TableColumn<Employee, String> empFirstNameColumn;
    @FXML private TableColumn<Employee, String> empLastNameColumn;

    @FXML private Label userIDLabel;
    @FXML private Label empFirstNameLabel;
    @FXML private Label empLastNameLabel;
    @FXML private Label passwordLabel;
    @FXML private Label administratorLabel;

    /**
     * Initializes the controller class.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        userIDColumn.setCellValueFactory(cellData -> cellData.getValue().userIDProperty());
        empFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().empFirstNameProperty());
        empLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().empLastNameProperty());
        
        // Add observable list data to the table
        //employeeTable.setItems(mainApp.getBookingData());
    }    
    
}
