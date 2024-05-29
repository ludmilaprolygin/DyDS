package View.Messages;

import javax.swing.*;

public class RetrievingWikipediaError
{
    public static void wikipediaError()
    {
        JOptionPane.showMessageDialog(null, "Error retrieving Wikipedia page", "Error", JOptionPane.ERROR_MESSAGE);
    }
}