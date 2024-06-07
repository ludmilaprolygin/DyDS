package Presenter;

import Model.DataBaseModel;
import Model.Listeners.ModelListener;
import View.StorageView;
import Model.DataBase.SavedTVSeriesDataBase;
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
            public void didGetExtract() { showSavedTVSeriesPage(); }
            @Override
            public void didSearchPageOnWiki() { }
            @Override
            public void didSearchTermOnWiki() { }
            @Override
            public void didDeletedSaved() { }
            @Override
            public void didSaveTVSeries() { }
            @Override
            public void didRateTVSeries() { }
            @Override
            public void didChangeTVSeries() { }
        });
    }

    public void onSelectedSavedResult()
    {
        String title = storageView.getSelectedTitle();
        dataBaseModel.getExtract(title);

    }
    protected void showSavedTVSeriesPage()
    {
        JTextPane storedPageContent = storageView.getPaneContent();
        String extract = dataBaseModel.getExtract();
        storedPageContent.setText(
                StringFormatting.textBodyToHtml(extract));
    }
}