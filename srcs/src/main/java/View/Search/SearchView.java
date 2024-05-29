package View.Search;

import Presenter.PagePresenter;
import Presenter.SearchPresenter;
import View.Popup.WikiSearchesPopupMenu;
import View.View;
import dyds.tvseriesinfo.fulllogic.SearchResult;
import retrofit2.Response;

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
    protected SearchPresenter searchPresenter;
    protected PagePresenter pagePresenter;

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

        initializeListeners();
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

    public void setSearchPresenter(SearchPresenter searchPresenter) { this.searchPresenter = searchPresenter; }
    public void setPagePresenter(PagePresenter pagePresenter) { this.pagePresenter = pagePresenter; }

    protected void initializeListeners()
    {
        initializeSearchButtonListener();
        initializeTextFieldActionListener();
    }
    protected void initializeSearchButtonListener()
    {
        searchButton.addActionListener(e ->
        {
            searchPresenter.onClickSearchButton();
        });
    }
    protected void initializeTextFieldActionListener()
    {
        searchTextField.addActionListener(e ->
        {
            searchPresenter.onEnterKeyPress();
        });
    }
    protected void initializePopupItemListener()
    {
        for(SearchResult sr : searchOptionsMenu.getSearchResults())
        {
            sr.addActionListener(e ->
            {
                searchPresenter.showPageResults(sr);
            });
        }
    }
}