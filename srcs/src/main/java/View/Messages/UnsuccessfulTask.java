package View.Messages;

import javax.swing.*;

public class UnsuccessfulTask
{
    public static void wikipediaError()
    {
        JOptionPane.showMessageDialog(null, "Error retrieving Wikipedia page", "Error", JOptionPane.ERROR_MESSAGE);
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
        JOptionPane.showMessageDialog(null, "The changed weren't made", "Error", JOptionPane.ERROR_MESSAGE);
    }
}