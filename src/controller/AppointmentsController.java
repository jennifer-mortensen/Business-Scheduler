package controller;

import DBAccess.DBAppointments;
import Model.Appointment;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.MessageBox;

/**
 * FXML Controller class
 * Appointment View Form
 * @author J. Mortensen
 */
public class AppointmentsController implements Initializable 
{   
    /**
     * Stores the currently selected appointment for easy access when modifying an appointment
     * from the add/modify appointments controller.
     */
    private static Appointment selectedAppointment = null;    
    /**
     * Main scene variable.
     */
    private Parent scene;      
    /**
     * Main stage variable.
     */
    private Stage stage;
    /**
     * Lists all appointments from the database.
     */
    private ObservableList<Appointment> appointments = DBAppointments.GetAllAppointments();      
    /**
     * Toggle group for selection of weekly or monthly appointments.
     */
    private ToggleGroup appointmentPeriod = new ToggleGroup();
    
    @FXML
    private javafx.scene.control.Button btnAddAppointment;
    @FXML
    private javafx.scene.control.Button btnUpdateAppointment;
    @FXML
    private javafx.scene.control.Button btnDeleteAppointment;
    @FXML
    private javafx.scene.control.Button btnBack;
    @FXML
    private TableView<Appointment> tblAppointments;
    @FXML
    private TableColumn<Appointment, Integer> colAppointmentID;
    @FXML
    private TableColumn<Appointment, String> colTitle;
    @FXML
    private TableColumn<Appointment, String> colDescription;
    @FXML
    private TableColumn<Appointment, String> colLocation;
    @FXML
    private TableColumn<Appointment, String> colContact;
    @FXML
    private TableColumn<Appointment, String> colType;
    @FXML
    private TableColumn<Appointment, String> colStartDateAndTime;
    @FXML
    private TableColumn<Appointment, String> colEndDateAndTime;
    @FXML
    private TableColumn<Appointment, Integer> colCustomerID;
    @FXML
    private Label lblAppointments;
    @FXML
    private AnchorPane root;
    @FXML
    private RadioButton optAppointmentsWeekly;
    @FXML
    private RadioButton optAppointmentsMonthly;
    
    /**
     * Handles form initialization, which populates the appointments table with all
     * appointments in the selected time period.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        optAppointmentsWeekly.setToggleGroup(appointmentPeriod);
        optAppointmentsMonthly.setToggleGroup(appointmentPeriod);
        optAppointmentsWeekly.setSelected(true);
        colAppointmentID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));  
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStartDateAndTime.setCellValueFactory(new PropertyValueFactory<>("startTimeDisplay"));
        colEndDateAndTime.setCellValueFactory(new PropertyValueFactory<>("endTimeDisplay"));
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        FilterAppointmentsWeekly();
    }    

    /**
     * Handles the add appointment button, which loads the add/modify appointment controller.
     */
    @FXML
    private void HandleAddAppointment(javafx.event.ActionEvent event) throws IOException
    {
        selectedAppointment = null;
        stage = (Stage) root.getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/frmAddAppointment.fxml"));
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
     * Handles the delete appointment button, which deletes the selected appointment
     * after confirmation.
     */
    @FXML
    private void HandleDeleteAppointment(javafx.event.ActionEvent event)
    {
        Appointment appointment = tblAppointments.getSelectionModel().getSelectedItem();
        
        if(appointment == null)
        {
            MessageBox.Show(Alert.AlertType.ERROR, "Error", "Invalid Selection", "You must first select an appointment to delete.");
            return;
        }
        if(MessageBox.Show(Alert.AlertType.CONFIRMATION, "Confirm Cancelation", "Canceling Appointment", "Cancel selected appointment?"))
        {
            if(DBAppointments.DeleteAppointment(appointment.getID()))
            {
                MessageBox.Show(Alert.AlertType.INFORMATION, "Update", "Appointment Canceled", "Appointment of type " + appointment.getType()
                    + " with ID " + appointment.getID() + " has been canceled.");
                appointments.remove(appointment);
            }            
        }
    }     

    /**
     * Handles the update appointment button, which stores the currently selected appointment
     * to pass to the add/modify appointments form.
     */
    @FXML
    private void HandleUpdateAppointment(javafx.event.ActionEvent event) throws IOException
    {
        selectedAppointment = tblAppointments.getSelectionModel().getSelectedItem();
        
        if(selectedAppointment == null)
        {
            MessageBox.Show(Alert.AlertType.ERROR, "Error", "Invalid Selection", "You must first select an appointment to modify.");
            return;
        }
        
        stage = (Stage) root.getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/frmAddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();            
    }       
    
    /**
     * Uses a lambda expression to filter appointments upcoming within the next month.
     */
    private void FilterAppointmentsMonthly()
    {
        FilteredList<Appointment> appointmentsMonthly = new FilteredList<>(appointments);    
        
        appointmentsMonthly.setPredicate(a -> 
        {
            LocalDate appointmentDate = a.getDate();
            return appointmentDate.isAfter(LocalDate.now().minusDays(1)) 
                    && appointmentDate.isBefore(LocalDate.now().plusMonths(1));
        });
        tblAppointments.setItems(appointmentsMonthly);  
    }    
    
    /**
     * Uses a lambda expression to filter appointments upcoming within the next week.
     */
    private void FilterAppointmentsWeekly()
    {
        FilteredList<Appointment> appointmentsWeekly = new FilteredList<>(appointments);     
        
        appointmentsWeekly.setPredicate(a -> 
        {
            LocalDate appointmentDate = a.getDate();
            return appointmentDate.isAfter(LocalDate.now().minusDays(1)) 
                    && appointmentDate.isBefore(LocalDate.now().plusWeeks(1));
        });
        tblAppointments.setItems(appointmentsWeekly);        
    }   

    /**
     * Updates the appointment period based on radio button selection, signaling
     * the appointments table to show appointments either within the next week
     * or month.
     */
    @FXML
    private void UpdateAppointmentPeriod() 
    {
        if(optAppointmentsWeekly.isFocused()) 
        {
            FilterAppointmentsWeekly();
        }
        else 
        {
            FilterAppointmentsMonthly();
        }
    }
    
    /**
     * Returns the selected appointment. 
     * @return Appointment
     */
    public static Appointment getSelectedAppointment()
    {
        return selectedAppointment;
    }    
}
