package Presenter;

import Model.DataBaseModel;
import Presenter.Listeners.ModelListener;
import View.StorageView;
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
        dataBaseModel.addListener(new ModelListener()
        {
            @Override
            public void taskFinished() { showSavedTVSeriesPage(); }
        });
    }

    public void onSelectedSavedResult()
    {
        JComboBox<String> savedTVSeries = storageView.getSavedTVSeries();
        String title = savedTVSeries.getSelectedItem().toString();
        String extract = dataBaseModel.getExtract(title);
    }
    protected void showSavedTVSeriesPage()
    {
        JTextPane storedPageContent = storageView.getPaneContent();
        String lastConsultedExtract = dataBaseModel.getLastConsultedExtract();
        storedPageContent.setText(
                StringFormatting.textToHtml(lastConsultedExtract));
    }
}