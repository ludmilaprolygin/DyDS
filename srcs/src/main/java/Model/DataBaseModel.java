package Model;

import dyds.tvseriesinfo.fulllogic.DataBase;
import java.util.ArrayList;

public class DataBaseModel extends Model
{
    protected String lastConsultedExtract;
    protected String lastSavedTitle;
    public DataBaseModel() { super(); }

    public void saveInfo(String title, String info)
    {
        DataBase.saveInfo(title, info);
        lastSavedTitle = title;
        notifyListeners();
    }

    public String getExtract(String title)
    {
        String extract = DataBase.getExtract(title);
        lastConsultedExtract = extract;
        notifyListeners();
        return extract;
    }
    public ArrayList<String> getTitles() {
        ArrayList<String> titles = DataBase.getTitles();
        notifyListeners();
        return titles;
    }
    public void deleteEntry(String title)
    {
        DataBase.deleteEntry(title);
        notifyListeners();
    }
    public String getLastConsultedExtract() { return lastConsultedExtract; }
    public String getLastSavedTitle() { return lastSavedTitle; }
}