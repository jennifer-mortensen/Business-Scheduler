package DBAccess;

import Database.DBConnection;
import Model.Appointment;
import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.Time;

/**
 * Contains methods for interacting with the appointments database table.
 * @author J. Mortensen
 */
public class DBAppointments 
{
    /**
     * Deletes an appointment from the database with the given appointment ID.
     * @param appointmentID the ID of the appointment to delete.
     * @return TRUE if deletion was successful.
     */
    public static boolean DeleteAppointment(int appointmentID)
    {
        PreparedStatement ps;
        
        try
        {
            ps = DBConnection.getConnection().prepareStatement("DELETE FROM appointments WHERE Appointment_ID = " + appointmentID);
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
     * Returns a list containing all appointments from the database.
     * @return ObservableLis
     */
    public static ObservableList<Appointment> GetAllAppointments()
    {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        ResultSet rs;
        
        try
        {
            rs = DBConnection.GetSQLResultSet("SELECT * FROM appointment");
            while(rs.next())
            {
                Appointment a = new Appointment(rs.getInt("appointmentId"), rs.getInt("customerId"), rs.getInt("userId"), rs.getString("title"),
                        rs.getString("description"), rs.getString("location"), rs.getString("contact"), rs.getString("type"), 
                        rs.getTimestamp("start").toLocalDateTime(), rs.getTimestamp("end").toLocalDateTime(), 
                        rs.getTimestamp("createDate").toLocalDateTime(), rs.getString("createdBy"), rs.getTimestamp("lastUpdate"),
                        rs.getString("lastUpdatedBy"));             
                appointments.add(a);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return appointments;
    }

    /**
     * Writes an appointment to the database, creating a new entry (if no ID assigned)
     * or overwriting the previous one.
     * @param appointment the appointment to write.
     * @return TRUE if write was successful.
     */
    public static boolean WriteAppointment(Appointment appointment)
    {
        PreparedStatement ps;
        int index = 0;
        
        try
        {
            if(appointment.getID() == Appointment.APPOINTMENT_NEW_ID)
            {
                ps = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO appointment(customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy)"
                    + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            }
            else
            {
                ps = DBConnection.getConnection().prepareStatement("UPDATE appointment "
                    + "SET customerId = ?, userId = ?, title = ?, description = ?, location = ?, contact = ?, type = ?, url = ?, start = ?, end = ?, createDate = ?, createdBy = ?, lastUpdate = ?, lastUpdateBy = ? "
                    + "WHERE appointmentId = " + appointment.getID());
            }
            ps.setInt(++index, appointment.getCustomerID());            
            if(appointment.getUserID() != User.USER_ID_INVALID)
            {
                ps.setInt(++index, appointment.getUserID());                 
            }
            else
            {   
                ps.setNull(++index, Types.INTEGER);
            }            
            ps.setString(++index, appointment.getTitle());         
            ps.setString(++index, appointment.getDescription());
            ps.setString(++index, appointment.getLocation());
            ps.setString(++index, appointment.getContact());
            ps.setString(++index, appointment.getType());     
            ps.setNull(++index, Types.VARCHAR); //Unused value.
            ps.setTimestamp(++index, Timestamp.valueOf(appointment.getStartDateTime()));
            ps.setTimestamp(++index, Timestamp.valueOf(appointment.getEndDateTime()));           
            ps.setTimestamp(++index, Timestamp.valueOf(appointment.getCreateDate()));  
            ps.setString(++index, appointment.getCreatedBy());
            ps.setTimestamp(++index, appointment.getLastUpdate());
            ps.setString(++index, appointment.getLastUpdatedBy());
       
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