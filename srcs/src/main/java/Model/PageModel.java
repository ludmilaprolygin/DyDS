package Model;

import Model.APIs.APIBuilder;
import Model.APIs.WikipediaPageAPI;
import Presenter.Listeners.ModelListener;
import utils.Messages.UnsuccessfulTask;

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

    protected void notifyListeners()
    {
        for(ModelListener modelListener : modelListeners)
            modelListener.didSearchPageOnWiki();
    }
}