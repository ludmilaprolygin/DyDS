package utils;

public class StringFormatting
{
    public static String textToHtml(String text)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("<font face=\"arial\">");
        String fixedText = text
                .replace("'", "`"); //Replace to avoid SQL errors, we will have to find a workaround..
        builder.append(fixedText);
        builder.append("</font>");
        return builder.toString();
    }
    public static String HTMLtitle(String title) { return "<h1>" + title + "</h1>"; }
    public static String HTMLurl(String url) { return "<p><a href='" + url + "'>" + url + "</a></p>"; }
    public static String prepareForSQL(String text) { return text.replace("'", "`"); }
}