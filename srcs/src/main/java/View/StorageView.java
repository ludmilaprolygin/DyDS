package View;

import javax.swing.*;

public class StorageView
{
    protected JPanel storagePanel;
    protected JComboBox<String> savedTVSeries;
    protected JTextPane storedPageContent;

    public static JPanel createStorageTab()
    {
        StorageView storageView = new StorageView();
        return storageView.getStoragePanel();
    }

    public JPanel getStoragePanel() { return storagePanel; }
}
