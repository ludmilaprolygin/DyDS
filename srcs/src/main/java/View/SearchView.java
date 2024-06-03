package View;

import Presenter.ShowSearchedPagePresenter;
import Presenter.SavePagePresenter;
import Presenter.SearchPresenter;
import dyds.tvseriesinfo.fulllogic.SearchResult;
import javax.swing.*;
import java.awt.*;

public class SearchView extends View
{
    protected JPanel searchPanel;
    protected JTextField searchTextField;
    protected JButton searchButton;
    protected JTextPane searchPageContent;
    protected JButton saveLocallyButton;
    protected WikiSearchesPopupMenu searchOptionsMenu;
    protected SearchPresenter searchPresenter;
    protected ShowSearchedPagePresenter showSearchedPagePresenter;
    protected SavePagePresenter savePagePresenter;

    public SearchView()
    {
        setUp();
    }

    protected void setUp()
    {
        searchPanelSetUp();
        searchPageContentSetUp();

        disableSaveLocallyButton();

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
    protected void disableSaveLocallyButton() { saveLocallyButton.setEnabled(false); }
    protected void initializeListeners()
    {
        initializeSearchButtonListener();
        initializeTextFieldActionListener();
        initializeSaveLocallyButtonListener();
        initializeHyperlinkListener();
    }
    protected void initializeSearchButtonListener()
    {
        searchButton.addActionListener(e ->
                searchPresenter.onClickSearchButton());
    }
    protected void initializeTextFieldActionListener()
    {
        searchTextField.addActionListener(e ->
            searchPresenter.onEnterKeyPress());
    }
    protected void initializeSaveLocallyButtonListener()
    {
        saveLocallyButton.addActionListener(e ->
            savePagePresenter.onClickSaveLocallyButton());
    }

    protected void initializePopupItemListener() // searchResult parametrizado
    {
        for(SearchResult searchResult : searchOptionsMenu.getSearchResults())
        {
            searchResult.addActionListener(e ->
                showSearchedPagePresenter.onSelectedSearchResult(searchResult));
        }
    }

    public WikiSearchesPopupMenu createPopUp()
    {
        searchOptionsMenu = new WikiSearchesPopupMenu();
        return searchOptionsMenu;
    }
    public void displayPopUp()
    {
        searchOptionsMenu.show(searchTextField, searchTextField.getX(), searchTextField.getY());
        initializePopupItemListener();
    }

    public JPanel getSearchPanel() { return searchPanel; }
    public JTextField getSearchTextField() { return searchTextField; }
    public JTextPane getPaneContent() { return searchPageContent; }

    public void setSearchPresenter(SearchPresenter searchPresenter) { this.searchPresenter = searchPresenter; }
    public void setShowPagePresenter(ShowSearchedPagePresenter showSearchedPagePresenter) { this.showSearchedPagePresenter = showSearchedPagePresenter; }
    public void setSavePresenter(SavePagePresenter savePagePresenter) { this.savePagePresenter = savePagePresenter; }

    public void disableAll()
    {
        for(Component c: searchPanel.getComponents())
            c.setEnabled(false);
        searchPageContent.setEnabled(false);
    }
    public void enableAll()
    {
        for(Component c: searchPanel.getComponents())
            c.setEnabled(true);
        searchPageContent.setEnabled(true);
    }

    public String getSearchedTitle() { return searchTextField.getText();}
}