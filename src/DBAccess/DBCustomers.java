package DBAccess;

import Database.DBConnection;
import Model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import DBAccess.DBAddresses;
import java.sql.Timestamp;

/**
 * Contains methods for interacting with the customers database table.
 * @author J. Mortensen
 */
public class DBCustomers 
{    
    /**
     * Deletes a customer from the database with the given customer ID.
     * @param customerID the ID of the customer to delete.
     * @return TRUE if deletion was successful.
     */
    public static boolean DeleteCustomer(int customerID)
    {
        PreparedStatement ps;
        ResultSet rs;
        
        try
        {
            
            rs = DBConnection.GetSQLResultSet("SELECT appointmentId FROM appointment WHERE customerId = " + customerID);
            while(rs.next())
            {
                if(!DBAppointments.DeleteAppointment(rs.getInt("appointmentId")))
                    return false;
            }    
            DeleteCustomerAddress(customerID);
            ps = DBConnection.getConnection().prepareStatement("DELETE FROM customer WHERE customerId = " + customerID);
            ps.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }    
    
    /**
     * Deletes an address from the database that's assigned to the given customer ID.
     * @param customerID the ID of the customer to delete an address for.
     * @return TRUE if deletion was successful.
     */
    private static boolean DeleteCustomerAddress(int customerID)
    {
        ResultSet rs;
        
        try
        {
            rs = DBConnection.GetSQLResultSet("SELECT addressId from customer WHERE customerId = " + customerID);
            while(rs.next())
            {
                if(!DBAddresses.DeleteAddress(rs.getInt("addressId")))
                    return false;
            }      
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Returns a list containing all customers from the database.
     * @return ObservableList
     */
    public static ObservableList<Customer> GetAllCustomers()
    {
        Customer c;
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        ResultSet rs;
        
        try
        {
            rs = DBConnection.GetSQLResultSet("SELECT * FROM customer");
            while(rs.next())
            {
                c = new Customer(rs.getInt("customerId"), rs.getString("customerName"), rs.getInt("addressId"),
                    rs.getBoolean("active"), rs.getTimestamp("createDate").toLocalDateTime(), rs.getString("createdBy"),
                 rs.getTimestamp("lastUpdate"), rs.getString("lastUpdatedBy"));
                customers.add(c);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return customers;
    }
    
    /**
     * Writes an customer to the database, creating a new entry (if no ID assigned)
     * or overwriting the previous one.
     * @param customer the customer to write.
     * @return TRUE if write was successful.
     */
    public static boolean WriteCustomer(Customer customer)
    {
        PreparedStatement ps;
        int index = 0;
        
        try
        {
            if(customer.getID() == Customer.CUSTOMER_NEW_ID)
            {
                ps = DBConnection.getConnection().prepareStatement("INSERT INTO customer(customerName, "
                        + "adressId, active, createDate, createdBy, lastUpdate, lastUpdatedBy)"
                        + " VALUES(?, ?, ?, ?, ?, ?, ?);");
            }
            else
            {
                ps = DBConnection.getConnection().prepareStatement("UPDATE customer "
                    + "SET customerName = ?, addressId = ?, active = ?, createDate = ?, createdBy = ?, "
                    + "lastUpdate = ?, lastUpdatedBy = ?"
                    + " WHERE customerId = " + customer.getID());
            }
            ps.setString(++index, customer.getName());
            ps.setInt(++index, customer.getAddressID());
            ps.setBoolean(++index, customer.getActive());
            ps.setTimestamp(++index, Timestamp.valueOf(customer.getCreateDate()));
            ps.setString(++index, customer.getCreatedBy());
            ps.setTimestamp(++index, customer.getLastUpdate());
            ps.setString(++index, customer.getLastUpdatedBy());
            
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