package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Contains methods for city objects, which contain information
 * as present in the city database.
 * @author J. Mortensen
 */
public class City 
{
    /**
     * The ID of the city.
     */    
    private SimpleIntegerProperty ID;
    /**
     * The name of the city.
     */
    private SimpleStringProperty name;    
    /**
     * The ID of the city's parent country.
     */
    private SimpleIntegerProperty countryID;
    /**
     * The date the city was added to the database.
     */
    private LocalDateTime createDate;
    /**
     * The name of the user that added the city to the database.
     */
    private SimpleStringProperty createdBy;
    /**
     * The date the city was last updated in the database.
     */
    private Timestamp lastUpdate;
    /**
     * The name of the user that last updated the city in the database.
     */
    private SimpleStringProperty lastUpdatedBy;

    /**
     * Standard city constructor.
     * @param ID the ID of the city.
     * @param name the name of the city.
     * @param countryID the ID of the city's parent country.
     * @param createDate the date the city was added to the database.
     * @param createdBy the name of the user that added the city to the database.
     * @param lastUpdate the date the city was last updated in the database.
     * @param lastUpdatedBy the name of hte user that last updated the city in the database.
     */
    public City(int ID, String name, int countryID, LocalDateTime createDate, String createdBy,
            Timestamp lastUpdate, String lastUpdatedBy)
    {
        this.ID = new SimpleIntegerProperty(ID);
        this.name = new SimpleStringProperty(name);
        this.countryID = new SimpleIntegerProperty(countryID);
        this.createDate = createDate;
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = new SimpleStringProperty(lastUpdatedBy);              
    }
    
    /**
     * Returns the ID of the first level division.
     * @return int
     */
    public int getID() { return ID.get(); }
    /**
     * Returns the name of the first level division.
     * @return String
     */
    public String getName() { return name.get(); }    
    /**
     * Returns the ID of the first level division's parent country.
     * @return int
     */
    public int getCountryId() { return countryID.get(); }
    /**
     * Returns the date the city was added to the database.
     * @return LocalDateTime
     */
    public LocalDateTime getCreateDate() { return createDate; }
    /**
     * Returns the name of the user that added the city to the database.
     * @return String
     */
    public String getCreatedBy() { return createdBy.get(); }
    /**
     * Returns the date the city was last updated in the database.
     * @return Timestamp
     */
    public Timestamp lastUpdate() { return lastUpdate; }
    /**
     * Returns the name of the user that last updated the city in the database.
     * @return 
     */
    public String lastUpdatedBy() { return lastUpdatedBy.get(); }
    
    /**
     * Override for first level division display in a combo box.
     * @return String
     */
    @Override
    public String toString() { return name.get(); }     
}
