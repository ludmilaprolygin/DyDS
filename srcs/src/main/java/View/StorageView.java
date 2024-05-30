package View;

import View.Popup.StoredInfoPopupMenu;

import javax.swing.*;

public class StorageView implements View
{
    protected JPanel storagePanel;
    protected JComboBox<String> savedTVSeries;
    protected JTextPane storedPageContent;
    protected JPopupMenu storedInfoPopup;

    public StorageView()
    {
        setUp();
    }

    protected void setUp()
    {
        storedInfoPopup = new StoredInfoPopupMenu();
        storedPageContentSetUp();
        storagePanelSetUp();
    }
    protected void storedPageContentSetUp()
    {
        storedPageContent.setContentType("text/html");
        storedPageContent.setEditable(true);
        storedPageContent.setComponentPopupMenu(storedInfoPopup);
    }
    protected void storagePanelSetUp() { storagePanel.setToolTipText("Stored info"); }

    public JPanel getStoragePanel() { return storagePanel; }
    public JComboBox<String> getSavedTVSeries() { return savedTVSeries; }
    public JTextPane getPaneContent() { return storedPageContent; }


    /////////////////////////////////////////////////////////////////////////

    @Override
    public void displayPopUp()
    {

    }
}