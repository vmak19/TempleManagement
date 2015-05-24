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

    private IntegerProperty refCode;
    private DoubleProperty amountPaid;
    private DoubleProperty amountDue;

    public Billing() {
    }
    
    public Billing(double amountPaid, double amountDue) {
        this.refCode = new SimpleIntegerProperty(-1);
        this.amountPaid = new SimpleDoubleProperty(amountPaid);
        this.amountDue = new SimpleDoubleProperty(amountDue);
    }

    public Billing(int refCode, double amountPaid, double amountDue) {
        this.refCode = new SimpleIntegerProperty(refCode);
        this.amountPaid = new SimpleDoubleProperty(amountPaid);
        this.amountDue = new SimpleDoubleProperty(amountDue);
    }

    public int getRefCode() {
        return refCode.get();
    }

    public void setRefCode(int refCode) {
        this.refCode.set(refCode);
    }

    public IntegerProperty refCodeProperty() {
        return refCode;
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
}
