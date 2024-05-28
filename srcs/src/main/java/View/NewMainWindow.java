package View;

import javax.swing.*;
import java.awt.*;

public class NewMainWindow extends JFrame
{
    protected JTabbedPane tabbedPane;
    protected JPanel searchPanel;
    protected JPanel storagePanel;

    public NewMainWindow()
    {
        super("TV Series Info Repo");
        tabbedPane = new JTabbedPane();
        searchPanel = SearchView.createSearchTab();
        storagePanel = StorageView.createStorageTab();

        addTabs();
    }

    protected void addTabs()
    {
        Component searchTab = tabbedPane.add("Search in Wikipedia", searchPanel);
        Component storageTab = tabbedPane.add("Stored info", storagePanel);
    }

    public static void main (String[]args)
    {
        NewMainWindow mainWindow = new NewMainWindow();
        mainWindow.add(mainWindow.tabbedPane);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(400, 400);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
    }
}