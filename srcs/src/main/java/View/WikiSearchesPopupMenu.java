package View;

import javax.swing.*;
import java.util.ArrayList;

public class WikiSearchesPopupMenu extends JPopupMenu {
    protected ArrayList<SearchResult> searchResults;

    public WikiSearchesPopupMenu()
    {
        super();
        searchResults = new ArrayList<>();
    }

    public Iterable<SearchResult> getSearchResults() { return searchResults; }
    public void add(SearchResult searchResult)
    {
        super.add(searchResult);
        searchResults.add(searchResult);
    }
}