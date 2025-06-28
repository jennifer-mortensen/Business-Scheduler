package Model.Reports;

import Database.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.Time;

/**
 * Processes the monthly additions by user report.
 * J. Mortensen
 */
public class MonthlyAdditionsByUser 
{
    /**
     * Queries the database and returns a set of table rows for the monthly
     * additions by user report.
     * @return ObservableList
     */
    public static ObservableList<MonthlyAdditionsByUserRow> GetReport() 
    { 
        int userID;
        String userName;
        int newCustomers;
        int newAppointments;
        ObservableList<MonthlyAdditionsByUserRow> rows = FXCollections.observableArrayList();   
        ResultSet rs;
        
        try
        {
            rs = DBConnection.GetSQLResultSet(""
                    + "SELECT user.userId, users.userName, COUNT(customer.customerId) as 'New Customers', COUNT(appointment.appointmentId) as 'New Appointments' "
                    + "FROM user "
                    + "LEFT JOIN customer ON user.userName = customer.createdBy AND MONTH(customer.createDate) = MONTH(CURRENT_DATE()) "
                    + "LEFT JOIN appointment ON user.userName = appointment.createdBy AND MONTH(appointment.createDate) = MONTH(CURRENT_DATE()) "
                    + "GROUP BY user.userId" );
            while(rs.next())
            {
                userID = rs.getInt("userId");
                userName = rs.getString("userName");
                newCustomers = rs.getInt("New Customers");
                newAppointments = rs.getInt("New Appointments");
                rows.add(new MonthlyAdditionsByUserRow(userID, userName, newCustomers, newAppointments));     
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return rows;
    }   
}