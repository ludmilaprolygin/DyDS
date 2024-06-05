package Model.DataBase;

import View.Messages.UnsuccessfulTask;

import java.sql.*;
import java.util.ArrayList;

public class RatedTVSeriesDataBase extends AbstractDataBase
{
    protected static final String tableName = "rated";
    public static ArrayList<String> getTitles()
    {
        return getTitles(tableName);
    }
    public static ArrayList<String> getAllEntries() { return getAllEntries(tableName); }
    public void deleteEntry(String title)
    {
        deleteEntry(title, tableName);
    }

    public static int getScore(String title)
    {
        int score = 0;
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet resultSet = statement.executeQuery("select score from " + tableName + " WHERE title = '" + title + "'");
            while(resultSet.next()) score = resultSet.getInt("score");
        }
        catch(SQLException e) { UnsuccessfulTask.dataBaseError(); }
        finally { closeConnection(connection); }
        return score;
    }

    public static void saveInfo(String title, int score)
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(url);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            String sql = "REPLACE INTO " + tableName + " (title, score, date) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, score);
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            UnsuccessfulTask.dataBaseError();
        }
        finally { closeConnection(connection); }
    }
}