package View;

import utils.StringFormatting;

import javax.swing.*;
import java.util.Date;

public class RatedResult extends JMenuItem
{
    protected String title;
    protected int score;
    protected Date date;

    public RatedResult(String title, int score, Date date)
    {
        this.title = title;
        this.score = score;
        this.date = date;
    }

    public String toString()
    { return title + " was rated with " + score + " (last updated: " + StringFormatting.dateFormat(date) + ")"; }
}