package Model.DataBase;

import View.Messages.UnsuccessfulTask;

import java.sql.*;
import java.util.ArrayList;

public abstract class AbstractDataBase
{
    protected static final String url = "jdbc:sqlite:./dictionary.db";
    public static void loadDatabase()
    {
        try (Connection connection = DriverManager.getConnection(url))
        {
            if (connection != null)
            {
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);  // set timeout to 30 sec.

                statement.executeUpdate("create table if not exists catalog (id auto_increment, title string primary key, extract string, source integer)");
                statement.executeUpdate("create table if not exists rated (" +
                        "pageid integer, " +
                        "title string primary key, " +
                        "score int unsigned check (score between 1 and 10), " +
                        "date date)");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage() + " errorrrrrrr");
        }
    }

    public static ArrayList<String> getTitles(String tableName)
    {
        ArrayList<String> titles = new ArrayList<>();
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet resultSet = statement.executeQuery("select * from " + tableName);
            while(resultSet.next()) titles.add(resultSet.getString("title"));
        }
        catch(SQLException e) { UnsuccessfulTask.dataBaseError(); }
        finally { closeConnection(connection); }
        return titles;
    }

    public static void deleteEntry(String title, String tableName)
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("DELETE FROM " + tableName + " WHERE title = '" + title + "'" );
        }
        catch(SQLException e) { UnsuccessfulTask.dataBaseError(); }
        finally { closeConnection(connection); }
    }

    protected static void closeConnection(Connection connection)
    {
        try
        {
            if(connection != null)
                connection.close();
        }
        catch(SQLException e) { UnsuccessfulTask.dataBaseError(); }
    }
}