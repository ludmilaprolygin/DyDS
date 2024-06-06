package Presenter;

import Model.DataBaseModel;
import Model.PageModel;
import Presenter.Listeners.ModelListener;
import utils.Messages.UnsuccessfulTask;
import View.SearchView;
import View.View;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import View.SearchResult;
import retrofit2.Response;
import utils.JsonParsing;
import utils.StringFormatting;

import javax.swing.*;

public class ShowSearchedPagePresenter
{
    protected PageModel pageModel;
    protected DataBaseModel dataBaseModel;
    protected View view;
    protected JsonObject jsonObject;
    public ShowSearchedPagePresenter(SearchView searchView, PageModel pageModel)
    {
        this.view = searchView;
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
        });
    }

    public void onSelectedSearchResult()
    {
        SearchResult selectedSearchResult = ((SearchView) view).getSelectedSearchResult();
        view.disableAll();

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
        SearchResult searchResult = ((SearchView) view).getSelectedSearchResult();
        int ratingValue = dataBaseModel.getScore(searchResult.getTitle());
        System.out.println(ratingValue);

        if(searchResult.isRated())
            ((SearchView) view).updateRateButton(ratingValue);
        else
            ((SearchView) view).updateRateButton(0);
    }

    protected void generateJsonObjectFromLastSearchResponse()
    {
        Response<String> callForPageResponse = pageModel.getResponse();
        jsonObject = JsonParsing.getQueryResultAsJsonObject(callForPageResponse, "pages");
    }

    public void showPageContent()
    {
        String textToDisplay = getExtract();
        JTextPane pageContent = view.getPaneContent();

        pageContent.setText(textToDisplay);
        pageContent.setCaretPosition(0);

        view.enableAll();
    }

    public void setDataBaseModel(DataBaseModel dataBaseModel) { this.dataBaseModel = dataBaseModel; }
}