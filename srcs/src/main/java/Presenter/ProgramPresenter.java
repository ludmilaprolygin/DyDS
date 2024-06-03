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

        SearchView searchView = mainWindow.getSearchView();
        StorageView storageView = mainWindow.getStorageView();
        //RatedView ratedView = mainWindow.getRatedView();

        SearchModel searchModel = new SearchModel();
        PageModel pageModel = new PageModel();
        DataBaseModel dataBaseModel = new DataBaseModel();
//        DataBaseExtractModel dataBaseExtractModel = new DataBaseExtractModel();
//        DataBaseModifierModel dataBaseModifierModel = new DataBaseModifierModel();

        SearchPresenter searchPresenter =
                new SearchPresenter(searchView, searchModel);
        ShowSearchedPagePresenter showSearchedPagePresenter =
                new ShowSearchedPagePresenter(searchView, pageModel);
        SavePagePresenter savePagePresenter =
                new SavePagePresenter(storageView, pageModel, dataBaseModel);
        ShowSavedPagePresenter showSavedPagePresenter =
                new ShowSavedPagePresenter(storageView, dataBaseModel);
        ModifySavedEntriesPresenter modifySavedEntriesPresenter =
                new ModifySavedEntriesPresenter(storageView, dataBaseModel);

        searchView.setSearchPresenter(searchPresenter);
        searchView.setShowPagePresenter(showSearchedPagePresenter);
        searchView.setSavePresenter(savePagePresenter);

        storageView.setShowSavedPagePresenter(showSavedPagePresenter);
        storageView.setModifyDataBasePresenter(modifySavedEntriesPresenter);
    }
}