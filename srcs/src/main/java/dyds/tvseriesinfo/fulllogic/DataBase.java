package dyds.tvseriesinfo.fulllogic;

import View.Messages.UnsuccessfulTask;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;

public abstract class DataBase
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
      }
    }
    catch (SQLException e)
    {
      System.out.println(e.getMessage() + " errorrrrrrr");
    }
  }

  public static void testDB()
  {

    Connection connection = null;
    try
    {
      // create a database connection
      connection = DriverManager.getConnection(url);//"jdbc:sqlite:./dictionary.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      ResultSet rs = statement.executeQuery("select * from catalog");
      while(rs.next())
      {
        // read the result set
        System.out.println("id = " + rs.getInt("id"));
        System.out.println("title = " + rs.getString("title"));
        System.out.println("extract = " + rs.getString("extract"));
        System.out.println("source = " + rs.getString("source"));
      }
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory",
      // it probably means no database file is found
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        System.err.println(e);
      }
    }
  }

  public static ArrayList<String> getTitles()
  {
    ArrayList<String> titles = new ArrayList<>();
    Connection connection = null;
    try
    {
      connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      ResultSet resultSet = statement.executeQuery("select * from catalog");
      while(resultSet.next()) titles.add(resultSet.getString("title"));
    }
    catch(SQLException e)
    {
      UnsuccessfulTask.dataBaseError();
    }
    finally { closeConnection(connection); }
    return titles;
  }

  public static void saveInfo(String title, String extract)
  {
    Connection connection = null;
    try
    {
      connection = DriverManager.getConnection(url);

      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      String sql = "REPLACE INTO catalog (title, extract, source) VALUES (?, ?, ?)";
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
    System.out.println("getExtract title: " + title);
    Connection connection = null;
    try
    {
      connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      ResultSet rs = statement.executeQuery("select * from catalog WHERE title = '" + title + "'" );
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
    Connection connection = null;
    try
    {
      connection = DriverManager.getConnection(url);
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      statement.executeUpdate("DELETE FROM catalog WHERE title = '" + title + "'" );
    }
    catch(SQLException e)
    {
      UnsuccessfulTask.dataBaseError();
    }
    finally { closeConnection(connection); }
  }

  protected static void closeConnection(Connection connection)
  {
    try
    {
      if(connection != null)
        connection.close();
    }
    catch(SQLException e)
    {
      UnsuccessfulTask.dataBaseError();
    }
  }
}