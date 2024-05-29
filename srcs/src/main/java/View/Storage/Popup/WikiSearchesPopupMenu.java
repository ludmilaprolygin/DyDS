package View.Storage.Popup;

import dyds.tvseriesinfo.fulllogic.SearchResult;

import javax.swing.*;
import java.util.ArrayList;

public class WikiSearchesPopupMenu extends JPopupMenu {
    protected ArrayList<SearchResult> searchResults;

    public WikiSearchesPopupMenu()
    {
        super();
        searchResults = new ArrayList<SearchResult>();
    }

    public Iterable<SearchResult> getSearchResults() { return searchResults; }
    public void add(SearchResult searchResult)
    {
        super.add(searchResult);
        searchResults.add(searchResult);
        System.out.println("Agrego un search result nuevo");
    }
}