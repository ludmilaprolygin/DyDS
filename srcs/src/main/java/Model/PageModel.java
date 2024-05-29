package Model;

import Model.APIs.APIBuilder;
import Model.APIs.WikipediaPageAPI;
import Presenter.Listeners.PageModelListener;
import View.Messages.RetrievingWikipediaError;
import retrofit2.Response;

public class PageModel
{
    protected final WikipediaPageAPI pageAPI = APIBuilder.createPageAPI();
    protected PageModelListener pageModelListener;
    protected Response<String> pageResponse;

    public void getPageFromWikipedia(String pageID)
    {
        try
        {
            pageResponse = pageAPI.getExtractByPageID(pageID).execute();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            RetrievingWikipediaError.wikipediaError();
        }
        notifyPageFinished();
    }

    protected void notifyPageFinished()
    {

    }

    public Response<String> getPageResponse() { return pageResponse; }
}