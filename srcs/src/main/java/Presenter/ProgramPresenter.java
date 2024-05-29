package Presenter;

import Model.PageModel;
import Model.SearchModel;
import View.MainWindow;
import View.Search.SearchView;
import dyds.tvseriesinfo.fulllogic.DataBase;

public class ProgramPresenter
{
    protected static MainWindow mainWindow;

    public static void main (String[]args)
    {
        mainWindow = new MainWindow();
        DataBase.loadDatabase();
        DataBase.saveInfo("test", "sarasa");

        SearchModel searchModel = new SearchModel();

        searchFunctionInitialize(searchModel);
    }

    protected static void searchFunctionInitialize(SearchModel searchModel)
    {
        SearchView searchView = SearchView.getInstance();
        //PageModel pageModel = new PageModel();
        SearchPresenter searchPresenter = new SearchPresenter(searchView, searchModel);

        searchView.setSearchPresenter(searchPresenter);

    }
//    protected static void pageFunctionInitialize(PageModel pageModel)
//    {
//        SearchView pageView = SearchView.getInstance();
//        PagePresenter pagePresenter = new PagePresenter(pageView, pageModel);
//    }
}