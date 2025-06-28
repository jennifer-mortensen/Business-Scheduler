package controller;

import Model.Reports.AppointmentsByMonth;
import Model.Reports.AppointmentsByMonthRow;
import Model.Reports.ContactSchedule;
import Model.Reports.ContactScheduleRow;
import Model.Reports.MonthlyAdditionsByUser;
import Model.Reports.MonthlyAdditionsByUserRow;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.MessageBox;

/**
 * FXML Controller class
 * Reports Form
 * @author J. Mortensen
 */
public class ReportsController implements Initializable 
{
    /**
     * Main scene variable.
     */
    private Parent scene;      
    /**
     * Main stage variable.
     */
    private Stage stage;    
    
    @FXML
    private AnchorPane root;
    @FXML
    private Tab tabAppointmentsByMonth1;
    @FXML
    private TableView<AppointmentsByMonthRow> tblAppointmentsByMonth;
    @FXML
    private TableColumn<AppointmentsByMonthRow, String> colABMMonth;
    @FXML
    private TableColumn<AppointmentsByMonthRow, Integer> colABMOpenAccount;
    @FXML
    private TableColumn<AppointmentsByMonthRow, Integer> colABMConsultation;
    @FXML
    private TableColumn<AppointmentsByMonthRow, Integer> colABMFollowUp;
    @FXML
    private TableColumn<AppointmentsByMonthRow, Integer> colABMOther;
    @FXML
    private Tab tabContactSchedule;
    @FXML
    private TableView<ContactScheduleRow> tblContactSchedule;
    @FXML
    private TableColumn<ContactScheduleRow, String> colCSContactName;
    @FXML
    private TableColumn<ContactScheduleRow, Integer> colCSAppointmentID;
    @FXML
    private TableColumn<ContactScheduleRow, String> colCSTitle;
    @FXML
    private TableColumn<ContactScheduleRow, String> colCSType;
    @FXML
    private TableColumn<ContactScheduleRow, String> colCSDescription;
    @FXML
    private TableColumn<ContactScheduleRow, String> colCSStartDateAndTime;
    @FXML
    private TableColumn<ContactScheduleRow, String> colCSEndDateAndTime;
    @FXML
    private TableColumn<ContactScheduleRow, Integer> colCSCustomerID;
    @FXML
    private Tab tabMothlyAdditionsByUser;
    @FXML
    private TableView<MonthlyAdditionsByUserRow> tblMonthlyAdditionsByUser;    
    @FXML
    private TableColumn<MonthlyAdditionsByUserRow, Integer> colABUUserID;
    @FXML
    private TableColumn<MonthlyAdditionsByUserRow, String> colABUUserName;
    @FXML
    private TableColumn<MonthlyAdditionsByUserRow, Integer> colABUNewCustomers;
    @FXML
    private TableColumn<MonthlyAdditionsByUserRow, Integer> colABUNewAppointments;
    @FXML
    private Label lblReports;
    @FXML
    private javafx.scene.control.Button btnBack;        
    
    /**
     * Initializes the reports form, querying the database to populate report tables appropriately.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        MessageBox.Show(AlertType.INFORMATION, "Please Wait", "Compiling Reports", "Please be patient. This might take some time...");
        tblAppointmentsByMonth.setItems(AppointmentsByMonth.GetReport());
        colABMMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        colABMOpenAccount.setCellValueFactory(new PropertyValueFactory<>("openAccount"));
        colABMConsultation.setCellValueFactory(new PropertyValueFactory<>("consultation"));
        colABMFollowUp.setCellValueFactory(new PropertyValueFactory<>("followUp"));
        colABMOther.setCellValueFactory(new PropertyValueFactory<>("other"));
        tblContactSchedule.setItems(ContactSchedule.GetReport());
        colCSContactName.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        colCSAppointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        colCSTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colCSType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colCSDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCSStartDateAndTime.setCellValueFactory(new PropertyValueFactory<>("startDateAndTime"));
        colCSEndDateAndTime.setCellValueFactory(new PropertyValueFactory<>("endDateAndTime"));
        colCSCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        tblMonthlyAdditionsByUser.setItems(MonthlyAdditionsByUser.GetReport());
        colABUUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        colABUUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colABUNewCustomers.setCellValueFactory(new PropertyValueFactory<>("newCustomers"));
        colABUNewAppointments.setCellValueFactory(new PropertyValueFactory<>("newAppointments"));                
    }
    
    /**
     * Handles the back button, which retains to the root form directory.
     */
    @FXML
    private void HandleBack(javafx.event.ActionEvent event) throws IOException {
        stage = (Stage) root.getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/frmMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();    
    }
    
}
