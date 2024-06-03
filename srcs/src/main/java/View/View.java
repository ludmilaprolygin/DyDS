package View;

import Presenter.HyperlinkPresenter;
import javax.swing.*;

public abstract class View
{
    public abstract JTextPane getPaneContent();
    public abstract void disableAll();
    public abstract void enableAll();

    protected void initializeHyperlinkListener()
    {
        JTextPane panel = getPaneContent();
        panel.addHyperlinkListener(e ->
                HyperlinkPresenter.onHyperlinkClick());
    }
}