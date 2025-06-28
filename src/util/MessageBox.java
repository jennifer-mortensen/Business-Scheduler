package util;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Utility class for presenting message boxes to the user.
 * @author J. Mortensen
 */
public class MessageBox 
{
    /**
     * Shows a message box.
     * @param alertType the type of alert.
     * @param title the title displayed over the alert.
     * @param headerText the header text for the alert.
     * @param contentText the content of the alert text.
     * @return TRUE if a confirmation prompt was given and selected.
     */
    public static boolean Show(Alert.AlertType alertType, String title, String headerText, String contentText)
    {
        // Just a helper function to make message box handling feel a little 'cleaner'.
        Alert alert = new Alert(alertType, contentText);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        Optional<ButtonType> result = alert.showAndWait();  
        return(result.isPresent() && result.get() == ButtonType.OK);      
    }        
}