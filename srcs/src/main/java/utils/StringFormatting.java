package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringFormatting
{
    public static String textBodyToHtml(String text)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("<font face=\"arial\">");
        String fixedText = fixHTMLtags(text);
        builder.append(fixedText);
        builder.append("</font>");
        return builder.toString();
    }
    protected static String fixHTMLtags(String HTMLtoFix)
    {
        return HTMLtoFix
                .replace("'", "`")
                .replace("<abbr title=\"number\">", "")
                .replace("</abbr>", "");
    }

    public static String HTMLtitle(String title) { return "<h1>" + title + "</h1>"; }
    public static String HTMLurl(String url) { return "<p><a href=" + url + ">" + url + "</a></p>"; }
    public static String replaceApostrophe(String text) { return text.replace("'", "`"); }
    public static boolean isURL(String text) { return text.startsWith("https://en.wikipedia.org/wiki/"); }
}