package Model.Reports;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Contains data for a single row in a month additions by user report.
 * @author J. Mortensen
 */
public class MonthlyAdditionsByUserRow 
{
    /**
     * The user ID for the table row.
     */
    private SimpleIntegerProperty userID;
    /**
     * The user name of the given user.
     */
    private SimpleStringProperty userName;
    /**
     * New customers added by the given user.
     */
    private SimpleIntegerProperty newCustomers;
    /**
     * New appointments added by the given user.
     */
    private SimpleIntegerProperty newAppointments;
    
    /**
     * Default MonthlyAdditionsByUserRow constructor.
     * @param userID the user ID for the table row.
     * @param userName the user name of the given user.
     * @param newCustomers new customers added by the given user.
     * @param newAppointments new appointments added by the given user.
     */
    public MonthlyAdditionsByUserRow(int userID, String userName, int newCustomers,int newAppointments)
    {
        this.userID = new SimpleIntegerProperty(userID);
        this.userName = new SimpleStringProperty(userName);
        this.newCustomers = new SimpleIntegerProperty(newCustomers);
        this.newAppointments = new SimpleIntegerProperty(newAppointments);
    }
    
    /**
     * Returns the user ID for the table row.
     * @return int
     */
    public int getUserID() { return userID.get(); }
    /**
     * Returns the name of the given user.
     * @return String
     */
    public String getUserName() { return userName.get(); }
    /**
     * Returns number of new customers added by the given user.
     * @return int
     */
    public int getNewCustomers() { return newCustomers.get(); }
    /**
     * Returns number of new appointments added by the given user.
     * @return int
     */
    public int getNewAppointments() { return newAppointments.get(); }
}