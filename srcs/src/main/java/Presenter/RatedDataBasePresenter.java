package Presenter;

import Model.DataBaseModel;
import Model.PageModel;
import Presenter.Listeners.ModelListener;
import View.Messages.UnsuccessfulTask;
import View.View;
import View.RatedView;
import View.SearchView;
import View.RatedResult;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Response;
import utils.ImageManager;
import utils.JsonParsing;
import utils.RatedSeries;
import utils.StringFormatting;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class RatedDataBasePresenter
{
    protected DataBaseModel dataBaseModel;
    protected PageModel pageModel;
    protected View view;
    protected SearchView searchView;
    protected JsonObject jsonObject;
    protected HashMap<Integer, RatedResult> ratedResults;

    public RatedDataBasePresenter(RatedView ratedView, SearchView searchView, PageModel pageModel, DataBaseModel dataBaseModel)
    {
        this.dataBaseModel = dataBaseModel;
        this.view = ratedView;
        this.searchView = searchView;
        this.pageModel = pageModel;
        jsonObject = null;

        ratedResults = new HashMap<Integer, RatedResult>();

        ratedTVseriesListSetUp();
        initializeModelListeners();
    }

    private void ratedTVseriesListSetUp()
    {
        ArrayList<RatedSeries> allRated = dataBaseModel.getAllRated();
        DefaultListModel<RatedResult> listModel = new DefaultListModel<>();
        //Date today = new Date();

        for (RatedSeries rated : allRated)
        {
            //System.out.println(title);
            int pageID = rated.getPageID();
            String title = rated.getTitle();
            int score = rated.getScore();
            Date date = rated.getDate();

            RatedResult ratedResult = new RatedResult(pageID, title, score, date);
            listModel.addElement(ratedResult);
        }

        RatedView ratedView = (RatedView) view;
        ratedView.getRatedTVseriesList().setModel(listModel);
    }

    protected void initializeModelListeners()
    {
        initializePageModelListener();
        initializeRateDataBaseModelListener();
    }

    private void initializeRateDataBaseModelListener()
    {
        dataBaseModel.addListener(new ModelListener() {
            @Override
            public void didRateTVSeries()
            {
                updateRateButton();
                updateRatedTVSeriesList();
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
            public void didSaveTVSeries() { }
        });
    }
    protected void initializePageModelListener()
    {
        pageModel.addListener(new ModelListener()
        {
            @Override
            public void didSearchPageOnWiki() { generateJsonObjectFromLastSearchResponse(); } // Aca deberia manejar algo
            @Override
            public void didSearchTermOnWiki() { }
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

    public void onClickRateButton()
    {
        saveOnDataBase();
    }
    protected void saveOnDataBase()
    {
        generateJsonObjectFromLastSearchResponse();
        String termToRate = JsonParsing.getAttributeAsString(jsonObject, "title");

        int score = getScore();
        int pageID = Integer.parseInt(JsonParsing.getAttributeAsString(jsonObject, "pageid"));

        if (score >=1 && score <= 10) { dataBaseModel.rateSeries(pageID, termToRate, score); }
    }
    protected int getScore()
    {
        String input = JOptionPane.showInputDialog(null, "Enter a number (1 to 10):", "Rating", JOptionPane.PLAIN_MESSAGE);
        int score = manageInput(input);

        return score;
    }
    protected int manageInput(String input)
    {
        int ratingNumber = 0;
        if (input != null)
        {
            try
            {
                ratingNumber = Integer.parseInt(input);
                if(ratingNumber < 1 || ratingNumber > 10)
                {
                    ratingNumber = 0;
                    UnsuccessfulTask.scoringError();
                }
            }
            catch (NumberFormatException e)
            {
                UnsuccessfulTask.scoringError();
            }
        }
        return ratingNumber;
    }
    protected void updateRateButton()
    {
        String termToRate = JsonParsing.getAttributeAsString(jsonObject, "title");//searchView.getSearchedTitle();
        int score = dataBaseModel.getScore(termToRate);
        searchView.getRateButton().setIcon(new ImageIcon(ImageManager.getImageURL(score)));
    }
    protected void updateRatedTVSeriesList()
    {

    }

    protected void generateJsonObjectFromLastSearchResponse()
    {
        Response<String> callForPageResponse = pageModel.getResponse();
        jsonObject = JsonParsing.getQueryResultAsJsonObject(callForPageResponse, "pages");
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
}