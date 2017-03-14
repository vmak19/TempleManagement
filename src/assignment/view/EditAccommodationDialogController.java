/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.view;

import assignment.database.AccommodationQueries;
import assignment.model.Accommodation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Mak
 */
public class EditAccommodationDialogController {

    @FXML
    public DatePicker dobDatePickerField;
    @FXML
    public DatePicker refugeDatePickerField;
    @FXML
    private Label newMemLabel;
    @FXML
    private Label memIDLabel; //Replaces newMemLabel (when edit is chosen) with current mem name
    @FXML
    private Label labelMemIDLabel;
    @FXML
    private Label cNameLabel;
    @FXML
    private Label fNameLabel;
    @FXML
    private Label lNameLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label dobLabel;
    @FXML
    private Label hPhoneLabel;
    @FXML
    private Label mobLabel;
    @FXML
    private Label wPhoneLabel;
    @FXML
    private Label faxLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label wechatLabel;
    @FXML
    private Label prefContact;
    @FXML
    private Label lang;
    @FXML
    private Label skill;
    @FXML
    private Label edu;
    @FXML
    private Label employment;
    @FXML
    private Label nation;
    @FXML
    private Label address;
    @FXML
    private Label suburb;
    @FXML
    private Label postcode;
    @FXML
    private Label state;
    @FXML
    private Label dharma;
    @FXML
    private Label dateRefuge;
    @FXML
    private Label note;
    @FXML
    private Label createDate;
    @FXML
    private Label updateOn;
    @FXML
    private Label createBy;
    @FXML
    private Label updateBy;

    @FXML
    private TextField cNameField;
    @FXML
    private TextField fNameField;
    @FXML
    private TextField lNameField;
    @FXML
    private TextField genderField;
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
    private TextField prefContactField;
    @FXML
    private TextField langField;
    @FXML
    private TextField skillField;
    @FXML
    private TextField eduField;
    @FXML
    private TextField employmentField;
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

