package Presenter.Implemented;

import Model.DataBaseModel;
import Model.PageModel;
import Model.Listeners.ModelListener;
import Presenter.Interfaces.RatedDataBasePresenter;
import Presenter.RatedSeries;
import utils.*;
import utils.Messages.UnsuccessfulTask;
import View.RatedView;
import View.SearchView;
import View.RatedResult;
import View.MainWindow;
import View.SearchResult;
import com.google.gson.JsonObject;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Date;

public class RatedDataBasePresenterImpl implements RatedDataBasePresenter
{
    protected DataBaseModel dataBaseModel;
    protected PageModel pageModel;
    protected RatedView ratedView;
    protected SearchView searchView;
    protected JsonObject jsonObject;
    protected ShowSearchedPagePresenterImpl showSearchedPagePresenter;

    public RatedDataBasePresenterImpl(RatedView ratedView, SearchView searchView, PageModel pageModel, DataBaseModel dataBaseModel)
    {
        this.dataBaseModel = dataBaseModel;
        this.ratedView = ratedView;
        this.searchView = searchView;
        this.pageModel = pageModel;
        jsonObject = null;

        ratedTVseriesListSetUp();
        initializeModelListeners();
    }

    private void ratedTVseriesListSetUp()
    {
        ArrayList<RatedSeries> allRated = dataBaseModel.getAllRated();
        DefaultTableModel tableModel = ratedView.getTableModel();

        for (RatedSeries rated : allRated)
        {
            String pageID = rated.getPageID();
            String title = rated.getTitle();
            int score = rated.getScore();
            Date date = rated.getDate();

            RatedResult ratedResult = new RatedResult(pageID, title, score, date);

            tableModel.addRow(new Object[]{title, score, StringFormatting.dateFormat(date)});
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
        String pageID = JsonParsing.getAttributeAsString(jsonObject, "pageid");

        if (score >=1 && score <= 10) { dataBaseModel.rateSeries(pageID, termToRate, score); }
    }
    protected int getScore()
    {
        String input = JOptionPane.showInputDialog(null, "Enter a number (1 to 10):", "Rating", JOptionPane.PLAIN_MESSAGE);
        return manageInput(input);
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
            { UnsuccessfulTask.scoringError(); }
        }
        return ratingNumber;
    }

    public void onClickRatedEntry()
    {
        String selectedTitle = ratedView.getSelectedTitle();
        RatedSeries ratedSeries = dataBaseModel.getRatedSeries(selectedTitle);
        SearchResult sr = new SearchResult(ratedSeries.getTitle(), ""+ratedSeries.getPageID(), "");
        searchView.updateSearchResult(sr);
        System.out.println(searchView.getSelectedSearchResult().getTitle());
        pageModel.getPageFromWikipedia("" + ratedSeries.getPageID());
        generateJsonObjectFromLastSearchResponse();
        showSearchedPagePresenter.showPageContent();
        searchView.updateRateButton(ratedSeries.getScore());
        MainWindow.openSearchTab();
    }

    protected void updateRatedTVSeriesTable()
    {
        generateJsonObjectFromLastSearchResponse();
        String termToRate = JsonParsing.getAttributeAsString(jsonObject, "title");

        RatedSeries rated = dataBaseModel.getRatedSeries(termToRate);
        DefaultTableModel tableModel = ratedView.getTableModel();

        String pageID = rated.getPageID();
        String title = rated.getTitle();
        int score = rated.getScore();
        Date date = rated.getDate();

        RatedResult ratedResult = new RatedResult(pageID, title, score, date);

        tableModel.addRow(new Object[]{title, score, StringFormatting.dateFormat(date)});
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

    public void setShowSearchedPagePresenter(ShowSearchedPagePresenterImpl pr) { showSearchedPagePresenter = pr; }
}