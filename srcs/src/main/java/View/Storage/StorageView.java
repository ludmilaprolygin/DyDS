package View.Storage;

import View.Popup.StoredInfoPopupMenu;
import View.View;

import javax.swing.*;

public class StorageView implements View
{
    protected static StorageView storageView;
    protected JPanel storagePanel;
    protected JComboBox<String> savedTVSeries;
    protected JTextPane storedPageContent;
    protected JPopupMenu storedInfoPopup;

    public static StorageView getInstance()
    {
        if(storageView == null)
            storageView = new StorageView();
        return storageView;
    }
    private StorageView()
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
    public JTextPane getStoredPageContent() { return storedPageContent; }


    /////////////////////////////////////////////////////////////////////////

    @Override
    public void displayPopUp() {

    }
    @Override
    public JTextPane getPaneContent() {
        return null;
    }
}