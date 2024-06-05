package Presenter;

import Model.DataBaseModel;
import Model.PageModel;
import Presenter.Listeners.ModelListener;
import View.Messages.SuccessfulTask;
import View.Messages.UnsuccessfulTask;
import View.StorageView;
import View.View;
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
    protected View view;
    protected JsonObject jsonObject;

    public SavedDataBasePresenter(StorageView storageView, PageModel pageModel, DataBaseModel dataBaseModel)
    {
        this.dataBaseModel = dataBaseModel;
        this.view = storageView;
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
        });
    }

    protected void initializeSaveInfoDataBaseModelListener()
    {
        dataBaseModel.addListener(new ModelListener()
        {
            @Override
            public void didSaveTVSeries() { showSavedTVSeries(); }
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

        System.out.println("Saved title " + selectedResultTitle);
        System.out.println("Saved info extract " + text);
        dataBaseModel.saveSeries(selectedResultTitle, text);
    }

    protected void showSavedTVSeries()
    {
        comboBoxModelSetUp();
        StorageView storageView = (StorageView) view;
        storageView.getPaneContent().setText(getExtract());
        SuccessfulTask.pageSaved();
    }
    protected void comboBoxModelSetUp()
    {
        StorageView storageView = (StorageView) view;
        ArrayList<String> titles = dataBaseModel.getSavedTitles();
        Object[] savedTVSeries = titles.stream().sorted().toArray();
        storageView.setSavedTVSeriesModel(savedTVSeries);
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
        JTextPane pageContent = view.getPaneContent();

        pageContent.setText(textToDisplay);
        pageContent.setCaretPosition(0);
    }
}