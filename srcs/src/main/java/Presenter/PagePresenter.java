package Presenter;

import Model.PageModel;
import View.Messages.UnsuccessfulTask;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Response;
import utils.JsonParsing;
import utils.StringFormatting;

public abstract class PagePresenter
{
    protected PageModel pageModel;

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
        String title = JsonParsing.getAttributeAsString(jsonObject, "title");

        return title;
    }
    protected JsonObject getJsonObjectFromLastSearchResponse()
    {
        Response<String> callForPageResponse = pageModel.getResponse();
        JsonObject jsonObject = JsonParsing.getQueryResultAsJsonObject(callForPageResponse, "pages");

        return jsonObject;
    }
}