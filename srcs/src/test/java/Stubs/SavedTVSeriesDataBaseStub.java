package Stubs;

import Model.DataBase.AbstractDataBase;
import java.util.ArrayList;

public class SavedTVSeriesDataBaseStub extends AbstractDataBase
{
    public ArrayList<String> getTitles()
    {
        ArrayList<String> titles = new ArrayList<>();
        titles.add("title1");
        titles.add("title2");
        titles.add("title3");

        return titles;
    }

    public void saveInfo(String title, String extract) { }

    public String getExtract(String title)
    {
        return "extract";
    }

    public void deleteEntry(String title) { }
}