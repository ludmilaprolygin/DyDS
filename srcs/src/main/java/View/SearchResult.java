package View;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.swing.*;

public class SearchResult extends JMenuItem
{
    protected String title;
    protected String pageID;
    protected String snippet;

    public SearchResult (String title, String pageID, String snippet)
    {
        this.title = title;
        this.pageID = pageID;
        this.snippet = snippet;
    }

    public String getPageID() { return pageID; }
    public String getSnippet() {  return snippet; }
    public String getTitle() { return title; }
}