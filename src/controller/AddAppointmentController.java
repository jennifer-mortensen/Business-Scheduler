package controller;

import DBAccess.DBAppointments;
import DBAccess.DBCustomers;
import Database.DBConnection;
import Model.Appointment;
import Model.Customer;
import Model.User;
import controller.LoginController;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.MessageBox;
import util.Time;

/**
 * FXML Controller class
 * Add/Modify Appointment Form
 * @author J. Mortensen
 */
public class AddAppointmentController implements Initializable 
{
    /**
     * Lists all types of appointment for user selection (i.e. Open Account, Consultation, etc.).
     */
    private final static String[] APPOINTMENT_TYPES = { "Open Account", "Consultation", "Follow-up", "Other" };
    /**
     * Lists all selectable business locations for the appointment. 
     */
    private final static String[] BUSINESS_LOCATIONS = { "New York, New York", "Los Angeles, California", "Toronto, Ontario", "Vancouver, British Columbia", "Oslo (Norway)" };  
    
    /**
     * Main scene variable.
     */
    private Parent scene;        
    /**
     * Main stage variable.
     */
    private Stage stage;
    
    @FXML
    private javafx.scene.control.Button btnSave;
    @FXML
    private javafx.scene.control.Button btnCancel;
    @FXML
    private Label lblCustomer;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblDescription;        
    @FXML
    private Label lblLocation;
    @FXML
    private Label lblContact;
    @FXML
    private Label lblType;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblStartTime;
    @FXML
    private Label lblEndTime;
    @FXML
    private Label lblCustomerID;
    @FXML
    private Label lblAddAppointment;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtDescription;
    @FXML
    private DatePicker datDate;
    @FXML
    private TextField txtCustomerID;
    @FXML
    private AnchorPane root;
    @FXML
    private ComboBox<Customer> cmbCustomer;
    @FXML
    private ComboBox<String> cmbLocation;
    @FXML
    private TextField txtContact;
    @FXML
    private ComboBox<String> cmbType;
    @FXML
    private ComboBox<LocalTime> cmbStartTime;
    @FXML
    private ComboBox<LocalTime> cmbEndTime;
    @FXML
    private TextField txtAppointmentID;
    @FXML
    private Label lblAppointmentID;
    
    /**
     Initializes the form, populating decision boxes and
     * loading previous data (if modifying an existing appointment).
     * @param arg0
     * @param arg1 
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        Appointment selectedAppointment = AppointmentsController.getSelectedAppointment();
        
        cmbCustomer.setItems(DBCustomers.GetAllCustomers());
        cmbLocation.getItems().addAll(BUSINESS_LOCATIONS);
        cmbType.getItems().addAll(APPOINTMENT_TYPES); 
        PopulateTime(cmbStartTime);
        PopulateTime(cmbEndTime);       
        
        if(selectedAppointment != null)
        {
            lblAddAppointment.setText("Modify Appointment");
            SelectCustomerByID(selectedAppointment.getCustomerID());
            txtTitle.setText(selectedAppointment.getTitle());
            txtDescription.setText(selectedAppointment.getDescription());
            cmbLocation.getSelectionModel().select(selectedAppointment.getLocation());
            txtContact.setText(selectedAppointment.getContact());
            cmbType.getSelectionModel().select(selectedAppointment.getType());
            datDate.setValue((selectedAppointment.getDate()));
            cmbStartTime.getSelectionModel().select(selectedAppointment.getStartTime());
            cmbEndTime.getSelectionModel().select(selectedAppointment.getEndTime());
            txtAppointmentID.setText(String.valueOf(selectedAppointment.getID()));
        }
        else
        {        
            datDate.setValue(LocalDate.now());
            lblAppointmentID.setVisible(false);
            txtAppointmentID.setVisible(false);            
            txtAppointmentID.setText(String.valueOf(Appointment.APPOINTMENT_NEW_ID));
        }
    }   

    /**
     * Handles the cancel button, which returns to the main appointments form.
     */
    @FXML
    private void HandleCancel(javafx.event.ActionEvent event) throws IOException 
    {
        stage = (Stage) root.getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/frmAppointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();        
    }    
    
