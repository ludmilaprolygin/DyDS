package Model;

import Model.APIs.APIBuilder;
import Model.APIs.WikipediaSearchAPI;
import Presenter.Listeners.SearchModelListener;
import View.Messages.RetrievingWikipediaError;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;

public class SearchModel
{
    protected final WikipediaSearchAPI searchAPI = APIBuilder.createSearchAPI();
    protected SearchModelListener searchModelListener;
    protected Response<String> searchResponse;

    public void searchInWikipedia(String term)
    {
        try
        {
            searchResponse = searchAPI.searchForTerm(term).execute();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            RetrievingWikipediaError.wikipediaError();
        }
        notifySearchFinished();
    }

    protected void notifySearchFinished()
    {

    }

    public Response<String> getSearchResponse() { return searchResponse; }
}