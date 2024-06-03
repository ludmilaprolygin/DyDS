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
        dataBaseModel.addListener("DeleteListener", new ModelListener()
        {
            @Override
            public void taskFinished()
            {

            }
        });

        dataBaseModel.addListener("SaveChangesListener", new ModelListener()
        {
            @Override
            public void taskFinished()
            {

            }
        });
    }

    public void deleteSelected()
    {
        String titleToDelete = storageView.getSelectedTitle();

        if(storageView.selectedEntryExists())
        {
            dataBaseModel.deleteEntry(titleToDelete);
            System.out.println("Ya borre");
            SuccessfulTask.pageDeleted();
            comboBoxModelSetUp();
            storageView.getPaneContent().setText("");
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
            dataBaseModel.saveInfo(titleToChange, newInfo);
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
        ArrayList<String> titles = dataBaseModel.getTitles();
        Object[] savedTVSeries = titles.stream().sorted().toArray();
        storageView.setSavedTVSeriesModel(savedTVSeries);
    }
}