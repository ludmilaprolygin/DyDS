package Model;

import dyds.tvseriesinfo.fulllogic.DataBase;
import java.util.ArrayList;

public class DataBaseModel extends Model
{

    public DataBaseModel() { super(); }

    public void saveInfo(String title, String info)
    {
        DataBase.saveInfo(title, info);
        notifySaveInfoListener();
    }

    public String getExtract(String title)
    {
        String extract = DataBase.getExtract(title);
        notifyGetExtractListener();
        return extract;
    }

    public ArrayList<String> getTitles() {
        ArrayList<String> titles = DataBase.getTitles();
        //notifyListeners();
        return titles;
    }

    public void deleteEntry(String title)
    {
        DataBase.deleteEntry(title);
        notifyDeleteListener();
    }

    protected void notifyGetExtractListener()
        { modelListenersMap.get("getExtractListener").taskFinished(); }
    protected void notifyDeleteListener()
        { modelListenersMap.get("deleteListener").taskFinished(); }
    protected void notifySaveInfoListener()
        { modelListenersMap.get("SaveOnDataBaseListener").taskFinished();}
}