/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author SONY
 */
public class Employee {
    
    private IntegerProperty userID;
    private StringProperty password;
    private StringProperty empFirstName;
    private StringProperty empLastName;
    private BooleanProperty isAdministrator;

    public Employee(int userID, String password, 
            String empFirstName, String empLastName, boolean isAdministrator) {
        this.userID = new SimpleIntegerProperty(userID);
        this.password = new SimpleStringProperty(password);
        this.empFirstName = new SimpleStringProperty(empFirstName);
        this.empLastName = new SimpleStringProperty(empLastName);
        this.isAdministrator = new SimpleBooleanProperty(isAdministrator);
    }
    
    public Employee(String password,String empFirstName, String empLastName, 
            boolean isAdministrator) {        
        this.password = new SimpleStringProperty(password);
        this.empFirstName = new SimpleStringProperty(empFirstName);
        this.empLastName = new SimpleStringProperty(empLastName);
        this.isAdministrator = new SimpleBooleanProperty(isAdministrator);
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
   
    public String getPassword() {
        return password.get();
    }
    
    public void setPassword(String password) {
        this.password.set(password);
    }
    
    public StringProperty passwordProperty() {
        return password;
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
    
    public boolean getIsAdministrator() {
        return isAdministrator.get();
    }
    
    public void setIsAdministrator(boolean isAdministrator) {
        this.isAdministrator.set(isAdministrator);
    }
    
    public BooleanProperty isAdministratorProperty() {
        return isAdministrator;
    }
}
