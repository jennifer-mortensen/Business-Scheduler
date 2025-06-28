package business.scheduler;

import DBAccess.DBCountries;
import Database.DBConnection;
import Model.Country;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main thread handler for the application.
 * @author J. Mortensen
 */
public class BusinessScheduler extends Application 
{

    /**
     * Initializes the program.
     * @param args -
     */
    public static void main(String[] args) 
    {                
        // Establish DB connection
        DBConnection.StartConnection();
        launch(args);
        DBConnection.CloseConnection();
    }

    /**
     * Launches the program's starting window.
     */
    @Override
    public void start(Stage stage) throws Exception 
    {        
        Parent root = FXMLLoader.load(getClass().getResource("/view/frmLogin.fxml"));        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Business Scheduler");
        stage.show();
    }
}
