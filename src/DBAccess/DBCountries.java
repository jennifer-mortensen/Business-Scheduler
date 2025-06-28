package DBAccess;

import Database.DBConnection;
import Database.DBConnection;
import Model.Country;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Contains methods for interacting with the countries database table.
 * @author J. Mortensen
 */
public class DBCountries 
{
    /**
     * Returns the name associated with the given country ID.
     * @param countryID the ID of the country to retrieve the name of.
     * @return the name of the country as a String.
     */
    public static String CountryIDToCountryName(int countryID)
    {
        ResultSet rs;
        try
        {
            rs = DBConnection.GetSQLResultSet("SELECT country FROM country WHERE countryId = " + countryID);
            rs.next();
            return(rs.getString("country"));
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return "";
        }
    }    
    
    /**
     * Returns a list containing all countries from the database.
     * @return ObservableList
     */
    public static ObservableList<Country> GetAllCountries()
    {
        Country c;
        ObservableList<Country> countries = FXCollections.observableArrayList();
        ResultSet rs;
        
        try
        {
            rs = DBConnection.GetSQLResultSet("SELECT * FROM country");
            while(rs.next())
            {
                c = new Country(rs.getInt("countryId"), rs.getString("country"), rs.getTimestamp("createDate").toLocalDateTime(),
                    rs.getString("createdBy"), rs.getTimestamp("lastUpdate"), rs.getString("lastUpdatedBy"));
                countries.add(c);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return countries;
    }
}