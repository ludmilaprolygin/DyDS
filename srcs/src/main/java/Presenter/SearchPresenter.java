package Presenter;

import Model.Listeners.SearchModelListener;
import Model.*;
import View.Search.SearchView;

import java.util.ArrayList;

public class SearchPresenter
{
    protected SearchView searchView;
    protected SearchModel searchModel;
    protected ArrayList<SearchModelListener> listeners = new ArrayList<SearchModelListener>();
}