package Presenter;

import Model.*;
import Presenter.Listeners.ModelListener;
import View.SearchView;
import View.WikiSearchesPopupMenu;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import dyds.tvseriesinfo.fulllogic.SearchResult;
import retrofit2.Response;
import utils.JsonParsing;

import javax.swing.*;

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
            SearchResult sr = new SearchResult(je);
            searchOptionsMenu.add(sr);
        }

        searchView.displayPopUp();
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