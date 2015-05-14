/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.model;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author z5018077
 */
public class Billing {
    
    private IntegerProperty amountPaid;
    private IntegerProperty amountDue;
    private ObjectProperty<LocalDate> date;

    public Billing(IntegerProperty amountPaid, IntegerProperty amountDue, ObjectProperty<LocalDate> date) {
        this.amountPaid = amountPaid;
        this.amountDue = amountDue;
        this.date = date;
    }

    public IntegerProperty getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(IntegerProperty amountPaid) {
        this.amountPaid = amountPaid;
    }

    public IntegerProperty getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(IntegerProperty amountDue) {
        this.amountDue = amountDue;
    }

    public ObjectProperty<LocalDate> getDate() {
        return date;
    }

    public void setDate(ObjectProperty<LocalDate> date) {
        this.date = date;
    }
    
    
}
