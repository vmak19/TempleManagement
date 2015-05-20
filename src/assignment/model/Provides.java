/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author SONY
 */
public class Provides {
    
    private IntegerProperty provideID;
    private IntegerProperty refCode;
    private IntegerProperty serviceID;
    
    public Provides(int provideID, int refCode, int serviceID) {
        this.provideID = new SimpleIntegerProperty(provideID);
        this.refCode = new SimpleIntegerProperty(refCode);
        this.serviceID = new SimpleIntegerProperty(serviceID);
    }
    
    public Provides(int refCode, int serviceID) {
        this.refCode = new SimpleIntegerProperty(refCode);
        this.serviceID = new SimpleIntegerProperty(serviceID);
    }
    
    public int getProvideID() {
        return provideID.get();
    }

    public void setProvideID(int provideID) {
        this.provideID.set(provideID);
    }
    
    public IntegerProperty provideIDProperty() {
        return provideID;        
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
    
    public int getServiceID() {
        return serviceID.get();
    }

    public void setServiceID(int serviceID) {
        this.serviceID.set(serviceID);
    }
    
    public IntegerProperty serviceIDProperty() {
        return serviceID;        
    }
}
