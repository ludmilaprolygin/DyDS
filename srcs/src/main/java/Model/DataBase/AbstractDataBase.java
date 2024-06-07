package Model.DataBase;

import utils.Messages.UnsuccessfulTask;
import utils.RatedSeries;

import java.sql.*;
import java.util.ArrayList;

public abstract class AbstractDataBase
{
    protected static final String url = "jdbc:sqlite:./dictionary.db";
    protected String tableName;

    public static void loadDatabase()
    {
        try (Connection connection = DriverManager.getConnection(url))
        {
            if (connection != null)
            {
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);

                createCatalogTable(statement);
                createRatedTable(statement);
            }
        }
        catch (SQLException e)
            { UnsuccessfulTask.dataBaseError(); }
    }
    protected static void createCatalogTable(Statement statement) throws SQLException
        { statement.executeUpdate("create table if not exists catalog (id auto_increment, title string primary key, extract string, source integer)"); }
    protected static void createRatedTable(Statement statement) throws SQLException
    {
        statement.executeUpdate("create table if not exists rated (" +
                "pageid string, " +
                "title string primary key, " +
                "score int unsigned check (score between 1 and 10), " +
                "date date)");
    }

    public ArrayList<String> getTitles(String tableName)
    {
        ArrayList<String> titles = new ArrayList<>();
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            ResultSet resultSet = statement.executeQuery("select * from " + tableName);
            while(resultSet.next()) titles.add(resultSet.getString("title"));
        }
        catch(SQLException e) { UnsuccessfulTask.dataBaseError(); }
        finally { closeConnection(connection); }
        return titles;
    }
    public void deleteEntry(String title, String tableName)
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("DELETE FROM " + tableName + " WHERE title = '" + title + "'" );
        }
        catch(SQLException e) { UnsuccessfulTask.dataBaseError(); }
        finally { closeConnection(connection); }
    }

    protected void closeConnection(Connection connection)
    {
        try
         { if(connection != null) { connection.close(); } }
        catch(SQLException e) { UnsuccessfulTask.dataBaseError(); }
    }
}