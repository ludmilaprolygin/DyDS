package Model;

import Model.DataBase.RatedTVSeriesDataBase;
import Model.DataBase.SavedTVSeriesDataBase;

import java.util.ArrayList;

public class DataBaseModel extends Model
{
    public DataBaseModel() { super(); }

    public void saveSeries(String title, String info)
    {
        SavedTVSeriesDataBase.saveInfo(title, info);
        notifySaveInfoListener();
    }

    public void rateSeries(String title, int score)
    {
        RatedTVSeriesDataBase.saveInfo(title, score);
        notifyRateSeriesListener();
    }

    public String getExtract(String title)
    {
        String extract = SavedTVSeriesDataBase.getExtract(title);
        notifyGetExtractListener();
        return extract;
    }

    public ArrayList<String> getTitles() {
        ArrayList<String> titles = SavedTVSeriesDataBase.getTitles();
        //notifyListeners();
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
        notifyDeleteListener();
    }

    public ArrayList<String> getAllRated()
    {
        ArrayList<String> rated = RatedTVSeriesDataBase.getAllEntries();
        return rated;
    }

    protected void notifyGetExtractListener()
        { modelListenersMap.get("getExtractListener").taskFinished(); }
    protected void notifyDeleteListener()
        { modelListenersMap.get("deleteSavedListener").taskFinished(); }
    protected void notifySaveInfoListener()
        { modelListenersMap.get("SavedDataBaseListener").taskFinished();}
    protected void notifyRateSeriesListener()
        { modelListenersMap.get("RatedDataBaseListener").taskFinished(); }
}