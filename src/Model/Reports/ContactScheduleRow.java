package Model.Reports;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Contains data for a single row in a contact schedule report.
 * @author J. Mortensen
 */
public class ContactScheduleRow 
{
    /**
     * The contact name for the table row.
     */
    private SimpleStringProperty contactName;
    /**
     * The appointment ID for the table row.
     */
    private SimpleIntegerProperty appointmentID;
    /**
     * The title for the given appointment.
     */
    private SimpleStringProperty title;
    /**
     * The type of the given appointment.
     */
    private SimpleStringProperty type;
    /**
     * The description of the given appointment.
     */
    private SimpleStringProperty description;
    /**
     * The start date and time for the given appointment.
     */
    private SimpleStringProperty startDateAndTime;
    /**
     * The end date and time for the given appointment.
     */
    private SimpleStringProperty endDateAndTime;
    /**
     * The customer ID for the given appointment.
     */
    private SimpleIntegerProperty customerID;

    /**
     * Default ContactScheduleRow constructor.
     * @param contactName the contact name for the table row.
     * @param appointmentID the appointment ID for the table row.
     * @param title the title of the given appointment.
     * @param type the type of the given appointment.
     * @param description the description of the given appointment.
     * @param startDateAndTime the start date and time of the given appointment.
     * @param endDateAndTime the end date and time of the given appointment.
     * @param customerID the customer ID for the given appointment.
     */
    public ContactScheduleRow(String contactName, int appointmentID, String title, String type,
        String description, String startDateAndTime, String endDateAndTime, int customerID)
    {
        this.contactName = new SimpleStringProperty(contactName);
        this.appointmentID = new SimpleIntegerProperty(appointmentID);
        this.title = new SimpleStringProperty(title);
        this.type = new SimpleStringProperty(type);
        this.description = new SimpleStringProperty(description);
        this.startDateAndTime = new SimpleStringProperty(startDateAndTime);
        this.endDateAndTime = new SimpleStringProperty(endDateAndTime);
        this.customerID = new SimpleIntegerProperty(customerID);
    }

    /**
     * Returns the contact name for the table row.
     * @return String
     */
    public String getContactName() { return contactName.get(); }
    /**
     * Returns the appointment ID for the table row.
     * @return int
     */
    public int getAppointmentID() { return appointmentID.get(); }
    /**
     * Returns the title of the given appointment.
     * @return String
     */
    public String getTitle() { return title.get(); }
    /**
     * Returns the type of the given appointment.
     * @return String
     */
    public String getType() { return type.get(); }
    /**
     * Returns the description of the given appointment.
     * @return String
     */
    public String getDescription() { return description.get(); }
    /**
     * Returns the start date and time of the given appointment.
     * @return String
     */
    public String getStartDateAndTime() { return startDateAndTime.get(); }
    /**
     * Returns the end date and time of the given appointment.
     * @return String
     */
    public String getEndDateAndTime() { return endDateAndTime.get(); }
    /**
     * Returns the customer ID for the given appointment.
     * @return int
     */
    public int getCustomerID() { return customerID.get(); }
}
