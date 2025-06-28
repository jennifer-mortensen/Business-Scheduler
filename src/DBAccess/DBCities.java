package DBAccess;

import Database.DBConnection;
import Model.City;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Contains methods for interacting with the cities database table.
 * @author J. Mortensen
 */
public class DBCities 
{
    
    /**
     * Designates an invalid country, i.e. no country could be found for the given ID. 
     */
    public static final int COUNTRY_INVALID = -1;
    
    /**
     * Returns the ID of the country containing the given city.
     * @param cityID the ID of the city to retrieve the parent country of.
     * @return the ID of the parent country as an int.
     */
    public static int CityIDToCountryID(int cityID)
    {        
        ResultSet rs;
        try
        {
            rs = DBConnection.GetSQLResultSet("SELECT countryId FROM city WHERE cityId = " + cityID);
            rs.next();
            return(rs.getInt("countryId"));
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return COUNTRY_INVALID;
    }
    
    /**
     * Returns the name associated with the given city ID.
     * @param cityID the ID of the city to retrieve the name of.
     * @return the name of the city as a String.
     */
    public static String CityIDIDToCityName(int cityID)
    {
        ResultSet rs;
        try
        {
            rs = DBConnection.GetSQLResultSet("SELECT city FROM city WHERE cityId = " + cityID);
            rs.next();
            return(rs.getString("city"));
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * Returns a list containing all cities from the database.
     * @return ObservableList
     */
    public static ObservableList<City> GetAllCities()
    {
        City c;
        ObservableList<City> cities = FXCollections.observableArrayList();
        ResultSet rs;
        
        try
        {
            rs = DBConnection.GetSQLResultSet("SELECT * FROM city");
            while(rs.next())
            {
                c = new City(rs.getInt("cityId"), rs.getString("city"), rs.getInt("countryId"),
                    rs.getTimestamp("createDate").toLocalDateTime(), rs.getString("createdBy"),
                    rs.getTimestamp("lastUpdate"), rs.getString("lastUpdateBy"));
                cities.add(c);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return cities;
    }

    /**
     * Returns a list containing all cities from the database that are associated
     * with the given country ID.
     * @param countryID the ID of the country to search on.
     * @return ObservableList
     */
    public static ObservableList<City> GetAllCities(int countryID)
    {
        City c;
        ObservableList<City> cities = FXCollections.observableArrayList();
        ResultSet rs;
         
        try
        {
            rs = DBConnection.GetSQLResultSet("SELECT * FROM city WHERE countryId = " + countryID);
            while(rs.next())
            {
                c = new City(rs.getInt("cityId"), rs.getString("city"), rs.getInt("countryId"),
                    rs.getTimestamp("createDate").toLocalDateTime(), rs.getString("createdBy"),
                    rs.getTimestamp("lastUpdate"), rs.getString("lastUpdateBy"));
                cities.add(c);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return cities;         
    }
}