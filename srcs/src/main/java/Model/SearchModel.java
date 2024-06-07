package Model;

import Model.APIs.APIBuilder;
import Model.APIs.WikipediaSearchAPI;
import Presenter.Listeners.ModelListener;
import utils.Messages.UnsuccessfulTask;
import java.io.IOException;

public class SearchModel extends APIModel
{
    protected final WikipediaSearchAPI searchAPI = APIBuilder.createSearchAPI();

    public SearchModel()
    {
        super();
    }

    public void searchInWikipedia(String term)
    {
        try
        {
            response = searchAPI.searchForTerm(term).execute();
        }
        catch (IOException ex)
        { UnsuccessfulTask.wikipediaError(); }

        notifyListeners();
    }

    protected void notifyListeners()
    {
        for(ModelListener modelListener : modelListeners)
            modelListener.didSearchTermOnWiki();
    }
}