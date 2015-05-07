/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import editController.EditBillingDialogController;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author SONY
 */
public class TabBillingController implements Initializable {
    @FXML
    private Button editBtn;
    @FXML
    private Button btn2;
    
    Stage myStage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void goToEditBillingDialog(ActionEvent event) throws IOException {    
        // Create edit billing dialog
        Stage newStageForEditBillingDialog = new Stage();
        newStageForEditBillingDialog.setTitle("Edit Billing");
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("EditBillingDialog.fxml"));
        Pane newPaneForEditBillingDialog = myLoader.load();
        Scene newSceneForEditBillingDialog = new Scene(newPaneForEditBillingDialog);
        newStageForEditBillingDialog.setScene(newSceneForEditBillingDialog);

        // Tell main page about my stage
        EditBillingDialogController editBillingDialogController = myLoader.getController();
        editBillingDialogController.setBackToTabBillingStage(myStage);
        editBillingDialogController.setMyStage(newStageForEditBillingDialog);

        // Close window of login in
        myStage.hide();

        // Display main page
        newStageForEditBillingDialog.show(); 
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        if (event.getSource() == editBtn) {
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("EditBillingDialog.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Edit");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(editBtn.getScene().getWindow());
            stage.showAndWait();
        } else {
            stage = (Stage) btn2.getScene().getWindow();
            stage.close();
        }
    }
}
