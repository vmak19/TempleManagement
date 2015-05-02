/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SONY
 */
public class EditBillingDialogController implements Initializable {
    
    private Stage tabBillingStage;
    private Stage myStage;
    private Button goToTabBilling;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     @FXML
    public void goBackToTabBilling (ActionEvent e) {
        myStage.close();
        tabBillingStage.show();
    }

    public void setBackToTabBillingStage(Stage myStage) {
        tabBillingStage = myStage;
    }

    public void setMyStage(Stage newStageForEditBillingDialogPage) {
        myStage = newStageForEditBillingDialogPage;
    }
}
