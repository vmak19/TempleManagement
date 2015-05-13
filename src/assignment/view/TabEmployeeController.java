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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author SONY
 */
public class TabEmployeeController extends HotelOverviewController implements Initializable {

    @FXML
    TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, Integer> userIDColumn;
    @FXML
    private TableColumn<Employee, String> empFirstNameColumn;
    @FXML
    private TableColumn<Employee, String> empLastNameColumn;

    @FXML
    private Label userIDLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label empFirstNameLabel;
    @FXML
    private Label empLastNameLabel;
    @FXML
    private CheckBox administratorBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Initialize the person table with the two columns.
            userIDColumn.setCellValueFactory(cellData -> cellData.getValue().userIDProperty().asObject());
            empFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().empFirstNameProperty());
            empLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().empLastNameProperty());

            employeeTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showEmployeeDetails(newValue));
            // Add observable list data to the table
            //employeeTable.setItems(mainApp.getBookingData());
            System.out.println("TabEmployee initialized!");
        } catch (Exception e) {
            System.out.println("Initilize error!");
        }
    }

    private void showEmployeeDetails(Employee employee) {
        if (employee != null) {
            // Fill the labels with info from the employee object.
            userIDLabel.setText(Integer.toString(employee.getUserID()));
            passwordLabel.setText(employee.getPassword());
            empFirstNameLabel.setText(employee.getEmpFirstName());
            empLastNameLabel.setText(employee.getEmpLastName());
            if (employee.getIsAdministrator()) {
                administratorBox.setSelected(true);
            } else {
                administratorBox.setSelected(false);
            }
        } else {
            // Employee is null, remove all the information.
            userIDLabel.setText("");
            passwordLabel.setText("");
            empFirstNameLabel.setText("");
            empLastNameLabel.setText("");
            administratorBox.setSelected(false);
        }
    }
}
