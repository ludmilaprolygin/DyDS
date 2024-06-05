package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatting
{
    public static java.util.Date toUtilDateFromSQLDate(java.sql.Date sqlDate)
    { return new java.util.Date(sqlDate.getTime()); }

    public static java.sql.Date toSQLDateFromUtilDate(java.util.Date utilDate)
    { return new java.sql.Date(utilDate.getTime()); }

    public static String dateFormat(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        return sdf.format(date);
    }
}