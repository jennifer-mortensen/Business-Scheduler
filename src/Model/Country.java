package Model;

import java.time.LocalDateTime;
import java.sql.Timestamp;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Contains methods for creating country objects, which contain information
 * as present in the country database.
 * @author J. Mortensen
 */
public class Country 
{
    /**
     * The ID of the country.
     */
    private SimpleIntegerProperty ID;
    /**
     * The name of the country.
     */
    private SimpleStringProperty name;
    /**
     * Time the country was added to the database.
     */
    private LocalDateTime createDate;
    /**
     * Name of the user that added the country to the database.
     */
    private SimpleStringProperty createdBy;
    /**
     * Last time the country was updated in the database.
     */
    private Timestamp lastUpdate;
    /**
     * Name of the user that last updated the country in the database.
     */
    private SimpleStringProperty lastUpdatedBy;  
    
/**
 * Standard country constructor.
 * @param ID ID of the country.
 * @param name Name of the country.
 * @param createDate Time the country was added to the database.
 * @param createdBy Name of the user that added the country to the database.
 * @param lastUpdate Last time the country was updated in the database.
 * @param lastUpdatedBy Name of the user that last updated the country in the database.
 */
    public Country(int ID, String name, LocalDateTime createDate, String createdBy,
            Timestamp lastUpdate, String lastUpdatedBy)
    {
        this.ID = new SimpleIntegerProperty(ID);
        this.name = new SimpleStringProperty(name);
        this.createDate = createDate;
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = new SimpleStringProperty(lastUpdatedBy);
    }
    
/**
 * Abridged country constructor. Includes just ID and name.
 * @param ID ID of the country.
 * @param name Name of the country.
 */
    public Country(int ID, String name)
    {
        this.ID = new SimpleIntegerProperty(ID);
        this.name = new SimpleStringProperty(name);
    }    
    
    /**
     * Returns the ID of the country.
     * @return int
     */
    public int getID() { return ID.get(); }
    /**
     * Returns the name of the country.
     * @return String
     */
    public String getName() { return name.get(); }
    /**
     * Time the country was added to the database.
     * @return LocalDateTime
     */
    public LocalDateTime getCreateDate() { return createDate; }
    /**
     * Name of the user that added the country to the database.
     * @return String
     */
    public String getCreatedBy() { return createdBy.get(); }
    /**
     * Last time the country was updated in the database.
     * @return Timestamp
     */
    public Timestamp lastUpdate() { return lastUpdate; }
    /**
     * Name of the user that last updated the country in the database.
     * @return String
     */
    public String lastUpdatedBy() { return lastUpdatedBy.get(); }
    
    /**
     * Override for country name display in a combo box.
     * @return String
     */
    @Override
    public String toString() { return name.get(); }     
}
