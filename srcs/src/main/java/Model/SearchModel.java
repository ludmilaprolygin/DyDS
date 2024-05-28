package Model;

import Model.APIs.WikipediaSearchAPI;
import Model.Listeners.SearchModelListener;

import java.util.ArrayList;

public class SearchModel
{
    protected WikipediaSearchAPI searchAPI;
    protected ArrayList<SearchModelListener> listeners = new ArrayList<SearchModelListener>();
}