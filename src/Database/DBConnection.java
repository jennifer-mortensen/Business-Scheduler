package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contains methods for interacting with the database.
 * @author J. Mortensen
 */
public class DBConnection 
{
    // JDBC URL components
    private static final String protocol = "jdbc";
    private static final String vendorName = "mysql";
    private static final String ipAddress = "127.0.0.1:3306";
    private static final String databaseName = "client_schedule";    
    // JDBC URL
    // This will build: "jdbc:mysql://wgudb.ucertify.com:3306/WJ06XyD"
    private static final String jdbcURL = protocol + ":" + vendorName + ":" + "//"
            + ipAddress + "/" + databaseName;
    // Driver and connection interface reference
    private static final String mysqlJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;
    // User details
    private static final String userName = "sqlUser";
    private static final String password = "Passw0rd!";
    
    /**
     * Returns the established database connection.
     * @return Connection
     */
    public static Connection getConnection()
    {
        return conn;
    }    
    
    /**
     * Closes the established database connection.
     */
    public static void CloseConnection()
    {
        try
        {
            conn.close();
            System.out.println("Connection closed.");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the number of elements contained within a result set.
     * @param rs the result set to analyze.
     * @return int
     */
    public static int GetNumResults(ResultSet rs)
    {
        int numResults = 0;
        
        try
        {
            rs.last();
            numResults = rs.getRow();
            rs.beforeFirst();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return numResults;
    }
    
    /**
     * Gets a result set from the given SQLQuery.
     * @param SQLQuery the query to execute.
     * @return ResultSet
     */
    public static ResultSet GetSQLResultSet(String SQLQuery)
    {        
        PreparedStatement ps;
        
        try
        {
            ps = conn.prepareStatement(SQLQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            return ps.executeQuery();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }    
    
    /**
     * Starts a new DB connection.
     * @return Connection
     */
    public static Connection StartConnection()
    {
        try
        {
            Class.forName(mysqlJDBCDriver);
            try
            {
                conn = (Connection) DriverManager.getConnection(jdbcURL, userName, password);
                System.out.println("Connection established.");
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();        
        }
        
        return conn;
    }
}