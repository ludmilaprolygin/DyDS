package Presenter;

import Model.DataBaseModel;
import Presenter.Listeners.ModelListener;
import utils.Messages.SuccessfulTask;
import utils.Messages.UnsuccessfulTask;
import View.StorageView;

import java.util.ArrayList;
import java.util.Objects;

public class ModifySavedEntriesPresenter
{
    protected StorageView storageView;
    protected DataBaseModel dataBaseModel;
    public ModifySavedEntriesPresenter(StorageView storageView, DataBaseModel dataBaseModel)
    {
        this.storageView = storageView;
        this.dataBaseModel = dataBaseModel;

        initializeDataBaseModelListeners();
    }

    protected void initializeDataBaseModelListeners()
    {
        dataBaseModel.addListener(new ModelListener()
        {
            @Override
            public void didDeletedSaved() { setDeletedStatus(); }
            @Override
            public void didChangeTVSeries() { SuccessfulTask.pageSaved(); }
            @Override
            public void didSaveTVSeries() { }
            @Override
            public void didRateTVSeries() { }
            @Override
            public void didSearchTermOnWiki() { }
            @Override
            public void didSearchPageOnWiki() { }
            @Override
            public void didGetExtract() { }
        });
    }

    public void deleteSelected()
    {
        String titleToDelete = getCurrentTitle();
        dataBaseModel.deleteSavedEntry(titleToDelete);
    }
    public void saveChangesOnSelected()
    {
        String titleToChange = getCurrentTitle();
        String newInfo = storageView.getSelectedContent();
        dataBaseModel.changeSeries(titleToChange, newInfo);
    }
    protected String getCurrentTitle() { return storageView.getSelectedTitle(); }

    protected void comboBoxModelSetUp()
    {
        ArrayList<String> titles = dataBaseModel.getSavedTitles();
        Object[] savedTVSeries = titles.stream().sorted().toArray();
        storageView.setSavedTVSeriesModel(savedTVSeries);
    }
    protected void setDeletedStatus()
    {
        SuccessfulTask.pageDeleted();
        comboBoxModelSetUp();
        storageView.getPaneContent().setText("");
    }
}