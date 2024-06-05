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

public class SearchPresenter
{
    protected SearchModel searchModel;
    protected SearchView searchView;

    public SearchPresenter(SearchView searchView, SearchModel searchModel)
    {
        this.searchView = searchView;
        this.searchModel = searchModel;

        initializeSearchModelListeners();
    }
    protected void initializeSearchModelListeners()
    {
        String modelListenerName = getModelListenerName();
        searchModel.addListener(modelListenerName, new ModelListener()
        {
            @Override
            public void taskFinished()
            {
                showSearchResults();
            }
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

        for (JsonElement je : jsonResults)
        {
            SearchResult searchResult = createSearchResult(je);

            searchOptionsMenu.add(searchResult);
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

    protected String getModelListenerName()
    {
        String modelListenerName = getClass().getName()
                .replace("Presenter", "")
                .replace(".", "");
        modelListenerName += "Listener";
        return modelListenerName;
    }
}