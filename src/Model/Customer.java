package Model;

import DBAccess.DBCountries;
import DBAccess.DBCities;
import java.time.LocalDateTime;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.sql.Timestamp;

/**
 * Contains methods for creating customer objects, which contain information
 * as present in the customer database.
 * @author J. Mortensen
 */
public class Customer 
{    
    /**
     * Indicates that a customer is new and has no database ID assigned yet.
     */
    public static final int CUSTOMER_NEW_ID = -1;    
    
    /**
     * The ID of the customer.
     */
    private SimpleIntegerProperty ID;    
    /**
     * The name of the customer.
     */
    private SimpleStringProperty name;
    /**
     * The ID of the customer's address.
     */
    private SimpleIntegerProperty addressID;
    /**
     * Whether the customer is active.
     */
    private boolean active;
    /**
     * The date that the customer was added to the database.
     */
    private LocalDateTime createDate;
    /**
     * The name of the user that created the customer.
     */    
    private SimpleStringProperty createdBy;    
    /**
     * The date that the customer was last updated in the database.
     */
    private Timestamp lastUpdate;
    /**
     * The name of the user that last updated customer details.
     */
    private SimpleStringProperty lastUpdatedBy;
    /**
     * Returns the customer's address.
     */
    private Address address;
    
    /**
     * Standard customer constructor.
     * @param ID the ID of the customer.
     * @param name the name of the customer.
     * @param addressID the database ID of the customer's address.
     * @param active whether the customer is active.
     * @param createDate the date the customer was added to the database.
     * @param createdBy the name of the user that added the customer to the database.
     * @param lastUpdate the date the customer was last updated in the database.
     * @param lastUpdatedBy the name of the user that last updated the customer in the database.
     */
    public Customer(int ID, String name, int addressID, boolean active, LocalDateTime createDate,
            String createdBy, Timestamp lastUpdate, String lastUpdatedBy)
    {
        this.ID = new SimpleIntegerProperty(ID);
        this.name = new SimpleStringProperty(name);
        this.addressID = new SimpleIntegerProperty(addressID);
        this.active = active;
        this.createDate = createDate;
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = new SimpleStringProperty(lastUpdatedBy);    
        address = null;
    }
    
    /**
     * Returns the ID of the customer.
     * @return int
     */
    public int getID() { return ID.get(); }
    /**
     * Returns the name of the customer.
     * @return String
     */
    public String getName() { return name.get(); }
    /**
     * Returns the database ID of the customer's address.
     * @return String
     */
    public int getAddressID() { return addressID.get(); }
    /**
     * Returns whether the customer is active.
     * @return boolean
     */
    public boolean getActive() { return active; }
    /**
     * Returns the date the customer was added to the database.
     * @return LocalDateTime
     */
    public LocalDateTime getCreateDate() { return createDate; }
    /**
     * Returns the name of the user that created the customer.
     * @return String
     */    
    public String getCreatedBy() { return createdBy.get(); }
    /**
     * Returns the date the customer was last updated in the database.
     * @return Timestamp
     */
    public Timestamp getLastUpdate() { return lastUpdate; }
    /**
     * Returns the name of the user that last updated customer details.
     * @return String
     */
    public String getLastUpdatedBy() { return lastUpdatedBy.get(); }
    
    /**
     * Override for customer name display in a combo box,
     * @return String
     */
    @Override
    public String toString() { return name.get(); } 
}