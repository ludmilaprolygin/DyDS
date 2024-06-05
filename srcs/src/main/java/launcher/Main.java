package launcher;

import Model.*;
import Presenter.*;
import View.*;
import Model.DataBase.AbstractDataBase;

public class Main
{
    protected static MainWindow mainWindow;

    public static void main (String[]args)
    {
        AbstractDataBase.loadDatabase();
        mainWindow = new MainWindow();

        SearchView searchView = mainWindow.getSearchView();
        StorageView storageView = mainWindow.getStorageView();
        RatedView ratedView = mainWindow.getRatedView();

        SearchModel searchModel = new SearchModel();
        PageModel pageModel = new PageModel();
        DataBaseModel dataBaseModel = new DataBaseModel();

        SearchPresenter searchPresenter =
                new SearchPresenter(searchView, searchModel);
        ShowSearchedPagePresenter showSearchedPagePresenter =
                new ShowSearchedPagePresenter(searchView, pageModel);
        SavedDataBasePresenter savedDataBasePresenter =
                new SavedDataBasePresenter(storageView, pageModel, dataBaseModel);
        ShowSavedPagePresenter showSavedPagePresenter =
                new ShowSavedPagePresenter(storageView, dataBaseModel);
        ModifySavedEntriesPresenter modifySavedEntriesPresenter =
                new ModifySavedEntriesPresenter(storageView, dataBaseModel);
        RatedDataBasePresenter ratedDataBasePresenter =
                new RatedDataBasePresenter(ratedView, searchView, pageModel, dataBaseModel);

        searchView.setSearchPresenter(searchPresenter);
        searchView.setShowPagePresenter(showSearchedPagePresenter);
        searchView.setSavePresenter(savedDataBasePresenter);
        searchView.setRatePresenter(ratedDataBasePresenter);

        storageView.setShowSavedPagePresenter(showSavedPagePresenter);
        storageView.setModifyDataBasePresenter(modifySavedEntriesPresenter);
    }
}