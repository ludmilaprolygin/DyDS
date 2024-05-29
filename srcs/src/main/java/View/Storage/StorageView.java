package View.Storage;

import Presenter.StoragePresenter;

import javax.swing.*;

public class StorageView
{
    protected final StoragePresenter storagePresenter = new StoragePresenter();

    protected JPanel storagePanel;
    protected JComboBox<String> savedTVSeries;
    protected JTextPane storedPageContent;

    public StorageView()
    {
        storedPageContentConfig();
    }

    protected void storedPageContentConfig()
    {
        storedPageContent.setContentType("text/html");
        storedPageContent.setEditable(true);
    }

    public JPanel getStoragePanel() { return storagePanel; }
    public JComboBox<String> getSavedTVSeries() { return savedTVSeries; }
    public JTextPane getStoredPageContent() { return storedPageContent; }
}
