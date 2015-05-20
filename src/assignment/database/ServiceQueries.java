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
    PreparedStatement getAllServices = null;
    PreparedStatement deleteService = null;
    List<Service> serviceList;
    List<ServiceInfo> services;
    ResultSet rs = null;
    
    public List<Service> getServiceList() {
        serviceList = new ArrayList<Service>();
        openConnection();
        try {
            getServiceList = conn.prepareStatement("select * from service");
            rs = getServiceList.executeQuery();
            while (rs.next()) {
                serviceList.add(
                        new Service(rs.getInt("serviceID"), rs.getString("serviceDesc")));
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
    
    public List<ServiceInfo> getServices() {
        services = new ArrayList<ServiceInfo>();
        openConnection();
        try {
            getAllServices = conn.prepareStatement("select app.SERVICE.SERVICEID, "
                    + "PROVIDEID, app.PROVIDES.REFCODE, ROOMID, SERVICEDESC, COST "
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
    
    
    public void deleteService(ServiceInfo toDelete) {
        openConnection();
        try {
            deleteService = conn.prepareStatement("delete from app.provides "
                    + "where provideID = ?");
            deleteService.setInt(1, toDelete.getProvideID());
            deleteService.execute();
        } catch (SQLException ex) {
            System.out.println("ERROR! deleteService()!");
            ex.printStackTrace();
        }
        closeConnection();
    }
    
}
