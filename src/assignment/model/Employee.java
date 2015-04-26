/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author SONY
 */
public class Employee {
    
    // Why use StringProperty instead of String
    // Why is all variable Final
    private final StringProperty password;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final boolean isAdministrator;

    // Why have input as String while variable's nature are SrtingProperty
    // Why equal to new... put not the direct inputs
    // Why use SimpleStringProperty but not StringProperty or String
    public Employee(String password, 
            String firstName, String lastName, boolean isAdministrator) {
        this.password = new SimpleStringProperty(password);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.isAdministrator = isAdministrator;
    }
    
    public String password() {
        return password.get();
    }
    
    public void setPassword(String password) {
        this.password.set(password);
    }
    
    public StringProperty passwordProperty() {
        return password;
    }
    
    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }
    
    public boolean isAdministrator() {
        return isAdministrator;
    }
}
