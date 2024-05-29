package View.Storage.Popup;

import javax.swing.JMenuItem;

public abstract class PopupItem extends JMenuItem
{
    public PopupItem(String text)
    {
        super(text);
        setBehaviour();
    }
    public abstract void setBehaviour();
}