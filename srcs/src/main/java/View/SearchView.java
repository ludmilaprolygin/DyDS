package View;

import Presenter.Implemented.RatedDataBasePresenterImpl;
import Presenter.Implemented.ShowSearchedPagePresenterImpl;
import Presenter.Implemented.SavedDataBasePresenterImpl;
import Presenter.Implemented.SearchPresenterImpl;
import utils.ImageManager;

import javax.swing.*;
import java.awt.*;

public class SearchView extends View
{
    protected JPanel searchPanel;
    protected JTextField searchTextField;
    protected JButton searchButton;
    protected JTextPane searchPageContent;
    protected JButton saveLocallyButton;
    protected JButton rateButton;
    protected SearchResult selectedSearchResult;
    protected WikiSearchesPopupMenu searchOptionsMenu;
    protected SearchPresenterImpl searchPresenter;
    protected ShowSearchedPagePresenterImpl showSearchedPagePresenter;
    protected SavedDataBasePresenterImpl savedDataBasePresenter;
    protected RatedDataBasePresenterImpl ratedDataBasePresenter;

    public SearchView()
    {
        setUp();
    }

    protected void setUp()
    {
        searchPanelSetUp();
        searchPageContentSetUp();
        disableSaveLocallyButton();
        rateTVShowButtonSetUp();
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
    protected void rateTVShowButtonSetUp()
    {
        disableRateTVShowButton();
        rateButton.setIcon(new ImageIcon(ImageManager.getUnratedImageURL()));
    }
    protected void disableRateTVShowButton() { rateButton.setEnabled(false); }

    protected void initializeListeners()
    {
        super.initializeListeners();
        initializeSearchButtonListener();
        initializeTextFieldActionListener();
        initializeSaveLocallyButtonListener();
        initializeRateButtonListener();
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
            savedDataBasePresenter.onClickSaveLocallyButton());
    }
    protected void initializeRateButtonListener()
    {
        rateButton.addActionListener(e ->
            ratedDataBasePresenter.onClickRateButton());
    }
    protected void initializePopupItemListener()
    {
        for(SearchResult searchResult : searchOptionsMenu.getSearchResults())
        {
            searchResult.addActionListener(e -> {
                selectedSearchResult = searchResult;
                showSearchedPagePresenter.onSelectedSearchResult();});
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
    public JTextPane getPaneContent() { return searchPageContent; }
    public JButton getRateButton() { return rateButton; }
    public String getSearchedTitle() { return searchTextField.getText();}
    public SearchResult getSelectedSearchResult() { return selectedSearchResult; }

    public void updateSearchResult(SearchResult searchResult)
    {
        selectedSearchResult = searchResult;
    }
    public void updateRateButton(int ratingValue)
    { rateButton.setIcon(new ImageIcon(ImageManager.getImageURL(ratingValue))); }
    public void setSearchPresenter(SearchPresenterImpl searchPresenter) { this.searchPresenter = searchPresenter; }
    public void setShowPagePresenter(ShowSearchedPagePresenterImpl showSearchedPagePresenter) { this.showSearchedPagePresenter = showSearchedPagePresenter; }
    public void setSavePresenter(SavedDataBasePresenterImpl savedDataBasePresenter) { this.savedDataBasePresenter = savedDataBasePresenter; }
    public void setRatePresenter(RatedDataBasePresenterImpl ratedDataBasePresenter) { this.ratedDataBasePresenter = ratedDataBasePresenter; }

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