package Presenter;

import View.Messages.RetrievingWikipediaError;
import View.NewMainWindow;

public class ProgramPresenter
{
    protected static NewMainWindow mainWindow;

    public static void main (String[]args)
    {
        mainWindow = new NewMainWindow();
    }
}