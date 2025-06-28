/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDateTime;
import java.sql.Timestamp;

/**
 *
 * @author J. Mortensen
 */
public class Address 
{
    /**
     * Indicates that an address is new and has no database ID assigned yet.
     */
    public static final int ADDRESS_NEW_ID = -1;
    
    /**
     * The ID of the address.
     */
    private SimpleIntegerProperty ID;
    /**
     * Main address info.
     */
    private SimpleStringProperty address;
    /**
     * Secondary address info.
     */
    private SimpleStringProperty address2;
    /**
     * City ID.
     */
    private SimpleIntegerProperty cityID;
    /**
     * The postal code.
     */
    private SimpleStringProperty postalCode;
    /**
     * The phone number.
     */
    private SimpleStringProperty phoneNumber;
    /**
     * The time this entry was created.
     */
    private LocalDateTime createDate;
    /**
     * The name of the user that created this address.
     */
    private SimpleStringProperty createdBy;
    /**
     * The timestamp of this address' last update.
     */
    private Timestamp lastUpdate;
    /**
     * The name of the user that last updated this address.
     */
    private SimpleStringProperty lastUpdatedBy;
    
    /**
     * Standard constructor for an existing address.
     * @param ID the ID of the address.
     * @param address standard address information.
     * @param address2 secondary address information.
     * @param cityID the ID of the city.
     * @param postalCode the postal code.
     * @param phoneNumber the phone number.
     * @param createDate the date this address was created.
     * @param createdBy the user name of the person that created this address.
     * @param lastUpdate the date this address was last updated.
     * @param lastUpdatedBy the user name of the person that last updated this address.
     */
    public Address(int ID, String address, String address2, int cityID, String postalCode, String phoneNumber,
            LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy)
    {
        this.ID = new SimpleIntegerProperty(ID);
        this.address = new SimpleStringProperty(address);
        this.address2 = new SimpleStringProperty(address2);
        this.cityID = new SimpleIntegerProperty(cityID);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.createDate = createDate;
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = new SimpleStringProperty(lastUpdatedBy);
    }
    
    /**
     * Default constructor for a new address.
     */
    public Address()
    {
        this.ID = new SimpleIntegerProperty(ADDRESS_NEW_ID);
    }
    
    /**
     * Returns the ID of the address.
     * @return int
     */
    public int getID()
    {
        return ID.get();
    }
    
    /**
     * Returns primary address info.
     * @return String
     */
    public String getAddress()
    {
        return address.get();
    }
    
    /**
     * Returns secondary address info.
     * @return String
     */
    public String getAddress2()
    {
        return address2.get();
    }
    
    /**
     * Returns the city ID.
     * @return int
     */
    public int getCityID()
    {
        return cityID.get();
    }
    
    /**
     * Returns the postal code.
     * @return String
     */
    public String getPostalCode()
    {
        return postalCode.get();
    }
    
    /**
     * Returns the phone number.
     * @return String
     */
    public String getPhoneNumber()
    {
        return phoneNumber.get();
    }
    
    /**
     * Returns the creation date.
     * @return LocalDateTime
     */
    public LocalDateTime getCreateDate()
    {
        return createDate;
    }
    
    /**
     * Returns the name of the user that created the address.
     * @return String
     */
    public String getCreatedBy()
    {
        return createdBy.get();
    }
    
    /**
     * Returns when the address was last updated.
     * @return Timestamp
     */
    public Timestamp getLastUpdate()
    {
        return lastUpdate;
    }
    
    /**
     * Returns the name of the user that last updated the address.
     * @return String
     */
    public String getLastUpdatedBy()
    {
        return lastUpdatedBy.get();
    }
}