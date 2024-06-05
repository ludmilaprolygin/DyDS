package Presenter;

import Model.DataBaseModel;
import Presenter.Listeners.ModelListener;
import View.Messages.SuccessfulTask;
import View.StorageView;

import java.util.ArrayList;

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
            public void didSaveTVSeries() { } // En este deberia agregar algo
            @Override
            public void didSearchTermOnWiki() { }
            @Override
            public void didSearchPageOnWiki() { }
            @Override
            public void didGetExtract() { }
            @Override
            public void didRateTVSeries() { }
        });
    }

    public void deleteSelected()
    {
        String titleToDelete = storageView.getSelectedTitle();

        if(storageView.selectedEntryExists())
        {
            dataBaseModel.deleteSavedEntry(titleToDelete);
        }
        else
        {
           // Error catching clause
        }
    }
    public void saveChangesOnSelected()
    {
        String titleToChange = storageView.getSelectedTitle();
        String newInfo = storageView.getSelectedContent();

        if(storageView.selectedEntryExists())
        {
            dataBaseModel.saveSeries(titleToChange, newInfo);
            System.out.println("Ya cambie");
            // Agregar manejo de cambios en pantalla
        }
        else
        {
            // Error catching clause
        }
    }

    protected void comboBoxModelSetUp()
    {
        ArrayList<String> titles = dataBaseModel.getSavedTitles();
        Object[] savedTVSeries = titles.stream().sorted().toArray();
        storageView.setSavedTVSeriesModel(savedTVSeries);
    }

    protected void setDeletedStatus()
    {
        System.out.println("Ya borre");
        SuccessfulTask.pageDeleted();
        comboBoxModelSetUp();
        storageView.getPaneContent().setText("");
    }
}