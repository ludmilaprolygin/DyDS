package Presenter;

import Model.*;
import Presenter.Listeners.ModelListener;
import View.SearchView;
import View.WikiSearchesPopupMenu;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import View.SearchResult;
import retrofit2.Response;
import utils.JsonParsing;

import java.util.ArrayList;

public class SearchPresenter
{
    protected SearchModel searchModel;
    protected DataBaseModel dataBaseModel;
    protected SearchView searchView;

    public SearchPresenter(SearchView searchView, SearchModel searchModel)
    {
        this.searchView = searchView;
        this.searchModel = searchModel;

        initializeSearchModelListeners();
    }
    protected void initializeSearchModelListeners()
    {
        searchModel.addListener(new ModelListener() {
            @Override
            public void didSearchTermOnWiki()
            {
                showSearchResults();
            }
            @Override
            public void didSearchPageOnWiki() { }
            @Override
            public void didGetExtract() { }
            @Override
            public void didDeletedSaved() { }
            @Override
            public void didSaveTVSeries() { }
            @Override
            public void didRateTVSeries() { }
            @Override
            public void didChangeTVSeries() { }
        });
    }

    public void onEnterKeyPress() { onClickSearchButton(); }
    public void onClickSearchButton()
    {
        new Thread(() ->
        {
            String termToSearch = searchView.getSearchedTitle();
            searchView.disableAll();
            termToSearch += " (Tv series) articletopic:\"television\"";
            searchModel.searchInWikipedia(termToSearch);
        }).start();
    }

    public void setRatedDataBaseModel(DataBaseModel dataBaseModel)
    { this.dataBaseModel = dataBaseModel; }

    protected void showSearchResults()
    {
        Response<String> searchResponse = searchModel.getResponse();
        JsonArray jsonResults = JsonParsing.getQueryResultsAsJsonArray(searchResponse, "search");

        createSearchResultsPopup(jsonResults);

        searchView.enableAll();
    }
    protected void createSearchResultsPopup(JsonArray jsonResults)
    {
        WikiSearchesPopupMenu searchOptionsMenu = searchView.createPopUp();
        ArrayList<SearchResult> allSearchResults = new ArrayList<SearchResult>();

        for (JsonElement je : jsonResults)
        {
            SearchResult searchResult = createSearchResult(je);

            searchOptionsMenu.add(searchResult);
            allSearchResults.add(searchResult);
        }

        ArrayList<String> ratedTitles = dataBaseModel.getRatedTitles();
        String title;

        for(SearchResult searchResult : allSearchResults)
        {
            title = searchResult.getTitle();
            if(ratedTitles.contains(title))
            {
                searchResult.setIsRatedIcon();
                searchResult.setRated(true);
            }
        }

        searchView.displayPopUp();
    }
    protected SearchResult createSearchResult(JsonElement je)
    {
        JsonObject jsonSearchResult = je.getAsJsonObject();

        String searchResultTitle = JsonParsing.getAttributeAsString(jsonSearchResult, "title");
        String searchResultPageId = JsonParsing.getAttributeAsString(jsonSearchResult, "pageid");
        String searchResultSnippet = JsonParsing.getAttributeAsString(jsonSearchResult, "snippet");

        SearchResult searchResult = new SearchResult(searchResultTitle, searchResultPageId, searchResultSnippet);

        String HTMLText = formatToHTML(searchResultTitle, searchResultSnippet);
        searchResult.setText(HTMLText);

        return searchResult;
    }

    protected String formatToHTML(String title, String snippet)
    {
        String toReturn = "<html><font face=\"arial\">" + title + ": " + snippet;
        toReturn = toReturn.replace("<span class=\"searchmatch\">", "")
                .replace("</span>", "");

        return toReturn;
    }
}