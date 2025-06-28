package DBAccess;

import Database.DBConnection;
import Model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Contains methods for interacting with the users database table.
 * @author J. Mortensen
 */
public class DBUsers 
{  
    /**
     * Returns a list containing all users from the database.
     * @return ObservableList
     */
    public static ObservableList<User> GetAllUsers()
    {
        ObservableList<User> users = FXCollections.observableArrayList();
        ResultSet rs;
        User u;
        
        try
        {
            rs = DBConnection.GetSQLResultSet("SELECT * FROM user");
            while(rs.next())
            {
                u = new User(rs.getInt("userId"), rs.getString("userName"), rs.getBoolean("active"),
                        rs.getTimestamp("createDate").toLocalDateTime(), rs.getString("createdBy"),
                        rs.getTimestamp("lastUpdate"), rs.getString("lastUpdatedBy"));
                users.add(u);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return users;  
    }        
}
