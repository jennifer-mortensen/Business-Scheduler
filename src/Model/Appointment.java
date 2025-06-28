package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Date;
import java.sql.Timestamp;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import util.Time;

/**
 * Contains methods for creating appointment objects, which contain information
 * as present in the appointments database.
 * @author J. Mortensen
 */
public class Appointment 
{
    /**
     * Indicates that an appointment is new and has no database ID assigned yet.
     */
    public static final int APPOINTMENT_NEW_ID = -1;
  
    /**
     * The ID of the appointment.
     */
    private SimpleIntegerProperty ID;
    /**
     * The ID of the appointment creator.
     */
    private SimpleIntegerProperty userID;    
    /**
     * The ID of the customer the appointment is assigned to.
     */
    private SimpleIntegerProperty customerID;    
    /**
     * The title of the appointment.
     */
    private SimpleStringProperty title;
    /**
     * Description of the appointment.
     */
    private SimpleStringProperty description;
    /**
     * Location of the appointment (e.g. New York).
     */
    private SimpleStringProperty location;
    /**
     * The name of the contact.
     */
    private SimpleStringProperty contact;
    /**
     * The type of appointment (e.g. consultation).
     */
    private SimpleStringProperty type;
    /**
     * Unused.
     */
    private String url;
    /**
     * Appointment start time.
     */    
    private LocalDateTime startTime;
    /**
     * Appointment end time.
     */
    private LocalDateTime endTime;
    /**
     * Date the appointment was created.
     */
    private LocalDateTime createDate;
    /**
     * Creator name of the appointment, i.e. the user that added it to the database.
     */            
    private SimpleStringProperty createdBy;
    /**
     * The last time the appointment was updated.
     */
    private Timestamp lastUpdate;
    /**
     * The last user to update the appointment.
     */
    private SimpleStringProperty lastUpdatedBy;
    /**
     * The appointment start time formatted for display in a table.
     */
    private SimpleStringProperty startTimeDisplay;
    /**
     * The appointment end time formatted for display in a table.
     */
    private SimpleStringProperty endTimeDisplay;
    

    public Appointment(int ID, int customerID, int userID, String title, String description, String location, String contact,
            String type, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime createDate, String createdBy,
            Timestamp lastUpdate, String lastUpdatedBy)
    {
        this.ID = new SimpleIntegerProperty(ID);
        this.customerID = new SimpleIntegerProperty(customerID);
        this.userID = new SimpleIntegerProperty(userID);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleStringProperty(location);
        this.contact = new SimpleStringProperty(contact);
        this.type = new SimpleStringProperty(type);
        this.url = null; // Unused value.
        this.startTime = startTime;
        this.endTime = endTime;
        this.createDate = createDate;
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = new SimpleStringProperty(lastUpdatedBy);
    }
    
    /**
     * Returns the ID of the appointment.
     * @return int
     */
    public int getID() { return ID.get(); }
    /**
     * Returns the ID of the customer the appointment is assigned to.
     * @return int
     */
    public int getCustomerID() { return customerID.get(); }    
    /**
     * Returns the ID of the user that created the appointment.
     * @return int
     */
    public int getUserID() { return userID.get(); }    
    /**
     * Returns the title of the appointment.
     * @return String
     */
    public String getTitle() { return title.get(); }
    /**
     * Returns the description of the appointment.
     * @return String
     */
    public String getDescription() { return description.get(); }
    /**
     * Returns the location of the appointment.
     * @return String
     */
    public String getLocation() { return location.get(); }
    /**
     * Returns the name of the appointment contact.
     * @return String
     */
    public String getContact() { return contact.get(); }
    /**
     * Returns the type of the appointment.
     * @return String
     */
    public String getType() { return type.get(); }
    /**
     * Unused.
     * @return null 
     */
    public String getUrl() { return null; }
    /**
     * Returns the start date and time of the appointment.
     * @return LocalDateTime
     */    
    public LocalDateTime getStartDateTime() { return startTime; }
    /**
     * Returns the end date and time of the appointment.
     * @return LocalDateTime
     */
    public LocalDateTime getEndDateTime() { return endTime; }
    /**
     * Returns the time this appointment was created.
     * @return LocalDateTime
     */
    public LocalDateTime getCreateDate() { return createDate; }
    /**
     * Returns the name of the user that created the appointment,
     * @return  String
     */
    public String getCreatedBy() { return createdBy.get(); }
    /**
     * Returns the last time this address was updated.
     * @return Timestamp
     */
    public Timestamp getLastUpdate() { return lastUpdate; }
    /**
     * Returns the name of the user that last updated the appointment.
     * @return String
     */
    public String getLastUpdatedBy() { return lastUpdatedBy.get(); }
    /**
     * Returns the appointment date.
     * @return LocalDate
     */
    public LocalDate getDate() { return startTime.toLocalDate(); }
    /**
     * Returns the start time of the appointment.
     * @return LocalTime
     */
    public LocalTime getStartTime() { return startTime.toLocalTime(); }
    /**
     * Returns the end time of the appointment.
     * @return LocalTime
     */
    public LocalTime getEndTime() { return endTime.toLocalTime(); }
    /**
     * Returns the start time of the appointment as formatted for table display.
     * @return String
     */
    public String getStartTimeDisplay() { return startTimeDisplay.get(); }
    /**
     * Returns the end time of the appointment as formatted for table display.
     * @return String
     */
    public String getEndTimeDisplay() { return endTimeDisplay.get(); }
}