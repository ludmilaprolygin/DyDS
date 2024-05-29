package Presenter;

import View.NewMainWindow;

public class ProgramPresenter
{
    protected static NewMainWindow mainWindow;

    public static void main (String[]args)
    {
        mainWindow = new NewMainWindow();

        searchFunctionInitialize();
    }

    protected static void searchFunctionInitialize() { SearchPresenter searchPresenter = SearchPresenter.getInstance(); }
}