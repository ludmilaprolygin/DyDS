package View;

import utils.DateFormatting;
import utils.StringFormatting;

import javax.swing.*;
import java.util.Date;

public class RatedResult extends JMenuItem
{
    protected String pageID;
    protected String title;
    protected int score;
    protected Date date;

        public RatedResult(String pageID, String title, int score, Date date)
    {
        this.pageID = pageID;
        this.title = title;
        this.score = score;
        this.date = date;
    }

    public String toString()
    { return title + " was rated with " + score + " (last updated: " + DateFormatting.dateFormat(date) + ")"; }
}