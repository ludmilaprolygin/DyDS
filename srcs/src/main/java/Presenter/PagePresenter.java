package Presenter;

import Model.PageModel;
import Presenter.Listeners.ModelListener;
import View.Messages.UnsuccessfulTask;
import View.Search.SearchView;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dyds.tvseriesinfo.fulllogic.SearchResult;
import retrofit2.Response;
import utils.JsonProcessing;
import utils.StringFormatting;

import javax.swing.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PagePresenter
{
    protected PageModel pageModel;
    protected SearchView searchView;

    public PagePresenter(SearchView searchView, PageModel pageModel)
    {
        this.searchView = searchView;
        this.pageModel = pageModel;

        initializePageModelListeners();
    }
    protected void initializePageModelListeners()
    {
        pageModel.addListener(new ModelListener()
        {
            @Override
            public void taskFinished() { showPageContent(); }
        });
    }

    public void onSelectedSearchResult()
    {
        SearchResult selectedSearchResult = selectedSearchResult();
        String pageID = selectedSearchResult.getPageID();
        pageModel.getPageFromWikipedia(pageID);
    }
    protected SearchResult selectedSearchResult()
    {
        Iterable<SearchResult> searchResults = searchView.getPopup().getSearchResults();
        SearchResult selectedSearchResult = null;

        for (SearchResult searchResult : searchResults)
        {
            if(searchResult.isSelected())
            {
                selectedSearchResult = searchResult;
                System.out.println(selectedSearchResult.getTitle());
                break;
            }
        }

        return selectedSearchResult;
    }

    public void showPageContent()
    {
        SearchResult searchResult = selectedSearchResult();
        Response<String> callForPageResponse = pageModel.getResponse();
        JTextPane searchPageContent = searchView.getPaneContent();

        JsonObject jsonObject = JsonProcessing.getQueryResultAsJsonObject(callForPageResponse, "pages");
        JsonElement selectedResultExtract = jsonObject.get("extract");

        String url = jsonObject.get("fullurl").getAsString();
        String textToDisplay = "";

        if (selectedResultExtract == null) { UnsuccessfulTask.wikipediaError("No results were found"); }
        else
        {
            String title = searchResult.getTitle();
            textToDisplay = StringFormatting.HTMLtitle(title);
            textToDisplay += selectedResultExtract.getAsString().replace("\\n", "\n");
            textToDisplay += StringFormatting.HTMLurl(url);
            textToDisplay = StringFormatting.textToHtml(textToDisplay);
        }
        searchPageContent.setText(textToDisplay);
        searchPageContent.setCaretPosition(0);

        searchView.enableAll();
    }
}