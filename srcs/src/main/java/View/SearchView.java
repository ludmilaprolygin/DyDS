package View;

import javax.swing.*;

public class SearchView
{
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
}