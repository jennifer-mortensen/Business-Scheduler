/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DBAccess;

import Database.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.Time;
import Model.Address;
import java.time.LocalDateTime;

/**
 * Contains methods for interacting with the address database table.
 * @author J. Mortensen
 */
public class DBAddresses 
{    
    public static boolean DeleteAddress(int addressID)
    {
        PreparedStatement ps;
        
        try
        {
            ps = DBConnection.getConnection().prepareStatement("DELETE FROM address WHERE addressId = " + addressID);
            ps.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public static ObservableList<Address> GetAllAddresses()
    {
        ObservableList<Address> addresses = FXCollections.observableArrayList();
        ResultSet rs;
        
        try
        {
            rs = DBConnection.GetSQLResultSet("SELECT * FROM address");
            while(rs.next())
            {
                Address a = new Address(rs.getInt("addressId"), rs.getString("address"), rs.getString("address2"),
                    rs.getInt("cityId"), rs.getString("postalCode"), rs.getString("phone"), rs.getTimestamp("createDate").toLocalDateTime(),
                    rs.getString("createdBy"), rs.getTimestamp("lastUpdate"), rs.getString("lastUpdateBy"));
                addresses.add(a);
            }           
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }        
        return addresses;
    }
    
    public static Address GetAddress(int addressID)
    {
        Address address = null;
        ResultSet rs;
        
        try
        {
            rs = DBConnection.GetSQLResultSet("SELECT * FROM address WHERE addressId = " + addressID);
            rs.next();
            address = new Address(rs.getInt("addressId"), rs.getString("address"), rs.getString("address2"),
                rs.getInt("cityId"), rs.getString("postalCode"), rs.getString("phone"), rs.getTimestamp("createDate").toLocalDateTime(),
                rs.getString("createdBy"), rs.getTimestamp("lastUpdate"), rs.getString("lastUpdateBy"));         
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }    
        return address;
    }
    
    public static int GetAddressByCreateDate(LocalDateTime createDate)
    {
        ResultSet rs;
        
        try
        {
            rs = DBConnection.GetSQLResultSet("SELECT addressId FROM address WHERE createDate = " + createDate);
            rs.next();
            return rs.getInt("addressId");           
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return -1;
    }
    
    public static boolean WriteAddress (Address address)
    {
        PreparedStatement ps;
        int index = 0;
        
        try
        {
            if(address.getID() == Address.ADDRESS_NEW_ID)
            {
                ps = DBConnection.getConnection().prepareStatement(
                "INSERT INTO address(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy)"
                        + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);");
            }
            else
            {
                ps = DBConnection.getConnection().prepareStatement("UPDATE appointments "
                        + "SET address = ?, address2 = ?, cityId = ?, postalCode = ?, phone = ?, createDate = ?, createdBy = ? "
                        + "lastUpdate = ?, lastUpdateBy = ? "
                        + " WHERE adressId = " + address.getID());
            }
            
            ps.setString(++index, address.getAddress());
            ps.setString(++index, address.getAddress2());
            ps.setInt(++index, address.getCityID());
            ps.setString(++index, address.getPostalCode());
            ps.setString(++index, address.getPhoneNumber());
            ps.setTimestamp(++index, Timestamp.valueOf(address.getCreateDate()));
            ps.setString(++index, address.getCreatedBy());
            ps.setTimestamp(++index, address.getLastUpdate());
            ps.setString(++index, address.getLastUpdatedBy());  
            
            ps.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}