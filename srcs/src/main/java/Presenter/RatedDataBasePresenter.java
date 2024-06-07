package Presenter;

import Model.DataBase.RatedTVSeriesDataBase;
import Model.DataBaseModel;
import Model.PageModel;
import Model.Listeners.ModelListener;
import utils.*;
import utils.Messages.UnsuccessfulTask;
import View.View;
import View.RatedView;
import View.SearchView;
import View.RatedResult;
import View.MainWindow;
import View.SearchResult;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Date;

public class RatedDataBasePresenter
{
    protected DataBaseModel dataBaseModel;
    protected PageModel pageModel;
    protected View view;
    protected SearchView searchView;
    protected JsonObject jsonObject;
    protected ShowSearchedPagePresenter presenter;

    public RatedDataBasePresenter(RatedView ratedView, SearchView searchView, PageModel pageModel, DataBaseModel dataBaseModel)
    {
        this.dataBaseModel = dataBaseModel;
        this.view = ratedView;
        this.searchView = searchView;
        this.pageModel = pageModel;
        jsonObject = null;

        ratedTVseriesListSetUp();
        initializeModelListeners();
    }

    private void ratedTVseriesListSetUp()
    {
        RatedView ratedView = (RatedView) view;

        ArrayList<RatedSeries> allRated = dataBaseModel.getAllRated();
        DefaultTableModel tableModel = ratedView.getTableModel();

        for (RatedSeries rated : allRated)
        {
            int pageID = rated.getPageID();
            String title = rated.getTitle();
            int score = rated.getScore();
            Date date = rated.getDate();

            RatedResult ratedResult = new RatedResult(pageID, title, score, date);

            tableModel.addRow(new Object[]{title, score, DateFormatting.dateFormat(date)});
        }
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
                updateRatedTVSeriesTable();
                //ratedTVseriesListSetUp();
            }
            @Override
            public void didChangeTVSeries() { }
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

            @Override
            public void didChangeTVSeries() { }
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

    public void onClickRatedEntry()
    {
        RatedView ratedView = (RatedView) view;
        String selectedTitle = ratedView.getSelectedTitle();
        RatedSeries ratedSeries = dataBaseModel.getRatedSeries(selectedTitle);
        SearchResult sr = new SearchResult(ratedSeries.getTitle(), ""+ratedSeries.getPageID(), "");
        searchView.updateSearchResult(sr);
        System.out.println(searchView.getSelectedSearchResult().getTitle());
        pageModel.getPageFromWikipedia("" + ratedSeries.getPageID());
        generateJsonObjectFromLastSearchResponse();
        presenter.showPageContent();
        searchView.updateRateButton(ratedSeries.getScore());
        MainWindow.openSearchTab();
    }

    protected void updateRatedTVSeriesTable()
    {
        RatedView ratedView = (RatedView) view;

        generateJsonObjectFromLastSearchResponse();
        String termToRate = JsonParsing.getAttributeAsString(jsonObject, "title");

        RatedSeries rated = dataBaseModel.getRatedSeries(termToRate);
        DefaultTableModel tableModel = ratedView.getTableModel();

        int pageID = rated.getPageID();
        String title = rated.getTitle();
        int score = rated.getScore();
        Date date = rated.getDate();

        RatedResult ratedResult = new RatedResult(pageID, title, score, date);

        tableModel.addRow(new Object[]{title, score, DateFormatting.dateFormat(date)});
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
        String termToRate = JsonParsing.getAttributeAsString(jsonObject, "title"); //searchView.getSearchedTitle();
        int score = dataBaseModel.getScore(termToRate);
        searchView.getRateButton().setIcon(new ImageIcon(ImageManager.getImageURL(score)));
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

    public void setShowSearchedPagePresenter(ShowSearchedPagePresenter pr) { presenter = pr; }
}