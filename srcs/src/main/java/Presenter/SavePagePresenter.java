package Presenter;

import Model.PageModel;
import View.Messages.SuccessfulTask;
import View.StorageView;
import dyds.tvseriesinfo.fulllogic.DataBase;
import utils.StringFormatting;

import javax.swing.*;

public class SavePagePresenter extends PagePresenter
{
    protected StorageView storageView;

    public SavePagePresenter(StorageView storageView, PageModel pageModel)
    {
        this.storageView = storageView;
        this.pageModel = pageModel;

        initializeSavedTVSeriesListeners();
    }

    public void onClickSaveLocallyButton()
    {
        String text = getExtractFromLastSearchResponse();
        String selectedResultTitle = getTitleFromLastSearchResponse();
        JComboBox<String> savedTVSeries = storageView.getSavedTVSeries();

        if(text != "")
        {
            DataBase.saveInfo(selectedResultTitle.replace("'", "`"), text);  //Dont forget the ' sql problem
            savedTVSeries.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));
        }

        SuccessfulTask.pageSaved();
    }

    protected void initializeSavedTVSeriesListeners()
    {
        JComboBox<String> savedTVSeries = storageView.getSavedTVSeries();
        JTextPane storedPageContent = storageView.getPaneContent();

        savedTVSeries.addActionListener(actionEvent ->
                storedPageContent.setText(
                        StringFormatting.textToHtml(
                                DataBase.getExtract(savedTVSeries.getSelectedItem().toString())
                        )));
        // This line is the one that makes the magic happen, it sets the text of the storedPageContent to the text of the selected item in the comboBox
        // The text is retrieved from the database using the getExtract method, which returns the text of the selected item
    }
}