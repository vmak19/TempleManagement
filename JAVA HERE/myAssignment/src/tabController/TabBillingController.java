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

/**
 * FXML Controller class
 *
 * @author SONY
 */
public class TabBillingController implements Initializable {
    Stage myStage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void gotoEditBillingDialog(ActionEvent event) throws IOException {
       // Create Main page
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

    void setMyStage(Stage primaryStage) {
        this.myStage = primaryStage;
    }
}
