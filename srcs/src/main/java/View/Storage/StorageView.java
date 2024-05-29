package View.Storage;

import Presenter.StoragePresenter;
import View.Storage.Popup.StoredInfoPopupMenu;

import javax.swing.*;

public class StorageView
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
    public JTextPane getStoredPageContent() { return storedPageContent; }
}