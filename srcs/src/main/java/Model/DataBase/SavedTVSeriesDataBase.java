package Model.DataBase;

import utils.Messages.UnsuccessfulTask;

import java.sql.*;
import java.util.ArrayList;

public class SavedTVSeriesDataBase extends AbstractDataBase
{
    public SavedTVSeriesDataBase() { tableName = "catalog"; }

    public ArrayList<String> getTitles()
        { return getTitles(tableName); }

    public void saveInfo(String title, String extract)
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(url);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String sql = "REPLACE INTO " + tableName + " (title, extract, source) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, extract);
            preparedStatement.setInt(3, 1);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e)
            { UnsuccessfulTask.dataBaseError(); }
        finally { closeConnection(connection); }
    }

    public String getExtract(String title)
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            ResultSet rs = statement.executeQuery("select * from " + tableName + " WHERE title = '" + title + "'" );
            rs.next();
            return rs.getString("extract");
        }
        catch(SQLException e)
            { UnsuccessfulTask.dataBaseError(); }
        finally { closeConnection(connection); }
        return null;
    }

    public void deleteEntry(String title)
        { deleteEntry(title, tableName); }
}