/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author z5018077
 */
public class Service {
    
    private IntegerProperty serviceID;
    private StringProperty serviceDesc;
    private DoubleProperty cost;

    public Service(int serviceID, String serviceDesc, double cost) {
        this.serviceID = new SimpleIntegerProperty(serviceID);
        this.serviceDesc = new SimpleStringProperty(serviceDesc);
        this.cost = new SimpleDoubleProperty(cost);
    }
    
    public Service(int serviceID, String serviceDesc) {
        this.serviceID = new SimpleIntegerProperty(serviceID);
        this.serviceDesc = new SimpleStringProperty(serviceDesc);
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

    public String getServiceDesc() {
        return serviceDesc.get();
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc.set(serviceDesc);
    }
    
    public StringProperty serviceDescProperty() {
        return serviceDesc;
    }

    public double getCost() {
        return cost.get();
    }

    public void setCost(double cost) {
        this.cost.set(cost);
    }
    
    public DoubleProperty costProperty() {
        return cost;        
    } 
    
    @Override
    public String toString() {
        return getServiceDesc();
    }
}
