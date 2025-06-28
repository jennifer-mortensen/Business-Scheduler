package Model;

import java.time.LocalDateTime;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.sql.Timestamp;

/**
 * Contains methods for creating user objects, which contain information
 * as present in the users database.
 * @author J. Mortensen
 */
public class User 
{
    /**
     * Indicates that the given user ID was invalid (i.e. could not be found in
     * the database).
     */
    public static final int USER_ID_INVALID = 0;
    
    /**
     * The ID of the user.
     */
    private SimpleIntegerProperty ID;
    /**
     * The name of the user.
     */
    private SimpleStringProperty name;
    
    //private SimpleStringProperty password; // The database wouldn't be very secure if we stored this in memory!
    
    /**
     * Whether the user is active (and should have access to the database).
     */
    private boolean active;
    /**
     * The date this user was added to the database.
     */
    private LocalDateTime createDate;
    /**
     * The name of the user that added this user to the database.
     */
    private SimpleStringProperty createdBy;
    /**
     * The time this user was last updated in the database.
     */
    private Timestamp lastUpdate;
    /**
     * The name of the user that last updated this user in the database.
     */
    private SimpleStringProperty lastUpdatedBy;
    
    /**
     * Standard user constructor.
     * @param ID the ID of the user.
     * @param name the name of the user.
     * @param active whether the user is active (and should have access to the database).
     * @param createDate the date the user was added to the database.
     * @param createdBy the name of the user that added this user to the database.
     * @param lastUpdate the date this user was last updated in the database.
     * @param lastUpdatedBy the name of the user that last updated this user in the database.
     */
    public User(int ID, String name, /*String password,*/ boolean active, LocalDateTime createDate,
            String createdBy, Timestamp lastUpdate, String lastUpdatedBy)
    {
        this.ID = new SimpleIntegerProperty(ID);
        this.name = new SimpleStringProperty(name);
        this.active = active;
        this.createDate = createDate;
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = new SimpleStringProperty(lastUpdatedBy);
    }
    
    /**
     * Abridged user constructor. Populates values for ID and name.
     * @param ID the ID of the user.
     * @param name the name of the user.
     */
    public User(int ID, String name)
    {
        this.ID = new SimpleIntegerProperty(ID);
        this.name = new SimpleStringProperty(name);
    }    
    
    /**
     * Returns the ID of the user.
     * @return int
     */
    public int getID() { return ID.get(); }
    /**
     * Returns the name of the user.
     * @return String
     */
    public String getName() { return name.get(); }    

    // public String getPassword() { return ""; }

    /**
     * Returns whether the user is active (and should have access to the database).
     * @return boolean
     */
    public boolean getActive() { return active; }
    /**
     * Returns the date the user was added to the database.
     * @return LocalDateTime
     */
    public LocalDateTime createDate() { return createDate; }
    /**
     * Returns the name of the user that added this user to the database.
     * @return String
     */
    public String createdBy() { return createdBy.get(); }
    /**
     * Returns the date this user was last updated in the database.
     * @return Timestamp
     */
    public Timestamp lastUpdate() { return lastUpdate; }
    /**
     * Returns the name of the user that last updated this user in the database.
     * @return String
     */
    public String lastUpdatedBy() { return lastUpdatedBy.get(); }
}