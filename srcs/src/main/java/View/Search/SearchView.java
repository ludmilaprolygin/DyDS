package View.Search;

import Presenter.PagePresenter;
import Presenter.SearchPresenter;
import View.Popup.WikiSearchesPopupMenu;
import View.View;
import dyds.tvseriesinfo.fulllogic.SearchResult;
import javax.swing.*;
import java.awt.*;

public class SearchView implements View
{
    protected JPanel searchPanel;
    protected JTextField searchTextField;
    protected JButton searchButton;
    protected JTextPane searchPageContent;
    protected JButton saveLocallyButton;
    protected WikiSearchesPopupMenu searchOptionsMenu;
    protected SearchPresenter searchPresenter;
    protected PagePresenter pagePresenter;

    public SearchView()
    {
        setUp();
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
    public void displayPopUp()
    {
        searchOptionsMenu.show(searchTextField, searchTextField.getX(), searchTextField.getY());
        initializePopupItemListener();
    }

    public JPanel getSearchPanel() { return searchPanel; }
    public JTextField getSearchTextField() { return searchTextField; }
    public JButton getSearchButton() { return searchButton; }
    public JTextPane getPaneContent() { return searchPageContent; }
    public JButton getSaveLocallyButton() { return saveLocallyButton; }
    public WikiSearchesPopupMenu getPopup() { return searchOptionsMenu; }

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
                //pagePresenter.onSelectedSearchResult();
                searchPageContent.setText(
                        "Aca iria lo que chupa de Wiki, pero pasaron cosas con los parametros: "
                        + sr.getTitle());
            });
        }
    }

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
}