package Stubs;

import Presenter.RatedSeries;
import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RatedTVSeriesDataBaseStub
{
    protected String pageID;
    protected String title;
    protected int score;

    public ArrayList<String> getTitles()
    {
        ArrayList<String> titles = new ArrayList<String>();
        titles.add("title1");
        titles.add("title2");
        titles.add("title3");

        return titles;
    }

    public RatedSeries getEntry(String title)
    {
        return new RatedSeries("111", "title1",  1, new Date());
    }

    public int getScore(String title)
    {
        return 0;
    }

    public ArrayList<RatedSeries> getAllEntries()
    {
        ArrayList<RatedSeries> allEntries = new ArrayList<RatedSeries>();

        Date date = mock(Date.class);
        when(date.toString()).thenReturn("date");

        allEntries.add(new RatedSeries("111", "title1",  1, date));
        allEntries.add(new RatedSeries("222", "title2",  2, date));
        allEntries.add(new RatedSeries("333", "title3",  3, date));

        return allEntries;
    }

    public void saveInfo(String pageid, String title, int score)
    {
        this.pageID = pageid;
        this.title = title;
        this.score = score;
    }

    public String getPageID() { return pageID; }
    public String getTitle() { return title; }
    public int getScore() { return score; }

}