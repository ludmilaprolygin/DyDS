package View.Search;

import Presenter.SearchPresenter;

import javax.swing.*;

public class SearchView
{
    protected final SearchPresenter searchPresenter = new SearchPresenter();

    protected JPanel searchPanel;
    protected JTextField searchTextField;
    protected JButton searchButton;
    protected JTextPane searchPageContent;
    protected JButton saveLocallyButton;

    public SearchView()
    {
        searchPageContentConfig();
    }

    protected void searchPageContentConfig()
    {
        searchPageContent.setContentType("text/html");
        searchPageContent.setEditable(false);
    }

    public JPanel getSearchPanel() { return searchPanel; }
    public JTextField getSearchTextField() { return searchTextField; }
    public JButton getSearchButton() { return searchButton; }
    public JTextPane getSearchPageContent() { return searchPageContent; }
    public JButton getSaveLocallyButton() { return saveLocallyButton; }
}