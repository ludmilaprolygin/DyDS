package View.Messages;

import javax.swing.*;

public class UnsuccessfulTask
{
    public static void wikipediaError(String message)
    {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    public static void wikipediaError()
    {
        wikipediaError("Error retrieving Wikipedia page");
    }

    public static void deleteError()
    {
        JOptionPane.showMessageDialog(null, "The page was not deleted", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void saveError()
    {
        JOptionPane.showMessageDialog(null, "The page was not saved", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void changesError()
    {
        JOptionPane.showMessageDialog(null, "The changes weren't made", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void lfError()
    {
        JOptionPane.showMessageDialog(null, "Something went wrong with UI", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void dataBaseError()
    {
        JOptionPane.showMessageDialog(null, "Something went wrong with the database", "Error", JOptionPane.ERROR_MESSAGE);
    }
}