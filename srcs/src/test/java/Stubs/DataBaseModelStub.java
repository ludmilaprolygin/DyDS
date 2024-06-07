package Stubs;

import Model.DataBaseModel;
import Presenter.RatedSeries;

import java.util.ArrayList;

public class DataBaseModelStub extends DataBaseModel
{
    protected RatedTVSeriesDataBaseStub ratedTVSeriesDataBaseStub;
    protected SavedTVSeriesDataBaseStub savedTVSeriesDataBaseStub;
    protected String extract;

    public DataBaseModelStub()
    {
        ratedTVSeriesDataBaseStub = new RatedTVSeriesDataBaseStub();
        savedTVSeriesDataBaseStub = new SavedTVSeriesDataBaseStub();
    }

    public void getExtract(String title)
    { extract = savedTVSeriesDataBaseStub.getExtract(title); }

    public String getExtract() { return extract; }

    public ArrayList<String> getSavedTitles()
    { return savedTVSeriesDataBaseStub.getTitles(); }

    public ArrayList<String> getRatedTitles()
    { return ratedTVSeriesDataBaseStub.getTitles(); }

    public int getScore(String title)
    { return ratedTVSeriesDataBaseStub.getScore(title); }

    public ArrayList<RatedSeries> getAllRated()
    { return ratedTVSeriesDataBaseStub.getAllEntries(); }

    public RatedSeries getRatedSeries(String title)
    { return ratedTVSeriesDataBaseStub.getEntry(title); }

    public void saveSeries(String title, String info) { }

    public void deleteSavedEntry(String title) { }

    public void changeSeries(String title, String info) { }

    protected void saveInfoOnDataBase(String title, String info) { }

    public void rateSeries(String pageID, String title, int score) { }
}