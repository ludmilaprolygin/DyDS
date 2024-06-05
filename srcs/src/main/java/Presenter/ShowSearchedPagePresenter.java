package Presenter;

import Model.PageModel;
import Presenter.Listeners.ModelListener;
import View.Messages.UnsuccessfulTask;
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
        String modelListenerName = getModelListenerName();
        pageModel.addListener(modelListenerName, new ModelListener()
        {
            @Override
            public void taskFinished() { showPageContent(); }
        });
    }

    public void onSelectedSearchResult(SearchResult selectedSearchResult)
    {
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

    protected void generateJsonObjectFromLastSearchResponse()
    {
        Response<String> callForPageResponse = pageModel.getResponse();
        jsonObject = JsonParsing.getQueryResultAsJsonObject(callForPageResponse, "pages");
    }

    protected void showPageContent()
    {
        String textToDisplay = getExtract();
        JTextPane pageContent = view.getPaneContent();

        pageContent.setText(textToDisplay);
        pageContent.setCaretPosition(0);

        view.enableAll();
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