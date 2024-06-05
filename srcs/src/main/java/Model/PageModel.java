package Model;

import Model.APIs.APIBuilder;
import Model.APIs.WikipediaPageAPI;
import Presenter.Listeners.ModelListener;
import View.Messages.UnsuccessfulTask;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Response;
import utils.JsonParsing;

public class PageModel extends APIModel
{
    protected final WikipediaPageAPI pageAPI = APIBuilder.createPageAPI();

    public PageModel() { super(); }

    public void getPageFromWikipedia(String pageID)
    {
        try
        {
            response = pageAPI.getExtractByPageID(pageID).execute();
        }
        catch (Exception ex)
        {
            UnsuccessfulTask.wikipediaError();
        }
        notifyListeners();
    }
}