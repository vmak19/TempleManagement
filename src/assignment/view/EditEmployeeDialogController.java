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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Mak
 */
public class EditEmployeeDialogController {

    @FXML
    private TextField passwordField;
    @FXML
    private TextField fNameField;
    @FXML
    private TextField lNameField;
    @FXML
    private CheckBox administratorBox;

    private Stage dialogStage;
    private Employee employee;
    private boolean okClicked = false;
    private boolean confirmClicked = false;
    private Stage employeeDialogStage;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param employee
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;

        passwordField.setText(employee.getPassword());
        fNameField.setText(employee.getEmpFirstName());
        lNameField.setText(employee.getEmpLastName());
        if (employee.getIsAdministrator()) {
            administratorBox.setSelected(true);
        } else {
            administratorBox.setSelected(false);
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
            if (employee.getIsAdministrator()) {
                administratorBox.setSelected(true);
            } else {
                administratorBox.setSelected(false);
            }

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
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
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    
    public void setEmployeeDialogStage(Stage employeeDialogStage) {
        this.employeeDialogStage = employeeDialogStage;
    }
    
    public boolean isConfirmClicked() {
        return confirmClicked;
    }
}
