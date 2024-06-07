package Presenter;

import java.util.Date;

public class RatedSeries
{
    protected String pageID;
    protected String title;
    protected int score;
    protected Date date;

    public RatedSeries(String pageID, String title, int score, Date date)
    {
        this.pageID = pageID;
        this.title = title;
        this.score = score;
        this.date = date;
    }

    public String getPageID() { return pageID; }
    public String getTitle() { return title; }
    public int getScore() { return score; }
    public Date getDate() { return date; }

    public String toString() {return title + " " + pageID; }
}