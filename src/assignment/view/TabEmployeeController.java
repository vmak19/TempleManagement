/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.MainApp;
import assignment.database.EmployeeQueries;
import assignment.database.LogQueries;
import assignment.database.LoginQueries;
import assignment.model.Employee;
import assignment.model.Log;
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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    @FXML
    private Button editBtn;
    @FXML
    private Button newBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button adminBtn;
    @FXML
    private TextField employeeFilterField;

    HotelOverviewController hotelOverview;
    MainApp mainApp;
    private ObservableList<Employee> employeeData = FXCollections.observableArrayList();
    private List<Employee> isAdmin;
    private EmployeeQueries employeeQueries = new EmployeeQueries();
    private LoginQueries loginQueries = new LoginQueries();
    private LogQueries logQueries = new LogQueries();
    private LoginScreenController loginScreenController = new LoginScreenController();

    public ObservableList<Employee> getEmployeeData() {
        return employeeData;
    }

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public TabEmployeeController() {
    }

    @FXML
    private void showEmployeeBtns() {
        isAdmin = loginQueries.getAdminDetails(hotelOverview.getUserID());
        boolean adminStatus = isAdmin.get(0).getIsAdministrator();
        if (adminStatus != false) {
            editBtn.setVisible(true);
            newBtn.setVisible(true);
            deleteBtn.setVisible(true);
            adminBtn.setVisible(false);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Not An Administrator");
            alert.setHeaderText("You do not have administrator privileges");
            alert.setContentText("This option is unavailable to you.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteEmployee() {
        try {
            int selectedIndex = employeeTable.getSelectionModel().getSelectedIndex();
            Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();

            if (selectedIndex >= 0) {
                // Delete record in the database
                employeeQueries.deleteEmployee(employeeTable.getSelectionModel().getSelectedItem());
                // Delete record on the table
                employeeTable.getItems().remove(selectedIndex);
                // Generate new log record
                Log log = new Log("Deleted Employee: " + selectedEmployee.getEmpFirstName()
                        + " " + selectedEmployee.getEmpLastName());
                logQueries.insertLog(log, hotelOverview.getUserID());
                hotelOverview.refreshLogTable();
            } else {
                // Nothing selected.
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("No Selection");
                alert.setHeaderText("No Employee Selected");
                alert.setContentText("Please select an employee in the table.");

                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("Error! handleDeleteEmployee()!");
        }
    }

    @FXML
    private void handleNewEmployee() {
        Employee tempEmployee = new Employee("", "", "", false);
        boolean okClicked = showNewEmployeeDialog(tempEmployee);
        if (okClicked) {
            employeeQueries.insertEmployee(tempEmployee);
            employeeTable.getItems().add(tempEmployee);
        }
        // Generate new log record
        Log log = new Log("Added New Employee: " + tempEmployee.getEmpFirstName()
                + " " + tempEmployee.getEmpLastName());
        logQueries.insertLog(log, hotelOverview.getUserID());
        hotelOverview.refreshLogTable();
    }

    @FXML
    private void handleEditEmployee() {
        try {
            Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();

            if (selectedEmployee != null) {
                boolean okClicked = showEditEmployeeDialog(selectedEmployee);
                if (okClicked) {
                    employeeQueries.updateEmployee(selectedEmployee);
                    hotelOverview.refreshLogTable();
                }
                //Generate new log record
                Log log = new Log("Edited Employee of userID " + selectedEmployee.getUserID());
                logQueries.insertLog(log, hotelOverview.getUserID());
                hotelOverview.refreshLogTable();
            } else {
                // Nothing selected.
                Alert alert = new Alert(AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("No Selection");
                alert.setHeaderText("No Employee Selected");
                alert.setContentText("Please select an employee in the table.");

                alert.showAndWait();
            }

        } catch (Exception e) {
            System.out.println("handleEditEmployee() error");
        }

    }

    /**
     * To refresh the table.
     */
    public void refreshTable(Employee selectedEmployee) {
        employeeData.clear();
        employeeData.addAll(employeeQueries.getEmployees());
    }

    public boolean showEditEmployeeDialog(Employee employee) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditEmployeeDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage employeeDialogStage = new Stage();
            employeeDialogStage.setTitle("Edit Employee");
            employeeDialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            employeeDialogStage.setScene(scene);

            // Set the person into the controller.
            EditEmployeeDialogController controller = loader.getController();
            controller.setEmployeeDialogStage(employeeDialogStage);
            controller.setEmployee(employee);

            // Show the dialog and wait until the user closes it
            employeeDialogStage.showAndWait();

            if (controller.isConfirmClicked()) {
                controller.getUpdatedEmployeeDetails();
            }

            return controller.isConfirmClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showNewEmployeeDialog(Employee employee) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditEmployeeDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage employeeDialogStage = new Stage();
            employeeDialogStage.setTitle("Add New Employee");
            employeeDialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            employeeDialogStage.setScene(scene);

            // Set the person into the controller.
            EditEmployeeDialogController controller = loader.getController();
            controller.setEmployeeDialogStage(employeeDialogStage);
            controller.setNewEmployee(employee);

            // Show the dialog and wait until the user closes it
            employeeDialogStage.showAndWait();

            if (controller.isConfirmClicked()) {
                controller.getUpdatedEmployeeDetails();
            }
            return controller.isConfirmClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showEmployeeDetails(Employee employee) {
        if (employee != null) {
            // Fill the labels with info from the employee object.
            userIDLabel.setText(Integer.toString(employee.getUserID()));
            passwordLabel.setText(employee.getPassword());
            empFirstNameLabel.setText(employee.getEmpFirstName());
            empLastNameLabel.setText(employee.getEmpLastName());
            administratorBox.setVisible(true);
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
            administratorBox.setVisible(false);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<Employee> filteredData = new FilteredList<>(employeeData, p -> true);

            // Set the filter Predicate whenever the filter changes.
            employeeFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(employee -> {
                    // If filter text is empty, display all employees.
                    if (newValue == null || newValue.isEmpty()) {
                        employeeTable.setItems(employeeData);
                        return true;
                    }

                    // Compare user ID, first name and last name of every booking with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (Integer.toString(employee.getUserID()).contains(lowerCaseFilter)) {
                        return true; // Filter matches user ID.
                    } else if (employee.getEmpFirstName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    } else if (employee.getEmpLastName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    }
                    return false; // Does not match.
                });
            });

            employeeFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
                // Wrap the FilteredList in a SortedList. 
                SortedList<Employee> sortedData = new SortedList<>(filteredData);

                // Bind the SortedList comparator to the TableView comparator.
                sortedData.comparatorProperty().bind(employeeTable.comparatorProperty());

                // Add sorted (and filtered) data to the table.
                employeeTable.setItems(sortedData);
            });

            employeeData.addAll(employeeQueries.getEmployees());
            employeeTable.setItems(employeeData);

            // Initialize the employee table with the two columns.
            userIDColumn.setCellValueFactory(cellData -> cellData.getValue().userIDProperty().asObject());
            empFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().empFirstNameProperty());
            empLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().empLastNameProperty());

            //Hide new, edit and delete buttons
            editBtn.setVisible(false);
            newBtn.setVisible(false);
            deleteBtn.setVisible(false);

            // Clear employee details.
            showEmployeeDetails(null);

            employeeTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showEmployeeDetails(newValue));
        } catch (Exception e) {
            System.out.println("TabEmployee initilize error!");
        }
    }

    /**
     * Is called by hotel overview controller to give a reference back to the
     * main application.
     */
    public void setMainApp(MainApp mainApp, HotelOverviewController hotelOverview) {
        this.mainApp = mainApp;
        this.hotelOverview = hotelOverview;
    }
}
