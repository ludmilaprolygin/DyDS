package Presenter.Implemented;

import Model.DataBaseModel;
import Model.PageModel;
import Model.Listeners.ModelListener;
import Presenter.Interfaces.ShowSearchedPagePresenter;
import utils.Messages.UnsuccessfulTask;
import View.SearchView;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import View.SearchResult;
import retrofit2.Response;
import utils.JsonParsing;
import utils.StringFormatting;

import javax.swing.*;

public class ShowSearchedPagePresenterImpl implements ShowSearchedPagePresenter
{
    protected PageModel pageModel;
    protected DataBaseModel dataBaseModel;
    protected SearchView searchView;
    protected JsonObject jsonObject;
    public ShowSearchedPagePresenterImpl(SearchView searchView, PageModel pageModel)
    {
        this.searchView = searchView;
        this.pageModel = pageModel;
        jsonObject = null;

        initializePageModelListener();
    }
    protected void initializePageModelListener()
    {
        pageModel.addListener(new ModelListener()
        {
            @Override
            public void didSearchPageOnWiki()
            {
                showPageContent();
                updateRateButton();
            }
            @Override
            public void didSearchTermOnWiki() { }
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

    public void onSelectedSearchResult()
    {
        SearchResult selectedSearchResult = ((SearchView) searchView).getSelectedSearchResult();
        searchView.disableAll();

        String pageID = selectedSearchResult.getPageID();
        pageModel.getPageFromWikipedia(pageID);
    }

    protected String getExtract()
    {
        return getExtractFromLastSearchResponse();
    }

    protected String getExtractFromLastSearchResponse()
    {
        generateJsonObjectFromLastSearchResponse();

        JsonElement selectedResultExtract = jsonObject.get("extract");

        String url = jsonObject.get("fullurl").getAsString();
        String textToDisplay = "";

        if (selectedResultExtract == null) { UnsuccessfulTask.wikipediaError("No results were found"); }
        else
        {
            String title = JsonParsing.getAttributeAsString(jsonObject, "title");
            textToDisplay = StringFormatting.HTMLtitle(title);
           // textToDisplay += "<img src=" + pageModel.getImageResponse() + "</br>";
            textToDisplay += selectedResultExtract.getAsString().replace("\\n", "\n");
            textToDisplay += StringFormatting.HTMLurl(url);
            textToDisplay = StringFormatting.textBodyToHtml(textToDisplay);
        }

        return textToDisplay;
    }
    protected String getTitleFromLastSearchResponse()
    {
        generateJsonObjectFromLastSearchResponse();
        return JsonParsing.getAttributeAsString(jsonObject, "title");
    }

    protected void updateRateButton()
    {
        SearchResult searchResult = ((SearchView) searchView).getSelectedSearchResult();
        int ratingValue = dataBaseModel.getScore(searchResult.getTitle());
        System.out.println(ratingValue);

        if(searchResult.isRated())
            ((SearchView) searchView).updateRateButton(ratingValue);
        else
            ((SearchView) searchView).updateRateButton(0);
    }

    protected void generateJsonObjectFromLastSearchResponse()
    {
        Response<String> callForPageResponse = pageModel.getResponse();
        jsonObject = JsonParsing.getQueryResultAsJsonObject(callForPageResponse, "pages");
    }

    public void showPageContent()
    {
        String textToDisplay = getExtract();
        JTextPane pageContent = searchView.getPaneContent();

        pageContent.setText(textToDisplay);
        pageContent.setCaretPosition(0);

        searchView.enableAll();
    }

    public void setDataBaseModel(DataBaseModel dataBaseModel) { this.dataBaseModel = dataBaseModel; }
}