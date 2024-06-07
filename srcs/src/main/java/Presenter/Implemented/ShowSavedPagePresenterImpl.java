package Presenter.Implemented;

import Model.DataBaseModel;
import Model.Listeners.ModelListener;
import Presenter.Interfaces.ShowSavedPagePresenter;
import View.StorageView;
import utils.StringFormatting;
import javax.swing.*;

public class ShowSavedPagePresenterImpl implements ShowSavedPagePresenter
{
    protected StorageView storageView;
    protected DataBaseModel dataBaseModel;
    public ShowSavedPagePresenterImpl(StorageView storageView, DataBaseModel dataBaseModel)
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