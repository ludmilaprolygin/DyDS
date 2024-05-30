package Model;

import Model.APIs.APIBuilder;
import Model.APIs.WikipediaPageAPI;
import View.Messages.UnsuccessfulTask;

public class PageModel extends Model
{
    protected final WikipediaPageAPI pageAPI = APIBuilder.createPageAPI();

    public PageModel()
    {
        super();
    }

    public void getPageFromWikipedia(String pageID)
    {
        try
        {
            response = pageAPI.getExtractByPageID(pageID).execute();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            UnsuccessfulTask.wikipediaError();
        }
        notifyPageFinished();
    }

    protected void notifyPageFinished()
    {
        modelListener.taskFinished();
    }
}