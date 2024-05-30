package View.Popup;

import javax.swing.JMenuItem;

public abstract class PopupItem extends JMenuItem
{
    public PopupItem(String text)
    {
        super(text);
        initializeListeners();
    }
    public abstract void initializeListeners();
}