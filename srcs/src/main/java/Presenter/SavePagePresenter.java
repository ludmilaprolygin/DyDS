package Presenter;

import Model.DataBaseModel;
import Model.PageModel;
import Presenter.Listeners.ModelListener;
import View.Messages.SuccessfulTask;
import View.StorageView;
import utils.StringFormatting;

import javax.swing.*;
import java.util.ArrayList;

public class SavePagePresenter extends PagePresenter
{
    protected DataBaseModel dataBaseModel;

    public SavePagePresenter(StorageView storageView, PageModel pageModel, DataBaseModel dataBaseModel)
    {
        super(storageView, pageModel);
        this.dataBaseModel = dataBaseModel;

        updateSavedTVSeriesTitles();
        initializeModelListeners();
    }
    protected void initializeModelListeners()
    {
        initializePageModelListener();
        initializeSaveInfoDataBaseModelListener();
    }
    protected void initializePageModelListener()
    {
        pageModel.addListener(new ModelListener()
        {
            @Override
            public void taskFinished() { }
        });
    }
    protected void initializeSaveInfoDataBaseModelListener()
    {
        dataBaseModel.addListener(new ModelListener()
        {
            @Override
            public void taskFinished() { showSavedTVSeries(); }
        });
    }

    public void onClickSaveLocallyButton()
    {
        String text = getExtractFromLastSearchResponse();
        String selectedResultTitle = getTitleFromLastSearchResponse();
        selectedResultTitle = StringFormatting.prepareForSQL(selectedResultTitle);

        dataBaseModel.saveInfo(selectedResultTitle, text);
    }
    protected void showSavedTVSeries()
    {
        updateSavedTVSeriesTitles();
        SuccessfulTask.pageSaved();
    }
    protected void updateSavedTVSeriesTitles()
    {
        StorageView storageView = (StorageView) view;
        JComboBox<String> savedTVSeries = storageView.getSavedTVSeries();
        DefaultComboBoxModel<String> comboBoxModel = comboBoxModelSetUp();
        savedTVSeries.setModel(comboBoxModel);
    }
    private DefaultComboBoxModel<String> comboBoxModelSetUp()
    {
        ArrayList<String> titles = dataBaseModel.getTitles();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        Object[] sortedTitles = titles.stream().sorted().toArray();
        for(Object title : sortedTitles)
            model.addElement((String) title);
        return model;
    }
}