    private Accommodation accommodation;
    private boolean okClicked = false;
    private boolean confirmClicked = false;
    private Stage accommodationDialogStage;
    private AccommodationQueries accommodationQueries = new AccommodationQueries();

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
    public void setAccommodationDialogStage(Stage accommodationDialogStage) {
        this.accommodationDialogStage = accommodationDialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param accommodation
     */
    public void setNewAccommodation(Accommodation accommodation) {
        try {
            this.accommodation = accommodation;

            labelMemIDLabel.setText("");
            memIDLabel.setText("");

        } catch (Exception e) {
            System.out.println("Set new accommodation labels error!");
            e.printStackTrace();
        }

    }

    public void setAccommodation(Accommodation accommodation) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");/*
        try {
            this.accommodation = accommodation;
            newMemLabel.setText("");
            memIDLabel.setText(accommodation.pkIDFormatter());
            cNameField.setText(accommodation.getCName());
            fNameField.setText(accommodation.getFName());
            lNameField.setText(accommodation.getLName());
            genderField.setText(accommodation.getGender());
            //### Replaces datepickerField as empty/null if date is 9999-01-01 ###
            if (accommodation.getDob().isEqual(LocalDate.parse("9999-01-01", formatter))) {
                dobDatePickerField.setValue(null);
            } else {
                dobDatePickerField.setValue(accommodation.getDob());
            }
            hPhoneField.setText(accommodation.getHPhone());
            mobField.setText(accommodation.getMob());
            wPhoneField.setText(accommodation.getWPhone());
            faxField.setText(accommodation.getFax());
            emailField.setText(accommodation.getEmail());
            wechatField.setText(accommodation.getWechat());
            prefContactField.setText(accommodation.getPrefContact());
            langField.setText(accommodation.getLang());
            skillField.setText(accommodation.getSkill());
            eduField.setText(accommodation.getEdu());
            employmentField.setText(accommodation.getEmployment());
            nationField.setText(accommodation.getNation());
            addressField.setText(accommodation.getAddress());
            suburbField.setText(accommodation.getSuburb());
            postcodeField.setText(accommodation.getPostcode());
            stateField.setText(accommodation.getState());
            dharmaField.setText(accommodation.getDharma());
            //### Replaces datepickerField as empty/null if date is 9999-01-01 ###
            if (accommodation.getDateRefuge().isEqual(LocalDate.parse("9999-01-01", formatter))) {
                refugeDatePickerField.setValue(null);
            } else {
                refugeDatePickerField.setValue(accommodation.getDob());
            }
            noteField.setText(accommodation.getNote());

        } catch (Exception e) {
            System.out.println("Set edit accommodation labels error!");
            e.printStackTrace();
        }*/
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
    private void handleOk() {/*
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

        //Sets special cases, mainly dealing with empty date fields
        switch (isInputValid()) {
            case 4:
                //DOB was empty, set to 99/99/9999
                accommodation.setDob(LocalDate.parse("9999-01-01", formatter));
                System.out.println("accommodation.getDob(): " + accommodation.getDob());
                break;
            case 3:
                //refuge date empty, set to 99/99/9999
                accommodation.setDateRefuge(LocalDate.parse("9999-01-01", formatter));
                break;
            case 2:
                //refuge and DOB empty, set to 99/99/9999
                accommodation.setDob(LocalDate.parse("9999-01-01", formatter));
                accommodation.setDateRefuge(LocalDate.parse("9999-01-01", formatter));
                break;
            case 0:
                //Form wasn't okay, break out
                System.out.println("Edit Accommodation dialog ==> click 'ok' isnt okay");
                return;
            default:
                //Date fields aren't empty, is okay to store value from form fields
                System.out.println("dobDatePickerField.getValue(): " + dobDatePickerField.getValue());
                accommodation.setDob(dobDatePickerField.getValue());
                accommodation.setDateRefuge(refugeDatePickerField.getValue());
                break;
        }

        //Sets other default fields (doesn't include any date picker fields)
        accommodation.setCName(cNameField.getText());
        accommodation.setLName(lNameField.getText());
        accommodation.setFName(fNameField.getText());
        accommodation.setGender(genderField.getText());
        accommodation.setHPhone(hPhoneField.getText());
        accommodation.setMob(mobField.getText());
        accommodation.setWPhone(wPhoneField.getText());
        accommodation.setFax(faxField.getText());
        accommodation.setEmail(emailField.getText());
        accommodation.setWechat(wechatField.getText());
        accommodation.setPrefContact(prefContactField.getText());
        accommodation.setLang(langField.getText());
        accommodation.setSkill(skillField.getText());
        accommodation.setEdu(eduField.getText());
        accommodation.setEmployment(employmentField.getText());
        accommodation.setNation(nationField.getText());
        accommodation.setAddress(addressField.getText());
        accommodation.setSuburb(suburbField.getText());
        accommodation.setPostcode(postcodeField.getText());
        accommodation.setState(stateField.getText());
        accommodation.setDharma(dharmaField.getText());
        accommodation.setNote(noteField.getText());

        okClicked = true;
        confirmClicked = true;*/
        accommodationDialogStage.close();
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        accommodationDialogStage.close();
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
        }
        if (fNameField.getText() == null || fNameField.getText().length() == 0) {
            //errorMessage += "No valid first name!\n";
        }
        if (cNameField.getText() == null || cNameField.getText().length() == 0) {
            //errorMessage += "No valid Chinese Name!\n";
            cNameField.setText("");
        }
        if (lNameField.getText() == null || lNameField.getText().length() == 0) {
            //errorMessage += "No valid last name!\n";
            lNameField.setText("");
        }
        if (dobDatePickerField.getValue() == null) {
            //errorMessage += "No valid date of birth!\n";
            dobDatePickerField.setValue(null);
        }
        if (prefContactField.getText() == null || prefContactField.getText().length() == 0) {
            //errorMessage += "No valid preferred contact method!\n";
            prefContactField.setText("");
        }
        if (refugeDatePickerField.getValue() == null) {
            //errorMessage += "No valid date of refuge!\n";
            refugeDatePickerField.setValue(null);
        }

        if (dobDatePickerField.getValue() == null) {
            return 4; //DOB field is empty, tell method to set DOB to 99/99/9999    <==to later set GUI field to empty
        } else if (refugeDatePickerField.getValue() == null) {
            return 3; //refuge date field is empty, tell method to set field to 99/99/9999    <==to later set GUI field to empty
        } else if (dobDatePickerField.getValue() == null && refugeDatePickerField.getValue() == null) {
            return 2; //DOB & refuge is empty, tell method to set to 99/99/9999    <==to later set GUI field to empty
        } else if (errorMessage.length() == 0) {
            return 1; //Everything is okay
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(accommodationDialogStage);
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
    }

    public boolean isConfirmClicked() {
        return confirmClicked;
    }
}
