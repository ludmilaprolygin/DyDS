package dyds.tvseriesinfo.fulllogic;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.swing.*;

public class SearchResult extends JMenuItem
{
    protected String title;
    protected String pageID;
    protected String snippet;

    public SearchResult(JsonElement je) { manageJsonElement(je); }
    
    protected void manageJsonElement(JsonElement je)
    {
        JsonObject searchResult = je.getAsJsonObject();
        String searchResultTitle = getSearchResultTitle(searchResult);
        String searchResultPageId = getSearchResultPageId(searchResult);
        String searchResultSnippet = getSearchResultSnippet(searchResult);

        setUpAttributes(searchResultTitle, searchResultPageId, searchResultSnippet);
    }
    protected String getSearchResultTitle(JsonObject searchResult) { return searchResult.get("title").getAsString(); }
    protected String getSearchResultPageId(JsonObject searchResult) { return searchResult.get("pageid").getAsString(); }
    protected String getSearchResultSnippet(JsonObject searchResult) { return searchResult.get("snippet").getAsString(); }
    protected void setUpAttributes(String title, String pageID, String snippet)
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

    public String getPageID() { return pageID; }
    public String getSnippet() {  return snippet; }
    public String getTitle() { return title; }
}