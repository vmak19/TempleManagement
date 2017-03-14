/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.database.MemberQueries;
import assignment.model.Member;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 *
 * @author Mak
 */
public class EditMemberDialogController implements Initializable {

    @FXML
    public DatePicker refugeDatePickerField;
    @FXML
    private Label newMemLabel;
    @FXML
    private Label memIDLabel; //Replaces newMemLabel (when edit is chosen) with current mem name
    @FXML
    private Label labelMemIDLabel;

    @FXML
    private TextField cNameField;
    @FXML
    private TextField fNameField;
    @FXML
    private TextField lNameField;
    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private RadioButton genderMBtn;
    @FXML
    private RadioButton genderFBtn;
    @FXML
    public TextField dobField;
    @FXML
    private TextField hPhoneField;
    @FXML
    private TextField mobField;
    @FXML
    private TextField wPhoneField;
    @FXML
    private TextField faxField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField wechatField;
    @FXML
    private ComboBox prefContactComboBox;
    @FXML
    private ComboBox lang1ComboBox;
    @FXML
    private ComboBox lang2ComboBox;
    @FXML
    private TextField eduEmpField;
    @FXML
    private TextField nationField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField suburbField;
    @FXML
    private TextField postcodeField;
    @FXML
    private TextField stateField;
    @FXML
    private TextField dharmaField;
    @FXML
    private TextArea noteField;
    @FXML
    private TextField createDateField;
    @FXML
    private TextField updateOnField;
    @FXML
    private TextField createByField;
    @FXML
    private TextField updateByField;

    @FXML
    private Button okBtn;
    @FXML
    private Button cancelBtn;