    /**
     * Handles the save button, which stores the new/updated appointment in
     */
    @FXML
    private void HandleSave(javafx.event.ActionEvent event) throws IOException 
    {        
        if(!ValidateAppointment()) return;

        Appointment a;
        int appointmentID = Integer.valueOf(txtAppointmentID.getText());
        int userID = 0;
        ResultSet rs;
        String createdBy = "";
        String updatedBy = LoginController.getUser().getName();
        LocalDateTime createDate = LocalDateTime.now();
     
        if(appointmentID != Appointment.APPOINTMENT_NEW_ID)
        {
            rs = DBConnection.GetSQLResultSet("SELECT userId, createdBy, createDate FROM appointment WHERE appointmentId = " + appointmentID);
            try
            {
                rs.next();
                userID = rs.getInt("userId");
                createdBy = rs.getString("createdBy");
                createDate = rs.getTimestamp("createDate").toLocalDateTime();
            }
            catch(SQLException e)
            {
                //e.printStackTrace();
                return;
            }
        }
        else
        {
            userID = LoginController.getUser().getID();
            createdBy = LoginController.getUser().getName();
        }
           
        a = new Appointment(
            appointmentID,
            Integer.valueOf(txtCustomerID.getText()),
            userID,
            txtTitle.getText(),
            txtDescription.getText(),
            cmbLocation.getSelectionModel().getSelectedItem(),
            txtContact.getText(),
            cmbType.getSelectionModel().getSelectedItem(),
            LocalDateTime.of(datDate.getValue(), cmbStartTime.getSelectionModel().getSelectedItem()),
            LocalDateTime.of(datDate.getValue(), cmbEndTime.getSelectionModel().getSelectedItem()),     
            createDate,
            createdBy,
            Timestamp.valueOf(LocalDateTime.now()),
            updatedBy
        );
        
        DBAppointments.WriteAppointment(a);
        MessageBox.Show(Alert.AlertType.INFORMATION, "Appointments", "Appointment Updated", "Appointment has been updated successfully.");
        if(appointmentID == Appointment.APPOINTMENT_NEW_ID)
        {
            stage = (Stage) root.getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/frmAppointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();               
        }
    }    

    /**
     * Handles customer combo box selection, which updates the selected customer ID.
     */
    @FXML
    private void HandleUpdateCustomer() 
    {
        txtCustomerID.setText(String.valueOf(cmbCustomer.getSelectionModel().getSelectedItem().getID()));
    }    
    
    /**
     * Checks for an existing appointment conflict.
     * @param column the name of the column to check for a conflict in, e.g. Contact_ID
     * for a contact conflict.
     * @param value the ID of the customer/contact to find a possible conflict for.
     * @return TRUE if a conflict exists.
     */
    private boolean CheckAppointmentConflict(String column, int value)
    {   
        int index = 0;
        ResultSet rs;
        Timestamp startTime = Timestamp.valueOf(LocalDateTime.of(datDate.getValue(), cmbStartTime.getSelectionModel().getSelectedItem()));
        Timestamp endTime = Timestamp.valueOf(LocalDateTime.of(datDate.getValue(), cmbEndTime.getSelectionModel().getSelectedItem())); 
        
        try
        {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(
                "SELECT * FROM appointment "
                + "WHERE (? BETWEEN start AND end OR ? BETWEEN start AND end OR ? < start AND ? > end) "
                + "AND " + column + " = ? "
                + "AND appointmentId NOT IN (?)");
            ps.setTimestamp(++index, startTime);
            ps.setTimestamp(++index, endTime);
            ps.setTimestamp(++index, startTime);
            ps.setTimestamp(++index, endTime);
            ps.setInt(++index, value);
            ps.setInt(++index, Integer.valueOf(txtAppointmentID.getText()));
            rs = ps.executeQuery();       
            
            return rs.next();
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
            return true;
        }
    }
            
    /**
     * Checks for an appointment scheduling conflict for the selected customer.
     * @return TRUE if a conflict exists.
     */
    private boolean CheckCustomerAppointmentConflict()
    {
        return CheckAppointmentConflict("customerId", cmbCustomer.getSelectionModel().getSelectedItem().getID());
    }
    
