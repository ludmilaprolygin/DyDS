package View;

import View.Rated.RatedView;
import View.Search.SearchView;
import View.Storage.StorageView;

import javax.swing.*;

public class NewMainWindow extends JFrame
{
    protected JTabbedPane tabbedPane;
    protected JPanel searchPanel;
    protected JPanel storagePanel;
    protected JPanel ratedPanel;

    public NewMainWindow()
    {
        super("TV Series Info Repo");

        tabbedPane = new JTabbedPane();

        createTabs();
        addTabs();
        frameSetUp();
    }

    protected void frameSetUp()
    {
        add(tabbedPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        setLF();
    }

    protected void setLF()
    {
        // TODO: Implement Look and Feel
    }

    protected void createTabs()
    {
        createSearchTab();
        createStorageTab();
        createRatedTab();
    }

    protected void createSearchTab()
    {
        SearchView searchView = SearchView.getInstance();
        searchPanel = searchView.getSearchPanel();
    }

    protected void createStorageTab()
    {
        StorageView storageView = StorageView.getInstance();
        storagePanel = storageView.getStoragePanel();
    }

    protected void createRatedTab() // Aplicar Singleton
    {
        RatedView ratedView = new RatedView();
        ratedPanel = ratedView.getRatedPanel();
    }

    protected void addTabs()
    {
        tabbedPane.add(searchPanel.getToolTipText(), searchPanel);
        tabbedPane.add(storagePanel.getToolTipText(), storagePanel);
        tabbedPane.add(ratedPanel.getToolTipText(), ratedPanel);
    }

    public JPanel getSearchPanel() { return searchPanel; }
    public JPanel getStoragePanel() { return storagePanel; }
    public JPanel getRatedPanel() { return ratedPanel; }
}