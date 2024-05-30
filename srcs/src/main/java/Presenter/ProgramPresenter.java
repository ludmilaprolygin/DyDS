package Presenter;

import Model.PageModel;
import Model.SearchModel;
import View.MainWindow;
import View.Rated.RatedView;
import View.Search.SearchView;
import View.Storage.StorageView;
import dyds.tvseriesinfo.fulllogic.DataBase;

public class ProgramPresenter
{
    protected static MainWindow mainWindow;

    public static void main (String[]args)
    {
        mainWindow = new MainWindow();
        DataBase.loadDatabase();
        DataBase.saveInfo("test", "sarasa");

        SearchView searchView = mainWindow.getSearchView();
        StorageView storageView = mainWindow.getStorageView();
        RatedView ratedView = mainWindow.getRatedView();

        SearchModel searchModel = new SearchModel();
        PageModel pageModel = new PageModel();

        SearchPresenter searchPresenter =
                new SearchPresenter(searchView, searchModel);
        PagePresenter pagePresenter =
                new PagePresenter(searchView, pageModel);

        searchView.setSearchPresenter(searchPresenter);
        searchView.setPagePresenter(pagePresenter);
    }
}