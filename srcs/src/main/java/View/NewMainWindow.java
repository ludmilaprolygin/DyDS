package View;

import Model.APIs.APIBuilder;
import Model.APIs.WikipediaPageAPI;
import Model.APIs.WikipediaSearchAPI;
import View.Search.SearchView;
import View.Storage.Popup.StoredInfoPopupMenu;
import View.Storage.StorageView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dyds.tvseriesinfo.fulllogic.DataBase;
import dyds.tvseriesinfo.fulllogic.MainWindow;
import dyds.tvseriesinfo.fulllogic.SearchResult;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

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
        this.searchPanel = searchView.getSearchPanel();
    }

    protected void createStorageTab()
    {
        StorageView storageView = new StorageView();
        this.storagePanel = storageView.getStoragePanel();
    }

    protected void addTabs()
    {
        tabbedPane.add(searchPanel.getToolTipText(), searchPanel);
        tabbedPane.add(storagePanel.getToolTipText(), storagePanel);
    }

    public JPanel getSearchPanel() { return searchPanel; }
    public JPanel getStoragePanel() { return storagePanel; }
}