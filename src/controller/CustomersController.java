package controller;

import DBAccess.DBCustomers;
import Model.Customer;
import Model.Customer;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.MessageBox;

/**
 * FXML Controller class
 * Customer View Form
 * @author J. Mortensen
 */
public class CustomersController implements Initializable 
{
    /**
     * Stores the currently selected customer for easy access when modifying a customer
     * from the add/modify customers controller.
     */
    private static Customer selectedCustomer = null;    
    /**
     * Main scene variable.
     */
    private Parent scene;    
    /**
     * Main stage variable.
     */
    private Stage stage;
    /**
     * Lists all customers from the database.
     */
    ObservableList<Customer> customers = DBCustomers.GetAllCustomers();    
    
    @FXML
    private javafx.scene.control.Button btnAddCustomer;
    @FXML
    private javafx.scene.control.Button btnModifyCustomer;
    @FXML
    private javafx.scene.control.Button btnDeleteCustomer;
    @FXML
    private javafx.scene.control.Button btnBack;
    @FXML
    private Label lblCustomers;
    @FXML
    private TableView<Customer> tblCustomers;
    @FXML
    private TableColumn<Customer, Integer> colCustomerID;
    @FXML
    private TableColumn<Customer, String> colCustomerName;
    @FXML
    private TableColumn<Customer, String> colLocation;
    @FXML
    private TableColumn<Customer, String> colPhoneNumber;    
    @FXML
    private AnchorPane root;
 
    /**
     * Initializes the customers form, which populates the table with customer
     * information.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        tblCustomers.setItems(customers);
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("ID"));  
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));          
        
    }    

    /**
     * Handles the add customer button, which loads the add/modify customers form.
     */
    @FXML
    private void HandleAddCustomer(javafx.event.ActionEvent event) throws IOException
    {
        selectedCustomer = null;
        stage = (Stage) root.getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/frmAddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();            
    }    
    
    /**
     * Handles the back button, which returns to the root form directory.
     */
    @FXML
    private void HandleBack(javafx.event.ActionEvent event) throws IOException 
    {
        stage = (Stage) root.getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/frmMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }    
   
    /**
     * Handles the delete customer button, which selects the selected customer after
     * confirmation.
     */
    @FXML
    private void HandleDeleteCustomer(javafx.event.ActionEvent event)
    {
        Customer customer = tblCustomers.getSelectionModel().getSelectedItem();
        
        if(customer == null)
        {
            MessageBox.Show(Alert.AlertType.ERROR, "Error", "Invalid Selection", "You must first select a customer to delete.");
        }
        if(MessageBox.Show(Alert.AlertType.CONFIRMATION, "Confirm Deletion", "Deleting Customer", "Delete selected customer?"))
        {
            if(DBCustomers.DeleteCustomer(customer.getID()))
            {
                MessageBox.Show(Alert.AlertType.INFORMATION, "Update", "Customer Deleted", "Customer with name " + customer.getName()
                    + " and ID " + customer.getID() + " has been deleted.");
                customers.remove(customer);
            }            
        }        
    }        

    /**
     * Handles the modify customer button, which stores the currently selected customer
     * to pass to the add/modify customers form.
     */
    @FXML
    private void HandleModifyCustomer(javafx.event.ActionEvent event) throws IOException
    {
        selectedCustomer = tblCustomers.getSelectionModel().getSelectedItem();
        
        if(selectedCustomer == null)
        {
            MessageBox.Show(Alert.AlertType.ERROR, "Error", "Invalid Selection", "You must first select a customer to modify.");
            return;        
        }
        stage = (Stage) root.getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/frmAddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();            
    }    
    
    /**
     * Returns the currently selected customer.
     * @return Customer 
     */
    public static Customer getSelectedCustomer()
    {
        return selectedCustomer;
    }
}
