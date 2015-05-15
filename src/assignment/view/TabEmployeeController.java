/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.MainApp;
import assignment.database.EmployeeQueries;
import assignment.model.Employee;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class TabEmployeeController implements Initializable {

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

    MainApp mainApp;

    private ObservableList<Employee> employeeData = FXCollections.observableArrayList();

    private EmployeeQueries employeeQueries;

    public ObservableList<Employee> getEmployeeData() {
        return employeeData;
    }

    public List<Employee> getEmployeesFromFile() {
        List<Employee> employees = new ArrayList<Employee>();
        try {

            // Open the file
            Scanner scanner = new Scanner(new File("resources/employees.csv"));

            //for all lines in file
            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                String[] userID = s.split(",");
                String[] password = s.split(",");
                String[] fname = s.split(",");
                String[] lname = s.split(",");
                String[] admin = s.split(",");

                Employee newEmployee = new Employee(
                        Integer.parseInt(userID[0]),
                        password[1],
                        fname[2],
                        lname[3],
                        Boolean.parseBoolean(admin[4])
                );

                employees.add(newEmployee);
            }

            //create room
            //add to list
            // Close the file
            scanner.close();

        } catch (FileNotFoundException ex) {
            System.out.println("getEmployeesFromFile() Error!");
            ex.printStackTrace();
        }
        return employees;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            employeeQueries = new EmployeeQueries();

            employeeData.addAll(getEmployeesFromFile());
            employeeTable.setItems(employeeData);

            // Initialize the person table with the two columns.
            userIDColumn.setCellValueFactory(cellData -> cellData.getValue().userIDProperty().asObject());
            empFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().empFirstNameProperty());
            empLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().empLastNameProperty());

            // Clear booking details.
            showEmployeeDetails(null);

            employeeTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showEmployeeDetails(newValue));
            // Add observable list data to the table
            //employeeTable.setItems(mainApp.getBookingData());
            System.out.println("TabEmployee initialized!");
        } catch (Exception e) {
            System.out.println("TabEmployee initilize error!");
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

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
