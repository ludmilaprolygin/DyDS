package utils;

public class ImageManager
{
    public static String getUnratedImageURL()
    {
        return "srcs/src/main/resources/0.png";
    }
    public static String getImageURL(int ratingValue)
    {
        return "srcs/src/main/resources/" + ratingValue + ".png";
    }
    public static String getRatedImageURL() { return "srcs/src/main/resources/star.png"; }
}