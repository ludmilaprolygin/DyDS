package Presenter;

import Model.DataBaseModel;
import Model.PageModel;
import Presenter.Listeners.ModelListener;
import View.Messages.UnsuccessfulTask;
import View.View;
import View.RatedView;
import View.SearchView;
import View.RatedResult;
import com.google.gson.JsonObject;
import utils.ImageManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

public class RatedDataBasePresenter
{
    protected DataBaseModel dataBaseModel;
    protected PageModel pageModel;
    protected View view;
    protected SearchView searchView;
    protected JsonObject jsonObject;

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
        ArrayList<String> allRated = dataBaseModel.getAllRated();
        for (String title : allRated)
        {
            System.out.println(title);
            //searchView.addRatedTVSeries(title);
        }
    }

    protected void ratedListUpdate(ArrayList<String> allRated)
    {
        DefaultListModel<RatedResult> listModel = new DefaultListModel<>();
        Date today = new Date();

//        for (String entry : allRated)
//        {
//            System.out.println(entry);
//            //searchView.addRatedTVSeries(title);
//            RatedResult ratedResult = new RatedResult(title, dataBaseModel.getScore(title), today);
//        }

        RatedView ratedView = (RatedView) view;
        ratedView.getRatedTVseriesList().setModel(listModel);
    }

    protected void initializeModelListeners()
    {
        //initializePageModelListener();
        initializeRateDataBaseModelListener();
    }

    private void initializeRateDataBaseModelListener()
    {
        String modelListenerName = getModelListenerName();
        dataBaseModel.addListener(modelListenerName, new ModelListener()
        {
            @Override
            public void taskFinished()
            {
                updateRateButton();
//              updateSearchResult();
            }
        });
    }

//    protected void initializePageModelListener()
//    {
//        String modelListenerName = getModelListenerName();
//        pageModel.addListener(modelListenerName, new ModelListener()
//        {
//            @Override
//            public void taskFinished() {  }
//        });
//    }

    public void onClickRateButton()
    {
        saveOnDataBase();
    }
    protected void saveOnDataBase()
    {
        String termToRate = searchView.getSearchedTitle();
        int score = getScore();

        if (score >=1 && score <= 10) { dataBaseModel.rateSeries(termToRate, score); }
    }
    protected int getScore()
    {
        String input = JOptionPane.showInputDialog(null, "Enter a number (1 to 10):", "Rating", JOptionPane.PLAIN_MESSAGE);
        int score = manageInput(input);

        if(score < 1 || score > 10) { score = 0; }
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

        String termToRate = searchView.getSearchedTitle();
        int score = dataBaseModel.getScore(termToRate);
        searchView.getRateButton().setIcon(new ImageIcon(ImageManager.getImageURL(score)));
    }

    protected String getModelListenerName()
    {
        String modelListenerName = getClass().getName()
                .replace("Presenter", "")
                .replace(".", "");
        modelListenerName += "Listener";
        return modelListenerName;
    }
}