package View;

import Presenter.ShowSavedPagePresenter;
import View.Popup.OptionsPopup;
import javax.swing.*;
import java.awt.*;

public class StorageView implements View
{
    protected JPanel storagePanel;
    protected JComboBox<String> savedTVSeries;
    protected JTextPane storedPageContent;
    protected JPopupMenu optionsPopup;
    protected ShowSavedPagePresenter showSavedPagePresenter;

    public StorageView()
    {
        setUp();
    }

    protected void setUp()
    {
        optionsPopup = new OptionsPopup();
        storedPageContentSetUp();
        storagePanelSetUp();

        initializeListeners();
    }
    protected void storedPageContentSetUp()
    {
        storedPageContent.setContentType("text/html");
        storedPageContent.setEditable(true);
        storedPageContent.setComponentPopupMenu(optionsPopup);
    }
    protected void storagePanelSetUp() { storagePanel.setToolTipText("Stored info"); }
    protected void initializeListeners()
    {
        initializeSavedTVSeriesListener();
    }
    protected void initializeSavedTVSeriesListener()
    {
        savedTVSeries.addActionListener(e ->
                showSavedPagePresenter.onSelectedSavedResult());
    }

    public JPanel getStoragePanel() { return storagePanel; }
    public JComboBox<String> getSavedTVSeries() { return savedTVSeries; }
    public JTextPane getPaneContent() { return storedPageContent; }

    public void setShowSavedPagePresenter(ShowSavedPagePresenter showSavedPagePresenter)
    {
        this.showSavedPagePresenter = showSavedPagePresenter;
    }

    public void disableAll()
    {
        for(Component c: storagePanel.getComponents())
            c.setEnabled(false);
        storedPageContent.setEnabled(false);
    }
    public void enableAll()
    {
        for(Component c: storagePanel.getComponents())
            c.setEnabled(true);
        storedPageContent.setEnabled(true);
    }
}