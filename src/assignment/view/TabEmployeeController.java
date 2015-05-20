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

    MainApp mainApp;
    private ObservableList<Employee> employeeData = FXCollections.observableArrayList();
    private EmployeeQueries employeeQueries = new EmployeeQueries();

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
    private void handleDeleteEmployee() {
        try {
            int selectedIndex = employeeTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                // Delete record in the database
                employeeQueries.deleteEmployee(employeeTable.getSelectionModel().getSelectedItem());

                // Delete record on the table
                employeeTable.getItems().remove(selectedIndex);
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
        Employee tempEmployee = new Employee();
        boolean okClicked = showEditEmployeeDialog(tempEmployee);
        if (okClicked) {
            employeeQueries.insertEmployee(tempEmployee);
            employeeData.add(tempEmployee);
        }
    }

    @FXML
    private void handleEditEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            boolean okClicked = showEditEmployeeDialog(selectedEmployee);
            if (okClicked) {
                showEmployeeDetails(selectedEmployee);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Employee Selected");
            alert.setContentText("Please select an employee in the table.");

            alert.showAndWait();
        }
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            employeeQueries = new EmployeeQueries();

            employeeData.addAll(employeeQueries.getEmployees());
            employeeTable.setItems(employeeData);

            // Initialize the employee table with the two columns.
            userIDColumn.setCellValueFactory(cellData -> cellData.getValue().userIDProperty().asObject());
            empFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().empFirstNameProperty());
            empLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().empLastNameProperty());

            // Clear employee details.
            showEmployeeDetails(null);

            employeeTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showEmployeeDetails(newValue));
        } catch (Exception e) {
            System.out.println("TabEmployee initilize error!");
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
