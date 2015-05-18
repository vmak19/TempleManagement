/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.model;

import java.time.LocalDate;
import java.sql.Date;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author z5018077
 */
public class Billing {

    private IntegerProperty billingID;
    private DoubleProperty amountPaid;
    private DoubleProperty amountDue;
    private ObjectProperty<LocalDate> date;

    public Billing(int billingID, double amountPaid, double amountDue, LocalDate date) {
        this.billingID = new SimpleIntegerProperty(billingID);
        this.amountPaid = new SimpleDoubleProperty(amountPaid);
        this.amountDue = new SimpleDoubleProperty(amountDue);
        this.date = new SimpleObjectProperty(date);
    }

    public int getBillingID() {
        return billingID.get();
    }

    public void setBillingID(int billingID) {
        this.billingID.set(billingID);
    }

    public IntegerProperty billingIDProperty() {
        return billingID;        
    }
    
    public double getAmountPaid() {
        return amountPaid.get();
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid.set(amountPaid);
    }

    public DoubleProperty amountPaidProperty() {
        return amountPaid;
    }

    public double getAmountDue() {
        return amountDue.get();
    }

    public void setAmountDue(double amountDue) {
        this.amountDue.set(amountDue);
    }

    public DoubleProperty amountDueProperty() {
        return amountDue;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }
    
    // Covert LocalDate to Date
    public Date getDateToDate() {
        LocalDate localDate = getDate();
        Date date = Date.valueOf(localDate);
        return date;
    }
}
