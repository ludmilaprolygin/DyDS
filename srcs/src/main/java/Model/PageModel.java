package Model;

import Model.APIs.APIBuilder;
import Model.APIs.WikipediaPageAPI;
import Presenter.Listeners.ModelListener;
import View.Messages.UnsuccessfulTask;

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
            ex.printStackTrace();
            UnsuccessfulTask.wikipediaError();
        }
        System.out.println(modelListenersMap.size());
        notifyListeners();
    }
}