package Presenter;

import Model.DataBaseModel;
import Presenter.Listeners.ModelListener;
import View.StorageView;
import dyds.tvseriesinfo.fulllogic.DataBase;
import utils.StringFormatting;
import javax.swing.*;

public class ShowSavedPagePresenter
{
    protected StorageView storageView;
    protected DataBaseModel dataBaseModel;
    public ShowSavedPagePresenter(StorageView storageView, DataBaseModel dataBaseModel)
    {
        this.storageView = storageView;
        this.dataBaseModel = dataBaseModel;

        initializeDataBaseModelListeners();
    }

    protected void initializeDataBaseModelListeners()
    {
        String modelListenerName = getModelListenerName();
        dataBaseModel.addListener("getExtractListener", new ModelListener()
        {
            @Override
            public void taskFinished() { showSavedTVSeriesPage(); }
        });
    }

    public void onSelectedSavedResult()
    {
        String title = storageView.getSelectedTitle();
        String extract = dataBaseModel.getExtract(title);
    }
    protected void showSavedTVSeriesPage()
    {
        JTextPane storedPageContent = storageView.getPaneContent();
        String title = storageView.getSelectedTitle();
        String extract = DataBase.getExtract(title); //dataBaseExtractModel.getExtract(title);
        storedPageContent.setText(
                StringFormatting.textBodyToHtml(extract));
    }

    protected String getModelListenerName()
    {
        String modelListenerName = getClass().getName()
                .replace("Presenter", "")
                .replace(".", "");
        modelListenerName += "Listener";
        return modelListenerName;
    }
}