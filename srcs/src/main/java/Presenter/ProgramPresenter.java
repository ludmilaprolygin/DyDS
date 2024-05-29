package Presenter;

import View.NewMainWindow;
import dyds.tvseriesinfo.fulllogic.DataBase;

public class ProgramPresenter
{
    protected static NewMainWindow mainWindow;

    public static void main (String[]args)
    {
        mainWindow = new NewMainWindow();
        DataBase.loadDatabase();
        DataBase.saveInfo("test", "sarasa");

        searchFunctionInitialize();
    }

    protected static void searchFunctionInitialize() { SearchPresenter searchPresenter = SearchPresenter.getInstance(); }
}