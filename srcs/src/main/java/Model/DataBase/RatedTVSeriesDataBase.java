package Model.DataBase;

import utils.Messages.UnsuccessfulTask;
import utils.RatedSeries;

import java.sql.*;
import java.util.ArrayList;

public class RatedTVSeriesDataBase extends AbstractDataBase
{
    protected static final String tableName = "rated";
    public static ArrayList<String> getTitles()
    {
        return getTitles(tableName);
    }
    public static ArrayList<RatedSeries> getAllEntries()
    {
        ArrayList<RatedSeries> allEntries = new ArrayList<RatedSeries>();
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet resultSet = statement.executeQuery("select * from " + tableName);

            allEntries = manageEntries(resultSet);
        }
        catch(SQLException e) { UnsuccessfulTask.dataBaseError(); }
        finally { closeConnection(connection); }
        return allEntries;
    }

    protected static ArrayList<RatedSeries> manageEntries(ResultSet resultSet) throws SQLException {
        ArrayList<RatedSeries> allEntries = new ArrayList<RatedSeries>();
        while(resultSet.next())
        {
            RatedSeries ratedSeries = createRatedSeries(resultSet);
            allEntries.add(ratedSeries);
        }
        return allEntries;
    }

    protected static RatedSeries createRatedSeries(ResultSet resultSet) throws SQLException
    {
        RatedSeries ratedSeries;

        int pageID = resultSet.getInt("pageID");
        String title = resultSet.getString("title");
        int score = resultSet.getInt("score");
        Date date = resultSet.getDate("date");

        ratedSeries = new RatedSeries(pageID, title, score, date);

        return ratedSeries;
    }

    public static RatedSeries getEntry(String title)
    {
        RatedSeries entry = null;
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet resultSet = statement.executeQuery("select * from " + tableName + " WHERE title = '" + title + "'");
            while(resultSet.next())
                entry = createRatedSeries(resultSet);
        }
        catch(SQLException e) { UnsuccessfulTask.dataBaseError(); }
        finally { closeConnection(connection); }
        return entry;
    }

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

    public static void saveInfo(int pageid, String title, int score)
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(url);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            String sql = "REPLACE INTO " + tableName + " (pageid, title, score, date) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, pageid);
            preparedStatement.setString(2, title);
            preparedStatement.setInt(3, score);
            preparedStatement.setDate(4, sqlDate);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            UnsuccessfulTask.dataBaseError();
        }
        finally { closeConnection(connection); }
    }
}