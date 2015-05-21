/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.database;

import assignment.model.BookingInfo;
import assignment.model.Service;
import assignment.model.ServiceInfo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SONY
 */
public class ServiceQueries extends DatabaseQuery{
    PreparedStatement insertService = null;
    PreparedStatement getServiceList = null;
    List<Service> serviceList;
    ResultSet rs = null;
    
    public List<Service> getServiceList() {
        serviceList = new ArrayList<Service>();
        openConnection();
        try {
            getServiceList = conn.prepareStatement("select * from service");
            rs = getServiceList.executeQuery();
            while (rs.next()) {
                serviceList.add(
                        new Service(rs.getInt("serviceID"), 
                                rs.getString("serviceDesc"), rs.getDouble("cost")));
            }
            rs.close();
            getServiceList.close();
        } catch (SQLException ex) {
            System.out.println("getServiceList() error!");
            ex.printStackTrace();
        }
        closeConnection();
        return serviceList;
    }
    
    public void insertService(Service toInsert) {
        int returnValue = -1;
        openConnection();

        try {
            insertService = conn.prepareStatement("insert into app.service "
                    + "(servicedesc, cost) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertService.setString(1, toInsert.getServiceDesc());
            insertService.setDouble(2, toInsert.getCost());
            insertService.executeUpdate();

            rs = insertService.getGeneratedKeys();
            rs.next();
            returnValue = rs.getInt(1);
            rs.close(); 
            insertService.close();
        } catch (SQLException ex) {
            System.out.println("insertService() ERROR!");
            ex.printStackTrace();
        }
        closeConnection();
    }
    
    
    
    
}
