package View.Storage.Popup;

import javax.swing.*;

public class StoredInfoPopupMenu extends JPopupMenu
{
    public StoredInfoPopupMenu()
    {
       createItems();
    }

    protected void createItems()
    {
        add(new DeleteItem());
        add(new SaveChangesItem());
    }
}