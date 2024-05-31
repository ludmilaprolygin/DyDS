package Presenter;

import Model.PageModel;
import View.Messages.UnsuccessfulTask;
import View.View;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Response;
import utils.JsonParsing;
import utils.StringFormatting;

import javax.swing.*;

public abstract class PagePresenter
{
    protected PageModel pageModel;
    protected View view;

    public PagePresenter(View view, PageModel pageModel)
    {
        this.view = view;
        this.pageModel = pageModel;
        initializePageModelListener();
    }
    protected abstract void initializePageModelListener();

    protected String getExtractFromLastSearchResponse()
    {
        JsonObject jsonObject = getJsonObjectFromLastSearchResponse();

        JsonElement selectedResultExtract = jsonObject.get("extract");

        String url = jsonObject.get("fullurl").getAsString();
        String textToDisplay = "";

        if (selectedResultExtract == null) { UnsuccessfulTask.wikipediaError("No results were found"); }
        else
        {
            String title = JsonParsing.getAttributeAsString(jsonObject, "title");
            textToDisplay = StringFormatting.HTMLtitle(title);
            textToDisplay += selectedResultExtract.getAsString().replace("\\n", "\n");
            textToDisplay += StringFormatting.HTMLurl(url);
            textToDisplay = StringFormatting.textToHtml(textToDisplay);
        }

        return textToDisplay;
    }
    protected String getTitleFromLastSearchResponse()
    {
        JsonObject jsonObject = getJsonObjectFromLastSearchResponse();
        return JsonParsing.getAttributeAsString(jsonObject, "title");
    }
    protected JsonObject getJsonObjectFromLastSearchResponse()
    {
        Response<String> callForPageResponse = pageModel.getResponse();
        return JsonParsing.getQueryResultAsJsonObject(callForPageResponse, "pages");
    }

    protected void showPageContent()
    {
        String textToDisplay = getExtractFromLastSearchResponse();
        JTextPane pageContent = view.getPaneContent();

        pageContent.setText(textToDisplay);
        pageContent.setCaretPosition(0);
    }
}