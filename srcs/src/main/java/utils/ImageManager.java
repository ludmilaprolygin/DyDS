package utils;

import javax.swing.*;

public class ImageManager
{
    public static String getUnratedImageURL()
    {
        return "srcs/src/main/resources/0.png";
    }
    public static String getImageURL(int ratingValue)
    {
        if(ratingValue == 0)
            return getUnratedImageURL();
        else
            return "srcs/src/main/resources/" + ratingValue + ".png";
    }
    public static ImageIcon getRatedImage() { return new ImageIcon("srcs/src/main/resources/star.png"); }
    public static String getRatedImageURL() { return "srcs/src/main/resources/star.png"; }
}