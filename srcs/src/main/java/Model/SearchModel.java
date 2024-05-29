package Model;

import Model.APIs.APIBuilder;
import Model.APIs.WikipediaSearchAPI;
import View.Messages.UnsuccessfulTask;

import java.io.IOException;

public class SearchModel extends Model
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