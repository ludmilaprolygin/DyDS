package View;

import View.Search.SearchView;
import View.Storage.StorageView;

import javax.swing.*;

public class NewMainWindow extends JFrame
{
    protected JTabbedPane tabbedPane;
    protected JPanel searchPanel;
    protected JPanel storagePanel;

    public NewMainWindow()
    {
        super("TV Series Info Repo");

        tabbedPane = new JTabbedPane();

        createTabs();
        addTabs();
        setFrameConfig();
    }

    protected void setFrameConfig()
    {
        add(tabbedPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    protected void createTabs()
    {
        createSearchTab();
        createStorageTab();
    }

    protected void createSearchTab()
    {
        SearchView searchView = new SearchView();
        searchPanel = searchView.getSearchPanel();
        searchPanel.setToolTipText("Search in Wikipedia");
    }

    protected void createStorageTab()
    {
        StorageView storageView = new StorageView();
        storagePanel = storageView.getStoragePanel();
        storagePanel.setToolTipText("Stored info");
    }

    protected void addTabs()
    {
        tabbedPane.add(searchPanel.getToolTipText(), searchPanel);
        tabbedPane.add(storagePanel.getToolTipText(), storagePanel);
    }

    public JPanel getSearchPanel() { return searchPanel; }
    public JPanel getStoragePanel() { return storagePanel; }
}