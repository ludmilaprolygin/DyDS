package utils;

import javax.swing.text.html.HTML;

public class StringFormatting
{
    public static String textBodyToHtml(String text)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("<font face=\"arial\">");
        String fixedText = fixedHTMLtags(text);
        builder.append(fixedText);
        builder.append("</font>");
        return builder.toString();
    }
    protected static String fixedHTMLtags(String HTMLtoFix)
    {
        return HTMLtoFix
                .replace("'", "`")
                .replace("<abbr title=\"number\">", "")
                .replace("</abbr>", "");
    }

    public static String HTMLtitle(String title) { return "<h1>" + title + "</h1>"; }
    public static String HTMLurl(String url) { return "<p><a href='" + url + "'>" + url + "</a></p>"; }
    public static String prepareForSQL(String text) { return text.replace("'", "`"); }
}