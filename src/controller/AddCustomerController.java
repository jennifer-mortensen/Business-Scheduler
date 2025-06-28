package controller;

import DBAccess.DBCountries;
import DBAccess.DBAddresses;
import DBAccess.DBCustomers;
import DBAccess.DBCities;
import Database.DBConnection;
import Model.Country;
import Model.Customer;
import Model.City;
import Model.Address;
import controller.CustomersController;
import controller.LoginController;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.MessageBox;
import java.sql.Timestamp;
import javafx.scene.control.CheckBox;

/**
 * FXML Controller class
 * Add/Modify Customer Form
 * @author J. Mortensen
 */
public class AddCustomerController implements Initializable 
{
    /**
     * Stores country IDs for all business locations: Canada, the United Kingdom,
     * and the United States respectively.
     */
    private static final int[] BUSINESS_LOCATIONS = new int[] {38, 230, 231};
    
    /**
     * Main scene variable. 
     */
    private Parent scene;      
    /**
     * Main stage variable.
     */
    private Stage stage;
    /**
     * Customer address.
     */
    Address address;

    @FXML
    private javafx.scene.control.Button btnSave;
    @FXML
    private javafx.scene.control.Button btnCancel;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtAddress2;    
    @FXML
    private TextField txtPostalCode;        
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private Label lblCustomerName;
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblAddress2;
    @FXML
    private Label lblPostalCode;
    @FXML
    private Label lblPhoneNumber;
    @FXML
    private Label lblCountry;
    @FXML
    private Label lblCity;
    @FXML
    private Label lblAddCustomer;
    @FXML
    private Label lblCustomerID;
    @FXML
    private TextField txtCustomerID;
    @FXML
    private AnchorPane root;
    @FXML
    private ComboBox<Country> cmbCountry;
    @FXML
    private ComboBox<City> cmbCity;
    @FXML
    private CheckBox chkActive;
    
/**
 *  Initializes the form, populating decision boxes and loading previous
 *  customer data (if applicable).
 * @param arg0
 * @param arg1 
 */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {        
        Customer selectedCustomer = CustomersController.getSelectedCustomer();
        
        PopulateCountries();      
        if(selectedCustomer != null)
        {
            address = DBAddresses.GetAddress(selectedCustomer.getAddressID());
            lblAddCustomer.setText("Modify Customer");
            txtCustomerName.setText(selectedCustomer.getName());
            txtAddress.setText(address.getAddress());
            txtAddress2.setText(address.getAddress2());
            txtPostalCode.setText(address.getPostalCode());
            txtPhoneNumber.setText(address.getPhoneNumber());
            SelectCountryByID(DBCities.CityIDToCountryID(address.getCityID()));
            SelectCityByID(address.getCityID()); 
            txtCustomerID.setText(String.valueOf(selectedCustomer.getID()));
            if(selectedCustomer.getActive() == true) chkActive.setSelected(true);
        }
        else
        {
            address = new Address();
            cmbCountry.getSelectionModel().selectFirst();
            lblCustomerID.setVisible(false);
            txtCustomerID.setVisible(false);
            txtCustomerID.setText(String.valueOf(Customer.CUSTOMER_NEW_ID));
        }
        HandleSelectCountry();        
    }

    /**
     * Handles the cancel button, which returns to the main customer form.
     */
    @FXML
    private void HandleCancel(javafx.event.ActionEvent event) throws IOException 
    {
        stage = (Stage) root.getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/frmCustomers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();            
    }    
    
