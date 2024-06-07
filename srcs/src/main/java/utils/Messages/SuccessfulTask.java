package utils.Messages;

import javax.swing.*;

public class SuccessfulTask
{
    public static void pageSaved()
    {
        JOptionPane.showMessageDialog(null, "The page has been saved to the database", "Saved page", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void pageDeleted()
    {
        JOptionPane.showMessageDialog(null, "The page has been deleted", "Page deleted", JOptionPane.INFORMATION_MESSAGE);
    }
}