/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.database.BillingQueries;
import assignment.model.Billing;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author z5018077
 */
public class EditBillingDialogController implements Initializable {

    @FXML
    private Label refCodeLabel;
    @FXML
    public Label amountPaidLabel;
    @FXML
    public Label amountDueLabel;
    @FXML
    public TextField payField;

    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;

    private Billing billing;
    private boolean confirmClicked = false;
    private Stage billingDialogStage;

    public void setData() {
        try {
            refCodeLabel.setText(Integer.toString(billing.getRefCode()));
            amountPaidLabel.setText(Double.toString(billing.getAmountPaid()));
            amountDueLabel.setText(Double.toString(billing.getAmountDue()));
        } catch (Exception e) {
            System.out.println("Set edit billing labels error:");
            e.printStackTrace();
        }
    }

    /**
     * Called when the user clicks confirm.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            TabBillingController tabBillingController = new TabBillingController();
            int myRefCode = Integer.parseInt(refCodeLabel.getText());
            double myPaymentAmount = Integer.parseInt(payField.getText());
            
            tabBillingController.billingQueries.updateBilling(myRefCode, myPaymentAmount);
            
            confirmClicked = true;
            billingDialogStage.close();
        }
    }

    @FXML
    public void handleCancel() {
        billingDialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (payField.getText() == null || payField.getText().length() == 0) {
            errorMessage += "No valid payment amount!\n";
        } else {
            // Try to parse number of payment amount into an int.
            try {
                Integer.parseInt(payField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid payment amount (must be an integer)!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(billingDialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
    
    /**
     * Returns true if the user clicked OK, false otherwise.
     */
    public boolean isConfirmClicked() {
        return confirmClicked;
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param bookingDialogStage
     */
    public void setBillingDialogStage(Stage billingDialogStage) {
        this.billingDialogStage = billingDialogStage;
    }

    /**
     * Is called by tab billing controller.
     */
    public void setBilling(Billing billing) {
        this.billing = billing;
        System.out.println("Get billing: "+billing);
        setData();
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
    }
}
