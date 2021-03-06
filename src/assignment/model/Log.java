package assignment.model;

import java.sql.Date;
import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Log {

    private IntegerProperty logID;
    private IntegerProperty userID;
    private StringProperty empFirstName;
    private StringProperty empLastName;
    //private ObjectProperty<LocalDate> dateMod;
    private StringProperty dateMod;
    private StringProperty itemModified;    //REF vase toString

    public Log(String itemModified) {
        this.logID = new SimpleIntegerProperty(-1);
        this.itemModified = new SimpleStringProperty(itemModified);
    }
    
    //For LogQueries getSessionDetails()
    public Log(int userID, String empFirstName, String empLastName) {
        this.logID = new SimpleIntegerProperty(-1);
        this.userID = new SimpleIntegerProperty(userID);
        this.empFirstName = new SimpleStringProperty(empFirstName);
        this.empLastName = new SimpleStringProperty(empLastName);
    }
    
    public Log(int logID, int userID, String empFirstName, String empLastName, String dateMod, String itemModified) {
        this.logID = new SimpleIntegerProperty(logID);
        this.userID = new SimpleIntegerProperty(userID);
        this.empFirstName = new SimpleStringProperty(empFirstName);
        this.empLastName = new SimpleStringProperty(empLastName);
        this.dateMod = new SimpleStringProperty(dateMod);
        this.itemModified = new SimpleStringProperty(itemModified);
    }

    public int getLogID() {
        return logID.get();
    }

    public void setLogID(int logID) {
        this.logID.set(logID);
    }

    public IntegerProperty logIDProperty() {
        return logID;
    }
    
    public int getUserID() {
        return userID.get();
    }

    public void setUserID(int userID) {
        this.userID.set(userID);
    }

    public IntegerProperty userIDProperty() {
        return userID;
    }

    public String getEmpFirstName() {
        return empFirstName.get();
    }

    public void setEmpFirstName(String empFirstName) {
        this.empFirstName.set(empFirstName);
    }

    public StringProperty empFirstNameProperty() {
        return empFirstName;
    }

    public String getEmpLastName() {
        return empLastName.get();
    }

    public void setEmpLastName(String empLastName) {
        this.empLastName.set(empLastName);
    }

    public StringProperty empLastNameProperty() {
        return empLastName;
    }

    public String getDateMod() {
        return dateMod.get();
    }

    public void setDateMod(String dateMod) {
        this.dateMod.set(dateMod);
    }

    public StringProperty dateModProperty() {
        return dateMod;
    }

    /*public Date getDateModToDate() {
        LocalDate localDate = getDateMod();
        Date date = Date.valueOf(localDate);
        return date;
    }*/

    public String getItemModified() {
        return itemModified.get();
    }

    public void setItemModified(String itemModified) {
        this.itemModified.set(itemModified);
    }

    public StringProperty itemModifiedProperty() {
        return itemModified;
    }
}
