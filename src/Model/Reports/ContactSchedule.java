package Model.Reports;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import util.Time;

/**
 * Processes the contact schedule report.
 * @author J. Mortensen
 */
public class ContactSchedule 
{
    /**
     * Queries the database and returns a set of table rows for the contact
     * schedule report.
     * @return ObservableList
     */
    public static ObservableList<ContactScheduleRow> GetReport() 
    { 
        String contactName;
        int appointmentID;
        String title;
        String type;
        String description;
        String startDateAndTime;
        String endDateAndTime;
        int customerID;        
        ObservableList<ContactScheduleRow> rows = FXCollections.observableArrayList();   
        ResultSet rs;
        
        try
        {
            rs = DBConnection.GetSQLResultSet("SELECT contact, appointmentId, title, type, description, start, end, customerId FROM appointment "
                + "WHERE start >= '" + Timestamp.valueOf(LocalDateTime.now()) + "' ORDER BY contact, start;");    
            while(rs.next())
            {
                contactName = rs.getString("contact");
                appointmentID = rs.getInt("appointmentId");
                title = rs.getString("title");
                type = rs.getString("type");
                description = rs.getString("description");
                startDateAndTime = Time.FormatDateTime(rs.getTimestamp("start").toLocalDateTime());
                endDateAndTime = Time.FormatDateTime(rs.getTimestamp("end").toLocalDateTime());
                customerID = rs.getInt("customerId");
                rows.add(new ContactScheduleRow(contactName, appointmentID, title, type, description, startDateAndTime, endDateAndTime, customerID));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return rows;
    }   
}