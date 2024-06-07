package launcher;

import Model.*;
import Presenter.Implemented.*;
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

        SearchPresenterImpl searchPresenter =
                new SearchPresenterImpl(searchView, searchModel);
        ShowSearchedPagePresenterImpl showSearchedPagePresenter =
                new ShowSearchedPagePresenterImpl(searchView, pageModel);
        SavedDataBasePresenterImpl savedDataBasePresenter =
                new SavedDataBasePresenterImpl(storageView, pageModel, dataBaseModel);
        ShowSavedPagePresenterImpl showSavedPagePresenter =
                new ShowSavedPagePresenterImpl(storageView, dataBaseModel);
        ModifySavedEntriesPresenterImpl modifySavedEntriesPresenter =
                new ModifySavedEntriesPresenterImpl(storageView, dataBaseModel);
        RatedDataBasePresenterImpl ratedDataBasePresenter =
                new RatedDataBasePresenterImpl(ratedView, searchView, pageModel, dataBaseModel);

        searchView.setSearchPresenter(searchPresenter);
        searchView.setShowPagePresenter(showSearchedPagePresenter);
        searchView.setSavePresenter(savedDataBasePresenter);
        searchView.setRatePresenter(ratedDataBasePresenter);

        storageView.setShowSavedPagePresenter(showSavedPagePresenter);
        storageView.setModifyDataBasePresenter(modifySavedEntriesPresenter);

        ratedView.setRatedDataBasePresenter(ratedDataBasePresenter);

        searchPresenter.setRatedDataBaseModel(dataBaseModel);
        showSearchedPagePresenter.setDataBaseModel(dataBaseModel);
        ratedDataBasePresenter.setShowSearchedPagePresenter(showSearchedPagePresenter);
    }
}