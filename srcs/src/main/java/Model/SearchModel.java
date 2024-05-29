package Model;

import Model.APIs.APIBuilder;
import Model.APIs.WikipediaSearchAPI;
import Presenter.Listeners.ModelListener;
import Presenter.Listeners.SearchModelListener;
import View.Messages.UnsuccessfulTask;
import retrofit2.Response;

import java.io.IOException;

public class SearchModel extends Model
{
    protected final WikipediaSearchAPI searchAPI = APIBuilder.createSearchAPI();
    protected static SearchModel searchModel;

    private SearchModel()
    {
        super();
    }
    public static SearchModel getInstance()
    {
        if(searchModel == null)
            searchModel = new SearchModel();
        return searchModel;
    }

    public void searchInWikipedia(String term)
    {
        try
        {
            response = searchAPI.searchForTerm(term).execute();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            UnsuccessfulTask.wikipediaError();
        }
        notifySearchFinished();
    }

    protected void notifySearchFinished()
    {
        modelListener.taskFinished();
    }
}