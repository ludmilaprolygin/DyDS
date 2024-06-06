package Model;

import Model.DataBase.RatedTVSeriesDataBase;
import Model.DataBase.SavedTVSeriesDataBase;
import Presenter.Listeners.ModelListener;
import utils.RatedSeries;

import java.util.ArrayList;

public class DataBaseModel extends Model
{
    public DataBaseModel() { super(); }

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
        { SavedTVSeriesDataBase.saveInfo(title, info); }

    public void rateSeries(int pageID, String title, int score)
    {
        RatedTVSeriesDataBase.saveInfo(pageID, title, score);
        notifyRateTVSeriesFinishedListener();
    }

    public String getExtract(String title)
    {
        String extract = SavedTVSeriesDataBase.getExtract(title);
        notifyGetExtractFinishedListener();
        return extract;
    }

    public ArrayList<String> getSavedTitles()
    {
        ArrayList<String> titles = SavedTVSeriesDataBase.getTitles();
        return titles;
    }

    public ArrayList<String> getRatedTitles()
    {
        ArrayList<String> titles = RatedTVSeriesDataBase.getTitles();
        return titles;
    }

    public int getScore(String title)
    {
        int score = RatedTVSeriesDataBase.getScore(title);
        return score;
    }

    public void deleteSavedEntry(String title)
    {
        SavedTVSeriesDataBase.deleteEntry(title);
        notifyDeletedSavedFinishedListener();
    }

    public ArrayList<RatedSeries> getAllRated()
    {
        ArrayList<RatedSeries> rated = RatedTVSeriesDataBase.getAllEntries();
        return rated;
    }

    public RatedSeries getRatedSeries(String title)
    {
        RatedSeries ratedSeries = RatedTVSeriesDataBase.getEntry(title);
        return ratedSeries;
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