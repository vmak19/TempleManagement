/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.model;

import java.sql.Date;
import java.time.LocalDate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author SONY
 */
public class ServiceInfo {
    
    private IntegerProperty provideID;
    private IntegerProperty refCode;
    private IntegerProperty roomID;
    private IntegerProperty serviceID;
    private StringProperty serviceDesc;
    private DoubleProperty cost;
    private ObjectProperty<LocalDate> createdDate;
    
    public ServiceInfo(int provideID, int refCode, int roomID, int serviceID, 
            String serviceDesc, double cost, LocalDate createdDate) {
        this.provideID = new SimpleIntegerProperty(provideID);
        this.refCode = new SimpleIntegerProperty(refCode);
        this.roomID = new SimpleIntegerProperty(roomID);
        this.serviceID = new SimpleIntegerProperty(serviceID);
        this.serviceDesc = new SimpleStringProperty(serviceDesc);
        this.cost = new SimpleDoubleProperty(cost);
        this.createdDate = new SimpleObjectProperty(createdDate);
    }
    
    // ProvideID
    public int getProvideID() {
        return provideID.get();
    }

    public IntegerProperty provideIDProperty() {
        return provideID;        
    }
    
    // RefCode
    public int getRefCode() {
        return refCode.get();
    }
    
    public IntegerProperty refCodeProperty() {
        return refCode;        
    }
    
    // Room ID
    public int getRoomID() {
        return roomID.get();
    }
    
    public IntegerProperty roomIDProperty() {
        return roomID;        
    }
    
    // ServiceID
    public int getServiceID() {
        return serviceID.get();
    }
    
    public IntegerProperty serviceIDProperty() {
        return serviceID;        
    } 

    // ServiceDesc
    public String getServiceDesc() {
        return serviceDesc.get();
    }

    public StringProperty serviceDescProperty() {
        return serviceDesc;
    }

    public double getCost() {
        return cost.get();
    }

    public DoubleProperty costProperty() {
        return cost;        
    }
    
    // Created Date
    public LocalDate getCreatedDate() {
        return createdDate.get();
    }
    
    public ObjectProperty<LocalDate> createdDateProperty() {
        return createdDate;
    }
    
    // Covert LocalDate to Date
    public Date getCreatedDateToDate() {
        LocalDate localDate = getCreatedDate();
        Date date = Date.valueOf(localDate);
        return date;
    }
}
