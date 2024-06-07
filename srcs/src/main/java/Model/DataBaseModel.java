package Model;

import Model.DataBase.RatedTVSeriesDataBase;
import Model.DataBase.SavedTVSeriesDataBase;
import Model.Listeners.ModelListener;
import Presenter.RatedSeries;

import java.util.ArrayList;

public class DataBaseModel extends Model
{
    protected RatedTVSeriesDataBase ratedTVSeriesDataBase;
    protected SavedTVSeriesDataBase savedTVSeriesDataBase;
    protected String extract;

    public DataBaseModel()
    {
        ratedTVSeriesDataBase = new RatedTVSeriesDataBase();
        savedTVSeriesDataBase = new SavedTVSeriesDataBase();
    }

    public void getExtract(String title)
    {
        extract = savedTVSeriesDataBase.getExtract(title);
        notifyGetExtractFinishedListener();
    }
    public String getExtract() { return extract; }

    public ArrayList<String> getSavedTitles()
    {
        return savedTVSeriesDataBase.getTitles();
    }

    public ArrayList<String> getRatedTitles()
    {
        return ratedTVSeriesDataBase.getTitles();
    }

    public int getScore(String title)
    {
        return ratedTVSeriesDataBase.getScore(title);
    }

    public void deleteSavedEntry(String title)
    {
        savedTVSeriesDataBase.deleteEntry(title);
        notifyDeletedSavedFinishedListener();
    }

    public ArrayList<RatedSeries> getAllRated()
    {
        return ratedTVSeriesDataBase.getAllEntries();
    }

    public RatedSeries getRatedSeries(String title)
    {
        return ratedTVSeriesDataBase.getEntry(title);
    }

    public void saveSeries(String title, String info)
    {
        saveInfoOnDataBase(title, info);
        notifySaveTVSeriesFinishedListener();
    }
    public void changeSeries(String title, String info)
    {
        saveInfoOnDataBase(title, info);
        notifyChangeTVSeriesFinishedListener();
    }
    protected void saveInfoOnDataBase(String title, String info)
    { savedTVSeriesDataBase.saveInfo(title, info); }

    public void rateSeries(String pageID, String title, int score)
    {
        ratedTVSeriesDataBase.saveInfo(pageID, title, score);
        notifyRateTVSeriesFinishedListener();
    }

    protected void notifyGetExtractFinishedListener()
    {
        for(ModelListener modelListener : modelListeners)
            modelListener.didGetExtract();
    }
    protected void notifyDeletedSavedFinishedListener()
    {
        for(ModelListener modelListener : modelListeners)
            modelListener.didDeletedSaved();
    }
    protected void notifySaveTVSeriesFinishedListener()
    {
        for(ModelListener modelListener : modelListeners)
            modelListener.didSaveTVSeries();
    }
    protected void notifyRateTVSeriesFinishedListener()
    {
        for(ModelListener modelListener : modelListeners)
            modelListener.didRateTVSeries();
    }
    protected void notifyChangeTVSeriesFinishedListener()
    {
        for(ModelListener modelListener : modelListeners)
            modelListener.didChangeTVSeries();
    }
}