package View.Popup;

import javax.swing.*;

public class OptionsPopup extends JPopupMenu
{
    public OptionsPopup() { createItems(); }

    protected void createItems()
    {
        add(new DeleteItem());
        add(new SaveChangesItem());
    }
}