package Model;

import Model.APIs.APIBuilder;
import Model.APIs.WikipediaSearchAPI;
import Model.Listeners.SearchModelListener;

import java.util.ArrayList;

public class SearchModel
{
    protected final static WikipediaSearchAPI searchAPI = APIBuilder.createSearchAPI();
    protected ArrayList<SearchModelListener> listeners = new ArrayList<SearchModelListener>();
}