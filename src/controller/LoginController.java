package controller;

import Database.DBConnection;
import Model.User;
import business.scheduler.BusinessScheduler;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.MessageBox;
import util.Time;

/**
 * FXML Controller class
 * Login Form
 * @author J. Mortensen
 */
public class LoginController implements Initializable
{

    /**
     * Test username, which allows a user to log in without connecting to the users database
     * when the test account is enabled.
     */
    private final static String TEST_USERNAME = "test";
    /**
     * Test password, which allows a user to log in without connecting to the users database
     * when the test account is enabled.
     */
    private final static String TEST_PASSWORD = "test";
    /**
     * If enabled, allows the user to bypass certification by the users database by using
     * a test account.
     */
    private final static boolean ENABLE_TEST_ACCOUNT = false;
    /**
     * Stores English text strings for the login form.
     */
    private final static String[] DICTIONARY_ENGLISH = {"Login", "Username", "Password", "Error", "Invalid Input", "Please input a Username.", 
        "Please input a Password.", "Invalid Login", "Login credentials do not match.", "Login Successful", "Successfully logged in as "};
    /**
     * Stores French text strings for the login form.
     */
    private final static String[] DICTIONARY_FRENCH = {"Connectez-vous", "Nom d'utilisateur", "Mot de passe", "Erreur", "Entrée Invalide", "Veuillez saisir un nom d'utilisateur.",
        "Veuillez saisir un mot de passe.", "Identifiant Invalide", "Les identifiants de connexion ne correspondent pas.",
        "Connexion Réussie", "Connexion réussie en tant que "};    
    
    /**
     * Main scene variable.
     */
    private Parent scene;
    /**
     * Main stage variable.
     */
    private Stage stage;
    /**
     * Stores current user details.
     */
    private static User currentUser = null;
    /**
     * Stores the system's default zone ID.
     */
    private static final ZoneId zoneID = ZoneId.systemDefault();

    @FXML
    private Label lblLogin;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private AnchorPane root;
    @FXML
    private Label lblZoneID;

    /**
     * Initializes the login form, which updates text to be in English or French
     * based on the user's location.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        lblLogin.setText(GetLocalizationString(0));
        lblZoneID.setText(zoneID.getId() + " (" + zoneID.getDisplayName(TextStyle.FULL, Locale.getDefault(Locale.Category.DISPLAY)) + ")");
        txtUsername.setPromptText(GetLocalizationString(1));
        txtPassword.setPromptText(GetLocalizationString(2));
        btnLogin.setText(GetLocalizationString(0));
    }    
    
    /**
     * Handles the login button, which validates login information, and if successful, then
     * stores the active user and loads the root directory form.
     */
    @FXML
    private void HandleLogin(ActionEvent event) throws IOException 
    {
        boolean testMode = false;        
        ResultSet rs;
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        
        if(username.equals(""))
        {
            MessageBox.Show(Alert.AlertType.ERROR, GetLocalizationString(3), GetLocalizationString(4), GetLocalizationString(5));
            return;
        }
        if(password.equals(""))
        {
            MessageBox.Show(Alert.AlertType.ERROR, GetLocalizationString(3), GetLocalizationString(4), GetLocalizationString(6));
            return;            
        }
        if(ENABLE_TEST_ACCOUNT && (username.equals(TEST_USERNAME) && password.equals(TEST_PASSWORD))) testMode = true;
        
        rs = DBConnection.GetSQLResultSet("SELECT userId, password FROM user WHERE active = 1 AND userName = '" + username + "'");

        try
        {        
            if((rs.next() == false || !rs.getString("password").equals(password)) && !testMode)
            {
                MessageBox.Show(Alert.AlertType.ERROR, GetLocalizationString(0), GetLocalizationString(7), GetLocalizationString(8));
                LogActivity(username, false);
            }
            else
            {
                MessageBox.Show(Alert.AlertType.INFORMATION, GetLocalizationString(0), GetLocalizationString(9), GetLocalizationString(10) + username + ".");
                if(testMode)
                {
                    currentUser = new User(User.USER_ID_INVALID, TEST_USERNAME);
                }
                else
                {
                    currentUser = new User(rs.getInt("userId"), username);
                }
                LogActivity(username, true);
                stage = (Stage) root.getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/frmMain.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();                
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns a string localized to French or English based on the given
     * string ID.
     * @param ID the location of the string in the English/French dictionary.
     * @return the localized String.
     */
    private String GetLocalizationString(int ID)
    {   
        if(Locale.getDefault().getLanguage().equals("fr"))
        {
            return DICTIONARY_FRENCH[ID];
        }
        
        // Default to English.
        return DICTIONARY_ENGLISH[ID];
    }
    
    /**
     * Tracks attempted logins in login_activity.text.
     * @param username the username to be logged into.
     * @param loginSuccessful whether the login credentials were valid.
     */
    private void LogActivity(String username, boolean loginSuccessful)
    {
        try 
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("login_activity.txt", true));
            writer.append(Time.FormatDateTime(LocalDateTime.now()) + ": " + username + " attempted login " );
            if(loginSuccessful) writer.append("successfully.\n");
            else writer.append("with error.\n");
            writer.flush();
            writer.close();
        }
        catch(IOException e) 
        {
            e.printStackTrace();
        }        
    }
    
    /**
     * Returns system default zone ID.
     * @return ZoneId
     */
    public static ZoneId getZoneID()
    {
        return zoneID;
    }
    
    /**
     * Returns the current user.
     * @return User
     */
    public static User getUser()
    {
        return currentUser;
    }    
}