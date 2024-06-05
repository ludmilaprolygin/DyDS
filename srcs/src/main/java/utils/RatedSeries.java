package utils;

import java.util.Date;

public class RatedSeries
{
    protected int pageID;
    protected String title;
    protected int score;
    protected Date date;

    public RatedSeries(int pageID, String title, int score, Date date)
    {
        this.pageID = pageID;
        this.title = title;
        this.score = score;
        this.date = date;
    }

    public int getPageID() { return pageID; }
    public String getTitle() { return title; }
    public int getScore() { return score; }
    public Date getDate() { return date; }
}