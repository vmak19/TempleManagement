/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.model.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Mak
 */
public class EditEmployeeDialogController {

    @FXML
    private Label userIDLabel;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField fNameField;
    @FXML
    private TextField lNameField;
    @FXML
    private CheckBox administratorBox;
    @FXML
    private Button okBtn;
    @FXML
    private Button cancelBtn;

    private Employee employee;
    private boolean okClicked = false;
    private boolean confirmClicked = false;
    private Stage employeeDialogStage;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */    
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setEmployeeDialogStage(Stage employeeDialogStage) {
        this.employeeDialogStage = employeeDialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param employee
     */
    public void setEmployee(Employee employee) {
        try {
            this.employee = employee;

            userIDLabel.setText(Integer.toString(employee.getUserID()));
            passwordField.setText(employee.getPassword());
            fNameField.setText(employee.getEmpFirstName());
            lNameField.setText(employee.getEmpLastName());
            if (employee.getIsAdministrator()) {
                administratorBox.setSelected(true);
            } else {
                administratorBox.setSelected(false);
            }
        } catch (Exception e) {
            System.out.println("BUG 2: Set edit employee labels error!");
        }

    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            employee.setPassword(passwordField.getText());
            employee.setEmpFirstName(fNameField.getText());
            employee.setEmpLastName(lNameField.getText());
            if (administratorBox.isSelected()) {
                employee.setIsAdministrator(true);
            } else {
                employee.setIsAdministrator(false);
            }
            /*
            if (employee.getIsAdministrator()) {
                administratorBox.setSelected(true);
            } else {
                administratorBox.setSelected(false);
            }*/

            okClicked = true;
            employeeDialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        employeeDialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (passwordField.getText() == null || passwordField.getText().length() == 0) {
            errorMessage += "No valid password!\n";
        }

        if (fNameField.getText() == null || fNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }
        if (lNameField.getText() == null || lNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(employeeDialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    
    public boolean isConfirmClicked() {
        return confirmClicked;
    }
}
