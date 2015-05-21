/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.database;

import assignment.model.Assignment;
import assignment.model.Provides;
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
public class ProvidesQueries extends DatabaseQuery{
    PreparedStatement insertProvides = null;
    PreparedStatement getAllServices = null;
    PreparedStatement deleteProvides = null;
    List<ServiceInfo> services;
    ResultSet rs = null;
    
    
    public List<ServiceInfo> getServices() {
        services = new ArrayList<ServiceInfo>();
        openConnection();
        try {
            getAllServices = conn.prepareStatement("select app.SERVICE.SERVICEID, "
                    + "PROVIDEID, REFCODE, ROOMID, SERVICEDESC, COST, CREATEDDATE "
                    + "from app.SERVICE "
                    + "inner join app.PROVIDES "
                    + "on app.SERVICE.SERVICEID = app.PROVIDES.SERVICEID");
            rs = getAllServices.executeQuery();
            while (rs.next()) {
                services.add(
                        new ServiceInfo(rs.getInt("provideID"), rs.getInt("refCode"), 
                                rs.getInt("roomID"), rs.getInt("serviceID"), 
                                rs.getString("serviceDesc"), rs.getDouble("cost"), 
                                rs.getDate("createdDate").toLocalDate()));
            }
            rs.close();
            getAllServices.close();
        } catch (SQLException ex) {
            System.out.println("getServices() error!");
            ex.printStackTrace();
        }
        closeConnection();
        return services;
    }
    
    
    public void insertProvides(Provides toInsert) {
        int returnValue = -1;
        openConnection();

        try {
            insertProvides = conn.prepareStatement("insert into app.provides "
                    + "(refCode, roomID, serviceID, createdDate) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertProvides.setInt(1, toInsert.getRefCode());
            insertProvides.setInt(2, toInsert.getRoomID());
            insertProvides.setInt(3, toInsert.getServiceID());
            insertProvides.setDate(4, toInsert.getCreatedDateToDate());
            insertProvides.executeUpdate();

            rs = insertProvides.getGeneratedKeys();
            rs.next();
            returnValue = rs.getInt(1);
            rs.close(); 
            insertProvides.close();
        } catch (SQLException ex) {
            System.out.println("insertProvides() ERROR!");
            ex.printStackTrace();
        }
        closeConnection();
    }

    public void deleteProvides(ServiceInfo toDelete) {
        openConnection();
        try {
            deleteProvides = conn.prepareStatement("delete from app.provides "
                    + "where provideID = ?");
            deleteProvides.setInt(1, toDelete.getProvideID());
            deleteProvides.execute();
        } catch (SQLException ex) {
            System.out.println("ERROR! deleteProvides()!");
            ex.printStackTrace();
        }
        closeConnection();
    }
    
}
