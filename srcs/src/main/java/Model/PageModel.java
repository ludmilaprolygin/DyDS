package Model;

import Model.APIs.APIBuilder;
import Model.APIs.WikipediaPageAPI;
import View.Messages.UnsuccessfulTask;

public class PageModel extends Model
{
    protected final WikipediaPageAPI pageAPI = APIBuilder.createPageAPI();
    protected static PageModel pageModel;

    private PageModel()
    {
        super();
    }
    public static PageModel getInstance()
    {
        if(pageModel == null)
            pageModel = new PageModel();
        return pageModel;
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