    /**
     * Handles the save button, which stores new/updated information in the
     */
    @FXML
    private void HandleSave(javafx.event.ActionEvent event) throws IOException 
    {
        if(!ValidateCustomer()) return;
        
        Customer c;
        int customerID = Integer.valueOf(txtCustomerID.getText());
        int addressID = address.getID();
        ResultSet rs;
        String createdBy = "";
        String updatedBy = LoginController.getUser().getName();
        LocalDateTime createDate = LocalDateTime.now();
        
        if(customerID != Customer.CUSTOMER_NEW_ID)
        {
            rs = DBConnection.GetSQLResultSet("SELECT createdBy, createDate FROM customer WHERE customerId = " + customerID);
            try
            {
                rs.next();
                createdBy = rs.getString("Created_By");
                createDate = rs.getTimestamp("createDate").toLocalDateTime();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }            
        }
        else
        {
            createdBy = LoginController.getUser().getName();
        }
        
        address = new Address(
            addressID,
            txtAddress.getText(),
            txtAddress2.getText(),
            cmbCity.getSelectionModel().getSelectedItem().getID(),
            txtPostalCode.getText(),
            txtPhoneNumber.getText(),
            createDate,
            createdBy,
            Timestamp.valueOf(LocalDateTime.now()),
            updatedBy
        );
        
        if(addressID == Address.ADDRESS_NEW_ID)
            addressID = DBAddresses.GetAddressByCreateDate(createDate);
            
        c = new Customer(
            customerID, 
            txtCustomerName.getText(),
            addressID,
            chkActive.isSelected(),
            createDate,
            createdBy,
            Timestamp.valueOf(LocalDateTime.now()),
            updatedBy
        );
        
        DBCustomers.WriteCustomer(c);
        MessageBox.Show(Alert.AlertType.INFORMATION, "Customers", "Customer Updated", "Customer has been updated successfully.");        
        if(customerID == Customer.CUSTOMER_NEW_ID)
        {
            stage = (Stage) root.getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/frmCustomers.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();                  
        }     
    }

    /**
     * Handles the country selection combo, which updates valid selections in the
     * first level divisions combo fox.
     */
    @FXML
    private void HandleSelectCountry() 
    {
        Country c = cmbCountry.getSelectionModel().getSelectedItem();
        
        cmbCity.setItems(DBCities.GetAllCities(c.getID()));
        cmbCity.getSelectionModel().selectFirst();
        cmbCity.setDisable(cmbCity.getSelectionModel().getSelectedItem() == null ? true : false);
    }
    
    /**
     * Populates the countries combo box with all valid countries (i.e. those with
     * an existing business location).
     */
    private void PopulateCountries()
    {
        ObservableList<Country> countries = FXCollections.observableArrayList();
        ResultSet rs;
        
        for(int countryID : BUSINESS_LOCATIONS)
        {
            rs = DBConnection.GetSQLResultSet("SELECT country FROM country WHERE countryId = " + countryID);
            try
            {
                rs.next();
                countries.add(new Country(countryID, rs.getString("country")));
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }            
        }
        cmbCountry.setItems(countries);
    }    
    
    /**
     * Uses a lambda expression to filter the country list for the given ID.
     * @param ID the ID of the country to search for.
     */
    private void SelectCountryByID(int ID)
    {
        FilteredList<Country> countries = new FilteredList<>(cmbCountry.getItems());
        
        countries.setPredicate(c ->
        {
            return c.getID() == ID;
        });
        cmbCountry.getSelectionModel().select(countries.get(0));
        HandleSelectCountry();
    }
    
    /**
     * Selects the first city in the cities combo box with the given ID. Uses
     * a lamba expression to quickly filter the list.
     * @param ID the ID of the first level division to search for.
     */
    private void SelectCityByID(int ID)
    {
        FilteredList<City> cities = new FilteredList<>(cmbCity.getItems());
        
        cities.setPredicate(c ->
        {
            return c.getID() == ID;
        });   
        cmbCity.getSelectionModel().select(cities.get(0));
    }    
    
    /**
     * Checks to see if all input customer details result in a valid customer
     * and prints an error message if they do not.
     * @return TRUE if the customer is valid.
     */
    private boolean ValidateCustomer()
    {
        if(txtCustomerName.getText().equals(""))
        {
            MessageBox.Show(Alert.AlertType.ERROR, "Error", "Invalid Input", "Please input a name for the customer.");                 
            return false;
        }
        if(txtAddress.getText().equals(""))
        {
            MessageBox.Show(Alert.AlertType.ERROR, "Error", "Invalid Input", "Please input a customer address.");                 
            return false;
        }
        if(txtPostalCode.getText().equals(""))
        {
            MessageBox.Show(Alert.AlertType.ERROR, "Error", "Invalid Input", "Please inputa postal code for the customer.");                 
            return false;
        }
        if(txtPhoneNumber.getText().equals(""))
        {
            MessageBox.Show(Alert.AlertType.ERROR, "Error", "Invalid Input", "Please input a phone number for the customer.");                 
            return false;
        }
        return true;
    }
}