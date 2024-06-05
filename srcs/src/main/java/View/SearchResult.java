package View;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import utils.ImageManager;

import javax.swing.*;

public class SearchResult extends JMenuItem
{
    protected String title;
    protected String pageID;
    protected String snippet;
    protected ImageIcon scoredImage;
    protected boolean isRated;

    public SearchResult (String title, String pageID, String snippet)
    {
        this.title = title;
        this.pageID = pageID;
        this.snippet = snippet;
    }

    public String getPageID() { return pageID; }
    public String getTitle() { return title; }
    public void setIsRatedIcon()
    {
        String url = ImageManager.getRatedImageURL();
        //scoredImage = ImageManager.getRatedImage();
        scoredImage = new ImageIcon("srcs/src/main/resources/star.png");
        //this.setImageIcon(scoredImage);
        this.setIcon(scoredImage);
    }
    public boolean isRated() { return isRated; }
    public void setRated(boolean isRated) { this.isRated = isRated; }
}