    private Member member;
    private boolean okClicked = false;
    private boolean confirmClicked = false;
    private Stage memberDialogStage;
    private MemberQueries memberQueries = new MemberQueries();

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lang1ComboBox.getItems().addAll(
                "中文",
                "英文",
                "粵語",
                "中/英文",
                "越語",
                "其他");
        lang2ComboBox.getItems().addAll(
                "中文",
                "英文",
                "粵語",
                "中/英文",
                "越語",
                "其他");
        prefContactComboBox.getItems().addAll(
                "Email",
                "SMS",
                "WeChat",
                "Phone",
                "其他");
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setMemberDialogStage(Stage memberDialogStage) {
        this.memberDialogStage = memberDialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param member
     */
    public void setNewMember(Member member) {
        try {
            this.member = member;

            labelMemIDLabel.setText("");
            memIDLabel.setText("");

        } catch (Exception e) {
            System.out.println("Set new member labels error!");
            e.printStackTrace();
        }

    }

    public void setMember(Member member) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        try {
            this.member = member;
            newMemLabel.setText("");
            memIDLabel.setText(member.getMID());
            cNameField.setText(member.getCName());
            fNameField.setText(member.getFName());
            lNameField.setText(member.getLName());
            if ((member.getGender() != null && member.getGender().equalsIgnoreCase("m")) 
                    || (member.getGender() != null && member.getGender().equalsIgnoreCase("male"))) {
                genderMBtn.setSelected(true);
            } else {
                genderMBtn.setSelected(false);
            }
            if ((member.getGender() != null && member.getGender().equalsIgnoreCase("f")) 
                    || (member.getGender() != null && member.getGender().equalsIgnoreCase("female"))) {
                genderFBtn.setSelected(true);
            } else {
                genderFBtn.setSelected(false);
            }
            dobField.setText(member.getDob());
            hPhoneField.setText(member.getHPhone());
            mobField.setText(member.getMob());
            wPhoneField.setText(member.getWPhone());
            faxField.setText(member.getFax());
            emailField.setText(member.getEmail());
            wechatField.setText(member.getWechat());
            if (!member.getPrefContact().equals("")) { //if not empty string
                prefContactComboBox.getSelectionModel().select(member.getPrefContact());
            }
            if (!member.getLang1().equals("")) { //if not empty string
                lang1ComboBox.getSelectionModel().select(member.getLang1());
            }
            if (!member.getLang2().equals("")) { //if not empty string
                lang2ComboBox.getSelectionModel().select(member.getLang2());
            }
            eduEmpField.setText(member.getEduEmp());
            nationField.setText(member.getNation());
            addressField.setText(member.getAddress());
            suburbField.setText(member.getSuburb());
            postcodeField.setText(member.getPostcode());
            stateField.setText(member.getState());
            dharmaField.setText(member.getDharma());
            //### Replaces datepickerField as empty/null if date is 9999-01-01 ###
            if (member.getDateRefuge().isEqual(LocalDate.parse("9999-01-01", formatter))) {
                refugeDatePickerField.setValue(null);
            } else {
                refugeDatePickerField.setValue(member.getDateRefuge());
            }
            noteField.setText(member.getNote());

        } catch (Exception e) {
            System.out.println("Set edit member labels error!");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLangComboBox() {
        //Removes 1st option from 2nd combo options
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
     * Called when the user clicks OK.
     */
    @FXML
    private void handleOk() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

        //Sets special cases, mainly dealing with empty date fields
        switch (isInputValid()) {
            case 4:
                //DOB was empty, set to 0
                member.setDob("0");
                break;
            case 3:
                //refuge date empty, set to 99/99/9999
                member.setDateRefuge(LocalDate.parse("9999-01-01", formatter));
                break;
            case 2:
                //refuge and DOB empty, set to 99/99/9999
                member.setDob("0");
                member.setDateRefuge(LocalDate.parse("9999-01-01", formatter));
                break;
            case 0:
                //Form wasn't okay, break out, (but still allow users to continue with form)
                System.out.println("Edit Member dialog ==> click 'ok' isnt okay OR showed name error msg");
                return;
            default:
                //Date fields aren't empty, is okay to store value from form fields
                member.setDob(dobField.getText());
                member.setDateRefuge(refugeDatePickerField.getValue());
                break;
        }

        //Sets other default fields (doesn't include any date picker fields)
        member.setCName(cNameField.getText());
        member.setLName(lNameField.getText());
        member.setFName(fNameField.getText());
        if (genderMBtn.isSelected()) {
            member.setGender("Male");
        } else if (genderFBtn.isSelected()) {
            member.setGender("Female");
        }
        member.setHPhone(hPhoneField.getText());
        member.setMob(mobField.getText());
        member.setWPhone(wPhoneField.getText());
        member.setFax(faxField.getText());
        member.setEmail(emailField.getText());
        member.setWechat(wechatField.getText());
        if (prefContactComboBox.getSelectionModel().isEmpty() || prefContactComboBox.getSelectionModel().getSelectedItem().toString().length() <= 0) {
            member.setPrefContact("");
        } else {
            member.setPrefContact(prefContactComboBox.getSelectionModel().getSelectedItem().toString());
        }
        if (lang1ComboBox.getSelectionModel().isEmpty() || lang1ComboBox.getSelectionModel().getSelectedItem().toString().length() <= 0) {
            member.setLang1("");
        } else {
            member.setLang1(lang1ComboBox.getSelectionModel().getSelectedItem().toString());
        }
        if (lang2ComboBox.getSelectionModel().isEmpty() || lang2ComboBox.getSelectionModel().getSelectedItem().toString().length() <= 0) {
            member.setLang2("");
        } else {
            member.setLang2(lang2ComboBox.getSelectionModel().getSelectedItem().toString());
        }
        member.setEduEmp(eduEmpField.getText());
        member.setNation(nationField.getText());
        member.setAddress(addressField.getText());
        member.setSuburb(suburbField.getText());
        member.setPostcode(postcodeField.getText());
        member.setState(stateField.getText());
        member.setDharma(dharmaField.getText());
        member.setNote(noteField.getText());
        member.setNote(noteField.getText());

        okClicked = true;
        confirmClicked = true;
        memberDialogStage.close();
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        memberDialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    int nameFlag = 0;

    private int isInputValid() {
        String errorMessage = "";

        //1. Either give chinese name OR FULL english name
        if (((cNameField.getText() == null || cNameField.getText().length() == 0) && (fNameField.getText() == null || fNameField.getText().length() == 0))
                || ((cNameField.getText() == null || cNameField.getText().length() == 0) && (lNameField.getText() == null || lNameField.getText().length() == 0))) {
            errorMessage += "Please complete one full name: \n1. Chinese name\nOR \n2. First name + Last name\n";
            nameFlag = 1;

            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(memberDialogStage);
            if (nameFlag == 1) {
                alert.setTitle("No Names");
                nameFlag = 0;
            } else {
                alert.setTitle("Invalid Fields");
            }
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return 0; //Has error
        }

        if (dobField.getText().length() <= 0 && refugeDatePickerField.getValue() == null) {
            return 2; //DOB & refuge is empty, tell method to set to 99/99/9999    <==to later set GUI field to empty
        } else if (dobField.getText().length() <= 0) {
            return 4; //DOB field is empty, tell method to set DOB to 99/99/9999    <==to later set GUI field to empty
        } else if (refugeDatePickerField.getValue() == null) {
            return 3; //refuge date field is empty, tell method to set field to 99/99/9999    <==to later set GUI field to empty
        } else if (errorMessage.length() == 0) {
            return 1; //Everything is okay
        }
        return 0;
    }

    public boolean isConfirmClicked() {
        return confirmClicked;
    }
}
