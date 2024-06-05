package View;

import Presenter.RatedDataBasePresenter;
import Presenter.ShowSearchedPagePresenter;
import Presenter.SavedDataBasePresenter;
import Presenter.SearchPresenter;
import utils.ImageManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SearchView extends View
{
    protected JPanel searchPanel;
    protected JTextField searchTextField;
    protected JButton searchButton;
    protected JTextPane searchPageContent;
    protected JButton saveLocallyButton;
    private JButton rateButton;
    protected WikiSearchesPopupMenu searchOptionsMenu;
    protected SearchPresenter searchPresenter;
    protected ShowSearchedPagePresenter showSearchedPagePresenter;
    protected SavedDataBasePresenter savedDataBasePresenter;
    protected RatedDataBasePresenter ratedDataBasePresenter;

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
    public JButton getRateButton() { return rateButton; }

    public void setSearchPresenter(SearchPresenter searchPresenter) { this.searchPresenter = searchPresenter; }
    public void setShowPagePresenter(ShowSearchedPagePresenter showSearchedPagePresenter) { this.showSearchedPagePresenter = showSearchedPagePresenter; }
    public void setSavePresenter(SavedDataBasePresenter savedDataBasePresenter) { this.savedDataBasePresenter = savedDataBasePresenter; }
    public void setRatePresenter(RatedDataBasePresenter ratedDataBasePresenter) { this.ratedDataBasePresenter = ratedDataBasePresenter; }

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