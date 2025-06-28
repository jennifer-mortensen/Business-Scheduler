package controller;

import Database.DBConnection;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.MessageBox;
import util.Time;

/**
 * FXML Controller class
 * Root Directory Form
 * @author J. Mortensen
 */
public class MainController implements Initializable 
{
    /**
     * Tracks whether this is the first time the main controller has been
     * accessed since login.
     */
    private static boolean firstLogin = true;
    /**
     * Main scene variable.
     */
    private Parent scene;    
    /**
     * Main stage variable.
     */
    private Stage stage;      
    
    @FXML
    private javafx.scene.control.Button btnCustomers;
    @FXML
    private javafx.scene.control.Button btnAppointments;
    @FXML
    private javafx.scene.control.Button btnReports;
    @FXML
    private javafx.scene.control.Button btnExit;  
    @FXML
    private AnchorPane root;
   
    /**
     * Handles initialization of the root directory form, which prints a welcome message
     * containing a list of all appointments upcoming within the next 15 minutse.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        int index = 0;
        PreparedStatement ps;
        ResultSet rs;
        String appointments = "";
        
        if(firstLogin)
        {
            try
            {
                ps = DBConnection.getConnection().prepareStatement(
                    "SELECT appointmentId, start FROM appointment "
                    + "WHERE start >= ? AND start <= ?");
                ps.setTimestamp(++index, Timestamp.valueOf(LocalDateTime.now()));
                ps.setTimestamp(++index, Timestamp.valueOf(LocalDateTime.now().plusMinutes(15)));
                rs = ps.executeQuery();
                while(rs.next())
                {
                    appointments += Time.FormatDateTime(rs.getTimestamp("start").toLocalDateTime()) + " | Appointment ID: " + rs.getInt("appointmentId") + "\n";
                }
                if(appointments.equals(""))
                {
                    appointments = "There are no upcoming appointments.";
                }
                MessageBox.Show(Alert.AlertType.INFORMATION, "Update", "Upcoming Appointments", appointments);
                firstLogin = false;
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }   
        }
    }    
    
    /**
     * Handles the appointments button, which loads the appointments form.
     */
    @FXML
    private void HandleAppointments(javafx.event.ActionEvent event) throws IOException
    {
        stage = (Stage) root.getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/frmAppointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();        
    }       
    
    /**
     * Handles the customers button, which loads the customers form.
     */
    @FXML
    private void HandleCustomers(javafx.event.ActionEvent event) throws IOException
    {
        stage = (Stage) root.getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/frmCustomers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();        
    }    
    
    /**
     * Handles the exit button, which closes the application.
     */
    @FXML
    private void HandleExit(javafx.event.ActionEvent event) 
    {
        System.exit(0);
    }
    
    /**
     * Handles the reports button, which loads the reports form.
     */
    @FXML
    private void HandleReports(javafx.event.ActionEvent event) throws IOException
    {
        stage = (Stage) root.getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/frmReports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();        
    }        
}
