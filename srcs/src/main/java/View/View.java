package View;

import Presenter.Listeners.PaneHyperlinkListener;

import javax.swing.*;

public abstract class View
{
    public abstract JTextPane getPaneContent();
    public abstract void disableAll();
    public abstract void enableAll();

    protected void initializeHyperlinkListener()
    {
        JTextPane panel = getPaneContent();
        panel.addHyperlinkListener(new PaneHyperlinkListener());
    }
}