package dyds.tvseriesinfo.fulllogic;

import javax.swing.*;

public class SearchResult extends JMenuItem
{
    protected String title;
    protected String pageID;
    protected String snippet;

    public SearchResult(String title, String pageID, String snippet)
    {
        this.title = title;
        this.pageID = pageID;
        this.snippet = snippet;

        String itemText = formatToHTML();
        this.setText(itemText);
    }

    protected String formatToHTML()
    {
        String toReturn = "<html><font face=\"arial\">" + title + ": " + snippet;
        toReturn = toReturn.replace("<span class=\"searchmatch\">", "")
                .replace("</span>", "");

        return toReturn;
    }
}
