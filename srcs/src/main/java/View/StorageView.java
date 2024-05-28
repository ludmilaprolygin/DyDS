package View;

import Presenter.StoragePresenter;

import javax.swing.*;

public class StorageView
{
    protected static final StoragePresenter storagePresenter = new StoragePresenter();

    protected JPanel storagePanel;
    protected JComboBox<String> savedTVSeries;
    protected JTextPane storedPageContent;

    public static JPanel createStorageTab()
    {
        StorageView storageView = new StorageView();
        return storageView.getStoragePanel();
    }

    public JPanel getStoragePanel() { return storagePanel; }
    public JComboBox<String> getSavedTVSeries() { return savedTVSeries; }
    public JTextPane getStoredPageContent() { return storedPageContent; }
}
