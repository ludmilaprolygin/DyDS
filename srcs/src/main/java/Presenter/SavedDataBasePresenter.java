package Presenter;

import Model.DataBaseModel;
import Model.PageModel;
import Model.Listeners.ModelListener;
import utils.Messages.SuccessfulTask;
import utils.Messages.UnsuccessfulTask;
import View.StorageView;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Response;
import utils.JsonParsing;
import utils.StringFormatting;

import javax.swing.*;
import java.util.ArrayList;

public class SavedDataBasePresenter
{
    protected DataBaseModel dataBaseModel;
    protected PageModel pageModel;
    protected StorageView storageView;
    protected JsonObject jsonObject;

    public SavedDataBasePresenter(StorageView storageView, PageModel pageModel, DataBaseModel dataBaseModel)
    {
        this.dataBaseModel = dataBaseModel;
        this.storageView = storageView;
        this.pageModel = pageModel;
        jsonObject = null;

        comboBoxModelSetUp();
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
            public void didSearchTermOnWiki() { showPageContent(); }
            @Override
            public void didSearchPageOnWiki() { }
            @Override
            public void didGetExtract() { }
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

    protected void initializeSaveInfoDataBaseModelListener()
    {
        dataBaseModel.addListener(new ModelListener()
        {
            @Override
            public void didSaveTVSeries()
            {
                showSavedTVSeries();
                updateComboBox();
            }
            @Override
            public void didSearchTermOnWiki() { }
            @Override
            public void didSearchPageOnWiki() { }
            @Override
            public void didGetExtract() { }
            @Override
            public void didDeletedSaved() { }
            @Override
            public void didRateTVSeries() { }
            @Override
            public void didChangeTVSeries() { }
        });
    }

    public void onClickSaveLocallyButton()
    {
        generateJsonObjectFromLastSearchResponse();
        saveOnDataBase();
    }
    protected void saveOnDataBase()
    {
        String text = getExtractFromLastSearchResponse();
        String selectedResultTitle = getTitleFromLastSearchResponse();
        selectedResultTitle = StringFormatting.replaceApostrophe(selectedResultTitle);

        dataBaseModel.saveSeries(selectedResultTitle, text);
    }

    protected void showSavedTVSeries()
    {
        comboBoxModelSetUp();
        storageView.getPaneContent().setText(getExtract());
        SuccessfulTask.pageSaved();
    }
    protected void comboBoxModelSetUp()
    {
        ArrayList<String> titles = dataBaseModel.getSavedTitles();
        Object[] savedTVSeries = titles.stream().sorted().toArray();
        storageView.setSavedTVSeriesModel(savedTVSeries);

    }
    protected void updateComboBox()
    {
        comboBoxModelSetUp();
        JComboBox<String> comboBox = storageView.getSavedTVSeries();
        String title = getTitleFromLastSearchResponse();
        comboBox.setSelectedItem(title);
    }

    protected String getExtract()
    {
        return getExtractFromLastSearchResponse();
    }

    protected String getExtractFromLastSearchResponse()
    {
        if(jsonObject == null)
            generateJsonObjectFromLastSearchResponse();

        JsonElement selectedResultExtract = jsonObject.get("extract");

        String url = jsonObject.get("fullurl").getAsString();
        String textToDisplay = "";

        if (selectedResultExtract == null) { UnsuccessfulTask.wikipediaError("No results were found"); }
        else
        {
            String title = JsonParsing.getAttributeAsString(jsonObject, "title");
            textToDisplay = StringFormatting.HTMLtitle(title);
            textToDisplay += selectedResultExtract.getAsString().replace("\\n", "\n");
            textToDisplay += StringFormatting.HTMLurl(url);
            textToDisplay = StringFormatting.textBodyToHtml(textToDisplay);
        }

        return textToDisplay;
    }
    protected String getTitleFromLastSearchResponse()
    {
        if(jsonObject == null)
            generateJsonObjectFromLastSearchResponse();
        return JsonParsing.getAttributeAsString(jsonObject, "title");
    }

    protected void generateJsonObjectFromLastSearchResponse()
    {
        Response<String> callForPageResponse = pageModel.getResponse();
        jsonObject = JsonParsing.getQueryResultAsJsonObject(callForPageResponse, "pages");
    }

    protected void showPageContent()
    {
        String textToDisplay = getExtract();
        JTextPane pageContent = storageView.getPaneContent();
        pageContent.setText(textToDisplay);
        pageContent.setCaretPosition(0);
    }
}