package View.Search;

import View.Popup.WikiSearchesPopupMenu;
import View.View;

import javax.swing.*;

public class SearchView implements View
{
    protected static SearchView searchView;
    protected JPanel searchPanel;
    protected JTextField searchTextField;
    protected JButton searchButton;
    protected JTextPane searchPageContent;
    protected JButton saveLocallyButton;
    protected WikiSearchesPopupMenu searchOptionsMenu;

    private SearchView()
    {
        setUp();
    }
    public static SearchView getInstance()
    {
        if(searchView == null)
            searchView = new SearchView();
        return searchView;
    }

    protected void setUp()
    {
        searchPanelSetUp();
        searchPageContentSetUp();
    }
    protected void searchPanelSetUp()
    {
        searchPanel.setToolTipText("Search in Wikipedia");
    }
    protected void searchPageContentSetUp()
    {
        searchPageContent.setContentType("text/html");
        searchPageContent.setEditable(false);
    }

    public WikiSearchesPopupMenu createPopUp()
    {
        searchOptionsMenu = new WikiSearchesPopupMenu();
        return searchOptionsMenu;
    }
    public void displayPopUp() {searchOptionsMenu.show(searchTextField, searchTextField.getX(), searchTextField.getY());}

    public JPanel getSearchPanel() { return searchPanel; }
    public JTextField getSearchTextField() { return searchTextField; }
    public JButton getSearchButton() { return searchButton; }
    public JTextPane getPaneContent() { return searchPageContent; }
    public JButton getSaveLocallyButton() { return saveLocallyButton; }
    public WikiSearchesPopupMenu getPopUp() { return searchOptionsMenu; }
}