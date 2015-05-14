/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author z5018077
 */
public class Service {
    
    private IntegerProperty serviceID;
    private StringProperty serviceDesc;
    private DoubleProperty cost;

    public Service(IntegerProperty serviceID, StringProperty serviceDesc, DoubleProperty cost) {
        this.serviceID = serviceID;
        this.serviceDesc = serviceDesc;
        this.cost = cost;
    }

    public IntegerProperty getServiceID() {
        return serviceID;
    }

    public void setServiceID(IntegerProperty serviceID) {
        this.serviceID = serviceID;
    }

    public StringProperty getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(StringProperty serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public DoubleProperty getCost() {
        return cost;
    }

    public void setCost(DoubleProperty cost) {
        this.cost = cost;
    }
    
    
}
