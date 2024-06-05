package View;

import javax.swing.*;

public class MainWindow extends JFrame
{
    protected JTabbedPane tabbedPane;
    protected SearchView searchView;
    protected StorageView storageView;
    protected RatedView ratedView;

    public MainWindow()
    {
        super("TV Series Info Repo");

        tabbedPane = new JTabbedPane();

        createViews();
        addTabs();
        frameSetUp();
    }

    protected void frameSetUp()
    {
        add(tabbedPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setLF();

        setVisible(true);
    }

    protected void setLF()
    {
        try
        {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (Exception e) {
            System.out.println("Something went wrong with UI!");
        }
    }

    protected void createViews()
    {
        searchView = new SearchView();
        storageView = new StorageView();
        ratedView = new RatedView();
    }

    protected void addTabs()
    {
        addSearchTab();
        addStorageTab();
        addRatedTab();
    }
    protected void addSearchTab()
    {
        JPanel searchPanel = searchView.getSearchPanel();
        tabbedPane.add(searchPanel.getToolTipText(), searchPanel);
    }
    protected void addStorageTab()
    {
        JPanel storagePanel = storageView.getStoragePanel();
        tabbedPane.add(storagePanel.getToolTipText(), storagePanel);
    }
    protected void addRatedTab()
    {
        JPanel ratedPanel = ratedView.getRatedPanel();
        tabbedPane.add(ratedPanel.getToolTipText(), ratedPanel);
    }

    public SearchView getSearchView() { return searchView; }
    public StorageView getStorageView() { return storageView; }
    public RatedView getRatedView() { return ratedView; }
}