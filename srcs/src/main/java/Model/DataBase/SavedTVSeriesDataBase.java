package Model.DataBase;

import View.Messages.UnsuccessfulTask;

import java.sql.*;
import java.util.ArrayList;

public class SavedTVSeriesDataBase extends AbstractDataBase
{
    protected static final String tableName = "catalog";
    public static ArrayList<String> getTitles()
    {
        return AbstractDataBase.getTitles(tableName);
    }

    public static void saveInfo(String title, String extract)
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(url);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            String sql = "REPLACE INTO " + tableName + " (title, extract, source) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, extract);
            preparedStatement.setInt(3, 1);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            UnsuccessfulTask.dataBaseError();
        }
        finally { closeConnection(connection); }
    }

    public static String getExtract(String title)
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("select * from " + tableName + " WHERE title = '" + title + "'" );
            rs.next();
            return rs.getString("extract");
        }
        catch(SQLException e)
        {
            UnsuccessfulTask.dataBaseError();
        }
        finally { closeConnection(connection); }
        return null;
    }

    public static void deleteEntry(String title)
    {
        deleteEntry(title, tableName);
    }
}