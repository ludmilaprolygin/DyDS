package Presenter;

import Model.*;
import View.*;
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
        ShowPagePresenter showPagePresenter =
                new ShowPagePresenter(searchView, pageModel);
        SavePagePresenter savePagePresenter =
                new SavePagePresenter(storageView, pageModel);

        searchView.setSearchPresenter(searchPresenter);
        searchView.setPagePresenter(showPagePresenter);
        searchView.setSavePresenter(savePagePresenter);
    }
}