    /**
     * Populates time in intervals of 15 minutes into the designated combo box. 
     * @param selector the combo box to populated.
     */
    private void PopulateTime(ComboBox<LocalTime> selector)
    {
        for(int hour = 0; hour <= 23; hour++)
        {
            for(int minute = 0; minute <= 45; minute += 15)
            {
                selector.getItems().add(LocalTime.of(hour, minute));
            }
        }
    }
  
    /**
     * Uses a lamba expression to filter customer list for the given ID.
     * @param ID the customer ID.
     */
    private void SelectCustomerByID(int ID)
    {
        FilteredList<Customer> customers = new FilteredList<>(cmbCustomer.getItems());
        customers.setPredicate(c ->
        {
            return c.getID() == ID;
        });
        cmbCustomer.getSelectionModel().select(customers.get(0));
        HandleUpdateCustomer();
    }    
    
    /**
     * Checks to see if all input appointment details result in a valid appointment
     * and prints an error message if they do not.
     * @return TRUE if the appointment is valid.
     */
    boolean ValidateAppointment()
    {
        LocalTime startTime = cmbStartTime.getSelectionModel().getSelectedItem();
        LocalTime endTime = cmbEndTime.getSelectionModel().getSelectedItem();
        
        if(cmbCustomer.getSelectionModel().getSelectedItem() == null)
        {
            MessageBox.Show(Alert.AlertType.ERROR, "Error", "Invalid Input", "Please select a customer to assign the appointment to.");
            return false;
        }
        if(txtTitle.getText().equals(""))
        {
            MessageBox.Show(Alert.AlertType.ERROR, "Error", "Invalid Input", "Please input a title for the appointment..");            
            return false;
        }
        if(txtDescription.getText().equals(""))
        {
            MessageBox.Show(Alert.AlertType.ERROR, "Error", "Invalid Input", "Please input a description for the appointment.");            
            return false;
        }
        if(cmbLocation.getSelectionModel().getSelectedItem() == null)
        {
            MessageBox.Show(Alert.AlertType.ERROR, "Error", "Invalid Input", "Please select a location for the appointment.");            
            return false;
        }
        if(txtContact.getText().equals(""))
        {
            MessageBox.Show(Alert.AlertType.ERROR, "Error", "Invalid Input", "Please assign a contact to the appointment.");            
            return false;
        }
        if(cmbType.getSelectionModel().getSelectedItem() == null)
        {
            MessageBox.Show(Alert.AlertType.ERROR, "Error", "Invalid Input", "Please select an appointment type.");            
            return false;
        }
        if(startTime == null || endTime == null)
        {
            MessageBox.Show(Alert.AlertType.ERROR, "Error", "Invalid Input", "Please select start and end time.");               
            return false;
        }
        if(LocalDateTime.of(datDate.getValue(), startTime).isBefore(LocalDateTime.now()))
        {
            MessageBox.Show(Alert.AlertType.ERROR, "Error", "Invalid Input", "Appointment cannot start before the current time.");               
            return false;            
        }
        if(!(endTime.isAfter(startTime)))
        {
            MessageBox.Show(Alert.AlertType.ERROR, "Error", "Invalid Input", "Appointment end time must be after start time.");            
            return false;
        }
       if(startTime.isBefore(Time.BusinessTimeToLocalTime(Time.getBusinessTimeOpen()).toLocalTime()) || 
                endTime.isAfter(Time.BusinessTimeToLocalTime(Time.getBusinessTimeClose()).toLocalTime()))
        {
            MessageBox.Show(Alert.AlertType.ERROR, "Error", "Invalid Input", "Appointment time must be within business hours of "
                + Time.FormatTime(Time.BusinessTimeToLocalTime(Time.getBusinessTimeOpen())) + " and " 
                + Time.FormatTime(Time.BusinessTimeToLocalTime(Time.getBusinessTimeClose())) + ".");            
            return false;            
        }
        if(CheckCustomerAppointmentConflict())
        {
            MessageBox.Show(Alert.AlertType.ERROR, "Error", "Invalid Input", "Customer is already assigned to an appointment during that time.");            
            return false;           
        }
        return true;
    } 
}