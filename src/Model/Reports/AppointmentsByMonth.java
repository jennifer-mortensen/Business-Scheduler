package Model.Reports;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import util.Time;

/**
 * Processes the appointments by month report.
 * @author J. Mortensen
 */
public class AppointmentsByMonth
{       
    /**
     * Queries the database and returns a set of table rows for the AppointmentsByMonth
     * report.
     * @return ObservableList
     */
    public static ObservableList<AppointmentsByMonthRow> GetReport() 
    { 
        String month;
        int openAccount;
        int consultation;
        int followUp;
        int other;
        ObservableList<AppointmentsByMonthRow> rows = FXCollections.observableArrayList();   
        
        for(int i = 1; i <= 12; i++)
        {
            month = Time.IntToMonth(i);
            openAccount = GetAppointmentTypeCountByMonth(i, "Open Account");
            consultation = GetAppointmentTypeCountByMonth(i, "Consultation");
            followUp = GetAppointmentTypeCountByMonth(i, "Follow-up");
            other = GetAppointmentTypeCountByMonth(i, "Other");                        
            rows.add(new AppointmentsByMonthRow(month, openAccount, consultation, followUp, other));
        }
        
        return rows;
    }
    
    /**
     * Returns the number of appointments per type for a given month.
     * @param month the index of the month (e.g. January = 1)
     * @param appointmentType value of the appointment type to search for.
     * @return int
     */
    private static int GetAppointmentTypeCountByMonth(int month, String appointmentType)
    {
        return DBConnection.GetNumResults(DBConnection.GetSQLResultSet("SELECT * FROM appointment WHERE MONTH(start) = " 
                + month + " AND type = '" + appointmentType + "';"));
    }
}
