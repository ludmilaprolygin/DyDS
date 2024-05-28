package View;

import Presenter.SearchPresenter;

import javax.swing.*;

public class SearchView
{
    protected static final SearchPresenter searchPresenter = new SearchPresenter();

    protected JPanel searchPanel;
    protected JTextField searchTextField;
    protected JButton searchButton;
    protected JTextPane searchPageContent;
    protected JButton saveLocallyButton;

    public static JPanel createSearchTab()
    {
        SearchView searchView = new SearchView();
        return searchView.getSearchPanel();
    }

    public JPanel getSearchPanel() { return searchPanel; }
    public JTextField getSearchTextField() { return searchTextField; }
    public JButton getSearchButton() { return searchButton; }
    public JTextPane getSearchPageContent() { return searchPageContent; }
    public JButton getSaveLocallyButton() { return saveLocallyButton; }
}